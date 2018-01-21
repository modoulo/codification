package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlChangeNombreContenue {
	private Long id;
	private int nombre;
	public Long getId() {
		return id;
	}
	public void setId(Long idConteneur) {
		id = idConteneur;
	}
	public int getNombre() {
		return nombre;
	}
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
	public ParamUrlChangeNombreContenue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParamUrlChangeNombreContenue(Long id, int nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
}
