package logica;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/CuentaServlet")
public class CuentaServlet extends HttpServlet {
    
    private final CuentaController controller = new CuentaController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String contra = request.getParameter("contra");
        String contraConf = request.getParameter("contraConf");
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        
        String mensaje = controller.manejarRegistro(correo, contra, contraConf, nombre, tipo);
        
        if (tipo.equals("registro")) {           
            if (mensaje.equals("Registro exitoso")) {
            	response.sendRedirect("PostInicio.html");
            }else {
            	request.setAttribute("mensaje", mensaje);
            	request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        }else if (tipo.equals("inicio")) {
            if (mensaje.equals("Inicio exitoso")) {
            	response.sendRedirect("PostInicio.html");
            }else {
            	request.setAttribute("mensaje", mensaje);
            	request.getRequestDispatcher("inicio.jsp").forward(request, response);
            }
        }
        
        
    }
}