package snakegame;

import javax.swing.*;
import java.awt.*;

public class IngameMenu extends JPanel {
    // size of IngameMenu
    private static final int INGAMEMENU_WIDTH = 300;
    private static final int INGAMEMENU_HEIGHT = 300;

    JButton[] buttons;

    public IngameMenu() {
        setLayout(null);
        setOpaque(false);

        Font font = new Font("Default", Font.BOLD, 28);
        JLabel label = new JLabel("PAUSE");
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setBounds(0, 0, 300, 60);
        add(label);

        buttons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setContentAreaFilled(false);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBounds(0, 60 * (i + 1), 300, 60);
            buttons[i].setFont(font);
            add(buttons[i]);
        }
        buttons[0].setText("RESUME");
        buttons[1].setText("RESTART");
        buttons[2].setText("SAVE");
        buttons[3].setText("EXIT");

        setFocusable(true);
        setPreferredSize(new Dimension(INGAMEMENU_WIDTH, INGAMEMENU_HEIGHT));
    }
}
