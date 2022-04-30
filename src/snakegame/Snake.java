package snakegame;

import snakegame.Board;

import java.awt.*;
import javax.swing.*;

public class Snake extends JFrame {
    // Snake 의 머리와 몸통 수의 합
    private int dots;
    
    // 현재 snake 의 진행 방향을 나타냄.
    private boolean leftDirection;
    private boolean rightDirection;
    private boolean upDirection;
    private boolean downDirection;
    
    private final Image head;  // head.png 의 이미지
    private final Image ball;  // dot.png 의 이미지

    // size of snake: snake 의 머리나 몸통의 크기를 나타냄.
    // 이 값은 apple.png, dot.png, head.png 의 가로와 세로 픽셀(화소) 수와 동일해야 함.
    private static final int DOT_SIZE = 10;

    // snake 의 머리부터 몸통, 꼬리 순서로 어느 좌표에 있는지를 나타냄.
    // x[0], y[0] --> 각각 snake 머리의 x 좌표, y좌표
    // x[i], y[i] for i >= 1 --> snake 몸통과 꼬리의 x 좌표, y 좌표
    private final int[] x;
    private final int[] y;

    public Snake() {
        x = new int[Board.B_WIDTH];
        y = new int[Board.B_HEIGHT];

        init();

        // 이미지 파일 읽기
        head = new ImageIcon("src/resources/head.png").getImage();
        ball = new ImageIcon("src/resources/dot.png").getImage();
    }

    public void init() {
        dots = 3;  // 게임 시작 시 Snake 의 크기 설정.

        // Snake 의 초기 진행 방향을 위쪽으로 설정함.
        leftDirection = false;
        rightDirection = false;
        upDirection = true;
        downDirection = false;

        // snake 의 시작 위치를 board 의 center 로 정함.
        for (int z = 0; z < dots; z++) {
            x[z] = 450;
            y[z] = 450 - z * 10 ;
        }
    }

    public void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        // 왼쪽으로 이동.
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        // 오른쪽으로 이동.
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        // 위쪽으로 이동.
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        // 아래쪽으로 이동.
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    public int getDots() {
        return dots;
    }

    public boolean isLeftDirection() {
        return leftDirection;
    }

    public boolean isRightDirection() {
        return rightDirection;
    }

    public boolean isUpDirection() {
        return upDirection;
    }

    public boolean isDownDirection() {
        return downDirection;
    }

    public Image getBallImage() {
        return ball;
    }

    public Image getHeadImage() {
        return head;
    }

    public int[] get_Xpos() {
        return x;
    }

    public int[] get_Ypos() {
        return y;
    }

    public void plusDots() {
        dots++;
    }

    public void setLeftDirection(boolean leftDirection) {
        this.leftDirection = leftDirection;
    }

    public void setRightDirection(boolean rightDirection) {
        this.rightDirection = rightDirection;
    }

    public void setUpDirection(boolean upDirection) {
        this.upDirection = upDirection;
    }

    public void setDownDirection(boolean downDirection) {
        this.downDirection = downDirection;
    }
}