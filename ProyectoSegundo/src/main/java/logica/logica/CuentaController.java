package logica;

import logica.cuentas;

public class CuentaController {

    public String manejarRegistro(String correo, String contra, String contraConf, String nombre, String tipo) {

    	if (tipo.equals("registro")) {
    		if (!cuentas.CorreoValido(correo)) {
                return "El formato del correo es inválido.";
            }
            
    		if (!cuentas.ContraValida(contra)) {
    			return "El formato de la contraseña es inválido.";
    		}
    		
            if (!cuentas.VerificarCorreoUso(correo)) {
                return "El correo ya está en uso";
            }
            
            if (!cuentas.CompararContra(contra,contraConf)) {
            	return "Las contraseñas no coinciden";
            }
            
            if (cuentas.RegistrarCuenta(correo, contra, nombre)) {
                return "Registro exitoso";
            } else {
                return "Ocurrió un error al registrar la cuenta.";
            }
            
    	}else if (tipo.equals("inicio")){
    		
    		int id_usuario = cuentas.CorreoID(correo);
    		if (id_usuario == -1) {
    			return "El correo no existe";
    		}
    		
    		if (cuentas.IDcontra(id_usuario,contra)) {
    			return "Inicio exitoso";
    		}else {
    			return "contraseña incorrecta";
    		}
    		
    	}
		 return ("error verificando tipo de request");
    }

}