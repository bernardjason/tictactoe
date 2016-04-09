package bjason.swagger.entity;

import java.util.ArrayList;

public class Players {

	ArrayList<String> players = new ArrayList<String>();

	public ArrayList<String> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<String> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "Players [players=" + players + "]";
	}
	
	
}
