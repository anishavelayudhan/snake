package com.snake.view;

import com.snake.model.GameState;
import com.snake.util.GameConstants;
import com.snake.view.theme.QquestTheme;
import com.snake.view.theme.RetroTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class GamePanel extends JPanel {
    private final GameState gameState;
    private Theme theme;
    private final List<Theme> themeList = Arrays.asList(
            new RetroTheme(),
            new QquestTheme()
    );

    private final JLabel scoreLabel = new JLabel("Score: 0");
    private final JLabel highScoreLabel = new JLabel("High Score: ");


    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        this.theme = themeList.get(0);
        initializePanel();
    }

    public void cycleTheme() {
        this.theme = themeList.get((themeList.indexOf(theme) + 1) % themeList.size());
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
        topPanel.setBackground(theme.getPanelColor1());

        topPanel.add(gameState.isPaused() ? createPausedLabel() : createScorePanel(), BorderLayout.CENTER);
        topPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        return topPanel;
    }

    private JLabel createPausedLabel() {
        JLabel label = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        label.setFont(GameConstants.Resources.FONT);
        label.setForeground(theme.getPanelColor2());
        label.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 0));
        return label;
    }

    private JPanel createScorePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.getPanelColor1());

        scoreLabel.setFont(GameConstants.Resources.FONT);
        scoreLabel.setForeground(theme.getPanelColor2());
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(9, 11, 6, 0));

        highScoreLabel.setFont(GameConstants.Resources.FONT);
        highScoreLabel.setForeground(theme.getPanelColor2());
        highScoreLabel.setBorder(BorderFactory.createEmptyBorder(9, 0, 6, 11));

        panel.add(scoreLabel, BorderLayout.WEST);
        panel.add(highScoreLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(theme.getBackgroundColor());
        buttonPanel.add(createHelpButton(), BorderLayout.EAST);
        return buttonPanel;
    }

    // Bottom Panel Components
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.getBackgroundColor());
        panel.add(createCreditsPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCreditsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(theme.getBackgroundColor());

        JLabel label = new JLabel("By Anisha Velayudhan");
        label.setFont(GameConstants.Resources.FONT.deriveFont(Font.PLAIN, GameConstants.FONT_SIZE_CREDITS));
        label.setForeground(theme.getPanelColor1());
        label.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));

        panel.add(label);
        return panel;
    }

    // Method for drawing game grid
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
                g2d.setColor(theme.getBackgroundColor());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }


            private void drawGameGrid(Graphics2D g2d) {
                for (int y = 0; y < GameConstants.GRID_SIZE; y++) {
                    for (int x = 0; x < GameConstants.GRID_SIZE; x++) {
                        Point pos = calculateCellPosition(calculateGridStart(), x, y);
                        drawCell(g2d, x, y, pos.x, pos.y);
                    }
                }
            }

            private Point calculateGridStart() {
                int totalWidth = GameConstants.GRID_CELL_SIZE * GameConstants.GRID_SIZE;
                return new Point(
                        (getWidth() - totalWidth) / 2,
                        70 + (getHeight() - 70 - GameConstants.GRID_CELL_SIZE * GameConstants.GRID_SIZE) / 2
                );
            }

            private Point calculateCellPosition(Point start, int x, int y) {
                return new Point(start.x + x * GameConstants.GRID_CELL_SIZE, start.y + y * GameConstants.GRID_CELL_SIZE);
            }

            private void drawCell(Graphics2D g2d, int x, int y, int xPos, int yPos) {
                if (gameState.getSnake().occupiesPosition(x, y)) {
                    boolean isHead = gameState.getSnake().isHead(x, y);
                    boolean isTail = gameState.getSnake().isTail(x, y);
                    theme.drawSnakeSegment(g2d, xPos, yPos, isHead, isTail,
                            gameState.getSnake().getTailDirection(), gameState.getSnake().getDirection());
                } else if (x == gameState.getApple().getX() && y == gameState.getApple().getY()) {
                    theme.drawApple(g2d, xPos, yPos);
                } else {
                    drawGridCell(g2d, x, y, xPos, yPos);
                }
            }

            private void drawGridCell(Graphics2D g2d, int x, int y, int xPos, int yPos) {
                Color color = (x + y) % 2 == 0 ?
                        theme.getGridColor1() : theme.getGridColor2();
                g2d.setColor(color);
                g2d.fillRect(xPos, yPos, GameConstants.GRID_CELL_SIZE, GameConstants.GRID_CELL_SIZE);
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
