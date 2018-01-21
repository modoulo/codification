package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlEtudiantUpdateDepartement {

		private Long id;
		private Long idDepartement;
		public ParamUrlEtudiantUpdateDepartement() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public void setIdDepartement(Long idDepartement) {
			this.idDepartement = idDepartement;
		}
		public Long getIdDepartement() {
			return idDepartement;
		}
		
}
