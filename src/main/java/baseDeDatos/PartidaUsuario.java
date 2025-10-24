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
            "  PP.Vida_Max AS Vida_Max, " + 
            "  PP.Mana_Max AS Mana_Max, " + 
            "  PP.Dano AS Dano_Partida, " + 
            "  PP.Descripcion AS Descripcion, " +
            "  GROUP_CONCAT(H.Nombre SEPARATOR ', ') AS Lista_Habilidades " +
            "FROM Usuario U " +
            "JOIN Partida_Usuario PU ON U.id_usuario = PU.ID_usuario " +
            "JOIN Partida P ON PU.ID_partida = P.ID_partida " +
            "JOIN Personaje_Partida PP ON P.ID_partida = PP.ID_partida " +
            "JOIN Personaje PJ ON PP.ID_personaje = PJ.ID_personaje " +
            "LEFT JOIN Personaje_Habilidad PH ON PJ.ID_personaje = PH.ID_personaje " +
            "LEFT JOIN Habilidades H ON PH.ID_Habilidad = H.ID_habilidad " +
            "WHERE P.ID_partida = ? AND U.correo = ? " +
            "GROUP BY PJ.Nombre, PP.Vida_Act, PP.Mana_Act, PP.Vida_Max, PP.Mana_Max, PP.Dano, PP.Descripcion"; 

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
                    info.setVida_Max(rs.getInt("Vida_Max")); 
                    info.setMana_Max(rs.getInt("Mana_Max"));            
                    info.setDano(rs.getInt("Dano_Partida"));
                    info.setDescripcion(rs.getString("Descripcion")); 
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

// --------------------------------------------------------------------------------
// MÉTODO EXISTENTE: obtenerPartidaExistente
// --------------------------------------------------------------------------------
    public PersonajePartidaInfo obtenerPartidaExistente(int idPartida, String correo) {
        int idUsuario = cuentas.CorreoID(correo);
        if (idUsuario <= 0) return null;

        String sql = "SELECT ppi.Vida_Act AS Vida_Act, ppi.Mana_Act AS Mana_Act, ppi.Dano AS Dano_Partida, p.Nombre AS Nombre, ppi.Descripcion AS Descripcion, pu.ID_partida AS ID_partida, " 
                   + "ppi.Vida_Max AS Vida_Max, ppi.Mana_Max AS Mana_Max "
                   + "FROM Partida_Usuario pu "
                   + "JOIN Usuario u ON pu.ID_usuario = u.id_usuario "
                   + "JOIN Personaje_Partida ppi ON pu.ID_partida = ppi.ID_partida " 
                   + "JOIN Personaje p ON ppi.ID_personaje = p.ID_personaje " 
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
                    int danoEnPartida = rs.getInt("Dano_Partida");
                    
                    info.setIdPartida(rs.getInt("ID_partida"));
                    info.setNombrePersonaje(rs.getString("Nombre"));
                    info.setVida_Actual(rs.getInt("Vida_Act"));
                    info.setMana_Actual(rs.getInt("Mana_Act"));
                    
                    info.setVida_Max(rs.getInt("Vida_Max"));
                    info.setMana_Max(rs.getInt("Mana_Max"));
                    
                    info.setDano(danoEnPartida);
                    info.setDescripcion(rs.getString("Descripcion")); 
                    return info;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error de BD al buscar partida existente:");
            e.printStackTrace();
        }
        return null;
    }
