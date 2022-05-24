package snakegame;

enum DIRECTION { NORTH, SOUTH, EAST, WEST }
enum PlayerType { PLAYER1, PLAYER2, AUTO } 

public class Player {
	private int score;
	private boolean is_game_over;
	
	private DIRECTION direction;
	private Deque snake;
	private PlayerType ptype;
	
	public Player(Deque _snake, DIRECTION _direction, int _score, boolean over, PlayerType t) {
		snake = _snake; direction = _direction; score = _score; is_game_over = over; ptype = t;
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
	
	public PlayerType getType() { return ptype; }
	
	public void decide(GameContext ctx) {}
}
