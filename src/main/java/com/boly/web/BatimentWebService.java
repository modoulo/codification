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

import com.boly.dao.BatimentRepository;
import com.boly.dao.DepartementRepository;
import com.boly.dao.EtageRepository;
import com.boly.entity.Batiment;
import com.boly.entity.Departement;
import com.boly.entity.Etage;
import com.boly.entity.Sexe;

import urlParams.ParamUrlAddRemoveContenue;
import urlParams.ParamUrlChangeAnneeDetude;
import urlParams.ParamUrlChangeName;
import urlParams.ParamUrlChangeNombreContenue;
import urlParams.paramSetSexeFixer;

@RestController
public class BatimentWebService {
	@Autowired
	private BatimentRepository batimentRepository;
	@Autowired
	private EtageRepository etageRepository;
	@Autowired
	private DepartementRepository departementRepository;
	
	private void createEtages(Batiment batiment) {
		for (int i = 1; i <= batiment.getNombreEtage(); i++) {
			etageRepository.save(new Etage(i, null, 0, batiment));
		}
	}

	/**
	 * @param batiment(nom, nombreEtage, sexeFixer)
	 * @return Long(Batiment.getId())
	 */
	
	@RequestMapping(value="/batiments", method=RequestMethod.POST)
	public Long create(@RequestBody Batiment batiment){
		batiment = batimentRepository.save(batiment);
		createEtages(batiment);
		System.out.println("@@@@@ - Nouveau batiment inserer: "+batiment.getNom());
		return batiment.getId();
	}
	@RequestMapping(value="/batiments", method=RequestMethod.GET)
	public List<Batiment> readAll(){
		return batimentRepository.findAll();
	}
	@RequestMapping(value="/batiments/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Batiment> readById(@PathVariable Long id){
		return batimentRepository.findById(id);
	}
	/**
	 * @param params(id, nombre)
	 * @return 
	 */
	@RequestMapping(value="/batiments/nombreEtages", method=RequestMethod.PUT )
	public boolean changeNombreEtages(@RequestBody ParamUrlChangeNombreContenue params){
		Long id = params.getId();
		int nombre = params.getNombre();
		if (!batimentRepository.findById(id).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(id).get();
		batiment.setNombreEtage(nombre);
		if (batiment.getEtages()==null) {
			batiment.setEtages(new ArrayList<>());
		}
		batiment.getEtages().clear();
		batiment = batimentRepository.save(batiment);
		createEtages(batiment);
		System.out.println("@@@@@ - batiment: "+batiment.getId()+" a etait Update");
		return true;
	}
	/**
	 * @param param(id, nom)
	 * @return
	 */
	@RequestMapping(value="/batiments/name", method=RequestMethod.PUT )
	public boolean updateSpec(@RequestBody  ParamUrlChangeName param){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		System.out.println("nom donnee: "+param.getNom());
		String nom = param.getNom();
		if (id==null) {
			return false;
		}
		if (!batimentRepository.findById(id).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(id).get();
		String nom1 = batiment.getNom();
		if(nom != "") 
			batiment.setNom(nom);
			else return false;
		batiment = batimentRepository.save(batiment);
		System.out.println("@@@@@ - le nom du batiment: "+nom1+" a ete changer en: "+batiment.getNom());
		return true;
	}
	/**
	 * @param param(id, sexe)
	 * @return
	 */
	@RequestMapping(value="/batiments/sexeFixer", method=RequestMethod.PUT )
	public boolean setSexeFixer(@RequestBody  paramSetSexeFixer param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		Sexe sexe = param.getSexe();
		if (sexe==null) {
			sexe=Sexe.I;
		}
		if (id==null) {
			return false;
		}
		if (!batimentRepository.findById(id).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(id).get();
		batiment.setSexeFixer(sexe);
		batiment = batimentRepository.save(batiment);
		return true;
	}
	/**
	 * @param param(id, nombre)
	 * @return
	 */
	@RequestMapping(value="/batiments/anneeDetudeFixer", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeDetude param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		int annee = param.getAnnee();
		
		if (id==null) {
			return false;
		}
		if (!batimentRepository.findById(id).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(id).get();
		batiment.setAnneeDetudeFixer(annee);
		batiment = batimentRepository.save(batiment);
		return true;
	}
	/**
	 * @param param(idContenue,  idConteneur)
	 * @return
	 */
	@RequestMapping(value="/batiments/departementFixer", method=RequestMethod.PUT )
	public @ResponseBody boolean addDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDep = param.getIdContenue();
		Long idBat = param.getIdConteneur();
		System.out.println("idConteneur: "+param.getIdConteneur()+" iDcontenue: "+param.getIdContenue());
		if (idDep==null || idBat==null) {
			return false;
		}
		if (!batimentRepository.findById(idBat).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDep).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(idBat).get();
		Departement departement = departementRepository.findById(idDep).get();
		batiment.getDepartementsFixer().add(departement);
		batiment = batimentRepository.save(batiment);
		return true;
	}
	/**
	 * @param param(idContenue,  idConteneur)
	 * @return
	 */
	@RequestMapping(value="/batiments/departementFixer", method=RequestMethod.DELETE )
	public boolean removeDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDep = param.getIdContenue();
		Long idBat = param.getIdConteneur();
		if (idDep==null || idBat==null) {
			return false;
		}
		if (!batimentRepository.findById(idBat).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDep).isPresent()) {
			return false;
		}
		Batiment batiment = batimentRepository.findById(idBat).get();
		Departement departement = departementRepository.findById(idDep).get();
		boolean result = batiment.getDepartementsFixer().remove(departement);
		batiment = batimentRepository.save(batiment);
		return result;
	}
	@RequestMapping(value="/batiments/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		batimentRepository.deleteById(id);
		return true;
	}
	
	
	
}

