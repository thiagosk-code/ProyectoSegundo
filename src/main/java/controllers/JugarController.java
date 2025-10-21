package controllers;

import logica.Jugar;

public class JugarController {

	public String manejarJuego (int idPartida, int idPersonaje) {
		String mensaje;
		
		if (Jugar.VerificarInicio1(idPartida)) {
			return mensaje = Jugar.Inicio1(idPartida);
		}
		
		if (Jugar.VerificarInicio2(idPartida)) {
			return mensaje = Jugar.Inicio2(idPartida,idPersonaje);
		}
		return "";
	}
	 
}
