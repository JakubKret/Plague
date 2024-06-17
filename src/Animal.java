import java.util.Random;

abstract class Animal {

    ////[    VARIABLES    ]\\\\

    int posX;
    int posY;
    Tile[][] board;
    int height;
    boolean isInfected = false;
    boolean canInfect = false;

    ////[    CONSTRUCTOR    ]\\\\

    /**
     *
     * @param x Objects' position X
     * @param y Object's position Y
     * @param board Map
     * @param height Map size
     */
    public Animal(int x, int y, Tile[][] board, int height) {
        this.posX = x;
        this.posY = y;
        this.board = board;
        this.height = height;
    }

    ////[    METHODS    ]\\\\
    /**
     * Method used to randomly move Animal object to tile around it.
     */
    public abstract void animalMove();
    ////[    SETTERS    ]\\\\
    public void setIsInfected(){this.isInfected = true;}
    ////[    GETTERS    ]\\\\
    public boolean getCanInfect(){return this.canInfect;}
    public boolean getIsInfected() {
        return isInfected;
    }
    public int getPosX(){ return posX; }
    public int getPosY(){ return posY; }


}
class Rat extends Animal{

    ////[    CONSTRUCTOR    ]\\\\

    public Rat(int x, int y, Tile[][] board,int height) {
        super(x, y, board,height);
    }

    ////[    METHODS    ]\\\\
    /**
     * Method used to randomly move Animal object to tile around it (excluding water tiles).
     */
    @Override
    public void animalMove(){
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0 && board[posX-1][posY].isLand)    posX--;
        else if(x==1 && posX!=height-1 && board[posX+1][posY].isLand)   posX++;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0 && board[posX][posY-1].isLand)    posY--;
        else if(y==1 && posY!=height-1 && board[posX][posY+1].isLand)  posY++;
    }
}
class Bat extends Animal{

    ////[    CONSTRUCTOR    ]\\\\

    public Bat(int x, int y, Tile[][] board,int height) {
        super(x, y, board,height);
    }

    ////[    METHODS    ]\\\\
    /**
     * Method used to randomly move Animal object to tile around it (including water tiles).
     */
    @Override
    public void animalMove() {
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0)    posX--;
        else if(x==1 && posX!=height-1)   posX++;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0)    posY--;
        else if(y==1 && posY!=height-1)  posY++;
    }


}