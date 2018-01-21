 package com.boly.web;


import java.io.File;
import java.util.List; 
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boly.dao.DepartementRepository;
import com.boly.dao.EtudiantRepository;
import com.boly.entity.Departement;
import com.boly.entity.Etudiant;
import com.boly.entity.Sexe;
import com.jayway.jsonpath.Option;

import net.glxn.qrgen.javase.QRCode;
import urlParams.ChangeMotDePasseParam;
import urlParams.ParamUrlEtudiantUpdateDepartement;



@RestController
public class EtudiantWebService {
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
    public JavaMailSender emailSender;
	
	/**
	 * @param etudiant(nom, prenom, sexe, email,[dateDeNaissance, lieuDeNaissance, numTel, numCartEtudiant, anneeDetude])
	 * @return Long(Batiment.getId())
	 */
	@RequestMapping(value="/etudiants", method=RequestMethod.POST)
	public Long create(@RequestBody Etudiant etudiant){
		etudiant.setId(null);
		if (etudiant.getNom()=="" || etudiant.getNom()==null) {
			System.out.println("le nom est: "+etudiant.getNom());
			return -1L;
		}
		if (etudiant.getEmail()=="" || etudiant.getEmail()==null) {
			System.out.println("l'email est: "+etudiant.getEmail());
			return -1L;
		}
		if (etudiant.isMotDePasseNull()) {
			System.out.println("le mot de passe est null: ");
			return -1L;
		}
		if (etudiant.getPrenom()=="" || etudiant.getPrenom()==null) {
			System.out.println("le prenom est: "+etudiant.getPrenom());
			return -1L;
		}
		if (etudiant.getSexe()==null || etudiant.getSexe()==Sexe.I) {
			System.out.println("le sexe est: "+etudiant.getSexe());
			return -1L;
		}
		if (etudiantRepository.findTopByEmail(etudiant.getEmail()).isPresent()) {
			System.out.println("il existe un compte avec le mail : "+etudiant.getEmail());
			return -1L;
		}
		etudiant.generateQRCodeValue();
		try {
			etudiant = etudiantRepository.save(etudiant);			
		} catch (Exception e) {
			System.out.println("erreur de sauvegarde");
			System.out.println(e.getMessage());
			return -1L;
		}
		return etudiant.getId();
	}
	@RequestMapping(value="/etudiants", method=RequestMethod.GET)
	public List<Etudiant> readAll(){
		return etudiantRepository.findAll();
	}
	@RequestMapping(value="/etudiants/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Etudiant> readById(@PathVariable Long id){
		return etudiantRepository.findById(id);
	}
	 /**
		 * @param etudiant([nom, prenom, sexe, email,dateDeNaissance, lieuDeNaissance, numTel, numCartEtudiant, anneeDetude])
		 * @return Long(Batiment.getId())
		 */
		@RequestMapping(value="/etudiants", method=RequestMethod.PUT)
		public boolean changeInfo(@RequestBody Etudiant etud){
			if (etud.getId()==null) {
				return false;
			}
			if (!etudiantRepository.findById(etud.getId()).isPresent()) {
				return false;
			}
			Etudiant etudiant = etudiantRepository.findById(etud.getId()).get();
			if (etud.getNom()!="" && etud.getNom()!=null) {
				etudiant.setNom(etud.getNom());
			}
			if (etud.getPrenom()!="" && etud.getPrenom()!=null) {
				etudiant.setPrenom(etud.getPrenom());
			}
			if (etud.getSexe()!=null && etud.getSexe()!=Sexe.I) {
				etudiant.setSexe(etud.getSexe());
			}
			if (etud.getEmail()!="" && etud.getEmail()!=null) {
				etudiant.setEmail(etud.getEmail());
			}
			if (etud.getDateDeNaissance()!=null) {
				etudiant.setDateDeNaissance(etud.getDateDeNaissance());
			}
			if (etud.getLieuDeNaissance()!="" && etud.getLieuDeNaissance()!=null) {
				etudiant.setLieuDeNaissance(etud.getLieuDeNaissance());
			}
			if (etud.getNumtel()!="" && etud.getNumtel()!=null) {
				etudiant.setNumtel(etud.getNumtel());
			}
			if (etud.getNumCartEtudiant()!="" && etud.getNumCartEtudiant()!=null) {
				etudiant.setNumCartEtudiant(etud.getNumCartEtudiant());
			}
			if (etud.getAnneeDetude()!=0) {
				etudiant.setAnneeDetude(etud.getAnneeDetude());
			}
			if (etudiant.getQRCode()==null) {
				etudiant.generateQRCodeValue();
			}
			try {
				etudiant = etudiantRepository.save(etudiant);			
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			return true;
		}
		
		 /**
		 * @param etudiant([nom, prenom, sexe, email,dateDeNaissance, lieuDeNaissance, numTel, numCartEtudiant, anneeDetude])
		 * @return Long(Batiment.getId())
		 */
		@RequestMapping(value="/etudiants/validerInscription", method=RequestMethod.PUT)
		public boolean validerInscription(@RequestBody Etudiant param){
			System.out.println("QRCode: "+param.getQRCode());
			System.out.println("ID: "+param.getId());
			if (param.getId()==null || param.getQRCode()==null || param.getQRCode()=="") {
				System.out.println("erreur: parametre incorrecte");
				return false;
			}
			if (!etudiantRepository.findById(param.getId()).isPresent()) {
				System.out.println("l'etudiant est inconnu");
				return false;
			}
			Etudiant etudiant = etudiantRepository.findById(param.getId()).get();
			boolean result = etudiant.validerInscription(param.getQRCode());
			try {
				etudiant = etudiantRepository.save(etudiant);			
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			return result;
		}
	

		/**
		 * @param param(id, nouveauMotDePasse, ancienMotDePasset)
		 * @return
		 */
		@RequestMapping(value="/etudiants/changerMotDePasse", method=RequestMethod.PUT )
		public @ResponseBody boolean changeMotDePasse(@RequestBody ChangeMotDePasseParam param){
			if (param.getId()==null) {
				System.out.println("param.id="+param.getId());
				return false;
			}
			if (param.getAncienMotDePasse()==null || param.getAncienMotDePasse()=="" ) {
				System.out.println("param.ancienMotDePasse="+param.getAncienMotDePasse());
				return false;
			}
			if (param.getNouveauMotDePasse()==null || param.getNouveauMotDePasse()=="" ) {
				System.out.println("param.nouveauMotDePasse="+param.getNouveauMotDePasse());
				return false;
			}
			Optional<Etudiant> etudiant = etudiantRepository.findById(param.getId());
			if (!etudiant.isPresent()) {
				System.out.println("L'etudiant est inconnu dans la basse de donnees");
				return false;
			}
			if (etudiant.get().verifieMotDePasse(param.getAncienMotDePasse())) {
				etudiant.get().setMotDePasse(param.getNouveauMotDePasse());
				etudiantRepository.save(etudiant.get());
				System.out.println("mot de passe de l'etudiant "+etudiant.get().getEmail()+" est update");
				return true;
			}else {
				System.out.println("ancien mot de passe incorrecte");
				return false;
			}
		}
		

		/**
		 * @param param(id,  idDepartement)
		 * @return
		 */
		@RequestMapping(value="/etudiants/departement", method=RequestMethod.PUT )
		public @ResponseBody boolean addDepartementFixer(@RequestBody ParamUrlEtudiantUpdateDepartement param){
			Long idDep = param.getIdDepartement();
			Long idEtudiant = param.getId();
			if (idDep==null || idEtudiant==null) {
				return false;
			}
			if (!etudiantRepository.findById(idEtudiant).isPresent()) {
				return false;
			}
			if (!departementRepository.findById(idDep).isPresent()) {
				return false;
			}
			Etudiant etudiant = etudiantRepository.findById(idEtudiant).get();
			Departement departement = departementRepository.findById(idDep).get();
			etudiant.setDepartement(departement);
			etudiant = etudiantRepository.save(etudiant);
			return true;
		}

	
	@RequestMapping(value="/etudiants/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		etudiantRepository.deleteById(id);
		return true;
	}

	
	@RequestMapping(value="/etudiants/validerInscription/{id}", method=RequestMethod.GET)
	public boolean getJavaMailSender(@PathVariable("id") Long id) {
		Optional<Etudiant> etudiant = etudiantRepository.findById(id);
		if (!etudiant.isPresent()) {
			System.out.println("etudiant inconnue dans la base de donnee");
			return false;
		}
		if (etudiant.get().getQRCode()==null) {
			System.out.println("Le codeQR de l'etudiant est null");
			return false;
		}
		System.out.println("codeQR de l'etudiant: "+etudiant.get().getEmail()+" est: "+etudiant.get().getQRCode());
		String mailSubject = "Validation de votre inscription sur HMCodification";
		String mailText = "<h1>Validation D'inscription</h1>\n" + 
				"\n Bonjour " +etudiant.get().getPrenom()+" "+etudiant.get().getNom()+"!"+ 
				"<p>Merci d'avoir choisi notre plateforme de codification \n" + 
				"\n" + 
				"<p>Veillez compléter votre inscription en scannant avec votre Smartphone le QRCode suivant <br>"
				+ "ou bien saisisez le code suivant\n" + 
				"\n"+etudiant.get().getQRCode()+"<p>Merci de votre colaboration.";
	    File out = generateQRCodeImage(etudiant.get().getQRCode());
	    sendSimpleMessage(etudiant.get().getEmail(),mailSubject, mailText, out);
	    return true;
	}
	
	public void sendSimpleMessage(
			String to, String subject, String text, File QRCode) {		        
		        emailSender.send(new MimeMessagePreparator() {	  
					@Override
					public void prepare(MimeMessage mimeMessage) throws Exception {
						// TODO Auto-generated method stub
						 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			        	     message.setTo(to);
			        	     message.setSubject(subject);
			        	     message.setText(text+" <img src='cid:myLogo'>", true);
			        	     message.addInline("myLogo", QRCode);
					}
		        	 });
	}
	
	public File generateQRCodeImage(String codeQR){
		return QRCode.from(codeQR).file();
    }


	
}
