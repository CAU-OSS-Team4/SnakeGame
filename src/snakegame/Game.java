package snakegame;

public class Game {
	private int WIDTH = 40;
	private int HEIGHT = 40;
	
	private Pair[] apples;
	private Player[] players;
	
	public void init() {
		players = new Player[1];
		Deque snake = new Deque(WIDTH * HEIGHT + 1);
		snake.push_front(new Pair(WIDTH / 2, HEIGHT / 2));
		players[0] = new Player(snake, DIRECTION.NORTH, 0, false);
		
		apples = new Pair[1];
		apples[0] = new Pair(-1, -1);
		generateApple(0);
	}
	
	public Game() {
		init();
	}
	
	public Game(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		init();
	}
	
	public Game(GameContext ctx) {
		players = ctx.players;
		apples = ctx.apples;
		WIDTH = ctx.width;
		HEIGHT = ctx.height;
	}
	
	private void generateApple(int n) {
		while (true) {
			int x = (int)(Math.random() * WIDTH);
			int y = (int)(Math.random() * HEIGHT);
			boolean valid = true;
			for (int i = 0; i < players.length; i++) {
				Pair[] arr = players[i].getSnake();
				for (int j = 0; j < arr.length; j++) {
					if (arr[i].x == x && arr[i].y == y) {
						valid = false;
						break;
					}
				}
			}
			for (int i = 0; i < apples.length; i++) {
				if (i == n) continue;
				if (apples[n].x == x && apples[n].y == y) valid = false;
			}
			if (valid) {
				apples[n].x = x;
				apples[n].y = y;
				break;
			}
		}
	}
	
	public void progress() {
		for (int i = 0; i < players.length; i++) {
			if (players[i].isGameOver()) return;
		}

		boolean eaten[] = new boolean[apples.length];
		for (int i = 0; i < players.length; i++) {
			if (players[i].isGameOver()) continue;
			Pair mv = players[i].getSnake()[0];
			
			players[i].decide(getContext());
			if (players[i].getDirection() == DIRECTION.NORTH) mv.y--;
			else if (players[i].getDirection() == DIRECTION.SOUTH) mv.y++;
			else if (players[i].getDirection() == DIRECTION.EAST) mv.x++;
			else if (players[i].getDirection() == DIRECTION.WEST) mv.x--;
			
			boolean eat = false;
			for (int j = 0; j < apples.length; j++) {
				if (mv.x == apples[j].x && mv.y == apples[j].y) {
					eat = true;
					eaten[j] = true;
				}
			}
			if (eat) {
				players[i].snake_push_front(mv);
				players[i].scoreIncrease();
			}
			else {
				players[i].snake_push_front(mv);
				players[i].snake_pop_back();
			}
		}
		
		boolean game_over[] = new boolean[players.length];
		for (int i = 0; i < players.length; i++) {
			if (players[i].isGameOver()) continue;
			Pair mv = players[i].getSnake()[0];
			for (int j = 0; j < players.length; j++) {
				if (players[j].isGameOver()) continue;
				Pair[] arr = players[j].getSnake();
				for (int k = 0; k < arr.length; k++) {
					if (i == j && k == 0) continue;
					if (arr[i].x == mv.x && arr[i].y == mv.y) game_over[i] = true;
				}
			}
			if (mv.x < 0 || WIDTH <= mv.x || mv.y < 0 || HEIGHT <= mv.y) game_over[i] = true;
		}

		for (int i = 0; i < players.length; i++) {
			if (game_over[i]) players[i].gameOver();
		}
	}
	
	public Pair[] getApple() { 
		Pair[] ret = new Pair[apples.length];
		for (int i = 0; i < apples.length; i++) ret[i] = new Pair(apples[0].x, apples[0].y);
		return ret;
	}
	
	public int getWidth() { return WIDTH; }
	
	public int getHeight() { return HEIGHT; }
	
	public GameContext getContext() { return new GameContext(WIDTH, HEIGHT, players, apples); }

	public void setContext(GameContext gameContext) {
		this.WIDTH = gameContext.width;
		this.HEIGHT = gameContext.height;
		this.apples = gameContext.apples;
		this.players = gameContext.players;
	}
}
