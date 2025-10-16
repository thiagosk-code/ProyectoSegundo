package baseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logica.PersonajePartidaInfo;
import logica.cuentas;

public class PartidaUsuario {

    public PersonajePartidaInfo obtenerDetallesPartida(int idPartida, String correo) throws SQLException {
        
        String query =
            "SELECT " +
            "  PJ.Nombre AS Nombre_Personaje, " +
            "  PP.Vida_Act AS Vida_Actual, " +
            "  PP.Mana_Act AS Mana_Actual, " +
            "  PJ.Vida_Ini AS Vida_Base, " +
            "  PJ.Mana_Ini AS Mana_Base, " +
            "  PJ.Dano_Ini AS Dano_Base, " +
            "  GROUP_CONCAT(H.Nombre SEPARATOR ', ') AS Lista_Habilidades " +
            "FROM Usuario U " +
            "JOIN Partida_Usuario PU ON U.id_usuario = PU.ID_usuario " +
            "JOIN Partida P ON PU.ID_partida = P.ID_partida " +
            "JOIN Partida_Personaje_Partida PPP ON P.ID_partida = PPP.ID_partida " +
            "JOIN Personaje_Partida PP ON PPP.ID_personaje_partida = PP.ID_personaje_partida " +
            "JOIN Personaje_Personaje_Partida PJP ON PP.ID_personaje_partida = PJP.ID_personaje_partida " +
            "JOIN Personaje PJ ON PJP.ID_personaje = PJ.ID_personaje " +
            "LEFT JOIN Personaje_Habilidad PH ON PJ.ID_personaje = PH.ID_personaje " +
            "LEFT JOIN Habilidades H ON PH.ID_Habilidad = H.ID_habilidad " +
            "WHERE P.ID_partida = ? AND U.correo = ? " +
            "GROUP BY PJ.Nombre, PP.Vida_Act, PP.Mana_Act, PJ.Vida_Ini, PJ.Mana_Ini, PJ.Dano_Ini";

        ConexionContra CC = new ConexionContra();

        try (Connection conn = CC.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (conn == null) throw new SQLException("Conexión a BD es null");

            ps.setInt(1, idPartida);
            ps.setString(2, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PersonajePartidaInfo info = new PersonajePartidaInfo();

                    info.setNombrePersonaje(rs.getString("Nombre_Personaje"));
                    info.setVida_Actual(rs.getInt("Vida_Actual"));
                    info.setMana_Actual(rs.getInt("Mana_Actual"));
                    info.setVida_Max(rs.getInt("Vida_Base"));
                    info.setMana_Max(rs.getInt("Mana_Base"));
                    info.setDano_Base(20);
                    info.setDano_Actual(20);
                    info.setListaHabilidades(rs.getString("Lista_Habilidades"));

                    return info;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de BD al obtener detalles de partida:");
            e.printStackTrace();
        }

        return null;
    }

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
                    info.setDano_Base(20);
                    info.setDano_Actual(20);
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

        ConexionContra cc = new ConexionContra();
        Connection conn = null;

        try {
            int idPersonajeBaseElegido = obtenerIdPersonajeValido(idPartida, cc);
            if (idPersonajeBaseElegido == -1) return null;

            int vidaBase = 50;
            int manaBase = 50;
            int danoBase = 20;
            String nombrePersonaje = "Niño";

            String sqlReadPersonaje = "SELECT Nombre, Vida_Ini, Mana_Ini FROM Personaje WHERE ID_personaje = ?";
            try (Connection connRead = cc.conectar();
                 PreparedStatement ps = connRead.prepareStatement(sqlReadPersonaje)) {

                ps.setInt(1, idPersonajeBaseElegido);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nombrePersonaje = rs.getString("Nombre");
                        vidaBase = rs.getInt("Vida_Ini");
                        manaBase = rs.getInt("Mana_Ini");
                    }
                }
            }

            if (vidaBase <= 0) vidaBase = 50;
            if (manaBase < 0) manaBase = 50;

            String sqlPartida = "INSERT INTO Partida (Fecha_creación, Fecha_último_registro, Baja_logica_Habilitado) VALUES (NOW(), NOW(), FALSE)";
            String sqlPP = "INSERT INTO Personaje_Partida (Mana_Max, Mana_Act, Vida_Max, Vida_Act, Baja_logica_Habilitado) VALUES (?, ?, ?, ?, FALSE)";
            String sqlPU = "INSERT INTO Partida_Usuario (ID_partida, ID_usuario) VALUES (?, ?)";
            String sqlPPP = "INSERT INTO Partida_Personaje_Partida (ID_partida, ID_personaje_partida) VALUES (?, ?)";
            String sqlPPPB = "INSERT INTO Personaje_Personaje_Partida (ID_personaje, ID_personaje_partida) VALUES (?, ?)";

            conn = cc.conectar();
            if (conn == null) throw new SQLException("Conexión a BD es null");
            conn.setAutoCommit(false);

            int idPartidaGenerado = -1;
            int idPPGenerado = -1;

            try (PreparedStatement stmt = conn.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) idPartidaGenerado = rs.getInt(1);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlPP, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, manaBase);   
                stmt.setInt(2, manaBase);
                stmt.setInt(3, vidaBase);    
                stmt.setInt(4, vidaBase);      
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
                nuevoInfo.setNombrePersonaje(nombrePersonaje);
                nuevoInfo.setVida_Actual(vidaBase);
                nuevoInfo.setMana_Actual(manaBase);
                nuevoInfo.setVida_Max(vidaBase);
                nuevoInfo.setMana_Max(manaBase);
                nuevoInfo.setDano_Base(danoBase);
                nuevoInfo.setDano_Actual(danoBase);

                return nuevoInfo;
            } else {
                conn.rollback();
                return null;
            }

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("Error de BD al crear nueva partida (Rollback):");
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    private int obtenerIdPersonajeValido(int candidato, ConexionContra cc) throws SQLException {
        // 1) comprobar candidato
        String sqlCheck = "SELECT ID_personaje FROM Personaje WHERE ID_personaje = ? LIMIT 1";
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
            ps.setInt(1, candidato);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ID_personaje");
            }
        } catch (SQLException e) {
        }
        
        String sqlFirst = "SELECT ID_personaje FROM Personaje LIMIT 1";
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlFirst);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("ID_personaje");
        }

        String sqlCreate = "INSERT INTO Personaje (Nombre, Mana_Ini, Vida_Ini, Dano_Ini, Baja_logica_Habilitado) VALUES (?, ?, ?, ?, FALSE)";
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "niño");
            ps.setInt(2, 50);
            ps.setInt(3, 50);
            ps.setInt(4, 0);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }

        return -1;
    }
}
