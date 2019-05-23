package org.sid.metier;

import java.util.List;

import org.sid.entities.Compte;
import org.sid.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueMetier {
	public Compte ConsulterCompte(String codeCpte);
	public void verser(String codeCpte, double montant);
	public void retirer(String codeCpte, double montant);
	public void virement(String codeCpte1, String codeCpt2, double montant);
	public Page<Operation> listeOperation(String codeCpte, int page, int size);
	public List<Operation> AfficherOperation();
}
