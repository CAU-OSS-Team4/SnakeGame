package snakegame;

import javax.swing.*;
import java.awt.*;

public class Controller{
    private final MainMenu mainMenu;
    private final RankingView rankingView;
    private final GameBoard board;
    private JFrame frame;
    private JPanel panel;
    private CardLayout cardLayout;
    private String gameMode;

    public Controller(MainMenu mainMenu, RankingView rankingView, GameBoard board) {
        this.mainMenu = mainMenu;
        this.rankingView = rankingView;
        this.board = board;
        board.setController(this);

        EventQueue.invokeLater(() -> {
            frame = new JFrame();
            cardLayout = new CardLayout();
            panel = new JPanel(cardLayout);
            panel.add(mainMenu, "mainMenu");
            panel.add(board, "board");
            panel.add(rankingView, "rankingView");
            frame.add(panel);

            // 프로그램 시작시 MainMenu 화면이 먼저 보이도록 설정.
            cardLayout.show(panel, "mainMenu");

            frame.setVisible(true);

            // 게임 화면을 마우스 드래그로 조정 불가능하게 함.
            frame.setResizable(false);
            frame.pack();

            frame.setTitle("Snake Game");  // 게임 화면의 Title 이름
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

        // SINGLE PLAY
        mainMenu.buttons[0].addActionListener(e -> {
            cardLayout.show(panel, "board");
            board.initSinglePlay();
            board.requestFocusInWindow();
            gameMode = "SINGLE PLAY";
        });

        // DUAL PLAY
        mainMenu.buttons[1].addActionListener(e -> {
            cardLayout.show(panel, "board");
            board.initDualPlay();
            board.requestFocusInWindow();
            gameMode = "DUAL PLAY";
        });

        // AUTO PLAY
        mainMenu.buttons[2].addActionListener(e -> {
            cardLayout.show(panel, "board");
            board.initAutoPlay();
            board.requestFocusInWindow();
            gameMode = "AUTO PLAY";
        });

        // LOAD
        mainMenu.buttons[3].addActionListener(e -> {
            cardLayout.show(panel, "board");
            try {
                board.load(DataLoader.loadAndRemove());
            } catch (Exception _e){
                board.initSinglePlay();
            } finally {
                board.requestFocusInWindow();
                gameMode = "SINGLE PLAY";
            }
        });

        // RANKING
        mainMenu.buttons[4].addActionListener(e -> {
            cardLayout.show(panel, "rankingView");
            rankingView.init();
        });

        // EXIT: terminate the game.
        mainMenu.buttons[5].addActionListener(e -> System.exit(0));

        rankingView.backButton.addActionListener(e -> {
            // MainMenu 화면으로 돌아감.
            cardLayout.show(panel, "mainMenu");
        });

        // EXIT
        board.ingameMenu.buttons[0].addActionListener(e -> {
            cardLayout.show(panel, "mainMenu");
            board.ingameMenu.setVisible(false);
        });

        // RESUME
        board.ingameMenu.buttons[1].addActionListener(e -> {
            board.timer.start();
            board.ingameMenu.setVisible(false);
        });

        // RESTART
        board.ingameMenu.buttons[2].addActionListener(e -> {
            board.ingameMenu.setVisible(false);
            switch (gameMode) {
                case "SINGLE PLAY":
                    board.initSinglePlay();
                    break;
                case "DUAL PLAY":
                    board.initDualPlay();
                    break;
                case "AUTO PLAY":
                    board.initAutoPlay();
                    break;
            }
        });

        // SAVE
        board.ingameMenu.buttons[3].addActionListener(e -> {
            board.save();
            cardLayout.show(panel, "mainMenu");
            board.ingameMenu.setVisible(false);
        });
    }

    public void showMainMenu(){
        cardLayout.show(panel, "mainMenu");
    }
}