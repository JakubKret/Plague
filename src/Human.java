import java.util.Random;

public class Human {
    private int posX,posY;
    private int health = 40;
    public Human(int X,int Y){
        this.posX = X;
        this.posY = Y;
    }

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
    public int getHealth() {
        return health;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
}
