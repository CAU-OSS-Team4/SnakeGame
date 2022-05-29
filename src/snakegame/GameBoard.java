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
        setPreferredSize(new Dimension(B_WIDTH * 2, B_HEIGHT));
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

    public void initSinglePlay(){
        ingameMenu.enableSave();
        this.backend = GameFactory.createSingleMode();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initDualPlay() {
        ingameMenu.disableSave();
        this.backend = GameFactory.createDualMode();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAutoPlay() {
        ingameMenu.disableSave();
        this.backend = GameFactory.createAutoMode();
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
    	Player[] players = backend.getPlayers();
    	for (int i = 0; i < players.length; i++) {
    		if (players[i].isGameOver()) return;
    	}
    	
    	Pair[] apples = backend.getApples();
    	for (int i = 0; i < apples.length; i++)
    		g.drawImage(appleImg, apples[i].x*EXPAND_RATE, apples[i].y*EXPAND_RATE, this);
    	
    	for (int i = 0; i < players.length; i++) {
    		Pair[] snake = players[i].getSnake();
    		for (int j = 0; j < snake.length; j++) {
                if (j == 0)
                    g.drawImage(headImg, snake[j].x*EXPAND_RATE, snake[j].y*EXPAND_RATE, this);
                else
                    g.drawImage(bodyImg, snake[j].x*EXPAND_RATE, snake[j].y*EXPAND_RATE, this);
    		}
    	}
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // SINGLE PLAY
        if (this.backend.getPlayers().length == 1) {
            if (!this.backend.getPlayers()[0].isGameOver()) {
                this.backend.progress();
            } else{
                gameOver();
            }
        } else {
            // DUAL PLAY & AUTO PLAY
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
            }
        } else {
            // DUAL PLAY Mode & AUTO PLAY Mode
            if (this.backend.getPlayers()[0].isGameOver() && this.backend.getPlayers()[1].isGameOver()) {
                JOptionPane.showMessageDialog(new JFrame(), "Draw");
            } else if (this.backend.getPlayers()[0].isGameOver()) {
                JOptionPane.showMessageDialog(new JFrame(), "Winner is PLAYER 2");
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Winner is PLAYER 1");
            }
        }
        this.controller.frame.setSize(new Dimension(875, 905));
        this.controller.showMainMenu();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        	Player[] players = backend.getPlayers();
        	int key = e.getKeyCode();
        	
        	if (key == KeyEvent.VK_ESCAPE) {
                timer.stop();
                ingameMenu.setVisible(true);
            }
        	
        	else if (key == KeyEvent.VK_LEFT) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER1 && players[i].getDirection() != DIRECTION.EAST)
        				players[i].setDirection(DIRECTION.WEST);
        		}
        	}
        	else if (key == KeyEvent.VK_RIGHT) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER1 && players[i].getDirection() != DIRECTION.WEST)
        				players[i].setDirection(DIRECTION.EAST);
        		}
        	}
        	else if (key == KeyEvent.VK_UP) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER1 && players[i].getDirection() != DIRECTION.SOUTH)
        				players[i].setDirection(DIRECTION.NORTH);
        		}
        	}
        	else if (key == KeyEvent.VK_DOWN) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER1 && players[i].getDirection() != DIRECTION.NORTH)
        				players[i].setDirection(DIRECTION.SOUTH);
        		}
        	}
        	
        	else if (key == KeyEvent.VK_A) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER2 && players[i].getDirection() != DIRECTION.EAST)
        				players[i].setDirection(DIRECTION.WEST);
        		}
        	}
        	else if (key == KeyEvent.VK_D) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER2 && players[i].getDirection() != DIRECTION.WEST)
        				players[i].setDirection(DIRECTION.EAST);
        		}
        	}
        	else if (key == KeyEvent.VK_W) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER2 && players[i].getDirection() != DIRECTION.SOUTH)
        				players[i].setDirection(DIRECTION.NORTH);
        		}
        	}
        	else if (key == KeyEvent.VK_S) {
        		for (int i = 0; i < players.length; i++) {
        			if (players[i].getType() == PlayerType.PLAYER2 && players[i].getDirection() != DIRECTION.NORTH)
        				players[i].setDirection(DIRECTION.SOUTH);
        		}
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
        initSinglePlay();
        backend.setContext(gameContext);
    }
}
