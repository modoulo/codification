package com.boly.entity;

import java.io.Serializable; 
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Couloire implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private int numero;
	@Enumerated(EnumType.STRING)
	private Sexe sexeFixer;
	private int nombreChambre;
	@OneToMany
	private Collection<Departement> departementsFixer;
	private int anneeDetudeFixer;
	@JsonIgnore
	@ManyToOne
	private Etage etage;
	private Long idConteneur;
	@OneToMany(mappedBy="couloire", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<Chambre> chambres;
	
	public boolean codifiable(Etudiant etudiant) {
		if ((etudiant.getSexe()!=this.sexeFixer) && (this.sexeFixer==Sexe.F || this.sexeFixer==Sexe.M)) {
			return false;
		}
		if (etudiant.getAnneeDetude()!=this.anneeDetudeFixer && this.anneeDetudeFixer!=0) {
			return false;
		}
		if (!this.departementsFixer.isEmpty() && !this.departementsFixer.contains(etudiant.getDepartement())) {
			return false;
		}
		return this.etage.codifiable(etudiant);
	}
	
	public Couloire() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Couloire(int numero, Sexe sexeFixer, int anneeDetudeFixer, Etage etage) {
		super();
		this.numero = numero;
		this.sexeFixer = sexeFixer;
		this.anneeDetudeFixer = anneeDetudeFixer;
		this.etage = etage;
		this.idConteneur = etage.getId();
	}
	public Couloire(int numero, Etage etage) {
		super();
		this.numero = numero;
		this.etage = etage;
		this.idConteneur = etage.getId();
	}
	public int getNombreChambre() {
		return nombreChambre;
	}
	public void setNombreChambre(int nombreChambre) {
		this.nombreChambre = nombreChambre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Sexe getSexeFixer() {
		return sexeFixer;
	}
	public void setSexeFixer(Sexe sexeFixer) {
		this.sexeFixer = sexeFixer;
	}
	public Collection<Departement> getDepartementsFixer() {
		return departementsFixer;
	}
	public void setDepartementsFixer(Collection<Departement> departementsFixer) {
		this.departementsFixer = departementsFixer;
	}
	public int getAnneeDetudeFixer() {
		return anneeDetudeFixer;
	}
	public void setAnneeDetudeFixer(int anneeDetudeFixer) {
		this.anneeDetudeFixer = anneeDetudeFixer;
	}
	public Etage getEtage() {
		return etage;
	}
	public void setEtage(Etage etage) {
		this.etage = etage;
		this.idConteneur = etage.getId();
	}
	public Long getIdConteneur() {
		return idConteneur;
	}
	public void setIdConteneur(Long idParent) {
		this.idConteneur = idParent;
		this.idConteneur = etage.getId();
	}
	public Collection<Chambre> getChambres() {
		return chambres;
	}
	public void setChambres(Collection<Chambre> chambres) {
		this.chambres = chambres;
	}
	
}
