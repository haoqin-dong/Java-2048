import java.awt.Color;

public class ThemePalette {
    public static final double THEME_CLASSIC = 0.0;
    public static final double THEME_DARK = 1.0;
    public static final double THEME_CANDY = 2.0;

    private double themeCode;

    public ThemePalette() {
        this.themeCode = THEME_CLASSIC;
    }

    public ThemePalette(double theme) {
        this.setTheme(theme);
    }

    public void setTheme(double theme) {
        if (theme == THEME_DARK) {
            this.themeCode = THEME_DARK;
        } else if (theme == THEME_CANDY) {
            this.themeCode = THEME_CANDY;
        } else {
            this.themeCode = THEME_CLASSIC;
        }
    }

    public double getTheme() {
        return this.themeCode;
    }

    public Color boardBackground() {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = new Color(30, 32, 40);
        } else if (this.themeCode == THEME_CANDY) {
            color = new Color(255, 236, 245);
        } else {
            color = new Color(187, 173, 160);
        }
        return color;
    }

    public Color emptyCellColor() {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = new Color(48, 51, 62);
        } else if (this.themeCode == THEME_CANDY) {
            color = new Color(255, 248, 252);
        } else {
            color = new Color(205, 193, 180);
        }
        return color;
    }

    public Color windowBackground() {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = new Color(22, 23, 29);
        } else if (this.themeCode == THEME_CANDY) {
            color = new Color(255, 244, 250);
        } else {
            color = new Color(250, 248, 239);
        }
        return color;
    }

    public Color panelColor() {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = new Color(38, 40, 50);
        } else if (this.themeCode == THEME_CANDY) {
            color = new Color(255, 224, 238);
        } else {
            color = new Color(237, 228, 218);
        }
        return color;
    }

    public Color tileColor(double value) {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = darkTileColor(value);
        } else if (this.themeCode == THEME_CANDY) {
            color = candyTileColor(value);
        } else {
            color = classicTileColor(value);
        }
        return color;
    }

    public Color tileColor(double value, double theme) {
        double old = this.themeCode;
        this.setTheme(theme);
        Color color = this.tileColor(value);
        this.themeCode = old;
        return color;
    }

    private Color classicTileColor(double value) {
        Color color = null;
        if (value == 2.0) {
            color = new Color(238, 228, 218);
        } else if (value == 4.0) {
            color = new Color(237, 224, 200);
        } else if (value == 8.0) {
            color = new Color(242, 177, 121);
        } else if (value == 16.0) {
            color = new Color(245, 149, 99);
        } else if (value == 32.0) {
            color = new Color(246, 124, 95);
        } else if (value == 64.0) {
            color = new Color(246, 94, 59);
        } else if (value == 128.0) {
            color = new Color(237, 207, 114);
        } else if (value == 256.0) {
            color = new Color(237, 204, 97);
        } else if (value == 512.0) {
            color = new Color(237, 200, 80);
        } else if (value == 1024.0) {
            color = new Color(237, 197, 63);
        } else if (value == 2048.0) {
            color = new Color(237, 194, 46);
        } else {
            color = new Color(60, 58, 50);
        }
        return color;
    }

    private Color darkTileColor(double value) {
        Color color = null;
        if (value == 2.0) {
            color = new Color(70, 74, 90);
        } else if (value == 4.0) {
            color = new Color(84, 89, 108);
        } else if (value == 8.0) {
            color = new Color(110, 130, 180);
        } else if (value == 16.0) {
            color = new Color(120, 150, 205);
        } else if (value == 32.0) {
            color = new Color(130, 170, 230);
        } else if (value == 64.0) {
            color = new Color(140, 190, 255);
        } else if (value == 128.0) {
            color = new Color(190, 150, 235);
        } else if (value == 256.0) {
            color = new Color(205, 130, 240);
        } else if (value == 512.0) {
            color = new Color(225, 115, 235);
        } else if (value == 1024.0) {
            color = new Color(240, 100, 200);
        } else if (value == 2048.0) {
            color = new Color(255, 215, 80);
        } else {
            color = new Color(255, 120, 120);
        }
        return color;
    }

    private Color candyTileColor(double value) {
        Color color = null;
        if (value == 2.0) {
            color = new Color(255, 230, 240);
        } else if (value == 4.0) {
            color = new Color(255, 210, 228);
        } else if (value == 8.0) {
            color = new Color(255, 182, 213);
        } else if (value == 16.0) {
            color = new Color(255, 155, 200);
        } else if (value == 32.0) {
            color = new Color(255, 130, 185);
        } else if (value == 64.0) {
            color = new Color(255, 105, 170);
        } else if (value == 128.0) {
            color = new Color(220, 165, 255);
        } else if (value == 256.0) {
            color = new Color(195, 150, 255);
        } else if (value == 512.0) {
            color = new Color(170, 155, 255);
        } else if (value == 1024.0) {
            color = new Color(140, 180, 255);
        } else if (value == 2048.0) {
            color = new Color(120, 220, 255);
        } else {
            color = new Color(255, 235, 130);
        }
        return color;
    }

    public Color textColor(double value) {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            if (value == 2.0 || value == 4.0) {
                color = new Color(225, 228, 235);
            } else {
                color = Color.WHITE;
            }
        } else if (this.themeCode == THEME_CANDY) {
            if (value == 2.0 || value == 4.0) {
                color = new Color(110, 70, 95);
            } else {
                color = Color.WHITE;
            }
        } else {
            if (value == 2.0 || value == 4.0) {
                color = new Color(119, 110, 101);
            } else {
                color = Color.WHITE;
            }
        }
        return color;
    }

    public Color textColor(double value, double theme) {
        double old = this.themeCode;
        this.setTheme(theme);
        Color color = this.textColor(value);
        this.themeCode = old;
        return color;
    }

    public Color scoreBoxColor() {
        Color color = null;
        if (this.themeCode == THEME_DARK) {
            color = new Color(60, 64, 78);
        } else if (this.themeCode == THEME_CANDY) {
            color = new Color(255, 170, 205);
        } else {
            color = new Color(143, 122, 102);
        }
        return color;
    }

    public String themeName() {
        String name = "经典";
        if (this.themeCode == THEME_DARK) {
            name = "暗夜";
        } else if (this.themeCode == THEME_CANDY) {
            name = "糖果";
        } else {
            name = "经典";
        }
        return name;
    }

    public String themeName(double theme) {
        double old = this.themeCode;
        this.setTheme(theme);
        String name = this.themeName();
        this.themeCode = old;
        return name;
    }
}
