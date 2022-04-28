package snakegame;

import java.util.Date;
import java.util.Scanner;

public class Test {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Game game = new Game(5, 5);
		while (true) {
			System.out.print("input: ");
			char ch = sc.next().charAt(0);
			if (ch == 'w') game.setDirection(DIRECTION.NORTH);
			else if (ch == 's') game.setDirection(DIRECTION.SOUTH);
			else if (ch == 'd') game.setDirection(DIRECTION.EAST);
			else if (ch == 'a') game.setDirection(DIRECTION.WEST);
			else if (ch == 'S') {
				try {
					DataLoader.saveGame(game.getContext());
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else if (ch == 'L') {
				try {
					game = new Game(DataLoader.loadGame());
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
        	
			if (ch == 'S') continue;
			if (ch != 'L') game.progress();
			
			if (game.isGameOver()) {
				System.out.println("game over!");
				try {
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
				}
				break;
			}
        	
			char[][] display = new char[game.getHeight()][game.getWidth()];
			for (int i = 0; i < game.getHeight(); i++) {
				for (int j = 0; j < game.getWidth(); j++) display[i][j] = '.';
			}
			
			Pair[] snake = game.getSnake();
			display[snake[0].y][snake[0].x] = '@';
			for (int i = 1; i < snake.length; i++) display[snake[i].y][snake[i].x] = '#';
			
			Pair apple = game.getApple();
			display[apple.y][apple.x] = '*';
			
			System.out.printf("score: %d\n", game.getScore());
			
			for (int i = 0; i < game.getHeight(); i++) {
				for (int j = 0; j < game.getWidth(); j++) System.out.print(display[game.getHeight()-i-1][j]);
				System.out.println();
			}
		}
	}
}
