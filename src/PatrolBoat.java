public class PatrolBoat extends Ship {
    public PatrolBoat(Character orientation) {
        this.size = 2;
        this.orientation = orientation;

        if (orientation.equals('E') || orientation.equals('W')) {
            shipSegments.add(new Tile("batt1.gif", true, false));
            shipSegments.add(new Tile("batt5.gif", true, false));
        } else if (orientation.equals('N') || orientation.equals('S')) {
            shipSegments.add(new Tile("batt6.gif", true, false));
            shipSegments.add(new Tile("batt10.gif", true, false));
        }
    }
}
