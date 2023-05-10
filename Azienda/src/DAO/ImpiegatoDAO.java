package DAO;

public interface ImpiegatoDAO {

    public boolean addImpiegatoDAO();

        /*
    @Override
    // Aggiunge un impiegato nel database
    public boolean addImpiegatoDAO(Impiegato imp) {
        try {
            PreparedStatement insertImp;
            insertImp = connection.prepareStatement("INSERT INTO IMPIEGATO (matricola, nome, cognome, codice_fiscale, curriculum, dirigente, tipo_impiegato, data_assunzione, data_licenziamento, stipendio, sesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertImp.setString(1, imp.getMatricola());
            insertImp.setString(2, imp.getNome());
            insertImp.setString(3, imp.getCognome());
            insertImp.setString(4, imp.getCodiceFiscale());
            insertImp.setString(5, imp.getCurriculum());
            insertImp.setBoolean(6, imp.isDirigente());
            insertImp.setString(7, imp.getTipoImpiegato());
            insertImp.setString(8, imp.getDataAssunzione());
            insertImp.setString(9, imp.getDataLicenziamento());
            insertImp.setFloat(10, imp.getStipendio());
            insertImp.setString(11, imp.getSesso());
            int result = insertImp.executeUpdate();
            if (result == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
         */

    public boolean removeImpiegatoDAO();

    public boolean modifyImpiegatoDAO();
}



