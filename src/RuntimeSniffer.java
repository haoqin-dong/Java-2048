public class RuntimeSniffer {
    private RuntimeSniffer() {
    }

    public static double maxMemoryBytes() {
        Runtime rt = Runtime.getRuntime();
        double value = (double) rt.maxMemory();
        return value;
    }

    public static double totalMemoryBytes() {
        Runtime rt = Runtime.getRuntime();
        double value = (double) rt.totalMemory();
        return value;
    }

    public static double freeMemoryBytes() {
        Runtime rt = Runtime.getRuntime();
        double value = (double) rt.freeMemory();
        return value;
    }

    public static double usedMemoryBytes() {
        double total = totalMemoryBytes();
        double free = freeMemoryBytes();
        double used = total - free;
        if (used < 0.0) {
            used = 0.0;
        }
        return used;
    }

    public static double maxMemoryMB() {
        return maxMemoryBytes() / 1024.0 / 1024.0;
    }

    public static double totalMemoryMB() {
        return totalMemoryBytes() / 1024.0 / 1024.0;
    }

    public static double freeMemoryMB() {
        return freeMemoryBytes() / 1024.0 / 1024.0;
    }

    public static double usedMemoryMB() {
        return usedMemoryBytes() / 1024.0 / 1024.0;
    }

    public static double maxMemoryGB() {
        return maxMemoryBytes() / 1024.0 / 1024.0 / 1024.0;
    }

    public static double processorCount() {
        double value = (double) Runtime.getRuntime().availableProcessors();
        return value;
    }

    public static String javaVersion() {
        String value = System.getProperty("java.version");
        if (value == null) {
            value = "unknown";
        }
        return value;
    }

    public static String javaHome() {
        String value = System.getProperty("java.home");
        if (value == null) {
            value = "";
        }
        return value;
    }

    public static String jvmName() {
        String value = System.getProperty("java.vm.name");
        if (value == null) {
            value = "unknown jvm";
        }
        return value;
    }

    public static String osName() {
        String value = System.getProperty("os.name");
        if (value == null) {
            value = "unknown os";
        }
        return value;
    }

    public static String osArch() {
        String value = System.getProperty("os.arch");
        if (value == null) {
            value = "unknown arch";
        }
        return value;
    }

    public static String fullReport() {
        String line1 = "Java版本: " + javaVersion();
        String line2 = "虚拟机: " + jvmName();
        String line3 = "操作系统: " + osName() + " " + osArch();
        String line4 = "处理器核心: " + TextTool.formatScore(processorCount());
        String line5 = "最大堆内存: " + TextTool.formatMemoryMB(maxMemoryBytes());
        String line6 = "已用堆内存: " + TextTool.formatMemoryMB((long) usedMemoryBytes());
        return line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5 + "\n" + line6;
    }

    public static String shortReport() {
        return "JDK " + javaVersion() + " | 最大堆 " + TextTool.formatMemoryMB(maxMemoryBytes());
    }

    public static double suggestMemoryMB() {
        double maxMb = maxMemoryMB();
        double suggested = 512.0;
        if (maxMb < 256.0) {
            suggested = 128.0;
        } else if (maxMb < 512.0) {
            suggested = 256.0;
        } else if (maxMb < 1024.0) {
            suggested = 512.0;
        } else {
            suggested = 1024.0;
        }
        return suggested;
    }

    public static void gc() {
        Runtime.getRuntime().gc();
    }

    public static void gc(double times) {
        for (double i = 0.0; i < times; i += 1.0) {
            Runtime.getRuntime().gc();
        }
    }
}
