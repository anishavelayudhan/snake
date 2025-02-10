package com.snake.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public final class GameConstants {
    private static final ResourceLoader resourceLoader = new ResourceLoader();

    private GameConstants () {}

    // Game Grid Configuration
    public static final int GRID_SIZE = 17;
    public static final int GRID_CELL_SIZE = 20;
    public static final int WINDOW_WIDTH = 550;
    public static final int WINDOW_HEIGHT = 600;

    // Snake configuration
    public static final int SNAKE_INITIAL_SIZE = 3;
    public static final int START_POSITION_X = 7;
    public static final int START_POSITION_Y = 5;

    // Font Size
    public static final int FONT_SIZE = 16;
    public static final int FONT_SIZE_CREDITS = 13;

    // Game Timing (in milliseconds)
    public static final int INITIAL_GAME_SPEED = 250;
    public static final int SPEED_INCREMENT = 2;
    public static final int MIN_GAME_SPEED = 50;

    // Scoring System
    public static final int SCORE_PER_APPLE = 100;
    public static final String HIGH_SCORE_FILE = "highscore.dat";

    // Game Controls
    public static final class Controls {
        private Controls () {}
        public static final int UP = KeyEvent.VK_UP;
        public static final int DOWN = KeyEvent.VK_DOWN;
        public static final int LEFT = KeyEvent.VK_LEFT;
        public static final int RIGHT = KeyEvent.VK_RIGHT;
        public static final int RESET = KeyEvent.VK_R;
        public static final int TAB = KeyEvent.VK_TAB;
        public static final int SPACE = KeyEvent.VK_SPACE;
    }

    // Resource Management
    public static final class Resources {
        private Resources () {}
        public static final Image SNAKE_ICON = resourceLoader.loadIcon();
        public static final ImageIcon HELP_ICON = resourceLoader.loadButtonIcon();
        public static final Font FONT = resourceLoader.loadFont();
    }

}