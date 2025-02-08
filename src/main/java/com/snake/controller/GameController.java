package com.snake.controller;

import com.snake.model.Direction;
import com.snake.model.GameState;
import com.snake.util.GameConstants;
import com.snake.view.GamePanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
    }

    public void processInput() {
        while (!directionQueue.isEmpty()) {
            Direction nextDirection = directionQueue.peek();
            Direction currentDirection = gameState.getSnake().getDirection();

            if (nextDirection.isValidDirection(currentDirection) && nextDirection != currentDirection) {
                gameState.getSnake().setDirection(nextDirection);
            }

            directionQueue.poll();
        }
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
                case GameConstants.Controls.SPACE -> gamePanel.cycleTheme();
                case GameConstants.Controls.ESC -> togglePause();
                default -> { /* Ignore other keys */ }
            }
        }


        private void togglePause() {
            gameState.setPaused(!gameState.isPaused());
            gamePanel.refreshUI();
        }

        private void queueDirection(Direction newDirection) {
            if (gameState.isPaused()) return;

            Direction currentSnakeDirection = gameState.getSnake().getDirection();
            if (newDirection.isValidDirection(currentSnakeDirection)) {
                directionQueue.add(newDirection);
            }
        }
    }
}