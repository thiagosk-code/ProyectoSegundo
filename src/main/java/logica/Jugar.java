package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import baseDeDatos.ConexionContra;

public class Jugar {
	
	public static boolean VerificarInicio1(int idPartida) {
		String sql = "SELECT baja_logica_habilitado FROM recorridos WHERE ID_recorrido IN (\r\n"
				+ "SELECT par.ID_recorrido FROM partida_recorridos par\r\n"
				+ "JOIN recorridos rec ON par.ID_recorrido = rec.ID_recorrido\r\n"
				+ "WHERE par.ID_recorrido = 1 AND par.ID_partida = ?)";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setInt(1, idPartida);
		    ResultSet rs = stmt.executeQuery();
		    return rs.getBoolean("baja_logica_habilitado");
	    }catch (SQLException ex) {
	    	System.out.print("error al verificar si se guardo el inicio");
	    	return true;
	    }	
	}
	
	public static String Inicio1 (int idPartida) {
		String sql = "UPDATE recorridos rec\r\n"
				+ "JOIN partida_recorridos par ON rec.ID_recorrido = par.ID_recorrido\r\n"
				+ "SET rec.baja_logica_habilitado = false\r\n"
				+ "WHERE par.ID_recorrido = 1 AND par.ID_partida = 1";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setInt(1, idPartida);
		    ResultSet rs = stmt.executeQuery();
		    return "En un barrio humilde vivía un niño cuyos sueños superaban los recursos de su familia. Anhelaba una computadora capaz de llevarlo a mundos desconocidos, pero sus bolsillos estaban casi vacíos. Una noche, mientras deambulaba por la calle principal, escuchó rumores de un hombre que vendía ordenadores a precios insólitamente bajos desde una vieja camioneta aparcada bajo el parpadeo de un farol."
				+ "Movido por la esperanza, el niño se acercó al improvisado puesto y, tras un breve regateo, compró la máquina por el único dinero que tenía. Con unas monedas aún en la palma de la mano, decidió invertirlas en un objeto adicional.\r\n"
				+ "Elige:"
				+ "1) Hachas de un guerrero muy prodigioso"
				+ "2) Varita mágica de una maga desconocida"
				+ "3) Arco y flecha de una arquera muy ágil"
				+ "4) Dagas de asesino medieval"
				+ "5) Libro de Dante Alighieri";
	    }catch (SQLException ex) {
	    	System.out.print("error al verificar si se guardo el inicio");
	    	return "";
	    }
	}
	
	public static boolean VerificarInicio2(int idPartida) {
		String sql = "SELECT baja_logica_habilitado FROM recorridos WHERE ID_recorrido IN (\r\n"
				+ "SELECT par.ID_recorrido FROM partida_recorridos par\r\n"
				+ "JOIN recorridos rec ON par.ID_recorrido = rec.ID_recorrido\r\n"
				+ "WHERE par.ID_recorrido = 2 AND par.ID_partida = ?)";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setInt(1, idPartida);
		    ResultSet rs = stmt.executeQuery();
		    return rs.getBoolean("baja_logica_habilitado");
	    }catch (SQLException ex) {
	    	System.out.print("error al verificar si se guardo el inicio");
	    	return true;
	    }	
	}
	
	public static String Inicio2 (int idPartida, int idPersonaje) {
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    int mana;
		int vida;
		int dano;
		String sql1 = "SELECT * FROM personaje WHERE ID_personaje = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
	    	stmt.setInt(1, idPersonaje);
		    ResultSet rs = stmt.executeQuery();
		    mana = rs.getInt("Mana_Ini");
		    vida = rs.getInt("Vida_Ini");
		    dano = rs.getInt("Dano_Ini");
	    }catch (SQLException ex) {
	    	System.out.print("error al verificar si se guardo el inicio");
	    	return "";
	    }
		
		String sql2 = "INSERT INTO personaje_partida (ID_partida,ID_personaje,Mana_Max,Mana_Act,Vida_Max,Vida_Act,Dano) VALUES (?,?,?,?,?,?,?)";
	    try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
	    	stmt.setInt(1, idPartida);
	    	stmt.setInt(2, idPersonaje);
	    	stmt.setInt(3, mana);
	    	stmt.setInt(4, mana);
	    	stmt.setInt(5, vida);
	    	stmt.setInt(6, vida);
	    	stmt.setInt(7, dano);
		    ResultSet rs = stmt.executeQuery();
		    return "Un niño, emocionado por usar un nuevo objeto en sus juegos virtuales, conecta su computadora. "
		    		+ "Al hacerlo, descubre un ícono llamado \"glitch\", y al abrirlo, la pantalla se funde en colores,"
		    		+ "se apaga y la computadora muere tras una descarga eléctrica. Frustrado, se va a dormir."
		    		+ "Al despertar, ya no está en su habitación, sino en un bosque brumoso. Allí, un anciano le explica que ha entrado en un mundo virtual"
		    		+ " y la única forma de volver a casa es atravesar una serie de zonas desafiantes. "
		    		+ "Para su aventura, deberá usar unos dados mágicos de veinte caras que determinarán su suerte y acciones, desatando diferentes poderes."
		    		+ "Con el objeto de su mundo real en mano, el niño emprende con valentía el sendero hacia la primera zona,"
		    		+ " impulsado por la determinación de regresar a su hogar.";
	    }catch (SQLException ex) {
	    	System.out.print("error al verificar si se guardo el inicio");
	    	return "";
	    }
	}
	
}
