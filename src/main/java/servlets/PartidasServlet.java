	package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.PartidaController;
import logica.PersonajePartidaInfo;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("serial")
@WebServlet("/PartidasServlet")
public class PartidasServlet extends HttpServlet {

    private final PartidaController partidaController = new PartidaController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id_partidaStr = request.getParameter("idPartida");
       
        String correoUsuario = (String) request.getSession().getAttribute("correo");

        if (id_partidaStr == null || correo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros incompletos (ID de partida o usuario no encontrado en sesión).");
            return;
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
