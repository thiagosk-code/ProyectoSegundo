package logica.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Personajes {

public void RegistrarPersonajes (Connection con, int ID_personaje, int Mana_Max, int Vida_Max, int Dano_Max, String Descripcion, boolean Baja_logica_Habilitado ) {	 
				 
		
		String sql = "INSERT INTO Personaje (ID_personaje, Mana_Max, Vida_Max, Dano_Max, Descripcion, Baja_logica_Habilitado)"
               + "VALUES (? , ?, ?, ?, ?, ?)";
		
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_personaje);
            stmt.setInt(2, Mana_Max);
            stmt.setInt(3, Vida_Max);
            stmt.setInt(4, Dano_Max);
            stmt.setString(5, Descripcion);
            stmt.setBoolean(6, Baja_logica_Habilitado);
            stmt.executeUpdate();
            System.out.println("Personaje Creado Correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
     
            
            // Convierte los datos int y boolean a String
            String[] datosPersonaje = {
                String.valueOf(ID_personaje),
                String.valueOf(Mana_Max),
                String.valueOf(Vida_Max),
                String.valueOf(Dano_Max),
                Descripcion,
                String.valueOf(Baja_logica_Habilitado)
            };

            // Imprime en pantalla el array
            System.out.println(Arrays.toString(datosPersonaje));
        }
         
        }


	public void MostrarPersonaje() {
	  String sql = "SELECT * FROM Personaje";
 	    try (
 	    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
  	        Statement stmt = con.createStatement();
  	        ResultSet rs = stmt.executeQuery(sql)
  	    ) 
 	    		
 	    	{
  	        System.out.println("Estadisticas Personaje");
  	        while (rs.next()) {
  	            int Id = rs.getInt("ID_personaje");
  	            int Mana_Max = rs.getInt("Mana_Max");
  	            int Vida_Max = rs.getInt("Vida_Max");
  	            int Dano_Max = rs.getInt("Dano_Max");
  	            String Descrpcion = rs.getString("Descripcion");
  	            Boolean Baja_logica_Habilitado = rs.getBoolean("Baja_logica_Habilitado");       
  	            
  	        }
  	    } catch (Exception e) {
  	        System.out.println("Error al obtener los datos de los personajes.");
  	        e.printStackTrace();
  	    }
  	}
  	
	
}
