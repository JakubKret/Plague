import java.util.*;

public class Plague {

    public static void main(String[] args) throws InterruptedException {
        CombinedVisualizer combinedVisualizer = new CombinedVisualizer();
        Virus virus = new Virus(combinedVisualizer);
        //combinedVisualizer.display();

        virus.patientZero();
        while (1>0){
            Thread.sleep(1);
            //System.out.println(combinedVisualizer.getPopulation().size());
            //System.out.println(combinedVisualizer.getPopulation().get(0).getPosX()+" "+combinedVisualizer.getPopulation().get(0).getPosY());
            virus.virusSpread();
            combinedVisualizer.movePopulation();
            //System.out.println(combinedVisualizer.getPopulation().get(0).getPosX()+" "+combinedVisualizer.getPopulation().get(0).getPosY());
            virus.virusAction();
            combinedVisualizer.refreshVisualization();
            //combinedVisualizer.display();

        }
    }
}
