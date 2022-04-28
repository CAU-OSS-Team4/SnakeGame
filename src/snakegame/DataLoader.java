package snakegame;

import java.io.*;
import java.util.*;

class RankingTableRow {
	String username;
	int score;
	Date date;
	public RankingTableRow(String u, int s, Date d) {
		username = u; score = s; date = d;
	}
}

class GameContext {
	Deque snake;
	Pair apple;
	int score;
	int width, height;
	DIRECTION direction;
	GameContext(Deque _snake, Pair _apple, int _score, int w, int h, DIRECTION _direction) {
		snake = _snake; apple = _apple; score = _score; width = w; height = h; direction = _direction;
	}
}

public final class DataLoader {
	public static final String DATA_FILES_PATH = "/snakegame/data";
	public static final String RANKING_FILE_NAME = "rank";
	public static final String SAVE_FILE_NAME = "save";
	
	static GameContext loadGame() throws Exception {
		File file = new File(DATA_FILES_PATH, SAVE_FILE_NAME);
		if (!file.exists()) throw new Exception("No save file");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		Pair apple; int score, width, height; DIRECTION direction;
		Deque snake = new Deque(40 * 40 + 1);
		
		String[] data = br.readLine().split(" ");
		int N = Integer.parseInt(data[0]);
		
		data = br.readLine().split(" ");
		for (int n = 0; n < N; n++) {
			int x = Integer.parseInt(data[n].split(":")[0]);
			int y = Integer.parseInt(data[n].split(":")[1]);
			snake.push_back(new Pair(x, y));
		}
		
		data = br.readLine().split(" ");
		apple = new Pair(Integer.parseInt(data[0].split(":")[0]), Integer.parseInt(data[0].split(":")[1]));
		
		score = Integer.parseInt(data[1]);
		width = Integer.parseInt(data[2]);
		height = Integer.parseInt(data[3]);
		
		if (data[4] == "NORTH") direction = DIRECTION.NORTH;
		else if (data[4] == "SOUTH") direction = DIRECTION.SOUTH;
		else if (data[4] == "EAST") direction = DIRECTION.EAST;
		else direction = DIRECTION.WEST;
		
		br.close();
		return new GameContext(snake, apple, score, width, height, direction);
	}
  
	static void saveGame(GameContext ctx) throws Exception {
		File dir = new File(DATA_FILES_PATH);
		if (!dir.exists()) dir.mkdirs();
		
		File file = new File(DATA_FILES_PATH, SAVE_FILE_NAME);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		StringBuilder sb = new StringBuilder();
		Pair[] snake = ctx.snake.list();
		
		sb.append(snake.length + "\n");
		for (int n = 0; n < snake.length; n++) sb.append(snake[n].x + ":" + snake[n].y + " ");
		sb.append("\n");
		
		sb.append(ctx.apple.x + ":" + ctx.apple.y + " ");
		sb.append(ctx.score + " ");
		sb.append(ctx.width + " ");
		sb.append(ctx.height + " ");
		
		if (ctx.direction == DIRECTION.NORTH) sb.append("NORTH");
		if (ctx.direction == DIRECTION.SOUTH) sb.append("SOUTH");
		if (ctx.direction == DIRECTION.EAST) sb.append("EAST");
		else sb.append("WEST");
		
		bw.write(sb.toString());
		bw.close();
	}
}
