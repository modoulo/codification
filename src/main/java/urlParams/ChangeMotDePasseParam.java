package urlParams;

import javax.persistence.Entity;

@Entity
public class ChangeMotDePasseParam {

		private Long id;
		private String nouveauMotDePasse;
		private String ancienMotDePasse;
		public ChangeMotDePasseParam() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNouveauMotDePasse() {
			return nouveauMotDePasse;
		}
		public void setNouveauMotDePasse(String motDePasse) {
			this.nouveauMotDePasse = motDePasse;
		}
		public String getAncienMotDePasse() {
			return ancienMotDePasse;
		}
		public void setAncienMotDePasse(String ancienMotDePasse) {
			this.ancienMotDePasse = ancienMotDePasse;
		}
		
}
