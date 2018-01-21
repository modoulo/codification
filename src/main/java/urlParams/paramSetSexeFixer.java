package urlParams;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.boly.entity.Sexe;

@Entity
public class paramSetSexeFixer {
	private Long id;
	@Enumerated(EnumType.STRING)
	private Sexe sexe;
	public paramSetSexeFixer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sexe getSexe() {
		return sexe;
	}
	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}
	
}
