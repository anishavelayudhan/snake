/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

/**
 *
 * @author anishavelayudhan
 */
public class Game {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int gridHeight = 15;
    int gridWidth = 15;
    JFrame frame;
    GamePanel gamePanel;
    Snake snake;
    Apple apple;
    int score;
    int speed = 300;
    int delay = 500;
    JLabel scoreText = new JLabel("Score: " + score);



    // Input
    public Game() {
        snake = new Snake(gridWidth, gridHeight);
        apple = new Apple(gridWidth, gridHeight);
        apple.randomizePos(snake);

        gamePanel = new GamePanel(this);
        JFrame frame = new JFrame("Snake Game");
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.BLACK);
        scoreText.setForeground(Color.gray);
        scorePanel.add(scoreText);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.SOUTH);
        frame.add(scorePanel, BorderLayout.NORTH);
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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (snake.direction != 'D'){
                            snake.direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (snake.direction != 'U'){
                            snake.direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (snake.direction != 'R'){
                            snake.direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (snake.direction != 'L') {
                            snake.direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        scheduler.shutdown();
                        int pause = JOptionPane.showConfirmDialog(frame, "GAME PAUSED" + "\n" + "YES to resume, NO to quit", "GAME PAUSED", JOptionPane.YES_NO_OPTION);
                        if (pause == JOptionPane.YES_OPTION) {
                            scheduler = Executors.newScheduledThreadPool(1);
                            start();
                            break;
                        } else {
                            System.exit(0);
                            break;
                        }
                    case KeyEvent.VK_ESCAPE:
                        scheduler.shutdown();
                        int quit = JOptionPane.showConfirmDialog(frame, "GAME PAUSED" + "\n\n" + "Are you sure that you want to quit?", "GAME PAUSED", JOptionPane.YES_NO_OPTION);
                        if (quit == JOptionPane.YES_OPTION) {
                            System.exit(0);
                            break;
                        } else {
                            scheduler = Executors.newScheduledThreadPool(1);
                            start();
                            break;
                        }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        start();
    }

    private int scoreGame(Snake snake) {
        return score += 100;
    }

    private void restartGame() {
        // Reset snake and apple positions
        scheduler.shutdown();
        snake.reset();
        apple.randomizePos(snake);
        score = 0;
        speed = 300;
        delay = 500;
        // Restart game timer
        scheduler = Executors.newScheduledThreadPool(1);
        start();
    }

    private void start() {
        final Runnable startGame = new Runnable() {
            @Override
            public void run() {
                if (!snake.checkCollision && !snake.snakeMax()) {
                    if (snake.move(apple.getX(), apple.getY())){
                        apple.randomizePos(snake);
                        scoreGame(snake);
                        increaseSpeed();
                    }
                    gamePanel.repaint();
                    scoreText.setText("Score: " + score);
                } else {
                    if (snake.checkCollision) {
                        int choice = JOptionPane.showConfirmDialog(frame, "GAME OVER!" + "\n\n" + "Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            restartGame();
                        } else {
                            System.exit(0);
                        }
                    } else if (snake.snakeMax()) {
                        int choice = JOptionPane.showConfirmDialog(frame, "CONGRATULATIONS! YOU WON THE GAME!" + "\n\n" + "Do you want to restart?", "Congratulations", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            restartGame();
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
        };

        ScheduledFuture<?> timer = scheduler.scheduleAtFixedRate(startGame, delay, speed, MILLISECONDS);
    }

    public void increaseSpeed(){
        this.speed = speed - 1;
        this.delay = speed;
        scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        start();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Game game = new Game();
            }
        });
    }

}

