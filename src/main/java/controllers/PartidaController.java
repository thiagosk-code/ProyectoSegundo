package controllers;

import java.sql.SQLException;
import baseDeDatos.PartidaUsuario;
import logica.PersonajePartidaInfo;

public class PartidaController {

    private final PartidaUsuario Partida;

    public PartidaController() {
        this.Partida = new PartidaUsuario();
    }

    public PersonajePartidaInfo obtenerDetallesPartida(int idPartidaSlot, String correo) throws SQLException {
        return this.Partida.obtenerDetallesPartida(idPartidaSlot, correo);
    }

    public PersonajePartidaInfo obtenerPartidaExistente(int idPartidaSlot, String correo) throws SQLException {
        try {
            return this.Partida.obtenerPartidaExistente(idPartidaSlot, correo);
        } catch (Exception e) {
             throw new SQLException(e);
        }
    }

    public PersonajePartidaInfo crearNuevaPartida(int idPartidaSlot, String correo) throws SQLException {
        return this.Partida.crearNuevaPartida(idPartidaSlot, correo);
    }
    
    public boolean actualizarPartida(PersonajePartidaInfo info) throws SQLException {
        return this.Partida.actualizarPersonajePartida(info);
    }
}
