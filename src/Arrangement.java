import java.util.ArrayList;
import java.util.List;

public class Arrangement {
    public final static int BOARD_LENGTH = 10;
    public final static int BOARD_SIZE = BOARD_LENGTH * BOARD_LENGTH;

    List<Tile> board = new ArrayList<>();

    public Arrangement() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Tile tile = new Tile("batt100.gif", false, false);
            board.add(tile);
        }
    }

    // TODO: Return false if we are trying to place a ship on top of another ship
    public boolean addShip(Ship ship, int row, int col) {
        int shipSize = ship.getSize();
        Character shipOrientation = ship.getOrientation();
        List<Tile> shipSegments = ship.getShipSegments();

        if (shipOrientation.equals('N')) {
            if (row - shipSize < -1) {
                return false;
            } else {
                int r = (row - shipSize) + 1;
                for (Tile tile : shipSegments) {
                    board.set(r*BOARD_LENGTH + col, tile);
                    r++;
                }
            }
        } else if (shipOrientation.equals('S')) {
            if (row + shipSize > BOARD_LENGTH) {
                return false;
            } else {
                int r = row;
                for (Tile tile : shipSegments) {
                    board.set(r*BOARD_LENGTH + col, tile);
                    r++;
                }
            }
        } else if (shipOrientation.equals('E')) {
            if (col + shipSize > BOARD_LENGTH) {
                return false;
            } else {
                int c = col;
                for (Tile tile : shipSegments) {
                    board.set(row*BOARD_LENGTH + c, tile);
                    c++;
                }
            }
        } else if (shipOrientation.equals('W')) {
            if (col - shipSize < -1) {
                return false;
            } else {
                int c = (col - shipSize) + 1;
                for (Tile tile : shipSegments) {
                    board.set(row*BOARD_LENGTH + c, tile);
                    c++;
                }
            }
        }

        return false;
    }

    public boolean checkForHit(int row, int col) {
        Tile tile = board.get(row*BOARD_LENGTH + col);
        if (tile.isShip()) {
            if(!tile.isHit()) {
                tile.setHit(true);
                return true;
            }
        }

        return false;
    }

    public boolean gameOver() {
        for (Tile tile : board) {
            if (tile.isShip()) {
                if (!tile.isHit()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> getBoard() {
        List<String> result = new ArrayList<>();
        for (Tile tile : board) {
            result.add(tile.getImage());
        }
        return result;
    }

    public void consolePrint() {
        int i = 1;
        for(Tile tile : board) {
            if (tile.isShip()) {
                System.out.print("S ");
            } else {
                System.out.print("W ");
            }

            if (i % 10 == 0) {
                System.out.println();
            }
            i++;
        }
    }
}
