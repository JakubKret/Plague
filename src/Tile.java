import java.util.ArrayList;
import java.util.List;

public class Tile {
    boolean isAirport=false;
    ArrayList<Plane> planes =  new ArrayList<>();
    boolean isLand=false;
    private int landModifier = 100;
    public List<Human> humans = new ArrayList<Human>();
    public List<Animal> animals = new ArrayList<Animal>();
    int posX,posY;
    public Tile(int rgbVal,int x,int y){
      if (rgbVal <= landModifier)
      {
          isLand = true;
      }
      this.posX=x;
      this.posY=y;
    }
    public Tile(){
        isLand = false;

    }
    public List<Human> getPeople(){
        return humans;
    }
    public void setAirport()
    {
        isAirport = true;
    }
    public ArrayList<Plane> getPlanes(){
        return planes;
    }
}
