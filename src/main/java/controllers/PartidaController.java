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
        return this.Partida.obtenerDetallesPartida(idPartida, correo);
    }

    public PersonajePartidaInfo obtenerPartidaExistente(int idPartida, String correo) throws SQLException {
        try {
            return this.Partida.obtenerPartidaExistente(idPartida, correo);
        } catch (Exception e) {
             throw new SQLException(e);
        }
    }

    public PersonajePartidaInfo crearNuevaPartida(int idPartida, String correo) throws SQLException {
        return this.Partida.crearNuevaPartida(idPartida, correo);
    }

    // MÉTODO NUEVO PARA GUARDAR
    public boolean actualizarPartida(PersonajePartidaInfo info) throws SQLException {
        return this.Partida.actualizarPersonajePartida(info);
    }
}