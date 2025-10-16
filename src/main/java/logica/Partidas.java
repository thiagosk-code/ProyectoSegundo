package logica;

import baseDeDatos.ConexionContra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Partidas: obtenerPartidaExistente y crearNuevaPartida
 */
public class Partidas {

    public PersonajePartidaInfo obtenerPartidaExistente(int idPartida, String correo) {
        int idUsuario = cuentas.CorreoID(correo);
        if (idUsuario <= 0) return null;

        String sql = "SELECT ppi.Vida_Act AS Vida_Act, ppi.Mana_Act AS Mana_Act, p.Nombre AS Nombre, pu.ID_partida AS ID_partida "
                   + "FROM Partida_Usuario pu "
                   + "JOIN Usuario u ON pu.ID_usuario = u.id_usuario "
                   + "JOIN Partida_Personaje_Partida ppp ON pu.ID_partida = ppp.ID_partida "
                   + "JOIN Personaje_Partida ppi ON ppp.ID_personaje_partida = ppi.ID_personaje_partida "
                   + "JOIN Personaje_Personaje_Partida pppb ON ppi.ID_personaje_partida = pppb.ID_personaje_partida "
                   + "JOIN Personaje p ON pppb.ID_personaje = p.ID_personaje "
                   + "WHERE u.correo = ? AND pu.ID_partida = ? AND ppi.Baja_logica_Habilitado = FALSE";

        ConexionContra cc = new ConexionContra();

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) return null;

            stmt.setString(1, correo);
            stmt.setInt(2, idPartida);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PersonajePartidaInfo info = new PersonajePartidaInfo();
                    info.setIdPartida(rs.getInt("ID_partida"));
                    info.setNombrePersonaje(rs.getString("Nombre"));
                    info.setVida_Actual(rs.getInt("Vida_Act"));
                    info.setMana_Actual(rs.getInt("Mana_Act"));
                    return info;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de BD al buscar partida existente:");
            e.printStackTrace();
        }
        return null;
    }

    public PersonajePartidaInfo crearNuevaPartida(int idPartida, String correo) {
        int idUsuario = cuentas.CorreoID(correo);
        if (idUsuario <= 0) return null;

        int vidaIni = 50;
        int manaIni = 50;
        int idPersonajeBaseElegido = idPartida; // asunción: el idPartida determina el personaje base por slot

        String sqlPartida = "INSERT INTO Partida (Fecha_creación, Fecha_último_registro, Baja_logica_Habilitado) VALUES (NOW(), NOW(), FALSE)";
        String sqlPP = "INSERT INTO Personaje_Partida (Mana_Max, Mana_Act, Vida_Max, Vida_Act, Descripcion, Baja_logica_Habilitado) VALUES (?, ?, ?, ?, ?, FALSE)";
        String sqlPU = "INSERT INTO Partida_Usuario (ID_partida, ID_usuario) VALUES (?, ?)";
        String sqlPPP = "INSERT INTO Partida_Personaje_Partida (ID_partida, ID_personaje_partida) VALUES (?, ?)";
        String sqlPPPB = "INSERT INTO Personaje_Personaje_Partida (ID_personaje, ID_personaje_partida) VALUES (?, ?)";

        ConexionContra cc = new ConexionContra();
        Connection conn = null;
        int idPartidaGenerado = -1;
        int idPPGenerado = -1;

        try {
            conn = cc.conectar();
            if (conn == null) return null;

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) idPartidaGenerado = rs.getInt(1);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlPP, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, manaIni);
                stmt.setInt(2, manaIni);
                stmt.setInt(3, vidaIni);
                stmt.setInt(4, vidaIni);
                stmt.setString(5, "Personaje inicial");
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) idPPGenerado = rs.getInt(1);
                }
            }

            if (idPartidaGenerado > 0 && idPPGenerado > 0) {
                try (PreparedStatement stmt = conn.prepareStatement(sqlPU)) {
                    stmt.setInt(1, idPartidaGenerado);
                    stmt.setInt(2, idUsuario);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlPPP)) {
                    stmt.setInt(1, idPartidaGenerado);
                    stmt.setInt(2, idPPGenerado);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlPPPB)) {
                    stmt.setInt(1, idPersonajeBaseElegido);
                    stmt.setInt(2, idPPGenerado);
                    stmt.executeUpdate();
                }

                conn.commit();

                PersonajePartidaInfo nuevoInfo = new PersonajePartidaInfo();
                nuevoInfo.setIdPartida(idPartidaGenerado);
                nuevoInfo.setNombrePersonaje("niño");
                nuevoInfo.setVida_Actual(vidaIni);
                nuevoInfo.setMana_Actual(manaIni);
                return nuevoInfo;
            } else {
                conn.rollback();
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error de BD al crear nueva partida (Rollback):");
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    // Stub mantenido
    public PersonajePartidaInfo obtenerDetallesPartida(int idPartida) throws SQLException {
        return null;
    }
}
	

