package adroit;

import java.io.Serializable;

public class Idea implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String phrase;

	
	public Idea(String id) {
		
		this.id = id;
	}
	
	
	public Idea(String id, String phrase) {
		
		this.phrase = phrase;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPhrase() {
		return phrase;
	}


	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	
	@Override
	public String toString() {
		return "Idea [id=" + id + ", phrase=" + phrase + "]";
	}
	
	
	
	

}
