package com.snake.view;

import com.snake.model.Direction;

import java.awt.*;

public interface Theme {
    void drawSnakeSegment(Graphics2D g2d, int xPos, int yPos, boolean isHead, boolean isTail, Direction dirTail, Direction dirHead);
    void drawApple(Graphics2D g2d, int xPos, int yPos);
    Color getSnakeColor();
    Color getAppleColor();
    Color getGridColor1();
    Color getGridColor2();
    Color getBackgroundColor();
    Color getPanelColor1();
    Color getPanelColor2();
}


