import java.util.Random;

public class Cure {

    ////[    VARIABLES    ]\\\\

    private int delay = 50, count = 0, speed = 2, iter = 1000;
    private double healRate = 0.45;
    private Board visualizer;
    private Virus virus;
    private int height;
    ////[    CONSTRUCTOR    ]\\\\

    /**
     *
     * @param visualizer Map
     * @param virus Virus object
     * @param healRate Variable affecting effectiveness of Cure
     * @param delay Variable defining number of cycles before Cure starts to spread
     * @param height Map size
     */
    public Cure(Board visualizer, Virus virus, double healRate, int delay,int height) {
        this.visualizer = visualizer;
        this.virus = virus;
        this.healRate = healRate;
        this.delay = delay;
        this.height = height;
    }

    ////[    METHODS    ]\\\\

    /**
     * Method that starts spreading Cure after a number(delay) of cycles.
     */
    void cureStart() {

        if(count!=0 && count == delay){
            while(true) {
                Random rand =  new Random();
                int x = rand.nextInt(height);
                int y = rand.nextInt(height);
                if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                    for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                        if (visualizer.getBoardTable()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                            for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                visualizer.getBoardTable()[x][y].getPeople().get(z).cureEffect(healRate);
                                if (virus.contagiousness + rand.nextDouble() < visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + healRate)  {
                                    visualizer.getBoardTable()[x][y].getPeople().get(z).setCure();
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        count++;
    }

    /**
     * Method that spawns cure at random Tiles and uses it's effect on Human object and calculates if Human object gets cured from Virus.
     */
    void cureSpread() {

        if(count > delay) {
            if (count % speed == 0) {
                for (int k = 0; k < iter; k++) {
                    while (true) {
                        Random rand = new Random();
                        int x = rand.nextInt(height);
                        int y = rand.nextInt(height);
                        if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                            for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                                if (visualizer.getBoardTable()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                    for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                        visualizer.getBoardTable()[x][y].getPeople().get(z).cureEffect(healRate);
                                        if (virus.contagiousness + rand.nextDouble() < visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + healRate) {
                                            visualizer.getBoardTable()[x][y].getPeople().get(z).setCure();
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (iter < (height* height)/150) iter++;
            }
        }
    }

    ////[    GETTERS    ]\\\\

    public double getHealRate(){return healRate;}

}