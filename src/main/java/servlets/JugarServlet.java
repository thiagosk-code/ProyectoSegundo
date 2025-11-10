package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.PersonajePartidaInfo;
import controllers.JugarController;
import controllers.PartidaController;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("serial")
@WebServlet("/JugarServlet") 
public class JugarServlet extends HttpServlet {
    private final PartidaController partidaController = new PartidaController();
    private final JugarController jugarController = new JugarController();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("correo") == null || session.getAttribute("idPartidaActual") == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }
        
        String accion = request.getParameter("accion");
        String estadoAct = request.getParameter("estado_actual");
        int idPartida = (int) session.getAttribute("idPartidaActual");
        PersonajePartidaInfo personajeActual = (PersonajePartidaInfo) session.getAttribute("personajeInfoActual");
        
        if ("guardarYSalir".equals(accion)) {
            
            if (personajeActual == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Datos de partida no encontrados para guardar.");
                return;
            }
            
            int idPartidaSlot = personajeActual.getIdPartidaSlot();
            String correo = (String) session.getAttribute("correo");

            try {
                
                boolean guardadoExitoso = partidaController.actualizarPartida(personajeActual);

                if (guardadoExitoso) {
                    
                    PersonajePartidaInfo detallesActualizados = partidaController.obtenerPartidaExistente(idPartidaSlot, correo);
                    
                    if (detallesActualizados != null) {
                        session.setAttribute("personajeInfoActual", detallesActualizados);
                    }
                    
                    response.sendRedirect("infoPartida.jsp"); 
                } else {
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fallo al guardar la partida en la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de servidor al guardar/salir: " + e.getMessage());
            }
        } else {

        	String mensaje;
            
        	if (estadoAct.equals("inicia")) {
        		accion = "continuar";
        	}
        	
            if (accion != null && !accion.trim().isEmpty()) {
                mensaje = jugarController.manejarJuego(idPartida, accion, session); 
                if (!mensaje.equals("no es valido")) {
                	jugarController.actualizarEstadoActual(idPartida,mensaje);
                }else {
                	mensaje = jugarController.obtenerEstadoActual(idPartida);
                }              
            } else {
                mensaje = jugarController.obtenerEstadoActual(idPartida); 
            }
            
            String mensajeFin = mensaje.replace("\r\n", "<br/>").replace("\n", "<br/>");
            request.setAttribute("mensaje", mensajeFin);           
            request.getRequestDispatcher("jugar.jsp").forward(request, response);
            
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST no está permitido para esta operación.");
    }
}