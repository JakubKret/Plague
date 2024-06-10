import java.util.ArrayList;
import java.util.List;

public class Tile {
    boolean isLand=false;
    private int landModifier = 100;
    public List<Human> humans = new ArrayList<Human>();
    public Tile(int rgbVal){
      if (rgbVal <= landModifier)
      {
          isLand = true;
      }
    }
    public Tile(){
        isLand = false;
    }
    public List<Human> getPeople(){
        return humans;
    }
}
