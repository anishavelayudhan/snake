/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.snake;
import com.formdev.flatlaf.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;


import static java.util.concurrent.TimeUnit.*;

/**
 *
 * @author anishavelayudhan
 */
public class Game {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int gridHeight = 17;
    int gridWidth = 17;
    JFrame frame;
    GamePanel gamePanel;
    Snake snake;
    Apple apple;
    int score;
    boolean keyPressed;
    boolean paused = false;
    private int speed = 200000;
    private int delay = 200000;
    JLabel scoreText = new JLabel("Score: " + score);

    Deque<Character> queue = new LinkedList<>();
    Font bitmapFont;


    // Input
    public Game() {
        JLabel instructions = new JLabel("Use arrow keys to move and ESC to pause.");
        JLabel credits = new JLabel("By Anisha Velayudhan");
        try {
            // Load font file from resources
            InputStream fontStream = getClass().getResourceAsStream("/nokiafc22.ttf");
            bitmapFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

            // Set the font size as needed
            bitmapFont = bitmapFont.deriveFont(Font.PLAIN, 12);

            // Set the font for the scoreText label
            scoreText.setFont(bitmapFont);
            instructions.setFont(bitmapFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scoreText.setVerticalAlignment(JLabel.BOTTOM);
        scoreText.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        snake = new Snake(gridWidth, gridHeight);
        apple = new Apple(gridWidth, gridHeight);
        apple.randomizePos(snake);

        gamePanel = new GamePanel(this);
        frame = new JFrame("Snake");
        frame.setIconImage(new ImageIcon(getClass().getResource("/snake-icon.png")).getImage());
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(39, 58, 19));
        scoreText.setForeground(new Color(155, 196, 92));
        scorePanel.add(scoreText);

        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBackground(new Color(155, 196, 92));
        credits.setForeground(new Color(119, 154, 68));
        instructions.setForeground(new Color(87, 116, 48));

        // Center align the instructions label
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(155, 196, 92));
        centerPanel.add(instructions);
        instructionsPanel.add(centerPanel, BorderLayout.CENTER);

        // Right align the credits label
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(155, 196, 92));
        rightPanel.add(credits);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        instructionsPanel.add(rightPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(instructionsPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setResizable(false);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key presses and update snake direction
                if (!keyPressed) {
                    keyPressed = true;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (queue.isEmpty()) {
                                if (snake.direction != 'D' && snake.direction != 'U')  {
                                    queue.add('U');
                                }
                            } else if (queue.peekLast() != 'D' && queue.peekLast() != 'U') {
                                queue.add('U');
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (queue.isEmpty()) {
                                if (snake.direction != 'U'  && snake.direction != 'D') {
                                    queue.add('D');
                                }
                            } else if (queue.peekLast() != 'U' && queue.peekLast() != 'D') {
                                queue.add('D');
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (queue.isEmpty()) {
                                if (snake.direction != 'R' && snake.direction != 'L') {
                                    queue.add('L');
                                }
                            } else if (queue.peekLast() != 'R' && queue.peekLast() != 'L') {
                                queue.add('L');
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (queue.isEmpty()) {
                                if (snake.direction != 'L' && snake.direction != 'R') {
                                    queue.add('R');
                                }
                            } else if (queue.peekLast() != 'L'  && queue.peekLast() != 'R') {
                                queue.add('R');
                            }
                            break;
                        case KeyEvent.VK_ESCAPE:
                            paused = !paused;
                            if (paused) {
                                scheduler.shutdown();
                                scoreText.setText("GAME PAUSED");
                            } else {
                                scheduler = Executors.newScheduledThreadPool(1);
                                start();
                                scoreText.setText("Score: " + score);
                            }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed = false;
            }
        });
        start();
    }

    private void scoreGame(Snake snake) {
        score += 100;
    }

    private void restartGame() {
        // Reset snake and apple positions
        scheduler.shutdown();
        snake.reset();
        apple.randomizePos(snake);
        score = 0;
        speed = 200000;
        delay = 500000;
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
                    scoreGame(snake);
                    increaseSpeed();
                }
                gamePanel.repaint();
                scoreText.setText("Score: " + score);
            } else {
                if (snake.checkCollision) {
                    int choice = JOptionPane.showConfirmDialog(frame, """
                            Do you want to restart?""", "Game Over", JOptionPane.YES_NO_OPTION);
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

        ScheduledFuture<?> timer = scheduler.scheduleAtFixedRate(startGame, delay, speed, MICROSECONDS);
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
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
        });
    }

}

