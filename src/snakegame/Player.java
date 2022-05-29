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
		int dist[][] = new int[ctx.width][ctx.height];
		boolean check[][] = new boolean[ctx.width][ctx.height];
		
		for (int w = 0; w < ctx.width; w++) {
			for (int h = 0; h < ctx.height; h++) {
				dist[w][h] = -1;
				check[w][h] = true;
			}
		}
		
		for (int i = 0; i < ctx.players.length; i++) {
			if (ctx.players[i].isGameOver()) continue;
			Pair[] snake = ctx.players[i].getSnake();
			for (int j = 0; j < snake.length; j++) check[snake[j].x][snake[j].y] = false;
		}
		
		Deque q = new Deque(ctx.width * ctx.height + 1);
		for (int i = 0; i < ctx.apples.length; i++) {
			Pair apple = ctx.apples[i];
			dist[apple.x][apple.y] = 0;
			q.push_back(apple);
		}
		
		int dx[] = { 0, 1, 0, -1 };
		int dy[] = { 1, 0, -1, 0 };
		DIRECTION d[] = { DIRECTION.SOUTH, DIRECTION.EAST, DIRECTION.NORTH, DIRECTION.WEST };
		
		while (q.size() > 0) {
			Pair here = q.pop_front();
			for (int i = 0; i < 4; i++) {
				if (here.x + dx[i] < 0 || ctx.width <= here.x + dx[i] || here.y + dy[i] < 0 || ctx.width <= here.y + dy[i]) continue;
				if (dist[here.x + dx[i]][here.y + dy[i]] != -1 || !check[here.x + dx[i]][here.y + dy[i]]) continue;
				dist[here.x + dx[i]][here.y + dy[i]] = dist[here.x][here.y] + 1;
				q.push_back(new Pair(here.x + dx[i], here.y + dy[i]));
			}
		}
		
		int op = ctx.height * ctx.height;
		Pair head = snake.front();
		for (int i = 0; i < 4; i++) {
			if (getDirection() == d[(i+2) % 4]) continue;
			if (head.x + dx[i] < 0 || ctx.width <= head.x + dx[i] || head.y + dy[i] < 0 || ctx.width <= head.y + dy[i]) continue;
			if (dist[head.x + dx[i]][head.y + dy[i]] != -1 && dist[head.x + dx[i]][head.y + dy[i]] < op) {
				op = dist[head.x + dx[i]][head.y + dy[i]];
				setDirection(d[i]);
			}
		}
	}
}
