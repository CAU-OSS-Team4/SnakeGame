package snakegame;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    // size of MainMenu
    private static final int MENU_WIDTH = 900;
    private static final int MENU_HEIGHT = 900;

    private final JLabel label;
    private ImageIcon icon;
    JButton[] buttons;

    public MainMenu() {
        label = new JLabel();
        icon = new ImageIcon("src/resources/mainscreen.png");
        label.setIcon(icon);
        buttons = new JButton[6];
        Font font = new Font("Default", Font.ITALIC, 28);
        for (int i = 0; i < 6; i++) {
            buttons[i] = new JButton();
            buttons[i].setContentAreaFilled(false);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFont(font);
            add(buttons[i]);
        }
        buttons[0].setText("SINGLE PLAY");
        buttons[1].setText("DUAL PLAY");
        buttons[2].setText("AUTO PLAY");
        buttons[3].setText("LOAD");
        buttons[4].setText("RANKING");
        buttons[5].setText("EXIT");

        setLayout(null);
        label.setBounds(0, 0, 900, 900);
        buttons[0].setBounds(600, 520, 250, 40);
        buttons[1].setBounds(600, 580, 250, 40);
        buttons[2].setBounds(600, 640, 250, 40);
        buttons[3].setBounds(600, 700, 250, 40);
        buttons[4].setBounds(600, 760, 250, 40);
        buttons[5].setBounds(600, 820, 250, 40);

        add(label);

        setBackground(Color.WHITE);  // Main menu 의 background 색을 나타냄.
        setFocusable(true);
        setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));  // Main menu 의 크기 결정.
    }
}
