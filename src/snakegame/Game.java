package snakegame;

enum DIRECTION { NORTH, SOUTH, EAST, WEST }

class Game {
	private int WIDTH = 40;
	private int HEIGHT = 40;
	
	private int score;
	private Pair apple;
	private DIRECTION d;
	private Deque snake;
	
	private boolean is_game_over;
	
	public void init() {
		score = 0;
		is_game_over = false;
		
		snake = new Deque(WIDTH * HEIGHT + 1);
		snake.push_front(new Pair(WIDTH / 2, HEIGHT / 2));
		d = DIRECTION.NORTH;
		
		apple = new Pair(-1, -1);
		generateApple();
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
		snake = ctx.snake;
		apple = ctx.apple;
		score = ctx.score;
		WIDTH = ctx.width;
		HEIGHT = ctx.height;
		d = ctx.direction;
	}
	
	private void generateApple() {
		Pair[] arr = snake.list();
		while (true) {
			int x = (int)(Math.random() * WIDTH);
			int y = (int)(Math.random() * HEIGHT);
			boolean valid = true;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].x == x && arr[i].y == y) {
					valid = false;
					break;
				}
			}
			if (valid) {
				apple.x = x;
				apple.y = y;
				break;
			}
		}
	}
	
	public void progress() {
		if (is_game_over) return;
		
		Pair mv = snake.front();
		if (d == DIRECTION.NORTH) mv.y--;
		else if (d == DIRECTION.SOUTH) mv.y++;
		else if (d == DIRECTION.EAST) mv.x++;
		else if (d == DIRECTION.WEST) mv.x--;
		
		if (mv.x == apple.x && mv.y == apple.y) {
			snake.push_front(mv);
			score++;
			if (score == WIDTH * HEIGHT - 1) is_game_over = true;
			else generateApple();
		}
		else {
			snake.push_front(mv);
			snake.pop_back();
		}
		
		Pair[] arr = snake.list();
		for (int i = 1; i < arr.length; i++) {
			if (arr[i].x == mv.x && arr[i].y == mv.y) is_game_over = true;
		}
		if (mv.x < 0 || WIDTH <= mv.x || mv.y < 0 || HEIGHT <= mv.y) is_game_over = true;
	}
	
	public void setDirection(DIRECTION x) { d = x; }
	
	public DIRECTION getDirection() { return d; }
	
	public Pair getApple() { return new Pair(apple.x, apple.y); }
	
	public Pair[] getSnake() { return snake.list(); }
	
	public int getScore() { return score; }
	
	public boolean isGameOver() { return is_game_over; }
	
	public int getWidth() { return WIDTH; }
	
	public int getHeight() { return HEIGHT; }
	
	public GameContext getContext() { return new GameContext(WIDTH, HEIGHT, snake, apple, score, d); }

	public void setContext(GameContext gameContext){
		this.WIDTH = gameContext.width;
		this.HEIGHT = gameContext.height;
		this.apple = gameContext.apple;
		this.snake = gameContext.snake;
		this.score = gameContext.score;
		setDirection(gameContext.direction);
	}
}
