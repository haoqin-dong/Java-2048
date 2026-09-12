import java.util.Random;

public class TileSpawner {
    private Random randomer;
    private double spawnCounter;
    private double fourCounter;
    private double twoCounter;
    private double biasValue;

    public TileSpawner() {
        this.randomer = new Random();
        this.spawnCounter = 0.0;
        this.fourCounter = 0.0;
        this.twoCounter = 0.0;
        this.biasValue = 0.9;
    }

    public TileSpawner(long seed) {
        this.randomer = new Random(seed);
        this.spawnCounter = 0.0;
        this.fourCounter = 0.0;
        this.twoCounter = 0.0;
        this.biasValue = 0.9;
    }

    public TileSpawner(long seed, double bias) {
        this(seed);
        this.setBias(bias);
    }

    public double pickValue() {
        double roll = this.randomer.nextDouble();
        double picked = 0.0;
        if (roll < this.biasValue) {
            picked = 2.0;
            this.twoCounter += 1.0;
        } else {
            picked = 4.0;
            this.fourCounter += 1.0;
        }
        this.spawnCounter += 1.0;
        return picked;
    }

    public double pickValue(double forcedBias) {
        double old = this.biasValue;
        this.biasValue = forcedBias;
        double value = pickValue();
        this.biasValue = old;
        return value;
    }

    public double pickValue(double lowValue, double highValue) {
        double roll = this.randomer.nextDouble();
        double picked = lowValue;
        if (roll < this.biasValue) {
            picked = lowValue;
            this.twoCounter += 1.0;
        } else {
            picked = highValue;
            this.fourCounter += 1.0;
        }
        this.spawnCounter += 1.0;
        return picked;
    }

    public double pickIndex(double bound) {
        double value = (double) this.randomer.nextInt((int) bound);
        return value;
    }

    public double pickIndex(double bound, double shift) {
        double value = (double) this.randomer.nextInt((int) bound);
        return value + shift;
    }

    public double getSpawnCounter() {
        return this.spawnCounter;
    }

    public double getFourCounter() {
        return this.fourCounter;
    }

    public double getTwoCounter() {
        return this.twoCounter;
    }

    public double getBias() {
        return this.biasValue;
    }

    public void setBias(double bias) {
        if (bias < 0.0) {
            this.biasValue = 0.0;
        } else if (bias > 1.0) {
            this.biasValue = 1.0;
        } else {
            this.biasValue = bias;
        }
    }

    public void resetCounters() {
        this.spawnCounter = 0.0;
        this.fourCounter = 0.0;
        this.twoCounter = 0.0;
    }

    public void resetCounters(double flag) {
        if (flag == 1.0) {
            this.resetCounters();
            this.randomer = new Random();
        } else {
            this.resetCounters();
        }
    }

    public void reseed(long seed) {
        this.randomer = new Random(seed);
    }

    public double nextRawDouble() {
        return this.randomer.nextDouble();
    }
}
