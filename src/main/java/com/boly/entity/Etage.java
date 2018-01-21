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
public class Etage implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private int numero;
	private int nombreCouloire;
	@Enumerated(EnumType.STRING)
	private Sexe sexeFixer;
	private int anneeDetudeFixer;
	@OneToMany
	private Collection<Departement> departementsFixer;
	@JsonIgnore
	@ManyToOne
	private Batiment batiment;
	private Long idConteneur;
	@OneToMany(mappedBy="etage",cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<Couloire> couloires;
	
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
		return this.batiment.codifiable(etudiant);
	}
	
	public Etage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Etage(int numero, Sexe sexeFixer, int anneeDetudeFixer, Batiment batiment) {
		super();
		this.numero = numero;
		this.sexeFixer = sexeFixer;
		this.anneeDetudeFixer = anneeDetudeFixer;
		this.batiment = batiment;
		this.idConteneur=batiment.getId();
	}
	public int getNombreCouloire() {
		return nombreCouloire;
	}
	public void setNombreCouloire(int nombreCouloire) {
		this.nombreCouloire = nombreCouloire;
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
	public int getAnneeDetudeFixer() {
		return anneeDetudeFixer;
	}
	public void setAnneeDetudeFixer(int anneeDetudeFixer) {
		this.anneeDetudeFixer = anneeDetudeFixer;
	}
	public Collection<Departement> getDepartementsFixer() {
		return departementsFixer;
	}
	public void setDepartementsFixer(Collection<Departement> departementsFixer) {
		this.departementsFixer = departementsFixer;
	}
	public Batiment getBatiment() {
		return batiment;
	}
	public void setBatiment(Batiment batiment) {
		this.batiment = batiment;
		this.idConteneur=batiment.getId();
	}
	public Long getConteneurId() {
		return idConteneur;
	}
	public void setConteneurId(Long batimentId) {
		this.idConteneur = batimentId;
	}
	public Collection<Couloire> getCouloires() {
		return couloires;
	}
	public void setCouloires(Collection<Couloire> couloires) {
		this.couloires = couloires;
	}
	
}
