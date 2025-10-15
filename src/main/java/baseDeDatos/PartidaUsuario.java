package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logica.PersonajePartidaInfo;

public class PartidaUsuario {



	 public PersonajePartidaInfo obtenerDetallesPartida(int idPartida, String correo) throws SQLException {

	        String query = "SELECT "
	        	    + "PJ.Nombre AS Nombre_Personaje, "
	        	    + "PP.Vida_Actual, "
	        	    + "PP.Mana_Actual, "
	        	    + "PP.Dano_Actual, "
	        	    + "PJ.Vida_Max AS Vida_Base, "
	        	    + "PJ.Mana_Max AS Mana_Base, "
	        	    + "PJ.Dano AS Dano_Base, "
	        	    + "PP.Descripcion AS Descripcion_Base, "
	        	    + "GROUP_CONCAT(H.Nombre SEPARATOR ', ') AS Lista_Habilidades "
	        	    + "FROM Usuario U "
	        	    + "JOIN Partida_Usuario PU ON U.id_usuario = PU.ID_usuario "
	        	    + "JOIN Partida P ON PU.ID_partida = P.ID_partida "
	        	    + "JOIN Partida_Personaje_Partida PPP ON P.ID_partida = PPP.ID_partida "
	        	    + "JOIN Personaje_Partida PP ON PPP.ID_personaje_partida = PP.ID_personaje_partida "
	        	    + "JOIN Personaje_Personaje_Partida PJP ON PP.ID_personaje_partida = PJP.ID_personaje_partida "
	        	    + "JOIN Personaje PJ ON PJP.ID_personaje = PJ.ID_personaje "
	        	    + "LEFT JOIN Personaje_Habilidad PH ON PJ.ID_personaje = PH.ID_personaje "
	        	    + "LEFT JOIN Habilidades H ON PH.ID_Habilidad = H.ID_Habilidad "
	        	    + "WHERE P.ID_partida = ? AND U.correo = ? " 		
	        	    + "GROUP BY "
	        	    + "PJ.Nombre, PP.Vida_Actual, PP.Mana_Actual, PP.Dano_Actual, "
	        	    + "PJ.Vida_Max, PJ.Mana_Max, PJ.Dano, PP.Descripcion";
	        
	        ConexionContra CC = new ConexionContra();

	    
	        try (Connection conn = CC.conectar();
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, idPartida);
	            ps.setString(2, correo);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    PersonajePartidaInfo info = new PersonajePartidaInfo();

	                    // El Mapeo de los atributos que se van a mostrar en la web 
	                    info.setNombrePersonaje(rs.getString("Nombre_Personaje"));
	                    info.setVida_Actual(rs.getInt("Vida_Actual"));
	                    info.setMana_Actual(rs.getInt("Mana_Actual"));
	                    info.setDano_Actual(rs.getInt("Dano_Actual"));
	                    info.setVida_Max(rs.getInt("Vida_Base"));
	                    info.setMana_Max(rs.getInt("Mana_Base"));
	                    info.setDano_Base(rs.getInt("Dano_Base"));
	                    info.setDescripcion(rs.getString("Descripcion_Base")); 
	                    info.setListaHabilidades(rs.getString("Lista_Habilidades"));

	                    return info;
	                }
	            }
	        }
	    
	        return null; 
	}


	

	
	        }
 
	    	

	












