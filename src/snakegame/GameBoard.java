package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

public class GameBoard extends JPanel implements ActionListener {
    private final int B_WIDTH = 900;
    private final int B_HEIGHT = 900;

    private final int EXPAND_RATE = 22;
    private final int DELAY = 100;
    private Image bodyImg;
    private Image appleImg;
    private Image headImg;

    private Game backend;
    public Timer timer;

    private Controller controller;
    public IngameMenu ingameMenu;

    public GameBoard() {
        initGameBoard();
    }
    private void initGameBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();

        ingameMenu = new IngameMenu();
        ingameMenu.setBounds(300, 300, 300, 300);
        ingameMenu.setVisible(false);
        add(ingameMenu);
    }
    private void loadImages() {

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        this.appleImg = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        this.headImg = iih.getImage();

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        this.bodyImg = iid.getImage();
    }

    public void initGame(){
        this.backend = GameFactory.createSingleMode();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
    private void doDrawing(Graphics g){
        // SINGLE PLAY & AUTO PLAY
        if (this.backend.getPlayers().length == 1) {
            if(!this.backend.getPlayers()[0].isGameOver()) {
                Pair apple = backend.getApples()[0];

                g.drawImage(appleImg, apple.x*EXPAND_RATE, apple.y*EXPAND_RATE, this);

                Pair[] snake = backend.getPlayers()[0].getSnake();
                for (int i=0;i<snake.length;i++) {
                    if (i == 0)
                        g.drawImage(headImg, snake[i].x*EXPAND_RATE, snake[i].y*EXPAND_RATE, this);
                    else
                        g.drawImage(bodyImg, snake[i].x*EXPAND_RATE, snake[i].y*EXPAND_RATE, this);
                }
                Toolkit.getDefaultToolkit().sync();
            }
        } else {
            // DUAL PLAY
            if(!this.backend.getPlayers()[0].isGameOver() && !this.backend.getPlayers()[1].isGameOver()) {
                Pair[] apples = backend.getApples();

                g.drawImage(appleImg, apples[0].x*EXPAND_RATE, apples[0].y*EXPAND_RATE, this);
                g.drawImage(appleImg, apples[1].x*EXPAND_RATE, apples[1].y*EXPAND_RATE, this);

                Pair[][] snakes = new Pair[2][];
                for (int i = 0; i < 2; i++) {
                    snakes[i] = backend.getPlayers()[i].getSnake();
                }

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < snakes[i].length; j++) {
                        if (j == 0)
                            g.drawImage(headImg, snakes[i][j].x*EXPAND_RATE, snakes[i][j].y*EXPAND_RATE, this);
                        else
                            g.drawImage(bodyImg, snakes[i][j].x*EXPAND_RATE, snakes[i][j].y*EXPAND_RATE, this);
                    }
                }

                Toolkit.getDefaultToolkit().sync();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // SINGLE PLAY & AUTO PLAY
        if (this.backend.getPlayers().length == 1) {
            if (!this.backend.getPlayers()[0].isGameOver()) {
                this.backend.progress();
            } else{
                gameOver();
            }
        } else {
            // DUAL PLAY
            if (!this.backend.getPlayers()[0].isGameOver() && !this.backend.getPlayers()[1].isGameOver()) {
                this.backend.progress();
            } else{
                gameOver();
            }
        }
        repaint();
    }

    private void gameOver() {
        timer.stop();
        if (this.backend.getPlayers().length == 1) {
            if (this.backend.getPlayers()[0].getType() == PlayerType.PLAYER1) {
                // SINGLE PLAY Mode
                int score = this.backend.getPlayers()[0].getScore();
                JFrame inputDialog = new JFrame();
                JOptionPane.showMessageDialog(new JFrame(), "Score: " + score);
                String name = JOptionPane.showInputDialog(inputDialog, "Enter name");
                if (name != null) {
                    name = name.split(" ")[0];
                    RankingTableRow record = new RankingTableRow(name, score, new Date());
                    try {
                        DataLoader.updateScoreboard(record);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
                // AUTO PLAY Mode
                int score = this.backend.getPlayers()[0].getScore();
                JOptionPane.showMessageDialog(new JFrame(), "Score: " + score);
            }
        } else {
            // DUAL PLAY Mode
            if (this.backend.getPlayers()[0].isGameOver()) {
                JOptionPane.showMessageDialog(new JFrame(), "Winner is PLAYER 2");
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Winner is PLAYER 1");
            }
        }
        this.controller.showMainMenu();
    }

    private class TAdapter extends KeyAdapter {
        // TODO: make KeyListener working on dual play mode and auto play mode
        @Override
        public void keyPressed(KeyEvent e) {
            DIRECTION d = backend.getPlayers()[0].getDirection();
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && !(d==DIRECTION.EAST)) {
                backend.getPlayers()[0].setDirection(DIRECTION.WEST);
            }
            if ((key == KeyEvent.VK_RIGHT) && !(d==DIRECTION.WEST)) {
                backend.getPlayers()[0].setDirection(DIRECTION.EAST);
            }
            if ((key == KeyEvent.VK_UP) && !(d==DIRECTION.SOUTH)) {
                backend.getPlayers()[0].setDirection(DIRECTION.NORTH);
            }
            if ((key == KeyEvent.VK_DOWN) && !(d==DIRECTION.NORTH)) {
                backend.getPlayers()[0].setDirection(DIRECTION.SOUTH);
            }
            if (key == KeyEvent.VK_ESCAPE){
                timer.stop();
                ingameMenu.setVisible(true);
            }
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void save(){
        GameContext gameContext = backend.getContext();
        try {
            DataLoader.saveGame(gameContext);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void load(GameContext gameContext) {
        initGame();
        backend.setContext(gameContext);
    }
}

