import java.util.Random;

public class Cure {
    Board visualizer;
    Virus virus;

    public Cure(Board visualizer, Virus virus) {
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
                int x = rand.nextInt(Board.WIDTH);
                int y = rand.nextInt(Board.HEIGHT);
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
    void cureSpread() {

        if(count > delay) {
            if (count % speed == 0) {
                for (int k = 0; k < iter; k++) {
                    while (true) {
                        Random rand = new Random();
                        int x = rand.nextInt(Board.WIDTH);
                        int y = rand.nextInt(Board.HEIGHT);
                        if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                            for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                                if (visualizer.getBoardTable()[x][y].getPeople().get(i).getIsInfected() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                    for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                        visualizer.getBoardTable()[x][y].getPeople().get(z).cureEffect(healRate);
                                        if (virus.contagiousness + rand.nextDouble() < visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + healRate) {
                                            visualizer.getBoardTable()[x][y].getPeople().get(z).setCure();
                                            System.out.println("Healed!");
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (iter < (Board.WIDTH* Board.HEIGHT)/150) iter++;
            }
        }
    }

}