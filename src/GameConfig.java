package main;

public final class GameConfig {
    private GameConfig() {}

    public static final int WIDTH = 520;
    public static final int HEIGHT = 720;
    public static final int TIMER_DELAY_MS = 16;

    public static final int SNAKE_SEGMENT_SIZE = 18;
    public static final int INITIAL_SNAKE_LENGTH = 8;
    public static final int SNAKE_SPEED = 6;
    public static final int SNAKE_X_START = WIDTH / 2;
    public static final int SNAKE_Y_START = HEIGHT - 110;

    public static final int BLOCK_SIZE = 58;
    public static final int FOOD_SIZE = 18;

    public static final double INITIAL_FALL_SPEED = 2.2;
    public static final double SPEED_INCREMENT = 0.45;
    public static final int DIFFICULTY_STEP_SCORE = 200;

    public static final int BLOCK_SPAWN_INTERVAL_FRAMES = 55;
    public static final int FOOD_SPAWN_INTERVAL_FRAMES = 150;

    public static final String HIGH_SCORE_FILE = "snake_vs_blocks_highscores.txt";
}
