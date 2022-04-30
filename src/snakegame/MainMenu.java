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
        buttons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setContentAreaFilled(false);
            buttons[i].setForeground(Color.WHITE);
        }
        buttons[0].setText("PLAY");
        buttons[1].setText("LOAD");
        buttons[2].setText("RANKING");
        buttons[3].setText("EXIT");

        setLayout(null);
        label.setBounds(0, 0, 900, 900);
        buttons[0].setBounds(650, 575, 200, 50);
        buttons[1].setBounds(650, 650, 200, 50);
        buttons[2].setBounds(650, 725, 200, 50);
        buttons[3].setBounds(650, 800, 200, 50);

        Font font = new Font("Default", Font.ITALIC, 28);
        buttons[0].setFont(font);
        buttons[1].setFont(font);
        buttons[2].setFont(font);
        buttons[3].setFont(font);

        for (int i = 0; i < 4; i++) {
            add(buttons[i]);
        }
        add(label);

        setBackground(Color.WHITE);  // Main menu 의 background 색을 나타냄.
        setFocusable(true);
        setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));  // Main menu 의 크기 결정.
    }
}
