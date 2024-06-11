import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plane {

    ////[    VARIABLES    ]\\\\

    private int posX,posY;
    int[] move = new int[2];
    ArrayList<Island> islands;
    private boolean isMoving=false;
    public List<Human> humans = new ArrayList<Human>();
    Tile[][] board;
    int x;
    int y;

    public Plane(int posX, int posY, ArrayList<Island> islands, Tile[][] board){
        this.posX = posX;
        this.posY = posY;
        this.islands = islands;
        this.board = board;
        calculateMove();
    }

    private void calculateMove()
    {
        if(!isMoving && !board[posX][posY].humans.isEmpty()){isMoving=true;}
        if (isMoving){
        Random random = new Random();
        int r = random.nextInt(islands.size());
        while (islands.get(r).getAirport().posX==this.posX&&islands.get(r).getAirport().posY==this.posY){r = random.nextInt(islands.size());}
        x=islands.get(r).getAirport().posX;
        y=islands.get(r).getAirport().posY;

        for (Human human : board[posX][posY].humans) {
            if (!human.getIsDead() && !human.getIsAfterFlight() && !human.getIsOnPlane()) {
                human.setIsOnPlane();
                this.humans.add(human);
                //board[posX][posY].humans.remove(human);
            }
        }
        for (Human human : this.humans)
        {
            board[posX][posY].humans.remove(human);
        }

//        int steps =fastFloor(Math.min(Math.abs(x-posX)/20,Math.abs(y-posY)/20));
//        if (((x-posX)/(steps*2))%2!=0)
//        {posX++;}
//        if (((y-posY)/(steps*2))%2!=0)
//        {posY++;}
        move[0]=(x-posX);//(steps*2);
        move[1]=(y-posY);//(steps*2);
        //System.out.println(steps);
        }
    }

    public void Move(){

        if (!isMoving && !board[posX][posY].humans.isEmpty()){isMoving=true;}
        if (!humans.isEmpty()){isMoving=true;}
        if(isMoving){
        board[posX][posY].planes.remove(this);
        //System.out.println(posX+" "+posY);
        //System.out.println(x + " "+ y);
        //System.out.println(move[0]+" "+move[1]);
        //posX+=move[0];
        //posY+=move[1];
        posX = x;
        posY = y;
        if (posX==x && posY==y){
            for (Human human : humans){
                human.setIsAfterFlight();
                human.setIsOnPlane();

                board[posX][posY].humans.add(human);

            }
            //System.out.println(humans.size());
            isMoving=false;
            humans.clear();

            calculateMove();
        }

        board[posX][posY].planes.add(this);
    }}
    private static int fastFloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    ////[    GETTERS    ]\\\\

    public int getPosX()    {return posX;}
    public int getPosY()    {return posY;}
}
