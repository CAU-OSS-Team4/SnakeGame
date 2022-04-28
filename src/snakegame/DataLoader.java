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
	int width, height;
	Deque snake;
	Pair apple;
	int score;
	DIRECTION direction;
	GameContext(int w, int h, Deque _snake, Pair _apple, int _score, DIRECTION _direction) {
		width = w; height = h; snake = _snake; apple = _apple; score = _score; direction = _direction;
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
		
		String[] data = br.readLine().split(" ");
		width = Integer.parseInt(data[0]);
		height = Integer.parseInt(data[1]);
		
		Deque snake = new Deque(width * height + 1);
		
		data = br.readLine().split(" ");
		int N = Integer.parseInt(data[0]);
		for (int n = 1; n <= N; n++) {
			int x = Integer.parseInt(data[n].split(":")[0]);
			int y = Integer.parseInt(data[n].split(":")[1]);
			snake.push_back(new Pair(x, y));
		}
		
		data = br.readLine().split(" ");
		apple = new Pair(Integer.parseInt(data[0].split(":")[0]), Integer.parseInt(data[0].split(":")[1]));
		score = Integer.parseInt(data[1]);
		
		if (data[2] == "NORTH") direction = DIRECTION.NORTH;
		else if (data[2] == "SOUTH") direction = DIRECTION.SOUTH;
		else if (data[2] == "EAST") direction = DIRECTION.EAST;
		else direction = DIRECTION.WEST;
		
		br.close();
		return new GameContext(width, height, snake, apple, score, direction);
	}
	
	static GameContext loadAndRemove() throws Exception {
		GameContext ret = loadGame();
		new File(DATA_FILES_PATH, SAVE_FILE_NAME).delete();
		return ret;
	}
  
	static void saveGame(GameContext ctx) throws Exception {
		File dir = new File(DATA_FILES_PATH);
		if (!dir.exists()) dir.mkdirs();
		
		File file = new File(DATA_FILES_PATH, SAVE_FILE_NAME);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(ctx.score + " ");
		sb.append(ctx.width + "\n");
		
		Pair[] snake = ctx.snake.list();
		
		sb.append(snake.length + " ");
		for (int n = 0; n < snake.length; n++) sb.append(snake[n].x + ":" + snake[n].y + " ");
		sb.append("\n");
		
		sb.append(ctx.apple.x + ":" + ctx.apple.y + " ");
		sb.append(ctx.height + " ");
		
		if (ctx.direction == DIRECTION.NORTH) sb.append("NORTH");
		else if (ctx.direction == DIRECTION.SOUTH) sb.append("SOUTH");
		else if (ctx.direction == DIRECTION.EAST) sb.append("EAST");
		else sb.append("WEST");
		
		bw.write(sb.toString());
		bw.close();
	}
}
