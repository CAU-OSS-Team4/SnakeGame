package snakegame;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

class RankingTableRow implements Comparable {
	String username;
	int score;
	Date date;
	public RankingTableRow(String u, int s, Date d) {
		username = u; score = s; date = d;
	}
	
	@Override
	public int compareTo(Object o) {
		RankingTableRow row = (RankingTableRow)o;
		if (score != row.score) return -(((Integer)score).compareTo(row.score));
		if (date.compareTo(row.date) != 0) return date.compareTo(row.date);
		return username.compareTo(row.username);
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
	public static final String DATA_FILES_PATH = "./snakegame/data";
	public static final String RANKING_FILE_NAME = "rank";
	public static final String SAVE_FILE_NAME = "save";
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss");
	
	static GameContext loadGame() throws Exception {
		File file = new File(DATA_FILES_PATH, SAVE_FILE_NAME);
		if (!file.exists()) throw new Exception("No save file");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		Pair apple; int score, width, height; DIRECTION direction;
		
		String line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		String[] data = line.split(" ");
		width = Integer.parseInt(data[0]);
		height = Integer.parseInt(data[1]);
		
		Deque snake = new Deque(width * height + 1);

		line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		data = line.split(" ");
		int N = Integer.parseInt(data[0]);
		for (int n = 1; n <= N; n++) {
			int x = Integer.parseInt(data[n].split(":")[0]);
			int y = Integer.parseInt(data[n].split(":")[1]);
			snake.push_back(new Pair(x, y));
		}
		
		line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		data = line.split(" ");
		apple = new Pair(Integer.parseInt(data[0].split(":")[0]), Integer.parseInt(data[0].split(":")[1]));
		score = Integer.parseInt(data[1]);
		
		if (data[2].equals("NORTH")) direction = DIRECTION.NORTH;
		else if (data[2].equals("SOUTH")) direction = DIRECTION.SOUTH;
		else if (data[2].equals("EAST")) direction = DIRECTION.EAST;
		else direction = DIRECTION.WEST;
		System.out.println(data[2]);
		
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
		
		sb.append(ctx.width + " ");
		sb.append(ctx.height + "\n");
		
		Pair[] snake = ctx.snake.list();
		
		sb.append(snake.length + " ");
		for (int n = 0; n < snake.length; n++) sb.append(snake[n].x + ":" + snake[n].y + " ");
		sb.append("\n");
		
		sb.append(ctx.apple.x + ":" + ctx.apple.y + " ");
		sb.append(ctx.score + " ");
		
		if (ctx.direction == DIRECTION.NORTH) sb.append("NORTH");
		else if (ctx.direction == DIRECTION.SOUTH) sb.append("SOUTH");
		else if (ctx.direction == DIRECTION.EAST) sb.append("EAST");
		else sb.append("WEST");
		
		bw.write(sb.toString());
		bw.close();
	}
	
	static RankingTableRow[] loadRanking() throws Exception {
		File file = new File(DATA_FILES_PATH, RANKING_FILE_NAME);
		if (!file.exists()) return new RankingTableRow[0];

		BufferedReader br = new BufferedReader(new FileReader(file));

		String line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		int N = Integer.parseInt(line);
		
		RankingTableRow[] ret = new RankingTableRow[N];
		for (int n = 0; n < N; n++) {
			line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
			String[] data = line.split(" ");
			ret[n] = new RankingTableRow(data[0], Integer.parseInt(data[1]), format.parse(data[2]));
		}
		br.close();
		return ret;
	}
	
	static int updateScoreboard(RankingTableRow record) throws Exception {
		File dir = new File(DATA_FILES_PATH);
		if (!dir.exists()) dir.mkdirs();
		
		File file = new File(DATA_FILES_PATH, RANKING_FILE_NAME);
		if (!file.exists()) {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("1\n");
			bw.write(record.username + " " + record.score + " " + format.format(record.date) + "\n");
			bw.close();
			return 1;
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();

		String line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		int ret = -1; int N = Integer.parseInt(line);
		sb.append((N+1) + "\n");
		
		for (int n = 1; n <= N; n++) {
			line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
			String[] data = line.split(" ");
			RankingTableRow row = new RankingTableRow(data[0], Integer.parseInt(data[1]), format.parse(data[2]));
			if (ret == -1 && record.compareTo(row) < 0) {
				sb.append(record.username + " " + record.score + " " + format.format(record.date) + "\n");
				ret = n;
			}
			sb.append(line + "\n");
		}
		if (ret == -1) {
			sb.append(record.username + " " + record.score + " " + format.format(record.date) + "\n");
			ret = N+1;
		}
		br.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
		bw.close();
		
		return ret;
	}
}
