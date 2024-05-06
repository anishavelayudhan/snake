/**
 *
 * @author anishavelayudhan
 */
package com.mycompany.snake;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static java.awt.Font.TRUETYPE_FONT;
import static java.lang.System.*;

public class GamePanel extends JPanel {

    private final Game game;
    private final Color[] snakeColors = {new Color(55, 192, 241), new Color(39, 58, 19)};
    private final Color[] appleColors = {new Color(255, 50, 0), new Color(39, 58, 19)};
    private final Color[] topPanelbgColors = {new Color(16, 41, 72), new Color(39, 58, 19)};
    private final Color[] topPanelTextColors = {new Color(55, 192, 241), new Color(155, 196, 92)};
    private final Color[] bottomPanelTextColors = {new Color(21, 63, 123), new Color(39, 58, 19)};
    private final Color[] gridColor1s = {new Color(16, 41, 72), new Color(136, 173, 78)};
    private final Color[] gridColor2s = {new Color(10, 34, 64), new Color(136, 173, 78)};
    private final Color[] backgroundColors = {new Color(9, 30, 56), new Color(155, 196, 92)};
    JLabel scoreText = new JLabel("Score: 0");
    private final JLabel highScoreLabel = new JLabel("High Score: ");
    private int index;
    private Font font;

