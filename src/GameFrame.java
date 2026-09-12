import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JFrame {
    private BoardModel model;
    private ScoreBook scoreBook;
    private ThemePalette palette;
    private GameCanvas canvas;
    private GridGeometry geometry;
    private JLabel currentScoreLabel;
    private JLabel bestScoreLabel;
    private JLabel moveCountLabel;
    private JLabel timeLabel;
    private JLabel maxTileLabel;
    private JLabel statusLabel;
    private JPanel scorePanel;
    private JPanel controlPanel;
    private JPanel directionPanel;
    private Timer tickTimer;
    private double elapsedSeconds;
    private double keyEventCounter;
    private double frameIndex;
    private double themeCode;
    private double winDialogShown;
    private double deadDialogShown;
    private double canvasSide;
    private double fitLoopCounter;

    public GameFrame() {
        this(4.0, ThemePalette.THEME_CLASSIC);
    }

    public GameFrame(double boardSize) {
        this(boardSize, ThemePalette.THEME_CLASSIC);
    }

    public GameFrame(double boardSize, double theme) {
        this.themeCode = theme;
        this.palette = new ThemePalette(theme);
        this.model = new BoardModel(boardSize);
        this.scoreBook = new ScoreBook();
        this.elapsedSeconds = 0.0;
        this.keyEventCounter = 0.0;
        this.frameIndex = 0.0;
        this.winDialogShown = 0.0;
        this.deadDialogShown = 0.0;
        this.fitLoopCounter = 0.0;
        this.canvasSide = this.computeCanvasSide();
        this.buildWindow();
        this.installTimers();
        this.installKeyControl();
        this.refreshAllLabels();
    }

    private double computeCanvasSide() {
        double side = 440.0;
        try {
            Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            double availH = (double) bounds.height;
            double availW = (double) bounds.width;
            double reservedH = 330.0;
            double byHeight = availH - reservedH;
            double byWidth = availW - 30.0;
            side = byHeight;
            if (byWidth < side) {
                side = byWidth;
            }
            if (side > 460.0) {
                side = 460.0;
            }
            if (side < 260.0) {
                side = 260.0;
            }
        } catch (Throwable t) {
            side = 400.0;
        }
        return side;
    }

    private void fitToScreen() {
        double guard = 0.0;
        while (guard < 6.0) {
            this.pack();
            Rectangle bounds = null;
            try {
                bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            } catch (Throwable t) {
                break;
            }
            double overH = (double) this.getHeight() - (double) bounds.height;
            double overW = (double) this.getWidth() - (double) bounds.width;
            if (overH <= 0.0 && overW <= 0.0) {
                break;
            }
            double next = this.canvasSide;
            if (overH > 0.0) {
                next = next - overH - 6.0;
            }
            if (overW > 0.0) {
                double widthSide = this.canvasSide - overW - 6.0;
                if (widthSide < next) {
                    next = widthSide;
                }
            }
            if (next < 240.0) {
                next = 240.0;
            }
            if (next >= this.canvasSide) {
                break;
            }
            this.canvasSide = next;
            this.canvas.setCanvasSize(this.canvasSide, this.canvasSide);
            guard += 1.0;
            this.fitLoopCounter += 1.0;
        }
    }

    private void buildWindow() {
        this.setTitle("2048 冗余版");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(this.palette.windowBackground());
        this.setLayout(new BorderLayout(8, 8));

        JPanel rootNorth = this.buildTopPanel();
        this.add(rootNorth, BorderLayout.NORTH);

        this.canvas = new GameCanvas(this.model, this.palette, this.canvasSide, this.canvasSide);
        this.canvas.setBackground(this.palette.windowBackground());
        JPanel boardHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        boardHolder.setBackground(this.palette.windowBackground());
        boardHolder.add(this.canvas);
        this.add(boardHolder, BorderLayout.CENTER);

        this.controlPanel = this.buildControlPanel();
        this.add(this.controlPanel, BorderLayout.SOUTH);

        this.fitToScreen();
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleClosing();
            }
        });
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 4));
        panel.setBackground(this.palette.windowBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 2, 10));

        JLabel title = ButtonFactory.makeCenterBoldLabel("2 0 4 8", 22.0);
        title.setForeground(new Color(119, 110, 101));
        panel.add(title, BorderLayout.NORTH);

        this.scorePanel = new JPanel(new GridLayout(1, 4, 6, 6));
        this.scorePanel.setBackground(this.palette.windowBackground());
        Color boxColor = this.palette.scoreBoxColor();
        this.currentScoreLabel = ButtonFactory.makeScoreBox("当前分数", 0.0, boxColor);
        this.bestScoreLabel = ButtonFactory.makeScoreBox("最高纪录", this.scoreBook.getBest(), boxColor);
        this.moveCountLabel = ButtonFactory.makeScoreBox("移动步数", 0.0, boxColor);
        this.timeLabel = ButtonFactory.makeScoreBox("游戏时间", 0.0, boxColor);
        this.scorePanel.add(this.currentScoreLabel);
        this.scorePanel.add(this.bestScoreLabel);
        this.scorePanel.add(this.moveCountLabel);
        this.scorePanel.add(this.timeLabel);
        panel.add(this.scorePanel, BorderLayout.CENTER);

        this.maxTileLabel = ButtonFactory.makeCenterLabel("最大方块: 0", 12.0);
        this.statusLabel = ButtonFactory.makeCenterLabel("方向键 / WASD 移动，R 重新开始", 12.0);
        JPanel subLine = new JPanel(new GridLayout(2, 1));
        subLine.setBackground(this.palette.windowBackground());
        subLine.add(this.maxTileLabel);
        subLine.add(this.statusLabel);
        panel.add(subLine, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 4));
        panel.setBackground(this.palette.windowBackground());
        panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 6, 10));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 3));
        buttons.setBackground(this.palette.windowBackground());

        JButton restart = ButtonFactory.makeButton("重新开始(R)", 13.0, 143.0, 122.0, 102.0);
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRestart();
            }
        });
        buttons.add(restart);

        JButton themeClassic = ButtonFactory.makeButton("经典", 13.0, 187.0, 173.0, 160.0);
        themeClassic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchTheme(ThemePalette.THEME_CLASSIC);
            }
        });
        buttons.add(themeClassic);

        JButton themeDark = ButtonFactory.makeButton("暗夜", 13.0, 60.0, 64.0, 80.0);
        themeDark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchTheme(ThemePalette.THEME_DARK);
            }
        });
        buttons.add(themeDark);

        JButton themeCandy = ButtonFactory.makeButton("糖果", 13.0, 255.0, 130.0, 185.0);
        themeCandy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchTheme(ThemePalette.THEME_CANDY);
            }
        });
        buttons.add(themeCandy);

        JButton envButton = ButtonFactory.makeButton("JVM信息", 13.0, 90.0, 130.0, 160.0);
        envButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRuntimeInfo();
            }
        });
        buttons.add(envButton);

        JButton quit = ButtonFactory.makeButton("退出游戏", 13.0, 200.0, 80.0, 80.0);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleClosing();
                dispose();
            }
        });
        buttons.add(quit);

        panel.add(buttons, BorderLayout.NORTH);

        this.directionPanel = new JPanel(new GridLayout(3, 3, 4, 4));
        this.directionPanel.setBackground(this.palette.windowBackground());
        this.directionPanel.setPreferredSize(new Dimension(165, 90));
        this.buildDirectionPad();
        JPanel padHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        padHolder.setBackground(this.palette.windowBackground());
        padHolder.add(this.directionPanel);
        panel.add(padHolder, BorderLayout.CENTER);
        return panel;
    }

    private void buildDirectionPad() {
        this.directionPanel.removeAll();
        JButton blank1 = new JButton();
        blank1.setVisible(false);
        JButton up = ButtonFactory.makeButton("↑", 14.0, 143.0, 122.0, 102.0);
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMove(DirectionCode.DIR_UP);
            }
        });
        JButton blank2 = new JButton();
        blank2.setVisible(false);
        JButton left = ButtonFactory.makeButton("←", 14.0, 143.0, 122.0, 102.0);
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMove(DirectionCode.DIR_LEFT);
            }
        });
        JButton center = ButtonFactory.makeButton("GO", 11.0, 170.0, 150.0, 130.0);
        center.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestGameFocus();
            }
        });
        JButton right = ButtonFactory.makeButton("→", 14.0, 143.0, 122.0, 102.0);
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMove(DirectionCode.DIR_RIGHT);
            }
        });
        JButton blank3 = new JButton();
        blank3.setVisible(false);
        JButton down = ButtonFactory.makeButton("↓", 14.0, 143.0, 122.0, 102.0);
        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doMove(DirectionCode.DIR_DOWN);
            }
        });
        JButton blank4 = new JButton();
        blank4.setVisible(false);
        this.directionPanel.add(blank1);
        this.directionPanel.add(up);
        this.directionPanel.add(blank2);
        this.directionPanel.add(left);
        this.directionPanel.add(center);
        this.directionPanel.add(right);
        this.directionPanel.add(blank3);
        this.directionPanel.add(down);
        this.directionPanel.add(blank4);
        java.awt.Component[] kids = this.directionPanel.getComponents();
        double kidCount = (double) kids.length;
        for (double i = 0.0; i < kidCount; i += 1.0) {
            java.awt.Component kid = kids[(int) i];
            if (kid instanceof JButton) {
                JButton btn = (JButton) kid;
                btn.setMargin(new java.awt.Insets(0, 0, 0, 0));
            }
        }
    }

    private void installTimers() {
        this.tickTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTick();
            }
        });
        this.tickTimer.start();
    }

    private void handleTick() {
        this.elapsedSeconds += 1.0;
        this.refreshTimeLabel();
    }

    private void refreshTimeLabel() {
        this.timeLabel.setText("<html><div style='text-align:center;'>游戏时间<br>" + TextTool.formatTime(this.elapsedSeconds) + "</div></html>");
    }

    private void installKeyControl() {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }
        });
    }

    private void handleKeyPressed(KeyEvent e) {
        this.keyEventCounter += 1.0;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_R) {
            this.handleRestart();
            return;
        }
        double direction = DirectionCode.keyCodeToDirection(code, e.getModifiersEx());
        if (direction != DirectionCode.DIR_NONE) {
            this.doMove(direction);
        }
    }

    public void doMove(double direction) {
        if (this.model == null) {
            return;
        }
        if (this.model.getFrozen() != 0.0) {
            this.setStatusText("棋局已冻结");
            return;
        }
        MoveReport report = this.model.performMove(direction);
        if (report.isMoved()) {
            this.scoreBook.addScore(report.getGain());
            this.canvas.flashBoard();
            this.refreshAllLabels();
            this.checkEndStates();
        } else {
            this.setStatusText("该方向无法移动");
        }
        this.requestGameFocus();
    }

    private void checkEndStates() {
        if (this.model.getWinReachedFlag() == 1.0 && this.winDialogShown == 0.0) {
            this.winDialogShown = 1.0;
            int choice = JOptionPane.showConfirmDialog(this, "你合成了 " + TextTool.formatTile(this.model.getWinTarget()) + "！是否继续挑战？", "胜利达成", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                this.handleRestart();
            }
        }
        if (this.model.getGameOverFlag() == 1.0 && this.deadDialogShown == 0.0) {
            this.deadDialogShown = 1.0;
            this.tickTimer.stop();
            int choice = JOptionPane.showConfirmDialog(this, "已经没有可以移动的方块了，是否重新开始？", "游戏结束", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                this.handleRestart();
            } else {
                this.setStatusText("游戏结束，按 R 重新开始");
            }
        }
    }

    public void handleRestart() {
        double size = this.model.getBoardSize();
        double target = this.model.getWinTarget();
        this.model.resetBoard(size, target);
        this.scoreBook.resetCurrent();
        this.elapsedSeconds = 0.0;
        this.winDialogShown = 0.0;
        this.deadDialogShown = 0.0;
        if (!this.tickTimer.isRunning()) {
            this.tickTimer.start();
        }
        this.canvas.setModel(this.model);
        this.refreshAllLabels();
        this.setStatusText("新棋局开始，方向键移动");
        this.requestGameFocus();
    }

    public void switchTheme(double theme) {
        this.themeCode = theme;
        this.palette.setTheme(theme);
        this.getContentPane().setBackground(this.palette.windowBackground());
        this.canvas.setPalette(this.palette);
        this.refreshThemeColors();
        this.canvas.repaint();
        this.setStatusText("已切换主题: " + this.palette.themeName());
        this.requestGameFocus();
    }

    private void refreshThemeColors() {
        Color bg = this.palette.windowBackground();
        this.scorePanel.setBackground(bg);
        this.controlPanel.setBackground(bg);
        this.directionPanel.setBackground(bg);
        Color box = this.palette.scoreBoxColor();
        this.currentScoreLabel.setBackground(box);
        this.bestScoreLabel.setBackground(box);
        this.moveCountLabel.setBackground(box);
        this.timeLabel.setBackground(box);
    }

    private void refreshAllLabels() {
        this.currentScoreLabel.setText("<html><div style='text-align:center;'>当前分数<br>" + TextTool.formatScore(this.scoreBook.getCurrent(), true) + "</div></html>");
        this.bestScoreLabel.setText("<html><div style='text-align:center;'>最高纪录<br>" + TextTool.formatScore(this.scoreBook.getBest(), true) + "</div></html>");
        this.moveCountLabel.setText("<html><div style='text-align:center;'>移动步数<br>" + TextTool.formatScore(this.model.getMoveCount()) + "</div></html>");
        this.maxTileLabel.setText("最大方块: " + TextTool.formatTile(this.model.maxTile()) + "  |  空格: " + TextTool.formatScore(this.model.countEmptyCells()));
        this.refreshTimeLabel();
    }

    private void setStatusText(String text) {
        this.statusLabel.setText(text);
    }

    public void requestGameFocus() {
        this.requestFocusInWindow();
        this.requestFocus();
    }

    private void showRuntimeInfo() {
        String report = RuntimeSniffer.fullReport() + "\n按键事件计数: " + TextTool.formatScore(this.keyEventCounter) + "\n纪录文件: " + this.scoreBook.getRecordPath();
        JOptionPane.showMessageDialog(this, report, "JVM 运行环境", JOptionPane.INFORMATION_MESSAGE);
        this.requestGameFocus();
    }

    private void handleClosing() {
        if (this.tickTimer != null) {
            this.tickTimer.stop();
        }
        this.scoreBook.saveBest();
    }

    public double getElapsedSeconds() {
        return this.elapsedSeconds;
    }

    public double getKeyEventCounter() {
        return this.keyEventCounter;
    }

    public BoardModel getModel() {
        return this.model;
    }

    public ScoreBook getScoreBook() {
        return this.scoreBook;
    }

    public void showGame() {
        this.frameIndex += 1.0;
        this.setVisible(true);
        this.requestGameFocus();
    }
}
