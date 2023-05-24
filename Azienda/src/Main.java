import GUI.MenuPrincipaleGUI;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

/**
 * The type Main.
 */
public class Main
{
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        // Imposta il Look and Feel di FlatLaf come Look and Feel predefinito del progetto
        FlatDarkLaf.installLafInfo();
        // Imposta il Look and Feel di FlatLaf come Look and Feel corrente per la finestra
        UIManager.setLookAndFeel(new FlatDarkLaf());
        new MenuPrincipaleGUI();
    }

}

