package snakegame;

import java.awt.*;
import javax.swing.JPanel;

public class Board extends JPanel {
    // size of Board
    public static final int B_WIDTH = 900;
    public static final int B_HEIGHT = 900;

    // number of dots
    // 게임 화면 내에 최대로 들어갈 수 있는 snake 의 머리와 몸통 수의 값을 나타냄.
    public static final int ALL_DOTS = 900;

    private final Snake snake;
    private final Apple apple;

    public Board(Snake snake, Apple apple) {
        this.snake = snake;
        this.apple = apple;

        setBackground(Color.BLACK);  // 게임 화면의 background 색을 나타냄.
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));  // 게임 화면 크기 결정.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        // 게임이 진행 중이면 지속적으로 apple.png 와 head.png, ball.png 을 그려냄.
        // 게임 진행 중: inGame = true
        // 게임 오버: inGame = false
        if (Controller.inGame) {
            // apple.png 을 그려냄.
            g.drawImage(apple.getImage(), apple.getApple_x(),  apple.getApple_y(), this);

            // head.png 와 ball.png 을 그려냄.
            for (int z = 0; z < snake.getDots(); z++) {
                if (z == 0) {
                    g.drawImage(snake.getHeadImage(), snake.get_Xpos()[z], snake.get_Ypos()[z], this);
                } else {
                    g.drawImage(snake.getBallImage(), snake.get_Xpos()[z], snake.get_Ypos()[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            // Game Over 메세지을 띄움.
            gameOver(g);
        }
    }

    // 게임이 종료되었을 때 Game Over 메세지를 띄우는 메소드
    private void gameOver(Graphics g) {
        String msg = "Game Over";  // 출력하는 메세지의 내용
        Font small = new Font("Default", Font.BOLD, 36);  // 메세지의 글꼴과 size
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);  // 메세지의 색
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
}
