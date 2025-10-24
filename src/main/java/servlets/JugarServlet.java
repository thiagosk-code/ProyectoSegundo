package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.PersonajePartidaInfo;
import controllers.PartidaController;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/JugarServlet") 
public class JugarServlet extends HttpServlet {
    private final PartidaController partidaController = new PartidaController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion"); 
        
        if (session == null || session.getAttribute("correo") == null || session.getAttribute("idPartidaActual") == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }

        if ("guardarYSalir".equals(accion)) {
            int idPartida = (int) session.getAttribute("idPartidaActual");
            String correo = (String) session.getAttribute("correo");
            PersonajePartidaInfo personajeActual = (PersonajePartidaInfo) session.getAttribute("personajeInfoActual");

            if (personajeActual == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Objeto de personaje no encontrado en sesión.");
                return;
            }
           
            if (personajeActual.getIdPartida() == 0) {
                 personajeActual.setIdPartida(idPartida);
            }

            try {
                boolean guardadoExitoso = partidaController.actualizarPartida(personajeActual);

                if (guardadoExitoso) {
                    PersonajePartidaInfo detallesActualizados = partidaController.obtenerPartidaExistente(idPartida, correo);
                    
                    if (detallesActualizados != null) {
                        session.setAttribute("personajeInfoActual", detallesActualizados);
                    }
                    
                    response.sendRedirect("infoPartida.jsp"); 
                } else {
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fallo al guardar la partida en la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de servidor al guardar/salir: " + e.getMessage());
            }
        } else {
            request.getRequestDispatcher("/jugar.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST no está permitido para esta operación.");
    }
}