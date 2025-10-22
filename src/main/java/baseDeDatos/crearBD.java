package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Arrays;

public class crearBD {
	private Statement st;

	public void crearBase(){
		Connection con = null;

		String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
		String user = "root";
		String pwd = "Root1234";


		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pwd);

			if(con != null){
				String nombreBD = "Proyecto";
				st = con.createStatement();
				
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
				        + "PRIMARY KEY (ID_personaje_partida),"
				        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
				        + "FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje)"
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

				RegistrarPersonajesMasivo(con);
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

	//---------------------------------------------------------------------------------------------------------

	public void RegistrarHabilidades (Connection con, int ID_habilidad, String Nombre, int Mana_Coste, boolean Baja_logica_Habilitado ) {

		String sql = "INSERT INTO Habilidades (ID_habilidad, Nombre, Mana_Coste, Baja_logica_Habilitado)"
	    + " SELECT ?, ?, ?, ?"
        + " WHERE NOT EXISTS (SELECT 1 FROM Habilidades WHERE ID_habilidad = ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, ID_habilidad);
	        stmt.setString(2, Nombre);
	        stmt.setInt(3, Mana_Coste);
	        stmt.setBoolean(4, Baja_logica_Habilitado);
            stmt.setInt(5, ID_habilidad);

	        int filasAfectadas = stmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("Habilidad Registrada Correctamente.");
            } else {
                System.out.println("La Habilidad con ID " + ID_habilidad + " ya existe. No se insertó.");
            }

		} catch (SQLException e) {
	        e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------

	public void RegistrarImagenes (Connection con, int ID_imagen, String Nombre, String URL, boolean Baja_logica_Habilitado ) {

		String sql = "INSERT INTO Imagenes (ID_imagen, Nombre, URL, Baja_logica_Habilitado)"
            + " SELECT ?, ?, ?, ?"
            + " WHERE NOT EXISTS (SELECT 1 FROM Imagenes WHERE ID_imagen = ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, ID_imagen);
	        stmt.setString(2, Nombre);
	        stmt.setString(3, URL);
	        stmt.setBoolean(4, Baja_logica_Habilitado);
            stmt.setInt(5, ID_imagen);

	        int filasAfectadas = stmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("Imagen Registrada Correctamente.");
            } else {
                System.out.println("La Imagen con ID " + ID_imagen + " ya existe. No se insertó.");
            }

		} catch (SQLException e) {
	        e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------

	public void RegistrarPersonajesMasivo(Connection con) {
	    Object[][] personajes = {
	        {1, "Niño", "El protagonista. Un niño valiente, pero inexperto.", 20, 20, 20, false},
	        {2, "Mago", "Especializado en magia ofensiva.", 50, 15, 10, false},
	        {3, "Guerrero", "Tanque y DPS cuerpo a cuerpo.", 10, 40, 30, false},
	        {4, "Arquero", "Daño a distancia, poca defensa.", 25, 20, 25, false},
	        {5, "Curandero", "Soporte y sanación.", 40, 20, 5, false},
	        {6, "Bardo", "Control de masas y buffs.", 30, 25, 15, false}
	    };

	    String sql = "INSERT INTO Personaje (ID_personaje, Nombre, Descripcion, Mana_Ini, Vida_Ini, Dano_Ini, Baja_logica_Habilitado)"
	               + " SELECT ?, ?, ?, ?, ?, ?, ?"
	               + " WHERE NOT EXISTS (SELECT 1 FROM Personaje WHERE ID_personaje = ?)";

	    try (PreparedStatement stmt = con.prepareStatement(sql)) {

	        for (Object[] p : personajes) {
	            int id_personaje = (int) p[0];
				
	            stmt.setInt(1, id_personaje);
	            stmt.setString(2, (String) p[1]);
	            stmt.setString(3, (String) p[2]);
	            stmt.setInt(4, (int) p[3]);
	            stmt.setInt(5, (int) p[4]);
	            stmt.setInt(6, (int) p[5]);
	            stmt.setBoolean(7, (boolean) p[6]);

	            stmt.setInt(8, id_personaje);

	            stmt.addBatch(); 
	        }
			
	        int[] resultados = stmt.executeBatch();
			
	        System.out.println("--- Resumen de Inserciones Masivas de Personajes ---");
	        int insertados = 0;
	        for (int res : resultados) {
	            if (res > 0) {
	                insertados++;
	            }
	        }
	        System.out.println(insertados + " personaje(s) insertado(s) o actualizado(s) correctamente.");

	    } catch (SQLException e) {
	        System.err.println("Error al registrar personajes masivamente:");
	        e.printStackTrace();
	    }
	}
}
}

