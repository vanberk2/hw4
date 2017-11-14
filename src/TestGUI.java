import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestGUI extends JFrame {
    private List<JButton> grid = new ArrayList<>();
    private Arrangement arrangement;

    private Container container;

    public TestGUI(Arrangement arrangement) {
        super("Battleship Test");
        this.arrangement = arrangement;

        container = new Container();
        getContentPane().add(container);
        container.setLayout(new GridLayout(10, 10));

        // TODO: resizeable frame and icons?
        setSize(200, 200);
        setVisible( true );

        List<String> images = arrangement.getBoard();
        for (int i = 0; i < Arrangement.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + images.get(i));
            JButton current = new JButton(icon);
            current.setBorder(new LineBorder(Color.black));
            current.setOpaque(true);
            grid.add(current);
            container.add(current);
        }

        redraw();
    }

    public void redraw() {
        List<String> images = arrangement.getBoard();
        for (int i = 0; i < Arrangement.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + images.get(i));
            grid.get(i).setIcon(icon);
        }
        container.repaint();
        container.validate();
    }

    public static void main(String args[]) {
        // Create and place ships
        Arrangement arrangement = new Arrangement();
        arrangement.addShip(new AircraftCarrier('N'), 8, 1);
        arrangement.addShip(new Battleship('E'), 0, 0);
        arrangement.addShip(new Destroyer('W'), 3, 8);
        arrangement.addShip(new Submarine('S'), 7, 9);
        arrangement.addShip(new PatrolBoat('S'), 5, 5);

        // Build a GUI and show the arrangement on screen
        TestGUI testGUI = new TestGUI(arrangement);
        testGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Hit some ships
        arrangement.checkForHit(0, 0);
        arrangement.checkForHit(6, 1);

        // Miss ships
        arrangement.checkForHit(0, 9);

        // Show the hits on the GUI
        testGUI.redraw();

        // DEBUG:
        arrangement.consolePrint();
    }
}
