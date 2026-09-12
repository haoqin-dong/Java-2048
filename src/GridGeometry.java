public class GridGeometry {
    private double outerMargin;
    private double innerGap;
    private double boardSizeCells;
    private double panelWidth;
    private double panelHeight;
    private double cellSize;
    private double boardOriginX;
    private double boardOriginY;

    public GridGeometry() {
        this.outerMargin = 14.0;
        this.innerGap = 10.0;
        this.boardSizeCells = 4.0;
        this.panelWidth = 480.0;
        this.panelHeight = 480.0;
        this.recalculate();
    }

    public GridGeometry(double cells) {
        this();
        this.setBoardSizeCells(cells);
    }

    public GridGeometry(double cells, double width, double height) {
        this(cells);
        this.setPanelSize(width, height);
    }

    public void setBoardSizeCells(double cells) {
        if (cells < 3.0) {
            this.boardSizeCells = 3.0;
        } else if (cells > 8.0) {
            this.boardSizeCells = 8.0;
        } else {
            this.boardSizeCells = cells;
        }
        this.recalculate();
    }

    public void setPanelSize(double width, double height) {
        if (width < 120.0) {
            width = 120.0;
        }
        if (height < 120.0) {
            height = 120.0;
        }
        this.panelWidth = width;
        this.panelHeight = height;
        this.recalculate();
    }

    public void recalculate() {
        double usableW = this.panelWidth - this.outerMargin * 2.0;
        double usableH = this.panelHeight - this.outerMargin * 2.0;
        double side = usableW;
        if (usableH < side) {
            side = usableH;
        }
        double totalGap = this.innerGap * (this.boardSizeCells + 1.0);
        double cellsArea = side - totalGap;
        if (cellsArea < this.boardSizeCells) {
            cellsArea = this.boardSizeCells;
        }
        this.cellSize = cellsArea / this.boardSizeCells;
        this.boardOriginX = (this.panelWidth - side) / 2.0;
        this.boardOriginY = (this.panelHeight - side) / 2.0;
    }

    public double getCellSize() {
        return this.cellSize;
    }

    public double getOuterMargin() {
        return this.outerMargin;
    }

    public double getInnerGap() {
        return this.innerGap;
    }

    public double getBoardSizeCells() {
        return this.boardSizeCells;
    }

    public void setOuterMargin(double value) {
        if (value < 0.0) {
            value = 0.0;
        }
        this.outerMargin = value;
        this.recalculate();
    }

    public void setInnerGap(double value) {
        if (value < 0.0) {
            value = 0.0;
        }
        if (value > 40.0) {
            value = 40.0;
        }
        this.innerGap = value;
        this.recalculate();
    }

    public double cellX(double col) {
        double x = this.boardOriginX + this.innerGap + col * (this.cellSize + this.innerGap);
        return x;
    }

    public double cellY(double row) {
        double y = this.boardOriginY + this.innerGap + row * (this.cellSize + this.innerGap);
        return y;
    }

    public double cellX(double col, double gapOverride) {
        double x = this.boardOriginX + gapOverride + col * (this.cellSize + gapOverride);
        return x;
    }

    public double cellY(double row, double gapOverride) {
        double y = this.boardOriginY + gapOverride + row * (this.cellSize + gapOverride);
        return y;
    }

    public double boardSide() {
        double side = this.innerGap * (this.boardSizeCells + 1.0) + this.cellSize * this.boardSizeCells;
        return side;
    }

    public double boardOriginX() {
        return this.boardOriginX;
    }

    public double boardOriginY() {
        return this.boardOriginY;
    }

    public double fontSizeForValue(double value) {
        double digits = TextTool.digitCount(value);
        double base = this.cellSize * 0.42;
        if (digits <= 2.0) {
            base = this.cellSize * 0.44;
        } else if (digits == 3.0) {
            base = this.cellSize * 0.36;
        } else if (digits == 4.0) {
            base = this.cellSize * 0.30;
        } else {
            base = this.cellSize * 0.24;
        }
        return base;
    }

    public double arcSize() {
        double arc = this.cellSize * 0.12;
        if (arc < 4.0) {
            arc = 4.0;
        }
        return arc;
    }

    public double boardArcSize() {
        double arc = this.outerMargin * 0.6;
        if (arc < 6.0) {
            arc = 6.0;
        }
        return arc;
    }
}
