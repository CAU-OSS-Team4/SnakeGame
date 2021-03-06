package snakegame;

public class Game {
	private int WIDTH = 40;
	private int HEIGHT = 40;
	
	private Pair[] apples;
	private Player[] players;
	
	public Game(GameContext ctx, boolean init) {
		players = ctx.players;
		apples = ctx.apples;
		WIDTH = ctx.width;
		HEIGHT = ctx.height;
		if (init) {
			for (int i = 0; i < apples.length; i++) generateApple(i);
		}
	}
	
	private void generateApple(int n) {
		while (true) {
			int x = (int)(Math.random() * WIDTH);
			int y = (int)(Math.random() * HEIGHT);
			boolean valid = true;
			for (int i = 0; i < players.length; i++) {
				Pair[] arr = players[i].getSnake();
				for (int j = 0; j < arr.length; j++) {
					if (arr[j].x == x && arr[j].y == y) {
						valid = false;
						break;
					}
				}
			}
			for (int i = 0; i < apples.length; i++) {
				if (i == n) continue;
				if (apples[i].x == x && apples[i].y == y) valid = false;
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
		
		for (int i = 0; i < players.length; i++) {
			if (players[i].getType() == PlayerType.AUTO) players[i].decide(getContext());
		}

		boolean eaten[] = new boolean[apples.length];
		for (int i = 0; i < players.length; i++) {
			if (players[i].isGameOver()) continue;
			Pair mv = players[i].getSnake()[0];
			
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
					if (arr[k].x == mv.x && arr[k].y == mv.y) game_over[i] = true;
				}
			}
			if (mv.x < 0 || WIDTH <= mv.x || mv.y < 0 || HEIGHT <= mv.y) game_over[i] = true;
		}
		
		int sum = 0;
		for (int i = 0; i < players.length; i++) sum += players[i].getScore();
		
		if (sum + players.length + apples.length > WIDTH * HEIGHT) {
			for (int i = 0; i < players.length; i++) game_over[i] = true;
		}
		else {
			for (int i = 0; i < apples.length; i++) {
				if (eaten[i]) generateApple(i);
			}
		}

		for (int i = 0; i < players.length; i++) {
			if (game_over[i]) players[i].gameOver();
		}
	}
	
	public Pair[] getApples() { 
		Pair[] ret = new Pair[apples.length];
		for (int i = 0; i < apples.length; i++) ret[i] = new Pair(apples[i].x, apples[i].y);
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

	public Player[] getPlayers() {
		return players;
	}
}
