package com.boly.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boly.dao.AdminRepository;
import com.boly.dao.EtudiantRepository;
import com.boly.entity.Admin;
import com.boly.entity.Etudiant;
import com.boly.entity.User;

import urlParams.ParamUrlConnection;
import urlParams.UserRole;


@RestController
public class connectionWebService {
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private AdminRepository adminRepository;
	
	/**
	 * @param User(email,  motDePasse)
	 * @return Long
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody UserRole getConnection(@RequestBody ParamUrlConnection param){
		System.out.println("param.email: "+param.getEmail());
		System.out.println("param.motdepasse: "+param.getMotDePasse());	
		if (param.getEmail()==null || param.getEmail()=="") {
			System.out.println("l'email est null");
			return null;
		}
		if (param.getMotDePasse()==null || param.getMotDePasse()=="") {
			System.out.println("le mot de passe est null");
			return null;
		}
		Optional<Etudiant> etudiant = etudiantRepository.findTopByEmail(param.getEmail());
		Optional<Admin> admin = adminRepository.findTopByEmail(param.getEmail());		
		if (etudiant.isPresent()) {
			System.out.println("etudiant.email: "+etudiant.get().getEmail());		
			if (etudiant.get().verifieMotDePasse(param.getMotDePasse())) {
				return etudiant.get().getIdentiy();
			}
			System.out.println("motDePasse incorrect: "+etudiant.get().getEmail());	
			return null;
		}
		if (admin.isPresent()) {
			System.out.println("admin.email: "+admin.get().getEmail());
			if (admin.get().verifieMotDePasse(param.getMotDePasse())) {
				return admin.get().getIdentiy();
			}
		}
		return null;
	}
	
}
