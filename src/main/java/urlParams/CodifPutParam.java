package urlParams;

import javax.persistence.Entity;

@Entity
public class CodifPutParam {

		private Long id;
		private Long idEtudiant;
		private Long idPosition;
		public CodifPutParam() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getIdEtudiant() {
			return idEtudiant;
		}
		public void setIdEtudiant(Long idEtudiant) {
			this.idEtudiant = idEtudiant;
		}
		public Long getIdPosition() {
			return idPosition;
		}
		public void setIdPosition(Long idPosition) {
			this.idPosition = idPosition;
		}
		
		
}
