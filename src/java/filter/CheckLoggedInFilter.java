package filter;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsf_managed_bean.UserBean;

/**
 *
 * @author gdm1
 */
@WebFilter(filterName = "checkLoggedIn", urlPatterns =
{
    "/faces/*"
})
public class CheckLoggedInFilter implements Filter
{

    private FilterConfig filterConfig = null;
    
    @Inject //the managed bean
    UserBean user;

    public CheckLoggedInFilter()
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        String loginURI = req.getContextPath() + "/faces/index.xhtml";

        boolean loginRequest = req.getRequestURI().equals(loginURI);

        if (loginRequest || user.getMember() != null || user.getLibrarian() != null)
        {
            chain.doFilter(request, response);
        }
        else
        {
            ((HttpServletResponse) response).sendRedirect(loginURI);
        }
    }

    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy()
    {
        filterConfig = null;
    }

    @Override
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    @Override
    public String toString()
    {
        if (filterConfig == null)
        {
            return ("CheckLoggedInFilter()");
        }
        StringBuffer sb = new StringBuffer("CheckLoggedInFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
}
