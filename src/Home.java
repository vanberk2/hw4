import java.util.ArrayList;
import java.util.List;

public class Home {
    public final static int BOARD_LENGTH = 10;
    public final static int BOARD_SIZE = BOARD_LENGTH * BOARD_LENGTH;
    private int shipAmt;

    List<Tile> board = new ArrayList<>();

    public Home() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Tile tile = new Tile("batt100.gif", false, false);
            board.add(tile);
        }
    }

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
                    if (board.get(r*BOARD_LENGTH + col).isShip()) {
                        return false;
                    }
                    r++;
                }

                r = (row - shipSize) + 1;
                for (Tile tile : shipSegments) {
                    board.set(r*BOARD_LENGTH + col, tile);
                    r++;
                }
                shipAmt++;
                return true;
            }
        } else if (shipOrientation.equals('S')) {
            if (row + shipSize > BOARD_LENGTH) {
                return false;
            } else {
                int r = row;
                for (Tile tile : shipSegments) {
                    if (board.get(r*BOARD_LENGTH + col).isShip()) {
                        return false;
                    }
                    r++;
                }

                r = row;
                for (Tile tile : shipSegments) {
                    board.set(r*BOARD_LENGTH + col, tile);
                    r++;
                }
                shipAmt++;
                return true;
            }
        } else if (shipOrientation.equals('E')) {
            if (col + shipSize > BOARD_LENGTH) {
                return false;
            } else {
                int c = col;
                for (Tile tile : shipSegments) {
                    if (board.get(row*BOARD_LENGTH + c).isShip()) {
                        return false;
                    }
                    c++;
                }

                c = col;
                for (Tile tile : shipSegments) {
                    board.set(row*BOARD_LENGTH + c, tile);
                    c++;
                }
                
                shipAmt++;
                return true;
            }
        } else if (shipOrientation.equals('W')) {
            if (col - shipSize < -1) {
                return false;
            } else {
                int c = (col - shipSize) + 1;
                for (Tile tile : shipSegments) {
                    if (board.get(row*BOARD_LENGTH + c).isShip()) {
                        return false;
                    }
                    c++;
                }

                c = (col - shipSize) + 1;
                for (Tile tile : shipSegments) {
                    board.set(row*BOARD_LENGTH + c, tile);
                    c++;
                }
                
                shipAmt++;
                return true;
            }
        }

        return false;
    }

    //
    // This method should be called when our opponent fires at us -- we'll enter their firing coordinates here.
    // TODO: If we've been hit, respond to the other player letting them know.
    //
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

    public boolean checkForHit(int index) {
        Tile tile = board.get(index);
        if (tile.isShip()) {
            if(!tile.isHit()) {
                tile.setHit(true);
                return true;
            }
        }

        return false;
    }

    
    public int getShipAmt () { return this.shipAmt; }
    
    //
    // Check if all of our ships are sunk.
    //
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

    //
    // Exports a list of strings, which are the filenames of images to be used on the GUI:
    //
    public List<String> getBoard() {
        List<String> result = new ArrayList<>();
        for (Tile tile : board) {
            result.add(tile.getImage());
        }
        return result;
    }
}
