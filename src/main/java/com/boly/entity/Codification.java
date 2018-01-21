package com.boly.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Codification implements Serializable{
	@Id @GeneratedValue
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDeReservation;
	private boolean valider;
	@ManyToOne
	private Etudiant etudiant;
	@ManyToOne
	private Position position;
	@ManyToOne
	private CodificationSpec codificationSpec;
	@OneToMany
	private Collection<Codification> propositionDechange;
	public Codification() {
		super();
	}
	public Codification(Long id, Date dateDeReservation, boolean valider, Etudiant etudiant, Position position,
			CodificationSpec codificationSpec) {
		super();
		this.id = id;
		this.dateDeReservation = dateDeReservation;
		this.valider = valider;
		this.etudiant = etudiant;
		this.position = position;
		this.codificationSpec = codificationSpec;
	}
	public Collection<Codification> getPropositionDechange() {
		return propositionDechange;
	}
	public void setPropositionDechange(ArrayList<Codification> propositionDechange) {
		this.propositionDechange = propositionDechange;
	}
	public void addPropositionDechange(Codification propositionDechange) {
		if (this.propositionDechange==null) {
			this.propositionDechange = new ArrayList<>();
		}
		this.propositionDechange.add(propositionDechange);
	}
	public void removePropositionDechange(Codification propositionDechange) {
		if (this.propositionDechange==null) {
			this.propositionDechange = new ArrayList<>();
		}
		this.propositionDechange.add(propositionDechange);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateDeReservation() {
		return dateDeReservation;
	}
	public void setDateDeReservation(Date dateDeReservation) {
		this.dateDeReservation = dateDeReservation;
	}
	public boolean isValider() {
		return valider;
	}
	public void setValider(boolean valider) {
		this.valider = valider;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public CodificationSpec getCodificationSpec() {
		return codificationSpec;
	}
	public void setCodificationSpec(CodificationSpec codificationSpec) {
		this.codificationSpec = codificationSpec;
	}
	
	public boolean isExpired() {
		Date dateDexpiration = new Date(this.getDateDeReservation().getTime() + (1000*60*60*24));
		return new Date().after(dateDexpiration) && !this.valider;
	}
	
	
}
