package snakegame;

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
        	
        	game.progress();
        	if (game.isGameOver()) {
        		System.out.println("game over!");
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
