package main;

public final class GameConfig {
    // ngl a gente não precisa instanciar isso, só constantes
    private GameConfig() {}

    // Dimensões da tela, tipo, é basicamente o canvas todo
    public static final int WIDTH = 520;
    public static final int HEIGHT = 720;

    // Delay entre cada frame - 16ms = ~60 FPS, que é smooth demais
    public static final int TIMER_DELAY_MS = 16;

    // Configuração da cobra
    public static final int SNAKE_SEGMENT_SIZE = 18;      // tamanho de cada "pedaço"
    public static final int INITIAL_SNAKE_LENGTH = 8;     // cobra começa com 8 segmentos
    public static final int SNAKE_SPEED = 6;              // velocidade do movimento (pixels por frame)
    public static final int SNAKE_X_START = WIDTH / 2;    // posição x inicial (centro)
    public static final int SNAKE_Y_START = HEIGHT - 110; // posição y (perto do final)

    // Tamanho dos objetos que caem
    public static final int BLOCK_SIZE = 58;    // quadrado dos blocos
    public static final int FOOD_SIZE = 18;     // bolinha de comida

    // Velocidade de queda e dificuldade
    public static final double INITIAL_FALL_SPEED = 2.2;   // qto mais cai rápido
    public static final double SPEED_INCREMENT = 0.45;     // aumenta a cada 200 pontos
    public static final int DIFFICULTY_STEP_SCORE = 200;   // intervalo pra aumentar dif

    // Quando spawnar as coisas (em frames)
    public static final int BLOCK_SPAWN_INTERVAL_FRAMES = 55;  // novo bloco a cada 55 frames
    public static final int FOOD_SPAWN_INTERVAL_FRAMES = 150;  // comida a cada 150 frames

    // Arquivo pra salvar high scores
    public static final String HIGH_SCORE_FILE = "snake_vs_blocks_highscores.txt";
}
