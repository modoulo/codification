package com.boly.entity;

import java.io.Serializable; 
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import urlParams.UserRole;

@Entity
public class Etudiant implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String nom;
	@NotNull
	private String prenom;
	@Temporal(TemporalType.DATE)
	private Date dateDeNaissance;
	private String lieuDeNaissance;
	private String numtel;
	private String numCartEtudiant;
	@NotNull
	@Email
	private String email;
	private String motDePasse;
	private String qrcode;
	private boolean inscriptionValider;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Sexe sexe;
	@ManyToOne
	private Departement departement;
	private int anneeDetude;
	@JsonIgnore
	@OneToMany(mappedBy="etudiant")
	private Collection<Codification> codifications;
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Etudiant(String nom, String prenom, Date dateDeNaissance, String lieuDeNaissance, String email,
			String numtel, String numCartEtudiant, Sexe sexe, int anneeDetude) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateDeNaissance = dateDeNaissance;
		this.lieuDeNaissance = lieuDeNaissance;
		this.numtel = numtel;
		this.numCartEtudiant = numCartEtudiant;
		this.sexe = sexe;
		this.anneeDetude = anneeDetude;
	}
	@JsonIgnore
	public boolean isMotDePasseNull() {
		return this.motDePasse == null || this.motDePasse == "";
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonIgnore
	public UserRole getIdentiy() {
		return new UserRole(this.id, Role.Etudiant);
	}
	@JsonIgnore
	public boolean verifieMotDePasse(String motDePasse) {
		if (motDePasse==null) {
			return false;
		}
		if (motDePasse.equals(this.motDePasse)) {
			return true;
		}
		return false;
	}
	
	
	public String generateQRCodeValue() {
		Random r= new Random();
		this.qrcode = (new Long(r.nextLong()).toString());
		return this.qrcode;
	}
	public boolean validerInscription(String codeRetour) {
		System.out.println("codeRetour: "+codeRetour);
		System.out.println("codeQR    : "+this.qrcode);
		if (this.qrcode.equals(codeRetour)) {
			this.inscriptionValider=true;
			return true;
		}
		return false;
	}
	public String getQRCode() {
		return qrcode;
	}
	public void setQRCode(String QRCode) {
		System.out.println("je suis setQRCode: "+QRCode);
		this.qrcode = QRCode;
	}
	public boolean isInscriptionValider() {
		return inscriptionValider;
	}
	public void setInscriptionValider(boolean inscriptionValider) {
		this.inscriptionValider = inscriptionValider;
	}
	public int getAnneeDetude() {
		return anneeDetude;
	}
	public void setAnneeDetude(int anneeDetude) {
		this.anneeDetude = anneeDetude;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		System.out.println("je suis setId: "+id);
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateDeNaissance() {
		return dateDeNaissance;
	}
	public void setDateDeNaissance(Date dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}
	public String getLieuDeNaissance() {
		return lieuDeNaissance;
	}
	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance = lieuDeNaissance;
	}
	public String getNumtel() {
		return numtel;
	}
	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}
	public String getNumCartEtudiant() {
		return numCartEtudiant;
	}
	public void setNumCartEtudiant(String numCartEtudiant) {
		this.numCartEtudiant = numCartEtudiant;
	}
	public Sexe getSexe() {
		return sexe;
	}
	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}
	public Departement getDepartement() {
		return departement;
	}
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	public Collection<Codification> getCodifications() {
		return codifications;
	}
	public void setCodifications(Collection<Codification> codifications) {
		this.codifications = codifications;
	}
	
}
