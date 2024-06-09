package ci.upb.gestion.service;

import ci.upb.gestion.domain.Employe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class EmployeServiceImpl implements EmployeService {

	@PersistenceContext(unitName = "gestionPersonnel")
	private EntityManager em;

	@Override
	public List<Employe> getAllEmployes() {
		return em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
	}

	public Employe recupererParLogin(String login) {
		try {
			return em.createQuery("SELECT e FROM Employe e WHERE e.login = :login", Employe.class).setParameter("login", login).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Employe getEmploye(Long id) {
		return em.find(Employe.class, id);
	}

	@Transactional
	@Override
	public void addEmploye(Employe employe) {
		Employe employeAvecLoginDejaExistant = null;
		try {
			employeAvecLoginDejaExistant = recupererParLogin(employe.getLogin());
			if (employeAvecLoginDejaExistant == null) {
				em.persist(employe);
			} else {
				throw new Exception("Le login existe déjà");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public void updateEmploye(Employe employe) {
		em.merge(employe);
	}

	@Transactional
	@Override
	public boolean deleteEmploye(Long id) {
		Employe employe = em.find(Employe.class, id);
		if (employe != null) {
			em.remove(employe);
			return true;
		}
		return false;
	}
}