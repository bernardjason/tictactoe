package bjason.swagger;

//import javax.faces.webapp.FacesServlet;

import java.util.Enumeration;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import bjason.swagger.controllers.OauthController;
import bjason.swagger.interceptor.CheckBearer;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("bjason.swagger")
public class HelloworldApplication  extends SpringBootServletInitializer {	

	public Log log = LogFactory.getLog(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}
	
	@Autowired 
	OauthController oc;
	
	@Bean
    public MappedInterceptor interceptor() {
        return new MappedInterceptor( new String[]{"/**/tictactoe/**"},null,new CheckBearer(oc));
    }
	
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
        
        servletRegistrationBean.setName("FacesServlet!!!!!!!!!!!!!!!!!!!!");
		return servletRegistrationBean;
    }
  
/*
     @Bean
 
    public ViewResolver jspViewResolver() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
*/
 
	
    @Override
    protected SpringApplicationBuilder configure
                  (SpringApplicationBuilder application) {
      return application.sources(
                  new Class[] { HelloworldApplication.class, ServletContextInit.class});
    }


    @Bean
    public Filter loggingFilter(){
    	
    	
        AbstractRequestLoggingFilter f = new AbstractRequestLoggingFilter() {

            @Override
            protected void beforeRequest(HttpServletRequest request, String message) {
            	
            	StringBuilder total = new StringBuilder();
            	String url = ((HttpServletRequest) request).getRequestURL()
        				.toString();
        		String queryString = ((HttpServletRequest) request)
        				.getQueryString();
        		total.append("Address:" + url + " " + queryString + "\n");

        		for (Enumeration<String> h = ((HttpServletRequest) request)
        				.getHeaderNames(); h.hasMoreElements();) {
        			String headerName = h.nextElement();
        			boolean first = true;
        			for (Enumeration<String> each = ((HttpServletRequest) request)
        					.getHeaders(headerName); each.hasMoreElements();) {
        				if (first)
        					first = false;
        				else
        					total.append(",");
        				total.append("Header:" + headerName + " "
        						+ each.nextElement() + "\n");
        			}
        		}
        	
                log.info(message+total.toString());
            }

            @Override
            protected void afterRequest(HttpServletRequest request, String message) {
            	
            	
            	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes()).getResponse();
            	
            	StringBuilder total = new StringBuilder();
            	total.append("HTTP ResponseCode:" + response.getStatus() + "\n");

        		for (String headerName : response.getHeaderNames()) {
        			boolean first = true;
        			for (String value : response.getHeaders(headerName)) {
        				if (first)
        					first = false;
        				else
        					total.append(",");
        				total.append("Headers:" + headerName + " " + value + "\n");
        			}
        		}
        		
            	log.info(message+total.toString());
            }
        };
        f.setIncludeClientInfo(true);
        f.setIncludePayload(true);
        f.setIncludeQueryString(true);
        
        f.setBeforeMessagePrefix("[");
        f.setAfterMessagePrefix("[");
        f.setAfterMessageSuffix("]");
        return f;
    }
  
   
}
