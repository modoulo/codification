package com.boly.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import urlParams.UserRole;

@Entity
public class Admin implements Serializable{

		
		@Id
		@GeneratedValue
		private Long id;
		@NotNull
		private String nom;
		@NotNull
		private String prenom;
		@NotNull
		@Email
		private String email;
		@JsonIgnore
		private String motDePasse;
		private String numtel;
		public Admin() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Admin(String nom, String prenom, Date dateDeNaissance, String lieuDeNaissance, String email,
				String numtel, String numCartEtudiant, Sexe sexe, int anneeDetude) {
			super();
			this.nom = nom;
			this.prenom = prenom;
			this.email = email;
			this.numtel = numtel;
		}

		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}
		@JsonIgnore
		public boolean isMotDePasseNull() {
			return this.motDePasse == null || this.motDePasse == "";
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean verifieMotDePasse(String motDePasse) {
			if (motDePasse==null) {
				return false;
			}
			if (motDePasse.equals(this.motDePasse)) {
				return true;
			}
			return false;
		}
		@JsonIgnore
		public UserRole getIdentiy() {
			return new UserRole(this.id, Role.Admin);
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
		public String getNumtel() {
			return numtel;
		}
		public void setNumtel(String numtel) {
			this.numtel = numtel;
		}
		

	}

