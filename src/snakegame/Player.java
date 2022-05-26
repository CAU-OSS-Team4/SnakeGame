package snakegame;

import java.util.Queue;
import java.util.LinkedList;

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
	
	public void decide(GameContext ctx) {
		// TODO: Determine direction in AUTO PLAY.
		// You need to change the variable 'direction'.
		Pair[] snake = getSnake();
		Pair apple = ctx.apples[0];
		int[][] visited = new int[ctx.width][ctx.height];

		for (int i = 0; i < ctx.width; i++) {
			for (int j = 0; j < ctx.height; j++) {
				visited[i][j] = Integer.MAX_VALUE;
			}
		}

		for (Pair pair : snake) {
			visited[pair.x][pair.y] = -1;
		}

		int x;
		int y;
		int distance = 0;
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[] { snake[0].x, snake[0].y, distance });
		visited[snake[0].x][snake[0].y] = distance;

		while (!queue.isEmpty()) {
			int[] temp = queue.poll();
			x = temp[0];
			y = temp[1];
			distance = temp[2];
			System.out.println(distance);

			if (x == apple.x && y == apple.y) {
				for (int value = distance; value >= 0; value--) {
					if (x + 1 < ctx.width && visited[x + 1][y] == distance - 1) {
						direction = DIRECTION.WEST;
						continue;
					}
					if (x - 1 >= 0 && visited[x - 1][y] == distance - 1) {
						direction = DIRECTION.EAST;
						continue;
					}
					if (y + 1 < ctx.height && visited[x][y + 1] == distance - 1) {
						direction = DIRECTION.NORTH;
						continue;
					}
					if (y - 1 >= 0 && visited[x][y - 1] == distance - 1) {
						direction = DIRECTION.SOUTH;
						continue;
					}
				}
				break;
			}

			if (x + 1 < ctx.width && visited[x + 1][y] > distance) {
				queue.offer(new int[] { x + 1, y, distance + 1 });
				visited[x + 1][y] = distance + 1;
			}
			if (x - 1 >= 0 && visited[x - 1][y] > distance) {
				queue.offer(new int[] { x - 1, y, distance + 1 });
				visited[x - 1][y] = distance + 1;
			}
			if (y + 1 < ctx.height && visited[x][y + 1] > distance) {
				queue.offer(new int[] { x, y + 1, distance + 1 });
				visited[x][y + 1] = distance + 1;
			}
			if (y - 1 >= 0 && visited[x][y - 1] > distance) {
				queue.offer(new int[] { x, y - 1, distance + 1 });
				visited[x][y - 1] = distance + 1;
			}
		}
	}
}
