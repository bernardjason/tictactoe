package bjason.swagger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


@Configuration
public class ServletContextInit implements ServletContextInitializer {
 
  @Override
  public void onStartup(ServletContext sc) throws ServletException {
    sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
    sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    sc.setInitParameter("primefaces.THEME", "bootstrap");
    
 
  }

}
