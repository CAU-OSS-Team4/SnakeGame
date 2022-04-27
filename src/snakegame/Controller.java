package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller implements ActionListener {
    private final Snake snake;
    private final Apple apple;
    private final MainMenu mainMenu;
    private final Board board;
    private JFrame frame;
    private JPanel panel;
    private CardLayout cardLayout;

    // 게임이 진행 중인지, 아니면 게임 오버 상태인지를 나타냄
    // true: 게임이 진행 중인 상태, false: 게임 오버 상태
    public static boolean inGame = true;

    // 게임의 frame 속도(?)을 나타냄. DELAY 의 값이 작을 수록 게임의 진행 속도가 빨라진다.
    private static final int DELAY = 50;

    private final Timer timer;

    public Controller(Snake snake, Apple apple, MainMenu mainMenu, Board board) {
        this.snake = snake;
        this.apple = apple;
        this.mainMenu = mainMenu;
        this.board = board;

        EventQueue.invokeLater(() -> {
            frame = new JFrame();
            cardLayout = new CardLayout();
            panel = new JPanel(cardLayout);
            panel.add(mainMenu);
            panel.add(board);
            frame.add(panel);

            // 프로그램 시작시 MainMenu 화면이 먼저 보이도록 설정.
            cardLayout.first(panel);

            frame.setVisible(true);

            // 게임 화면을 마우스 드래그로 조정 불가능하게 함.
            frame.setResizable(false);
            frame.pack();

            frame.setTitle("Snake Game");  // 게임 화면의 Title 이름
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

        mainMenu.buttons[0].addActionListener(e -> {
            initGame();
        });

        mainMenu.buttons[1].addActionListener(e -> {
            // TODO: load a saved game
        });

        mainMenu.buttons[2].addActionListener(e -> {
            // TODO: display the top-ranked players' name and score
        });

        // terminate the game.
        mainMenu.buttons[3].addActionListener(e -> System.exit(0));

        board.addKeyListener(new TAdapter());  // 방향키로 Snake 의 진행 방향을 변경하기 위한 KeyLister.

        timer = new Timer(DELAY, this);
    }

    // start the game.
    public void initGame() {
        // 게임 화면으로 전환함.
        cardLayout.next(panel);

        // generate an apple at a random location.
        apple.locateApple();
        board.requestFocusInWindow();  // activate keylistener.
        timer.start();
    }

    // check whether snake dies or not.
    private void checkCollision() {
        // TODO: snakes dies by running into its own body.
        // TODO: This logic works incorrectly.
        int[] x = snake.get_Xpos();
        int[] y = snake.get_Ypos();

        for (int z = snake.getDots(); z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        // 아래쪽 edge 에 충돌한 경우
        if (y[0] >= Board.B_HEIGHT) {
            inGame = false;
        }

        // 위쪽 edge 에 충돌한 경우
        if (y[0] < 0) {
            inGame = false;
        }

        // 오른쪽 edge 에 충돌한 경우
        if (x[0] >= Board.B_WIDTH) {
            inGame = false;
        }

        // 왼쪽 edge 에 충돌한 경우
        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    // If snake eats an apple, it gets longer
    // and an apple appears at a random location.
    private void checkApple() {
        if ((snake.get_Xpos()[0] == apple.getApple_x())
                && (snake.get_Ypos()[0] == apple.getApple_y())) {
            snake.plusDots();
            apple.locateApple();
        }
    }


    // Key Event 받기 위한 Adapter
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();  // 입력받은 Key Code 값.

            // 오른쪽으로 이동하지 않으면서 왼쪽 방향키를 누른 경우
            if ((key == KeyEvent.VK_LEFT) && (!snake.isRightDirection())) {
                snake.setLeftDirection(true);
                snake.setUpDirection(false);
                snake.setDownDirection(false);
            }

            // 왼쪽으로 이동하지 않으면서 오른쪽 방향키를 누른 경우
            if ((key == KeyEvent.VK_RIGHT) && (!snake.isLeftDirection())) {
                snake.setRightDirection(true);
                snake.setUpDirection(false);
                snake.setDownDirection(false);
            }

            // 아래쪽으로 이동하지 않으면서 위쪽 방향키를 누른 경우
            if ((key == KeyEvent.VK_UP) && (!snake.isDownDirection())) {
                snake.setLeftDirection(false);
                snake.setRightDirection(false);
                snake.setUpDirection(true);
            }

            // 위쪽으로 이동하지 않으면서 아래쪽 방향키를 누른 경우
            if ((key == KeyEvent.VK_DOWN) && (!snake.isUpDirection())) {
                snake.setLeftDirection(false);
                snake.setRightDirection(false);
                snake.setDownDirection(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 게임이 끝날 때까지 계속 반복
        if (inGame) {
            checkApple();
            checkCollision();
            snake.move();
        }

        // 생성된 사과, 이동한 snake 의 위치 등을 반영하여 게임 화면에 그려냄.
        board.repaint();
    }
}