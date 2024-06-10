package org.example;
import org.example.*;
import java.util.Random;
public class Human {
    static Random rand = new Random();
    private int health = 40;
    private static double immunity= rand.nextDouble();
    public static int posX;
    public static int posY;
    public boolean isInfected = false;
    public boolean canInfect = false;

    public Human(int X, int Y){
        this.posX = X;
        this.posY = Y;
    }
    public void Move(){

        int x = rand.nextInt(3);
        x=x-1;
        if(x==-1 && CombinedVisualizer.Board[posX-1][posY].isLand)--posX;
        else if(x==1 && CombinedVisualizer.Board[posX+1][posY].isLand)++posX;
        int y = rand.nextInt(3);
        y=y-1;
        if(y==-1 && CombinedVisualizer.Board[posX][posY-1].isLand)--posY;
        else if(y==1 && CombinedVisualizer.Board[posX][posY+1].isLand)++posY;
    }
    public int getHealth() {
        return health;
    }
    public static int getPosX(){
        return posX;
    }
    public static int getPosY(){
        return posY;
    }
    public void getSick(){
        this.isInfected = true;
    }
    public void startToInfect(){
        this.canInfect = true;
    }
    public static double getImmunity(){
        return immunity;
    }
}
