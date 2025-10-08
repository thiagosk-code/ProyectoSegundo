package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Habilidades {

		
		public void RegistrarHabilidades (Connection con, int ID_habilidad, String Nombre, int Mana_Coste, boolean Baja_logica_Habilitado ) {	 
						 
				
				String sql = "INSERT INTO Imagenes (ID_habilidad, Nombre, Mana_Coste, Baja_logica_Habilitado)"
		               + "VALUES (? , ?, ?, ?)";
				
				try (PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, ID_habilidad);
		            stmt.setString(2, Nombre);
		            stmt.setInt(3, Mana_Coste);
		            stmt.setBoolean(4, Baja_logica_Habilitado);
		            stmt.executeUpdate();
		            System.out.println("Habilidaddes Correctamente");
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		     
		        }
		         
				
				public void MostrarHabilidades() {
					  String sql = "SELECT * FROM Habilidades";
				 	    
					  try (
				 	    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
				  	        Statement stmt = con.createStatement();
				  	        ResultSet rs = stmt.executeQuery(sql)
				  	    ) 
				 	    		
				 	    	{
				  	        System.out.println("Estadisticas Personaje");
				  	        while (rs.next()) {
				  	            int ID_habilidad = rs.getInt("ID_imagen");
				  	            String Nombre = rs.getString("Nombre");
				  	            int Mana_Coste= rs.getInt("Mana_Coste");
				  	            Boolean Baja_logica_Habilitado = rs.getBoolean("Baja_logica_Habilitado");       
				  	        }
				  	    } catch (Exception e) {
				  	        System.out.println("Error al obtener los datos de las habilidades");
				  	        e.printStackTrace();
				  	    }
				}
				  	
					
				


				
				
				
 }	            
		            
 
		 
				
