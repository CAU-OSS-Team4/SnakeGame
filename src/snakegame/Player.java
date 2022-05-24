package snakegame;

enum DIRECTION { NORTH, SOUTH, EAST, WEST }

public class Player {
	private int score;
	private boolean is_game_over;
	
	private DIRECTION direction;
	private Deque snake;
	
	public Player(Deque _snake, DIRECTION _direction, int _score, boolean over) {
		snake = _snake; direction = _direction; score = _score; is_game_over = over;
	}

	public Pair[] getSnake() { return snake.list(); }
	
	public int getScore() { return score; }
	
	public boolean isGameOver() { return is_game_over; }
	
	public void gameOver() { is_game_over = true; }
	
	public void setDirection(DIRECTION x) { direction = x; }
	
	public DIRECTION getDirection() { return direction; }
	
	public void snake_push_front(Pair p) { snake.push_front(p); }
	
	public void snake_pop_back() { snake.pop_back(); }
	
	public void scoreIncrease() { score++; }
	
	public void decide(GameContext ctx) {}
}
