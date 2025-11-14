		package baseDeDatos;
		
		import java.sql.Connection;
		import java.sql.DriverManager;
		import java.sql.PreparedStatement;
		import java.sql.SQLException;
		import java.sql.Statement;
		
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
						        + "Efecto INT NOT NULL,"
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
						        + "Jefe BOOLEAN NOT NULL,"
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
						        + "Dinero INT NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_personaje)"
						        + ");");
		
						st.executeUpdate("CREATE TABLE IF NOT EXISTS Partida ("
						        + "ID_partida INT NOT NULL AUTO_INCREMENT,"
						        + "Fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
						        + "Fecha_ultimo_registro DATETIME NOT NULL,"
						        + "Mensaje_actual VARCHAR(10000) DEFAULT 'mensaje predeterminado' ,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_partida)"
						        + ");");
		
						st.executeUpdate("CREATE TABLE IF NOT EXISTS Recorridos ("
						        + "ID_recorrido INT NOT NULL AUTO_INCREMENT,"
						        + "Nombre VARCHAR(100) NOT NULL,"
						        + "Baja_logica_Habilitado BOOLEAN,"
						        + "PRIMARY KEY (ID_recorrido)"
						        + ");");
		
						st.executeUpdate("CREATE TABLE IF NOT EXISTS Inventario ("
				                + "ID_inventario INT NOT NULL AUTO_INCREMENT,"
				                + "ID_partida INT NOT NULL,"
				                + "ID_objeto INT NOT NULL,"
				                + "Cantidad INT NOT NULL DEFAULT 1," // Opcional: para manejar pilas de objetos
				                + "Baja_Logica_Habilitado BOOLEAN,"
				                + "PRIMARY KEY (ID_inventario),"
				                + "FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),"
				                + "FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)" // Asumo que existe tabla Objetos
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
								+ "Dinero INT NOT NULL,"
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
						        + "Flag BOOLEAN NOT NULL,"
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
						
						st.executeUpdate("CREATE TABLE IF NOT EXISTS Enemigos_Lugares ("
								 + "ID_lugar INT NOT NULL,"
								 + "ID_enemigos INT NOT NULL,"
								 + "PRIMARY KEY (ID_enemigos, ID_lugar),"
								 + "UNIQUE KEY uq_enemigo_unico (ID_enemigos),"
								 + "FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos),"
								 + "FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar)"
								 + ");");
						
						
						// Inserción Masiva
						RegistrarHabilidadesMasivo(con);
						RegistrarPersonajesMasivo(con);
						RegistrarImagenesMasivo(con);
						RegistrarLugaresMasivo(con);
						RegistrarObjetosMasivo(con);
						RegistrarTiendasMasivo(con);
						RegistrarRecorridosMasivo(con);
						RegistrarEnemigos(con);
						RegistrarEnemigosLugaresMasivoPorBloques(con);
						RegistrarTiendaObjetos(con);
						
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
		
			
		
			public void RegistrarImagenesMasivo(Connection con) {
		
				String URL_base = "src/main/webapp/Imagenes/";
				Object[][] imagenes = {
						{1, "Fondo_Bosque", URL_base + "Fondos/Bosque.png", false},
				        {2, "Fondo_Inicio", URL_base + "Fondos/Inicio.png", false}, 
				        {3, "Fondo_Ciudad", URL_base + "Fondos/A/Ciudad.png", false},
				        {4, "Fondo_Desierto", URL_base + "Fondos/A/Desierto_cenizas.png", false},
				        {5, "Fondo_Montaña_Nevada", URL_base + "Fondos/A/Montaña_Nevada.png", false},
				        {6, "Fondo_Ruinas_Antiguas", URL_base + "Fondos/A/Ruinas_Antiguas.png", false},
				        {7, "Fondo_Pantano", URL_base + "Fondos/A/Pantano.png", false},
				        {8, "Fondo_Castillo", URL_base + "Fondos/A/Castillo.png", false},
				        {9, "Fondo_Oceano", URL_base + "Fondos/B/Oceano_Barcos_Hundidos.png", false},
				        {10, "Fondo_Catacumbas", URL_base + "Fondos/B/Catacumbas_Perdidas.png", false},
				        {11, "Fondo_Laberinto", URL_base + "Fondos/B/Laberinto_Subterraneo.png", false},
				        {12, "Fondo_Volcan", URL_base + "Fondos/B/Volcan.png", false},
				        {13, "Fondo_Cielo_Nuboso", URL_base + "Fondos/C/Cielo_Nuboso.png", false},
				        {14, "Fondo_Circulo_Infierno", URL_base + "Fondos/C/Circulo_del_Infierno.png", false},
				        {15, "Fondo_Biblioteca_Magica", URL_base + "Fondos/C/Biblioteca_Magica.png", false},
				        {16, "Fondo_Olimpo", URL_base + "Fondos/D/Olimpo.png", false},
				        {17, "Fondo_Abismo_Infernal", URL_base + "Fondos/D/Abismo_del_Infierno.png", false},
				   
				        // IMÁGENES DE PERSONAJES
				        {18, "Personaje_Nino", URL_base + "PJs/Nino.png", false},
				        {19, "Personaje_Thargrim", URL_base + "PJs/Guerrero.png", false},
				        {20, "Personaje_Selenne", URL_base + "PJs/Maga.png", false},
				        {21, "Personaje_Lyrianne", URL_base + "PJs/Arquera.png", false},
				        {22, "Personaje_Kael", URL_base + "PJs/Asesino.png", false},
				        {23, "Personaje_Dante", URL_base + "PJs/Dante.png", false},
				        {24, "Personaje_Goblin_Aristoteles", URL_base + "PJs/Aristo.png", false}
					};
		
			    String sql = "INSERT INTO Imagenes (ID_imagen, Nombre, URL, Baja_logica_Habilitado)"
			               + " SELECT ?, ?, ?, ?"
			               + " WHERE NOT EXISTS (SELECT 1 FROM Imagenes WHERE ID_imagen = ?)";
		
			    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		
			        for (Object[] img : imagenes) {
			            int id_imagen = (int) img[0];
		
			            stmt.setInt(1, id_imagen);
			            stmt.setString(2, (String) img[1]);
			            stmt.setString(3, (String) img[2]); // La URL completa
			            stmt.setBoolean(4, (boolean) img[3]);
		
			            stmt.setInt(5, id_imagen);
		
			            stmt.addBatch();
		
			        }
		
			        int[] resultados = stmt.executeBatch(); 
			        System.out.println("--- Resumen de Inserciones Masivas de Imágenes ---");
			        int insertados = 0;
			        for (int res : resultados) {
			            if (res > 0) {
			                insertados++;
			            }
			        }
		
			        System.out.println(insertados + " imagen(es) insertada(s). Las restantes ya existían.");
		
			    } catch (SQLException e) {
			        System.err.println("Error al registrar imágenes masivamente:");
			        e.printStackTrace();
			    }
			}
		
			//---------------------------------------------------------------------------------------------------------
		
				public void RegistrarRecorridosMasivo(Connection con) {
					Object[][] recorridos = {
						    {1, "inicio1", false},
						    {2, "inicio2", false},
						    {3, "Bosque", false},
						    {4, "Desierto de cenizas", false},
						    {5, "Montaña Nevada", false},
						    {6, "Ruinas Antiguas", false},
						    {7, "Pantano", false},
						    {8, "Castillo", false},
						    {9, "Oceano con Barcos Hundidos", false},
						    {10, "Catacumbas Perdidas", false},
						    {11, "Laberinto Subterraneo", false},
						    {12, "Volcan", false},
						    {13, "Cielo Nuboso", false},
						    {14, "Circulo del Infierno", false},
						    {15, "Biblioteca Magica", false},
						    {16, "Olimpo", false},
						    {17, "Abismo del Infierno", false}
						};
		
				    String sql = "INSERT INTO Recorridos (ID_recorrido, Nombre, Baja_logica_Habilitado)"
				               + " SELECT ?, ?, ?"
				               + " WHERE NOT EXISTS (SELECT 1 FROM Recorridos WHERE ID_recorrido = ?)";
		
				    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		
				        for (Object[] r : recorridos) {
				            int id_recorrido = (int) r[0];
		
				            stmt.setInt(1, id_recorrido);
				            stmt.setString(2, (String) r[1]);
				            stmt.setBoolean(3, (boolean) r[2]);
		
				            stmt.setInt(4, id_recorrido);
				            stmt.addBatch();
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
						    {1, "Poción de Salud Menor", "curar", 30, false},
						    {2, "Poción de Salud Media", "curar", 40, false},
						    {3, "Poción de Salud Alta", "curar", 50, false},
						    {4, "Poción de Maná Menor", "mana", 20, false},
						    {5, "Poción de Maná Media", "mana", 30, false},
						    {6, "Poción de Maná Alta", "mana", 40, false},
						    {7, "Cuchillo Arrojadizo", "Ataque", 50, false},
						    {8, "Bomba Casera", "Ataque", 60, false},
						};
		
				    String sql = "INSERT INTO Objetos (ID_objeto, Nombre, Tipo, Efecto, Baja_logica_Habilitado)"
				               + " SELECT ?, ?, ?, ?, ?"
				               + " WHERE NOT EXISTS (SELECT 1 FROM Objetos WHERE ID_objeto = ?)";
		
				    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		
				        for (Object[] o : objetos) {
				            int id_objeto = (int) o[0];
							
				            stmt.setInt(1, id_objeto);
				            stmt.setString(2, (String) o[1]);
				            stmt.setString(3, (String) o[2]);
				            stmt.setInt(4, (int) o[3]);
				            stmt.setBoolean(5, (boolean) o[4]);
				            stmt.setInt(6, id_objeto);
		
				            stmt.addBatch();
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
		
			//---------------------------------------------------------------------------------------------------------
		
			public void RegistrarPersonajesMasivo(Connection con) {
				Object[][] personajes = {
					    {1, "Niño", "El protagonista. Un niño valiente, pero inexperto.", 20, 20, 20, 40, false},
					    {2, "Thargrim", "Un Guerrero enano robusto y de baja estatura. Su personalidad es de lealtad inquebrantable, aunque a veces su orgullo lo ciega. Sus armas son las hachas ancestrales de su clan, útiles para el combate y la forja.", 70, 180, 120, 40, false},
					    {3, "Selenne", "Una Maga humana de aspecto común pero portadora de una gran varita. Su rasgo principal es una curiosidad peligrosa, que la llevó a abandonar su torre arcana en pos de una profecía. Es una maestra en el uso de hechizos arcanos y su varita mágica.", 180, 85, 100, 40, false},
					    {4, "Lyrianne", "Una Arquera elfa con la conexión natural y las orejas puntiagudas de su raza. Se caracteriza por su distancia emocional y su instinto protector hacia los indefensos. Su arma principal es el arco lunar de su aldea, usado para daño a distancia.", 130, 90, 110, 40, false},
					    {5, "Kael", "Un demonio asesino que busca adrenalina en el mundo mortal. Su única motivación es el placer de la lucha y el desafío de enfrentar guerreros dignos. Su arma es la daga infernal, utilizada para matar y coleccionar desafíos.", 90, 80, 180, 40, false},
					    {6, "Dante Alighieri", "Un escritor humano de porte político, que ha viajado por los reinos espirituales. Su fortaleza radica en su capacidad de inspiración a través de su palabra y su intelecto. Su principal arma es la “Divina Comedia”, cuyos escritos luchan contra los enemigos.", 110, 110, 110, 40, false},
					    {7, "Goblin Aristóteles", "Baja estatura, contextura delgada y piel verdosa. Su personalidad se define por su insaciable sed de conocimiento y gran elocuencia. Utiliza su ingenio y la improvisación como sus mejores herramientas en el combate.", 300, 10, 600, 40, false}
					};
		
			    String sql = "INSERT INTO Personaje (ID_personaje, Nombre, Descripcion, Mana_Ini, Vida_Ini, Dano_Ini, Dinero, Baja_logica_Habilitado)"
			               + " SELECT ?, ?, ?, ?, ?, ?, ?, ?"
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
			            stmt.setInt(7, (int) p[6]);
			            stmt.setBoolean(8, (boolean) p[7]);	
			            stmt.setInt(9, id_personaje);
		
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
			
			public void RegistrarLugaresMasivo(Connection con) {
				Object[][] lugares = {
					    {1, "inicio1", 0, false},
					    {2, "inicio2", 0, false},
					    {3, "Bosque", 1, false},
					    {4, "Desierto de cenizas", 2, false},
					    {5, "Montaña Nevada", 2, false},
					    {6, "Ruinas Antiguas", 2, false},
					    {7, "Pantano", 2, false},
					    {8, "Castillo", 2, false},
					    {9, "Oceano con Barcos Hundidos", 3, false},
					    {10, "Catacumbas Perdidas", 3, false},
					    {11, "Laberinto Subterraneo", 3, false},
					    {12, "Volcan", 3, false},
					    {13, "Cielo Nuboso", 4, false},
					    {14, "Circulo del Infierno", 4, false},
					    {15, "Biblioteca Magica", 4, false},
					    {16, "Olimpo", 5, false},
					    {17, "Abismo del Infierno", 5, false}
				};
		
			    String sql = "INSERT INTO Lugares (ID_lugar, Nombre, Etapa, Baja_logica_Habilitado) "
			               + "SELECT ?, ?, ?, ? "
			               + "WHERE NOT EXISTS (SELECT 1 FROM Lugares WHERE ID_lugar = ?)";
		
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
			
			public void RegistrarEnemigos(Connection con) {
				Object[][] enemigos = {
					
					    {1, "Panteras", 30, 50, false, false},
					    {2, "Leones", 50, 50, false, false},
					    {3, "Lobos", 40, 50, false, false},
					    {4, "Árbol Ancestral", 80, 50, true, false},
					    
					    {5, "Sabuesos", 50, 50, false, false},
					    {6, "Cactus", 50, 50, false, false},
					    {7, "Momia", 50, 50, false, false},
					    {8, "Dragonfly", 50, 50, true, false},
					    
					    {9, "Muñeco de Nieve", 50, 50, false, false},
					    {10, "Gólem de hielo", 50, 50, false, false},
					    {11, "Dragón de hielo", 50, 50, false, false},
					    {12, "Rey Helado", 50, 50, true, false},
					    
					    {13, "Estatua viviente", 50, 50, false, false},
					    {14, "Escorpión gigante", 50, 50, false, false},
					    {15, "Espíritu guardián", 50, 50, false, false},
					    {16, "Anubis", 50, 50, true, false},
					    
					    {17, "Rana gigante", 50, 50, false, false},
					    {18, "Bruja", 50, 50, false, false},
					    {19, "Slime", 50, 50, false, false},
					    {20, "Hidra", 50, 50, true, false},
		
					    {21, "Fantasma", 50, 50, false, false},
					    {22, "Saqueadores", 50, 50, false, false},
					    {23, "Caballero", 50, 50, false, false},
					    {24, "Fantasma Real", 50, 50, true, false},
					    
					    {25, "Orcalero Orcalá", 50, 50, false, false},
					    {26, "Sirenas", 50, 50, false, false},
					    {27, "Piratas", 50, 50, false, false},
					    {28, "Kraken", 50, 50, true, false},
					    
					    {29, "Zombi", 50, 50, false, false},
					    {30, "Sombra tenebrosa", 50, 50, false, false},
					    {31, "Ratas rabiosas", 50, 50, false, false},
					    {32, "Jinete sin Cabeza", 50, 50, true, false},
					    
					    {33, "Esqueletos", 50, 50, false, false},
					    {34, "Hordas de Silver fish", 50, 50, false, false},
					    {35, "Estatua viviente", 50, 50, false, false},
					    {36, "Medusa", 50, 50, true, false},
					    
					    {37, "Slime de magma", 50, 50, false, false},
					    {38, "Sabueso de fuego", 50, 50, false, false},
					    {39, "Pyronita", 50, 50, false, false},
					    {40, "Charizard", 50, 50, true, false},
					    
					    {41, "Valquiria", 50, 50, false, false},
					    {42, "Grifos", 50, 50, false, false},
					    {43, "Drifblim", 50, 50, false, false},
					    {44, "Quetzalcóatl", 50, 50, true, false},
					    
					    {45, "Demonio", 50, 50, false, false},
					    {46, "Cerbero", 50, 50, false, false},
					    {47, "Salamandra", 50, 50, false, false},
					    {48, "Lucifer", 50, 50, true, false},
					    
					    {49, "Hechicero", 50, 50, false, false},
					    {50, "Mímico", 50, 50, false, false},
					    {51, "Libro poseído", 50, 50, false, false},
					    {52, "Supremo Archivista", 50, 50, true, false},
					    
					    {53, "Cíclope", 50, 50, false, false},
					    {54, "Pegaso", 50, 50, false, false},
					    {55, "Semidioses", 50, 50, false, false},
					    {56, "Zeus", 50, 50, true, false},
					    
					    {57, "Ángeles caídos", 50, 50, false, false},
					    {58, "Fénix", 50, 50, false, false},
					    {59, "Ghast", 50, 50, false, false},
					    {60, "Hades", 50, 50, true, false},
					};
		
			    String sql = "INSERT INTO Enemigos (ID_enemigos, Nombre, Dano, Vida_Max, Jefe, Baja_logica_Habilitado)"
			               + " SELECT ?, ?, ?, ?, ?, ?"
			               + " WHERE NOT EXISTS (SELECT 1 FROM Enemigos WHERE ID_enemigos = ?)";
		
			    try (PreparedStatement stmt = con.prepareStatement(sql)) {
			    	 
			        for (Object[] e : enemigos) {
			            int id_enemigos = (int) e[0];
			            
			            stmt.setInt(1, id_enemigos);
			            stmt.setString(2, (String) e[1]);
			            stmt.setInt(3, (int) e[2]);
			            stmt.setInt(4, (int) e[3]);      
			            stmt.setBoolean(5, (boolean) e[4]);
			            stmt.setBoolean(6, (boolean) e[5]);
			            stmt.setInt(7, id_enemigos);        
			            stmt.addBatch();
			        }
		
			        int[] resultados = stmt.executeBatch(); 
		
			        System.out.println("--- Resumen de Inserciones de Enemigos ---");
			        int insertados = 0;
			        for (int res : resultados) {
			            if (res > 0) {
			                insertados++;
			            }
			        }
			        System.out.println(insertados + " enemigo(s) insertado(s). Las restantes ya existían.");
		
			    } catch (SQLException e) {
			        System.err.println("Error al registrar enemigos masivamente:");
			        e.printStackTrace();
			    }
			}
			
			public void RegistrarEnemigosLugaresMasivoPorBloques(Connection con) {
			    int[] lugaresOrden = {
			        2,  3,  4,  5,  6,
			        7,  8,  9, 10, 11,
			       12, 13, 14, 15, 16
			    };
		
			    java.util.List<int[]> relaciones = new java.util.ArrayList<>();
			    int enemyId = 1;
			    for (int placeIdx = 0; placeIdx < lugaresOrden.length; placeIdx++) {
			        int idLugar = lugaresOrden[placeIdx];
			        for (int j = 0; j < 4; j++) { 
			            if (enemyId > 60) break;
			            relaciones.add(new int[] { enemyId, idLugar });
			            enemyId++;
			        }
			    }
		
			    String sql = "INSERT INTO Enemigos_Lugares (ID_enemigos, ID_lugar) "
			               + "SELECT ?, ? "
			               + "WHERE NOT EXISTS (SELECT 1 FROM Enemigos_Lugares WHERE ID_enemigos = ?)";
		
			    try (PreparedStatement stmt = con.prepareStatement(sql)) {
			        for (int[] r : relaciones) {
			            int idEnem = r[0];
			            int idLugar = r[1];
		
			            stmt.setInt(1, idEnem);
			            stmt.setInt(2, idLugar);
			            stmt.setInt(3, idEnem);
		
			            stmt.addBatch();
			        }
		
			        int[] resultados = stmt.executeBatch();
		
			        int insertados = 0;
			        for (int res : resultados) {
			            if (res > 0) insertados++;
			        }
			        System.out.println("--- Resumen Enemigos_Lugares por bloques ---");
			        System.out.println(insertados + " relación(es) insertada(s). Las restantes ya existían o fueron ignoradas.");
			    } catch (SQLException e) {
			        System.err.println("Error al registrar Enemigos_Lugares por bloques:");
			        e.printStackTrace();
			    }
			}
			
			public void RegistrarTiendasMasivo(Connection con) {
			    Object[][] tiendas = {
			        {1, "Tienda del Bosque", true},
			        {2, "Tienda del Desierto de cenizas", true},
			        {3, "Tienda de la Montaña Nevada", true},
			        {4, "Tienda de las Ruinas Antiguas", true},
			        {5, "Tienda del Pantano", true},
			        {6, "Tienda del Castillo", true},
			        {7, "Tienda del Oceano con Barcos Hundidos", true},
			        {8, "Tienda de las Catacumbas Perdidas", true},
			        {9, "Tienda del Laberinto Subterraneo", true},
			        {10, "Tienda del Volcan", true},
			        {11, "Tienda del Cielo Nuboso", true},
			        {12, "Tienda del Circulo del Infierno", true},
			        {13, "Tienda de la Biblioteca Magica", true},
			        {14, "Tienda del Olimpo", true},
			        {15, "Tienda del Abismo del Infierno", true}
			    };

			    String sql = "INSERT INTO Tienda (ID_tienda, Nombre, Baja_logica_Habilitado) "
			               + "SELECT ?, ?, ? "
			               + "WHERE NOT EXISTS (SELECT 1 FROM Tienda WHERE ID_tienda = ?)";

			    try (PreparedStatement stmt = con.prepareStatement(sql)) {

			        for (Object[] t : tiendas) {
			            int id_tienda = (int) t[0];

			            stmt.setInt(1, id_tienda);
			            stmt.setString(2, (String) t[1]);
			            stmt.setBoolean(3, (boolean) t[2]);

			            stmt.setInt(4, id_tienda);

			            stmt.addBatch();
			        }

			        int[] resultados = stmt.executeBatch();

			        System.out.println("--- Resumen de Inserciones Masivas de Tiendas ---");
			        int insertadas = 0;
			        for (int res : resultados) {
			            if (res > 0) insertadas++;
			        }
			        System.out.println(insertadas + " tienda(s) insertada(s). Las restantes ya existían.");

			    } catch (SQLException e) {
			        System.err.println("Error al registrar tiendas masivamente:");
			        e.printStackTrace();
			    }
			}

		public void RegistrarTiendaObjetos (Connection con) throws SQLException { 
			Object[][] Tienda_Objetos = {
			        {1, 1, 1, 20},
			        {2, 1, 2, 25},
			        {3, 1, 4, 25},
			        {4, 1, 5, 30},
			        {5, 1, 7, 50},
			        {6, 2, 1, 20},
			        {7, 2, 2, 25},
			        {8, 2, 4, 25},
			        {9, 2, 5, 30},
			        {10, 2, 7, 50},
			        {11, 2, 3, 40},
			        {12, 3, 1, 25},
			        {13, 3, 2, 30},
			        {14, 3, 4, 30},
			        {15, 3, 5, 35},
			        {16, 3, 7, 55},
			        {17, 3, 3, 45},
			        {18, 3, 6, 60},
			        {19, 4, 1, 25},
			        {20, 4, 2, 30},
			        {21, 4, 4, 30},
			        {22, 4, 5, 35},
			        {23, 4, 7, 55},
			        {24, 4, 3, 45},
			        {25, 4, 6, 60},
			        {26, 4, 8, 70},
			        {27, 5, 1, 25},
			        {28, 5, 2, 30},
			        {29, 5, 4, 30},
			        {30, 5, 5, 35},
			        {31, 5, 7, 55},
			        {32, 5, 6, 65},
			        {33, 5, 8, 75},
			        {34, 6, 1, 30},
			        {35, 6, 2, 35},
			        {36, 6, 4, 35},
			        {37, 6, 5, 40},
			        {38, 6, 7, 60},
			        {39, 6, 3, 50},
			        {40, 6, 6, 70},
			        {41, 6, 8, 85},
			        {42, 7, 1, 30},
			        {43, 7, 2, 35},
			        {44, 7, 4, 35},
			        {45, 7, 5, 40},
			        {46, 7, 7, 60},
			        {47, 7, 3, 50},
			        {48, 7, 6, 70},
			        {49, 7, 8, 85},
			        {50, 8, 1, 35},
			        {51, 8, 2, 40},
			        {52, 8, 4, 40},
			        {53, 8, 5, 45},
			        {54, 8, 7, 65},
			        {55, 8, 3, 55},
			        {56, 8, 6, 75},
			        {57, 8, 8, 90},
			        {58, 9, 1, 35},
			        {59, 9, 2, 40},
			        {60, 9, 4, 40},
			        {61, 9, 5, 45},
			        {62, 9, 7, 65},
			        {63, 9, 3, 55},
			        {64, 9, 6, 75},
			        {65, 9, 8, 90},
			        {66, 10, 1, 40},
			        {67, 10, 2, 45},
			        {68, 10, 4, 45},
			        {69, 10, 5, 50},
			        {70, 10, 7, 70},
			        {71, 10, 3, 60},
			        {72, 10, 6, 80},
			        {73, 10, 8, 95},
			        {74, 11, 1, 45},
			        {75, 11, 2, 50},
			        {76, 11, 4, 50},
			        {77, 11, 5, 55},
			        {78, 11, 7, 75},
			        {79, 11, 3, 65},
			        {80, 11, 6, 85},
			        {81, 11, 8, 100},
			        {82, 12, 1, 50},
			        {83, 12, 2, 55},
			        {84, 12, 4, 55},
			        {85, 12, 5, 60},
			        {86, 12, 7, 80},
			        {87, 12, 3, 70},
			        {88, 12, 6, 90},
			        {89, 12, 8, 110},
			        {90, 13, 1, 50},
			        {91, 13, 2, 55},
			        {92, 13, 4, 55},
			        {93, 13, 5, 60},
			        {94, 13, 7, 80},
			        {95, 13, 3, 70},
			        {96, 13, 6, 90},
			        {97, 13, 8, 110},
			        {98, 14, 1, 55},
			        {99, 14, 2, 60},
			        {100, 14, 4, 60},
			        {101, 14, 5, 65},
			        {102, 14, 7, 85},
			        {103, 14, 3, 75},
			        {104, 14, 6, 95},
			        {105, 14, 8, 120},
			        {106, 15, 1, 60},
			        {107, 15, 2, 65},
			        {108, 15, 4, 65},
			        {109, 15, 5, 70},
			        {110, 15, 7, 90},
			        {111, 15, 3, 80},
			        {112, 15, 6, 100},
			        {113, 15, 8, 130}
			    };
    	  
	        String sql = "INSERT INTO Tienda_Objetos (ID_tienda_objetos, ID_tienda, ID_objeto, Precio)"
	            + " SELECT ?, ?, ?, ?"
	            + " WHERE NOT EXISTS (SELECT 1 FROM Tienda_Objetos WHERE ID_tienda_objetos = ?)";
	
	        try (PreparedStatement stmt = con.prepareStatement(sql)) {
	         
	        	for (Object[] t : Tienda_Objetos) {
	        		int id_tienda_objetos = (int) t[0];
		            
		            stmt.setInt(1, id_tienda_objetos);
		            stmt.setInt(2, (int) t[1]);
		            stmt.setInt(3, (int) t[2]);
		            stmt.setInt(4, (int) t[3]);      
		            stmt.setInt(5, id_tienda_objetos);        
		            stmt.addBatch();
	        	}
	        	int[] resultados = stmt.executeBatch(); 
	        	int insertados = 0;
		        for (int res : resultados) {
		            if (res > 0) {
		                insertados++;
		            }
		        }
		        System.out.println("--- Resumen Enemigos_Lugares por bloques ---");
		        System.out.println(insertados + " relación(es) insertada(s). Las restantes ya existían o fueron ignoradas.");
	        } 
			       
		}
		
		public static void RegistrarRecorridosPartidaMasivo(Connection con, int idPartida) {
		    Object[][] recorridospartida = {
		        {1, 1, idPartida, false},
		        {2, 2, idPartida, false},
		        {3, 3, idPartida, false},
		        {4, 4, idPartida, false},
		        {5, 5, idPartida, false},
		        {6, 6, idPartida, false},
		        {7, 7, idPartida, false},
		        {8, 8, idPartida, false},
		        {9, 9, idPartida, false},
		        {10, 10, idPartida, false},
		        {11, 11, idPartida, false},
		        {12, 12, idPartida, false},
		        {13, 13, idPartida, false},
		        {14, 14, idPartida, false},
		        {15, 15, idPartida, false},
		        {16, 16, idPartida, false},
		        {17, 17, idPartida, false}
		    };

		    String sql = "INSERT INTO recorridos_partida (ID_recorrido, ID_partida, Flag)"
		               + " SELECT ?, ?, ?"
		               + " WHERE NOT EXISTS (SELECT 1 FROM recorridos_partida WHERE ID_recorrido = ? AND ID_partida = ?)";

		    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		        for (Object[] r : recorridospartida) {
		            stmt.setInt(1, (Integer) r[1]);
		            stmt.setInt(2, (Integer) r[2]);
		            stmt.setBoolean(3, (Boolean) r[3]);
		            stmt.setInt(4, (Integer) r[1]);
		            stmt.setInt(5, (Integer) r[2]);
		            stmt.addBatch();
		        }

		        int[] resultados = stmt.executeBatch();

		        System.out.println("--- Resumen de Inserciones Masivas de recorridos_partida ---");
		        int insertados = 0;
		        for (int res : resultados) {
		            if (res > 0) insertados++;
		        }
		        System.out.println(insertados + " recorridos_partida(s) insertado(s). Los restantes ya existían.");

		    } catch (SQLException e) {
		        System.err.println("Error al registrar recorridos_partida masivamente:");
		        e.printStackTrace();
		    }
		}


	}
		
		
		
		

