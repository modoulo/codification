package com.boly.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import urlParams.UserRole;

public abstract class User {

	@NotNull
	@Email
	private String email;
	private String motDePasse;

	public User() {
		super();
	}	
	
	public abstract UserRole getIdentiy(String email);
	
	public String getMotDePasse() {
		return motDePasse;
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
}
