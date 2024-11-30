package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import login.Login;

public class Main {
	public static void main(String[] args) {
        // Establecer el Look and Feel de Nimbus antes de crear la ventana
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear e invocar la ventana de login
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}