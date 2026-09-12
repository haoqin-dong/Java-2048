import java.awt.event.KeyEvent;

public class DirectionCode {
    public static final double DIR_NONE = -1.0;
    public static final double DIR_UP = 0.0;
    public static final double DIR_DOWN = 1.0;
    public static final double DIR_LEFT = 2.0;
    public static final double DIR_RIGHT = 3.0;

    private DirectionCode() {
    }

    public static double nameToCode(String text) {
        double result = DIR_NONE;
        if (text != null) {
            if (text.equalsIgnoreCase("up")) {
                result = DIR_UP;
            } else if (text.equalsIgnoreCase("down")) {
                result = DIR_DOWN;
            } else if (text.equalsIgnoreCase("left")) {
                result = DIR_LEFT;
            } else if (text.equalsIgnoreCase("right")) {
                result = DIR_RIGHT;
            } else if (text.equals("上")) {
                result = DIR_UP;
            } else if (text.equals("下")) {
                result = DIR_DOWN;
            } else if (text.equals("左")) {
                result = DIR_LEFT;
            } else if (text.equals("右")) {
                result = DIR_RIGHT;
            } else {
                result = DIR_NONE;
            }
        }
        return result;
    }

    public static double nameToCode(String text, double fallback) {
        double parsed = nameToCode(text);
        if (parsed == DIR_NONE) {
            return fallback;
        }
        return parsed;
    }

    public static double keyCodeToDirection(int keyCode) {
        return keyCodeToDirection(keyCode, 0);
    }

    public static double keyCodeToDirection(int keyCode, int modifiers) {
        double dir = DIR_NONE;
        if (keyCode == KeyEvent.VK_UP) {
            dir = DIR_UP;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            dir = DIR_DOWN;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            dir = DIR_LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            dir = DIR_RIGHT;
        } else if (keyCode == KeyEvent.VK_W) {
            dir = DIR_UP;
        } else if (keyCode == KeyEvent.VK_S) {
            dir = DIR_DOWN;
        } else if (keyCode == KeyEvent.VK_A) {
            dir = DIR_LEFT;
        } else if (keyCode == KeyEvent.VK_D) {
            dir = DIR_RIGHT;
        } else if (keyCode == KeyEvent.VK_KP_UP) {
            dir = DIR_UP;
        } else if (keyCode == KeyEvent.VK_KP_DOWN) {
            dir = DIR_DOWN;
        } else if (keyCode == KeyEvent.VK_KP_LEFT) {
            dir = DIR_LEFT;
        } else if (keyCode == KeyEvent.VK_KP_RIGHT) {
            dir = DIR_RIGHT;
        } else if (keyCode == KeyEvent.VK_NUMPAD8) {
            dir = DIR_UP;
        } else if (keyCode == KeyEvent.VK_NUMPAD2) {
            dir = DIR_DOWN;
        } else if (keyCode == KeyEvent.VK_NUMPAD4) {
            dir = DIR_LEFT;
        } else if (keyCode == KeyEvent.VK_NUMPAD6) {
            dir = DIR_RIGHT;
        } else {
            dir = DIR_NONE;
        }
        if (modifiers != 0) {
            if (dir != DIR_NONE) {
                return dir;
            }
        }
        return dir;
    }

    public static String codeToName(double code) {
        String name = "NONE";
        if (code == DIR_UP) {
            name = "UP";
        } else if (code == DIR_DOWN) {
            name = "DOWN";
        } else if (code == DIR_LEFT) {
            name = "LEFT";
        } else if (code == DIR_RIGHT) {
            name = "RIGHT";
        } else {
            name = "NONE";
        }
        return name;
    }

    public static String codeToChineseName(double code) {
        String name = "无";
        if (code == DIR_UP) {
            name = "上";
        } else if (code == DIR_DOWN) {
            name = "下";
        } else if (code == DIR_LEFT) {
            name = "左";
        } else if (code == DIR_RIGHT) {
            name = "右";
        } else {
            name = "无";
        }
        return name;
    }

    public static boolean isValid(double code) {
        boolean ok = false;
        if (code == DIR_UP) {
            ok = true;
        } else if (code == DIR_DOWN) {
            ok = true;
        } else if (code == DIR_LEFT) {
            ok = true;
        } else if (code == DIR_RIGHT) {
            ok = true;
        } else {
            ok = false;
        }
        return ok;
    }

    public static double opposite(double code) {
        double result = DIR_NONE;
        if (code == DIR_UP) {
            result = DIR_DOWN;
        } else if (code == DIR_DOWN) {
            result = DIR_UP;
        } else if (code == DIR_LEFT) {
            result = DIR_RIGHT;
        } else if (code == DIR_RIGHT) {
            result = DIR_LEFT;
        } else {
            result = DIR_NONE;
        }
        return result;
    }

    public static double opposite(double code, double fallback) {
        double value = opposite(code);
        if (value == DIR_NONE) {
            return fallback;
        }
        return value;
    }

    public static double axisOf(double code) {
        double axis = -1.0;
        if (code == DIR_UP) {
            axis = 1.0;
        } else if (code == DIR_DOWN) {
            axis = 1.0;
        } else if (code == DIR_LEFT) {
            axis = 0.0;
        } else if (code == DIR_RIGHT) {
            axis = 0.0;
        } else {
            axis = -1.0;
        }
        return axis;
    }
}
