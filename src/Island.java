import java.util.ArrayList;

public class Island {

    ////[    VARIABLES    ]\\\\
    private ArrayList<Tile> islandLand;
    private Tile airport;

    ////[    METHODS    ]\\\\

    public Island(ArrayList<Tile> islandTiles)
    {
        this.islandLand = islandTiles;
    }

    ////[    SETTERS    ]\\\\

    public void setAirport(Tile tile){//Tile tile
        airport =tile;
    }

    ////[    GETTERS    ]\\\\

    public ArrayList<Tile> getIslandLand(){
        return this.islandLand;
    }
    public /*int[]*/Tile getAirport(){
        return airport;
    }
}
