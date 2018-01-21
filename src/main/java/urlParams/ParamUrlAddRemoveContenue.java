package urlParams;

import javax.persistence.Entity;

@Entity
public class ParamUrlAddRemoveContenue {

		private Long idContenue;
		private Long idConteneur;
		public ParamUrlAddRemoveContenue() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Long getIdContenue() {
			return idContenue;
		}
		public void setIdContenue(Long idDepartement) {
			this.idContenue = idDepartement;
		}
		public Long getIdConteneur() {
			return idConteneur;
		}
		public void setIdConteneur(Long idBatiment) {
			idConteneur = idBatiment;
		}
		
}
