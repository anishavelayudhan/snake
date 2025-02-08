package com.snake.model;

import com.snake.util.GameConstants;

import java.awt.*;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Snake {
    private final Deque<Segment> body;
    private final Set<Point> occupiedPositions;
    private Direction direction;
    private boolean collided;
    private boolean growNextMove;

    public Snake() {
        this.direction = Direction.RIGHT;
        this.body = new LinkedList<>();
        this.occupiedPositions = new HashSet<>();
        initializeBody();
    }

    private void initializeBody() {
        for (int i = 0; i < GameConstants.SNAKE_INITIAL_SIZE; i++) {
            this.body.addFirst(new Segment(GameConstants.START_POSITION_X + i,
                    GameConstants.START_POSITION_Y, direction));
            occupiedPositions.add(new Point(GameConstants.START_POSITION_X + i, GameConstants.START_POSITION_Y));
        }
    }

    public boolean isMaxLength() {
        return this.body.size() == (GameConstants.GRID_SIZE * GameConstants.GRID_SIZE);
    }

    public boolean occupiesPosition(int x, int y) {
        return occupiedPositions.contains(new Point(x, y));
    }

    public void move() {
        Segment head = getHead();
        int newX = (head.x() + direction.getDeltaX() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        int newY = (head.y() + direction.getDeltaY() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;

        if (occupiedPositions.contains(new Point(newX, newY))) {
            collided = true;
            return;
        }

        body.addFirst(new Segment(newX, newY, direction));
        occupiedPositions.add(new Point(newX, newY));

        if (!growNextMove) {
            Segment tail = body.removeLast();
            occupiedPositions.remove(new Point(tail.x(), tail.y()));
        }

        growNextMove = false;
    }

    public void grow() {
        this.growNextMove = true;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setDirection(Direction newDirection) {
        if (direction.isValidDirection(newDirection)) {
            direction = newDirection;
        }
    }

    // Getters
    public Direction getDirection() { return direction; }
    private Segment getHead() { return body.getFirst(); }
    private Segment getTail() { return body.getLast(); }
    public Direction getTailDirection() {
        return body.size() > 1 ? body.getLast().direction() : direction;
    }
    public int[] getHeadPosition() {
        Segment head = getHead();
        return new int[] {head.x(), head.y()};
    }


    public void reset() {
        body.clear();
        occupiedPositions.clear();
        initializeBody();
        direction = Direction.RIGHT;
        collided = false;
    }

    public boolean isHead(int x, int y) {
        Segment head = getHead();
        return head.x() == x && head.y() == y;
    }

    public boolean isTail(int x, int y) {
        Segment tail = getTail();
        return tail.x() == x && tail.y() == y;
    }

}