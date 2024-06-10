//
//import java.util.Random;
//
//public class Virus {
//    int contagiousness = 1, resistance, deathRate, fieldOfView;
//    Random rand = new Random();
//    CombinedVisualizer visualizer = new CombinedVisualizer();
//    public Virus(CombinedVisualizer visualizer) {
//        this.visualizer = visualizer;
//    }
//    void PatientZero() {
//        int zero = rand.nextInt(visualizer.getPopulation().size());
//        visualizer.getPopulation().get(zero).giveSick();
//        visualizer.getPopulation().get(zero).canInfect = true;
//    }
//
//    void virusSpread() {
//        for (int x = 0; x < visualizer.WIDTH; x++) {
//            for (int y = 0; y < visualizer.HEIGHT; y++) {
//                if (visualizer.getBoard()[x][y].isLand){
//                    boolean infection = false;
//                    for (int i = 0; i <= visualizer.getBoard()[x][y].getPeople().size(); i++) {
//                        if (!infection) {
//                            for (int k = -1; k <= 1; k = k + 2) {
//                                if (visualizer.getBoard()[x][y].getPeople().get(i).canInfect || (visualizer.getBoard()[x + k][y].getPeople().get(i).canInfect || visualizer.getBoard()[x][y + k].getPeople().get(i).canInfect)) {
//                                    infection = true;
//                                    break;
//                                }
//                            }
//                        }
//                        if (infection) {
//                            for (int z = 0; z <= visualizer.getBoard()[x][y].getPeople().size(); z++) {
//
//                                if (contagiousness + rand.nextDouble() > Human.getImmunity()) {
//                                    visualizer.getBoard()[x][y].getPeople().get(z).giveSick();
//                                }
//                            }
//                        }
//                        infection = false;
//                    }
//                }
//            }
//        }
//        for (Human human : visualizer.getPopulation()) {
//            if (human.isInfected) {
//                human.setCanInfect();
//            }
//        }
//    }
//}