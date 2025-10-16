package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.PersonajePartidaInfo;
import java.io.IOException;

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
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partida no es un número válido.");
            return;
        }

        try {
            PersonajePartidaInfo detalles = partidaController.obtenerPartidaExistente(idPartida, correo);

            if (detalles == null) {
                detalles = partidaController.crearNuevaPartida(idPartida, correo);
            }

            if (detalles != null) {
                request.setAttribute("personajeInfo", detalles);
                request.getRequestDispatcher("/infoPartida.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fatal: No se pudo cargar ni crear la partida. Ver logs del servidor para detalles.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("Error al obtener/crear partida: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Excepción en servidor: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
