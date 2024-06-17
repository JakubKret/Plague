import java.util.Random;

public class Human {
    ////[    VARIABLES    ]\\\\
    private int posX,posY;
    private double health = 40;
    private double immunity = 0.1;
    private boolean isInfected=false;
    private boolean canInfect = false;
    private boolean isDead = false;
    private boolean isOnPlane = false;
    private boolean isAfterFlight = false;
    private Tile[][] board;
    private int height;
    Random rand = new Random();
    ////[    CONSTRUCTOR    ]\\\\

    /**
     * Human object constructor.
     * @param X Object's X position
     * @param Y Object's Y position
     * @param board Map
     * @param height Map size
     */
    public Human(int X,int Y, Tile[][] board, int height){
        this.posX = X;
        this.posY = Y;
        this.board = board;
        this.immunity = rand.nextDouble();
        this.height = height;
    }

    ////[    METHODS    ]\\\\

    /**
     * Method used to randomly move Human object to tile around it (excluding water tiles).
     */
    public void Move(){
        if(!isDead&&!isOnPlane){
            Random rand = new Random();
            int x = rand.nextInt(3);
            x=x-1;
            if(x==-1 && posX!=0 && board[posX-1][posY].isLand)    posX--;
            else if(x==1 && posX!= height-1 && board[posX+1][posY].isLand)   posX++;
            int y = rand.nextInt(3);
            y=y-1;
            if(y==-1 && posY!=0 && board[posX][posY-1].isLand)    posY--;
            else if(y==1 && posY!= height-1 && board[posX][posY+1].isLand)  posY++;
            isAfterFlight = false;
        }}

    /**
     * Method used to calculate Virus effect on object.
     * @param x - deathRate from Virus class.
     */
    public void virusEffect(double x){
        if(isInfected){
            x = x - (immunity/2);
            if(x <= 0.01){x = 0.01;}
            health = health - x;
        }
        if(health <= 0){
            canInfect = false;
            isDead = true;
        }
    }

    /**
     * Method used to calculate Cure effect on object.
     * @param x - healRate from Cure class.
     */
    public void cureEffect(double x){
        if(isInfected) {
            health = health + x;
        }
    }

    ////[    SETTERS    ]\\\\

    public void giveSick(){this.isInfected = true;}
    public void setCanInfect(){this.canInfect = true;}
    public void setCure(){
        this.isInfected = false;
        this.canInfect=false;
    }
    public void setIsOnPlane()  {isOnPlane = !this.isOnPlane;}
    public void setIsAfterFlight()  {isAfterFlight = true;}

    ////[    GETTERS    ]\\\\

    public double getHealth() {return health;}
    public int getPosX()    {return posX;}
    public int getPosY()    {return posY;}
    public boolean getIsInfected()  {return isInfected;}
    public boolean getCanInfect()   {return canInfect;}
    public double getImmunity() {return immunity;}
    public boolean getIsDead()  { return isDead;}
    public boolean getIsOnPlane()   {return isOnPlane;}
    public boolean getIsAfterFlight()   {return isAfterFlight;}
}