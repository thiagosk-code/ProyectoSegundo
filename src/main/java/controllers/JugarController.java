package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import baseDeDatos.ConexionContra;
import jakarta.servlet.http.HttpSession;
import logica.Jugar;
import logica.PersonajePartidaInfo;

public class JugarController {

	public String manejarJuego(int idPartida, String accion, HttpSession session) {
        String estadoActual = (String) session.getAttribute("estadoJuego");
        
        if (!Jugar.VerificarFlag(idPartida, 2)) { 
            
            if (esAccionValida(accion, 1, 6)) {
                String mensaje = Jugar.Inicio2(idPartida, accion);
                
                Jugar.ActivarFlag(idPartida, 2); 
                actualizarEstadoActual(idPartida, mensaje); 
                session.setAttribute("estadoJuego", "transicion_inicio"); 
                
                ActualizarAtributosPersonajeInicial(session, idPartida); 
                
                return mensaje; 
            }
            
            if (Jugar.VerificarFlag(idPartida, 1) || estadoActual == null) {
                if (!accion.equals("continuar") && !esAccionValida(accion, 1, 5)) {
                    return "no es valido"; 
                }
            }
            return Jugar.Inicio1();
        }

        if (estadoActual == null) {
            String mensajeDB = obtenerEstadoActual(idPartida);
            
            if (mensajeDB != null && !mensajeDB.contains("Qué querés hacer?") && !mensajeDB.contains("1. Tienda")) {
                
                session.setAttribute("estadoJuego", "transicion_inicio");
                estadoActual = "transicion_inicio"; 
                
                if (accion.equals("1") || accion.equalsIgnoreCase("seguir")) {
                     session.setAttribute("estadoJuego", "menu_principal");
                     String menuMsg = Jugar.mostrarMenuPrincipal();
                     actualizarEstadoActual(idPartida, menuMsg);
                     return menuMsg;
                }
                
                return mensajeDB;
                
            } else {
                session.setAttribute("estadoJuego", "menu_principal");
                estadoActual = "menu_principal";
            }
        }
        
        if (accion.equals("0") && ("tienda".equals(estadoActual) || "inventario".equals(estadoActual))) {
            session.setAttribute("estadoJuego", "menu_principal");
            String menuMsg = Jugar.mostrarMenuPrincipal();
            actualizarEstadoActual(idPartida, menuMsg);
            return menuMsg;
        }
        
        if ("menu principal".equals(accion)) { 
            session.setAttribute("estadoJuego", "menu_principal");
            String menuMsg = Jugar.mostrarMenuPrincipal();
            actualizarEstadoActual(idPartida, menuMsg);
            return menuMsg;
        }

        if ("transicion_inicio".equals(estadoActual)) {
            if (accion.equals("1") || accion.equalsIgnoreCase("seguir")) {
                session.setAttribute("estadoJuego", "menu_principal");
                String menuMsg = Jugar.mostrarMenuPrincipal();
                actualizarEstadoActual(idPartida, menuMsg); 
                return menuMsg;
            }
            return "no es valido"; 
        }

        if ("menu_principal".equals(estadoActual)) {
            switch (accion) {
                case "1": 
                    session.setAttribute("estadoJuego", "tienda");
                    return Jugar.mostrarTienda(idPartida, session);
                case "2": 
                    session.setAttribute("estadoJuego", "inventario");
                    return Jugar.mostrarInventario(idPartida, session);
                case "3": 
                    return "¡Te preparás para luchar! (modo combate en desarrollo)";
                default:
                    return "no es valido"; 
            }
        }

        if ("tienda".equals(estadoActual)) {
            String resultadoCompra = Jugar.comprarObjeto(idPartida, accion, session);
            
            if ("menu principal".equals(resultadoCompra)) { 
                return manejarJuego(idPartida, "menu principal", session); 
            }
            
            if ("no es valido".equals(resultadoCompra)) { 
                return "no es valido"; 
            }
            
            actualizarEstadoActual(idPartida, resultadoCompra);
            return resultadoCompra; 
        }

        if ("inventario".equals(estadoActual)) {
             return "no es valido"; 
        }
        
        return "no es valido"; 
    }  
            
