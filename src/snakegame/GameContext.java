package snakegame;

public class GameContext {
        int width, height;
        Player[] players;
        Pair[] apples;
        GameContext(int w, int h, Player[] _players, Pair[] _apples) {
            width = w; height = h; players = _players; apples = _apples;
}
}
