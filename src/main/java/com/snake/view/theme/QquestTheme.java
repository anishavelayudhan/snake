package com.snake.view.theme;

import com.snake.model.Direction;
import com.snake.util.GameConstants;
import com.snake.view.Theme;

import java.awt.*;

public class QquestTheme implements Theme {
    private static final Color SNAKE_COLOR = new Color(55, 192, 241);
    private static final Color APPLE_COLOR = new Color(255, 50, 0);
    private static final Color GRID_COLOR_1 = new Color(16, 41, 72);
    private static final Color GRID_COLOR_2 = new Color(10, 34, 64);
    private static final Color BACKGROUND_COLOR = new Color(9, 30, 56);
    private static final Color PANEL_COLOR_1 = new Color(26, 56, 93);
    private static final Color PANEL_COLOR_2 = new Color(55, 192, 241);
    private static final int CELL_SIZE = GameConstants.GRID_CELL_SIZE;

    @Override
    public void drawSnakeSegment(Graphics2D g2d, int xPos, int yPos, boolean isHead, boolean isTail, Direction dirTail, Direction dirHead) {
        g2d.setColor(getSnakeColor());

        if (isHead) drawSnakeHead(g2d, xPos, yPos, dirHead);
        else if (isTail) drawSnakeTail(g2d, xPos, yPos, dirTail);
        else g2d.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
    }

    private void drawSnakeHead(Graphics2D g2d, int x, int y, Direction dir) {
        Rectangle rect;
        switch (dir) {
            case UP:
                rect = new Rectangle(0, CELL_SIZE/2, CELL_SIZE, CELL_SIZE / 2);
                drawArcWithRect(g2d, x, y, 0, rect);
                break;
            case DOWN:
                rect = new Rectangle(0, 0, CELL_SIZE, CELL_SIZE / 2);
                drawArcWithRect(g2d, x, y, 180, rect);
                break;
            case LEFT:
                rect = new Rectangle(CELL_SIZE / 2, 0, CELL_SIZE / 2, CELL_SIZE);
                drawArcWithRect(g2d, x, y, 90, rect);
                break;
            case RIGHT:
                rect = new Rectangle(0, 0, CELL_SIZE / 2, CELL_SIZE);
                drawArcWithRect(g2d, x, y, -90, rect);
                break;
        }
    }


    private void drawSnakeTail(Graphics2D g2d, int x, int y, Direction dir) {
        Rectangle rect;
        switch (dir) {
            case UP:
                rect = new Rectangle(0, 0, CELL_SIZE, CELL_SIZE / 2);
                drawArcWithRect(g2d, x, y, 180, rect);
                break;
            case DOWN:
                rect = new Rectangle(0, CELL_SIZE/2, CELL_SIZE, CELL_SIZE / 2);
                drawArcWithRect(g2d, x, y, 0, rect);
                break;
            case LEFT:
                rect = new Rectangle(0, 0, CELL_SIZE / 2, CELL_SIZE);
                drawArcWithRect(g2d, x, y, -90, rect);
                break;
            case RIGHT:
                rect = new Rectangle(CELL_SIZE / 2, 0, CELL_SIZE / 2, CELL_SIZE);
                drawArcWithRect(g2d, x, y, 90, rect);
                break;
        }
    }


    private void drawArcWithRect(Graphics2D g2d, int x, int y, int arcStart,
                                 Rectangle rect) {
        g2d.fillArc(x, y, CELL_SIZE, CELL_SIZE, arcStart, 180);
        g2d.fillRect(rect.x + x, rect.y + y, rect.width, rect.height);
    }



    @Override
    public void drawApple(Graphics2D g2d, int xPos, int yPos) {
        g2d.setColor(getAppleColor());
        g2d.fillOval(xPos + 2, yPos + 2, CELL_SIZE - 4, CELL_SIZE - 4);
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
