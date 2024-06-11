import java.util.Random;

public class Cure {
    CombinedVisualizer visualizer;
    Virus virus;

    public Cure(CombinedVisualizer visualizer,  Virus virus) {
        this.visualizer = visualizer;
        this.virus = virus;
    }

    private int delay = 50, count = 0, speed = 6, iter = 1;

    private double healRate = 0.15;

    public double getHealRate(){return healRate;}
     void cureStart() {

        if(count!=0 && count == delay){
        while(true) {
            Random rand =  new Random();
            int x = rand.nextInt(CombinedVisualizer.WIDTH);
            int y = rand.nextInt(CombinedVisualizer.HEIGHT);
            if (visualizer.getBoard()[x][y].isLand && !visualizer.getBoard()[x][y].getPeople().isEmpty()) {
                for (int i = 0; i < visualizer.getBoard()[x][y].getPeople().size(); i++) {
                    if (visualizer.getBoard()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoard()[x][y].getPeople().get(i).getIsDead()) {
                        for (int z = 0; z < visualizer.getBoard()[x][y].getPeople().size(); z++) {
                            visualizer.getBoard()[x][y].getPeople().get(z).cureEffect(healRate);
                            if (virus.contagiousness + rand.nextDouble() < visualizer.getBoard()[x][y].getPeople().get(z).getImmunity() + healRate)  {
                                visualizer.getBoard()[x][y].getPeople().get(z).setCure();
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
    void cureSpread() {

        if(count > delay) {
            if (count % speed == 0) {
                for (int k = 0; k < iter; k++) {
                    while (true) {
                        Random rand = new Random();
                        int x = rand.nextInt(CombinedVisualizer.WIDTH);
                        int y = rand.nextInt(CombinedVisualizer.HEIGHT);
                        if (visualizer.getBoard()[x][y].isLand && !visualizer.getBoard()[x][y].getPeople().isEmpty()) {
                            for (int i = 0; i < visualizer.getBoard()[x][y].getPeople().size(); i++) {
                                if (visualizer.getBoard()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoard()[x][y].getPeople().get(i).getIsDead()) {
                                    for (int z = 0; z < visualizer.getBoard()[x][y].getPeople().size(); z++) {
                                        visualizer.getBoard()[x][y].getPeople().get(z).cureEffect(healRate);
                                        if (virus.contagiousness + rand.nextDouble() < visualizer.getBoard()[x][y].getPeople().get(z).getImmunity() + healRate) {
                                            visualizer.getBoard()[x][y].getPeople().get(z).setCure();
                                            System.out.println("Healed!");
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (iter < (CombinedVisualizer.WIDTH*CombinedVisualizer.HEIGHT)/150) iter++;
            }
        }
    }

    }
