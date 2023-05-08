package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipale extends JFrame {
    JButton employeeButton;
    JButton projectButton;
    JButton labButton;

    public MenuPrincipale() {
        super("Finestra con tre bottoni");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta il layout del frame come BorderLayout
        setLayout(new BorderLayout());

        // Aggiungi un pannello al centro del frame con il layout GridLayout
        JPanel centerPanel = new JPanel(new GridLayout(1, 3));

        // Aggiungi i tre bottoni al pannello centrale
        employeeButton = new JButton("Impiegato");
        projectButton = new JButton("Progetto");
        labButton = new JButton("Laboratorio");

        // Personalizza lo stile dei bottoni
        employeeButton.setFont(new Font("Arial", Font.BOLD, 24));
        projectButton.setFont(new Font("Arial", Font.BOLD, 24));
        labButton.setFont(new Font("Arial", Font.BOLD, 24));

        employeeButton.setForeground(Color.WHITE);
        projectButton.setForeground(Color.WHITE);
        labButton.setForeground(Color.WHITE);

        employeeButton.setBackground(Color.BLUE);
        projectButton.setBackground(Color.GREEN);
        labButton.setBackground(Color.RED);

        // Aggiungi i bottoni al pannello centrale
        centerPanel.add(employeeButton);
        centerPanel.add(projectButton);
        centerPanel.add(labButton);

        // Aggiungi il pannello centrale al frame
        add(centerPanel, BorderLayout.CENTER);

        setSize(500, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipale();
    }
}

