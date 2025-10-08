package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Imagenes {

	
public void RegistrarImagenes (Connection con, int ID_imagen, String Nombre, String URL, boolean Baja_logica_Habilitado ) {	 
				 
		
		String sql = "INSERT INTO Imagenes (ID_imagen, Nombre, URL, Baja_logica_Habilitado)"
               + "VALUES (? , ?, ?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_imagen);
            stmt.setString(2, Nombre);
            stmt.setString(3, URL);
            stmt.setBoolean(4, Baja_logica_Habilitado);
            stmt.executeUpdate();
            System.out.println("Imagen Correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
     
        }
         
		
		public void MostrarImagenes() {
			  String sql = "SELECT * FROM Imagenes";
		 	    
			  try (
		 	    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
		  	        Statement stmt = con.createStatement();
		  	        ResultSet rs = stmt.executeQuery(sql)
		  	    ) 
		 	    		
		 	    	{
		  	        System.out.println("Imagenes");
		  	        while (rs.next()) {
		  	            int ID_imagen = rs.getInt("ID_imagen");
		  	            String Nombre = rs.getString("Nombre");
		  	            String URL = rs.getString("URL");
		  	            Boolean Baja_logica_Habilitado = rs.getBoolean("Baja_logica_Habilitado");       
		  	        }
		  	    } catch (Exception e) {
		  	        System.out.println("Error al obtener las imagenes");
		  	        e.printStackTrace();
		  	    }
		}
		  	
			
		


		
		
		
            
            
  }
 
		
		
		
		
		
		
		