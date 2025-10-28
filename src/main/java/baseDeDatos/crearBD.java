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
				        + "Fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
				        + "Fecha_ultimo_registro DATETIME NOT NULL,"
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

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Usuario ("
				        + "ID_usuario INT NOT NULL,"
				        + "ID_partida_global INT NOT NULL,"
                        + "ID_partida_slot INT NOT NULL,"
				        + "PRIMARY KEY (ID_usuario, ID_partida_slot),"
				        + "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario),"
				        + "FOREIGN KEY (ID_partida_global) REFERENCES Partida(ID_partida)"
				        + ");");

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida_Lugares ("
				        + "ID_partida_lugares INT NOT NULL AUTO_INCREMENT,"
				        + "ID_partida INT NOT NULL,"
				        + "ID_lugar INT NOT NULL,"
				        + "PRIMARY KEY (ID_partida_lugares),"
				        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
				        + "FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar)"
				        + ");");
				
				st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario_Objetos ("
				        + "ID_inventario_objetos INT NOT NULL AUTO_INCREMENT,"
				        + "ID_inventario INT NOT NULL,"
				        + "ID_objeto INT NOT NULL,"
				        + "Cantidad INT NOT NULL,"
				        + "PRIMARY KEY (ID_inventario_objetos),"
				        + "FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),"
				        + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)"
				        + ");");

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Recorridos_Partida ("
				        + "ID_recorridos_partida INT NOT NULL AUTO_INCREMENT,"
				        + "ID_recorrido INT NOT NULL,"
				        + "ID_partida INT NOT NULL,"
				        + "PRIMARY KEY (ID_recorridos_partida),"
				        + "FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido),"
				        + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida)"
				        + ");");

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Habilidades_Enemigos ("
				        + "ID_habilidad_enemigos INT NOT NULL AUTO_INCREMENT,"
				        + "ID_habilidad INT NOT NULL,"
				        + "ID_enemigos INT NOT NULL,"
				        + "PRIMARY KEY (ID_habilidad_enemigos),"
				        + "FOREIGN KEY (ID_habilidad) REFERENCES Habilidades(ID_habilidad),"
				        + "FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)"
				        + ");");

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Objetos_Enemigos ("
				        + "ID_objetos_enemigos INT NOT NULL AUTO_INCREMENT,"
				        + "ID_objeto INT NOT NULL,"
				        + "ID_enemigos INT NOT NULL,"
				        + "PRIMARY KEY (ID_objetos_enemigos),"
				        + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto),"
				        + "FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)"
				        + ");");

				st.executeUpdate("CREATE TABLE IF NOT EXISTS Tienda_Objetos ("
				        + "ID_tienda_objetos INT NOT NULL AUTO_INCREMENT,"
				        + "ID_tienda INT NOT NULL,"
				        + "ID_objeto INT NOT NULL,"
				        + "Precio INT NOT NULL,"
				        + "PRIMARY KEY (ID_tienda_objetos),"
				        + "FOREIGN KEY (ID_tienda) REFERENCES Tienda(ID_tienda),"
				        + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)"
				        + ");");
				
				// 3. Inserción Masiva
                RegistrarHabilidadesMasivo(con);
				RegistrarPersonajesMasivo(con);
			} else {
				System.err.println("Error al establecer la conexión.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//---------------------------------------------------------------------------------------------------------

	public void RegistrarHabilidadesMasivo(Connection con) {
		Object[][] habilidades = {
			
			    {1, "Golpe de Guardia", 30, false},
			    {2, "Grito de Orgullo", 50, false},
			    {3, "Herida Forjada", 40, false},
			    {4, "Llamada a Kar-Dûm", 80, false},
			    
			    {5, "Doble Arcano", 55, false},
			    {6, "Congelación Rúnica", 45, false},
			    {7, "Maldición Prohibida", 70, false},
			    {8, "Atracción Dimensional", 100, false},
			    
			    {9, "Ráfaga Lunar", 40, false},
			    {10, "Lazo de Naturaleza", 60, false},
			    {11, "Fuego Fatuo", 35, false},
			    {12, "Defensores del Bosque", 70, false},
			    
			    {13, "Danza de Dagas", 25, false},
			    {14, "Parálisis Infernal", 50, false},
			    {15, "Marca del Desafío", 45, false},
			    {16, "Sombras del Infierno", 85, false},
			    
			    {17, "Versos Gemelos", 50, false},
			    {18, "Juicio Divino", 65, false},
			    {19, "Pena del Purgatorio", 35, false},
			    {20, "Guía de Virgilio", 95, false},

                {21, "Caricia", 50, false},
			    {22, "Mordiso Rabioso", 65, false},
			    {23, "Encanto", 35, false},
			    {24, "Jauria Goblin", 95, false}
			};

	    String sql = "INSERT INTO Habilidades (ID_habilidad, Nombre, Mana_Coste, Baja_logica_Habilitado)"
	               + " SELECT ?, ?, ?, ?"
	               + " WHERE NOT EXISTS (SELECT 1 FROM Habilidades WHERE ID_habilidad = ?)";

	    try (PreparedStatement stmt = con.prepareStatement(sql)) {

	        for (Object[] h : habilidades) {
	            int id_habilidad = (int) h[0];
				
	            stmt.setInt(1, id_habilidad); 
	            stmt.setString(2, (String) h[1]);
	            stmt.setInt(3, (int) h[2]); 
	            stmt.setBoolean(4, (boolean) h[3]); 

	            stmt.setInt(5, id_habilidad); 

	            stmt.addBatch(); 
	        }

	        int[] resultados = stmt.executeBatch(); 

	        System.out.println("--- Resumen de Inserciones Masivas de Habilidades ---");
	        int insertados = 0;
	        for (int res : resultados) {
	            if (res > 0) {
	                insertados++;
	            }
	        }
	        System.out.println(insertados + " habilidad(es) insertada(s). Las restantes ya existían.");

	    } catch (SQLException e) {
	        System.err.println("Error al registrar habilidades masivamente:");
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

		public void RegistrarRecorridosMasivo(Connection con) {
			Object[][] recorridos = {
				    {1, "Ciudad", false},
				    {2, "Bosque", false},
				    {3, "Desierto de cenizas", false},
				    {4, "Montaña Nevada", false},
				    {5, "Ruinas Antiguas", false},
				    {6, "Pantano", false},
				    {7, "Castillo", false},
				    {8, "Oceano con Barcos Hundidos", false},
				    {9, "Catacumbas Perdidas", false},
				    {10, "Laberinto Subterraneo", false},
				    {11, "Volcan", false},
				    {12, "Cielo Nuboso", false},
				    {13, "Circulo del Infierno", false},
				    {14, "Biblioteca Magica", false},
				    {15, "Olimpo", false},
				    {16, "Abismo del Infierno", false}
				};

		    String sql = "INSERT INTO Recorridos (ID_recorrido, Nombre, Baja_logica_Habilitado)"
		               + " SELECT ?, ?, ?"
		               + " WHERE NOT EXISTS (SELECT 1 FROM Recorridos WHERE ID_recorrido = ?)";

		    try (PreparedStatement stmt = con.prepareStatement(sql)) {

		        for (Object[] r : recorridos) {
		            int id_recorrido = (int) r[0];
					
		            // Asigna los valores para INSERT
		            stmt.setInt(1, id_recorrido);
		            stmt.setString(2, (String) r[1]); // Nombre del Lugar/Recorrido
		            stmt.setBoolean(3, (boolean) r[2]);

		            // Asigna el ID para la condición WHERE NOT EXISTS 
		            stmt.setInt(4, id_recorrido);

		            stmt.addBatch(); // Agrega el comando al lote
		        }

		        int[] resultados = stmt.executeBatch(); 

		        System.out.println("--- Resumen de Inserciones Masivas de Recorridos (Lugares) ---");
		        int insertados = 0;
		        for (int res : resultados) {
		            if (res > 0) {
		                insertados++;
		            }
		        }
		        System.out.println(insertados + " recorrido(s) (lugar) insertado(s). Los restantes ya existían.");

		    } catch (SQLException e) {
		        System.err.println("Error al registrar recorridos masivamente:");
		        e.printStackTrace();
		    }
		}
	
	
	//---------------------------------------------------------------------------------------------------------

		
		public void RegistrarObjetosMasivo(Connection con) {
			Object[][] objetos = {
				    {1, "Poción de Salud Menor", "Combate", false},
				    {2, "Poción de Salud Media", "Combate", false},
				    {3, "Poción de Salud Alta", "Combate", false},
				    {4, "Poción de Maná Menor", "Combate", false},
				    {5, "Poción de Maná Media", "Combate", false},
				    {6, "Poción de Maná Alta", "Combate", false},
				    {7, "Cuchillo Arrojadizo", "Combate", false},
				    {8, "Bomba Casera", "Combate", false},
				    {9, "Amuleto de Iniciación", "Automático", false},
				    {10, "Capa de Viajero", "Automático", false},
				    {11, "Guantes Reforzados", "Automático", false}, 
				    {12, "Anillo de Magia Concentrada", "Automático", false},
				    {13, "nosee", "Automático", false},
				};

		    String sql = "INSERT INTO Objetos (ID_objeto, Nombre, Tipo, Baja_logica_Habilitado)"
		               + " SELECT ?, ?, ?, ?"
		               + " WHERE NOT EXISTS (SELECT 1 FROM Objetos WHERE ID_objeto = ?)";

		    try (PreparedStatement stmt = con.prepareStatement(sql)) {

		        for (Object[] o : objetos) {
		            int id_objeto = (int) o[0];
					
		            // Asigna los valores para INSERT
		            stmt.setInt(1, id_objeto);
		            stmt.setString(2, (String) o[1]);
		            stmt.setString(3, (String) o[2]);
		            stmt.setBoolean(4, (boolean) o[3]);

		            // Asigna el ID para la condición WHERE NOT EXISTS 
		            stmt.setInt(5, id_objeto);

		            stmt.addBatch(); // Agrega el comando al lote
		        }

		        int[] resultados = stmt.executeBatch(); 

		        System.out.println("--- Resumen de Inserciones Masivas de Objetos ---");
		        int insertados = 0;
		        for (int res : resultados) {
		            if (res > 0) {
		                insertados++;
		            }
		        }
		        System.out.println(insertados + " objeto(s) insertado(s). Los restantes ya existían.");

		    } catch (SQLException e) {
		        System.err.println("Error al registrar objetos masivamente:");
		        e.printStackTrace();
		    }
		}
	
	
	public void RegistrarLugaresMasivo(Connection con) {
		Object[][] lugares = {
		
			{1, "Ciudad", 0, false}, 		
			{2, "Bosque", 0, false},
			{3, "Desierto de cenizas", 1, false}, 
			{4, "Montaña Nevada", 1, false},
			{5, "Ruinas Antiguas", 1, false}, 	
			{6, "Pantano", 1, false}, 		
			{7, "Castillo", 1, false}, 			
			{8, "Oceano con Barcos Hundidos", 2, false}, 
			{9, "Catacumbas Perdidas", 2, false},
			{10, "Laberinto Subterraneo", 2, false},
			{11, "Volcan", 2, false}, 			
			{12, "Cielo Nuboso", 3, false}, 		
		    {13, "Circulo del Infierno", 3, false}, 
			{14, "Biblioteca Magica", 3, false}, 
			{15, "Olimpo", 4, false}, 			
			{16, "Abismo del Infierno", 4, false}	
			};

	String sql = "INSERT INTO Lugares (ID_lugar, Nombre, Etapa, Baja_logica_Habilitado)"
			+ " SELECT ?, ?, ?, ?"
			+ " WHERE NOT EXISTS (SELECT 1 FROM Lugares WHERE ID_lugar = ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			for (Object[] l : lugares) {
				int id_lugar = (int) l[0];
				
	
				stmt.setInt(1, id_lugar);
				
				stmt.setString(2, (String) l[1]);

				stmt.setInt(3, (int) l[2]); 
				stmt.setBoolean(4, (boolean) l[3]);

				stmt.setInt(5, id_lugar);

				stmt.addBatch(); 	
				}

			int[] resultados = stmt.executeBatch(); 

			System.out.println("--- Resumen de Inserciones Masivas de Lugares ---");
			int insertados = 0;
			for (int res : resultados) {
				if (res > 0) {
					insertados++;
				}
			}
			System.out.println(insertados + " lugar(es) insertado(s). Los restantes ya existían.");

		} catch (SQLException e) {
			System.err.println("Error al registrar lugares masivamente:");
			e.printStackTrace();
	    }
	}

	//---------------------------------------------------------------------------------------------------------

	public void RegistrarPersonajesMasivo(Connection con) {
		Object[][] personajes = {
			    {1, "Niño", "El protagonista. Un niño valiente, pero inexperto.", 20, 20, 20, false},
			    {2, "Thargrim", "Un Guerrero enano robusto y de baja estatura. Su personalidad es de lealtad inquebrantable, aunque a veces su orgullo lo ciega. Sus armas son las hachas ancestrales de su clan, útiles para el combate y la forja.", 70, 180, 120, false},
			    {3, "Selenne", "Una Maga humana de aspecto común pero portadora de una gran varita. Su rasgo principal es una curiosidad peligrosa, que la llevó a abandonar su torre arcana en pos de una profecía. Es una maestra en el uso de hechizos arcanos y su varita mágica.", 180, 85, 100, false},
			    {4, "Lyrianne", "Una Arquera elfa con la conexión natural y las orejas puntiagudas de su raza. Se caracteriza por su distancia emocional y su instinto protector hacia los indefensos. Su arma principal es el arco lunar de su aldea, usado para daño a distancia.", 130, 90, 110, false},
			    {5, "Kael", "Un demonio asesino que busca adrenalina en el mundo mortal. Su única motivación es el placer de la lucha y el desafío de enfrentar guerreros dignos. Su arma es la daga infernal, utilizada para matar y coleccionar desafíos.", 90, 80, 180, false},
			    {6, "Dante Alighieri", "Un escritor humano de porte político, que ha viajado por los reinos espirituales. Su fortaleza radica en su capacidad de inspiración a través de su palabra y su intelecto. Su principal arma es la “Divina Comedia”, cuyos escritos luchan contra los enemigos.", 110, 110, 110, false},
			    {7, "Goblin Aristóteles", "Baja estatura, contextura delgada y piel verdosa. Su personalidad se define por su insaciable sed de conocimiento y gran elocuencia. Utiliza su ingenio y la improvisación como sus mejores herramientas en el combate.", 300, 10, 600, false}
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
	        System.out.println(insertados + " personaje(s) insertado(s). Los restantes ya existían.");

	    } catch (SQLException e) {
	        System.err.println("Error al registrar personajes masivamente:");
	        e.printStackTrace();
	    }
	}
}
