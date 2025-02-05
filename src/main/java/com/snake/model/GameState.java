package com.snake.model;

import com.snake.util.GameConstants;

import java.io.Serializable;
import java.util.Arrays;

public class GameState implements Serializable {
    // Game objects
    private final transient Snake snake;
    private final transient Apple apple;

    // Core game status
    private boolean paused;
    private boolean gameOver;
    private boolean gameWon;

    // Scoring system
    private int score;
    private int highScore;

    // Speed management
    private int currentSpeed;

    public GameState() {
        this.snake = new Snake();
        this.apple = new Apple();
        this.currentSpeed = GameConstants.INITIAL_GAME_SPEED;
        reset();
    }

    // State management methods
    public void reset() {
        this.paused = false;
        this.gameOver = false;
        this.gameWon = false;
        this.score = 0;
        this.currentSpeed = GameConstants.INITIAL_GAME_SPEED;
    }


    // Getters/Setters with validation
    public void setScore(int score) {
        this.score = Math.max(score, 0);
        if (this.score > highScore) {
            highScore = this.score;
        }
    }


    public void increaseSpeed() {
        this.currentSpeed = Math.max(
                currentSpeed - GameConstants.SPEED_INCREMENT,
                GameConstants.MIN_GAME_SPEED
        );
    }

    public void updateGameState() {
        snake.move();

        if (snake.isCollided()) {
            setGameOver(true);
            return;
        }

        if (snake.isMaxLength()) {
            setGameWon(true);
        }

    }

    public void handleAppleEating() {
        setScore(score + GameConstants.SCORE_PER_APPLE);
        apple.randomizePosition(snake);
        snake.grow();
        increaseSpeed();
    }

    // Standard getters
    public Snake getSnake() { return snake; }
    public Apple getApple() { return apple; }
    public boolean isPaused() { return paused; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }
    public boolean isGameActive() { return gameOver || gameWon; }
    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public int getCurrentSpeed() { return currentSpeed; }
    public boolean isEatingApple() {
        return Arrays.equals(snake.getHeadPosition(), apple.getPosition());
    }

    // Controlled setters
    public void setPaused(boolean paused) {
        if (!gameOver) this.paused = paused;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if (gameOver) this.paused = true;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
        if (gameWon) this.paused = true;
    }

}