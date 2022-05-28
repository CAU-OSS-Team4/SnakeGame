package snakegame;

import java.util.Date;
import java.util.Scanner;

public class Test {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Game game = GameFactory.createDualMode();
		while (true) {
			GameContext ctx = game.getContext();
			
			System.out.print("input: ");
			char ch = sc.next().charAt(0);
			
			if (ch == 'w') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER1) ctx.players[i].setDirection(DIRECTION.NORTH);
			}
			else if (ch == 's') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER1) ctx.players[i].setDirection(DIRECTION.SOUTH);
			}
			else if (ch == 'd') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER1) ctx.players[i].setDirection(DIRECTION.EAST);
			}
			else if (ch == 'a') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER1) ctx.players[i].setDirection(DIRECTION.WEST);
			}
			
			else if (ch == 'o') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER2) ctx.players[i].setDirection(DIRECTION.NORTH);
			}
			else if (ch == 'l') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER2) ctx.players[i].setDirection(DIRECTION.SOUTH);
			}
			else if (ch == ';') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER2) ctx.players[i].setDirection(DIRECTION.EAST);
			}
			else if (ch == 'k') {
				for (int i = 0; i < ctx.players.length; i++) if (ctx.players[i].getType() == PlayerType.PLAYER2) ctx.players[i].setDirection(DIRECTION.WEST);
			}
			
			else if (ch == 'S') {
				try {
					DataLoader.saveGame(game.getContext());
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else if (ch == 'L') {
				try {
					game = new Game(DataLoader.loadGame(), false);
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
        	
			if (ch == 'S') continue;
			if (ch != 'L') game.progress();
			ctx = game.getContext();
			
			boolean is_game_over = false;
			for (int i = 0; i < ctx.players.length; i++) {
				if (ctx.players[i].isGameOver()) is_game_over = true;
			}
			if (is_game_over) {
				System.out.println("game over!");
				/*try {
					String username = Integer.toString((int)(Math.random() * 1000000), 36);
					int rank = DataLoader.updateScoreboard(new RankingTableRow(username, game.getScore(), new Date()));
					
					RankingTableRow[] ranking = DataLoader.loadRanking();
					System.out.println("=====Scoreboard=====");
					for (int n = 0; n < ranking.length; n++) {
						System.out.println((n+1) + " " + ranking[n].score + " " + ranking[n].username + " " + DataLoader.format.format(ranking[n].date));
					}
					System.out.println("You are ranked " + rank);
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}*/
				break;
			}
        	
			char[][] display = new char[game.getHeight()][game.getWidth()];
			for (int i = 0; i < game.getHeight(); i++) {
				for (int j = 0; j < game.getWidth(); j++) display[i][j] = '.';
			}
			
			for (int i = 0; i < ctx.players.length; i++) {
				Pair[] snake = ctx.players[i].getSnake();
				display[snake[0].y][snake[0].x] = '@';
				for (int n = 1; n < snake.length; n++) display[snake[n].y][snake[n].x] = (char)('0' + i);
			}
			
			for (int i = 0; i < ctx.apples.length; i++) {
				Pair apple = ctx.apples[i];
				display[apple.y][apple.x] = '*';
			}
			
			//System.out.printf("score: %d\n", game.getScore());
			
			for (int i = 0; i < game.getHeight(); i++) {
				for (int j = 0; j < game.getWidth(); j++) System.out.print(display[i][j]);
				System.out.println();
			}
		}
	}
}
