package com.boly.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boly.dao.CodificationSpecRepository;
import com.boly.entity.CodificationSpec;

import urlParams.ParamUrlChangeAnneeScolaire;


@RestController
public class CodificationSpecWebService {
	@Autowired
	private CodificationSpecRepository codifSpecRepository;

	
	/**
	 * @param batiment(anneeScolaire, [dateDeDebut, dateDeFin])
	 * @return Long(Batiment.getId())
	 */
	
	@RequestMapping(value="/codificationSpecs", method=RequestMethod.POST)
	public Long create(@RequestBody CodificationSpec codifSpec){
		codifSpec.setId(null);
		codifSpec.setDateDeCreation(new Date());
		if (codifSpec.getAnneeScolaire()=="" || codifSpec.getAnneeScolaire()==null) {
			return -1L;
		}
		System.out.println("date de debut donnee: "+codifSpec.getDateDeDebut());
		if (codifSpec.getDateDeDebut()==null) {
			System.out.println("pas de dateDeDebut");
			return -1L;
		}
		if (codifSpec.getDateDeFin()==null) {
			System.out.println("pas de dateDeFin");
			return -1L;
		}
		try {
			codifSpec = codifSpecRepository.save(codifSpec);
		} catch (Exception e) {
			System.out.println("errreur de creation de codificationSpec");
			System.out.println(e.getMessage());
		}
		System.out.println("@@@@@ - Nouveau codificationSpec inserer: "+codifSpec.getAnneeScolaire());
		return codifSpec.getId();
	}
	@RequestMapping(value="/codificationSpecs", method=RequestMethod.GET)
	public List<CodificationSpec> readAll(){
		return codifSpecRepository.findAll();
	}
	@RequestMapping(value="/codificationSpecs/actif", method=RequestMethod.GET)
	public CodificationSpec readCodifSpecActif(){
		return codifSpecRepository.getLastCodificationSpec().get();
	}
	@RequestMapping(value="/codificationSpecs/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<CodificationSpec> readById(@PathVariable Long id){
		return codifSpecRepository.findById(id);
	}
	@RequestMapping(value="/codificationSpecs/codificationSpecActive", method=RequestMethod.GET)
	public @ResponseBody CodificationSpec getLastCodificationSpec(){
		Optional<CodificationSpec> codifSpec;
		codifSpec = codifSpecRepository.getLastCodificationSpec();
		if (codifSpec==null) {
			return null;
		}
		return codifSpecRepository.getLastCodificationSpec().get();
	}
	/**
	 * @param param(id, anneeScolaire)
	 * @return
	 */
	@RequestMapping(value="/codificationSpecs/anneeScolaire", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeScolaire param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		String anneeScolaire = param.getAnneeScolaire();
		
		if (id==null) {
			return false;
		}
		if (!codifSpecRepository.findById(id).isPresent()) {
			return false;
		}
		CodificationSpec codifSpec = codifSpecRepository.findById(id).get();
		codifSpec.setAnneeScolaire(anneeScolaire);
		codifSpec = codifSpecRepository.save(codifSpec);
		return true;
	}
	/**
	 * @param param(id, dateDeDebut)
	 * @return
	 */
	@RequestMapping(value="/codificationSpecs/dateDebut", method=RequestMethod.PUT)
	public boolean setDateDebut(@RequestBody CodificationSpec codifSpec){
		if (codifSpec.getId()==null) {
			return false;
		}
		if (!codifSpecRepository.findById(codifSpec.getId()).isPresent()) {
			return false;
		}
		Date dateDebut = codifSpec.getDateDeDebut();
		codifSpec = codifSpecRepository.findById(codifSpec.getId()).get();
		codifSpec.setDateDeDebut(dateDebut);
		codifSpecRepository.save(codifSpec);
		System.out.println("@@@@@ - Nouveau codificationSpec inserer: "+codifSpec.getAnneeScolaire());
		return true;
	}
	/**
	 * @param param(id, dateDeDebut)
	 * @return
	 */
	@RequestMapping(value="/codificationSpecs/dateFin", method=RequestMethod.PUT)
	public boolean setDateFin(@RequestBody CodificationSpec codifSpec){
		if (codifSpec.getId()==null) {
			return false;
		}
		if (!codifSpecRepository.findById(codifSpec.getId()).isPresent()) {
			return false;
		}
		Date dateFin = codifSpec.getDateDeFin();
		codifSpec = codifSpecRepository.findById(codifSpec.getId()).get();
		codifSpec.setDateDeFin(dateFin);
		codifSpecRepository.save(codifSpec);
		System.out.println("@@@@@ - Nouveau codificationSpec inserer: "+codifSpec.getAnneeScolaire());
		return true;
	}
	@RequestMapping(value="/codificationSpecs/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		try {
			codifSpecRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
