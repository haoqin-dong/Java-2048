import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ButtonFactory {
    private ButtonFactory() {
    }

    public static JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("微软雅黑", Font.BOLD, 15));
        return button;
    }

    public static JButton makeButton(String text, double fontSize) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        int size = (int) fontSize;
        if (size < 8) {
            size = 8;
        }
        button.setFont(new Font("微软雅黑", Font.BOLD, size));
        return button;
    }

    public static JButton makeButton(String text, double fontSize, double r, double g, double b) {
        JButton button = makeButton(text, fontSize);
        int ir = clampColor(r);
        int ig = clampColor(g);
        int ib = clampColor(b);
        button.setBackground(new Color(ir, ig, ib));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        return button;
    }

    public static JButton makeButton(String text, double fontSize, Color bg, Color fg) {
        JButton button = makeButton(text, fontSize);
        if (bg != null) {
            button.setBackground(bg);
            button.setOpaque(true);
            button.setBorderPainted(false);
        }
        if (fg != null) {
            button.setForeground(fg);
        }
        return button;
    }

    public static JButton makeWideButton(String text, double width, double height) {
        JButton button = makeButton(text);
        int w = (int) width;
        int h = (int) height;
        if (w < 20) {
            w = 20;
        }
        if (h < 10) {
            h = 10;
        }
        button.setPreferredSize(new Dimension(w, h));
        return button;
    }

    public static JButton makeWideButton(String text, double width, double height, double fontSize) {
        JButton button = makeButton(text, fontSize);
        button.setPreferredSize(new Dimension((int) width, (int) height));
        return button;
    }

    public static JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        return label;
    }

    public static JLabel makeLabel(String text, double fontSize) {
        JLabel label = new JLabel(text);
        int size = (int) fontSize;
        if (size < 6) {
            size = 6;
        }
        label.setFont(new Font("微软雅黑", Font.PLAIN, size));
        return label;
    }

    public static JLabel makeBoldLabel(String text, double fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.BOLD, (int) fontSize));
        return label;
    }

    public static JLabel makeCenterLabel(String text, double fontSize) {
        JLabel label = makeLabel(text, fontSize);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static JLabel makeCenterBoldLabel(String text, double fontSize) {
        JLabel label = makeBoldLabel(text, fontSize);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static JLabel makeScoreBox(String title, double value, Color color) {
        return makeScoreBox(title, value, color, 96.0, 44.0);
    }

    public static JLabel makeScoreBox(String title, double value, Color color, double width, double height) {
        String valueText = TextTool.formatScore(value, true);
        JLabel label = new JLabel("<html><div style='text-align:center;'>" + title + "<br>" + valueText + "</div></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        if (color != null) {
            label.setBackground(color);
        }
        label.setForeground(Color.WHITE);
        label.setFont(new Font("微软雅黑", Font.BOLD, 12));
        int w = (int) width;
        int h = (int) height;
        if (w < 40) {
            w = 40;
        }
        if (h < 24) {
            h = 24;
        }
        label.setPreferredSize(new Dimension(w, h));
        return label;
    }

    public static int clampColor(double value) {
        int result = (int) value;
        if (result < 0) {
            result = 0;
        }
        if (result > 255) {
            result = 255;
        }
        return result;
    }

    public static void styleComponent(Component component, double fontSize) {
        if (component != null) {
            component.setFont(new Font("微软雅黑", Font.PLAIN, (int) fontSize));
        }
    }
}
