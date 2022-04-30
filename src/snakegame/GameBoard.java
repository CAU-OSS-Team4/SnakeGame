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
        this.backend = new Game();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
    private void doDrawing(Graphics g){
        if(!this.backend.isGameOver()) {
            Pair apple = backend.getApple();

            g.drawImage(appleImg, apple.x*EXPAND_RATE, apple.y*EXPAND_RATE, this);

            Pair[] snake = backend.getSnake();
            for (int i=0;i<snake.length;i++) {
                if (i == 0)
                    g.drawImage(headImg, snake[i].x*EXPAND_RATE, snake[i].y*EXPAND_RATE, this);
                else
                    g.drawImage(bodyImg, snake[i].x*EXPAND_RATE, snake[i].y*EXPAND_RATE, this);
            }
            Toolkit.getDefaultToolkit().sync();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.backend.isGameOver()) {
            this.backend.progress();
        } else{
            gameOver();
        }
        repaint();
    }

    private void gameOver() {
        timer.stop();
        int score = this.backend.getScore();
        JFrame inputDialog = new JFrame();
        String name = JOptionPane.showInputDialog(inputDialog, "Enter name");
        RankingTableRow record = new RankingTableRow(name, score, new Date());
        try {
            DataLoader.updateScoreboard(record);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.controller.showMainMenu();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            DIRECTION d = backend.getDirection();
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && !(d==DIRECTION.WEST)) {
                backend.setDirection(DIRECTION.WEST);
            }
            if ((key == KeyEvent.VK_RIGHT) && !(d==DIRECTION.EAST)) {
                backend.setDirection(DIRECTION.EAST);
            }
            if ((key == KeyEvent.VK_UP) && !(d==DIRECTION.NORTH)) {
                backend.setDirection(DIRECTION.SOUTH);
            }
            if ((key == KeyEvent.VK_DOWN) && !(d==DIRECTION.SOUTH)) {
                backend.setDirection(DIRECTION.NORTH);
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
}

