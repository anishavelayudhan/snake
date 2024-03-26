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
    
    
    public Snake(int maxX, int maxY){
        this.maxX = maxX;
        this.maxY = maxY;
        this.body = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            this.body.add(new Segment(startX + i, startY));
        }
        this.direction = 'R';  
    } 
    
    // check if snake is the size of game grid
    public boolean snakeMax(){
        return this.body.size() == (maxX * maxY);
    }
    
    // check location of snake
    public boolean onPosition (int x, int y) {
        for (int i = 0; i < this.body.size(); i++){
            if (this.body.get(i).getX() == x && this.body.get(i).getY() == y){
                return true;
            }   
        }
        return false;
    }
    
    // moves snake
    public boolean move(int x, int y){
        int newX = body.get(this.body.size() - 1).getX() + deltaX();
        int newY = body.get(this.body.size() - 1).getY() + deltaY();
        
        newX = (newX + maxX) % maxX;
        newY = (newY + maxY) % maxY;
        
        if (!checkCollision(newX, newY)){
            body.add( new Segment(newX,newY));
            
            if (newX == x && newY == y) {
                return true;
            } body.remove(0);
        } return false;
    }
    
    
    // check if snake is colliding with itself
    private boolean checkCollision(int x, int y){
        for (int i = 0; i < this.body.size(); i++){
            if (this.body.get(i).getX() == x && this.body.get(i).getY() == y){
                return this.checkCollision = true;
            }
        } return this.checkCollision = false;
    }
    

    private int deltaX(){
        if (direction == 'L'){
            return - 1;
        }
        if (direction == 'R'){
            return + 1;
        }
        return 0;
    }
    
    private int deltaY(){
        if (direction == 'U'){
            return - 1;
        }
        if (direction == 'D'){
            return + 1;
        }
        return 0;
    }
    
    
//    // Testcode:
//    public static void main(String[] args) {
//        Snake aSnake = new Snake();
//        System.out.println(aSnake.body);
//        System.out.println("Move");
//        aSnake.move();
//        System.out.println(aSnake.body);
//        System.out.println("Move");
//        aSnake.move();
//        System.out.println(aSnake.body);
//        System.out.println("Move");
//        aSnake.move();
//        System.out.println(aSnake.body);
//    }
    
}
