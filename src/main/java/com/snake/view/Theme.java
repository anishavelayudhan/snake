package com.snake.view;

import java.awt.Color;
import java.util.List;

public class Theme {
    private final String name;
    private final Color snakeColor;
    private final Color appleColor;
    private final Color gridColor1;
    private final Color gridColor2;
    private final Color backgroundColor;
    private final Color panelColor1;
    private final Color panelColor2;

    private static final Theme RETRO = new Theme(
            "Retro",
            new Color(39, 58, 19),     // Snake
            new Color(39, 58, 19),     // Apple
            new Color(136, 173, 78),   // Grid1
            new Color(136, 173, 78),   // Grid2
            new Color(155, 196, 92),   // Background
            new Color(39, 58, 19),     // Panel1
            new Color(155, 196, 92)    // Panel2
    );

    private static final Theme QQUEST = new Theme(
            "Qquest",
            new Color(55, 192, 241),   // Snake
            new Color(255, 50, 0),     // Apple
            new Color(16, 41, 72),     // Grid1
            new Color(10, 34, 64),     // Grid2
            new Color(9, 30, 56),      // Background
            new Color(26, 56, 93),     // Panel1
            new Color(55, 192, 241)    // Panel2
    );

    private static final List<Theme> ALL_THEMES = List.of(RETRO, QQUEST);

    private Theme(String name, Color snake, Color apple, Color grid1,
                  Color grid2, Color bg, Color panel1, Color panel2) {
        this.name = name;
        this.snakeColor = snake;
        this.appleColor = apple;
        this.gridColor1 = grid1;
        this.gridColor2 = grid2;
        this.backgroundColor = bg;
        this.panelColor1 = panel1;
        this.panelColor2 = panel2;
    }

    public static Theme getDefaultTheme() {
        return ALL_THEMES.get(0);
    }

    public static Theme getNextTheme(Theme current) {
        int index = ALL_THEMES.indexOf(current);
        return ALL_THEMES.get((index + 1) % ALL_THEMES.size());
    }

    public String getName() { return name; }
    public Color getSnakeColor() { return snakeColor; }
    public Color getAppleColor() {  return appleColor; }
    public Color getGridColor1() { return gridColor1; }
    public Color getGridColor2() { return gridColor2; }
    public Color getBackgroundColor() { return backgroundColor; }
    public Color getPanelColor1() { return panelColor1; }
    public Color getPanelColor2() { return panelColor2; }

}