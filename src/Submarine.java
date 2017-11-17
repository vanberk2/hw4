//
// Author(s): Nathan Seitz
//

//
// A Submarine is a ship type of size 3.
//
public class Submarine extends Ship {
    public Submarine(Character orientation) {
        this.size = 3;
        this.orientation = orientation;

        if (orientation.equals('E') || orientation.equals('W')) {
            shipSegments.add(new Tile("batt1.gif", true, false));
            shipSegments.add(new Tile("batt3.gif", true, false));
            shipSegments.add(new Tile("batt5.gif", true, false));
        } else if (orientation.equals('N') || orientation.equals('S')) {
            shipSegments.add(new Tile("batt6.gif", true, false));
            shipSegments.add(new Tile("batt8.gif", true, false));
            shipSegments.add(new Tile("batt10.gif", true, false));
        }
    }
}
