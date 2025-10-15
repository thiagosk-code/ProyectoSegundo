
package controllers;

import java.sql.SQLException;
import baseDeDatos.PartidaUsuario;
import logica.PersonajePartidaInfo;

public class PartidaController {
	
   
    private final PartidaUsuario Partida;
	
	

    public PartidaController() {
     
        this.Partida = new PartidaUsuario();
    }

    public PersonajePartidaInfo obtenerDetallesPartida(int idPartida, String correo) throws SQLException {
        if (idPartida < 0) {
            System.out.println("ID de partida inválido");
            return null;
        }
       
        return Partida.obtenerDetallesPartida(idPartida, correo);
    }
}
