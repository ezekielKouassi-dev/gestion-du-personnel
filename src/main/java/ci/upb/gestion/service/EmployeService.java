package ci.upb.gestion.service;

import ci.upb.gestion.domain.Employe;

import java.util.List;

public interface EmployeService {
	List<Employe> getAllEmployes();

	Employe getEmploye(Long id);

	void addEmploye(Employe employe);

	void updateEmploye(Employe employe);

	boolean deleteEmploye(Long id);
}
