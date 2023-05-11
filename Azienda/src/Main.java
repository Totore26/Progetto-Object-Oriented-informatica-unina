import GUI.MenuPrincipale;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main
{
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        System.out.println("GRANDE RAIMONDO!");
        // Imposta il Look and Feel di FlatLaf come Look and Feel predefinito del tuo progetto
        FlatDarkLaf.installLafInfo();
        // Imposta il Look and Feel di FlatLaf come Look and Feel corrente per la finestra
        UIManager.setLookAndFeel(new FlatDarkLaf());
        new MenuPrincipale();

    }

}


