//
//
//import java.util.Random;
//public class Human {
//    static Random rand = new Random();
//    private int health = 40;
//    private static double immunity= rand.nextDouble();
//    public static int posX;
//    public static int posY;
//    public boolean isInfected = false;
//    public boolean canInfect = false;
//    private Tile[][] board;
//    public Human(int X, int Y, Tile[][] board){
//        this.posX = X;
//        this.posY = Y;
//        this.board = board;
//    }
//    public void Move(){
//
//        int x = rand.nextInt(3);
//        x=x-1;
//        if(x==-1 && board[posX-1][posY].isLand)--posX;
//        else if(x==1 && board[posX+1][posY].isLand)++posX;
//        int y = rand.nextInt(3);
//        y=y-1;
//        if(y==-1 && board[posX][posY-1].isLand)--posY;
//        else if(y==1 && board[posX][posY+1].isLand)++posY;
//    }
//    public int getHealth() {
//        return health;
//    }
//    public static int getPosX(){
//        return posX;
//    }
//    public static int getPosY(){
//        return posY;
//    }
//    public void getSick(){
//        this.isInfected = true;
//    }
//    public static double getImmunity(){
//        return immunity;
//    }
//}
import java.util.Random;

public class Human {
    private int posX,posY;
    private double health = 40;
    private double immunity= 0.1;
    private boolean isInfected=false;
    private boolean canInfect = false;
    private boolean isDead = false;
    public Human(int X,int Y){
        this.posX = X;
        this.posY = Y;
    }

    public void Move(){
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0)    posX--;
        else if(x==1 && posX!=CombinedVisualizer.WIDTH-1)   posX++;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0)    posY--;
        else if(y==1 && posY!=CombinedVisualizer.HEIGHT-1)  posY++;
    }
    public void giveSick()//
    {
        this.isInfected = true;
    }
    public double getHealth() {
        return health;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public boolean getIsInfected(){
        return isInfected;
    }

    public void startToInfect(){this.canInfect = true;}
    public double getImmunity(){
        return immunity;
    }
    public void virusEffect(double x){
        if(isInfected){
            health = health - x;
        }
        if(health <= 0){
            canInfect =false;
            isDead = true;
        }
    }
}


/*import java.util.Random;
public class Human {
    static Random rand = new Random();
    private double health = 40;
    private static double immunity= rand.nextDouble();
    public static int posX;
    public static int posY;
    public boolean isInfected = false;
    public boolean canInfect = false;
    public boolean isDead = false;
    private Tile[][] board;
    public Human(int X, int Y,Tile[][] board){
        this.posX = X;
        this.posY = Y;
        this.board = board;
    }
//    public void Move(){
//
//        int x = rand.nextInt(3);
//        x=x-1;
//        if(x==-1 && board[posX-1][posY].isLand)--posX;
//        else if(x==1 && board[posX+1][posY].isLand)++posX;
//        int y = rand.nextInt(3);
//        y=y-1;
//        if(y==-1 && board[posX][posY-1].isLand)--posY;
//        else if(y==1 && board[posX][posY+1].isLand)++posY;
//    }
    public void Move(){
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0)--posX;
        else if(x==1 && posX!=CombinedVisualizer.WIDTH-1)++posX;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0)--posY;
        else if(y==1 && posY!=CombinedVisualizer.HEIGHT-1)++posY;
    }
    public double getHealth() {
        return health;
    }
    public static int getPosX(){
        return posX;
    }
    public static int getPosY(){
        return posY;
    }
    public void giveSick(){this.isInfected = true;}
    public void startToInfect(){this.canInfect = true;}
    public static double getImmunity(){
        return immunity;
    }
    public void virusEffect(double x){
        if(isInfected){
            health = health - x;
        }
        if(health <= 0){
            canInfect =false;
            isDead = true;
        }
    }

}*/