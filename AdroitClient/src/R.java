package adroit;

import java.io.Serializable;

public class R implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String password;
	String option;
	Idea idea;

	public R() {

	}

	public R(String username, String password, String option, Idea idea) {

		this.username = username;
		this.password = password;
		this.option = option;
		this.idea = idea;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}
	
	@Override
	public String toString() {
		return "R [username=" + username + ", password=" + password + ", option=" + option + ", idea=" + idea + "]";
	}
	
	

}
