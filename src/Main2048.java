import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main2048 {
    private static double bootStage;
    private static double bootFlag;

    public static void main(String[] args) {
        bootStage = 0.0;
        bootFlag = 0.0;
        enterBoot(args);
    }

    private static void enterBoot(String[] args) {
        bootStage = 1.0;
        printBanner(args);
        bootStage = 2.0;
        prepareLookAndFeel();
        bootStage = 3.0;
        prepareLookAndFeel(1.0);
        bootStage = 4.0;
        launchWindow(args);
        bootStage = 5.0;
    }

    private static void enterBoot(String[] args, double flag) {
        bootFlag = flag;
        enterBoot(args);
    }

    private static void printBanner(String[] args) {
        String line1 = "==========================================";
        String line2 = "        2048 REDUNDANT EDITION BOOT       ";
        String line3 = "==========================================";
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
        double argCount = 0.0;
        if (args != null) {
            argCount = (double) args.length;
        }
        System.out.println("boot args count = " + argCount);
        System.out.println("java version = " + RuntimeSniffer.javaVersion());
        System.out.println("jvm name = " + RuntimeSniffer.jvmName());
        System.out.println("max heap = " + TextTool.formatMemoryMB((long) RuntimeSniffer.maxMemoryBytes()));
        printBannerExtra(0.0);
    }

    private static void printBannerExtra(double flag) {
        if (flag == 1.0) {
            System.out.println("verbose boot enabled");
        } else {
            System.out.println("boot stage ok");
        }
    }

    private static void prepareLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            tryFallbackLookAndFeel();
        }
    }

    private static void prepareLookAndFeel(double flag) {
        if (flag == 1.0) {
            String name = UIManager.getSystemLookAndFeelClassName();
            if (name != null) {
                if (name.length() > 0) {
                    System.out.println("look and feel = " + name);
                }
            }
        }
    }

    private static void tryFallbackLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex2) {
        }
    }

    private static void launchWindow(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                openLauncher(args);
            }
        });
    }

    private static void launchWindow(final String[] args, double mode) {
        if (mode == 0.0) {
            launchWindow(args);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    openLauncher(args, mode);
                }
            });
        }
    }

    private static void openLauncher(String[] args) {
        LauncherWindow launcher = new LauncherWindow();
        launcher.showLauncher();
    }

    private static void openLauncher(String[] args, double mode) {
        LauncherWindow launcher = new LauncherWindow();
        launcher.showLauncher();
        if (mode == 2.0) {
            launcher.handleCheckEnv();
        }
    }

    private static double getBootStage() {
        return bootStage;
    }

    private static double getBootFlag() {
        return bootFlag;
    }
}
