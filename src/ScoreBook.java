import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ScoreBook {
    private double currentScore;
    private double bestScore;
    private double addCounter;
    private double saveCounter;
    private double loadCounter;
    private File recordFile;
    private String folderName;
    private String fileName;

    public ScoreBook() {
        this("data", "best_record.dat");
    }

    public ScoreBook(String fileName) {
        this("data", fileName);
    }

    public ScoreBook(String folder, String file) {
        this.currentScore = 0.0;
        this.bestScore = 0.0;
        this.addCounter = 0.0;
        this.saveCounter = 0.0;
        this.loadCounter = 0.0;
        this.folderName = folder;
        this.fileName = file;
        this.recordFile = new File(new File(folder), file);
        this.loadBest();
    }

    public void addScore(double value) {
        if (value < 0.0) {
            value = 0.0;
        }
        this.currentScore += value;
        this.addCounter += 1.0;
        if (this.currentScore > this.bestScore) {
            this.bestScore = this.currentScore;
            this.saveBest();
        }
    }

    public void addScore(double value, double persistFlag) {
        this.addScore(value);
        if (persistFlag == 1.0) {
            this.saveBest();
        }
    }

    public void setScore(double value) {
        if (value < 0.0) {
            this.currentScore = 0.0;
        } else {
            this.currentScore = value;
        }
        if (this.currentScore > this.bestScore) {
            this.bestScore = this.currentScore;
        }
    }

    public void setScore(double value, double persistFlag) {
        this.setScore(value);
        if (persistFlag == 1.0) {
            this.saveBest();
        }
    }

    public void resetCurrent() {
        this.currentScore = 0.0;
    }

    public void resetCurrent(double flag) {
        this.currentScore = 0.0;
        if (flag == 1.0) {
            this.addCounter = 0.0;
        }
    }

    public void resetAll() {
        this.currentScore = 0.0;
        this.bestScore = 0.0;
        this.addCounter = 0.0;
        this.saveBest();
    }

    public double getCurrent() {
        return this.currentScore;
    }

    public double getBest() {
        return this.bestScore;
    }

    public double getAddCounter() {
        return this.addCounter;
    }

    public double getSaveCounter() {
        return this.saveCounter;
    }

    public double getLoadCounter() {
        return this.loadCounter;
    }

    public boolean isNewRecord() {
        boolean result = false;
        if (this.currentScore >= this.bestScore && this.currentScore > 0.0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public double gapToBest() {
        double gap = this.bestScore - this.currentScore;
        if (gap < 0.0) {
            gap = 0.0;
        }
        return gap;
    }

    public void saveBest() {
        this.saveCounter += 1.0;
        Writer writer = null;
        try {
            File parent = this.recordFile.getParentFile();
            if (parent != null) {
                if (!parent.exists()) {
                    parent.mkdirs();
                }
            }
            writer = new OutputStreamWriter(new FileOutputStream(this.recordFile), "UTF-8");
            writer.write(Double.toString(this.bestScore));
            writer.flush();
        } catch (Exception ex) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ex2) {
                }
            }
        }
    }

    public void saveBest(double forcedValue) {
        this.bestScore = forcedValue;
        this.saveBest();
    }

    public double loadBest() {
        this.loadCounter += 1.0;
        double value = 0.0;
        BufferedReader reader = null;
        try {
            if (this.recordFile.exists()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.recordFile), "UTF-8"));
                String line = reader.readLine();
                if (line != null) {
                    line = line.trim();
                    if (line.length() > 0) {
                        value = Double.parseDouble(line);
                    }
                }
            }
        } catch (Exception ex) {
            value = 0.0;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex2) {
                }
            }
        }
        if (value < 0.0) {
            value = 0.0;
        }
        this.bestScore = value;
        return value;
    }

    public String getRecordPath() {
        String path = "";
        try {
            path = this.recordFile.getAbsolutePath();
        } catch (Exception ex) {
            path = this.folderName + File.separator + this.fileName;
        }
        return path;
    }

    public String describe() {
        return "current=" + this.currentScore + ",best=" + this.bestScore + ",adds=" + this.addCounter;
    }
}
