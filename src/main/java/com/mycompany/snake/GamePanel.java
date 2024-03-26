/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author anishavelayudhan
 */


public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(450, 450)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / game.gridWidth;
        int cellHeight = getHeight() / game.gridHeight;

        // Draw game grid
        for (int y = 0; y < game.gridHeight; y++) {
            for (int x = 0; x < game.gridWidth; x++) {
                if (game.snake.onPosition(x, y)) {
                    g.setColor(Color.GREEN);
                } else if (x == game.apple.getX() && y == game.apple.getY()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                g.setColor(Color.GRAY);
                g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }
    }
}
