/**
 *
 * @author anishavelayudhan
 */
package com.mycompany.snake;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;
    private final Color snakeColor = new Color(39, 58, 19);
    private final Color appleColor = new Color(39, 58, 19);
    private final Color gridLineColor = new Color(136, 173, 78);
    private final Color backgroundColor = new Color(155, 196, 92);

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(450, 380));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background gradient
        g2d.setPaint(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int borderWidth = 70;
        int borderHeightTop = 2; // Adjust top border height here
        int availableWidth = getWidth() - 2 * borderWidth;
        int availableHeight = getHeight() - 2 * borderHeightTop;
        int cellWidth = availableWidth / game.gridWidth;
        int cellHeight = availableHeight / game.gridHeight;

        // Adjust cellWidth or cellHeight to maintain square cells
        if (cellWidth < cellHeight) {
            cellHeight = cellWidth;
        } else {
            cellWidth = cellHeight;
        }

        // Center the grid within the available space
        int startX = borderWidth + (availableWidth - cellWidth * game.gridWidth) / 2;
        int startY = borderHeightTop + (availableHeight - cellHeight * game.gridHeight);

        // Draw game grid
        for (int y = 0; y < game.gridHeight; y++) {
            for (int x = 0; x < game.gridWidth; x++) {
                int xPos = borderWidth + x * cellWidth;
                int yPos = borderWidth + y * cellHeight;

                // Draw snake
                if (game.snake.onPosition(x, y)) {
                    g2d.setColor(snakeColor);
                    g2d.fillRoundRect(xPos + 1, yPos + 1, cellWidth - 2, cellHeight - 2, 0, 0);
                } else if (x == game.apple.getX() && y == game.apple.getY()) {
                    g2d.setColor(gridLineColor);
                    g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                    g2d.setColor(appleColor);
                    int size = Math.min(cellWidth, cellHeight);
                    int padding = size / 6;
                    int centerX = xPos + cellWidth / 2;
                    int centerY = yPos + cellHeight / 2;

                    // Draw horizontal line
                    g2d.fillRect(centerX - size / 2 + padding, centerY - size / 6, size - 2 * padding, size / 3);
                    // Draw vertical line
                    g2d.fillRect(centerX - size / 6, centerY - size / 2 + padding, size / 3, size - 2 * padding);
                } else {
                    g2d.setColor(gridLineColor);
                    g2d.drawRect(xPos, yPos, cellWidth, cellHeight);
                    g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                }
            }
        }
    }
}