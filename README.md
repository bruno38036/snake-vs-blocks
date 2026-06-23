# SCC0504 — Substitute Project: Snake vs. Blocks

**Technology:** Java Swing desktop application  
<<<<<<< HEAD
**Main class:** `br.usp.scc0504.snakeblocks.Main`
=======
**Main class:** `Main`
>>>>>>> 82e0bf6d73c631e446cb72aa492f0234fc4f02e5

This project implements a single-player arcade game inspired by *Snake vs. Blocks* using Java Swing. It was designed to cover the required SCC0504 learning outcomes: game loop design, object-oriented entity hierarchy, collision detection, score management, game states, and local persistence.

## Features implemented

- Swing `JFrame` with a custom-painted `GamePanel`.
- Main menu with **New Game**, **High Scores**, and **Exit**.
- Automatic upward snake movement with left/right arrow steering.
- Falling numbered blocks with random values from 1 to 20.
- Collision between snake head and blocks decreases the block value by 1.
- Destroyed blocks award points equal to their original value.
- Blocks reaching the bottom remove one snake segment.
- Food items fall between blocks and increase snake length when eaten.
- Score and snake length displayed on screen.
- Difficulty increases every 200 points.
- Game Over screen with final score, restart and exit options.
- Top 5 high scores saved to a local file.
- OOP entity hierarchy with `FallingObject` as an abstract superclass, extended by `Block` and `FoodItem`.

## Project structure

```text
snake-vs-blocks/
├── README.md
<<<<<<< HEAD
├── src/main/java/br/usp/scc0504/snakeblocks/
=======
├── src/
>>>>>>> 82e0bf6d73c631e446cb72aa492f0234fc4f02e5
│   ├── Main.java
│   ├── GameFrame.java
│   ├── GamePanel.java
│   ├── MenuPanel.java
│   ├── HighScoresPanel.java
│   ├── GameOverPanel.java
│   ├── GameState.java
│   ├── Snake.java
│   ├── Segment.java
│   ├── FallingObject.java
│   ├── Block.java
│   ├── FoodItem.java
│   ├── HighScoreManager.java
│   └── GameConfig.java
└── snake_vs_blocks_highscores.txt  (created automatically when playing)
```

## How to compile and run

From the project root:

```bash
<<<<<<< HEAD
javac -d out src/main/java/br/usp/scc0504/snakeblocks/*.java
java -cp out br.usp.scc0504.snakeblocks.Main
=======
javac -d out src/*.java
java Main
>>>>>>> 82e0bf6d73c631e446cb72aa492f0234fc4f02e5
```

## Controls

- **Left Arrow:** move snake left
- **Right Arrow:** move snake right
- **P:** pause/resume
- **ESC:** return to main menu

## Notes

The game saves high scores in the local file `snake_vs_blocks_highscores.txt`, created in the same directory where the program is executed.
