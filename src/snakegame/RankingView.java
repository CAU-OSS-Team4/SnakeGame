package snakegame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RankingView extends JPanel {
    // size of RankingView
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;

    private final JLabel backgroundImg;
    final JButton backButton;
    private final JLabel[] labels;
    private final JLabel[] rankingLabels;
    private final JLabel[] scoreLabels;
    private final JLabel[] nameLabels;
    private final JLabel[] dateLabels;


    public RankingView() {
        setLayout(null);

        backgroundImg = new JLabel();
        backgroundImg.setIcon(new ImageIcon("src/resources/background.png"));
        backgroundImg.setBounds(0, 0, 900, 900);

        backButton = new JButton();
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon("src/resources/return_icon.png"));
        backButton.setBounds(738, 25,96, 96);

        labels = new JLabel[5];
        labels[0] = new JLabel("RANKINGS");
        labels[0].setBounds(60, 50, 300, 50);
        labels[1] = new JLabel("RANK");
        labels[1].setBounds(90, 155, 100, 50);
        labels[2] = new JLabel("SCORE");
        labels[2].setBounds(220, 155, 100, 50);
        labels[3] = new JLabel("NAME");
        labels[3].setBounds(370, 155, 100, 50);
        labels[4] = new JLabel("DATE");
        labels[4].setBounds(550, 155, 100, 50);

        rankingLabels = new JLabel[10];
        scoreLabels = new JLabel[10];
        nameLabels = new JLabel[10];
        dateLabels = new JLabel[10];

        try {
            RankingTableRow[] ret = DataLoader.loadRanking();
            for (int i = 0; i < 10 ; i++) {
                rankingLabels[i] = new JLabel("" + (i + 1));
                scoreLabels[i] = new JLabel("" + ret[i].score);
                nameLabels[i] = new JLabel(ret[i].username);
                dateLabels[i] = new JLabel(DataLoader.format.format(ret[i].date));
                rankingLabels[i].setBounds(90, 245 + 65 * i, 100, 60);
                scoreLabels[i].setBounds(220, 245 + 65 * i, 100, 60);
                nameLabels[i].setBounds(370, 245 + 65 * i, 100, 60);
                dateLabels[i].setBounds(550, 245 + 65 * i, 300, 60);
                rankingLabels[i].setForeground(Color.WHITE);
                scoreLabels[i].setForeground(Color.WHITE);
                nameLabels[i].setForeground(Color.WHITE);
                dateLabels[i].setForeground(Color.WHITE);
                rankingLabels[i].setFont(new Font("Default", Font.BOLD, 26));
                scoreLabels[i].setFont(new Font("Default", Font.BOLD, 26));
                nameLabels[i].setFont(new Font("Default", Font.BOLD, 26));
                dateLabels[i].setFont(new Font("Default", Font.BOLD, 26));
                add(rankingLabels[i]);
                add(scoreLabels[i]);
                add(nameLabels[i]);
                add(dateLabels[i]);
            }
        } catch (Exception e) {
            System.out.println("Finish reading data");
        }

        for (int i = 0; i < 5; i++) {
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(new Font("Default", Font.BOLD, 28));
            add(labels[i]);
        }
        add(backButton);
        add(backgroundImg);

        setBackground(Color.WHITE);  // RankingView 의 background 색을 나타냄.
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
