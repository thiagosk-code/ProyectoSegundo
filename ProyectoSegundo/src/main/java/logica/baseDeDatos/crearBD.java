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
 
		String url = "jdbc:mysql://localhost:3306/?useTimezone=true&serverTimezone=UTC";
		String user = "root";
		String pwd = "57865578";
 
		
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
				if(!existe) {
					st.executeUpdate("CREATE DATABASE IF NOT EXISTS pruebaUsuario;");
					st.executeUpdate("USE pruebaUsuario;");
					st.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (\r\n"
							+ "id_usuario INT AUTO_INCREMENT PRIMARY KEY,\r\n"
							+ "correo VARCHAR(100) NOT NULL,\r\n"	
							+ "contra VARCHAR(50) NOT NULL,\r\n"							
							+ "nombre VARCHAR(50) NOT NULL)");
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		finally{
			if( rs != null){
				try{
				    rs.close();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
			if( con != null){
				try{
				    con.close();
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}
	}
}