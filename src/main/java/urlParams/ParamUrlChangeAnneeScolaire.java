package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlChangeAnneeScolaire {
	private Long id;
	private String anneeScolaire;
	public Long getId() {
		return id;
	}
	public void setId(Long idConteneur) {
		id = idConteneur;
	}
	public String getAnneeScolaire() {
		return anneeScolaire;
	}
	public void setAnneeScolaire(String nombre) {
		this.anneeScolaire = nombre;
	}
	public ParamUrlChangeAnneeScolaire() {
		super();
		// TODO Auto-generated constructor stub
	}
}
