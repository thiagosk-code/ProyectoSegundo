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
 
		String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
		String user = "root";
		String pwd = "Root1234";
 
		
		try{
 
			Class.forName("com.mysql.cj.jdbc.Driver");
 
			con = DriverManager.getConnection(url, user, pwd);
			String nombreBD = "Proyecto";
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
					 st.executeUpdate("CREATE SCHEMA IF NOT EXISTS Proyecto;");
						st.executeUpdate("USE Proyecto;");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario ("
						        + "ID_inventario INT AUTO_INCREMENT,"
						        + "Slots INT NOT NULL,"
						        + "Baja_Logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_inventario)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Usuario ("
						        + "id_usuario INT AUTO_INCREMENT,"
						        + "nombre VARCHAR(100) NOT NULL,"
						        + "contra VARCHAR(200) NOT NULL,"
						        + "correo VARCHAR(100) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_usuario)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Lugares ("
						        + "ID_lugar INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Etapa INT NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_lugar)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Tienda ("
						        + "ID_tienda INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_tienda)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Objetos ("
						        + "ID_objeto INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Tipo VARCHAR(50) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_objeto)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Habilidades ("
						        + "ID_habilidad INT NOT NULL AUTO_INCREMENT,"
								+ "Nombre VARCHAR (100) NOT NULL,"
						        + "Mana_Coste INT NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_Habilidad)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Imagenes ("
						        + "ID_imagen INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "URL VARCHAR(255) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_imagen)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Enemigos ("
						        + "ID_enemigos INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Dano INT NOT NULL,"
						        + "Vida_Max INT NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_enemigos)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje ("
						        + "ID_personaje INT NOT NULL AUTO_INCREMENT,"
								+ "Nombre VARCHAR(100) NOT NULL,"
						        + "Descripcion VARCHAR(1000) DEFAULT 'DescripcionEjem' ,"
						        + "Mana_Ini INT NOT NULL,"
						        + "Vida_Ini INT NOT NULL,"
						        + "Dano_Ini INT NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_personaje)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida ("
						        + "ID_partida INT NOT NULL AUTO_INCREMENT,"
						        + "Fecha_creación DATETIME NOT NULL,"
						        + "Fecha_último_registro DATETIME NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_partida)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Recorridos ("
						        + "ID_recorrido INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_recorrido)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Partida ("
						        + "ID_personaje_partida INT NOT NULL AUTO_INCREMENT,"
								+ "ID_partida INT NOT NULL,"
								+ "ID_personaje INT NOT NULL,"
						        + "Mana_Max INT NOT NULL,"
						        + "Mana_Act INT NOT NULL,"
						        + "Vida_Max INT NOT NULL,"
						        + "Vida_Act INT NOT NULL,"
								+ "Dano INT NOT NULL,"
						        + "Descripcion VARCHAR (1000) DEFAULT 'descripcionEjem',"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
						        + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),"
						        + "PRIMARY KEY (ID_personaje_partida)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Habilidad ("
						        + "ID_personaje_habilidad INT NOT NULL AUTO_INCREMENT,"
						        + "ID_personaje INT NOT NULL,"
						        + "ID_Habilidad INT NOT NULL,"
						        + "PRIMARY KEY (ID_personaje_habilidad),"
						        + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),"
						        + "FOREIGN KEY (ID_Habilidad) REFERENCES Habilidades(ID_Habilidad)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Personaje_Imagenes ("
						        + "ID_personaje_imagen INT NOT NULL AUTO_INCREMENT,"
						        + "ID_personaje INT NOT NULL,"
						        + "ID_imagen INT NOT NULL,"
						        + "PRIMARY KEY (ID_personaje_imagen),"
						        + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),"
						        + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Recorridos ("
						        + "ID_partida_recorrido INT NOT NULL AUTO_INCREMENT,"
						        + "ID_partida INT NOT NULL,"
						        + "ID_recorrido INT NOT NULL,"
						        + "PRIMARY KEY (ID_partida_recorrido),"
						        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
						        + "FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Lugares_Recorridos ("
						        + "ID_lugar_recorrido INT NOT NULL AUTO_INCREMENT,"
						        + "ID_lugar INT NOT NULL,"
						        + "ID_recorrido INT NOT NULL,"
						        + "PRIMARY KEY (ID_lugar_recorrido),"
						        + "FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),"
						        + "FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario_Personaje_Partida ("
						        + "ID_inventario_personaje_partida INT NOT NULL AUTO_INCREMENT,"
						        + "ID_inventario INT NOT NULL,"
						        + "ID_personaje_partida INT NOT NULL,"
						        + "PRIMARY KEY (ID_inventario_personaje_partida),"
						        + "FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),"
						        + "FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario_Objeto ("
						        + "ID_inventario_objeto INT NOT NULL AUTO_INCREMENT,"
						        + "ID_inventario INT NOT NULL,"
						        + "ID_objeto INT NOT NULL,"
						        + "precio INT NOT NULL,"
						        + "stock INT NOT NULL,"
						        + "PRIMARY KEY (ID_inventario_objeto),"
						        + "FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),"
						        + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Tienda_Objeto ("
						        + "ID_tienda_objeto INT NOT NULL AUTO_INCREMENT,"
						        + "ID_tienda INT NOT NULL,"
						        + "ID_objeto INT NOT NULL,"
						        + "precio INT NOT NULL,"
						        + "stock INT NOT NULL,"
						        + "PRIMARY KEY (ID_tienda_objeto),"
						        + "FOREIGN KEY (ID_tienda) REFERENCES Tienda(ID_tienda),"
						        + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Usuario ("
						        + "ID_partida_usuario INT NOT NULL AUTO_INCREMENT,"
						        + "ID_partida INT NOT NULL,"
						        + "ID_usuario INT NOT NULL,"
						        + "PRIMARY KEY (ID_partida_usuario),"
						        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
						        + "FOREIGN KEY (ID_usuario) REFERENCES Usuario(id_usuario)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Imagenes_Usuario ("
						        + "ID_imagen_usuario INT NOT NULL AUTO_INCREMENT,"
						        + "ID_imagen INT NOT NULL,"
						        + "ID_usuario INT NOT NULL,"
						        + "PRIMARY KEY (ID_imagen_usuario),"
						        + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),"
						        + "FOREIGN KEY (ID_usuario) REFERENCES Usuario(id_usuario)"
						        + ");");

						st.executeUpdate("CREATE TABLE IF NOT EXISTS Imagenes_Enemigos ("
						        + "ID_imagen_enemigos INT NOT NULL AUTO_INCREMENT,"
						        + "ID_imagen INT NOT NULL,"
						        + "ID_enemigos INT NOT NULL,"
						        + "PRIMARY KEY (ID_imagen_enemigos),"
						        + "FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),"
						        + "FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)"
						        + ");");
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
