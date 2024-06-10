import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class CombinedVisualizer extends JPanel {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private BufferedImage image;
    private int maxPeoplePerTile=5;
    private double density=0.1;
    private static final double SCALE = 2.5;//1.3;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];;
    private ArrayList<Human> population = new ArrayList<Human>();
    private ArrayList<Island> islands = new ArrayList<Island>();
    public CombinedVisualizer() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);


        generateNoiseImage();
        display();
    }
    public ArrayList<Human> getPopulation() {
        return population;
    }

    private void generateNoiseImage() {
        int[][] gradientValues = generateGradientValues(WIDTH, HEIGHT);




        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double nx = x / (double) WIDTH;
                double ny = y / (double) HEIGHT;
                double noiseValue = noise(nx * 10, ny * 10); // Skala szumu
                int noiseGray = (int) ((noiseValue + 1) * 127.5); // Przeskalowanie do zakresu 0-255

                int gradientGray = gradientValues[x][y];

                int gray = fastFloor((noiseGray + fastFloor(1.75*gradientGray)) / 2.75); // Średnia z obu wartości

                int rgb;
                board[x][y] = new Tile(gray);
                if(board[x][y].isLand)
                {
                Random random = new Random();
                if(random.nextDouble() < density){
                int number = (int) (Math.random() * (maxPeoplePerTile+1));
                for (int z = 0; z < number; z++) {
                    Human human = new Human(x,y);
                    board[x][y].humans.add(human);
                    population.add(human);
                }}}

                if (board[x][y].isLand && !board[x][y].humans.isEmpty()) {//zoptymalizowac i polaczyc warunki by nie robic 2razy island?
                    int value = board[x][y].humans.size();
                    rgb = (256-fastFloor(value*256/(2*maxPeoplePerTile) << 16)) | (256-fastFloor(value*256/(2*maxPeoplePerTile)) << 8) | fastFloor(256-value*256/(2*maxPeoplePerTile));

                }
                else if (board[x][y].isLand)//w zaleznosci od ilosci ludzi(póżniej zwierząt) i czy zarazeni inne kolory
                    rgb = (0 << 16) | (255 << 8) | 0; // Zielony
                else
                    rgb = (0 << 16) | (0 << 8) | 255; // Niebieski


                image.setRGB(x, y, rgb);
            }
        }
        groupIslands();
        System.out.println(islands.size());
    }



    private int[][] generateGradientValues(int width, int height) {
        int[][] values = new int[width][height];
        Point2D center = new Point2D.Float(fastFloor(width / 2), fastFloor(height / 2));
        float radius = fastFloor(Math.max(width, height) / 1.25);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double dx = x - center.getX();
                double dy = y - center.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double normalizedDistance = Math.min(distance / radius, 1.0);
                int gray = (int) (255 * normalizedDistance);
                values[x][y] = gray;
            }
        }

        return values;
    }

    private static int fastFloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Apply scaling
        g2d.scale(SCALE, SCALE);
        g.drawImage(image, 0, 0, this);
    }

    public  void display() {
        JFrame frame = new JFrame("Combined Visualizer");
        frame.add(this);
        frame.setSize((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });

    }
    public void refreshVisualization() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int rgb;
                if (board[x][y].isLand && !board[x][y].humans.isEmpty()) {//zoptymalizowac i polaczyc warunki by nie robic 2razy island?
                    int value = board[x][y].humans.size();
                    rgb = (256-fastFloor(value*256/(2*maxPeoplePerTile) << 16)) | (256-fastFloor(value*256/(2*maxPeoplePerTile)) << 8) | fastFloor(256-value*256/(2*maxPeoplePerTile));

                } else if (board[x][y].isLand) {
                    rgb = (0 << 16) | (255 << 8) | 0; // Green
                } else {
                    rgb = (0 << 16) | (0 << 8) | 255; // Blue
                }

                image.setRGB(x, y, rgb);
            }
        }
        repaint();
    }



    private void groupIslands(){
        ArrayList<ArrayList<Tile>> lands = findIslands();

        // Pass each island to the island method
        for (ArrayList<Tile> islandTiles : lands) {
            Island island = new Island(islandTiles);
            islands.add(island);
        }
    }



        // Method to find and group neighboring tiles
        public ArrayList<ArrayList<Tile>> findIslands() {
            ArrayList<ArrayList<Tile>> lands = new ArrayList<>();

            boolean[][] visited = new boolean[WIDTH][HEIGHT];

            // Iterate through each tile on the board
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (board[i][j].isLand && !visited[i][j]) {
                        ArrayList<Tile> islandTiles = new ArrayList<>();
                        dfs(board, visited, i, j, islandTiles);
                        lands.add(islandTiles);
                    }

                }
            }
            return lands;
        }

        // Depth-First Search to find all connected land tiles
        private void dfs(Tile[][] board, boolean[][] visited, int row, int col, ArrayList<Tile> islandTiles) {
            int[] rowDirection = {-1, 1, 0, 0}; // Up, Down, Left, Right
            int[] colDirection = {0, 0, -1, 1}; // Up, Down, Left, Right

            Stack<int[]> stack = new Stack<>();
            stack.push(new int[]{row, col});

            while (!stack.isEmpty()) {
                int[] current = stack.pop();
                int currentRow = current[0];
                int currentCol = current[1];

                if (currentRow < 0 || currentRow >= board.length || currentCol < 0 || currentCol >= board[0].length || visited[currentRow][currentCol] || !board[currentRow][currentCol].isLand) {
                    continue;
                }

                visited[currentRow][currentCol] = true;
                islandTiles.add(board[currentRow][currentCol]);

                for (int k = 0; k < 4; k++) {
                    int newRow = currentRow + rowDirection[k];
                    int newCol = currentCol + colDirection[k];
                    stack.push(new int[]{newRow, newCol});
                }
            }
        }



    public void movePopulation() {
        for (Human human : population) {
            board[human.getPosX()][human.getPosY()].humans.remove(human);
            human.Move();
            board[human.getPosX()][human.getPosY()].humans.add(human);
        }
    }

    // Simplex noise functions (as defined in previous messages)
    private static double noise(double xin, double yin) {
        double s = (xin + yin) * 0.5 * (Math.sqrt(3.0) - 1.0);
        int i = fastFloor(xin + s);
        int j = fastFloor(yin + s);

        double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
        double t = (i + j) * G2;
        double X0 = i - t;
        double Y0 = j - t;
        double x0 = xin - X0;
        double y0 = yin - Y0;

        int i1, j1;
        if (x0 > y0) {
            i1 = 1;
            j1 = 0;
        } else {
            i1 = 0;
            j1 = 1;
        }

        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = y0 - 1.0 + 2.0 * G2;

        int ii = i & 255;
        int jj = j & 255;
        int gi0 = PERM_MOD12[ii + PERM[jj]];
        int gi1 = PERM_MOD12[ii + i1 + PERM[jj + j1]];
        int gi2 = PERM_MOD12[ii + 1 + PERM[jj + 1]];

        double t0 = 0.5 - x0 * x0 - y0 * y0;
        double n0;
        if (t0 < 0) {
            n0 = 0.0;
        } else {
            t0 *= t0;
            n0 = t0 * t0 * dot(GRAD3[gi0], x0, y0);
        }

        double t1 = 0.5 - x1 * x1 - y1 * y1;
        double n1;
        if (t1 < 0) {
            n1 = 0.0;
        } else {
            t1 *= t1;
            n1 = t1 * t1 * dot(GRAD3[gi1], x1, y1);
        }

        double t2 = 0.5 - x2 * x2 - y2 * y2;
        double n2;
        if (t2 < 0) {
            n2 = 0.0;
        } else {
            t2 *= t2;
            n2 = t2 * t2 * dot(GRAD3[gi2], x2, y2);
        }

        return 70.0 * (n0 + n1 + n2);
    }

    private static final int[][] GRAD3 = {
            {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0},
            {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1},
            {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
    };

    private static final int[] p = new int[256];
    private static final int[] PERM = new int[512];
    private static final int[] PERM_MOD12 = new int[512];

    static {
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }
        for (int i = 0; i < 256; i++) {
            int j = (int) (Math.random() * 256);
            int temp = p[i];
            p[i] = p[j];
            p[j] = temp;
        }
        for (int i = 0; i < 512; i++) {
            PERM[i] = p[i & 255];
            PERM_MOD12[i] = PERM[i] % 12;
        }
    }

    private static double dot(int[] g, double x, double y) {
        return g[0] * x + g[1] * y;
    }
}
