package ch.hesge.cours634.webservice;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@WebFilter(urlPatterns = "/*")
public class FilterAuth implements Filter {
    Logger logger = Logger.getLogger(FilterAuth.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse rep = (HttpServletResponse)servletResponse;
        String user=req.getHeader("USER");

        //trace user
        if(user==null){
            logger.info("absence du champ user");
            //send message d'erreur;
            rep.sendError(403);
            return;
        }
        logger.info("nom du user"+user);
        if(!user.equals("user1")&&!user.equals("user2")&&!user.equals("user3")){
            //send message d'erreur
            rep.sendError(403);
            return;
        }
        logger.info("URL:"+req.getRequestURL());
        logger.info("METHODE"+req.getMethod());
        filterChain.doFilter(servletRequest,servletResponse);
    }



    @Override
    public void destroy() {

    }

}
