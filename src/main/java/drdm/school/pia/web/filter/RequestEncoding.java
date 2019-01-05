package drdm.school.pia.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * {@inheritDoc}
 * Filter setting character encoding for every request
 * @author Michal Drda
 */
@WebFilter(filterName = "requestEncoding",
        urlPatterns = {"/*"})
public class RequestEncoding implements Filter {

    /**
     * character encoding attribute
     */
    private static final String ENC = "UTF-8";

    /**
     * {@inheritDoc}
     * Initializes the Filter to be used
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * {@inheritDoc}
     * Used to set encoding to the request or response
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (req != null)
            req.setCharacterEncoding(ENC);
        if (resp != null)
            resp.setCharacterEncoding(ENC);
        chain.doFilter(req, resp);
    }

    /**
     * {@inheritDoc}
     * Has to be implemented, but not used atm
     */
    @Override
    public void destroy() {

    }
}
