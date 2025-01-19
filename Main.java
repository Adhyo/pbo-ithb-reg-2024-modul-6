import javax.swing.SwingUtilities;

import Controller.PendudukController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PendudukController();
        });
    }
}
