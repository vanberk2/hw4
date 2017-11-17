import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.*;

public class TestGUI extends JFrame {
    private List<JButton> homeGrid = new ArrayList<>();
    private List<JButton> awayGrid = new ArrayList<>();
    private int fireIndex;
    private int firedHits = 0;
    private int theirHits = 0;
    private boolean myturn = false;
    private boolean gameInProgress = false;
    private boolean continuePlaying = false;

    private String ip = "127.0.0.1";
    private String port = "1037";
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

    private Thread serverThread;
    private Thread clientThread;

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
        gui.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
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
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

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
                            shipList.setSelectedIndex(0);

                            if (shipListModel.isEmpty()) {
                                // All ships have been placed
                            }
                        }

                        redraw();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
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
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

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

                    fireIndex = index;
                    System.out.println("Firing index: " + fireIndex);
                    if (myturn) { myturn = false; }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
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
        gameState = "Initiate server or connect to server";
        redrawStatus();
        //
        // Add all panels to the GUI:
        //
        gui.add(homeGridGUI);
        gui.add(awayGridGUI);
        gui.add(shipSelection);
        gui.add(status);
        redraw();

        //
        // Add Start menu
        //
        JMenu startMenu = new JMenu("Start");
        JMenuItem startServer = new JMenuItem("Start Server");
        startServer.setMnemonic('s');
        startServer.addActionListener(new startServrHandler());
        startMenu.add(startServer);
        JMenuItem startClient = new JMenuItem("Start Client");
        startClient.setMnemonic('C');
        startClient.addActionListener(new startClientHandler());
        startMenu.add(startClient);
        //
        // Add menu bar to the JFrame:
        //
        MenuBar menuBar = new MenuBar(away);
        setJMenuBar(menuBar);
        menuBar.add(startMenu);

        // TODO: resizeable game window scales images appropriately?
        setSize(400, 440);
        setResizable(false);
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
    }

    public void redrawStatus () {
        status.setText("<html> Game state: " + gameState
                + "<br>Hits: " + away.getHits()
                + "<br>Misses: " + away.getMisses()
                + "<br>Ships: " + home.getShipAmt()
                + "<br>Enemy ship hits: " + firedHits + "</html>"
        );
        status.repaint();
        status.validate();
    }

    public void gameOperations (ObjectInputStream in, ObjectOutputStream out) {
        int index;
        boolean hitOrMiss;
        System.out.println("In operations");

        while (firedHits < 17 && theirHits < 17) {
            if (home.getShipAmt() != 5) {
                gameState = "Waiting for your ship placement";
                redrawStatus();
                while (home.getShipAmt() != 5) {
                    Thread.yield();
                }
            }

            if (myturn) {
                try {
                    gameState = "Waiting for your shot";
                    redrawStatus();
                    while (myturn) {
                        Thread.yield();
                    }
                    out.writeInt(fireIndex);
                    out.flush();
                    System.out.println("Sending index: " + fireIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    hitOrMiss = in.readBoolean();
                    if (hitOrMiss == true) {System.out.println("Hit");away.setHit(fireIndex); firedHits++;}
                    else {System.out.println("Miss");away.setMiss(fireIndex);}
                    redraw();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    gameState = "Waiting for opponent's shot";
                    redrawStatus();
                    index = in.readInt();
                    System.out.println("Got index: " + index);
                    hitOrMiss = home.checkForHit(index);
                    if (hitOrMiss) theirHits++;
                    redraw();
                    out.writeBoolean(hitOrMiss);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myturn = true;
            }
        }

        gameState = "Game Over";
        redrawStatus();
    }

    private class waitForClientThread implements Runnable {
        private ServerSocket server;

        public waitForClientThread (ServerSocket s) {
            super ();
            server = s;
            System.out.println("Server thread initiated");
        }

        public void run () {
            ObjectInputStream in;
            ObjectOutputStream out;
            if (!gameInProgress) {
                try {
                    gameState = "Waiting for client";
                    redrawStatus();

                    System.out.println("Waiting for client");
                    Socket clientSocket = server.accept();
                    System.out.println("Client accepted");

                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    in = new ObjectInputStream(clientSocket.getInputStream());

                    gameInProgress = true;
                    System.out.println("Got client input/output stream");
                    gameOperations(in, out);
                } catch (IOException e) {
                    System.out.println("Couldn't get I/O");
                    e.printStackTrace();
                }

                int result = JOptionPane.showConfirmDialog(null, "Would you like to continue playing?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                {
                    resetGame();
                    try {
                        gameState = "Waiting for client";
                        redrawStatus();

                        System.out.println("Waiting for client");
                        Socket clientSocket = server.accept();
                        System.out.println("Client accepted");

                        out = new ObjectOutputStream(clientSocket.getOutputStream());
                        in = new ObjectInputStream(clientSocket.getInputStream());
                        myturn = false;
                        gameOperations(in, out);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void initServer () {
        ServerSocket server;
        String serverport = JOptionPane.showInputDialog("Port: ");
        port = serverport;
        try {
            server = new ServerSocket(Integer.parseInt(port));
            Thread thread = new Thread(new waitForClientThread(server));
            serverThread = thread;
            clientThread = thread;
            thread.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public class sendToServerThread implements Runnable {
        public void run () {
            ObjectOutputStream out;
            ObjectInputStream in;
            System.out.println("Trying to send request to server");
            if (!gameInProgress) {
                String serverip = JOptionPane.showInputDialog("IP: ");
                ip = serverip;
                String clientport = JOptionPane.showInputDialog("Port: ");
                try {
                    Socket clientSocket = new Socket(serverip, Integer.parseInt(clientport));

                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    in = new ObjectInputStream(clientSocket.getInputStream());
                    System.out.println("Got client input/out stream");
                    gameInProgress = true;
                    myturn = true;
                    gameOperations(in, out);
                } catch (IOException e) {
                    System.out.println("Couldn't get I/O");
                    e.printStackTrace();
                }
                int result = JOptionPane.showConfirmDialog(null, "Would you like to continue playing?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                {
                    resetGame();
                    try {
                        Socket clientSocket = new Socket(serverip, Integer.parseInt(clientport));

                        out = new ObjectOutputStream(clientSocket.getOutputStream());
                        in = new ObjectInputStream(clientSocket.getInputStream());
                        myturn = true;
                        gameOperations(in, out);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void initClient () {
        try {
            Thread thread = new Thread (new sendToServerThread());
            clientThread = thread;
            thread.start();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public void resetGame() {
        home.reset();
        away.reset();

        theirHits = 0;
        firedHits = 0;
        gameState = "";

        String[] shipStrings = {"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
        for (String s : shipStrings) {
            shipListModel.addElement(s);
        }
        shipList.setSelectedIndex(0);

        redraw();
    }

    public static void main(String args[]) {
        // Create and place ships
        Home home = new Home();
        Away away = new Away();

        // Build a GUI and show the home on screen
        TestGUI testGUI = new TestGUI(home, away);
        testGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private class startClientHandler implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initClient();
        }
    }

    private class startServrHandler implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            initServer();
        }
    }
}


