package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.PersonajePartidaInfo;
import java.io.IOException;
import java.sql.SQLException;

import controllers.PartidaController;

@SuppressWarnings("serial")
@WebServlet("/PartidasServlet")
public class PartidasServlet extends HttpServlet {

    private final PartidaController partidaController = new PartidaController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id_partidaStr = request.getParameter("idPartida");
       
        String correo = (String) request.getSession().getAttribute("correo");

        if (id_partidaStr == null || correo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros incompletos (ID de partida o usuario no encontrado en sesión).");
            return;
        }
        int idPartida;
        try {
        	idPartida = Integer.parseInt(id_partidaStr);
        	PersonajePartidaInfo detalles = null;
        	detalles = partidaController.obtenerPartidaExistente(idPartida, correo);
        	        
        	if (detalles == null) {
        		System.out.println("Partida slot ID " + idPartida + " no encontrada. Intentando crear una nueva...");
        		detalles = partidaController.crearNuevaPartida(idPartida, correo);
        	}
        	
        	if (detalles != null) {
        		request.setAttribute("personajeInfo", detalles);
        		request.getRequestDispatcher("/infoPartida.jsp").forward(request, response);
        		
        	}else {
        		response.sendRedirect("error.jsp?msg=Error fatal: No se pudo caragr ni crear la partida. Verifique la conexión a BD.");
        	}
        }catch (NumberFormatException e) {
        		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partida no es un número válido");
        	}

        try {
            int idPartida = Integer.parseInt(id_partidaStr);
            PersonajePartidaInfo detalles = partidaController.obtenerDetallesPartida(idPartida, correo);

            if (detalles != null) {
            
                request.setAttribute("personajeInfo", detalles); 
                request.getRequestDispatcher("/infoPartida.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp?msg=No se encontró personaje para esta partida.");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partida no es válido.");
        } catch (SQLException e) {
            
            throw new ServletException("Error de base de datos.", e);
        }
    } 
} 
