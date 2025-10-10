package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PartidaUsuario {

	public void EncontrarPartida (String correo)  {
			String sql = "SELECT P.ID_partida FROM Partida P JOIN Partida_Usuario PU ON P.ID_partida = PU.ID_partida JOIN Usuario U ON U.id_usuario = PU.ID_usuario WHERE U.correo = ?";
				
	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
			
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	     
	    		
	    	{
	    		
	        System.out.println("Estadisticas Personaje Actual");
	        while (rs.next()) {
	            String Correo = rs.getString("Correo");
	
	

	
 }
 }
}
}