// --------------------------------------------------------------------------------
    
    public PersonajePartidaInfo crearNuevaPartida(int idPartida, String correo) {
        int idUsuario = cuentas.CorreoID(correo);
        if (idUsuario <= 0) return null;

        ConexionContra cc = new ConexionContra();
        Connection conn = null;

        final String descripcion_defecto = "Un niño valiente, pero inexperto, en una tierra de glitches.";

        try {
            int idPersonajeBaseElegido = obtenerIdPersonajeValido(idPartida, cc);
            if (idPersonajeBaseElegido == -1) return null;

            int vidaBase = 20; 
            int manaBase = 20; 
            int danoBase = 20; 
            String nombrePersonaje = "Niño"; 
            String descripcion = descripcion_defecto; 

            String sqlReadPersonaje = "SELECT Nombre, Vida_Ini, Mana_Ini, Dano_Ini, Descripcion FROM Personaje WHERE ID_personaje = ?";
            try (Connection connRead = cc.conectar();
                 PreparedStatement ps = connRead.prepareStatement(sqlReadPersonaje)) {

                ps.setInt(1, idPersonajeBaseElegido);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nombrePersonaje = rs.getString("Nombre");
                        vidaBase = rs.getInt("Vida_Ini");
                        manaBase = rs.getInt("Mana_Ini");
                        danoBase = rs.getInt("Dano_Ini");
                        descripcion = rs.getString("Descripcion");
                        
                        if (descripcion == null || descripcion.trim().isEmpty()) {
                            descripcion = descripcion_defecto;
                        }
                    }
                }
            }

            if (vidaBase <= 0) vidaBase = 20;
            if (manaBase < 0) manaBase = 20;
            if (danoBase < 0) danoBase = 20;

            String sqlPartida = "INSERT INTO Partida (Fecha_creación, Fecha_último_registro, Baja_logica_Habilitado) VALUES (NOW(), NOW(), FALSE)";
            String sqlPP = "INSERT INTO Personaje_Partida (ID_partida, ID_personaje, Mana_Max, Mana_Act, Vida_Max, Vida_Act, Dano, Descripcion, Baja_logica_Habilitado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, FALSE)";
            String sqlPU = "INSERT INTO Partida_Usuario (ID_partida, ID_usuario) VALUES (?, ?)";

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
                stmt.setInt(1, idPartidaGenerado);         // ID_partida
                stmt.setInt(2, idPersonajeBaseElegido);    // ID_personaje
                stmt.setInt(3, manaBase);                  // Mana_Max
                stmt.setInt(4, manaBase);                  // Mana_Act
                stmt.setInt(5, vidaBase);                  // Vida_Max
                stmt.setInt(6, vidaBase);                  // Vida_Act
                stmt.setInt(7, danoBase);                  // Dano (de Dano_Ini)
                stmt.setString(8, descripcion);            // Descripcion
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

                conn.commit();

                PersonajePartidaInfo nuevoInfo = new PersonajePartidaInfo();
                nuevoInfo.setIdPartida(idPartidaGenerado);
                nuevoInfo.setNombrePersonaje(nombrePersonaje);
                nuevoInfo.setVida_Actual(vidaBase);
                nuevoInfo.setMana_Actual(manaBase);
                nuevoInfo.setVida_Max(vidaBase);
                nuevoInfo.setMana_Max(manaBase);
                nuevoInfo.setDano(danoBase); 
                nuevoInfo.setDescripcion(descripcion);

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

        final String descripcion_por_defecto = "Un niño valiente, pero inexperto, en una tierra de glitches.";
        String sqlCreate = "INSERT INTO Personaje (Nombre, Mana_Ini, Vida_Ini, Dano_Ini, Descripcion, Baja_logica_Habilitado) VALUES (?, ?, ?, ?, ?, FALSE)";
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "Niño");
            ps.setInt(2, 20); 
            ps.setInt(3, 20); 
            ps.setInt(4, 20); 
            ps.setString(5, descripcion_por_defecto);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }

        return -1;
    }
    
// --------------------------------------------------------------------------------
// MÉTODO NUEVO: actualizarPersonajePartida (DAO de Guardado)
// --------------------------------------------------------------------------------
    public boolean actualizarPersonajePartida(PersonajePartidaInfo info) throws SQLException {
        
        // Solo actualiza los stats dinámicos (Vida_Act, Mana_Act, Dano)
        String sql = "UPDATE Personaje_Partida SET "
                   + "Vida_Act = ?, "
                   + "Mana_Act = ?, "
                   + "Dano = ? "
                   + "WHERE ID_partida = ?"; 

        ConexionContra cc = new ConexionContra();
        
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) throw new SQLException("Conexión a BD es null");

            ps.setInt(1, info.getVida_Actual());
            ps.setInt(2, info.getMana_Actual());
            ps.setInt(3, info.getDano());
            ps.setInt(4, info.getIdPartida()); 

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error de BD al actualizar Personaje_Partida:");
            e.printStackTrace();
            throw e; 
        }
    }
}