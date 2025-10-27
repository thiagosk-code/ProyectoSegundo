package baseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import logica.PersonajePartidaInfo;

public class PartidaUsuario {

    public PersonajePartidaInfo obtenerDetallesPartida(int idPartidaSlot, String correo) throws SQLException {
        
        String query =
            "SELECT " +
            "  PJ.Nombre AS Nombre_Personaje, " +
            "  PP.Vida_Act AS Vida_Actual, " +
            "  PP.Mana_Act AS Mana_Actual, " +
            "  PP.Vida_Max AS Vida_Max, " + 
            "  PP.Mana_Max AS Mana_Max, " + 
            "  PP.Dano AS Dano_Partida, " + 
            "  PP.Descripcion AS Descripcion, " +
            "  PU.ID_partida_global AS idPartida, " +
            "  GROUP_CONCAT(H.Nombre SEPARATOR ', ') AS Lista_Habilidades " +
            "FROM Usuario U " +
            "JOIN Partida_Usuario PU ON U.id_usuario = PU.ID_usuario " +
            "JOIN Partida P ON PU.ID_partida_global = P.ID_partida " +
            "JOIN Personaje_Partida PP ON P.ID_partida = PP.ID_partida " +
            "JOIN Personaje PJ ON PP.ID_personaje = PJ.ID_personaje " +
            "LEFT JOIN Personaje_Habilidad PH ON PJ.ID_personaje = PH.ID_personaje " +
            "LEFT JOIN Habilidades H ON PH.ID_Habilidad = H.ID_habilidad " +
            "WHERE U.correo = ? AND PU.ID_partida_slot = ? " + 
            "GROUP BY PJ.Nombre, PP.Vida_Act, PP.Mana_Act, PP.Vida_Max, PP.Mana_Max, PP.Dano, PP.Descripcion, PU.ID_partida_global"; 

            
        ConexionContra CC = new ConexionContra(); 
     
        try (Connection conn = CC.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (conn == null) throw new SQLException("Conexión a BD es null");

            ps.setString(1, correo);
            ps.setInt(2, idPartidaSlot); 

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PersonajePartidaInfo info = new PersonajePartidaInfo();

                    info.setIdPartida(rs.getInt("idPartida"));
                    
                    info.setIdPartidaSlot(idPartidaSlot);
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
            System.err.println("Error en obtenerDetallesPartida: " + e.getMessage());
            throw e;
        }
        
        return null; 
    }
    
    // --------------------------------------------------------------------------------

    public PersonajePartidaInfo obtenerPartidaExistente(int idPartidaSlot, String correo) throws SQLException {
        return obtenerDetallesPartida(idPartidaSlot, correo);
    }
    
    // --------------------------------------------------------------------------------
 
    public PersonajePartidaInfo crearNuevaPartida(int idPartidaSlot, String correo) throws SQLException {
        
        ConexionContra cc = new ConexionContra();
        int idUsuario = obtenerIdUsuario(correo); 
        int idPartidaGenerado = 0;
        int idPersonajeDefault = obtenerIdPersonajeDefault();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        PersonajePartidaInfo infoPersonaje = null;
        
        try (Connection conn = cc.conectar();
             PreparedStatement psPartida = conn.prepareStatement(
                 "INSERT INTO Partida (Fecha_creacion, Fecha_ultimo_registro, Baja_logica_Habilitado) VALUES (?, ?, FALSE)",
                 Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psPersonajePartida = conn.prepareStatement(
                 "INSERT INTO Personaje_Partida (ID_partida, ID_personaje, Vida_Act, Mana_Act, Vida_Max, Mana_Max, Dano, Descripcion) " +
                 "SELECT ?, ID_personaje, Vida_Ini, Mana_Ini, Vida_Ini, Mana_Ini, Dano_Ini, Descripcion " +
                 "FROM Personaje WHERE ID_personaje = ?");
             PreparedStatement psPartidaUsuario = conn.prepareStatement(
                 "INSERT INTO Partida_Usuario (ID_partida_global, ID_usuario, ID_partida_slot) VALUES (?, ?, ?)")
             ) {

            psPartida.setTimestamp(1, timestamp);
            psPartida.setTimestamp(2, timestamp);
            psPartida.executeUpdate();

            try (ResultSet rs = psPartida.getGeneratedKeys()) {
                if (rs.next()) {
                    idPartidaGenerado = rs.getInt(1);
                }
            }
            
            if (idPartidaGenerado == 0 || idUsuario == -1 || idPersonajeDefault == -1) {
                throw new SQLException("Fallo al obtener IDs para la nueva partida o usuario inválido.");
            }
            
            psPersonajePartida.setInt(1, idPartidaGenerado);
            psPersonajePartida.setInt(2, idPersonajeDefault); 
            psPersonajePartida.executeUpdate();

            psPartidaUsuario.setInt(1, idPartidaGenerado);
            psPartidaUsuario.setInt(2, idUsuario);
            psPartidaUsuario.setInt(3, idPartidaSlot);
            psPartidaUsuario.executeUpdate();
            
            infoPersonaje = obtenerDetallesPartida(idPartidaSlot, correo);


        } catch (SQLException e) {
            System.err.println("Error al crear la nueva partida: " + e.getMessage());
            throw e;
        }

        return infoPersonaje;
    }
    
    // --------------------------------------------------------------------------------
    
    private int obtenerIdUsuario(String correo) throws SQLException {
        String query = "SELECT id_usuario FROM Usuario WHERE correo = ?";
        ConexionContra cc = new ConexionContra();
        
        try (Connection conn = cc.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de usuario: " + e.getMessage());
            throw e;
        }
        return -1; 
    }
    
    // --------------------------------------------------------------------------------
    
    private int obtenerIdPersonajeDefault() throws SQLException {
        ConexionContra cc = new ConexionContra();
        int candidato = 1; 
        
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
    
    public boolean actualizarPersonajePartida(PersonajePartidaInfo info) throws SQLException {
        
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
            System.err.println("Error al actualizar Personaje_Partida: " + e.getMessage());
            throw e;
        }
    }
}
