package filter;

import fileUtils.FileApp;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)){}
            // nope
        else if (FileApp.getListForbiddenID().contains(request.getRemoteAddr())) {
           PrintWriter pw = response.getWriter();
           pw.printf("<h1> Error! Your id %s in the list of banned!</h>", request.getRemoteAddr());
           pw.flush();
        }
        else {
           chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
