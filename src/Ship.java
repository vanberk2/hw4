//
// Author(s): Nathan Seitz
//

//
// This class is extended by the all of the ship types in our game.
//
import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    protected int size;
    protected Character orientation;
    protected List<Tile> shipSegments = new ArrayList<>();

    public int getSize() {
        return size;
    }

    public Character getOrientation() {
        return orientation;
    }

    public List<Tile> getShipSegments() {
        return shipSegments;
    }
}
