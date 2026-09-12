import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class LauncherWindow extends JFrame {
    private double selectedSize;
    private double selectedTheme;
    private double launchCounter;
    private double envCheckCounter;
    private ButtonGroup sizeGroup;
    private ButtonGroup themeGroup;
    private JRadioButton size4;
    private JRadioButton size5;
    private JRadioButton size6;
    private JRadioButton themeClassic;
    private JRadioButton themeDark;
    private JRadioButton themeCandy;
    private JTextArea envArea;
    private JLabel memoryLabel;
    private GameFrame runningFrame;

    public LauncherWindow() {
        this.selectedSize = 4.0;
        this.selectedTheme = ThemePalette.THEME_CLASSIC;
        this.launchCounter = 0.0;
        this.envCheckCounter = 0.0;
        this.buildLauncher();
    }

    private void buildLauncher() {
        this.setTitle("2048 图形化控制台");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(250, 248, 239));
        this.setLayout(new BorderLayout(10, 10));

        JPanel header = this.buildHeader();
        this.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        center.setBackground(new Color(250, 248, 239));
        center.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        center.add(this.buildSizePanel());
        center.add(this.buildThemePanel());
        this.add(center, BorderLayout.CENTER);

        JPanel south = this.buildSouthPanel();
        this.add(south, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                refreshEnvArea();
            }
        });

        this.pack();
        this.fitLauncherToScreen();
        this.setLocationRelativeTo(null);
    }

    private void fitLauncherToScreen() {
        double guard = 0.0;
        while (guard < 4.0) {
            java.awt.Rectangle bounds = null;
            try {
                bounds = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            } catch (Throwable t) {
                break;
            }
            double overH = (double) this.getHeight() - (double) bounds.height;
            double overW = (double) this.getWidth() - (double) bounds.width;
            if (overH <= 0.0 && overW <= 0.0) {
                break;
            }
            int newW = this.getWidth();
            int newH = this.getHeight();
            if (overW > 0.0) {
                newW = (int) ((double) bounds.width - 8.0);
            }
            if (overH > 0.0) {
                newH = (int) ((double) bounds.height - 8.0);
            }
            this.setSize(newW, newH);
            this.pack();
            if (this.getHeight() <= bounds.height && this.getWidth() <= bounds.width) {
                break;
            }
            this.setSize(newW, newH);
            guard += 1.0;
        }
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 248, 239));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        JLabel title = ButtonFactory.makeCenterBoldLabel("2048 游戏图形化操作界面", 24.0);
        title.setForeground(new Color(119, 110, 101));
        panel.add(title, BorderLayout.NORTH);
        JLabel sub = ButtonFactory.makeCenterLabel("选择棋盘规格与主题，点击启动游戏", 13.0);
        sub.setForeground(new Color(140, 130, 120));
        panel.add(sub, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildSizePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 6, 6));
        panel.setBorder(new TitledBorder("棋盘规格"));
        panel.setBackground(new Color(237, 228, 218));
        this.size4 = new JRadioButton("4 x 4  经典模式", true);
        this.size5 = new JRadioButton("5 x 5  进阶模式");
        this.size6 = new JRadioButton("6 x 6  挑战模式");
        this.styleRadio(this.size4);
        this.styleRadio(this.size5);
        this.styleRadio(this.size6);
        this.sizeGroup = new ButtonGroup();
        this.sizeGroup.add(this.size4);
        this.sizeGroup.add(this.size5);
        this.sizeGroup.add(this.size6);
        this.size4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSize = 4.0;
            }
        });
        this.size5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSize = 5.0;
            }
        });
        this.size6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSize = 6.0;
            }
        });
        panel.add(this.size4);
        panel.add(this.size5);
        panel.add(this.size6);
        JLabel hint = ButtonFactory.makeLabel("规格越大，方块越多，难度越高", 12.0);
        panel.add(hint);
        return panel;
    }

    private JPanel buildThemePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 6, 6));
        panel.setBorder(new TitledBorder("界面主题"));
        panel.setBackground(new Color(237, 228, 218));
        this.themeClassic = new JRadioButton("经典配色", true);
        this.themeDark = new JRadioButton("暗夜配色");
        this.themeCandy = new JRadioButton("糖果配色");
        this.styleRadio(this.themeClassic);
        this.styleRadio(this.themeDark);
        this.styleRadio(this.themeCandy);
        this.themeGroup = new ButtonGroup();
        this.themeGroup.add(this.themeClassic);
        this.themeGroup.add(this.themeDark);
        this.themeGroup.add(this.themeCandy);
        this.themeClassic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedTheme = ThemePalette.THEME_CLASSIC;
            }
        });
        this.themeDark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedTheme = ThemePalette.THEME_DARK;
            }
        });
        this.themeCandy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedTheme = ThemePalette.THEME_CANDY;
            }
        });
        panel.add(this.themeClassic);
        panel.add(this.themeDark);
        panel.add(this.themeCandy);
        JLabel hint = ButtonFactory.makeLabel("游戏内也可以随时切换主题", 12.0);
        panel.add(hint);
        return panel;
    }

    private JPanel buildSouthPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(new Color(250, 248, 239));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));

        this.envArea = new JTextArea(6, 30);
        this.envArea.setEditable(false);
        this.envArea.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        this.envArea.setBackground(new Color(245, 242, 235));
        panel.add(this.envArea, BorderLayout.NORTH);

        this.memoryLabel = ButtonFactory.makeLabel("", 12.0);
        JPanel memoryLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        memoryLine.setBackground(new Color(250, 248, 239));
        memoryLine.add(this.memoryLabel);
        panel.add(memoryLine, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        buttons.setBackground(new Color(250, 248, 239));

        JButton start = ButtonFactory.makeButton("启动游戏", 17.0, 143.0, 122.0, 102.0);
        start.setPreferredSize(new Dimension(140, 42));
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLaunch();
            }
        });
        buttons.add(start);

        JButton env = ButtonFactory.makeButton("检测环境", 14.0, 90.0, 130.0, 160.0);
        env.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCheckEnv();
            }
        });
        buttons.add(env);

        JButton help = ButtonFactory.makeButton("玩法说明", 14.0, 170.0, 150.0, 120.0);
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleHelp();
            }
        });
        buttons.add(help);

        JButton exit = ButtonFactory.makeButton("退出", 14.0, 200.0, 90.0, 90.0);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
        buttons.add(exit);

        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void styleRadio(JRadioButton radio) {
        radio.setBackground(new Color(237, 228, 218));
        radio.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 14));
        radio.setFocusable(false);
    }

    public void handleLaunch() {
        this.launchCounter += 1.0;
        double size = this.selectedSize;
        double theme = this.selectedTheme;
        if (size != 4.0 && size != 5.0 && size != 6.0) {
            size = 4.0;
        }
        if (this.runningFrame != null) {
            if (this.runningFrame.isDisplayable()) {
                this.runningFrame.toFront();
                this.runningFrame.requestGameFocus();
                return;
            }
        }
        this.runningFrame = new GameFrame(size, theme);
        this.runningFrame.showGame();
    }

    public void handleCheckEnv() {
        this.envCheckCounter += 1.0;
        this.refreshEnvArea();
        JOptionPane.showMessageDialog(this, "环境检测完成，已刷新运行信息", "检测环境", JOptionPane.INFORMATION_MESSAGE);
    }

    public void refreshEnvArea() {
        String text = RuntimeSniffer.fullReport();
        text = text + "\n启动次数: " + TextTool.formatScore(this.launchCounter);
        text = text + "\n检测次数: " + TextTool.formatScore(this.envCheckCounter);
        this.envArea.setText(text);
        double maxMb = RuntimeSniffer.maxMemoryMB();
        this.memoryLabel.setText("当前 JVM 最大堆内存: " + TextTool.formatScore(maxMb) + " MB（由启动脚本中的 -Xmx 参数决定）");
    }

    public void handleHelp() {
        String help = "玩法说明:\n";
        help = help + "1. 使用键盘方向键或 W A S D 移动方块。\n";
        help = help + "2. 相同数字的方块碰撞会合并成两倍的数字。\n";
        help = help + "3. 每次有效移动后会随机出现 2 或 4。\n";
        help = help + "4. 合成 2048 即获得胜利，可以继续挑战更高数字。\n";
        help = help + "5. 棋盘填满且无法合并时游戏结束。\n";
        help = help + "6. 按 R 键或点击按钮可以重新开始。\n";
        help = help + "7. 也可以点击界面下方的方向圆盘用鼠标操作。";
        JOptionPane.showMessageDialog(this, help, "玩法说明", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleExit() {
        if (this.runningFrame != null) {
            if (this.runningFrame.isDisplayable()) {
                this.runningFrame.dispose();
            }
        }
        this.dispose();
        System.exit(0);
    }

    public void showLauncher() {
        this.setVisible(true);
    }

    public double getLaunchCounter() {
        return this.launchCounter;
    }

    public double getSelectedSize() {
        return this.selectedSize;
    }

    public double getSelectedTheme() {
        return this.selectedTheme;
    }
}
