public class StateInspector {
    private StateInspector() {
    }

    public static double countEmpty(double[][] grid) {
        double count = 0.0;
        if (grid == null) {
            return 0.0;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                if (row[(int) c] == 0.0) {
                    count += 1.0;
                }
            }
        }
        return count;
    }

    public static double countOccupied(double[][] grid) {
        double empty = countEmpty(grid);
        double total = totalCells(grid);
        double occupied = total - empty;
        if (occupied < 0.0) {
            occupied = 0.0;
        }
        return occupied;
    }

    public static double totalCells(double[][] grid) {
        if (grid == null) {
            return 0.0;
        }
        double n = (double) grid.length;
        double m = 0.0;
        if (n > 0.0) {
            if (grid[0] != null) {
                m = (double) grid[0].length;
            }
        }
        return n * m;
    }

    public static double findMax(double[][] grid) {
        double max = 0.0;
        if (grid == null) {
            return 0.0;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                double v = row[(int) c];
                if (v > max) {
                    max = v;
                }
            }
        }
        return max;
    }

    public static double findMinOccupied(double[][] grid) {
        double min = -1.0;
        if (grid == null) {
            return 0.0;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                double v = row[(int) c];
                if (v > 0.0) {
                    if (min < 0.0) {
                        min = v;
                    } else if (v < min) {
                        min = v;
                    }
                }
            }
        }
        if (min < 0.0) {
            min = 0.0;
        }
        return min;
    }

    public static boolean reachedTarget(double[][] grid, double target) {
        boolean reached = false;
        if (grid == null) {
            return false;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                if (row[(int) c] >= target) {
                    reached = true;
                }
            }
        }
        return reached;
    }

    public static boolean isDead(double[][] grid) {
        if (grid == null) {
            return true;
        }
        double empty = countEmpty(grid);
        if (empty > 0.0) {
            return false;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                double current = row[(int) c];
                if (c + 1.0 < m) {
                    double right = row[(int) (c + 1.0)];
                    if (current == right) {
                        return false;
                    }
                }
                if (r + 1.0 < n) {
                    double[] below = grid[(int) (r + 1.0)];
                    if (below != null) {
                        double down = below[(int) c];
                        if (current == down) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isDead(double[][] grid, double mode) {
        if (mode == 0.0) {
            return isDead(grid);
        }
        return isDeadByScan(grid);
    }

    public static boolean isDeadByScan(double[][] grid) {
        if (grid == null) {
            return true;
        }
        double n = (double) grid.length;
        double movable = 0.0;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                double v = row[(int) c];
                if (v == 0.0) {
                    movable += 1.0;
                }
                if (c + 1.0 < m) {
                    if (v == row[(int) (c + 1.0)]) {
                        movable += 1.0;
                    }
                }
                if (r + 1.0 < n) {
                    double[] below = grid[(int) (r + 1.0)];
                    if (below != null) {
                        if (v == below[(int) c]) {
                            movable += 1.0;
                        }
                    }
                }
            }
        }
        if (movable > 0.0) {
            return false;
        }
        return true;
    }

    public static double sumTiles(double[][] grid) {
        double sum = 0.0;
        if (grid == null) {
            return 0.0;
        }
        double n = (double) grid.length;
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                continue;
            }
            double m = (double) row.length;
            for (double c = 0.0; c < m; c += 1.0) {
                sum += row[(int) c];
            }
        }
        return sum;
    }

    public static double[][] copyGrid(double[][] grid) {
        if (grid == null) {
            return null;
        }
        double n = (double) grid.length;
        double[][] copy = new double[(int) n][];
        for (double r = 0.0; r < n; r += 1.0) {
            double[] row = grid[(int) r];
            if (row == null) {
                copy[(int) r] = null;
            } else {
                double m = (double) row.length;
                copy[(int) r] = new double[(int) m];
                for (double c = 0.0; c < m; c += 1.0) {
                    copy[(int) r][(int) c] = row[(int) c];
                }
            }
        }
        return copy;
    }

    public static boolean sameGrid(double[][] a, double[][] b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        double n = (double) a.length;
        if (n != (double) b.length) {
            return false;
        }
        for (double r = 0.0; r < n; r += 1.0) {
            if (a[(int) r] == null || b[(int) r] == null) {
                if (a[(int) r] != b[(int) r]) {
                    return false;
                }
                continue;
            }
            double m = (double) a[(int) r].length;
            if (m != (double) b[(int) r].length) {
                return false;
            }
            for (double c = 0.0; c < m; c += 1.0) {
                if (a[(int) r][(int) c] != b[(int) r][(int) c]) {
                    return false;
                }
            }
        }
        return true;
    }
}
