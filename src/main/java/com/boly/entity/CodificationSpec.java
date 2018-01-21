package com.boly.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CodificationSpec implements Serializable{
	@Id @GeneratedValue
	private Long id;
	private String anneeScolaire;
	@Temporal(TemporalType.DATE)
	private Date dateDeDebut;
	@Temporal(TemporalType.DATE)
	private Date dateDeFin;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDeCreation;
	@JsonIgnore
	@OneToMany(mappedBy="codificationSpec")
	Collection<Codification> codifications;
	public CodificationSpec() {
		super();
	}
	public CodificationSpec(Long id, String anneeScolaire, Date dateDeDebut, Date dateDeFin) {
		super();
		this.id = id;
		this.anneeScolaire = anneeScolaire;
		this.dateDeDebut = dateDeDebut;
		this.dateDeFin = dateDeFin;
	}
	public Date getDateDeCreation() {
		return dateDeCreation;
	}
	public void setDateDeCreation(Date dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnneeScolaire() {
		return anneeScolaire;
	}
	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}
	public Date getDateDeDebut() {
		return dateDeDebut;
	}
	public void setDateDeDebut(Date dateDeDebut) {
		this.dateDeDebut = dateDeDebut;
	}
	public Date getDateDeFin() {
		return dateDeFin;
	}
	public void setDateDeFin(Date dateDeFin) {
		this.dateDeFin = dateDeFin;
	}
	public Collection<Codification> getCodifications() {
		return codifications;
	}
	public void setCodifications(Collection<Codification> codifications) {
		this.codifications = codifications;
	}
	
	
}
