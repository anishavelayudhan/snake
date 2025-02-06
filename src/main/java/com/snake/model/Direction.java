package com.snake.model;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public boolean isOpposite(Direction other) {
        return (this != UP || other != DOWN) &&
                (this != DOWN || other != UP) &&
                (this != LEFT || other != RIGHT) &&
                (this != RIGHT || other != LEFT);
    }

    public int getDeltaX() { return deltaX; }
    public int getDeltaY() { return deltaY; }
}