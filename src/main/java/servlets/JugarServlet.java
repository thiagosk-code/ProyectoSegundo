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

    // El doGet está aquí para manejar una posible acción de movimiento o entrada.
    // Aunque no está implementado, lo dejamos para el flujo de juego.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lógica de juego o movimiento aquí. 
        // Si no tienes lógica de juego, puedes simplemente redirigir a jugar.jsp si hay sesión.
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("correo") == null || session.getAttribute("idPartidaActual") == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }

        // Si se llama por GET (ej. un movimiento) simplemente vuelve a cargar jugar.jsp con los datos de sesión
        request.getRequestDispatcher("/jugar.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion");
        
        // 1. Verificación de sesión
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
                    
                    // 5. Reenviar (FORWARD) a infoPartida.jsp (CLAVE PARA MOSTRAR LAS STATS)
                    request.getRequestDispatcher("/infoPartida.jsp").forward(request, response);
                } else {
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fallo al guardar la partida en la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de servidor al guardar/salir: " + e.getMessage());
            }
        } else {
            // Manejar otras acciones POST o simplemente volver a jugar.jsp
            request.getRequestDispatcher("/jugar.jsp").forward(request, response);
        }
    }
}