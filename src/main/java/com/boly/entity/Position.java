package com.boly.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Position implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private int numero;
	private int anneeDetudeFixer;
	@JsonIgnore
	@ManyToOne
	private Chambre chambre;
	@OneToMany(mappedBy="position")
	private Collection<Codification> codifications;
	private Long idConteneur;
	
	public boolean codifiable(Etudiant etudiant) {
		System.out.println("dans position");
		if (etudiant.getAnneeDetude()!=this.anneeDetudeFixer && this.anneeDetudeFixer!=0) {
			return false;
		}
		return this.chambre.codifiable(etudiant);
	}
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Position(Long id, int numero, int anneeDetudeFixer, Chambre chambre) {
		super();
		this.id = id;
		this.numero = numero;
		this.anneeDetudeFixer = anneeDetudeFixer;
		this.chambre = chambre;
		this.idConteneur = chambre.getId();
	}
	public Position(int numero, Chambre chambre) {
		super();
		this.numero = numero;
		this.chambre = chambre;
		this.idConteneur = chambre.getId();
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
	public int getAnneeDetudeFixer() {
		return anneeDetudeFixer;
	}
	public void setAnneeDetudeFixer(int anneeDetudeFixer) {
		this.anneeDetudeFixer = anneeDetudeFixer;
	}
	public Chambre getChambre() {
		return chambre;
	}
	public void setChambre(Chambre chambre) {
		this.chambre = chambre;
		this.idConteneur = chambre.getId();
	}
	public Long getIdConteneur() {
		return idConteneur;
	}

	public void setIdConteneur(Long idConteneur) {
		this.idConteneur = idConteneur;
		this.idConteneur = this.chambre.getId();
	}

	public Collection<Codification> getCodifications() {
		Iterator<Codification> iterator = this.codifications.iterator();
		Codification codif=null;
		Collection<Codification> result=new ArrayList<>();
		System.out.println("idConteneur: "+this.idConteneur);
		while (iterator.hasNext()) {
			codif = iterator.next();
			System.out.println("id next: "+codif.getId());
			if (codif.getPosition().getId()==this.id) {
				System.out.println("c bon id next: "+codif.getId());
				codif.setPosition(null);
				result.add(codif);
			}
		}
		return result;
	}
	public void setCodifications(Collection<Codification> codifications) {
		this.codifications = codifications;
	}
	
	
}
 