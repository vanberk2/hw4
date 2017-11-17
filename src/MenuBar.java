import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    Away away;

    public MenuBar(Away away) {
        super();

        this.away = away;

        //
        // Create menu drop-down:
        //
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic('M');

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic('A');
        menu.add(aboutItem);
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg =
                        "CS342 Project 4 -  Networked Battleship\n\n" +
                        "Authors:\n" +
                        "Adam vanBerkum (vanberk2)\n" +
                        "Sharaf Atwa (satwa3)\n" +
                        "Nathan Seitz (nseitz3)\n";
                JOptionPane.showMessageDialog(null, msg, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem statisticsItem = new JMenuItem("Game Statistics");
        statisticsItem.setMnemonic('S');
        menu.add(statisticsItem);
        statisticsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg =
                        "Hits: " + away.getHits() + "\n" +
                        "Misses:" + away.getMisses() + "\n";
                JOptionPane.showMessageDialog(null, msg, "Game Statistics", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(menu);
        
        //
        // Create help drop-down:
        //
        JMenu helpMenu = new JMenu("Help");
        menu.setMnemonic('H');

        JMenuItem connectionItem = new JMenuItem("Connection");
        connectionItem.setMnemonic('C');
        helpMenu.add(connectionItem);
        connectionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "Call up your friend you want to play with and decide which of you wants to \n" +
                        "host the game. The host should click, Start Server under the Make A Connection \n" +
                        "menu.  Finally, the client must click Start Client in the Make a Connection menu \n" +
                        "and enter in the host's IP address in the dialog box.";
                JOptionPane.showMessageDialog(null, msg, "Connection Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem gameRulesItem = new JMenuItem("Game Rules");
        gameRulesItem.setMnemonic('G');
        helpMenu.add(gameRulesItem);
        gameRulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg =
                        "First, place your ships. Then, each player will take turns firing\n " +
                        "at each other's ships until one player sinks all of the other's ships \n" +
                        "(every ship segment has been destroyed). The player with ships remaining " +
                        "wins.\n The players can choose to play again if they desire.";
                JOptionPane.showMessageDialog(null, msg, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        this.add(helpMenu);
    }
}
