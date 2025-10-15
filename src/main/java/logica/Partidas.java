package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import baseDeDatos.ConexionContra;


public class Partidas {


	
public PersonajePartidaInfo obtenerDetallesPartida(int idPartida) throws SQLException {
		String queryVida = "SELECT \r\n"
				+ "    P.ID_partida,\r\n"
				+ "    PP.ID_personaje_partida,\r\n"
				+ "    PP.Nombre AS Nombre_Personaje,\r\n"
				+ "    PP.Vida_Actual,\r\n"
				+ "    PP.Mana_Actual,\r\n"
				+ "    PP.Dano_Actual,\r\n"
				+ "    PJ.ID_personaje,\r\n"
				+ "    PJ.Vida_Max AS Vida_Base,\r\n"
				+ "    PJ.Mana_Max AS Mana_Base,\r\n"
				+ "    PJ.Dano AS Dano_Base\r\n"
				+ "FROM Usuario U\r\n"
				+ "JOIN Partida_Usuario PU ON U.id_usuario = PU.ID_usuario\r\n"
				+ "JOIN Partida P ON PU.ID_partida = P.ID_partida\r\n"
				+ "JOIN Partida_Personaje_Partida PPP ON P.ID_partida = PPP.ID_partida\r\n"
				+ "JOIN Personaje_Partida PP ON PPP.ID_personaje_partida = PP.ID_personaje_partida\r\n"
				+ "JOIN Personaje_Personaje_Partida PJP ON PP.ID_personaje_partida = PJP.ID_personaje_partida\r\n"
				+ "JOIN Personaje PJ ON PJP.ID_personaje = PJ.ID_personaje\r\n"
				+ "WHERE ID_partida =? and U.correo = ?";
		
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
		return null;
		
	}
	

}
	
	

