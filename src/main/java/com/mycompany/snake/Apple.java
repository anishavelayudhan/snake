/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;
import java.util.Random;

/**
 *
 * @author anishavelayudhan
 */
public class Apple {
    int x;
    int y;
    int gridWidth;
    int gridHeight;
    Random r = new Random();
    
    public Apple(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }
    
    // randomize apple position
    public void randomizePos(Snake snake) {
        do {
            this.x = r.nextInt(gridWidth);
            this.y = r.nextInt(gridHeight);
        } while (snake.onPosition(this.x, this.y));
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
