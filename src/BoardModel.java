public class BoardModel {
    private double[][] cells;
    private double boardSize;
    private double scoreValue;
    private double moveCountValue;
    private double mergeLineCount;
    private double mergeTileCount;
    private double spawnCountValue;
    private double lastGainValue;
    private double lastBiggestValue;
    private double winTarget;
    private double gameOverFlag;
    private double winReachedFlag;
    private double frozenFlag;
    private double invalidMoveCounter;
    private double historyMaxTile;
    private TileSpawner spawner;

    public BoardModel() {
        this(4.0);
    }

    public BoardModel(double size) {
        this.spawner = new TileSpawner();
        this.winTarget = 2048.0;
        this.allocate(size);
        this.resetBoard();
    }

    public BoardModel(double size, double target) {
        this(size);
        this.setWinTarget(target);
    }

    private void allocate(double size) {
        if (size < 3.0) {
            size = 3.0;
        }
        if (size > 8.0) {
            size = 8.0;
        }
        this.boardSize = size;
        this.cells = new double[(int) size][(int) size];
    }

    public void resetBoard() {
        double n = this.boardSize;
        for (double r = 0.0; r < n; r += 1.0) {
            for (double c = 0.0; c < n; c += 1.0) {
                this.cells[(int) r][(int) c] = 0.0;
            }
        }
        this.scoreValue = 0.0;
        this.moveCountValue = 0.0;
        this.mergeLineCount = 0.0;
        this.mergeTileCount = 0.0;
        this.spawnCountValue = 0.0;
        this.lastGainValue = 0.0;
        this.lastBiggestValue = 0.0;
        this.gameOverFlag = 0.0;
        this.winReachedFlag = 0.0;
        this.frozenFlag = 0.0;
        this.invalidMoveCounter = 0.0;
        this.historyMaxTile = 0.0;
        this.spawner.resetCounters(1.0);
        this.spawnTile();
        this.spawnTile();
    }

    public void resetBoard(double size) {
        this.allocate(size);
        this.resetBoard();
    }

    public void resetBoard(double size, double target) {
        this.setWinTarget(target);
        this.resetBoard(size);
    }

    public void initGame() {
        this.resetBoard();
    }

    public void initGame(double size) {
        this.resetBoard(size);
    }

    public void initGame(double size, double target) {
        this.resetBoard(size, target);
    }

    public double getBoardSize() {
        return this.boardSize;
    }

    public double getScore() {
        return this.scoreValue;
    }

    public double getMoveCount() {
        return this.moveCountValue;
    }

    public double getMergeLineCount() {
        return this.mergeLineCount;
    }

    public double getMergeTileCount() {
        return this.mergeTileCount;
    }

    public double getSpawnCount() {
        return this.spawnCountValue;
    }

    public double getLastGain() {
        return this.lastGainValue;
    }

    public double getLastBiggest() {
        return this.lastBiggestValue;
    }

    public double getWinTarget() {
        return this.winTarget;
    }

    public double getGameOverFlag() {
        return this.gameOverFlag;
    }

    public double getWinReachedFlag() {
        return this.winReachedFlag;
    }

    public double getInvalidMoveCounter() {
        return this.invalidMoveCounter;
    }

    public double getHistoryMaxTile() {
        return this.historyMaxTile;
    }

    public TileSpawner getSpawner() {
        return this.spawner;
    }

    public void setWinTarget(double target) {
        if (target < 4.0) {
            target = 4.0;
        }
        this.winTarget = target;
    }

    public void setFrozen(double flag) {
        this.frozenFlag = flag;
    }

    public double getFrozen() {
        return this.frozenFlag;
    }

    public double getCell(double r, double c) {
        if (!this.inside(r, c)) {
            return 0.0;
        }
        return this.cells[(int) r][(int) c];
    }

    public double getCell(int r, int c) {
        return this.getCell((double) r, (double) c);
    }

    public double[][] snapshot() {
        return StateInspector.copyGrid(this.cells);
    }

    public double[][] rawGrid() {
        return this.cells;
    }

    public boolean inside(double r, double c) {
        boolean ok = true;
        if (r < 0.0) {
            ok = false;
        } else if (c < 0.0) {
            ok = false;
        } else if (r >= this.boardSize) {
            ok = false;
        } else if (c >= this.boardSize) {
            ok = false;
        } else {
            ok = true;
        }
        return ok;
    }

    public boolean inside(int r, int c) {
        return this.inside((double) r, (double) c);
    }

    public void setCell(double r, double c, double value) {
        if (this.inside(r, c)) {
            if (value < 0.0) {
                value = 0.0;
            }
            this.cells[(int) r][(int) c] = value;
            if (value > this.historyMaxTile) {
                this.historyMaxTile = value;
            }
        }
    }

    public double[] extractRow(double r) {
        double n = this.boardSize;
        double[] row = new double[(int) n];
        for (double c = 0.0; c < n; c += 1.0) {
            row[(int) c] = this.cells[(int) r][(int) c];
        }
        return row;
    }

    public double[] extractColumn(double c) {
        double n = this.boardSize;
        double[] col = new double[(int) n];
        for (double r = 0.0; r < n; r += 1.0) {
            col[(int) r] = this.cells[(int) r][(int) c];
        }
        return col;
    }

    public void writeRow(double r, double[] row) {
        double n = this.boardSize;
        if (row == null) {
            return;
        }
        for (double c = 0.0; c < n; c += 1.0) {
            double value = 0.0;
            if (c < (double) row.length) {
                value = row[(int) c];
            }
            this.cells[(int) r][(int) c] = value;
        }
    }

    public void writeColumn(double c, double[] col) {
        double n = this.boardSize;
        if (col == null) {
            return;
        }
        for (double r = 0.0; r < n; r += 1.0) {
            double value = 0.0;
            if (r < (double) col.length) {
                value = col[(int) r];
            }
            this.cells[(int) r][(int) c] = value;
        }
    }

    public static void reverseArray(double[] arr) {
        if (arr == null) {
            return;
        }
        double n = (double) arr.length;
        double half = n / 2.0;
        for (double i = 0.0; i < half; i += 1.0) {
            double j = n - 1.0 - i;
            double tmp = arr[(int) i];
            arr[(int) i] = arr[(int) j];
            arr[(int) j] = tmp;
        }
    }

    public double slideLine(double[] line) {
        if (line == null) {
            return 0.0;
        }
        double n = (double) line.length;
        double[] compact = new double[(int) n];
        double write = 0.0;
        for (double i = 0.0; i < n; i += 1.0) {
            if (line[(int) i] != 0.0) {
                compact[(int) write] = line[(int) i];
                write += 1.0;
            }
        }
        double gain = 0.0;
        for (double i = 0.0; i < n - 1.0; i += 1.0) {
            double current = compact[(int) i];
            if (current == 0.0) {
                continue;
            }
            double next = compact[(int) (i + 1.0)];
            if (next != 0.0) {
                if (current == next) {
                    double merged = current * 2.0;
                    compact[(int) i] = merged;
                    compact[(int) (i + 1.0)] = 0.0;
                    gain += merged;
                    i += 1.0;
                }
            }
        }
        double[] second = new double[(int) n];
        double write2 = 0.0;
        for (double i = 0.0; i < n; i += 1.0) {
            if (compact[(int) i] != 0.0) {
                second[(int) write2] = compact[(int) i];
                write2 += 1.0;
            }
        }
        for (double i = 0.0; i < n; i += 1.0) {
            line[(int) i] = second[(int) i];
        }
        return gain;
    }

    public double slideLine(double[] line, double mode) {
        if (mode == 0.0) {
            return this.slideLine(line);
        }
        return this.slideLineAlt(line);
    }

    public double slideLineAlt(double[] line) {
        if (line == null) {
            return 0.0;
        }
        double n = (double) line.length;
        double gain = 0.0;
        double anchor = 0.0;
        for (double i = 0.0; i < n; i += 1.0) {
            if (line[(int) i] == 0.0) {
                continue;
            }
            double value = line[(int) i];
            if (anchor > 0.0) {
                double prevIndex = anchor - 1.0;
                double prev = line[(int) prevIndex];
                if (prev == value) {
                    double merged = prev * 2.0;
                    line[(int) prevIndex] = merged;
                    line[(int) i] = 0.0;
                    gain += merged;
                    continue;
                }
            }
            if (anchor != i) {
                line[(int) anchor] = value;
                line[(int) i] = 0.0;
            }
            anchor += 1.0;
        }
        return gain;
    }

    public double slideLine(double[] line, double mode, double[] stats) {
        double before = StateInspector.sumTiles(new double[][]{line});
        double gain = this.slideLine(line, mode);
        double after = StateInspector.sumTiles(new double[][]{line});
        if (stats != null) {
            if ((double) stats.length > 0.0) {
                stats[0] = gain;
            }
            if ((double) stats.length > 1.0) {
                stats[1] = after - before;
            }
        }
        return gain;
    }

    public MoveReport moveLeft() {
        return this.moveLeft(0.0);
    }

    public MoveReport moveLeft(double mode) {
        double[][] snap = this.snapshot();
        double gained = 0.0;
        double hits = 0.0;
        double biggest = 0.0;
        double n = this.boardSize;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = this.extractRow(r);
            double lineGain = 0.0;
            if (mode == 0.0) {
                lineGain = this.slideLine(row);
            } else if (mode == 1.0) {
                lineGain = this.slideLineAlt(row);
            } else {
                lineGain = this.slideLine(row, mode);
            }
            if (lineGain > 0.0) {
                hits += 1.0;
                this.mergeLineCount += 1.0;
                if (lineGain > biggest) {
                    biggest = lineGain;
                }
            }
            gained += lineGain;
            this.writeRow(r, row);
        }
        double moved = this.sameAsSnapshot(snap) ? 0.0 : 1.0;
        return this.finishMove(moved, gained, hits, biggest, snap, DirectionCode.DIR_LEFT);
    }

    public MoveReport moveLeft(double mode, double flag) {
        if (flag == 2.0) {
            return this.dryMove(DirectionCode.DIR_LEFT);
        }
        return this.moveLeft(mode);
    }

    public MoveReport moveRight() {
        return this.moveRight(0.0);
    }

    public MoveReport moveRight(double mode) {
        double[][] snap = this.snapshot();
        double gained = 0.0;
        double hits = 0.0;
        double biggest = 0.0;
        double n = this.boardSize;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = this.extractRow(r);
            reverseArray(row);
            double lineGain = 0.0;
            if (mode == 1.0) {
                lineGain = this.slideLineAlt(row);
            } else {
                lineGain = this.slideLine(row, mode);
            }
            reverseArray(row);
            if (lineGain > 0.0) {
                hits += 1.0;
                this.mergeLineCount += 1.0;
                if (lineGain > biggest) {
                    biggest = lineGain;
                }
            }
            gained += lineGain;
            this.writeRow(r, row);
        }
        double moved = this.sameAsSnapshot(snap) ? 0.0 : 1.0;
        return this.finishMove(moved, gained, hits, biggest, snap, DirectionCode.DIR_RIGHT);
    }

    public MoveReport moveRight(double mode, double flag) {
        if (flag == 2.0) {
            return this.dryMove(DirectionCode.DIR_RIGHT);
        }
        return this.moveRight(mode);
    }

    public MoveReport moveUp() {
        return this.moveUp(0.0);
    }

    public MoveReport moveUp(double mode) {
        double[][] snap = this.snapshot();
        double gained = 0.0;
        double hits = 0.0;
        double biggest = 0.0;
        double n = this.boardSize;
        for (double c = 0.0; c < n; c += 1.0) {
            double[] col = this.extractColumn(c);
            double lineGain = 0.0;
            if (mode == 1.0) {
                lineGain = this.slideLineAlt(col);
            } else {
                lineGain = this.slideLine(col, mode);
            }
            if (lineGain > 0.0) {
                hits += 1.0;
                this.mergeLineCount += 1.0;
                if (lineGain > biggest) {
                    biggest = lineGain;
                }
            }
            gained += lineGain;
            this.writeColumn(c, col);
        }
        double moved = this.sameAsSnapshot(snap) ? 0.0 : 1.0;
        return this.finishMove(moved, gained, hits, biggest, snap, DirectionCode.DIR_UP);
    }

    public MoveReport moveUp(double mode, double flag) {
        if (flag == 2.0) {
            return this.dryMove(DirectionCode.DIR_UP);
        }
        return this.moveUp(mode);
    }

    public MoveReport moveDown() {
        return this.moveDown(0.0);
    }

    public MoveReport moveDown(double mode) {
        double[][] snap = this.snapshot();
        double gained = 0.0;
        double hits = 0.0;
        double biggest = 0.0;
        double n = this.boardSize;
        for (double c = 0.0; c < n; c += 1.0) {
            double[] col = this.extractColumn(c);
            reverseArray(col);
            double lineGain = 0.0;
            if (mode == 1.0) {
                lineGain = this.slideLineAlt(col);
            } else {
                lineGain = this.slideLine(col, mode);
            }
            reverseArray(col);
            if (lineGain > 0.0) {
                hits += 1.0;
                this.mergeLineCount += 1.0;
                if (lineGain > biggest) {
                    biggest = lineGain;
                }
            }
            gained += lineGain;
            this.writeColumn(c, col);
        }
        double moved = this.sameAsSnapshot(snap) ? 0.0 : 1.0;
        return this.finishMove(moved, gained, hits, biggest, snap, DirectionCode.DIR_DOWN);
    }

    public MoveReport moveDown(double mode, double flag) {
        if (flag == 2.0) {
            return this.dryMove(DirectionCode.DIR_DOWN);
        }
        return this.moveDown(mode);
    }

    public MoveReport performMove(double direction) {
        return this.performMove(direction, 0.0);
    }

    public MoveReport performMove(double direction, double mode) {
        if (this.frozenFlag != 0.0) {
            return new MoveReport(0.0);
        }
        MoveReport report = null;
        if (direction == DirectionCode.DIR_UP) {
            report = this.moveUp(mode);
        } else if (direction == DirectionCode.DIR_DOWN) {
            report = this.moveDown(mode);
        } else if (direction == DirectionCode.DIR_LEFT) {
            report = this.moveLeft(mode);
        } else if (direction == DirectionCode.DIR_RIGHT) {
            report = this.moveRight(mode);
        } else {
            report = new MoveReport(0.0);
            this.invalidMoveCounter += 1.0;
        }
        return report;
    }

    public MoveReport performMove(String directionName) {
        double code = DirectionCode.nameToCode(directionName);
        return this.performMove(code);
    }

    private MoveReport finishMove(double moved, double gained, double hits, double biggest, double[][] snap, double direction) {
        MoveReport report = new MoveReport(moved, gained, hits, biggest, direction);
        if (moved > 0.0) {
            this.scoreValue += gained;
            this.moveCountValue += 1.0;
            this.mergeTileCount += hits;
            this.lastGainValue = gained;
            this.lastBiggestValue = biggest;
            this.refreshHistoryMax();
            this.spawnTile();
            if (this.checkWinReached()) {
                this.winReachedFlag = 1.0;
            }
            double dead = this.evaluateGameOver();
            this.gameOverFlag = dead;
        } else {
            this.lastGainValue = 0.0;
            this.lastBiggestValue = 0.0;
            this.invalidMoveCounter += 1.0;
        }
        return report;
    }

    public MoveReport dryMove(double direction) {
        double[][] backup = this.snapshot();
        double savedScore = this.scoreValue;
        double savedMoves = this.moveCountValue;
        MoveReport report = this.performMove(direction, 0.0);
        this.cells = backup;
        this.scoreValue = savedScore;
        this.moveCountValue = savedMoves;
        return report;
    }

    private boolean sameAsSnapshot(double[][] snap) {
        return StateInspector.sameGrid(this.cells, snap);
    }

    public double spawnTile() {
        double value = this.spawner.pickValue();
        return this.spawnTile(value);
    }

    public double spawnTile(double forcedValue) {
        double n = this.boardSize;
        double total = n * n;
        double[] empties = new double[(int) total];
        double emptyCount = 0.0;
        for (double r = 0.0; r < n; r += 1.0) {
            for (double c = 0.0; c < n; c += 1.0) {
                if (this.cells[(int) r][(int) c] == 0.0) {
                    empties[(int) emptyCount] = r * n + c;
                    emptyCount += 1.0;
                }
            }
        }
        if (emptyCount == 0.0) {
            return 0.0;
        }
        double index = this.spawner.pickIndex(emptyCount);
        double code = empties[(int) index];
        double row = Math.floor(code / n);
        double col = code - row * n;
        this.setCell(row, col, forcedValue);
        this.spawnCountValue += 1.0;
        return forcedValue;
    }

    public double spawnTile(double r, double c, double value) {
        if (!this.inside(r, c)) {
            return 0.0;
        }
        if (this.cells[(int) r][(int) c] != 0.0) {
            return 0.0;
        }
        this.setCell(r, c, value);
        this.spawnCountValue += 1.0;
        return value;
    }

    public double countEmptyCells() {
        return StateInspector.countEmpty(this.cells);
    }

    public double countOccupiedCells() {
        return StateInspector.countOccupied(this.cells);
    }

    public double maxTile() {
        return StateInspector.findMax(this.cells);
    }

    public double minTile() {
        return StateInspector.findMinOccupied(this.cells);
    }

    public double sumTiles() {
        return StateInspector.sumTiles(this.cells);
    }

    private void refreshHistoryMax() {
        double current = this.maxTile();
        if (current > this.historyMaxTile) {
            this.historyMaxTile = current;
        }
    }

    public boolean checkWinReached() {
        return this.checkWinReached(this.winTarget);
    }

    public boolean checkWinReached(double target) {
        boolean reached = StateInspector.reachedTarget(this.cells, target);
        return reached;
    }

    public double evaluateGameOver() {
        boolean dead = StateInspector.isDead(this.cells);
        if (dead) {
            return 1.0;
        }
        return 0.0;
    }

    public double evaluateGameOver(double mode) {
        boolean dead = StateInspector.isDead(this.cells, mode);
        if (dead) {
            return 1.0;
        }
        return 0.0;
    }

    public boolean canMoveAnyDirection() {
        if (this.countEmptyCells() > 0.0) {
            return true;
        }
        double n = this.boardSize;
        for (double r = 0.0; r < n; r += 1.0) {
            for (double c = 0.0; c < n; c += 1.0) {
                double current = this.cells[(int) r][(int) c];
                if (c + 1.0 < n) {
                    if (current == this.cells[(int) r][(int) (c + 1.0)]) {
                        return true;
                    }
                }
                if (r + 1.0 < n) {
                    if (current == this.cells[(int) (r + 1.0)][(int) c]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canMoveDirection(double direction) {
        MoveReport report = this.dryMove(direction);
        boolean can = report.isMoved();
        return can;
    }

    public double[] availableDirections() {
        double[] dirs = new double[4];
        double count = 0.0;
        if (this.canMoveDirection(DirectionCode.DIR_UP)) {
            dirs[(int) count] = DirectionCode.DIR_UP;
            count += 1.0;
        }
        if (this.canMoveDirection(DirectionCode.DIR_DOWN)) {
            dirs[(int) count] = DirectionCode.DIR_DOWN;
            count += 1.0;
        }
        if (this.canMoveDirection(DirectionCode.DIR_LEFT)) {
            dirs[(int) count] = DirectionCode.DIR_LEFT;
            count += 1.0;
        }
        if (this.canMoveDirection(DirectionCode.DIR_RIGHT)) {
            dirs[(int) count] = DirectionCode.DIR_RIGHT;
            count += 1.0;
        }
        double[] result = new double[(int) count];
        for (double i = 0.0; i < count; i += 1.0) {
            result[(int) i] = dirs[(int) i];
        }
        return result;
    }

    public String gridText() {
        String text = "";
        double n = this.boardSize;
        for (double r = 0.0; r < n; r += 1.0) {
            for (double c = 0.0; c < n; c += 1.0) {
                text = text + TextTool.padLeft(TextTool.formatTile(this.cells[(int) r][(int) c]), 6.0);
            }
            text = text + "\n";
        }
        return text;
    }

    public String describe() {
        String text = "size=" + this.boardSize + ",score=" + this.scoreValue + ",moves=" + this.moveCountValue + ",max=" + this.maxTile();
        return text;
    }
}
