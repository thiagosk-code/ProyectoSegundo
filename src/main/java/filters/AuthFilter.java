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

@WebFilter(urlPatterns = {"/partidas.jsp", "/infoPartida.jsp", "/jugar.jsp", "/PostInicio.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	System.out.println("AuthFilter inicializado en: " + this.getClass().getName());

       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        System.out.println("AuthFilter interceptó: " + req.getMethod() + " " + req.getRequestURI());


        
        HttpSession session = req.getSession(false);

        
        boolean loggedIn = session != null && session.getAttribute("correo") != null;

        if (!loggedIn) {
            
            String loginPath = req.getContextPath() + "/index.html";
            res.sendRedirect(loginPath);
            return;
        }

        // 🙌
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}
