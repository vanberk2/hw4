import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class TestGUI extends JFrame {
    private List<JButton> homeGrid = new ArrayList<>();
    private List<JButton> awayGrid = new ArrayList<>();

    private JPanel gui;
    private JPanel homeGridGUI;
    private JPanel awayGridGUI;
    JList<String> shipList;
    JComboBox orientList;
    private JPanel shipSelection;
    private JLabel status;
    private DefaultListModel shipListModel;

    private Home home;
    private Away away;

    // TODO: keep track of game state (Place your ships, Waiting for other player's turn, Game over, etc.)
    private String gameState = "";

    public TestGUI(Home home, Away away) {
        super("Battleship Test");
        this.home = home;
        this.away = away;

        //
        // Create the JPanel to hold the following four sections:
        //   1. Home board in the top left (for your ships)
        //   2. Away board in the top right (for your shots against the opponent; will display hits/misses)
        //   3. Ship selection window in the bottom left (to choose where you want to place your ships at the
        //        beginning of the game)
        //   4. Game information and status in the bottom right (# hits, # misses, ... )
        //
        gui = new JPanel(new GridLayout(2, 2));
        gui.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        getContentPane().add(gui);

        //
        // Create grid of buttons with mouse listeners for home grid:
        //
        homeGridGUI = new JPanel();
        homeGridGUI.setBorder(new LineBorder(Color.black));
        homeGridGUI.setLayout(new GridLayout(10, 10));
        List<String> homeImages = home.getBoard();
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + homeImages.get(i));
            JButton current = new JButton(icon);

            //
            // If one of the home buttons are clicked, place the selected ship with the selected orientation.
            //
            current.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) { }

                @Override
                public void mousePressed(MouseEvent e) { }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //
                    // Get the index of the JButton clicked:
                    //
                    JButton reference = (JButton) e.getComponent();
                    int index = -1;
                    int i = 0;
                    for (JButton jb : homeGrid) {
                        if (jb.equals(reference)) {
                            index = i;
                        }
                        i++;
                    }

                    //
                    // Get row, col from index
                    //
                    int row = index / Home.BOARD_LENGTH;
                    int col = index % Home.BOARD_LENGTH;

                    //
                    // Try placing the selected ship type with the selected orientation:
                    //
                    int j = shipList.getSelectedIndex();
                    if (j != -1) {
                        boolean shipPlacedSuccussfully = false;
                        String ship = shipList.getSelectedValue();
                        Character c = (Character) orientList.getSelectedItem();
                        if (ship.equals("Aircraft Carrier")) {
                            shipPlacedSuccussfully = home.addShip(new AircraftCarrier(c), row, col);
                        } else if (ship.equals("Battleship")) {
                            shipPlacedSuccussfully = home.addShip(new Battleship(c), row, col);
                        } else if (ship.equals("Destroyer")) {
                            shipPlacedSuccussfully = home.addShip(new Destroyer(c), row, col);
                        } else if (ship.equals("Submarine")) {
                            shipPlacedSuccussfully = home.addShip(new Submarine(c), row, col);
                        } else if (ship.equals("Patrol Boat")) {
                            shipPlacedSuccussfully = home.addShip(new PatrolBoat(c), row, col);
                        }

                        if (shipPlacedSuccussfully) {
                            // Remove the ship type from the list (we do this because the user is only allowed to place
                            // one of each ship type)
                            shipListModel.remove(j);

                            if (shipListModel.isEmpty()) {
                                // All ships have been placed
                                // TODO: Send opponent notice that we're done placing ships
                                // TODO: Do not allow any more ship placement
                            }
                        }

                        redraw();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) { }

                @Override
                public void mouseExited(MouseEvent e) { }
            });
            current.setBorder(new LineBorder(Color.black));
            current.setOpaque(true);
            homeGrid.add(current);
            homeGridGUI.add(current);
        }

        //
        // Create grid of buttons, using images from Away board tiles, with mouse listeners for away grid:
        //
        awayGridGUI = new JPanel();
        awayGridGUI.setBorder(new LineBorder(Color.black));
        awayGridGUI.setLayout(new GridLayout(10, 10));
        List<String> awayImages = away.getBoard();
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + awayImages.get(i));
            JButton current = new JButton(icon);
            current.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) { }

                @Override
                public void mousePressed(MouseEvent e) { }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // Get the index of the JButton clicked:
                    JButton reference = (JButton) e.getComponent();
                    int index = -1;
                    int i = 0;
                    for (JButton jb : awayGrid) {
                        if (jb.equals(reference)) {
                            index = i;
                        }
                        i++;
                    }

                    // Fire at the opponent:
                    away.fire(index);
                    redraw();
                }

                @Override
                public void mouseEntered(MouseEvent e) { }

                @Override
                public void mouseExited(MouseEvent e) { }
            });
            current.setBorder(new LineBorder(Color.black));
            current.setOpaque(true);
            awayGrid.add(current);
            awayGridGUI.add(current);
        }

        //
        // Create combo boxes for placing ships:
        //
        shipSelection = new JPanel();
        shipSelection.setLayout(new GridLayout(2, 1));

        // Create a list of ship types to place on board
        String[] shipStrings = {"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
        shipListModel = new DefaultListModel();
        for (String s : shipStrings) {
            shipListModel.addElement(s);
        }
        shipList = new JList<>(shipListModel);
        shipList.setSelectedIndex(0);
        shipSelection.add(shipList);

        // Create a drop down menu to select the ship's orientation
        Character[] orientStrings = {'N', 'S', 'E', 'W'};
        orientList = new JComboBox(orientStrings);
        orientList.setSelectedIndex(0);
        shipSelection.add(orientList);

        //
        // Game status information:
        //
        status = new JLabel();

        //
        // Add all panels to the GUI:
        //
        gui.add(homeGridGUI);
        gui.add(awayGridGUI);
        gui.add(shipSelection);
        gui.add(status);
        redraw();

        //
        // Add menu bar to the JFrame:
        //
        MenuBar menuBar = new MenuBar();
        setJMenuBar(menuBar);

        // TODO: resizeable game window scales images appropriately?
        setSize(400, 440);
        setVisible(true);
    }

    //
    // Redraws the each component of the board. Call this function every time we change the state of the board.
    //
    public void redraw() {
        //
        // Redraw home board:
        //
        List<String> homeImages = home.getBoard();
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + homeImages.get(i));
            homeGrid.get(i).setIcon(icon);
        }
        homeGridGUI.repaint();
        homeGridGUI.validate();

        //
        // Redraw away board:
        //
        List<String> awayImages = away.getBoard();
        for (int i = 0; i < Home.BOARD_SIZE; i++) {
            Icon icon = new ImageIcon("images\\" + awayImages.get(i));
            awayGrid.get(i).setIcon(icon);
        }
        awayGridGUI.repaint();
        awayGridGUI.validate();

        //
        // Redraw ship selection list:
        //
        shipSelection.repaint();
        shipSelection.validate();

        //
        // Redraw game status information:
        //
        status.setText("<html> Game state: " + gameState
                + "<br>Hits: " + away.getHits()
                + "<br>Misses: " + away.getMisses() + "</html>"
        );
        status.repaint();
        status.validate();
    }

    public static void main(String args[]) {
        // Create and place ships
        Home home = new Home();
        Away away = new Away();

        // Build a GUI and show the home on screen
        TestGUI testGUI = new TestGUI(home, away);
        testGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
