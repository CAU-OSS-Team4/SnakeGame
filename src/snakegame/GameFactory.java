package snakegame;

public class GameFactory {
	public static Game createSingleMode() {
		int width = 40;
		int height = 40;
		
		Player[] players = new Player[1];
		Deque snake = new Deque(width * height + 1);
		snake.push_front(new Pair(width / 2, height / 2));
		players[0] = new Player(snake, DIRECTION.NORTH, 0, false, PlayerType.PLAYER1);
		
		Pair[] apples = new Pair[1];
		apples[0] = new Pair(-1, -1);
		
		GameContext ctx = new GameContext(width, height, players, apples);
		return new Game(ctx, true);
	}
	public static Game createDualMode() {
		int width = 40;
		int height = 80;
		
		Player[] players = new Player[2];
		Deque snake = new Deque(width * height + 1);
		snake.push_front(new Pair(0, 0));
		players[0] = new Player(snake, DIRECTION.SOUTH, 0, false, PlayerType.PLAYER1);

		snake = new Deque(width * height + 1);
		snake.push_front(new Pair(width - 1, height - 1));
		players[1] = new Player(snake, DIRECTION.NORTH, 0, false, PlayerType.PLAYER2);
		
		Pair[] apples = new Pair[2];
		apples[0] = new Pair(-1, -1);
		apples[1] = new Pair(-1, -1);
		
		GameContext ctx = new GameContext(width, height, players, apples);
		return new Game(ctx, true);
	}
	public static Game createAutoMode() {
		int width = 40;
		int height = 40;
		
		Player[] players = new Player[1];
		Deque snake = new Deque(width * height + 1);
		snake.push_front(new Pair(width / 2, height / 2));
		players[0] = new Player(snake, DIRECTION.NORTH, 0, false, PlayerType.AUTO);

		Pair[] apples = new Pair[1];
		apples[0] = new Pair(-1, -1);
		
		GameContext ctx = new GameContext(width, height, players, apples);
		return new Game(ctx, true);
	}
}
