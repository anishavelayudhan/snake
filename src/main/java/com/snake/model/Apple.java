package com.snake.model;

import com.snake.util.GameConstants;

import java.util.Random;

public class Apple {
    private int x;
    private int y;
    private static final Random RANDOM = new Random();
    
    public Apple() {
        randomizePosition(null);
    }

    public void randomizePosition(Snake snake) {
        int maxAttempts = GameConstants.GRID_SIZE * GameConstants.GRID_SIZE;
        int attempts = 0;

        do {
            this.x = RANDOM.nextInt(GameConstants.GRID_SIZE);
            this.y = RANDOM.nextInt(GameConstants.GRID_SIZE);
            attempts++;
        } while (snake != null &&
                snake.occupiesPosition(x, y) &&
                attempts < maxAttempts);
    }

    public int[] getPosition() { return new int[] { this.x, this.y }; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}
