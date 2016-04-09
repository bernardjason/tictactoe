package bjason.swagger.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.springframework.hateoas.ResourceSupport;

@Entity
public class TicTacToeEntity extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long ticTacToeId;
	private String gameName;
	private ArrayList<String> players = new ArrayList<String>();

	@ApiModelProperty(hidden=true)
	@ManyToOne
	private GameEntity gameEntity;
	
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	
	public GameEntity getGameEntity() {
		return gameEntity;
	}

	public void setGameEntity(GameEntity gameEntity) {
		this.gameEntity = gameEntity;
	}

	public Long getTicTacToeId() {
		return ticTacToeId;
	}

	public void setTicTacToeId(Long ticTacToeId) {
		this.ticTacToeId = ticTacToeId;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<String> players) {
		this.players = players;
	}

	



	
}

