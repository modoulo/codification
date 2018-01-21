package com.boly.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boly.dao.ChambreRepository;
import com.boly.dao.CouloireRepository;
import com.boly.dao.DepartementRepository;
import com.boly.dao.EtageRepository;
import com.boly.dao.PositionRepository;
import com.boly.entity.Batiment;
import com.boly.entity.Chambre;
import com.boly.entity.Couloire;
import com.boly.entity.Departement;
import com.boly.entity.Etage;
import com.boly.entity.Position;
import com.boly.entity.Sexe;

import urlParams.ParamUrlAddRemoveContenue;
import urlParams.ParamUrlChangeAnneeDetude;
import urlParams.ParamUrlChangeName;
import urlParams.ParamUrlChangeNombreContenue;
import urlParams.paramSetSexeFixer;


@RestController
public class DepartementWebService {
	@Autowired
	private DepartementRepository departementRepository;


	/**
	 * @param departement(nom)
	 * @return Long(Batiment.getId())
	 */
	
	@RequestMapping(value="/departements", method=RequestMethod.POST)
	public Long create(@RequestBody Departement departement){
		departement = departementRepository.save(departement);
		System.out.println("@@@@@ - Nouveau departement inserer: "+departement.getNom());
		return departement.getId();
	}
	@RequestMapping(value="/departements", method=RequestMethod.GET)
	public List<Departement> readAll(){
		return departementRepository.findAll();
	}
	@RequestMapping(value="/departements/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Departement> readById(@PathVariable Long id){
		return departementRepository.findById(id);
	}

	/**
	 * @param param(id, nom)
	 * @return
	 */
	@RequestMapping(value="/departements/name", method=RequestMethod.PUT )
	public boolean updateSpec(@RequestBody  ParamUrlChangeName param){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		System.out.println("nom donnee: "+param.getNom());
		String nom = param.getNom();
		if (id==null) {
			return false;
		}
		if (!departementRepository.findById(id).isPresent()) {
			return false;
		}
		Departement departement = departementRepository.findById(id).get();
		String nom1 = departement.getNom();
		if(nom != "") 
			departement.setNom(nom);
			else return false;
		departement = departementRepository.save(departement);
		System.out.println("@@@@@ - le nom du departement: "+nom1+" a ete changer en: "+departement.getNom());
		return true;
	}
	
	@RequestMapping(value="/departements/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		departementRepository.deleteById(id);
		return true;
	}
}
