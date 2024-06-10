import java.util.ArrayList;

public class Island {
    private ArrayList<Tile> islandLand;
    private Tile Airport;
    public Island(ArrayList<Tile> islandTiles)
    {
        this.islandLand = islandTiles;
    }
    public ArrayList<Tile> getIslandLand(){
        return this.islandLand;
    }
    public void setAirport(Tile tile){
        Airport = tile;
    }
}
