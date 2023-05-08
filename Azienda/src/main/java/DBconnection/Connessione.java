package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connessione {

    private static Connessione istanza;
    public Connection connection = null;
    private String url = "jdbc:postgresql://horton.db.elephantsql.com/kbqxtmfi";
    private String nome = "kbqxtmfi";
    private String password = "OxqIttx3hRPeQE0pT0hdZTJdK1MWipce";
    private String driver = "org.postgresql.Driver";

    // COSTRUTTORE
    public Connessione() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public static Connessione getInstance() throws SQLException {
        if (istanza == null) {
            istanza = new Connessione();
        } else if (istanza.getConnection().isClosed()) {
            istanza = new Connessione();
        }
        return istanza;
    }


}