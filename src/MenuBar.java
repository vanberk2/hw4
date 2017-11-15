import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();

        JMenu menu = new JMenu("Menu");
        menu.setMnemonic('M');

        // TODO: Add all submenus

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(menu);
    }
}
