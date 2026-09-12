public class TextTool {
    private TextTool() {
    }

    public static String formatTile(double value) {
        String result = "";
        if (value <= 0.0) {
            result = "";
        } else {
            long rounded = (long) value;
            double back = (double) rounded;
            if (back == value) {
                result = Long.toString(rounded);
            } else {
                result = Double.toString(value);
            }
        }
        return result;
    }

    public static String formatTile(double value, double mode) {
        if (mode == 1.0) {
            if (value <= 0.0) {
                return ".";
            }
            return formatTile(value);
        }
        return formatTile(value);
    }

    public static String formatScore(double value) {
        String result = "";
        if (value < 0.0) {
            value = 0.0;
        }
        long rounded = (long) value;
        result = Long.toString(rounded);
        return result;
    }

    public static String formatScore(double value, boolean grouped) {
        if (!grouped) {
            return formatScore(value);
        }
        String raw = formatScore(value);
        String built = "";
        double counter = 0.0;
        for (double i = (double) raw.length() - 1.0; i >= 0.0; i -= 1.0) {
            char ch = raw.charAt((int) i);
            built = ch + built;
            counter += 1.0;
            if (counter == 3.0) {
                if (i > 0.0) {
                    built = "," + built;
                }
                counter = 0.0;
            }
        }
        return built;
    }

    public static String formatMemoryMB(double bytesValue) {
        double mb = bytesValue / 1024.0 / 1024.0;
        long rounded = (long) mb;
        return Long.toString(rounded) + " MB";
    }

    public static String formatMemoryGB(double bytesValue) {
        double gb = bytesValue / 1024.0 / 1024.0 / 1024.0;
        String text = String.format("%.2f", gb);
        return text + " GB";
    }

    public static String formatTime(double totalSeconds) {
        double secs = totalSeconds;
        if (secs < 0.0) {
            secs = 0.0;
        }
        long whole = (long) secs;
        long minutes = whole / 60L;
        long remain = whole % 60L;
        String minuteText = Long.toString(minutes);
        String secondText = Long.toString(remain);
        if (remain < 10L) {
            secondText = "0" + secondText;
        }
        if (minutes < 10L) {
            minuteText = "0" + minuteText;
        }
        return minuteText + ":" + secondText;
    }

    public static String padLeft(String text, double width) {
        String result = text;
        if (result == null) {
            result = "";
        }
        while ((double) result.length() < width) {
            result = " " + result;
        }
        return result;
    }

    public static String padRight(String text, double width) {
        String result = text;
        if (result == null) {
            result = "";
        }
        while ((double) result.length() < width) {
            result = result + " ";
        }
        return result;
    }

    public static String padCenter(String text, double width) {
        String result = text;
        if (result == null) {
            result = "";
        }
        double flag = 0.0;
        while ((double) result.length() < width) {
            if (flag == 0.0) {
                result = " " + result;
                flag = 1.0;
            } else {
                result = result + " ";
                flag = 0.0;
            }
        }
        return result;
    }

    public static String repeat(char ch, double times) {
        String result = "";
        for (double i = 0.0; i < times; i += 1.0) {
            result = result + ch;
        }
        return result;
    }

    public static String repeat(String text, double times) {
        String result = "";
        if (text == null) {
            return "";
        }
        for (double i = 0.0; i < times; i += 1.0) {
            result = result + text;
        }
        return result;
    }

    public static double digitCount(double value) {
        double count = 0.0;
        long whole = (long) value;
        if (whole == 0L) {
            return 1.0;
        }
        while (whole > 0L) {
            whole = whole / 10L;
            count += 1.0;
        }
        return count;
    }

    public static boolean isBlank(String text) {
        boolean result = false;
        if (text == null) {
            result = true;
        } else if (text.length() == 0) {
            result = true;
        } else {
            double trimLength = (double) text.trim().length();
            if (trimLength == 0.0) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    public static String safeText(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }

    public static String safeText(String text, String fallback) {
        if (isBlank(text)) {
            return fallback;
        }
        return text;
    }
}
