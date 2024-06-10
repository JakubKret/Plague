package org.example;
import java.util.Random;

public class Virus {
    int contagiousness = 1, resistance, deathRate, fieldOfView;
    Random rand = new Random();

    void PatientZero() {
        int zero = rand.nextInt(CombinedVisualizer.population.size());
        CombinedVisualizer.population.get(zero).getSick();
    }

    void VirusSpread() {
        int x = 0, y = 0;
        for (Tile[] board : CombinedVisualizer.Board) {
            boolean infection = false;
            for (int i = 0; i <= CombinedVisualizer.getPeople(Human.getPosX(), Human.getPosY()).size(); i++) {
                if(!infection){
                    for(int k = -1; k<=1; k=k+2){
                        if (CombinedVisualizer.getPeople(Human.getPosX(), Human.getPosY()).get(i).canInfect || (CombinedVisualizer.getPeople(Human.getPosX() + k, Human.getPosY()).get(i).canInfect || (CombinedVisualizer.getPeople(Human.getPosX(), Human.getPosY() + k).get(i).canInfect || CombinedVisualizer.getPeople(Human.getPosX() + k, Human.getPosY() + k).get(i).canInfect))) {
                            infection = true;
                            break;
                        }}}
                if (infection) {
                    for (int z = 0; z <= CombinedVisualizer.getPeople(Human.getPosX(), Human.getPosY()).size(); z++) {

                        if(contagiousness + rand.nextDouble() > Human.getImmunity()) {
                            CombinedVisualizer.getPeople(Human.getPosX(), Human.getPosY()).get(z).isInfected = true;
                        }
                    }
                }
                infection = false;
            }
        }
        for (Human human : CombinedVisualizer.population) {
            if (human.isInfected) {
                human.startToInfect();
            }
        }
    }
}
