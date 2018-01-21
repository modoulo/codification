package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlConnection {
	private String email;
	private String motDePasse;
	public ParamUrlConnection() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
}