    private boolean esAccionValida(String accion, int min, int max) {
        try {
            int opcion = Integer.parseInt(accion);
            return opcion >= min && opcion <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String obtenerEstadoActual(int idPartida) {
        String sql = "SELECT Mensaje_Actual FROM partida where ID_partida = ?";
        ConexionContra cc = new ConexionContra();
        
        String mensajeActual = null;
        try (Connection conn = cc.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setInt(1, idPartida);
        	ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	mensajeActual = rs.getString("Mensaje_Actual");
            }

        } catch (SQLException ex) {
        	System.out.println("Error al obtener el mensaje actual: " + ex.getMessage());
        }
        
        if (mensajeActual.equals("mensaje predeterminado")) {
        	return mensajeActual = Jugar.Inicio1();
        }else{
        	return mensajeActual;
        }
    }
    
    public void actualizarEstadoActual(int idPartida, String mensajeActual) {
    	String sql = "UPDATE partida SET Mensaje_actual = ? WHERE ID_partida = ?";
    	ConexionContra cc = new ConexionContra();
    	
    	try (Connection conn = cc.conectar();
    			PreparedStatement stmt = conn.prepareStatement(sql)) {

    		stmt.setString(1, mensajeActual);
    		stmt.setInt(2, idPartida);
            stmt.executeUpdate();

    	} catch (SQLException ex) {
    		System.out.println("Error al actualizar el mensaje actual: " + ex.getMessage());
    	}
    }
    
    public void ActualizarAtributosPersonajeInicial (HttpSession session, int idPartida) {
    	PersonajePartidaInfo info = (PersonajePartidaInfo) session.getAttribute("personajeInfoActual");
    	int manaAct = 0;
    	int manaMax = 0;
        int vidaAct = 0;
        int vidaMax = 0;
        int dano = 0;
        int dinero = 0;
        String desc = null;
        String nombre = null;
        
        ConexionContra cc = new ConexionContra();
        
        String sql1 = "SELECT * FROM personaje p "
    			+ "JOIN personaje_partida pp ON p.ID_personaje = pp.ID_personaje "
    			+ "WHERE ID_partida = ?";

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql1)) {

            stmt.setInt(1, idPartida);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                manaAct = rs.getInt("Mana_Act");
                manaMax = rs.getInt("Mana_Max");
                vidaAct = rs.getInt("Vida_Act");
                vidaMax = rs.getInt("Vida_Max");
                dano = rs.getInt("Dano");
                dinero = rs.getInt("Dinero");
                desc = rs.getString("descripcion");
                nombre = rs.getString("nombre");
            }
            
        } catch (SQLException ex) {
            System.out.print("Error al obtener personaje: " + ex.getMessage());
        }

        String sql2 = "UPDATE personaje_partida SET Mana_Max = ?, Mana_Act = ?, "
                    + "Vida_Max = ?, Vida_Act = ?, Dano = ?, Dinero = ? WHERE ID_partida = ?";

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {

            stmt.setInt(1, manaMax);
            stmt.setInt(2, manaAct);
            stmt.setInt(3, vidaMax);
            stmt.setInt(4, vidaAct);
            stmt.setInt(5, dano);
            stmt.setInt(6, dinero);
            stmt.setInt(7, idPartida);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.print("Error al actualizar personaje: " + ex.getMessage());
        }

        info.setIdPartida(idPartida);           
        info.setNombrePersonaje(nombre);
        info.setVida_Actual(vidaAct);
        info.setMana_Actual(manaAct);
        info.setVida_Max(vidaMax);
        info.setMana_Max(manaMax);
        info.setDano(dano);
        info.setDinero(dinero);
        info.setDescripcion(desc);
         	
        session.setAttribute("personajeInfoActual", info);
    }

    public void ActualizarAtributosPersonaje (HttpSession session, int idPartida) {
    	PersonajePartidaInfo info = (PersonajePartidaInfo) session.getAttribute("personajeInfoActual");
        int manaAct = 0;
        int manaMax = 0;
        int vidaAct = 0;
        int vidaMax = 0;
        int dano = 0;
        int dinero = 0;
        String desc = null;
        String nombre = null;
        
        ConexionContra cc = new ConexionContra();
        
        String sql1 = "SELECT pp.*, p.nombre, p.descripcion FROM personaje p "
        			+ "JOIN personaje_partida pp ON p.ID_personaje = pp.ID_personaje "
        			+ "WHERE pp.ID_partida = ?";

        try (Connection conn = cc.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql1)) {

            stmt.setInt(1, idPartida);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                manaAct = rs.getInt("Mana_Act");
                manaMax = rs.getInt("Mana_Max");
                vidaAct = rs.getInt("Vida_Act");
                vidaMax = rs.getInt("Vida_Max");
                dano = rs.getInt("Dano");
                dinero = rs.getInt("Dinero");
                desc = rs.getString("descripcion");
                nombre = rs.getString("nombre");
            }
            
        } catch (SQLException ex) {
            System.out.print("Error al obtener personaje para el refresco: " + ex.getMessage());
            return;
        }
        
        info.setIdPartida(idPartida);           
        info.setNombrePersonaje(nombre);
        info.setVida_Actual(vidaAct);
        info.setMana_Actual(manaAct);
        info.setVida_Max(vidaMax);
        info.setMana_Max(manaMax);
        info.setDano(dano);
        info.setDinero(dinero);
        info.setDescripcion(desc);
         	
        session.setAttribute("personajeInfoActual", info);
    }
}
