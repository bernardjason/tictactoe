package bjason.swagger.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bjason.swagger.controllers.OauthController;

public class CheckBearer extends HandlerInterceptorAdapter {
	public Log log = LogFactory.getLog(this.getClass());

	OauthController oc;
	
	public CheckBearer(OauthController oc) {
		this.oc = oc;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String authorization=request.getHeader("authorization");

    	
    	log.info("****** check "+authorization);
    	if ( authorization == null)     		{
    		return bad(authorization,response);
    	}

    	String[] checkAccess_token= authorization.split("Bearer ");
    	if ( checkAccess_token.length != 2 ) {
    		return bad(authorization,response);
    	}
    	
    	try{
    		oc.get(checkAccess_token[1]);
    	} catch (RuntimeException re) {
    		return bad(authorization+" "+re.getMessage(),response);
    	}
    	
    	return true;
	}
	private boolean  bad(String authorization, HttpServletResponse response) {
		log.warn("****** About to return unauthorised *********"+HttpStatus.UNAUTHORIZED.value());
    	response.setStatus(HttpStatus.UNAUTHORIZED.value());
		try {
			response.getOutputStream().println("UNAUTHORIZED something happpend !!!"+authorization);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			log.error(e);
		}
    	return false;
	}

} 


