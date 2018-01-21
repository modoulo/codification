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
import com.boly.entity.Chambre;
import com.boly.entity.Couloire;
import com.boly.entity.Departement;
import com.boly.entity.Etage;
import com.boly.entity.Position;
import com.boly.entity.Sexe;

import urlParams.ParamUrlAddRemoveContenue;
import urlParams.ParamUrlChangeAnneeDetude;
import urlParams.ParamUrlChangeNombreContenue;
import urlParams.paramSetSexeFixer;


@RestController
public class ChambreWebService {
	@Autowired
	private ChambreRepository chambreRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private DepartementRepository departementRepository;

	private void createPosition(Chambre chambre) {
		for (int i = 1; i <= chambre.getNombreDePosition(); i++) {
			positionRepository.save(new Position(i, chambre));
		}
	}

	
	@RequestMapping(value="/chambres", method=RequestMethod.GET)
	public List<Chambre> readAll(){
		return chambreRepository.findAll();
	}
	@RequestMapping(value="/chambres/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Chambre> readById(@PathVariable Long id){
		return chambreRepository.findById(id);
	}
	/**
	 * @param params(id, nombre)
	 * @return
	 */
	@RequestMapping(value="/chambres/nombrePosition", method=RequestMethod.PUT )
	public boolean changeNombreChambre(@RequestBody ParamUrlChangeNombreContenue params){
		Long id = params.getId();
		int nombre = params.getNombre();
		if (!chambreRepository.findById(id).isPresent()) {
			return false;
		}
		Chambre chambre = chambreRepository.findById(id).get();
		chambre.setNombreDePosition(nombre);
		if (chambre.getPositions()==null) {
			chambre.setPositions(new ArrayList<>());
		}
		chambre.getPositions().clear();
		chambre = chambreRepository.save(chambre);
		createPosition(chambre);
		System.out.println("@@@@@ - batiment: "+chambre.getId()+" a etait Update");
		return true;
	}
	/**
	 * @param param(id, annee)
	 * @return
	 */
	@RequestMapping(value="/chambres/anneeDetudeFixer", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeDetude param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		int annee = param.getAnnee();
		
		if (id==null) {
			return false;
		}
		if (!chambreRepository.findById(id).isPresent()) {
			return false;
		}
		Chambre chambre = chambreRepository.findById(id).get();
		chambre.setAnneeDetudeFixer(annee);
		chambre = chambreRepository.save(chambre);
		return true;
	}
	/**
	 * @param param(id, sexe)
	 * @return
	 */
	@RequestMapping(value="/chambres/sexeFixer", method=RequestMethod.PUT )
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
		if (!chambreRepository.findById(id).isPresent()) {
			return false;
		}
		Chambre chambre = chambreRepository.findById(id).get();
		System.out.println("Sexe donnee: "+sexe);
		chambre.setSexeFixer(sexe);
		System.out.println("Sexe contenue a la fin: "+chambre.getSexeFixer());
		chambre = chambreRepository.save(chambre);
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/chambres/departementFixer", method=RequestMethod.PUT )
	public @ResponseBody boolean addDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDepartement = param.getIdContenue();
		Long idCouloire = param.getIdConteneur();
		System.out.println("c bon 1 idDepartement: "+idDepartement+" idCouloire"+idCouloire);
		if (idDepartement==null || idCouloire==null) {
			return false;
		}
		if (!chambreRepository.findById(idCouloire).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDepartement).isPresent()) {
			return false;
		}
		System.out.println("c bon 1");
		Chambre chambre = chambreRepository.findById(idCouloire).get();
		System.out.println("c bon 2");
		Departement departement = departementRepository.findById(idDepartement).get();
		System.out.println("c bon 3");
		chambre.getDepartementsFixer().add(departement);
		chambre = chambreRepository.save(chambre);
		System.out.println("c bon 4");
		return true;
	}
	/**
	 * @param param(idContenue, idConteneur)
	 * @return
	 */
	@RequestMapping(value="/chambres/departementFixer", method=RequestMethod.DELETE )
	public boolean removeDepartementFixer(@RequestBody ParamUrlAddRemoveContenue param){
		Long idDep = param.getIdContenue();
		Long idBat = param.getIdConteneur();
		if (idDep==null || idBat==null) {
			return false;
		}
		if (!chambreRepository.findById(idBat).isPresent()) {
			return false;
		}
		if (!departementRepository.findById(idDep).isPresent()) {
			return false;
		}
		Chambre chambre = chambreRepository.findById(idBat).get();
		Departement departement = departementRepository.findById(idDep).get();
		boolean result = chambre.getDepartementsFixer().remove(departement);
		chambre = chambreRepository.save(chambre);
		return result;
	}
	@RequestMapping(value="/chambres/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		chambreRepository.deleteById(id);
		return true;
	}
}
