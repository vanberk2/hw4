//
// Author(s): Adam vanBerkum, Nathan Seitz
//

import java.util.ArrayList;
import java.util.List;

//
// The Away class holds the logical representation of the opponents board from a user's perspective. This class manages
//   details relating to firing at the opponents board, marking whether we hit or missed any ships, as well as the
//   the images that go along with that.
//
public class Away {
    private List<String> board = new ArrayList<>();
    private Integer hits = 0;
    private Integer misses = 0;

    //
    // Initializes the away board to be all water tiles.
    //
    public Away() {
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            board.add(new String("batt100.gif"));
        }
    }

    //
    // Resets the away board to its original state of all water tiles. This is intended to be used when the game is
    // being restarted.
    //
    public void reset() {
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            board.set(i, new String("batt100.gif"));
        }

        hits = 0;
        misses = 0;
    }

    //
    // Returns the strings referring to the images to make up the away board.
    //
    public List<String> getBoard() {
        return board;
    }

    //
    // Change the image of the tile at the specified index to the hit image. Also, increment the hits.
    //
    public void setHit (int index)
    {
        hits++;
        board.set(index, "batt103.gif");
    }

    //
    // Change the image of the tile at the specified index to the miss image. Also, increment the misses.
    //
    public void setMiss (int index)
    {
        misses++;
        board.set(index, "batt102.gif");
    }

    public Integer getHits() {
        return hits;
    }

    public Integer getMisses() {
        return misses;
    }
}
