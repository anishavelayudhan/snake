/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author anishavelayudhan
 */
public class Segment {
    private int x;
    private int y;
    // character print 

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x; 
    }

    public int getY() {
        return this.y; 
    }

    @Override
    public String toString() {
        return "Segment{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}

// segment, snake, apple, food hebben iets gelijk > dat zijn de coordinaten. Maak een andere object
