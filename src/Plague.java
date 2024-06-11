public class Plague {
    public static void main(String[] args) throws InterruptedException {
        Board combinedVisualizer = new Board();
        Virus virus = new Virus(combinedVisualizer);
        Cure cure = new Cure(combinedVisualizer, virus);

        virus.patientZero();
        while (1>0){
            Thread.sleep(1);
            virus.virusSpread();
            virus.virusAction();
            combinedVisualizer.movePopulation();
            combinedVisualizer.movePlanes();
            cure.cureStart();
            cure.cureSpread();
            combinedVisualizer.refreshVisualization();
        }
    }
}