package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



 
public class crearBD {
	private Statement st;
	ResultSet rs = null;
	boolean existe;

	public void crearBase(){
		Connection con = null;
		ResultSet rs = null;
 
		String url = "jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC";
		String user = "root";
		String pwd = "root";
 
		
		try{
 
			Class.forName("com.mysql.cj.jdbc.Driver");
 
			con = DriverManager.getConnection(url, user, pwd);
			String nombreBD = "pruebaUsuario";
			st = con.createStatement();
			if(con != null){
				rs = con.getMetaData().getCatalogs();
				while(rs.next()){
					String catalogs = rs.getString(1);
					if(nombreBD.equals(catalogs)){
						existe = true;
					}
				}
				
				 if (!existe) {
	                    st.executeUpdate("CREATE SCHEMA IF NOT EXISTS Proyecto;\r\n"
	                                   + "USE Proyecto;\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Inventario (\r\n"
	                                   + "ID_inventario INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Slots INT NOT NULL,\r\n"
	                                   + "Baja_Logica_Habilitado INT NOT NULL,\r\n"
	                                   + "PRIMARY KEY (ID_inventario)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Usuario (\r\n"
	                                   + "id_usuario INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "nombre VARCHAR(100),\r\n"
	                                   + "contra VARCHAR(200),\r\n"
	                                   + "correo VARCHAR(100),\r\n"
	                                   + "Baja_Logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_usuario)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Lugares (\r\n"
	                                   + "ID_lugar INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(100) NOT NULL,\r\n"
	                                   + "Etapa INT NOT NULL,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_lugar)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Tienda (\r\n"
	                                   + "ID_tienda INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(100) NOT NULL,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_tienda)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Objetos (\r\n"
	                                   + "ID_objeto INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(100) NOT NULL,\r\n"
	                                   + "Tipo VARCHAR(50),\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_objeto)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Habilidades (\r\n"
	                                   + "ID_habilidad INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(50) NOT NULL,\\r\\n "
	                                   + "Mana_Coste INT,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_Habilidad)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Imagenes (\r\n"
	                                   + "ID_imagen INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(100) NOT NULL,\r\n"
	                                   + "URL VARCHAR(255) NOT NULL,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_imagen)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Enemigos (\r\n"
	                                   + "ID_enemigos INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Nombre VARCHAR(100) NOT NULL,\r\n"
	                                   + "Dano INT,\r\n"
	                                   + "Vida_maxima INT,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_enemigos)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Personaje (\r\n"
	                                   + "ID_personaje INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Mana_Max INT NOT NULL,\r\n"
	                                   + "Vida_Max INT,\r\n"
	                                   + "Dano INT,\r\n"
	                                   + "Descripcion text,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_personaje)\r\n"
	                                   + ");\r\n"

	                                   + "CREATE TABLE IF NOT EXISTS Partida (\r\n"
	                                   + "ID_partida INT NOT NULL AUTO_INCREMENT,\r\n"
	                                   + "Fecha_creación DATETIME,\r\n"
	                                   + "Fecha_último_registro DATETIME,\r\n"
	                                   + "Baja_logica_Habilitado INT,\r\n"
	                                   + "PRIMARY KEY (ID_partida)\r\n"
	                                   + "); \r\n" 
	                                   
										+ "CREATE TABLE IF NOT EXISTS Personaje_Partida (\r\n"
										+ "ID_personaje_partida INT NOT NULL AUTO_INCREMENT,\r\n"
										+ "Mana_Actual INT NOT NULL,\r\n"
										+ "Vida_Actual INT,\r\n"
										+ "Dano_Actual INT,\r\n"
										+ "Nombre VARCHAR(50),\r\n"
										+ "Baja_logica_Habilitado INT,\r\n"
										+ "PRIMARY KEY (ID_personaje)\r\n"
										+ ");\r\n"
	                                   
	                    				);
	                                  
	                                   
	                                   
		                                   st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Habilidad (\r\n"
		                                           + "ID_personaje_habilidad INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_personaje INT NOT NULL,\r\n"
		                                           + "ID_Habilidad INT NOT NULL,\r\n"
		                                           + ""
		                                           + "PRIMARY KEY (ID_personaje_habilidad),\r\n"
		                                           + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),\r\n"
		                                           + "FOREIGN KEY (ID_Habilidad) REFERENCES Habilidades(ID_Habilidad)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Imagenes (\r\n"
		                                           + "ID_personaje_imagen INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_personaje INT NOT NULL,\r\n"
		                                           + "ID_imagen INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_personaje_imagen),\r\n"
		                                           + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),\r\n"
		                                           + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Personaje_Partida (\r\n"
		                                           + "ID_personaje_personaje_partida INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_personaje INT NOT NULL,\r\n"
		                                           + "ID_personaje_partida INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_personaje_personaje_partida),\r\n"
		                                           + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),\r\n"
		                                           + "FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)\r\n"
		                                           + ");\r\n");
		
		                            
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Personaje_Partida (\r\n"
		                                           + "ID_partida_personaje_partida INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_partida INT NOT NULL,\r\n"
		                                           + "ID_personaje_partida INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_partida_personaje_partida),\r\n"
		                                           + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),\r\n"
		                                           + "FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Recorridos (\r\n"
		                                           + "ID_partida_recorrido INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_partida INT NOT NULL,\r\n"
		                                           + "ID_recorrido INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_partida_recorrido),\r\n"
		                                           + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),\r\n"
		                                           + "FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Lugares_Recorridos (\r\n"
		                                           + "ID_lugar_recorrido INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_lugar INT NOT NULL,\r\n"
		                                           + "ID_recorrido INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_lugar_recorrido),\r\n"
		                                           + "FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),\r\n"
		                                           + "FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario_Personaje_Partida (\r\n"
		                                           + "ID_inventario_personaje_partida INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_inventario INT NOT NULL,\r\n"
		                                           + "ID_personaje_partida INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_inventario_personaje_partida),\r\n"
		                                           + "FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),\r\n"
		                                           + "FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario_Objeto (\r\n"
		                                           + "ID_inventario_objeto INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_inventario INT NOT NULL,\r\n"
		                                           + "ID_objeto INT NOT NULL,\r\n"
		                                           + "precio INT NOT NULL,\r\n"
		                                           + "stock INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_inventario_objeto),\r\n"
		                                           + "FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),\r\n"
		                                           + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Tienda_Objeto (\r\n"
		                                           + "ID_tienda_objeto INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_tienda INT NOT NULL,\r\n"
		                                           + "ID_objeto INT NOT NULL,\r\n"
		                                           + "precio INT NOT NULL,\r\n"
		                                           + "stock INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_tienda_objeto),\r\n"
		                                           + "FOREIGN KEY (ID_tienda) REFERENCES Tienda(ID_tienda),\r\n"
		                                           + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Usuario (\r\n"
		                                           + "ID_partida_usuario INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_partida INT NOT NULL,\r\n"
		                                           + "ID_usuario INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_partida_usuario),\r\n"
		                                           + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),\r\n"
		                                           + "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Imagenes_Usuario (\r\n"
		                                           + "ID_imagen_usuario INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_imagen INT NOT NULL,\r\n"
		                                           + "ID_usuario INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_imagen_usuario),\r\n"
		                                           + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),\r\n"
		                                           + "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)\r\n"
		                                           + ");\r\n");
		
		                            st.executeUpdate("CREATE TABLE IF NOT EXISTS Imagenes_Enemigos (\r\n"
		                                           + "ID_imagen_enemigos INT NOT NULL AUTO_INCREMENT,\r\n"
		                                           + "ID_imagen INT NOT NULL,\r\n"
		                                           + "ID_enemigos INT NOT NULL,\r\n"
		                                           + "PRIMARY KEY (ID_imagen_enemigos),\r\n"
		                                           + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),\r\n"
		                                           + "FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)\r\n"
		                                           + ");\r\n");
		                                           
				 	}   
			 	} 
			} catch (Exception ex) {
			            ex.printStackTrace();
			        } finally {
			            if (rs != null) {
			                try {
			                    rs.close();
			                } catch (SQLException ex) {
			                    ex.printStackTrace();
			                }
			            }
			            if (con!= null) {
			                try {
			                    con.close();
			                } catch (SQLException ex) {
			                    ex.printStackTrace();
			                }
			            }
			        
			    }  
	} 
} 
				 

		
		
	  
	  
	  	    	            
	  	    	            
	  	    	            
	  	    	            
	  	    	            
	  	    	      
	
