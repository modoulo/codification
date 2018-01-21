package com.boly.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Chambre implements Serializable{
		@Id @GeneratedValue
	private Long id;
	private int numero;
	@Enumerated(EnumType.STRING)
	private Sexe sexeFixer;
	private int anneeDetudeFixer;
	private int nombreDePosition;
	@OneToMany
	private Collection<Departement> departementsFixer;
	@JsonIgnore
	@ManyToOne
	private Couloire couloire;
	@OneToMany(mappedBy="chambre", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<Position> positions;	
	private Long idConteneur;
	
	
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
		return this.couloire.codifiable(etudiant);
	}
	
	public Chambre() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Chambre(Long id, int numero, Sexe sexeFixer, int anneeDetudeFixer, int nombreDePosition) {
		super();
		this.id = id;
		this.numero = numero;
		this.sexeFixer = sexeFixer;
		this.anneeDetudeFixer = anneeDetudeFixer;
		this.nombreDePosition = nombreDePosition;
		initialiserListePosition();
		this.nombreDePosition=this.getPositions().size();
	}
	public Chambre(int numero, Couloire couloire) {
		super();
		this.numero = numero;
		this.couloire = couloire;
		this.idConteneur = couloire.getId();
		initialiserListePosition();
		this.nombreDePosition=this.getPositions().size();
	}
	private void initialiserListePosition() {
		this.setPositions(new ArrayList<>());
		for (int i = 1; i <= 4; i++) {
			this.positions.add(new Position(i, this));
		}
	}
	public Long getIdConteneur() {
		return idConteneur;
	}
	public void setIdConteneur(Long idConteneur) {
		this.idConteneur = idConteneur;
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
	public int getNombreDePosition() {
		return nombreDePosition;
	}
	public void setNombreDePosition(int nombreDePosition) {
		this.nombreDePosition = nombreDePosition;
	}
	public Collection<Departement> getDepartementsFixer() {
		return departementsFixer;
	}
	public void setDepartementsFixer(Collection<Departement> departementsFixer) {
		this.departementsFixer = departementsFixer;
	}
	public Couloire getCouloire() {
		return couloire;
	}
	public void setCouloire(Couloire couloire) {
		this.couloire = couloire;
		this.idConteneur = couloire.getId();
	}
	public Collection<Position> getPositions() {
		return positions;
	}
	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
	
}
