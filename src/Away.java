import java.util.ArrayList;
import java.util.List;

public class Away {
    private List<String> board = new ArrayList<>();
    private Boolean weWon = false;
    private Integer hits = 0;
    private Integer misses = 0;

    public Away() {
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            board.add(new String("batt100.gif"));
        }
    }

    public List<String> getBoard() {
        return board;
    }

    //
    // TODO: call this in the GUI after we fire at our opponent, if they tell if we won, then end the game!
    //
    public Boolean didWeWin() {
        return weWon;
    }

    public Integer getHits() {
        return hits;
    }

    public Integer getMisses() {
        return misses;
    }

    // TODO: send (x, y) over the connection to opponent and see if we struck their ship
    public boolean fire(int index) {
        // TODO: if we hit our opponent then:
        //hits++;
        //board.set(index, "batt103.gif");
        //weWon = true;
        //return true

        // TODO: if we miss our opponent then:
        misses++;
        board.set(index, "batt102.gif");
        return false;
    }
}
