
import java.util.Random;

public class Virus {
//    int  resistance, fieldOfView;
    double contagiousness = 0.2, deathRate = 0.05;
    Random rand = new Random();
    Random rand1 = new Random();
    CombinedVisualizer visualizer;
    public Virus(CombinedVisualizer visualizer) {
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
                if (visualizer.getBoard()[x][y].isLand && !visualizer.getBoard()[x][y].getPeople().isEmpty()) {
                    for (int i = 0; i < visualizer.getBoard()[x][y].getPeople().size(); i++) {
                        if (visualizer.getBoard()[x][y].getPeople().get(i).getCanInfect() && !visualizer.getBoard()[x][y].getPeople().get(i).getIsDead()) {
                            for (int z = 0; z < visualizer.getBoard()[x][y].getPeople().size(); z++) {
                                if (contagiousness + rand.nextDouble() > visualizer.getBoard()[x][y].getPeople().get(z).getImmunity() + rand1.nextDouble()) {
                                    visualizer.getBoard()[x][y].getPeople().get(z).giveSick();
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
