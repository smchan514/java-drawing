package simple_drawing;

import javax.swing.UIManager;

public class AppStarter {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to to look and feel");
            e.printStackTrace();
        }

        MainWindow mainWindow = new MainWindow();

        // Center the window
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setVisible(true);
    }
}
