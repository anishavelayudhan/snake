package com.snake.model;

import com.snake.util.GameConstants;
import com.snake.util.HighScoreManager;

public class GameState {
    // Game objects
    private Snake snake;
    private Apple apple;

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
        this.highScore = HighScoreManager.loadHighScore();
        this.currentSpeed = GameConstants.INITIAL_GAME_SPEED;
    }

    // State management methods
    public void reset() {
        this.paused = false;
        this.gameOver = false;
        this.gameWon = false;
        this.score = 0;
        this.currentSpeed = GameConstants.INITIAL_GAME_SPEED;

        this.snake = new Snake();
        this.apple = new Apple();
    }

    // Getters/Setters with validation
    public void setScore(int score) {
        this.score = Math.max(score, 0);
        if (this.score > highScore) {
            highScore = this.score;
            HighScoreManager.saveHighScore(highScore);
        }
    }

    public void increaseSpeed() {
        this.currentSpeed = Math.max(
                currentSpeed - GameConstants.SPEED_INCREMENT,
                GameConstants.MIN_GAME_SPEED
        );
    }

    public boolean isGameOver() {
        if (snake.isCollided()) {
            setGameOver(true);
            return true;
        }

        if (snake.isMaxLength()) {
            setGameWon(true);
            return true;
        }

        return false;
    }


    // Standard getters
    public Snake getSnake() {
        return snake;
    }

    public Apple getApple() {
        return apple;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
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