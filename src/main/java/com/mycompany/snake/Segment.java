/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 * @author anishavelayudhan
 */
public record Segment(int x, int y, char direction) {

    @Override
    public String toString() {
        return "Segment{" + "x=" + x + ", y=" + y + '}';
    }


}
