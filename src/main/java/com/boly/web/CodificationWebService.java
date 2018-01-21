package com.boly.web;


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

import com.boly.dao.CodificationRepository;
import com.boly.dao.CodificationSpecRepository;
import com.boly.dao.EtudiantRepository;
import com.boly.dao.PositionRepository;
import com.boly.entity.Codification;
import com.boly.entity.CodificationSpec;
import com.boly.entity.Etudiant;
import com.boly.entity.Position;

import urlParams.CodifPutParam;
import urlParams.EchangeCodifParam;

@RestController
public class CodificationWebService {
	@Autowired
	private CodificationRepository codificationRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private CodificationSpecRepository codificationSpecRepository;
	

	/**
	 * @param Codification(idEtudiant,  idPosition)
	 * @return Long
	 */
	@RequestMapping(value="/codifications", method=RequestMethod.POST)
	public Long create(@RequestBody CodifPutParam param){
		Codification codif=new Codification();
		codif.setDateDeReservation(new Date());
		Optional<CodificationSpec> codifSpec = codificationSpecRepository.getLastCodificationSpec();
		if (!codifSpec.isPresent()) {
			return -1L;
		}
		if (codifSpec.get().getDateDeDebut()==null || codifSpec.get().getDateDeFin()==null) {
			return -1L;
		}
		
		if (new Date().before(codifSpec.get().getDateDeDebut()) || new Date().after(codifSpec.get().getDateDeFin())) {
			System.out.println("pas dans la periode de codification: ");
			System.out.println("Date de Debut: "+codifSpec.get().getDateDeDebut());
			System.out.println("Date de fin: "+codifSpec.get().getDateDeFin());
			return -1L;
		}
		if (param.getIdPosition()==null || param.getIdEtudiant()==null) {
			System.out.println("parametre incorrecte");
			return -1L;
		}
		Optional<Etudiant> etudiant = etudiantRepository.findById(param.getIdEtudiant());
		Optional<Position> position = positionRepository.findById(param.getIdPosition());
		if (!etudiant.isPresent() || !position.isPresent()) {
			System.out.println("l'etudiant ou la position n'existe pas dans la base de donnee");
			return -1L;
		}
		if (!etudiant.get().isInscriptionValider()) {
			System.out.println("l'etudiant n'a pas validon  sa inscription");
			return -1L;
		}
		if (!position.get().codifiable(etudiant.get())) {
			System.out.println("l'etudiant ne peut pas codier a cette position");
			return -1L;
		}
		Optional<Codification> ancienCodifEtudiant = codificationRepository.findTopByEtudiantAndCodificationSpecOrderByDateDeReservationDesc(etudiant.get(), codifSpec.get());
		if (ancienCodifEtudiant.isPresent()) {
			if (ancienCodifEtudiant.get().isValider()) {
				System.out.println("l'etudiant a deja valider une codification");
				return -1L;
			}
		}

		Optional<Codification> ancienCodifPosition = codificationRepository.findTopByPositionAndCodificationSpecOrderByDateDeReservationDesc(position.get(), codifSpec.get());
		if (ancienCodifPosition.isPresent()) {
			
			if(!ancienCodifPosition.get().isExpired()) {
				System.out.println("Quelqu'un a deja codifier a cette position");
				return -1L;
			}
			System.out.println("ancienCodif espirer");
		}
		if (ancienCodifEtudiant.isPresent()) {
			System.out.println("suppression de l'ancienCodification de l'etudiant");
			codificationRepository.delete(ancienCodifEtudiant.get());
		}
		codif.setCodificationSpec(codifSpec.get());
		codif.setEtudiant(etudiant.get());
		codif.setPosition(position.get());
		codif = codificationRepository.save(codif);
		System.out.println("@@@@@ - nouvelle codification creer: ");
		return codif.getId();
	}
	@RequestMapping(value="/codifications/etudiants/{idEtudiant}", method=RequestMethod.GET)
	public Codification readByIdEtudiant(@PathVariable Long idEtudiant){
		Optional<CodificationSpec> codifSpec = codificationSpecRepository.getLastCodificationSpec();
		Optional<Etudiant> etudiant = etudiantRepository.findById(idEtudiant);
		if (!etudiant.isPresent() ) {
			return null;
		}
		Optional<Codification> codif = codificationRepository.findTopByEtudiantAndCodificationSpecOrderByDateDeReservationDesc(etudiant.get(), codifSpec.get());
		return codif.get();
	}
	@RequestMapping(value="/codifications/positions/{idPosition}", method=RequestMethod.GET)
	public Codification readByIdPosition(@PathVariable Long idPosition){
		System.out.println("dans readByIdPosition");
		Optional<CodificationSpec> codifSpec = codificationSpecRepository.getLastCodificationSpec();
		Optional<Position> position = positionRepository.findById(idPosition);
		if (!position.isPresent() ) {
			return null;
		}
		System.out.println("position: "+position.get().getId());
		System.out.println("codifSpec: "+codifSpec.get().getId());
		Optional<Codification> codifs = codificationRepository.findTopByPositionAndCodificationSpecOrderByDateDeReservationDesc(position.get(), codifSpec.get());
		System.out.println("codif: "+codifs.get().getId());
		return codifs.get();
	}
	@RequestMapping(value="/codifications", method=RequestMethod.GET)
	public @ResponseBody List<Codification> readAll(){
		return codificationRepository.findAll();
	}
	@RequestMapping(value="/codifications/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Codification> readById(@PathVariable Long id){
		return codificationRepository.findById(id);
	}

