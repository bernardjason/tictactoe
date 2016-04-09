package bjason.swagger.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.hateoas.ResourceSupport;

@Entity
public class GameEntity extends ResourceSupport {
	@Id
	@GeneratedValue
	private Long gameId;
	
	@OneToOne
	TicTacToeEntity ticTacToeEntity;
	
	private Date created;
	private boolean finished;
	private String r0c0;
	private String r0c1;
	private String r0c2;
	
	private String r1c0;
	private String r1c1;
	private String r1c2;
	
	private String r2c0;
	private String r2c1;
	private String r2c2;
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public TicTacToeEntity getTicTacToeEntity() {
		return ticTacToeEntity;
	}
	public void setTicTacToeEntity(TicTacToeEntity ticTacToeEntity) {
		this.ticTacToeEntity = ticTacToeEntity;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public String getR0c0() {
		return r0c0;
	}
	public void setR0c0(String r0c0) {
		this.r0c0 = r0c0;
	}
	public String getR0c1() {
		return r0c1;
	}
	public void setR0c1(String r0c1) {
		this.r0c1 = r0c1;
	}
	public String getR0c2() {
		return r0c2;
	}
	public void setR0c2(String r0c2) {
		this.r0c2 = r0c2;
	}
	public String getR1c0() {
		return r1c0;
	}
	public void setR1c0(String r1c0) {
		this.r1c0 = r1c0;
	}
	public String getR1c1() {
		return r1c1;
	}
	public void setR1c1(String r1c1) {
		this.r1c1 = r1c1;
	}
	public String getR1c2() {
		return r1c2;
	}
	public void setR1c2(String r1c2) {
		this.r1c2 = r1c2;
	}
	public String getR2c0() {
		return r2c0;
	}
	public void setR2c0(String r2c0) {
		this.r2c0 = r2c0;
	}
	public String getR2c1() {
		return r2c1;
	}
	public void setR2c1(String r2c1) {
		this.r2c1 = r2c1;
	}
	public String getR2c2() {
		return r2c2;
	}
	public void setR2c2(String r2c2) {
		this.r2c2 = r2c2;
	}
	@Override
	public String toString() {
		return "GameEntity [gameId=" + gameId + ", ticTacToeEntity="
				+ ticTacToeEntity + ", created=" + created + ", finished="
				+ finished + ", r0c0=" + r0c0 + ", r0c1=" + r0c1 + ", r0c2="
				+ r0c2 + ", r1c0=" + r1c0 + ", r1c1=" + r1c1 + ", r1c2=" + r1c2
				+ ", r2c0=" + r2c0 + ", r2c1=" + r2c1 + ", r2c2=" + r2c2 + "]";
	}

	



	
}

