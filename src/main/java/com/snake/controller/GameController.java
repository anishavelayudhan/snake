package com.snake.controller;

import com.snake.model.Direction;
import com.snake.model.GameState;
import com.snake.util.GameConstants;
import com.snake.view.GamePanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;


public class GameController {
    private final Deque<Direction> directionQueue = new LinkedList<>();
    private final GamePanel gamePanel;
    private final GameState gameState;
    private final InputHandler inputHandler;

    public GameController(GamePanel gamePanel, GameState gameState) {
        this.gamePanel = gamePanel;
        this.gameState = gameState;
        this.inputHandler = new InputHandler();
    }

    public void registerInputListeners(Component component) {
        component.addKeyListener(inputHandler);
        component.setFocusable(true);
        component.requestFocusInWindow();

        // Disable focus traversal keys
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(component.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
        component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
    }

    public void processInput() {
        if (!directionQueue.isEmpty())  {
            gameState.getSnake().setDirection(directionQueue.poll());
        }

        gameState.getSnake().move();
        processAppleEating();
    }

    public void processAppleEating() {
        if (Arrays.equals(gameState.getSnake().getHeadPosition(), gameState.getApple().getPosition())) {
            gameState.setScore(gameState.getScore() + GameConstants.SCORE_PER_APPLE);
            gameState.getApple().randomizePosition(gameState.getSnake());
            gameState.getSnake().grow();
            gameState.increaseSpeed();

            gamePanel.updateScore();
        }
    }

    public void resetGame() {
        gameState.reset();
        gamePanel.updateScore();
    }

    private class InputHandler extends KeyAdapter {
        private final Set<Integer> pressedKeys = new HashSet<>();

        @Override
        public void keyPressed(KeyEvent e) {
            if (!pressedKeys.contains(e.getKeyCode())) {
                pressedKeys.add(e.getKeyCode());
                handleKeyPress(e.getKeyCode());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys.remove(e.getKeyCode());
        }

        private void handleKeyPress(int keyCode) {
            switch (keyCode) {
                case GameConstants.Controls.UP -> queueDirection(Direction.UP);
                case GameConstants.Controls.DOWN -> queueDirection(Direction.DOWN);
                case GameConstants.Controls.LEFT -> queueDirection(Direction.LEFT);
                case GameConstants.Controls.RIGHT -> queueDirection(Direction.RIGHT);
                case GameConstants.Controls.RESET -> resetGame();
                case GameConstants.Controls.TAB -> gamePanel.cycleTheme();
                case GameConstants.Controls.SPACE -> togglePause();
                default -> { /* Ignore other keys */ }
            }
        }

        private void togglePause() {
            gameState.setPaused(!gameState.isPaused());
            gamePanel.refreshUI();
        }

        private void queueDirection(Direction newDirection) {
            if (gameState.isPaused()) return;

            if (directionQueue.isEmpty() && gameState.getSnake().getDirection().isValidDirection(newDirection)
                    || !directionQueue.isEmpty() && directionQueue.peekLast().isValidDirection(newDirection)) {
                directionQueue.add(newDirection);
            }
        }
    }
}