/**
 *
 * @author anishavelayudhan
 */
package com.mycompany.snake;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;
    private final Color snakeColor = new Color(39,58,19);
    private final Color appleColor = new Color(39,58,19);
    private final Color gridLineColor = new Color(150,190,90,250);
    private final Color backgroundColor = new Color(155,196,92,255);

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(450, 450));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background gradient
        g2d.setPaint(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cellWidth = getWidth() / game.gridWidth;
        int cellHeight = getHeight() / game.gridHeight;

        // Draw game grid
        for (int y = 0; y < game.gridHeight; y++) {
            for (int x = 0; x < game.gridWidth; x++) {
                int xPos = x * cellWidth;
                int yPos = y * cellHeight;

                // Draw snake
                if (game.snake.onPosition(x, y)) {
                    g2d.setColor(snakeColor);
                    g2d.fillRoundRect(xPos, yPos, cellWidth, cellHeight, 15, 15);
                }
                // Draw apple
                else if (x == game.apple.getX() && y == game.apple.getY()) {
                    g2d.setColor(appleColor);
                    int size = Math.min(cellWidth, cellHeight);
                    int padding = size / 6;
                    int centerX = xPos + cellWidth / 2;
                    int centerY = yPos + cellHeight / 2;

                    // Draw horizontal line
                    g2d.fillRect(centerX - size / 2 + padding, centerY - size / 6, size - 2 * padding, size / 3);
                    // Draw vertical line
                    g2d.fillRect(centerX - size / 6, centerY - size / 2 + padding, size / 3, size - 2 * padding);
                }
                // Draw grid lines
                else {
                    g2d.setColor(gridLineColor);
                    g2d.drawRect(xPos, yPos, cellWidth, cellHeight);
                }
            }
        }
    }
}