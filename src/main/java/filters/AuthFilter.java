package filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/partidas.jsp", "/infoPartida.jsp", "/jugar.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No necesitamos inicializar nada por ahora
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Evitar crear sesión nueva por accidente
        HttpSession session = req.getSession(false);

        // Comprueba si el usuario está logueado usando el atributo que guardas en CuentaServlet
        boolean loggedIn = session != null && session.getAttribute("correo") != null;

        if (!loggedIn) {
            // Redirige a la página de inicio de sesión
            String loginPath = req.getContextPath() + "/inicio.jsp";
            res.sendRedirect(loginPath);
            return;
        }

        // Si está logueado, continuar la cadena
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Liberar recursos si fuera necesario
    }
}
