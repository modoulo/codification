package urlParams;

import javax.persistence.Entity;

@Entity
public class EchangeCodifParam {

		private Long idCodificationVoulue;
		private Long idCodificationProposer;
		private boolean reponche;
		public EchangeCodifParam() {
			super();
			// TODO Auto-generated constructor stub
		}
		public boolean getReponche() {
			return reponche;
		}
		public void setReponche(boolean reponche) {
			this.reponche = reponche;
		}
		public Long getIdCodificationVoulue() {
			return idCodificationVoulue;
		}
		public void setIdCodificationVoulue(Long idCodification1) {
			this.idCodificationVoulue = idCodification1;
		}
		public Long getIdCodificationProposer() {
			return idCodificationProposer;
		}
		public void setIdCodificationProposer(Long idCodification2) {
			this.idCodificationProposer = idCodification2;
		}
		
		
}
