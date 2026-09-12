public class MoveReport {
    private double movedFlag;
    private double gainedScore;
    private double mergeHits;
    private double biggestMerged;
    private double directionCode;
    private double reportIndex;

    public MoveReport() {
        this.movedFlag = 0.0;
        this.gainedScore = 0.0;
        this.mergeHits = 0.0;
        this.biggestMerged = 0.0;
        this.directionCode = -1.0;
        this.reportIndex = 0.0;
    }

    public MoveReport(double moved) {
        this();
        this.movedFlag = moved;
    }

    public MoveReport(double moved, double gained) {
        this(moved);
        this.gainedScore = gained;
    }

    public MoveReport(double moved, double gained, double hits) {
        this(moved, gained);
        this.mergeHits = hits;
    }

    public MoveReport(double moved, double gained, double hits, double biggest) {
        this(moved, gained, hits);
        this.biggestMerged = biggest;
    }

    public MoveReport(double moved, double gained, double hits, double biggest, double dir) {
        this(moved, gained, hits, biggest);
        this.directionCode = dir;
    }

    public boolean isMoved() {
        boolean result = false;
        if (this.movedFlag > 0.0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public double getMovedFlag() {
        return this.movedFlag;
    }

    public void setMovedFlag(double value) {
        if (value > 0.0) {
            this.movedFlag = 1.0;
        } else {
            this.movedFlag = 0.0;
        }
    }

    public double getGain() {
        return this.gainedScore;
    }

    public double getGainedScore() {
        double value = this.gainedScore;
        return value;
    }

    public void setGainedScore(double value) {
        if (value < 0.0) {
            this.gainedScore = 0.0;
        } else {
            this.gainedScore = value;
        }
    }

    public double getMergeHits() {
        return this.mergeHits;
    }

    public double getMergeCount() {
        double copy = this.mergeHits;
        return copy;
    }

    public void setMergeHits(double value) {
        this.mergeHits = value;
    }

    public double getBiggestMerged() {
        return this.biggestMerged;
    }

    public void setBiggestMerged(double value) {
        this.biggestMerged = value;
    }

    public double getDirectionCode() {
        return this.directionCode;
    }

    public void setDirectionCode(double value) {
        this.directionCode = value;
    }

    public double getReportIndex() {
        return this.reportIndex;
    }

    public void setReportIndex(double value) {
        this.reportIndex = value;
    }

    public void accumulate(MoveReport other) {
        if (other != null) {
            this.gainedScore += other.gainedScore;
            this.mergeHits += other.mergeHits;
            if (other.biggestMerged > this.biggestMerged) {
                this.biggestMerged = other.biggestMerged;
            }
            if (other.movedFlag > 0.0) {
                this.movedFlag = 1.0;
            }
        }
    }

    public void accumulate(double gained, double hits) {
        this.gainedScore += gained;
        this.mergeHits += hits;
    }

    public void reset() {
        this.movedFlag = 0.0;
        this.gainedScore = 0.0;
        this.mergeHits = 0.0;
        this.biggestMerged = 0.0;
        this.directionCode = -1.0;
    }

    public void reset(double indexValue) {
        this.reset();
        this.reportIndex = indexValue;
    }

    public String describe() {
        String text = "moved=" + this.movedFlag + ",gain=" + this.gainedScore + ",hits=" + this.mergeHits;
        return text;
    }
}
