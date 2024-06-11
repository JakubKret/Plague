import java.util.ArrayList;
import java.util.List;

public class Tile {

    ////[    VARIABLES    ]\\\\

    boolean isAirport=false;
    private ArrayList<Plane> planes =  new ArrayList<>();
    public boolean isLand=false;
    private int landModifier = 100;
    private List<Human> humans = new ArrayList<Human>();
    public List<Animal> animals = new ArrayList<Animal>();
    int posX,posY;

    ////[    CONSTRUCTORS    ]\\\\

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

    ////[    SETTERS    ]\\\\

    public void setAirport(){isAirport = true;}

    ////[    GETTERS    ]\\\\

    public List<Human> getPeople(){
        return humans;
    }
    public List<Animal> getAnimals(){
        return animals;
    }
    public ArrayList<Plane> getPlanes(){
        return planes;
    }
}
