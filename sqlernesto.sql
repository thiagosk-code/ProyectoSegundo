create schema ProyectoV2;
use ProyectoV2;

CREATE TABLE Personaje (
    ID_personaje INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_personaje)
);

CREATE TABLE Habilidades (
    ID_Habilidad INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID_Habilidad)
);

CREATE TABLE Imagenes (
    ID_imagen INT NOT NULL AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID_imagen)
);

CREATE TABLE Partida (
    ID_partida INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    PRIMARY KEY (ID_partida)
);

CREATE TABLE Recorridos (
    ID_recorrido INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(255),
    PRIMARY KEY (ID_recorrido)
);

CREATE TABLE Lugares (
    ID_lugar INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (ID_lugar)
);

CREATE TABLE Inventario (
    ID_inventario INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID_inventario)
);

CREATE TABLE Objetos (
    ID_objeto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (ID_objeto)
);

CREATE TABLE Tienda (
    ID_tienda INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (ID_tienda)
);

CREATE TABLE Usuario (
    ID_usuario INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (ID_usuario)
);

CREATE TABLE Enemigos (
    ID_enemigos INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100),
    PRIMARY KEY (ID_enemigos)
);

CREATE TABLE Personaje_Partida (
    ID_personaje_partida INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ID_personaje_partida)
);

-- =========================
-- TABLAS DE RELACIÓN
-- =========================
CREATE TABLE Personaje_Habilidad (
    ID_personaje_habilidad INT NOT NULL AUTO_INCREMENT,   
    ID_personaje INT NOT NULL,
    ID_Habilidad INT NOT NULL,
    PRIMARY KEY (ID_personaje_habilidad),
    FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
    FOREIGN KEY (ID_Habilidad) REFERENCES Habilidades(ID_Habilidad)
);
  
CREATE TABLE Personaje_Imagenes (
    ID_personaje_imagen INT NOT NULL AUTO_INCREMENT,   
    ID_personaje INT NOT NULL,
    ID_imagen INT NOT NULL,
    PRIMARY KEY (ID_personaje_imagen),
    FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
    FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen)
);

CREATE TABLE Personaje_Personaje_Partida (
    ID_personaje_personaje_partida INT NOT NULL AUTO_INCREMENT,   
    ID_personaje INT NOT NULL,
    ID_personaje_partida INT NOT NULL,
    PRIMARY KEY (ID_personaje_personaje_partida),
    FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
    FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
); 

CREATE TABLE Partida_Personaje_Partida (
    ID_partida_personaje_partida INT NOT NULL AUTO_INCREMENT,   
    ID_partida INT NOT NULL,
    ID_personaje_partida INT NOT NULL,
    PRIMARY KEY (ID_partida_personaje_partida),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
    FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
); 

CREATE TABLE Partida_Recorridos (
    ID_partida_recorrido INT NOT NULL AUTO_INCREMENT,   
    ID_partida INT NOT NULL,
    ID_recorrido INT NOT NULL,
    PRIMARY KEY (ID_partida_recorrido),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
    FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)
); 

CREATE TABLE Lugares_Recorridos (
    ID_lugar_recorrido INT NOT NULL AUTO_INCREMENT,   
    ID_lugar INT NOT NULL,
    ID_recorrido INT NOT NULL,
    PRIMARY KEY (ID_lugar_recorrido),
    FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),
    FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)
); 
 
CREATE TABLE Inventario_Personaje_Partida (
    ID_inventario_personaje_partida INT NOT NULL AUTO_INCREMENT,   
    ID_inventario INT NOT NULL,
    ID_personaje_partida INT NOT NULL,
    PRIMARY KEY (ID_inventario_personaje_partida),
    FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),
    FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
);  

CREATE TABLE Inventario_Objeto (
    ID_inventario_objeto INT NOT NULL AUTO_INCREMENT,
    ID_inventario INT NOT NULL,
    ID_objeto INT NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (ID_inventario_objeto),
    FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),
    FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)
);
  
CREATE TABLE Tienda_Objeto (
    ID_tienda_objeto INT NOT NULL AUTO_INCREMENT,
    ID_tienda INT NOT NULL,
    ID_objeto INT NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (ID_tienda_objeto),
    FOREIGN KEY (ID_tienda) REFERENCES Tienda(ID_tienda),
    FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)
);

CREATE TABLE Partida_Usuario (
    ID_partida_usuario INT NOT NULL AUTO_INCREMENT,   
    ID_partida INT NOT NULL,
    ID_usuario INT NOT NULL,
    PRIMARY KEY (ID_partida_usuario),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
    FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)
); 

CREATE TABLE Partida_NPC (
    ID_partida_NPC INT NOT NULL AUTO_INCREMENT,   
    ID_partida INT NOT NULL,
    ID_NPC INT NOT NULL,
    PRIMARY KEY (ID_partida_NPC ),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
    FOREIGN KEY (ID_NPC) REFERENCES NPC(ID_NPC)
); 

CREATE TABLE Partida_Enemigos (
    ID_partida_enemigo INT NOT NULL AUTO_INCREMENT,   
    ID_partida INT NOT NULL,
    ID_enemigo INT NOT NULL,
    PRIMARY KEY (ID_partida_nemigo),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
    FOREIGN KEY (ID_enemigo) REFERENCES Enemigos(ID_enemigo)
); 

CREATE TABLE Lugares_Enemigos (
    ID_lugar_enemigo INT NOT NULL AUTO_INCREMENT,   
    ID_lugar INT NOT NULL,
    ID_enemigo INT NOT NULL,
    PRIMARY KEY (ID_lugar_enemigo),
    FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),
    FOREIGN KEY (ID_enemigo) REFERENCES Enemigos(ID_enemigo)
); 

CREATE TABLE Lugares_NPC (
    ID_lugar_NPC INT NOT NULL AUTO_INCREMENT,   
    ID_lugar INT NOT NULL,
    ID_NPC INT NOT NULL,
    PRIMARY KEY (ID_lugar_NPC),
    FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),
    FOREIGN KEY (ID_NPC) REFERENCES NPC(ID_NPC)
); 

CREATE TABLE Imagenes_Partida (
    ID_imagen_partida INT NOT NULL AUTO_INCREMENT,   
    ID_imagen INT NOT NULL,
    ID_partida INT NOT NULL,
    PRIMARY KEY (ID_imagen_NPC),
    FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
    FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida)
); 

CREATE TABLE Imagenes_NPC (
    ID_imagen_NPC INT NOT NULL AUTO_INCREMENT,   
    ID_imagen INT NOT NULL,
    ID_NPC INT NOT NULL,
    PRIMARY KEY (ID_imagen_NPC),
    FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
    FOREIGN KEY (ID_NPC) REFERENCES NPC(ID_NPC)
); 

CREATE TABLE Imagenes_Usuario (
    ID_imagen_usuario INT NOT NULL AUTO_INCREMENT,   
    ID_imagen INT NOT NULL,
    ID_usuario INT NOT NULL,
    PRIMARY KEY (ID_imagen_usuario),
    FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
    FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)
); 

CREATE TABLE Imagenes_Enemigos (
    ID_imagen_enemigos INT NOT NULL AUTO_INCREMENT,   
    ID_imagen INT NOT NULL,
    ID_enemigos INT NOT NULL,
    PRIMARY KEY (ID_imagen_enemigos),
    FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
    FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)
);
