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
import com.boly.entity.Chambre;
import com.boly.entity.Couloire;
import com.boly.entity.Departement;
import com.boly.entity.Etage;
import com.boly.entity.Sexe;

import urlParams.ParamUrlAddRemoveContenue;
import urlParams.ParamUrlChangeAnneeDetude;
import urlParams.ParamUrlChangeNombreContenue;
import urlParams.paramSetSexeFixer;


@RestController
public class CouloireWebService {
	@Autowired
	private CouloireRepository couloireRepository;
	@Autowired
	private ChambreRepository chambreRepository;
	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private ChambreWebService chambreWebService;

	private void createChambre(Couloire couloire) {
		for (int i = 1; i <= couloire.getNombreChambre(); i++) {
			Long id = chambreRepository.save(new Chambre(i, couloire)).getId();
			chambreWebService.changeNombreChambre(new ParamUrlChangeNombreContenue(id, 4));
		}
	}

	
	@RequestMapping(value="/couloires", method=RequestMethod.GET)
	public List<Couloire> readAll(){
		return couloireRepository.findAll();
	}
	@RequestMapping(value="/couloires/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Couloire> readById(@PathVariable Long id){
		return couloireRepository.findById(id);
	}
	/**
	 * @param params(id, nombre)
	 * @return
	 */
	@RequestMapping(value="/couloires/nombreChambre", method=RequestMethod.PUT )
	public boolean changeNombreChambre(@RequestBody ParamUrlChangeNombreContenue params){
		Long id = params.getId();
		int nombre = params.getNombre();
		if (!couloireRepository.findById(id).isPresent()) {
			return false;
		}
		Couloire couloire = couloireRepository.findById(id).get();
		couloire.setNombreChambre(nombre);
		if (couloire.getChambres()==null) {
			couloire.setChambres(new ArrayList<>());
		}
		couloire.getChambres().clear();
		couloire = couloireRepository.save(couloire);
		createChambre(couloire);
		System.out.println("@@@@@ - couloire: "+couloire.getId()+" a etait Update");
		return true;
	}
	/**
	 * @param param(id, annee)
	 * @return
	 */
	@RequestMapping(value="/couloires/anneeDetudeFixer", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeDetude param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		int annee = param.getAnnee();
		
		if (id==null) {
			return false;
		}
		if (!couloireRepository.findById(id).isPresent()) {
			return false;
		}
		Couloire couloire = couloireRepository.findById(id).get();
		couloire.setAnneeDetudeFixer(annee);
		couloire = couloireRepository.save(couloire);
		return true;
	}
	/**
	 * @param param(id, sexe)
	 * @return
	 */
	@RequestMapping(value="/couloires/sexeFixer", method=RequestMethod.PUT )
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
		if (!couloireRepository.findById(id).isPresent()) {
			return false;
		}
		Couloire couloire = couloireRepository.findById(id).get();
		System.out.println("Sexe donnee: "+sexe);
		couloire.setSexeFixer(sexe);
		System.out.println("Sexe contenue a la fin: "+couloire.getSexeFixer());
		couloire = couloireRepository.save(couloire);
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/couloires/departementFixer", method=RequestMethod.PUT )
	public @ResponseBody boolean addDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDepartement = param.getIdContenue();
		Long idCouloire = param.getIdConteneur();
		System.out.println("c bon 1 idDepartement: "+idDepartement+" idCouloire"+idCouloire);
		if (idDepartement==null || idCouloire==null) {
			return false;
		}
		if (!couloireRepository.findById(idCouloire).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDepartement).isPresent()) {
			return false;
		}
		System.out.println("c bon 1");
		Couloire couloire = couloireRepository.findById(idCouloire).get();
		System.out.println("c bon 2");
		Departement departement = departementRepository.findById(idDepartement).get();
		System.out.println("c bon 3");
		couloire.getDepartementsFixer().add(departement);
		couloire = couloireRepository.save(couloire);
		System.out.println("c bon 4");
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/couloires/departementFixer", method=RequestMethod.DELETE )
	public boolean removeDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDep = param.getIdContenue();
		Long idBat = param.getIdConteneur();
		if (idDep==null || idBat==null) {
			return false;
		}
		if (!couloireRepository.findById(idBat).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDep).isPresent()) {
			return false;
		}
		Couloire couloire = couloireRepository.findById(idBat).get();
		Departement departement = departementRepository.findById(idDep).get();
		boolean result = couloire.getDepartementsFixer().remove(departement);
		couloire = couloireRepository.save(couloire);
		return result;
	}
	@RequestMapping(value="/couloires/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		couloireRepository.deleteById(id);
		return true;
	}
}
