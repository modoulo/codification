package com.boly.entity;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Batiment implements Serializable {
	@Id @GeneratedValue
	private Long id;
	@Column(nullable=false)
	private String nom;
	@Column(nullable=false)
	private int nombreEtage;
	@Enumerated(EnumType.STRING)
	private Sexe sexeFixer;
	private int anneeDetudeFixer;
	@ManyToMany
	private Collection<Departement> departementsFixer;
	@OneToMany(mappedBy="batiment",cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<Etage> etages;
	
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
		return true;
	}
	
	public Batiment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Batiment(Long id, String nom, int nombreDetage, Sexe sexeFixer, int anneeDetudeFixer) {
		super();
		this.id = id;
		this.nom = nom;
		this.nombreEtage = nombreDetage;
		this.sexeFixer = sexeFixer;
		this.anneeDetudeFixer = anneeDetudeFixer;
	}
	public int getNombreEtage() {
		return nombreEtage;
	}
	public void setNombreEtage(int nombreDetage) {
		this.nombreEtage = nombreDetage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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
	public Collection<Etage> getEtages() {
		return etages;
	}
	public void setEtages(Collection<Etage> etages) {
		this.etages = etages;
	}
	public void addEtages(Etage etage) {
		if(this.etages == null) {
			this.etages = new ArrayList<>();
		}
		this.etages.add(etage);
		etage.setBatiment(this);
	}
	public void removeEtages(Etage etage) {
		this.etages.remove(etage);
	}
	public void removeAllEtages() {
		System.out.println("nombre d'etage avant le update: "+this.etages.size());
		this.etages.clear();
		System.out.println("nombre d'etage apres le update: "+this.etages.size());
	}
	
}
