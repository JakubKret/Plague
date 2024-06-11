
import java.util.Random;

public class Virus {

    ////[    VARIABLES    ]\\\\

    double contagiousness = 0.2, deathRate = 0.05;
    Random rand = new Random();
    Random rand1 = new Random();
    Board visualizer;
    public Virus(Board visualizer) {
        this.visualizer = visualizer;
    }
    void patientZero() {
        int zero = rand.nextInt(visualizer.getPopulation().size());
        visualizer.getPopulation().get(zero).giveSick();
        visualizer.getPopulation().get(zero).setCanInfect();
    }

    void virusSpread() {
        if(contagiousness!=0){
            for (int x = 0; x < visualizer.WIDTH; x++) {
                for (int y = 0; y < visualizer.HEIGHT; y++) {
                    if (visualizer.getBoardTable()[x][y].isLand && !visualizer.getBoardTable()[x][y].getPeople().isEmpty()) {
                        for (int i = 0; i < visualizer.getBoardTable()[x][y].getPeople().size(); i++) {
                            if (visualizer.getBoardTable()[x][y].getPeople().get(i).getCanInfect() && !visualizer.getBoardTable()[x][y].getPeople().get(i).getIsDead()) {
                                for (int z = 0; z < visualizer.getBoardTable()[x][y].getPeople().size(); z++) {
                                    if (contagiousness + rand.nextDouble() > visualizer.getBoardTable()[x][y].getPeople().get(z).getImmunity() + rand1.nextDouble()) {
                                        visualizer.getBoardTable()[x][y].getPeople().get(z).giveSick();
                                    }
                                }
                            }
                        }
                    }

                }
            }
            for(Human human : visualizer.getPopulation()) {
                if (human.getIsInfected()) {
                    human.setCanInfect();
                }}}
    }

    public void virusAction() {
        for (Human human : visualizer.getPopulation()) {
            if (human.getIsInfected()) {
                human.virusEffect(deathRate);
            }
        }

    }
}