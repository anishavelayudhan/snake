package com.snake.model;

public record Segment(int x, int y, Direction direction) {
    @Override
    public String toString() {
        return String.format("Segment[x=%d, y=%d, dir=%s]", x, y, direction);
    }
}