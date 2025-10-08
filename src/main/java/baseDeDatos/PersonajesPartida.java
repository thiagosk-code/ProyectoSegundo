package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonajesPartida {

public void RegistrarPersonajes (Connection con, int ID_personaje_partida, int Mana_Actual, int Vida_Actual, int Dano_Actual, String Nombre, boolean Baja_logica_Habilitado ) {	 
				 
		
		String sql = "INSERT INTO Personaje_Partida (ID_personaje_partida, Mana_Actual, Vida_Actual, Dano_Actual, Nombre, Baja_logica_Habilitado)"
               + "VALUES (? , ?, ?, ?, ?, ?)";
		
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_personaje_partida);
            stmt.setInt(2, Mana_Actual);
            stmt.setInt(3, Vida_Actual);
            stmt.setInt(4, Dano_Actual);
            stmt.setString(5, Nombre);
            stmt.setBoolean(6, Baja_logica_Habilitado);
            stmt.executeUpdate();
            System.out.println("Datos actual correctos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
		
		public void MostrarPersonaje_Partida() {
			  String sql = "SELECT * FROM Personaje_Partida";
		 	    try (
		 	    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
		  	        Statement stmt = con.createStatement();
		  	        ResultSet rs = stmt.executeQuery(sql)
		  	    ) 
		 	    		
		 	    	{
		  	        System.out.println("Estadisticas Personaje Actual");
		  	        while (rs.next()) {
		  	            int Id = rs.getInt("ID_personaje_partida");
		  	            int Mana_Max = rs.getInt("Mana_Actual");
		  	            int Vida_Max = rs.getInt("Vida_Actual");
		  	            int Dano_Max = rs.getInt("Dano_Actual");
		  	            String Nombre = rs.getString("Nombre");
		  	            Boolean Baja_logica_Habilitado = rs.getBoolean("Baja_logica_Habilitado");       
		  	            
		  	        }
		  	    } catch (Exception e) {
		  	        System.out.println("Error al obtener los datos actuales.");
		  	        e.printStackTrace();
		  	    }
		  	

	}
}
