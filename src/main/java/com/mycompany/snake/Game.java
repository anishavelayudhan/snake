/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.snake;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author anishavelayudhan
 */
public class Game {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int gridHeight = 17;
    int gridWidth = 17;
    private final JFrame frame;
    GamePanel gamePanel;
    Snake snake;
    Apple apple;
    private int score;
    private int highScore;
    private int index = 1;
    boolean paused = false;
    private int speed = 250000;
    private int delay = 250000;
    JLabel scoreText = new JLabel("Score: " + score);

    Deque<Character> queue = new LinkedList<>();


    // Input
    public Game() {
        snake = new Snake(gridWidth, gridHeight);
        apple = new Apple(gridWidth, gridHeight);
        apple.randomizePos(snake);

        gamePanel = new GamePanel(this);
        gamePanel.setTheme(index);
        frame = new JFrame("Snake");
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/snake-icon.png"))).getImage());

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(gamePanel, BorderLayout.CENTER);  // Add the GamePanel to the JFrame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setResizable(false);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not needed for this case, but necessary to have
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key presses and update snake direction
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> {
                            if (!paused) {
                                if (queue.isEmpty()) {
                                    if (snake.direction != 'D' && snake.direction != 'U') {
                                        queue.add('U');
                                    }
                                } else if (queue.peekLast() != 'D' && queue.peekLast() != 'U') {
                                    queue.add('U');
                                }
                            }
                        }
                        case KeyEvent.VK_DOWN -> {
                            if (!paused) {
                                if (queue.isEmpty()) {
                                    if (snake.direction != 'U' && snake.direction != 'D') {
                                        queue.add('D');
                                    }
                                } else if (queue.peekLast() != 'U' && queue.peekLast() != 'D') {
                                    queue.add('D');
                                }
                            }
                        }
                        case KeyEvent.VK_LEFT -> {
                            if (!paused) {
                                if (queue.isEmpty()) {
                                    if (snake.direction != 'R' && snake.direction != 'L') {
                                        queue.add('L');
                                    }
                                } else if (queue.peekLast() != 'R' && queue.peekLast() != 'L') {
                                    queue.add('L');
                                }
                            }
                        }
                        case KeyEvent.VK_RIGHT -> {
                            if (!paused) {
                                if (queue.isEmpty()) {
                                    if (snake.direction != 'L' && snake.direction != 'R') {
                                        queue.add('R');
                                    }
                                } else if (queue.peekLast() != 'L' && queue.peekLast() != 'R') {
                                    queue.add('R');
                                }
                            }
                        }
                        case KeyEvent.VK_SPACE -> {
                            index = (index + 1) % 2;
                            gamePanel.setTheme(index);
                        }
                        case KeyEvent.VK_ESCAPE -> {
                            paused = !paused;
                            gamePanel.repaint();
                            if (paused) {
                                scheduler.shutdown();
                                gamePanel.repaint();
                            } else {
                                scheduler = Executors.newScheduledThreadPool(1);
                                start();
                                gamePanel.revalidate();
                                gamePanel.updateScore(score);
                            }
                            gamePanel.recreatePanelsWithTheme();
                        }
                    }
                }


            @Override
            public void keyReleased(KeyEvent e) {
                // Not needed for this case, but necessary to have
            }
        });

        start();
    }

    private void scoreGame() {
        score += 100;
        gamePanel.updateScore(score);
    }

    private void restartGame() {
        // Reset snake and apple positions
        scoreRegister();
        score = 0;
        scheduler.shutdown();
        snake.reset();
        apple.randomizePos(snake);
        gamePanel.updateScore(score);
        gamePanel.updateHighScore(getHighScore());
        speed = 250000;
        delay = 250000;
        // Restart game timer
        scheduler = Executors.newScheduledThreadPool(1);
        start();
    }

    private void start() {
        final Runnable startGame = () -> {
            if (!snake.checkCollision && !snake.snakeMax()) {
                if (!queue.isEmpty()) snake.direction = queue.poll();
                if (snake.move(apple.getX(), apple.getY())) {
                    apple.randomizePos(snake);
                    scoreGame();
                    increaseSpeed();
                }
                gamePanel.repaint();
                scoreText.setText("Score: " + score);
            } else {
                if (snake.checkCollision) {
                    int choice = JOptionPane.showConfirmDialog(frame,"Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        restartGame();
                    } else {
                        System.exit(0);
                    }
                } else if (snake.snakeMax()) {
                    int choice = JOptionPane.showConfirmDialog(frame, """
                            CONGRATULATIONS! YOU WON THE GAME!

                            Do you want to restart?""", "Congratulations", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        restartGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        };

        scheduler.scheduleAtFixedRate(startGame, delay, speed, MICROSECONDS);
    }

    public void scoreRegister() {
        if (score > highScore) {
            highScore = score;
            gamePanel.updateHighScore(highScore);
        }
    }


    public int getHighScore() {
        return highScore;
    }


    public void increaseSpeed() {
        this.speed = speed - 450;
        this.delay = speed;
        scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        start();
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Game::new);
    }

}

