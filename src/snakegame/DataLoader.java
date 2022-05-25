package snakegame;

import java.io.*;
import java.text.SimpleDateFormat;


public final class DataLoader {
	public static final String DATA_FILES_PATH = "./snakegame/data";
	public static final String RANKING_FILE_NAME = "rank";
	public static final String SAVE_FILE_NAME = "save";
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss");
	
	static GameContext loadGame() throws Exception {
		File file = new File(DATA_FILES_PATH, SAVE_FILE_NAME);
		if (!file.exists()) throw new Exception("No save file");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int width, height;
		Player[] players; Pair[] apples;
		
		String line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		String[] data = line.split(" ");
		width = Integer.parseInt(data[0]);
		height = Integer.parseInt(data[1]);
		
		line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		int num_player = Integer.parseInt(line);
		players = new Player[num_player];
		for (int i = 0; i < num_player; i++) {
			line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
			data = line.split(" ");
			
			PlayerType ptype;
			if (data[0].equals("PLAYER1")) ptype = PlayerType.PLAYER1; 
			else if (data[0].equals("PLAYER2")) ptype = PlayerType.PLAYER2;
			else ptype = PlayerType.AUTO;
			
			int score = Integer.parseInt(data[1]);
			boolean over = data[2].equals("T");
			
			DIRECTION direction;
			if (data[3].equals("NORTH")) direction = DIRECTION.NORTH;
			else if (data[3].equals("SOUTH")) direction = DIRECTION.SOUTH;
			else if (data[3].equals("EAST")) direction = DIRECTION.EAST;
			else direction = DIRECTION.WEST;
			System.out.println(data[2]);

			Deque snake = new Deque(width * height + 1);
			int len = Integer.parseInt(data[4]);
			for (int n = 0; n < len; n++) {
				int x = Integer.parseInt(data[5+n].split(":")[0]);
				int y = Integer.parseInt(data[5+n].split(":")[1]);
				snake.push_back(new Pair(x, y));
			}
			players[i] = new Player(snake, direction, score, over, ptype);
		}
		
		line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
		int num_apple = Integer.parseInt(line);
		apples = new Pair[num_apple];
		for (int i = 0; i < num_player; i++) {
			line = br.readLine(); if (line.endsWith("\n")) line = line.substring(0, line.length()-1);
			apples[i] = new Pair(Integer.parseInt(line.split(":")[0]), Integer.parseInt(line.split(":")[1]));
		}
			
		br.close();
		return new GameContext(width, height, players, apples);
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
		
		sb.append(ctx.players.length + "\n");
		for (int i = 0; i < ctx.players.length; i++) {
			if (ctx.players[i].getType() == PlayerType.PLAYER1) sb.append("PLAYER1");
			else if (ctx.players[i].getType() == PlayerType.PLAYER2) sb.append("PLAYER2");
			else sb.append("AUTO");
			sb.append(" ");
			
			sb.append(ctx.players[i].getScore() + " ");
			sb.append((ctx.players[i].isGameOver() ? "T" : "F") + " ");
			
			if (ctx.players[i].getDirection() == DIRECTION.NORTH) sb.append("NORTH");
			else if (ctx.players[i].getDirection() == DIRECTION.SOUTH) sb.append("SOUTH");
			else if (ctx.players[i].getDirection() == DIRECTION.EAST) sb.append("EAST");
			else sb.append("WEST");
			sb.append(" ");
			
			Pair[] snake = ctx.players[i].getSnake();
			sb.append(snake.length + " ");
			for (int n = 0; n < snake.length; n++) sb.append(snake[n].x + ":" + snake[n].y + " ");
			sb.append("\n");
		}
		
		sb.append(ctx.apples.length + "\n");
		for (int i = 0; i < ctx.apples.length; i++) {
			sb.append(ctx.apples[i].x + ":" + ctx.apples[i].y + "\n");
		}
		
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
