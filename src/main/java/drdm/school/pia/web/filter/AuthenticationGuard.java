package drdm.school.pia.web.filter;

import drdm.school.pia.web.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter class used for access restriction for user
 * Used for security
 * @author Michal Drda
 */
@WebFilter({"/banking/*", "/managing/*", "/logout/*"})
public class AuthenticationGuard implements Filter {

    /**
     * Auth service initialization
     */
    private AuthenticationService authService;

    /**
     * Setter for AuthService
     * @param authService provided authService
     */
    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * A method used for initialization of the web context
     * @param filterConfig provided filter config
     * @throws ServletException in case of error
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(filterConfig.getServletContext());
        AutowireCapableBeanFactory ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

    /**
     * @inheritDoc
     * Used for filtering users to decide if they are or are not allowed to access the page
     * @param request provided request
     * @param response provided response
     * @param chain provided Filter chain
     * @throws IOException in case of IO exception
     * @throws ServletException in case of ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean allowed = authService.isLoggedIn(req.getSession());

        if(allowed) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/");
        }
    }

    /**
     * @inheritDoc
     * Destroy
     */
    @Override
    public void destroy() {

    }
}
