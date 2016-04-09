package bjason.swagger.jsf;

import javax.faces.context.ExceptionHandler;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


// see http://www.networkworld.com/article/2224081/opensource-subnet/how-to-add-exception-handling-to-jsf-applications.html
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	
	public Log log = LogFactory.getLog(this.getClass());

  private ExceptionHandler wrapped;
 
  
  public CustomExceptionHandler(ExceptionHandler wrapped) {
    this.wrapped = wrapped;
  }
 
  @Override
  public ExceptionHandler getWrapped() {
    return wrapped;
  }

  @Override
  public void handle() throws FacesException {
    Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();
    
    while (iterator.hasNext()) {
      ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
      ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();
 
      Throwable throwable = context.getException();
      
      if ( throwable instanceof javax.faces.application.ViewExpiredException ) {
	      FacesContext fc = FacesContext.getCurrentInstance();
	      
	      try {
	          Flash flash = fc.getExternalContext().getFlash();
	          
	          flash.put("errorDetails", throwable.getMessage());
	          
	          log.warn("the error is put in the flash: " + throwable.getMessage());
	          
	          NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
	          
	          navigationHandler.handleNavigation(fc, null, "home?faces-redirect=true");
	          
	          fc.renderResponse();
	      } finally {
	          iterator.remove();
	      }
      } else {
    	  super.handle();
      }
      
    }
    
    // Let the parent handle the rest
    getWrapped().handle();
  }
}