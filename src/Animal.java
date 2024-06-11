import java.util.Random;

abstract class Animal {

    int posX;
    int posY;
    Tile[][] board;
    public Animal(int x, int y, Tile[][] board) {
        this.posX = x;
        this.posY = y;
        this.board = board;
    }

    public abstract void animalMove();
}
class Rat extends Animal{


    public Rat(int x, int y, Tile[][] board) {
        super(x, y, board);
    }
    public void animalMove(){
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0 && board[posX-1][posY].isLand)    posX--;
        else if(x==1 && posX!=Board.WIDTH-1 && board[posX+1][posY].isLand)   posX++;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0 && board[posX][posY-1].isLand)    posY--;
        else if(y==1 && posY!=Board.HEIGHT-1 && board[posX][posY+1].isLand)  posY++;
    }
}
class Bat extends Animal{
    public Bat(int x, int y, Tile[][] board) {
        super(x, y, board);
    }
    public void animalMove() {
        Random rand = new Random();
        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && posX!=0)    posX--;
        else if(x==1 && posX!=Board.WIDTH-1)   posX++;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && posY!=0)    posY--;
        else if(y==1 && posY!=Board.HEIGHT-1)  posY++;
    }
}