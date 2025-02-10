package com.snake.model;

import com.snake.util.GameConstants;

import java.awt.*;
import java.util.*;

public class Snake {
    private final ArrayList<Segment> body;
    private final Set<Point> occupiedPositions;
    private Direction direction;
    private boolean collided;
    private boolean growNextMove;

    public Snake() {
        this.body = new ArrayList<>();
        this.occupiedPositions = new HashSet<>();
        initializeBody();
    }

    private void initializeBody() {
        setDirection(Direction.RIGHT);
        for (int i = 0; i < GameConstants.SNAKE_INITIAL_SIZE; i++) {
            this.body.add(new Segment(GameConstants.START_POSITION_X + i,
                    GameConstants.START_POSITION_Y, direction));
            occupiedPositions.add(new Point(GameConstants.START_POSITION_X + i, GameConstants.START_POSITION_Y));
        }
    }

    public void move() {
        Segment head = getHead();
        int newX = (head.x() + direction.getX() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        int newY = (head.y() + direction.getY() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;

        if (!occupiedPositions.add(new Point(newX, newY))) {
            collided = true;
            return;
        }

        body.add(new Segment(newX, newY, direction));

        if (!growNextMove) {
            Segment tail = body.remove(0);
            occupiedPositions.remove(new Point(tail.x(), tail.y()));
        }

        growNextMove = false;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void grow() {
        this.growNextMove = true;
    }


    // Checkers for game logic
    public boolean occupiesPosition(int x, int y) {
        return occupiedPositions.contains(new Point(x, y));
    }

    public boolean isCollided() {
        return collided;
    }

    public boolean isMaxLength() {
        return this.body.size() == (GameConstants.GRID_SIZE * GameConstants.GRID_SIZE);
    }

    public boolean isHead(int x, int y) {
        Segment head = getHead();
        return head.x() == x && head.y() == y;
    }

    public boolean isTail(int x, int y) {
        Segment tail = getTail();
        return tail.x() == x && tail.y() == y;
    }


    // Getters
    public Direction getDirection() {
        return direction;
    }

    private Segment getHead() {
        return body.get(body.size() - 1);
    }

    private Segment getTail() {
        return body.get(0);
    }

    public Direction getTailDirection() {
        return body.get(1).direction();
    }

    public int[] getHeadPosition() {
        Segment head = getHead();
        return new int[] {head.x(), head.y()};
    }
}