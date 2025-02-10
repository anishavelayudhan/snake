package com.snake;

import com.formdev.flatlaf.FlatLightLaf;
import com.snake.controller.GameController;
import com.snake.model.*;
import com.snake.util.GameConstants;
import com.snake.view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeGame {
    private final GameState gameState;
    private final GamePanel gamePanel;
    private final GameController gameController;

    public SnakeGame() {
        this.gameState = new GameState();

        // Initialize UI and controller
        this.gamePanel = new GamePanel(gameState);
        this.gameController = new GameController(gamePanel, gameState);

        // Set up main window
        createMainWindow();
        gameController.registerInputListeners(gamePanel);
        startTimer();
    }

    private void createMainWindow() {
        JFrame frame = new JFrame("Snake");
        frame.setIconImage(GameConstants.Resources.SNAKE_ICON);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startTimer() {
        new Timer(gameState.getCurrentSpeed(), e -> gameLoop()).start();
    }

    private void gameLoop() {
        if (gameState.isPaused() || gameState.isGameOver()) return;

        try {
            gameController.processInput();

            if (gameState.isGameOver()) {
                showEndGameDialog();
            }

            gamePanel.repaint();

        } catch (Exception e) {
            Logger.getLogger(SnakeGame.class.getName())
                    .log(Level.SEVERE, "Error during game loop", e);
        }

    }

    private void showEndGameDialog() {
        SwingUtilities.invokeLater(() -> {
            String message = gameState.isGameWon()
                    ? "CONGRATULATIONS! YOU WON!\nRestart?"
                    : "GAME OVER!\nRestart?";

            int choice = JOptionPane.showConfirmDialog(
                    gamePanel,
                    message,
                    "Game Over",
                    JOptionPane.YES_NO_OPTION
            );

            handleRestartChoice(choice);
        });
    }

    private void handleRestartChoice(int choice) {
        if (choice == JOptionPane.YES_OPTION) {
            gameController.resetGame();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.invokeLater(SnakeGame::new);
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getLogger(SnakeGame.class.getName())
                    .log(Level.SEVERE, "Failed to initialize UI theme", e);
            JOptionPane.showMessageDialog(
                    null,
                    "Error initializing application UI",
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}