package com.snake.view;

import com.snake.model.Direction;
import com.snake.model.GameState;
import com.snake.util.GameConstants;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private final GameState gameState;

    private transient Theme currentTheme;
    private final JLabel scoreLabel = new JLabel("Score: 0");
    private final JLabel highScoreLabel = new JLabel("High Score: ");

    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        this.currentTheme = Theme.getDefaultTheme();
        initializePanel();
    }

    public void cycleTheme() {
        currentTheme = Theme.getNextTheme(currentTheme);
        refreshUI();
    }

    private void initializePanel() {
        setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT));
        setLayout(new BorderLayout());
        add(createGridPanel(), BorderLayout.CENTER);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    public void refreshUI() {
        removeAll();
        add(createGridPanel(), BorderLayout.CENTER);
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    // Top Panel Components
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(currentTheme.getPanelColor1());

        topPanel.add(gameState.isPaused() ? createPausedLabel() : createScorePanel(), BorderLayout.CENTER);
        topPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        return topPanel;
    }

    private JLabel createPausedLabel() {
        JLabel label = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        label.setFont(GameConstants.Resources.FONT);
        label.setForeground(currentTheme.getPanelColor2());
        label.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 0));
        return label;
    }

    private JPanel createScorePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(currentTheme.getPanelColor1());

        scoreLabel.setFont(GameConstants.Resources.FONT);
        scoreLabel.setForeground(currentTheme.getPanelColor2());
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(9, 11, 6, 0));

        highScoreLabel.setFont(GameConstants.Resources.FONT);
        highScoreLabel.setForeground(currentTheme.getPanelColor2());
        highScoreLabel.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 11));

        panel.add(scoreLabel, BorderLayout.WEST);
        panel.add(highScoreLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(currentTheme.getBackgroundColor());
        buttonPanel.add(createHelpButton(), BorderLayout.EAST);
        return buttonPanel;
    }

    // Bottom Panel Components
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(currentTheme.getBackgroundColor());
        panel.add(createCreditsPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCreditsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(currentTheme.getBackgroundColor());

        JLabel label = new JLabel("By Anisha Velayudhan");
        label.setFont(GameConstants.Resources.FONT.deriveFont(Font.PLAIN, GameConstants.FONT_SIZE_CREDITS));
        label.setForeground(currentTheme.getPanelColor1());
        label.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));

        panel.add(label);
        return panel;
    }

    // Game Grid Components
    private JPanel createGridPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                configureRendering(g2d);
                drawGameGrid(g2d);
            }

            private void configureRendering(Graphics2D g2d) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(currentTheme.getBackgroundColor());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            private void drawGameGrid(Graphics2D g2d) {
                int cellSize = calculateCellSize();
                Point gridStart = calculateGridStart(cellSize);

                for (int y = 0; y < GameConstants.GRID_SIZE; y++) {
                    for (int x = 0; x < GameConstants.GRID_SIZE; x++) {
                        Point pos = calculateCellPosition(gridStart, x, y, cellSize);
                        drawCell(g2d, x, y, pos.x, pos.y, cellSize);
                    }
                }
            }

            private int calculateCellSize() {
                int availableWidth = getWidth() - 180;
                int availableHeight = getHeight() - 70;
                return Math.min(availableWidth / GameConstants.GRID_SIZE,
                        availableHeight / GameConstants.GRID_SIZE);
            }

            private Point calculateGridStart(int cellSize) {
                int totalWidth = cellSize * GameConstants.GRID_SIZE;
                return new Point(
                        (getWidth() - totalWidth) / 2,
                        70 + (getHeight() - 70 - cellSize * GameConstants.GRID_SIZE) / 2
                );
            }

            private Point calculateCellPosition(Point start, int x, int y, int size) {
                return new Point(start.x + x * size, start.y + y * size);
            }

            private void drawCell(Graphics2D g2d, int x, int y, int xPos, int yPos, int size) {
                if (gameState.getSnake().occupiesPosition(x, y)) {
                    drawSnakeSegment(g2d, x, y, xPos, yPos, size);
                } else if (x == gameState.getApple().getX() && y == gameState.getApple().getY()) {
                    drawApple(g2d, xPos, yPos, size);
                } else {
                    drawGridCell(g2d, x, y, xPos, yPos, size);
                }
            }

            private void drawSnakeSegment(Graphics2D g2d, int x, int y, int xPos, int yPos, int size) {
                Color gridColor = (x + y) % 2 == 0 ?
                        currentTheme.getGridColor1() : currentTheme.getGridColor2();
                g2d.setColor(gridColor);
                g2d.fillRect(xPos, yPos, size, size);

                g2d.setColor(currentTheme.getSnakeColor());
                if (currentTheme.getName().equals("Retro")) {
                    drawRetroSnakeSegment(g2d, xPos, yPos, size);
                } else {
                    drawStandardSnakeSegment(g2d, x, y, xPos, yPos, size);
                }
            }

            private void drawStandardSnakeSegment(Graphics2D g2d, int x, int y,
                                                  int xPos, int yPos, int size) {
                if (gameState.getSnake().isHead(x, y)) drawHead(g2d, xPos, yPos, size);
                else if (gameState.getSnake().isTail(x, y)) drawTail(g2d, xPos, yPos, size);
                else g2d.fillRect(xPos, yPos, size, size);
            }

            private void drawRetroSnakeSegment(Graphics2D g2d, int xPos, int yPos, int size) {
                g2d.fillRect(xPos + 1, yPos + 1, size - 2, size - 2);
            }

            private void drawHead(Graphics2D g2d, int x, int y, int size) {
                switch (gameState.getSnake().getDirection()) {
                    case UP -> drawArcWithRect(g2d, x, y, size, 0, 0, size/2, size, size/2);
                    case DOWN -> drawArcWithRect(g2d, x, y, size, 180, 0, 0, size, size/2);
                    case LEFT -> drawArcWithRect(g2d, x, y, size, 90, size/2, 0, size/2, size);
                    case RIGHT -> drawArcWithRect(g2d, x, y, size, -90, 0, 0, size/2, size);
                }
            }

            private void drawTail(Graphics2D g2d, int x, int y, int size) {
                Direction dir = gameState.getSnake().getTailDirection();
                switch (dir) {
                    case UP -> drawArcWithRect(g2d, x, y, size, 180, 0, 0, size, size/2);
                    case DOWN -> drawArcWithRect(g2d, x, y, size, 0, 0, size/2, size, size/2);
                    case LEFT -> drawArcWithRect(g2d, x, y, size, -90, 0, 0, size/2, size);
                    case RIGHT -> drawArcWithRect(g2d, x, y, size, 90, size/2, 0, size/2, size);
                }
            }

            private void drawArcWithRect(Graphics2D g2d, int x, int y, int size,
                                         int arcStart,
                                         int rectX, int rectY, int rectW, int rectH) {
                g2d.fillArc(x, y, size, size, arcStart, 180);
                g2d.fillRect(x + rectX, y + rectY, rectW, rectH);
            }

            private void drawApple(Graphics2D g2d, int xPos, int yPos, int size) {
                g2d.setColor(currentTheme.getGridColor1());
                g2d.fillRect(xPos, yPos, size, size);
                g2d.setColor(currentTheme.getAppleColor());

                if (currentTheme.getName().equals("Retro")) {
                    drawRetroApple(g2d, xPos, yPos, size);
                } else {
                    g2d.fillOval(xPos + 2, yPos + 2, size - 4, size - 4);
                }
            }

            private void drawRetroApple(Graphics2D g2d, int xPos, int yPos, int size) {
                int pad = size / 6;
                int centerX = xPos + size/2;
                int centerY = yPos + size/2;

                // Horizontal
                g2d.fillRect(centerX - size/2 + pad, centerY - size/6, size - 2*pad, size/3);
                // Vertical
                g2d.fillRect(centerX - size/6, centerY - size/2 + pad, size/3, size - 2*pad);
            }

            private void drawGridCell(Graphics2D g2d, int x, int y, int xPos, int yPos, int size) {
                Color color = (x + y) % 2 == 0 ?
                        currentTheme.getGridColor1() : currentTheme.getGridColor2();
                g2d.setColor(color);
                g2d.fillRect(xPos, yPos, size, size);
            }
        };
    }

    private JButton createHelpButton() {
        JButton button = new JButton(GameConstants.Resources.HELP_ICON);
        button.setToolTipText("Controls:\n→←↑↓ Movement\nESC Pause\nSPACE Themes");
        styleButton(button);
        return button;
    }


    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }

    public void updateScore() {
        scoreLabel.setText("Score: " + gameState.getScore());
        if (gameState.getScore() == gameState.getHighScore()) {
            updateHighScore();
        }
    }

    public void updateHighScore() {
        highScoreLabel.setText("High Score: " + gameState.getHighScore());
    }
}