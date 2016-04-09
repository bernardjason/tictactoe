package bjason.swagger.jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "jsfGameBean", eager = true)
@SessionScoped
public class JsfGameBean implements Serializable {

   private static final long serialVersionUID = 1L;

    private String gameName;
      
   private String href;
  
   private String gameHref;
   
   private String playerNumber;
   
   public String getGameName() {
      return gameName;
   }
   public void setGameName(String gameName) {
      this.gameName = gameName;
   }
public String registerAction() {
	return "match";
	//return "match";
}
public String joinAction() {
	return "match";
	//return "match";
}

public String okToStartMatchAction() {
	return "match";
}

public String getHref() {
	return href;
}
public void setHref(String href) {
	this.href = href;
}
public String getGameHref() {
	return gameHref;
}
public void setGameHref(String gameHref) {
	this.gameHref = gameHref;
}
public String getPlayerNumber() {
	return playerNumber;
}
public void setPlayerNumber(String playerNumber) {
	if ( playerNumber.equalsIgnoreCase("0") )this.playerNumber = "X";
	else if ( playerNumber.equalsIgnoreCase("1") ) this.playerNumber = "O";
	else this.playerNumber = playerNumber;
}



 


}