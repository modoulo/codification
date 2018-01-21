package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlChangeAnneeDetude {
	private Long id;
	private int annee;
	public Long getId() {
		return id;
	}
	public void setId(Long idConteneur) {
		id = idConteneur;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int nombre) {
		this.annee = nombre;
	}
	public ParamUrlChangeAnneeDetude() {
		super();
		// TODO Auto-generated constructor stub
	}
}
