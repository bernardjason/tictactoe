package bjason.swagger.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import bjason.swagger.jsf.JsfAuthenticationBean;

@Component
public class JsfFilter implements Filter {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		String redirect = null;
		if (req instanceof HttpServletRequest) {
			HttpServletRequest httpreq = (HttpServletRequest) req;
			String url = httpreq.getRequestURL().toString();
			log.info("JSF filter");

			if (url.contains(".xhtml") == true) {
				redirect = processJSF(req, redirect, httpreq, url);
			}
		}

		if (redirect == null)
			chain.doFilter(req, resp);
		else
			((HttpServletResponse) resp).sendRedirect(redirect);
	}

	private String processJSF(ServletRequest req, String redirect,
			HttpServletRequest httpreq, String url) {
		HttpSession session = ((HttpServletRequest) req).getSession();

		if (session.getAttribute("jsfAuthenticationBean") != null) {
			JsfAuthenticationBean jsfAuthenticationBean = (JsfAuthenticationBean) session
					.getAttribute("jsfAuthenticationBean");

			if (jsfAuthenticationBean.getNickName() == null
					&& url.contains("home.xhtml") == false) {
				redirect = httpreq.getContextPath()
						+ "/home.xhtml?faces-redirect=true";
				log.info("NOT LOGGED ON, BACK TO HOME " + redirect);
			}
		} else if (url.contains("home.xhtml") == false) {
			redirect = httpreq.getContextPath()
					+ "/home.xhtml?faces-redirect=true";
			log.info("NOT LOGGED ON, BACK TO HOME " + redirect);
		}
		return redirect;
	}

	@Override
	public void destroy() {

	}
}
