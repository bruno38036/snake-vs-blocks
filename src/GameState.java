package main;

// Enum pra saber em qual tela a gente tá
public enum GameState {
    MENU,        // tela inicial, vibe
    PLAYING,     // tá jogando, é agora!
    PAUSED,      // pausado (ngl não é muito usado aqui)
    GAME_OVER,   // perdeu, triste :(
    HIGH_SCORES  // mostrando ranking
}
