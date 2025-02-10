package com.snake.util;

import java.io.*;
import java.util.logging.Logger;

public class HighScoreManager {
    private HighScoreManager() {}

    private static final String HIGH_SCORE_FILE = GameConstants.HIGH_SCORE_FILE;

    public static void saveHighScore(int highScore) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            dos.writeInt(highScore);
        } catch (IOException e) {
            Logger.getLogger(HighScoreManager.class.getName()).severe("Error writing to high score file: " + HIGH_SCORE_FILE);
        }
    }

    public static int loadHighScore() {
        return ResourceLoader.loadHighScore(HIGH_SCORE_FILE);
    }
}