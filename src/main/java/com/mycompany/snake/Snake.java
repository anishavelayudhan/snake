/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;
import java.util.ArrayList;


/**
 *
 * @author anishavelayudhan
 */
public class Snake {
    ArrayList<Segment> body;
    char direction;
    int maxX;
    int maxY;
    int startX = 7;
    int startY = 5;
    boolean checkCollision;


    public Snake(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.direction = 'R';
        this.body = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.body.add(new Segment(startX + i, startY, direction));
        }

    }

    // check if snake is the size of game grid
    public boolean snakeMax() {
        return this.body.size() == (maxX * maxY);
    }

    // check location of snake
    public boolean onPosition (int x, int y) {
        for (Segment segment : this.body) {
            if (segment.x() == x && segment.y() == y) {
                return true;
            }
        }
        return false;
    }

    // moves snake
    public boolean move(int x, int y) {
        int newX = body.get(this.body.size() - 1).x() + deltaX();
        int newY = body.get(this.body.size() - 1).y() + deltaY();

        newX = (newX + maxX) % maxX;
        newY = (newY + maxY) % maxY;

        if (!checkCollision(newX, newY)) {
            body.add( new Segment(newX,newY, direction));

            if (newX == x && newY == y) {
                return true;
            } body.remove(0);
        } return false;
    }


    // check if snake is colliding with itself
    private boolean checkCollision(int x, int y) {
        for (Segment segment : this.body) {
            if (segment.x() == x && segment.y() == y) return this.checkCollision = true;
        }
        return this.checkCollision = false;
    }


    private int deltaX() {
        if (direction == 'L'){
            return - 1;
        }
        if (direction == 'R'){
            return + 1;
        }
        return 0;
    }

    private int deltaY() {
        if (direction == 'U'){
            return - 1;
        }
        if (direction == 'D'){
            return + 1;
        }
        return 0;
    }

    public boolean isHead(int x, int y) {
        Segment head = body.get(body.size() - 1);
        return head.x() == x && head.y() == y;
    }

    public boolean isTail(int x, int y) {
        Segment tail = body.get(0);
        return tail.x() == x && tail.y() == y;
    }

    public char getTailDirection() {
        Segment tail = body.get(1);
        return tail.direction();
    }

    // Method to reset the snake's position and direction
    public void reset() {
        this.body.clear();
        for (int i = 0; i < 3; i++) {
            this.body.add(new Segment(startX + i, startY, direction));
        }
        this.direction = 'R';
        this.checkCollision = false;
    }

}
