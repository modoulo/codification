package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlChangeName {
	private Long Id;
	private String nom;
	public ParamUrlChangeName() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
