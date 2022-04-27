package snakegame;

public class Main {
    public static void main(String[] args) {
        Snake snake = new Snake();
        Apple apple = new Apple();
        MainMenu mainMenu = new MainMenu();
        Board board = new Board(snake, apple);
        Controller controller = new Controller(snake, apple, mainMenu, board);
    }
}
