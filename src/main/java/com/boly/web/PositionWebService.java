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
public class PositionWebService {
	@Autowired
	private PositionRepository positioRepository;

	
	
	@RequestMapping(value="/positions", method=RequestMethod.GET)
	public List<Position> readAll(){
		return positioRepository.findAll();
	}
	@RequestMapping(value="/positions/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Position> readById(@PathVariable Long id){
		return positioRepository.findById(id);
	}
	/**
	 * @param param(id, annee)
	 * @return
	 */
	@RequestMapping(value="/positions/anneeDetudeFixer", method=RequestMethod.PUT )
	public boolean setSexeAnneeDetudeFixer(@RequestBody  ParamUrlChangeAnneeDetude param ){
		Long id = param.getId();
		System.out.println("id donnee: "+param.getId());
		
		int annee = param.getAnnee();
		
		if (id==null) {
			return false;
		}
		if (!positioRepository.findById(id).isPresent()) {
			return false;
		}
		Position position = positioRepository.findById(id).get();
		position.setAnneeDetudeFixer(annee);
		position = positioRepository.save(position);
		return true;
	}
	@RequestMapping(value="/positions/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		positioRepository.deleteById(id);
		return true;
	}
}
