package logica;

import java.sql.SQLException;

public class PartidaController {
	
   
    private final Dao partidaDao; 

    public PartidaController() {
     
        this.partidaDao = new Dao();
    }

    public PersonajePartidaInfo obtenerDetallesPartida(int idPartida, String correoUsuario) throws SQLException {
        if (idPartida < 0) {
            System.out.println("ID de partida inválido");
            return null;
        }
       
        return partidaDao.obtenerDetallesPartida(idPartida, correoUsuario);
    }
}