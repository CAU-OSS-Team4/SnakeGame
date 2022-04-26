package snakegame;

class Game {
	public static int WIDTH = 40;
	public static int HEIGHT = 40;
	private static int QUEUE_SIZE = WIDTH * HEIGHT + 1;

	private Pair apple;
	private int d;
	private Deque snake;
	
	public boolean is_game_over;

	public Game() {
		is_game_over = false;
		snake = new Deque(QUEUE_SIZE);
		snake.push_front(new Pair(WIDTH / 2, HEIGHT / 2));
		apple = new Pair(-1, -1);
		generateApple();
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
	
	public void progress() {}
	
	public void setDirection(int x) {}
	
	public Pair getApple() {}
	
	public Pair[] getSnake() {}
}
