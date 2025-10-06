package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import baseDeDatos.ConexionContra;

public class cuentas {
	
	public static boolean CorreoValido(String correo) {
		String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return correo != null && correo.matches(patron);
	}
	
	public static boolean ContraValida (String contra) {
		String patron = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#\\\\!@$%^&*()_+\\\\-\\\\[\\\\]{};':\"\\\\|,.<>\\\\/?]).{6,12}$";
		return contra != null && contra.matches(patron);
	}
	
	public static boolean VerificarCorreoUso(String correo) {
		String queryRegistro1 = "SELECT correo FROM Usuario";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    boolean x = true;
	    
	    try (PreparedStatement stmt = conn.prepareStatement(queryRegistro1)){
	    	ResultSet rs = stmt.executeQuery();
	    	while (rs.next()) {	    		
	    		if (rs.getString("correo").equals(correo)) {
	    			x = false;
	    		}
	    	}
	    }catch (SQLException e) {
	    	System.err.println("error, al verificar si el correo está en uso");
	    	e.printStackTrace();
	    }
	    
	    return x;
	}

	public static boolean CompararContra(String contra, String contraConf) {
		return contra.equals(contraConf);
	}
	
	public static boolean RegistrarCuenta(String correo,String contra,String nombre) {
		String salt = BCrypt.gensalt(12);
		String hashcontra = BCrypt.hashpw(contra, salt);
		
		String queryRegistro = "INSERT INTO Usuario (correo,contra,nombre) VALUES (?,?,?)";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	      
	    try (Connection connection = conn;
	    		PreparedStatement statement = connection.prepareStatement(queryRegistro)) {
	        statement.setString(1, correo);
	        statement.setString(2, hashcontra);
	        statement.setString(3, nombre);
	        
	        int rowsInserted = statement.executeUpdate();
	        if (rowsInserted > 0) {
	        	return true;
	        }else {
	        	return false;
	        }
	          
	    } catch (SQLException e) {
	        System.err.println("error al registrar usuario");
	        e.printStackTrace();
	        return false;
	    }
	}

	public static  int CorreoID(String correo) {
		String sql = "SELECT id_usuario FROM Usuario WHERE correo = ?";
		ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, correo);
		    ResultSet rs = stmt.executeQuery();
		    if (rs.next()) {
		    	return rs.getInt("id_usuario");
		    }
		}catch (SQLException e) {
			System.err.println("error al vincular un id al correo");
	        e.printStackTrace();
		}
		return -1;
	}
	
	public static boolean IDcontra(int id, String contra) {
	    String sql = "SELECT contra FROM Usuario WHERE id_usuario = ?";
	    ConexionContra cc = new ConexionContra();
	    Connection conn = cc.conectar();
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            String contraBD = rs.getString("contra");	            
	            return BCrypt.checkpw(contra, contraBD);
	        }
	    }catch (SQLException e) {
			System.err.println("error al comprobar contraseña");
	        e.printStackTrace();
		}
	    return false;
	}


}
