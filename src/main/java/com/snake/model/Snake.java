package com.snake.model;

import com.snake.util.GameConstants;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Segment> body;
    private Direction direction;
    private boolean collided;
    private boolean maxLengthReached;
    private boolean growNextMove;

    public Snake() {
        this.direction = Direction.RIGHT;
        this.body = new ArrayList<>();
        initializeBody();
    }

    private void initializeBody() {
        for (int i = 0; i < 3; i++) {
            this.body.add(new Segment(GameConstants.START_POSITION_X + i,
                    GameConstants.START_POSITION_Y, direction));
        }
    }

    public boolean isMaxLength() {
        return this.body.size() == (GameConstants.GRID_SIZE * GameConstants.GRID_SIZE);
    }

    public boolean occupiesPosition(int x, int y) {
        return body.stream().anyMatch(segment ->
                segment.x() == x && segment.y() == y
        );
    }

    public void move() {
        Segment head = getHead();
        int newX = (head.x() + direction.getDeltaX() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        int newY = (head.y() + direction.getDeltaY() + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;

        if (checkCollision(newX, newY)) {
            collided = true;
            return;
        }

        body.add(new Segment(newX, newY, direction));
        if (!maxLengthReached && !growNextMove) {
            body.remove(0);
        }

        if (growNextMove) {
            growNextMove = false;
        }
    }

    public void grow() {
        this.growNextMove = true;
    }

    private boolean checkCollision(int x, int y) {
        boolean collision = body.stream()
                .anyMatch(segment -> segment.x() == x && segment.y() == y);
        maxLengthReached = isMaxLength();
        return collision;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setDirection(Direction newDirection) {
        if (newDirection.isOpposite(direction)) {
            direction = newDirection;
        }
    }

    // Getters
    public Direction getDirection() { return direction; }
    public Segment getHead() { return body.get(body.size() - 1); }
    public Direction getTailDirection() {
        return body.size() > 1 ? body.get(1).direction() : direction;
    }
    public int[] getHeadPosition() {
        Segment head = getHead();
        return new int[] {head.x(), head.y()};
    }


    public void reset() {
        body.clear();
        initializeBody();
        direction = Direction.RIGHT;
        collided = false;
        maxLengthReached = false;
    }

    public boolean isHead(int x, int y) {
        Segment head = getHead();
        return head.x() == x && head.y() == y;
    }

    public boolean isTail(int x, int y) {
        Segment tail = body.get(0);
        return tail.x() == x && tail.y() == y;
    }

}