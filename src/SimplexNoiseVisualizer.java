import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SimplexNoiseVisualizer extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private BufferedImage image;

    public SimplexNoiseVisualizer() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        generateNoiseImage();
    }

    private void generateNoiseImage() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double nx = x / (double) WIDTH;
                double ny = y / (double) HEIGHT;
                double noiseValue = noise(nx * 10, ny * 10); // Skala szumu
                int gray = (int) ((noiseValue + 1) * 127.5); // Przeskalowanie do zakresu 0-255
                int rgb;
                if (gray < 160) {
                    rgb = (0 << 16) | (0 << 8) | 255; // Niebieski
                } else {
                    rgb = (0 << 16) | (255 << 8) | 0; // Zielony
                }

                image.setRGB(x, y, rgb);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simplex Noise Visualizer");
        SimplexNoiseVisualizer panel = new SimplexNoiseVisualizer();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Simplex noise functions (as defined in previous messages)
    private static int fastFloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    private static double dot(int[] g, double x, double y) {
        return g[0] * x + g[1] * y;
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

    public static double noise(double xin, double yin) {
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
}
