package bjason.swagger.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "jsfAuthenticationBean", eager = true)
@SessionScoped
public class JsfAuthenticationBean implements Serializable {

   private static final long serialVersionUID = 1L;

   private String nickName;
   
   //@ManagedProperty("#{param.access_token}")
   private String access_token="NOTSET!!!!";

   public void setBearer() {
	    String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("access_token");
	   this.setAccess_token(value);
   }

public String getNickName() {
	return nickName;
}



public void setNickName(String nickName) {
	this.nickName = nickName;
}

public String getAccess_token() {
	return access_token;
}

/*
 * check if null as page redirect caused url to change, so access_token no longer set.
 */
public void setAccess_token(String access_token) {
	if (access_token != null ) 	this.access_token = access_token;
}
public void clearAccess_token() {
	access_token=null;
}

public String registerAction() throws IOException {
	
	String visitOauth = "/swagger/authorization/authorization_server.html?"+
	           "response_type=token"+
				"&redirect_uri="+URLEncoder.encode("/swagger/creategame.xhtml")+
	            "&realm=tictactoe"+
				"&client_id=tictactoe-id"+
	            "&scope=play%3Aall"+
				"&state=tictactoe_auth";
	
	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    externalContext.redirect(visitOauth);
    
	return  "creategame";
}

 

public String logout() {
	  ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.invalidateSession();
	   
	    System.out.println("LOGOUT");
     return "home?faces-redirect=true";
}

}