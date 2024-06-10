import java.util.*;

public class Plague {

    public static void main(String[] args) throws InterruptedException {
        CombinedVisualizer combinedVisualizer = new CombinedVisualizer();
        //combinedVisualizer.display();

        while (1>0){
            Thread.sleep(1000);
            //System.out.println(combinedVisualizer.getPopulation().size());
            //System.out.println(combinedVisualizer.getPopulation().get(0).getPosX()+" "+combinedVisualizer.getPopulation().get(0).getPosY());
            combinedVisualizer.movePopulation();
            //System.out.println(combinedVisualizer.getPopulation().get(0).getPosX()+" "+combinedVisualizer.getPopulation().get(0).getPosY());
            combinedVisualizer.refreshVisualization();
            //combinedVisualizer.display();
        }
    }
}
