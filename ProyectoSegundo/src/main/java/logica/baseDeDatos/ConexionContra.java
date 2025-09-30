package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import logica.baseDeDatos.crearBD;

public class ConexionContra {
	
	private crearBD cB = new crearBD();
	
	public Connection conectar() {
		
		Connection conn = null;
		
		try {
			cB.crearBase();
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			String url = "jdbc:mysql://localhost:3306/Proyecto?useSSL=false&serverTimezone=UTC";
			conn = DriverManager.getConnection(url,"root","root");
			
			
		}catch (ClassNotFoundException e) {
			System.out.print("Error en la conexión con el driver");
		}catch (SQLException ex) {
			System.out.print("Error en la conexión con la base");
		}
		return conn;
		
	}
}
