package logica.baseDeDatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Partida {

	public void Partida1 (Connection con, int ID_partida, Date fecha_creacion, Date fecha_ultimo_registro, boolean Baja_logica_Habilitado) {

		String sql = "INSERT INTO Partida (ID_partida, fecha_creacion, fecha_ultimo_registro, Baja_logico_Habilitado)"
               + "VALUES (?, ?, ?, ?)";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_partida);
            stmt.setDate(2, fecha_creacion);
            stmt.setDate(3, fecha_ultimo_registro);
            stmt.setBoolean(4, Baja_logica_Habilitado);
            stmt.executeUpdate();
            System.out.println("Partida Creada Correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        	 
            String [] datosPartida= {
            	String.valueOf(ID_partida),
            	String.valueOf(fecha_creacion),
            	String.valueOf(fecha_ultimo_registro),
            	String.valueOf(Baja_logica_Habilitado)
            };
            
            System.out.println(Arrays.toString(datosPartida));
           
        	  }
            
        	}   
        	            

	            
  	    	            
  public void MostrarPartida() {
	  String sql= "Select * FROM Partida";
	  try (
			Connection con=  DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC", "root", "root");
			  Statement stmt = con.createStatement();
    	        ResultSet rs = stmt.executeQuery(sql)
    	    ) 
	  	{	
		  
		  System.out.println("Partida");
		  
	        while (rs.next()) {
	            int Id = rs.getInt("ID_partida");
	            int Mana_Max = rs.getInt("Mana_Max");
	            int Vida_Max = rs.getInt("Vida_Max");
	            int Dano_Max = rs.getInt("Dano_Max");
	            String Descrpcion = rs.getString("Descripcion");
	            Boolean Baja_logica_Habilitado = rs.getBoolean("Baja_logica_Habilitado");       
	      
	            
	       }
	    } catch (Exception e) {
	        System.out.println("Error al obtener los datos de la partida.");
	        e.printStackTrace();
	    }
	}
}
	
	
	

