import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Away {
    private List<String> board = new ArrayList<>();
    private Boolean clientBool = false, serverBool = false;
    private Integer hits = 0;
    private Integer misses = 0;
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private ServerSocket server = null;

    public Away() {
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            board.add(new String("batt100.gif"));
        }
    }

    public void reset() {
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            board.set(i, new String("batt100.gif"));
        }

        hits = 0;
        misses = 0;
    }

    public List<String> getBoard() {
        return board;
    }

    public void setHit (int index)
    {
        hits++;
        board.set(index, "batt103.gif");
    }

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
