package snakegame;

import javax.swing.*;
import java.awt.*;

public class Apple {
    // 생성된 apple 의 좌표 값
    private int apple_x;
    private int apple_y;

    private final Image apple;  // apple.png 의 이미지

    // RAND_POS = ALL_DOTS / DOT_SIZE
    // TODO: 900 -> ALL_DOTS, 10 -> DOT_SIZE
    private final int RAND_POS = Board.ALL_DOTS / 10;

    public Apple() {
        apple = new ImageIcon("src/resources/apple.png").getImage();
        locateApple();
    }

    // An apple appears at a random location
    public void locateApple() {
        // TODO: 10 -> DOT_SIZE
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * 10));

        // TODO: 10 -> DOT_SIZE
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * 10));
    }

    public int getApple_x() {
        return apple_x;
    }

    public int getApple_y() {
        return apple_y;
    }

    public Image getImage() {
        return apple;
    }
}
