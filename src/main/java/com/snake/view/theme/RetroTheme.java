package com.snake.view.theme;

import com.snake.model.Direction;
import com.snake.util.GameConstants;
import com.snake.view.Theme;

import java.awt.*;

public class RetroTheme implements Theme {
    private static final Color SNAKE_COLOR = new Color(39, 58, 19);
    private static final Color APPLE_COLOR = new Color(39, 58, 19);
    private static final Color GRID_COLOR_1 = new Color(136, 173, 78);
    private static final Color GRID_COLOR_2 = new Color(136, 173, 78);
    private static final Color BACKGROUND_COLOR = new Color(155, 196, 92);
    private static final Color PANEL_COLOR_1 = new Color(39, 58, 19);
    private static final Color PANEL_COLOR_2 = new Color(155, 196, 92);
    private static final int CELL_SIZE = GameConstants.GRID_CELL_SIZE;

    @Override
    public void drawSnakeSegment(Graphics2D g2d, int xPos, int yPos, boolean isHead, boolean isTail, Direction dirTail, Direction dirHead) {
        g2d.setColor(getGridColor1());
        g2d.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
        g2d.setColor(getSnakeColor());

        if (isHead) {
            drawRetroSnakeHead(g2d, xPos, yPos);
        } else if (isTail) {
            drawRetroSnakeTail(g2d, xPos, yPos);
        } else {
            g2d.fillRect(xPos + 1, yPos + 1, CELL_SIZE - 2, CELL_SIZE - 2);
        }
    }

    private void drawRetroSnakeHead(Graphics2D g2d, int xPos, int yPos) {
        g2d.setColor(getSnakeColor());
        g2d.fillRect(xPos + 1, yPos + 1, RetroTheme.CELL_SIZE - 2, RetroTheme.CELL_SIZE - 2);
    }

    private void drawRetroSnakeTail(Graphics2D g2d, int xPos, int yPos) {
        g2d.setColor(getAppleColor());
        g2d.fillRect(xPos + 1, yPos + 1, RetroTheme.CELL_SIZE - 2, RetroTheme.CELL_SIZE - 2);
    }

    @Override
    public void drawApple(Graphics2D g2d, int xPos, int yPos) {
        g2d.setColor(getGridColor1());
        g2d.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
        g2d.setColor(getAppleColor());
        int pad = CELL_SIZE / 6;
        int centerX = xPos + CELL_SIZE /2;
        int centerY = yPos + CELL_SIZE /2;

        // Horizontal
        g2d.fillRect(centerX - CELL_SIZE / 2 + pad, centerY - CELL_SIZE / 6, CELL_SIZE - 2 * pad, CELL_SIZE / 3);
        // Vertical
        g2d.fillRect(centerX - CELL_SIZE / 6, centerY - CELL_SIZE /2 + pad, CELL_SIZE / 3, CELL_SIZE - 2 * pad);
    }

    @Override
    public Color getSnakeColor() {
        return SNAKE_COLOR;
    }

    @Override
    public Color getAppleColor() {
        return APPLE_COLOR;
    }

    @Override
    public Color getGridColor1() {
        return GRID_COLOR_1;
    }

    @Override
    public Color getGridColor2() {
        return GRID_COLOR_2;
    }

    @Override
    public Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    @Override
    public Color getPanelColor1() {
        return PANEL_COLOR_1;
    }

    @Override
    public Color getPanelColor2() {
        return PANEL_COLOR_2;
    }
}
