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

import com.boly.dao.CouloireRepository;
import com.boly.dao.DepartementRepository;
import com.boly.dao.EtageRepository;
import com.boly.entity.Couloire;
import com.boly.entity.Departement;
import com.boly.entity.Etage;
import com.boly.entity.Sexe;

import urlParams.ParamUrlAddRemoveContenue;
import urlParams.ParamUrlChangeAnneeDetude;
import urlParams.ParamUrlChangeNombreContenue;
import urlParams.paramSetSexeFixer;


@RestController
public class EtageWebService {
	@Autowired
	private EtageRepository etageRepository;
	@Autowired
	private CouloireRepository couloireRepository;
	@Autowired
	private DepartementRepository departementRepository;

	private void createCouloire(Etage etage) {
		for (int i = 1; i <= etage.getNombreCouloire(); i++) {
			couloireRepository.save(new Couloire(i, etage));
		}
	}

	
	@RequestMapping(value="/etages", method=RequestMethod.GET)
	public List<Etage> readAll(){
		return etageRepository.findAll();
	}
	@RequestMapping(value="/etages/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Etage> readById(@PathVariable Long id){
		return etageRepository.findById(id);
	}
	/**
	 * @param params(id, nombre)
	 * @return
	 */
	@RequestMapping(value="/etages/nombreCouloire", method=RequestMethod.PUT )
	public boolean changeNombreCouloire(@RequestBody ParamUrlChangeNombreContenue params){
		Long id = params.getId();
		int nombre = params.getNombre();
		if (!etageRepository.findById(id).isPresent()) {
			return false;
		}
		Etage etage = etageRepository.findById(id).get();
		etage.setNombreCouloire(nombre);
		if (etage.getCouloires()==null) {
			etage.setCouloires(new ArrayList<>());
		}
		etage.getCouloires().clear();
		etage = etageRepository.save(etage);
		createCouloire(etage);
		System.out.println("@@@@@ - batiment: "+etage.getId()+" a etait Update");
		return true;
	}
	/**
	 * @param param(id, annee)
	 * @return
	 */
	@RequestMapping(value="/etages/anneeDetudeFixer", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeDetude param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		int annee = param.getAnnee();
		
		if (id==null) {
			return false;
		}
		if (!etageRepository.findById(id).isPresent()) {
			return false;
		}
		Etage etage = etageRepository.findById(id).get();
		etage.setAnneeDetudeFixer(annee);
		etage = etageRepository.save(etage);
		return true;
	}
	/**
	 * @param param(id, sexe)
	 * @return
	 */
	@RequestMapping(value="/etages/sexeFixer", method=RequestMethod.PUT )
	public boolean setSexeFixer(@RequestBody  paramSetSexeFixer param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		System.out.println("Sexe donnee: "+param.getSexe());
		Sexe sexe = param.getSexe();
		if (sexe==null) {
			sexe=Sexe.I;
		}
		if (id==null) {
			return false;
		}
		if (!etageRepository.findById(id).isPresent()) {
			return false;
		}
		Etage etage = etageRepository.findById(id).get();
		System.out.println("Sexe donnee: "+sexe);
		etage.setSexeFixer(sexe);
		System.out.println("Sexe contenue a la fin: "+etage.getSexeFixer());
		etage = etageRepository.save(etage);
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/etages/departementFixer", method=RequestMethod.PUT )
	public @ResponseBody boolean addDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDepartement = param.getIdContenue();
		Long idEtage = param.getIdConteneur();
		System.out.println("c bon 1 idDepartement: "+idDepartement+" idEtage"+idEtage);
		if (idDepartement==null || idEtage==null) {
			return false;
		}
		if (!etageRepository.findById(idEtage).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDepartement).isPresent()) {
			return false;
		}
		System.out.println("c bon 1");
		Etage etage = etageRepository.findById(idEtage).get();
		System.out.println("c bon 2");
		Departement departement = departementRepository.findById(idDepartement).get();
		System.out.println("c bon 3");
		etage.getDepartementsFixer().add(departement);
		etage = etageRepository.save(etage);
		System.out.println("c bon 4");
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/etages/departementFixer", method=RequestMethod.DELETE )
	public boolean removeDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDep = param.getIdContenue();
		Long idBat = param.getIdConteneur();
		if (idDep==null || idBat==null) {
			return false;
		}
		if (!etageRepository.findById(idBat).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDep).isPresent()) {
			return false;
		}
		Etage etage = etageRepository.findById(idBat).get();
		Departement departement = departementRepository.findById(idDep).get();
		boolean result = etage.getDepartementsFixer().remove(departement);
		etage = etageRepository.save(etage);
		return result;
	}
	@RequestMapping(value="/etages/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		etageRepository.deleteById(id);
		return true;
	}
}
