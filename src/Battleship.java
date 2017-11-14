public class Battleship extends Ship {
    public Battleship(Character orientation) {
        this.size = 4;
        this.orientation = orientation;

        if (orientation.equals('E') || orientation.equals('W')) {
            shipSegments.add(new Tile("batt1.gif", true, false));
            shipSegments.add(new Tile("batt2.gif", true, false));
            shipSegments.add(new Tile("batt4.gif", true, false));
            shipSegments.add(new Tile("batt5.gif", true, false));
        } else if (orientation.equals('N') || orientation.equals('S')) {
            shipSegments.add(new Tile("batt6.gif", true, false));
            shipSegments.add(new Tile("batt7.gif", true, false));
            shipSegments.add(new Tile("batt9.gif", true, false));
            shipSegments.add(new Tile("batt10.gif", true, false));
        }
    }
}
