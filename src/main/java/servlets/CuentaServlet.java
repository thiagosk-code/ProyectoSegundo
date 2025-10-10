package servlets;

import java.io.IOException;

import controllers.CuentaController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
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
        
        if (tipo != null && tipo.equals("logout")) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/index.html"); 
            return; 
        }
        
        String mensaje = controller.manejarRegistro(correo, contra, contraConf, nombre, tipo);
        
        if (tipo != null && tipo.equals("registro")) {           
            if (mensaje.equals("Registro exitoso")) {
                HttpSession sesion=request.getSession();
                sesion.setAttribute("nombre", nombre);
                sesion.setAttribute("correo", correo);
                sesion.setAttribute("contra", contra);
            	response.sendRedirect("PostInicio.jsp");
            }else {
            	request.setAttribute("mensaje", mensaje);
            	request.setAttribute("correo", correo);
                request.setAttribute("contra", contra);
                request.setAttribute("contraConf", contraConf);
                request.setAttribute("nombre", nombre);
            	request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        }else if (tipo != null && tipo.equals("inicio")) {
            if (mensaje.equals("Inicio exitoso")) {
            	HttpSession sesion=request.getSession();
            	sesion.setAttribute("nombre", nombre);
            	sesion.setAttribute("correo", correo);
            	sesion.setAttribute("contra", contra);
            	response.sendRedirect("PostInicio.jsp");
            }else {
            	request.setAttribute("mensaje", mensaje);
            	request.setAttribute("correo", correo);
                request.setAttribute("contra", contra);
            	request.getRequestDispatcher("inicio.jsp").forward(request, response);
            }
        }
        
        
    }

}
