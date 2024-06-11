import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Board extends JPanel {//initialize random in beginning

    ////[    VARIABLES    ]\\\\

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private BufferedImage image;
    private int maxPeoplePerTile=5;
    private double density=0.1;
    private static final double SCALE = 2.5;//1.3;
    private Tile[][] boardTable = new Tile[WIDTH][HEIGHT];;
    private ArrayList<Human> population = new ArrayList<Human>();
    private ArrayList<Plane> planePopulation = new ArrayList<Plane>();
    private ArrayList<Animal> animalPopulation = new ArrayList<Animal>();
    private double animalDensity = 0.1;
    private int maxAnimalsPerTile=3;
    private ArrayList<Island> islands = new ArrayList<Island>();

    ////[    CONSTRUCTOR    ]\\\\

    public Board() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        generateNoiseImage();//GENERATES NOISE, SUBTRACTS GRADIENT NOISE, GENERATES OBJECTS, APPLIES COLORS
        display();
    }

    ////[    METHODS    ]\\\\

    private void generateNoiseImage() {
        int[][] gradientValues = generateGradientValues(WIDTH, HEIGHT);//CIRCULAR GRADIENT
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double nx = x / (double) WIDTH;
                double ny = y / (double) HEIGHT;
                double noiseValue = noise(nx * 10, ny * 10); //NOISE SCALE
                int noiseGray = (int) ((noiseValue + 1) * 127.5); //SCALING TO 0-255 RANGE
                int gradientGray = gradientValues[x][y];
                int gray = fastFloor((noiseGray + fastFloor(1.75*gradientGray)) / 2.75); //CONNECTING BOTH GRADIENTS TO GET RID OF LAND NEAR BORDER
                int rgb;
                boardTable[x][y] = new Tile(gray, x, y);
                if(boardTable[x][y].isLand) {//GENERATES OBJECTS ONLY ON LAND
                    Random random = new Random();
                    if(random.nextDouble() < density){//CHECKS IF IT SHOULD SPAWN PEOPLE IN THE TILE
                        int number = (int) (Math.random() * (maxPeoplePerTile+1));//HOW MANY PEOPLE ON THE TILE
                        for (int z = 0; z < number; z++) {//SPAWN HUMANS
                            Human human = new Human(x,y,boardTable);
                            boardTable[x][y].humans.add(human);
                            population.add(human);
                        }
                    }
                    random = new Random();
                    if(random.nextDouble() < animalDensity){
                        int number = (int) (Math.random() * (maxAnimalsPerTile+1));
                        for (int z = 0; z < number; z++) {
                            int r = random.nextInt(2);
                            if(r==0) {
                                Animal animal = new Rat(x, y, boardTable);
                                boardTable[x][y].animals.add(animal);
                                animalPopulation.add(animal);
                            }
                            if(r==1) {
                                Animal animal = new Bat(x, y, boardTable);
                                boardTable[x][y].animals.add(animal);
                                animalPopulation.add(animal);
                            }
                        }
                    }
                }
                if (boardTable[x][y].isLand && !boardTable[x][y].humans.isEmpty()) {//zoptymalizowac i polaczyc warunki by nie robic 2razy island?
                    int value = boardTable[x][y].humans.size();
                    rgb = (256-fastFloor(value*256/(2*maxPeoplePerTile) << 16)) | (256-fastFloor(value*256/(2*maxPeoplePerTile)) << 8) | fastFloor(256-value*256/(2*maxPeoplePerTile));//SHADES OF GREY DEPENDING ON AMOUNT OF PEOPLE
                }
                else if (boardTable[x][y].isLand)
                    rgb = (0 << 16) | (255 << 8) | 0; //GREEN
                else
                    rgb = (0 << 16) | (0 << 8) | 255; //BLUE
                image.setRGB(x, y, rgb);
            }
        }
        groupIslands();
        generateAirports();
        createPlanes();
    }

    private int[][] generateGradientValues(int width, int height) {//GENERATES CIRCULAR GRADIENT
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

    @Override//RESPONSIBLE FOR PAINTING
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(SCALE, SCALE);//APPLIES SCALE
        g.drawImage(image, 0, 0, this);
    }

    public  void display() {//CREATES VISUALIZATION
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

    public void refreshVisualization() {//UPDATES THE VISUALIZATION
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int rgb;
                //cure
                if (boardTable[x][y].planes.size() > 0)//IF THERE IS A PLANE
                {
                    rgb = 255 << 16 | 255 << 8 | 0;//YELLOW
                }
                else if (boardTable[x][y].isLand && !boardTable[x][y].humans.isEmpty()) {//zoptymalizowac i polaczyc warunki by nie robic 2razy island?
                    int infected=0,value=0,dead=0;
                    for (Human h : boardTable[x][y].humans) {//COUNTS HUMANS OF DIFFERENT STATES
                        if (h.getIsDead()) {dead++;}
                        else if (h.getIsInfected()) {infected++;}
                        else {value++;}
                    }
                    if (dead>0) {//IF THERE ARE ANY DEAD
                        rgb = 0<<16 | 0<<8 | 0;//BLACK
                    }
                    else if (infected==0) {//IF NO INFECTED SETS APPROPRIATE SHADE OF WHITE->GREY
                        rgb = (256 - fastFloor(value * 256 / (2 * maxPeoplePerTile) << 16)) | (256 - fastFloor(value * 256 / (2 * maxPeoplePerTile)) << 8) | fastFloor(256 - value * 256 / (2 * maxPeoplePerTile));
                    }
                    else {
                        rgb = ((int) (Math.min((double)infected/value,1.0)*255) << 16) | (0 << 8) | 0;//DEPENDING ON INFECTED/HEALTHY RATIO SETS APPROPRIATE SHADE OF RED
                    }}
                    else if (boardTable[x][y].isLand && !boardTable[x][y].animals.isEmpty()) {
                        int rats = 0;
                        int bats = 0;
                        for (Animal a : boardTable[x][y].animals) {//COUNTS RATS AND BATS ON THE TILE
                            if (a.getClass().getSimpleName().equals("Rat")) {rats++;}
                            else if (a.getClass().getSimpleName().equals("Bat")) {bats++;}
                        }
                        if (bats>=rats) {rgb= 165 << 16 | 42 << 8 | 42;}//WHEN MORE BATS => BROWN
                        else {rgb = 128 << 16 | 0 << 8 | 128;}//PURPLE
                    }
                 else if (boardTable[x][y].isLand) {//FOR LAND
                    rgb = (0 << 16) | (255 << 8) | 0; //GREEN
                } else {//FOR WATER
                    rgb = (0 << 16) | (0 << 8) | 255; //BLUE
                }
                image.setRGB(x, y, rgb);//SETS THE COLOR IN [X,Y] ON IMAGE
            }
        }
        repaint();
    }

    private void groupIslands(){//CREATES TYPE ISLAND OBJECTS FOR EACH LAND GROUP
        ArrayList<ArrayList<Tile>> lands = findIslands();
        for (ArrayList<Tile> islandTiles : lands) {
            Island island = new Island(islandTiles);
            islands.add(island);
        }
    }

    private void createPlanes(){//FOR EACH AIRPORT/ISLAND CREATE A PLANE
        for (Island island : islands)
        {
            Plane plane = new Plane(island.getAirport().posX,island.getAirport().posY,islands,boardTable);
            planePopulation.add(plane);
        }
    }

    // FINDING AND GROUPING NEIGHBORING LANDS
    public ArrayList<ArrayList<Tile>> findIslands() {
        ArrayList<ArrayList<Tile>> lands = new ArrayList<>();

        boolean[][] visited = new boolean[WIDTH][HEIGHT];

        //ITERATING THROUGH EACH TILE ON THE BOARD
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (boardTable[i][j].isLand && !visited[i][j]) {
                    ArrayList<Tile> islandTiles = new ArrayList<>();
                    dfs(boardTable, visited, i, j, islandTiles);
                    lands.add(islandTiles);
                }
            }
        }
        return lands;
    }

    private void dfs(Tile[][] boardTable, boolean[][] visited, int row, int col, ArrayList<Tile> islandTiles) {//FINDING ALL CONNECTED LANDS
        int[] rowDirection = {-1, 1, 0, 0}; // Up, Down, Left, Right
        int[] colDirection = {0, 0, -1, 1}; // Up, Down, Left, Right

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int currentRow = current[0];
            int currentCol = current[1];

            if (currentRow < 0 || currentRow >= boardTable.length || currentCol < 0 || currentCol >= boardTable[0].length || visited[currentRow][currentCol] || !boardTable[currentRow][currentCol].isLand) {
                continue;
            }

            visited[currentRow][currentCol] = true;
            islandTiles.add(boardTable[currentRow][currentCol]);

            for (int k = 0; k < 4; k++) {
                int newRow = currentRow + rowDirection[k];
                int newCol = currentCol + colDirection[k];
                stack.push(new int[]{newRow, newCol});
            }
        }
    }

    private void generateAirports(){
        Random random = new Random();
        for (Island island : islands) {
            for (int k=0; k<(1/*+fastFloor(island.getIslandLand().size())/1500*/);k++) {
                int r = random.nextInt(island.getIslandLand().size());
                island.setAirport(island.getIslandLand().get(r));
                island.getIslandLand().get(r).setAirport();
            }
        }
    }


    public void movePopulation() {
        for (Human human : population) {
            boardTable[human.getPosX()][human.getPosY()].humans.remove(human);
            human.Move();
            boardTable[human.getPosX()][human.getPosY()].humans.add(human);
        }
    }

    public void movePlanes(){
        for (Plane plane : planePopulation) {
            boardTable[plane.getPosX()][plane.getPosY()].planes.remove(plane);
            plane.Move();
            boardTable[plane.getPosX()][plane.getPosY()].planes.add(plane);
        }
    }

    //SIMPLEX NOISE
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



    ////[    GETTERS    ]\\\\

    public Tile[][] getBoardTable() {return boardTable;}
    public ArrayList<Human> getPopulation() {
        return population;
    }

    ////[    SUPPORT METHODS    ]\\\\

    private static double dot(int[] g, double x, double y) {return g[0] * x + g[1] * y;}//ILOCZYN SKALARNY
    private static int fastFloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }//ZAOKRĄGLENIE W DÓŁ

    ////[    NOISE STATICS    ]\\\\

    private static final int[][] GRAD3 = { //ALL 3D GRADIENTS POSSIBILITIES
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

}
