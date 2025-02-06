package com.snake.util;

import com.snake.view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.Font.TRUETYPE_FONT;

public class ResourceLoader {

    public Font loadFont() {
        Font customFont;

        try (InputStream is = getClass().getResourceAsStream("/fonts/nokiafc22.ttf")) {
            assert is != null;
            customFont = Font.createFont(TRUETYPE_FONT, is).deriveFont(Font.PLAIN, GameConstants.FONT_SIZE);
        } catch (Exception e) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, e);
            customFont = new Font("SansSerif", Font.PLAIN, GameConstants.FONT_SIZE);
        }

        return customFont;
    }

    public ImageIcon loadButtonIcon() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull
                (getClass().getResource("/images/snake-helpbutton.png")));
        return new ImageIcon(icon.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH));
    }

    public Image loadIcon() {
        return new ImageIcon(Objects.requireNonNull
                (getClass().getResource("/images/snake-icon.png"))).getImage();
    }

}
