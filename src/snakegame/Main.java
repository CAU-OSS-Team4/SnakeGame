package snakegame;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        RankingView rankingView = new RankingView();
        GameBoard board = new GameBoard();
        Controller controller = new Controller(mainMenu, rankingView, board);
    }
}