    public GamePanel(Game game) {
        this.game = game;
        try {
            loadFont();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(550, 600));
        setLayout(new BorderLayout());
        add(createGridPanel(), BorderLayout.CENTER);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    public void setTheme(int themeIndex) {
        if (themeIndex == 0 || themeIndex == 1) {
            index = themeIndex;
            recreatePanelsWithTheme(); // Recreate panels with the updated theme
        } else {
            err.println("Invalid theme index");
        }
    }

    public void recreatePanelsWithTheme() {
        removeAll(); // Remove all components from the panel
        add(createGridPanel(), BorderLayout.CENTER); // Recreate grid panel
        add(createTopPanel(), BorderLayout.NORTH); // Recreate top panel
        add(createBottomPanel(), BorderLayout.SOUTH); // Recreate bottom panel
        revalidate(); // Revalidate the panel
        repaint(); // Repaint the panel to update its appearance
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(topPanelbgColors[index]);

        if (!game.paused) {
            // Create the score label
            Font scoreFont = font.deriveFont(Font.PLAIN, 16);
            scoreText.setBorder(BorderFactory.createEmptyBorder(9, 11, 6, 0));
            scoreText.setFont(scoreFont);
            scoreText.setForeground(topPanelTextColors[index]);

            // Create the high score label
            highScoreLabel.setText("High Score: " + game.getHighScore());
            highScoreLabel.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 11));
            highScoreLabel.setFont(scoreFont);
            highScoreLabel.setForeground(topPanelTextColors[index]);

            // Create a panel to hold the score and high score labels
            JPanel scorePanel = new JPanel(new BorderLayout());
            scorePanel.setBackground(topPanelbgColors[index]);

            scorePanel.add(scoreText, BorderLayout.WEST);
            scorePanel.add(highScoreLabel, BorderLayout.EAST);
            topPanel.add(scorePanel, BorderLayout.CENTER); // Center align
        } else {
            // Display "GAME PAUSED" text centered
            JLabel gamePausedLabel = new JLabel("GAME PAUSED", SwingConstants.CENTER);
            gamePausedLabel.setFont(font.deriveFont(Font.PLAIN, 16));
            gamePausedLabel.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 0));
            gamePausedLabel.setForeground(topPanelTextColors[index]);
            topPanel.add(gamePausedLabel, BorderLayout.CENTER);
        }

        // Create a panel to hold theme and help buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(backgroundColors[index]);

        // Create help button
        ImageIcon helpIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/snake-helpbutton.png")));
        Image scaledHelpImage = helpIcon.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH);
        ImageIcon scaledHelpIcon = new ImageIcon(scaledHelpImage);
        JButton helpButton = new JButton(scaledHelpIcon);
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setFocusPainted(false);
        helpButton.setToolTipText("Instructions:\nARROW KEYS to move\nESC to pause/play\nSPACE to switch themes");

        buttonPanel.add(helpButton, BorderLayout.EAST);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        return topPanel;
    }

    public void updateScore(int score) {
        scoreText.setText("Score: " + score);
        revalidate();
        repaint();
    }

    public void updateHighScore(int highscore) {
        highScoreLabel.setText("High Score: " + highscore);
        revalidate();
        repaint();
    }

    public final void loadFont() throws IOException, FontFormatException {
        try (InputStream fontStream = getClass().getResourceAsStream("/nokiafc22.ttf")) {
            assert fontStream != null;
            font = Font.createFont(TRUETYPE_FONT, fontStream);
            font = font.deriveFont(Font.PLAIN, 13);
        }

    }

    private JPanel createBottomPanel() {
        JLabel credits = new JLabel("By Anisha Velayudhan");
        credits.setFont(font);
        credits.setForeground(bottomPanelTextColors[index]);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(gridColor1s[index]);

        // Center align the instructions label
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(backgroundColors[index]);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        // Right align the credits label
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(backgroundColors[index]);
        rightPanel.add(credits);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));
        bottomPanel.add(rightPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setPaint(backgroundColors[index]);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int borderWidth = 90;
                int borderHeightTop = 70; // Adjust top border height here
                int availableWidth = getWidth() - 2 * borderWidth;
                int availableHeight = getHeight() - borderHeightTop;
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

                        int xPos = startX + x * cellWidth;
                        int yPos = startY + y * cellHeight;

                        Color gridColor = (x + y) % 2 == 0 ? gridColor1s[index] : gridColor2s[index];
                        // Draw snake
                        if (game.snake.onPosition(x, y)) {
                            g2d.setColor(gridColor);
                            g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                            g2d.setColor(snakeColors[index]);
                            if (index == 1) {
                                g2d.fillRect(xPos + 1, yPos + 1, cellWidth - 2, cellHeight - 2);
                            } else if (index == 0) {
                                if (!game.snake.isHead(x, y) && !game.snake.isTail(x, y)) {
                                    g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                                } else if (game.snake.isHead(x, y)) {
                                    switch (game.snake.direction) {
                                        case 'U' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 0, 180);
                                            g2d.fillRect(xPos, yPos + cellHeight / 2, cellWidth, 1 + cellHeight / 2);
                                        }
                                        case 'D' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 180, 180);
                                            g2d.fillRect(xPos, yPos, cellWidth, 1 + cellHeight / 2);
                                        }
                                        case 'L' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 90, 180);
                                            g2d.fillRect(xPos + cellWidth / 2, yPos, 1 + cellWidth / 2, cellHeight);
                                        }
                                        case 'R' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, -90, 180);
                                            g2d.fillRect(xPos, yPos, 2 + cellWidth / 2, cellHeight);
                                        }
                                        default ->
                                                throw new IllegalStateException("Unexpected value: " + game.snake.direction);
                                    }
                                } else if (game.snake.isTail(x, y)) {
                                    switch (game.snake.getTailDirection()) {
                                        case 'D' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 0, 180);
                                            g2d.fillRect(xPos, yPos + cellHeight / 2, cellWidth, 1 + cellHeight / 2);
                                        }
                                        case 'U' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 180, 180);
                                            g2d.fillRect(xPos, yPos, cellWidth, 1 + cellHeight / 2);
                                        }
                                        case 'R' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, 90, 180);
                                            g2d.fillRect(xPos + cellWidth / 2, yPos, 1 + cellWidth / 2, cellHeight);
                                        }
                                        case 'L' -> {
                                            g2d.fillArc(xPos, yPos, cellWidth, cellHeight, -90, 180);
                                            g2d.fillRect(xPos, yPos, 2 + cellWidth / 2, cellHeight);
                                        }

                                    }
                                }
                            }
                        } // Draw apple
                        else if (x == game.apple.getX() && y == game.apple.getY()) {
                            g2d.setColor(gridColor);
                            g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                            g2d.setColor(appleColors[index]);
                            if (index == 0) {
                                g2d.fillOval(xPos + 2, yPos + 2, cellWidth - 4, cellHeight - 4);
                            } else if (index == 1) {
                                int padding = cellWidth / 6;
                                int centerX = xPos + cellWidth / 2;
                                int centerY = yPos + cellHeight / 2;
                                g2d.fillRect(centerX - cellWidth / 2 + padding, centerY - cellWidth / 6, cellWidth - 2 * padding, cellWidth / 3);
                                // Draw vertical line
                                g2d.fillRect(centerX - cellWidth / 6, centerY - cellWidth / 2 + padding, cellWidth / 3, cellWidth - 2 * padding);
                            }

                        } // Draw checkered grid
                        else {
                            g2d.setColor(gridColor);
                            g2d.drawRect(xPos, yPos, cellWidth, cellHeight);
                            g2d.fillRect(xPos, yPos, cellWidth, cellHeight);
                        }
                    }
                }
            }
        };
        gridPanel.setBackground(backgroundColors[index]);
        return gridPanel;
    }
}