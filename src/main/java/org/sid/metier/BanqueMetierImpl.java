package org.sid.metier;


import java.util.Date;
import java.util.List;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Compte ConsulterCompte(String codeCpte) {
		Compte cp= compteRepository.findById(codeCpte).orElse(null);
		if (cp==null)throw new RuntimeException("compte introuvale");
			return cp;
		
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=ConsulterCompte(codeCpte);
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde()+montant);
		compteRepository.save(cp);
		
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp=ConsulterCompte(codeCpte);
		double faciliteCaisse=0;
		if (cp instanceof CompteCourant) 
			faciliteCaisse= ((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+faciliteCaisse<montant)
			throw new RuntimeException("solde insufisant");
		Retrait r = new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpt2, double montant) {
		if(codeCpte1.equals(codeCpt2))
			throw new RuntimeException("Impossible, Virement sur le meme compte");
		verser(codeCpte1, montant);
		retirer(codeCpt2, montant);
		
	}

	@Override
	public Page<Operation> listeOperation(String codeCpte, int page, int size) {
		// TODO Auto-generated method stub
		return operationRepository.listeOperation(codeCpte, new PageRequest(page, size));
	}

	@Override
	public List<Operation> AfficherOperation() {
		return operationRepository.findAll();
	}

}
