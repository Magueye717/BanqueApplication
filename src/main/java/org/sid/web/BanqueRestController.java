package org.sid.web;

import java.util.List;

import org.sid.entities.Operation;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comtes")
@CrossOrigin
public class BanqueRestController {
	@Autowired
	private IBanqueMetier  ibanqueMetier;
	
	@RequestMapping("/consulterOperation")
	public List<Operation> afficherTransactions() { 
		return ibanqueMetier.AfficherOperation();
	}
}
