create schema if not exists Proyecto;
use Proyecto;

Create table  if not exists Inventario (
ID_inventario INT NOT NULL AUTO_INCREMENT,
Slots INT NOT NULL,
Baja_Logica_Habilitado INT NOT NULL,
PRIMARY KEY (ID_inventario)
);
Create table  if not exists Usuario(
id_usuario INT NOT NULL AUTO_INCREMENT,
nombre VARCHAR(100),
contra VARCHAR(100),
correo VARCHAR(100),
Baja_Logica_Habilitado INT,
PRIMARY KEY (ID_usuario)
);
CREATE TABLE  if not exists Lugares (
    ID_lugar INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Etapa INT NOT NULL,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_lugar)
);
CREATE TABLE  if not exists Tienda (
    ID_tienda INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_tienda)
);
CREATE TABLE  if not exists Objetos (
    ID_objeto INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Tipo VARCHAR(50),
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_objeto)
);
CREATE TABLE  if not exists Habilidades (
    ID_habilidad INT NOT NULL AUTO_INCREMENT,
    Mana_Coste INT,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_Habilidad)
);
CREATE TABLE  if not exists Imagenes (
    ID_imagen INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    URL VARCHAR(255) NOT NULL,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_imagen)
);
CREATE TABLE  if not exists NPC (
    ID_npc INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Genero CHAR(50),
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_npc)
);
CREATE TABLE  if not exists Enemigos (
    ID_enemigos INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Daño INT,
    Vida_maxima INT,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_enemigos)
);
CREATE TABLE  if not exists Personaje (
    ID_personaje INT NOT NULL AUTO_INCREMENT,
    Mana_Max INT NOT NULL,
    Vida_Max INT,
    Daño INT,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_personaje)
);
CREATE TABLE  if not exists Partida (
ID_partida INT NOT NULL AUTO_INCREMENT,
Fecha_creación DATETIME,
Fecha_último_registro DATETIME,
Baja_logica_Habilitado INT,
PRIMARY KEY (ID_partida)
);
CREATE TABLE  if not exists Personaje_Partida (
ID_personaje_partida INT NOT NULL AUTO_INCREMENT,
Nombre VARCHAR(100) NOT NULL,
  Vida_Actual INT,
    Mana_Actual INT,
    Defensa_Actual INT,
Daño_Actual INT,
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_personaje_partida)
);
CREATE TABLE  if not exists Recorridos (
    ID_recorrido INT NOT NULL AUTO_INCREMENT,
    Zonas_Recorrida VARCHAR(100),
    Baja_logica_Habilitado INT,
    PRIMARY KEY (ID_recorrido)
);

CREATE TABLE  if not exists Personaje_Habilidad (
ID_personaje_habilidad INT NOT NULL AUTO_INCREMENT,
ID_personaje INT NOT NULL,
ID_Habilidad INT NOT NULL,
PRIMARY KEY (ID_personaje_habilidad),
FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
FOREIGN KEY (ID_Habilidad) REFERENCES Habilidades(ID_Habilidad)
);
  
CREATE TABLE  if not exists Personaje_Imagenes (
ID_personaje_imagen INT NOT NULL AUTO_INCREMENT,
ID_personaje INT NOT NULL,
ID_imagen INT NOT NULL,
PRIMARY KEY (ID_personaje_imagen),
FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
FOREIGN KEY (ID_imagen ) REFERENCES Imagenes (ID_imagen )
);

CREATE TABLE  if not exists Personaje_Personaje_Partida (
ID_personaje_personaje_partida  INT NOT NULL AUTO_INCREMENT,
ID_personaje INT NOT NULL,
ID_personaje_partida INT NOT NULL,
PRIMARY KEY (ID_personaje_personaje_partida),
FOREIGN KEY (ID_personaje) REFERENCES Personaje(ID_personaje),
FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
); 

CREATE TABLE  if not exists Partida_Personaje_Partida (
ID_partida_personaje_partida  INT NOT NULL AUTO_INCREMENT,
ID_partida INT NOT NULL,
ID_personaje_partida INT NOT NULL,
PRIMARY KEY (ID_partida_personaje_partida),
FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
); 

CREATE TABLE  if not exists Partida_Recorridos (
ID_partida_recorrido INT NOT NULL AUTO_INCREMENT,
ID_partida INT NOT NULL,
ID_recorrido INT NOT NULL,
PRIMARY KEY (ID_partida_recorrido),
FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)
); 

CREATE TABLE  if not exists Lugares_Recorridos (
ID_lugar_recorrido  INT NOT NULL AUTO_INCREMENT,
ID_lugar INT NOT NULL,
ID_recorrido INT NOT NULL,
PRIMARY KEY (ID_lugar_recorrido),
FOREIGN KEY (ID_lugar) REFERENCES Lugares(ID_lugar),
FOREIGN KEY (ID_recorrido) REFERENCES Recorridos(ID_recorrido)
); 
 
 
CREATE TABLE  if not exists Inventario_Personaje_Partida (
ID_inventario_personaje_partida  INT NOT NULL AUTO_INCREMENT,
ID_inventario INT NOT NULL,
ID_personaje_partida INT NOT NULL,
PRIMARY KEY (ID_inventario_personaje_partida),
FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),
FOREIGN KEY (ID_personaje_partida) REFERENCES Personaje_Partida(ID_personaje_partida)
);  

CREATE TABLE  if not exists Inventario_Objeto (
ID_inventario_objeto INT NOT NULL AUTO_INCREMENT,
ID_inventario INT NOT NULL,
ID_objeto INT NOT NULL,
precio INT NOT NULL,
stock INT NOT NULL,
PRIMARY KEY (ID_inventario_objeto),
FOREIGN KEY (ID_inventario) REFERENCES Inventario(ID_inventario),
FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)
);
  
CREATE TABLE  if not exists Tienda_Objeto (
ID_tienda_objeto INT NOT NULL AUTO_INCREMENT,
ID_tienda INT NOT NULL,
ID_objeto INT NOT NULL,
precio INT NOT NULL,
stock INT NOT NULL,
PRIMARY KEY (ID_tienda_objeto),
FOREIGN KEY (ID_tienda) REFERENCES Tienda(ID_tienda),
FOREIGN KEY (ID_objeto) REFERENCES Objetos(ID_objeto)
);

CREATE TABLE  if not exists Partida_Usuario (
ID_partida_usuario  INT NOT NULL AUTO_INCREMENT,
ID_partida INT NOT NULL,
ID_usuario INT NOT NULL,
PRIMARY KEY (ID_partida_usuario),
FOREIGN KEY (ID_partida) REFERENCES Partida(ID_partida),
FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)
); 

CREATE TABLE  if not exists Imagenes_Usuario (
ID_imagen_usuario  INT NOT NULL AUTO_INCREMENT,
ID_imagen INT NOT NULL,
ID_usuario INT NOT NULL,
PRIMARY KEY (ID_imagen_usuario),
FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID_usuario)
); 

CREATE TABLE  if not exists Imagenes_Enemigos (
ID_imagen_enemigos int NOT NULL AUTO_INCREMENT,
ID_imagen INT NOT NULL,
ID_enemigos INT NOT NULL,
PRIMARY KEY (ID_imagen_enemigos),
FOREIGN KEY (ID_imagen) REFERENCES Imagenes(ID_imagen),
FOREIGN KEY (ID_enemigos) REFERENCES Enemigos(ID_enemigos)
);
