package snakegame;

public class Main {
    public static void main(String[] args) {
        Snake snake = new Snake();
        Apple apple = new Apple();
        MainMenu mainMenu = new MainMenu();
        RankingView rankingView = new RankingView();
        Board board = new Board(snake, apple);
        Controller controller = new Controller(snake, apple, mainMenu, rankingView, board);
    }
}