	/**
	 * @param Codification(id)
	 * @return Long
	 */
	@RequestMapping(value="/codifications/valider", method=RequestMethod.PUT)
	public boolean valider(@RequestBody CodifPutParam param){
		if (param.getId()==null) {
			return false;
		}
		Optional<Codification> codif = codificationRepository.findById(param.getId());
		if (!codif.isPresent()) {
			System.out.println("l'id de cette codification ne se trouve pas dans la base");
			return false;
		}
		codif.get().setValider(true);
		codificationRepository.save(codif.get());
		System.out.println("validation de la codification: "+codif.get().getId()+" effectuee");
		return true;
	}
	/**
	 * @param Codification(idCodificationVoulue,  idCodificationProposer)
	 * @return Long
	 */
	@RequestMapping(value="/codifications/proposerEchange", method=RequestMethod.PUT)
	public boolean echanger(@RequestBody EchangeCodifParam param){
		if (param.getIdCodificationProposer()==null || param.getIdCodificationVoulue()==null) {
			System.out.println("erreur parametre");
			return false;
		}
		Optional<Codification> codif1 = codificationRepository.findById(param.getIdCodificationVoulue());
		Optional<Codification> codif2 = codificationRepository.findById(param.getIdCodificationProposer());
		if (!codif1.isPresent() || !codif2.isPresent()) {
			System.out.println("l'une des id de codification ne se trouve pas dans la base");
			return false;
		}
		codif1.get().addPropositionDechange(codif2.get());
		codificationRepository.save(codif1.get());
		System.out.println("l'etudiant "+codif1.get().getEtudiant().getPrenom()+"a echanger sa codification avec letudiant "+codif2.get().getEtudiant().getPrenom());
		return true;
	}

	/**
	 * @param Codification(idCodificationVoulue,  idCodificationProposer, reponche)
	 * @return Long
	 */
	@RequestMapping(value="/codifications/repondreEchange", method=RequestMethod.PUT)
	public boolean repondreechamger(@RequestBody EchangeCodifParam param){
		if (param.getIdCodificationProposer()==null || param.getIdCodificationVoulue()==null) {
			return false;
		}
		System.out.println("resultat: "+param.getReponche());
		if (!param.getReponche()) {
			return false;
		}
		Optional<Codification> codif1 = codificationRepository.findById(param.getIdCodificationVoulue());
		Optional<Codification> codif2 = codificationRepository.findById(param.getIdCodificationProposer());
		if (!codif1.isPresent() || !codif2.isPresent()) {
			System.out.println("l'une des id de codification ne se trouve pas dans la base");
			return false;
		}
		if (!codif1.get().getPropositionDechange().contains(codif2.get())) {
			System.out.println("vous ne pouvez pas accepter ce qu'on ne vous a pas proposer!! ILfaut faire une proposition d'echange");
			return false;
		}
		if (!param.getReponche()) {
			codif1.get().removePropositionDechange(codif2.get());
			return true;
		}
		codif1.get().setPropositionDechange(null);
		Position positionTampon = codif1.get().getPosition();
		codif1.get().setPosition(codif2.get().getPosition());
		codif2.get().setPosition(positionTampon);
		codificationRepository.save(codif1.get());
		codificationRepository.save(codif2.get());
		System.out.println("l'etudiant "+codif1.get().getEtudiant().getPrenom()+"a echanger sa codification avec letudiant "+codif2.get().getEtudiant().getPrenom());
		return true;
	}
	
	@RequestMapping(value="/codifications/{id}", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable Long id){
		try {
			codificationRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	
}

