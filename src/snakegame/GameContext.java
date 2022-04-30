package snakegame;

public class GameContext {
        int width, height;
        Deque snake;
        Pair apple;
        int score;
        DIRECTION direction;
        GameContext(int w, int h, Deque _snake, Pair _apple, int _score, DIRECTION _direction) {
            width = w; height = h; snake = _snake; apple = _apple; score = _score; direction = _direction;
        }
}
