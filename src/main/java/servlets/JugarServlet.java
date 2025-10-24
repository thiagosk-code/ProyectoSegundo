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
        String accion = request.getParameter("accion"); // Capturamos la acción de la URL
        
        // 1. Verificación de sesión
        if (session == null || session.getAttribute("correo") == null || session.getAttribute("idPartidaActual") == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }

        if ("guardarYSalir".equals(accion)) {
            // LÓGICA DE GUARDADO MOVIDA AQUI (GET)
            int idPartida = (int) session.getAttribute("idPartidaActual");
            String correo = (String) session.getAttribute("correo");
            PersonajePartidaInfo personajeActual = (PersonajePartidaInfo) session.getAttribute("personajeInfoActual");

            if (personajeActual == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Objeto de personaje no encontrado en sesión.");
                return;
            }
            
            // Asignar ID si es necesario (el controlador lo necesita para el UPDATE)
            if (personajeActual.getIdPartida() == 0) {
                 personajeActual.setIdPartida(idPartida);
            }

            try {
                // 2. Ejecutar la lógica de Guardado
                boolean guardadoExitoso = partidaController.actualizarPartida(personajeActual);

                if (guardadoExitoso) {
                    // 3. Recargar los datos frescos desde la BD
                    PersonajePartidaInfo detallesActualizados = partidaController.obtenerPartidaExistente(idPartida, correo);
                    
                    if (detallesActualizados != null) {
                        // 4. Sobrescribir el objeto en la SESIÓN con los datos actualizados
                        session.setAttribute("personajeInfoActual", detallesActualizados);
                    }
                    
                    // 5. REDIRECCIÓN (SENDREDIRECT) a infoPartida.jsp (Necesario al usar GET)
                    // NOTA: Usamos sendRedirect para que la URL se limpie y el botón no se ejecute dos veces al recargar
                    response.sendRedirect("infoPartida.jsp"); 
                } else {
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fallo al guardar la partida en la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de servidor al guardar/salir: " + e.getMessage());
            }
        } else {
            // Lógica para una petición GET normal (ej. movimiento, o recarga de jugar.jsp)
            request.getRequestDispatcher("/jugar.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // El doPost queda vacío o manejará solo acciones que no sean el guardado.
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST no está permitido para esta operación.");
    }
}