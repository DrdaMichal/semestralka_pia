package drdm.school.pia.web.servlet.spring;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Abstract servlet class used for Spring wiring
 * @author Michal Drda
 */
public abstract class AbstractServlet extends HttpServlet {

    /**
     * Spring wiring bean factory
     */
    protected AutowireCapableBeanFactory ctx;

    /**
     * Init of spring servlets config
     * @param config config
     * @throws ServletException exception in case of error
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

}
