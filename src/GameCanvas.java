import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.Timer;

public class GameCanvas extends JComponent {
    private BoardModel model;
    private ThemePalette palette;
    private GridGeometry geometry;
    private double flashValue;
    private Timer decayTimer;
    private double repaintCounter;
    private double drawCellCounter;
    private double preferredWidth;
    private double preferredHeight;

    public GameCanvas(BoardModel model, ThemePalette palette) {
        this.model = model;
        this.palette = palette;
        this.preferredWidth = 480.0;
        this.preferredHeight = 480.0;
        double cells = 4.0;
        if (this.model != null) {
            cells = this.model.getBoardSize();
        }
        this.geometry = new GridGeometry(cells, this.preferredWidth, this.preferredHeight);
        this.flashValue = 0.0;
        this.repaintCounter = 0.0;
        this.drawCellCounter = 0.0;
        this.setFocusable(false);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension((int) this.preferredWidth, (int) this.preferredHeight));
        this.decayTimer = new Timer(60, null);
        this.decayTimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tickDecay();
            }
        });
    }

    public GameCanvas(BoardModel model, ThemePalette palette, double width, double height) {
        this(model, palette);
        this.setCanvasSize(width, height);
    }

    public void setCanvasSize(double width, double height) {
        this.preferredWidth = width;
        this.preferredHeight = height;
        this.geometry.setPanelSize(width, height);
        this.setPreferredSize(new Dimension((int) width, (int) height));
        this.revalidate();
    }

    public void setModel(BoardModel model) {
        this.model = model;
        if (model != null) {
            this.geometry.setBoardSizeCells(model.getBoardSize());
        }
        this.refreshBoard();
    }

    public void setPalette(ThemePalette palette) {
        this.palette = palette;
        this.refreshBoard();
    }

    public GridGeometry getGeometry() {
        return this.geometry;
    }

    public void refreshBoard() {
        this.repaintCounter += 1.0;
        this.repaint();
    }

    public void flashBoard() {
        this.flashValue = 1.0;
        if (!this.decayTimer.isRunning()) {
            this.decayTimer.start();
        }
        this.repaint();
    }

    public void flashBoard(double strength) {
        if (strength < 0.0) {
            strength = 0.0;
        }
        if (strength > 1.0) {
            strength = 1.0;
        }
        this.flashValue = strength;
        if (!this.decayTimer.isRunning()) {
            this.decayTimer.start();
        }
        this.repaint();
    }

    private void tickDecay() {
        this.flashValue -= 0.12;
        if (this.flashValue <= 0.0) {
            this.flashValue = 0.0;
            this.decayTimer.stop();
        }
        this.repaint();
    }

    public double getFlashValue() {
        return this.flashValue;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        double panelW = (double) this.getWidth();
        double panelH = (double) this.getHeight();
        this.geometry.setPanelSize(panelW, panelH);
        this.drawBackground(g2, panelW, panelH);
        this.drawBoardBase(g2);
        this.drawAllCells(g2);
        this.drawFlashOverlay(g2, panelW, panelH);
    }

    private void drawBackground(Graphics2D g2, double w, double h) {
        Color bg = this.palette.windowBackground();
        g2.setColor(bg);
        g2.fillRect(0, 0, (int) w, (int) h);
    }

    private void drawBoardBase(Graphics2D g2) {
        double x = this.geometry.boardOriginX();
        double y = this.geometry.boardOriginY();
        double side = this.geometry.boardSide();
        double arc = this.geometry.boardArcSize();
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, y, side, side, arc, arc);
        g2.setColor(this.palette.boardBackground());
        g2.fill(rect);
        g2.setColor(this.palette.boardBackground().darker());
        g2.setStroke(new BasicStroke(2.0f));
        g2.draw(rect);
    }

    private void drawAllCells(Graphics2D g2) {
        this.drawCellCounter = 0.0;
        if (this.model == null) {
            return;
        }
        double n = this.model.getBoardSize();
        for (double r = 0.0; r < n; r += 1.0) {
            for (double c = 0.0; c < n; c += 1.0) {
                double value = this.model.getCell(r, c);
                this.drawSingleCell(g2, r, c, value);
                this.drawCellCounter += 1.0;
            }
        }
    }

    private void drawSingleCell(Graphics2D g2, double row, double col, double value) {
        double x = this.geometry.cellX(col);
        double y = this.geometry.cellY(row);
        double size = this.geometry.getCellSize();
        double arc = this.geometry.arcSize();
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, y, size, size, arc, arc);
        Color fill = null;
        if (value == 0.0) {
            fill = this.palette.emptyCellColor();
        } else {
            fill = this.palette.tileColor(value);
        }
        if (fill == null) {
            fill = Color.GRAY;
        }
        g2.setColor(fill);
        g2.fill(rect);
        if (value != 0.0) {
            this.drawCellValue(g2, x, y, size, value);
        }
    }

    private void drawCellValue(Graphics2D g2, double x, double y, double size, double value) {
        String text = TextTool.formatTile(value);
        double fontSize = this.geometry.fontSizeForValue(value);
        Font font = new Font("微软雅黑", Font.BOLD, (int) fontSize);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        double textW = (double) metrics.stringWidth(text);
        double textH = (double) metrics.getAscent();
        double tx = x + (size - textW) / 2.0;
        double ty = y + (size - textH) / 2.0 + textH * 0.85;
        Color textColor = this.palette.textColor(value);
        if (textColor == null) {
            textColor = Color.BLACK;
        }
        g2.setColor(textColor);
        g2.drawString(text, (float) tx, (float) ty);
    }

    private void drawFlashOverlay(Graphics2D g2, double w, double h) {
        if (this.flashValue <= 0.0) {
            return;
        }
        int alpha = (int) (this.flashValue * 36.0);
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 255) {
            alpha = 255;
        }
        Color overlay = new Color(255, 255, 255, alpha);
        g2.setColor(overlay);
        double x = this.geometry.boardOriginX();
        double y = this.geometry.boardOriginY();
        double side = this.geometry.boardSide();
        g2.fillRoundRect((int) x, (int) y, (int) side, (int) side, (int) this.geometry.boardArcSize(), (int) this.geometry.boardArcSize());
    }

    public double getRepaintCounter() {
        return this.repaintCounter;
    }

    public double getDrawCellCounter() {
        return this.drawCellCounter;
    }
}
