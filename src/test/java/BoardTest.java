import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setup() {
        int speed = 40;
        double contagiousness = 0.3;
        double deathRate = 0.15;
        double healRate = 0.15;
        int delay = 200;
        double density = 0.1;
        double scale = 2.5;
        int maxPeople = 5;
        int maxAnimals = 1;
        int iter = 10;

        board = new Board(speed, contagiousness, deathRate, healRate, delay,
                density, scale, maxPeople, maxAnimals, iter);
    }

    @Test
    public void testBoardInitialization() {
        assertNotNull(board.getBoardTable(), "Board has not been initialized" );
    }
    @Test
    public void testPopulation() {
        assertFalse(board.getPopulation().isEmpty(), "People have not got spawned");
    }
    @Test
    public void testAnimalPopulation() {
        assertFalse(board.getAnimalPopulation().isEmpty(), "Animals have not got spawned");
    }
    @Test
    public void testIslandCreating() {
        assertFalse(board.getIslands().isEmpty(), "Islands have not been created");
    }
    @Test
    public void testPlaneCreating() {
        assertFalse(board.getPlanePopulation().isEmpty(), "Planes have not been created");
    }

    @Test
    public void testPeopleNotSpawningOnWater(){
        boolean isOnWater = false;
        for (Human human :board.getPopulation()){
            if(!board.getBoardTable()[human.getPosX()][human.getPosY()].isLand && !human.getIsOnPlane())
            {
                isOnWater = true;
                break;
            }
        }
        assertFalse(isOnWater, "People have been spawned on water");
    }

    @Test
    public void testPatientZero(){
        Virus virus = new Virus(board, board.contagiousness,board.deathRate);
        virus.patientZero();
        boolean humanInfected=false;
        for (Human human :board.getPopulation()){
            if(human.getIsInfected())
            {
                humanInfected = true;
                break;
            }
        }
        assertTrue(humanInfected,"There is no patient Zero");
    }
    @Test
    public void testVirusAction(){
        Virus virus = new Virus(board, 1,board.deathRate);
        virus.patientZero();
        virus.virusSpread();
        Human testHuman = new Human(0,0, board.getBoardTable(), 400);
        double initialHealth=0;
        for (Human human :board.getPopulation()){
            if (human.getIsInfected()) {
                initialHealth = human.getHealth();
                testHuman = human;
            }
        }
        virus.virusAction();
        assertTrue(testHuman.getHealth()!=initialHealth, "Infected person did not get damage");
    }
    @Test
    public void testVirusSpread(){
        Virus virus = new Virus(board, board.contagiousness,board.deathRate);
        virus.patientZero();
        for (int k=0;k<6;k++)
        {
            virus.virusSpread();
        }
        int infectedCount=0;
        for (Human human :board.getPopulation()){
            if(human.getIsInfected())
            {
                infectedCount++;

            }
        }
        assertTrue(infectedCount>1,"Virus has not spread");
    }

    @Test
    public void testMovePopulation() {
        for (Human human : board.getPopulation()) {
            int initialX = human.getPosX();
            int initialY = human.getPosY();


            boolean nextToWater=false;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx != 0 || dy != 0) {
                        int x = initialX + dx;
                        int y = initialY + dy;
                        if (!board.getBoardTable()[x][y].isLand && !human.getIsOnPlane()) {
                            nextToWater=true;
                            break;
                        }
                    }
                }
                if (nextToWater) {break;}
            }


            int currentX = human.getPosX();
            int currentY = human.getPosY();
            boolean moved = false;
            for(int k=0;k<10;k++)
            {
                human.Move();
                if (!(currentX == human.getPosX() && currentY == human.getPosY())) {moved=true; break;}
            }




            if (!nextToWater) {
                assertTrue(moved, "Human has not moved");

            }
        }
    }
    @Test
    public void testBatMove() {
        boolean moved = false;
        for (Animal animal : board.getAnimalPopulation()){
            if(animal.getClass().getSimpleName().equals("Bat"))
            {
                int initX = animal.getPosX();
                int initY = animal.getPosY();
                for(int k=0;k<10;k++)
                {
                    animal.animalMove();
                    if (!(initX == animal.getPosX() || initY == animal.getPosY())) {moved=true; break;}
                }
                break;
            }


        }
        assertTrue(moved,"Bat has not moved");
    }
}
