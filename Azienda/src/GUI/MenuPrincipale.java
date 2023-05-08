package GUI;





import javax.swing.*;
import java.awt.*;

public class MenuPrincipale {
    private JFrame frame;
    private JLabel labelBenvenuto;
    private JButton bottoneImpiegati;
    private JButton bottoneLaboratori;
    private JButton bottoneProgetti;

    public MenuPrincipale() {
        frame = new JFrame("Menu Principale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea la JLabel di benvenuto
        labelBenvenuto = new JLabel("Benvenuto nell'app Aziendale!");
        labelBenvenuto.setFont(new Font("Arial",Font.PLAIN,18));

        // Crea i bottoni
        bottoneImpiegati = new JButton("Impiegati");
        bottoneLaboratori = new JButton("Laboratori");
        bottoneProgetti = new JButton("Progetti");

        // Imposta il font dei bottoni
        bottoneImpiegati.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneLaboratori.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneProgetti.setFont(new Font("Arial", Font.PLAIN, 16));

        // Imposta il layout del frame come BoxLayout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Imposta l'allineamento orizzontale dei componenti al centro
        labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneImpiegati.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneLaboratori.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneProgetti.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi la JLabel e i bottoni al frame
        frame.add(Box.createVerticalStrut(50));
        frame.add(labelBenvenuto);
        frame.add(Box.createVerticalStrut(20));
        frame.add(bottoneImpiegati);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneLaboratori);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneProgetti);

        Dimension buttonSize = new Dimension(200, 50);
        bottoneImpiegati.setMaximumSize(buttonSize);
        bottoneLaboratori.setMaximumSize(buttonSize);
        bottoneProgetti.setMaximumSize(buttonSize);

        // Imposta le dimensioni del frame, la posizione al centro e rendilo visibile
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipale();
    }
}
