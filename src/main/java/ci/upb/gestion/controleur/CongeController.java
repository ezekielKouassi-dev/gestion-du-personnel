package ci.upb.gestion.controleur;

import ci.upb.gestion.domain.Conge;
import ci.upb.gestion.domain.Employe;
import ci.upb.gestion.service.CongeService;
import ci.upb.gestion.service.EmployeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CongeController implements Serializable {

	@Inject
	private CongeService congeService;

	@Inject
	private EmployeService employeService;

	private Conge newConge = new Conge();
	private List<Conge> pendingConges;
	private List<Conge> conges;
	private List<Conge> employeeConges;

	private List<Conge> congeApprouveOuRejeters = new ArrayList<>();

	@PostConstruct
	public void init() {
		conges = congeService.listerEnList();
		pendingConges = congeService.listerCongeEnAttente();
		congeApprouveOuRejeters = congeService.listerCongeApprouveOuRejete();
		Employe employe = getLoggedInEmploye();
		if (employe != null) {
			employeeConges = congeService.recupererCongesParEmploye(employe);
		}
	}

	@Transactional
	public String createConge() {
		Employe employe = getLoggedInEmploye();
		if (employe != null) {
			newConge.setEmploye(employe);
			newConge.setStatut("EN_ATTENTE");
			congeService.create(newConge);

			if (employe.getResponsable() != null) {
				String managerEmail = employe.getResponsable().getEmail();
				String employeeName = employe.getNom();
				String leaveStartDate = newConge.getDateDebut().toString();
				String leaveEndDate = newConge.getDateFin().toString();
				// congeService.sendLeaveRequestNotificationToManager(managerEmail, employeeName, leaveStartDate, leaveEndDate);
			}

			return "success";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé non trouvé"));
			return null;
		}
	}

	@Transactional
	public void approuverConge(Long congeId) {
		congeService.approuverConge(congeId);
		pendingConges = congeService.listerCongeEnAttente();
	}

	@Transactional
	public void rejeterConge(Long congeId) {
		congeService.rejeterConge(congeId);
		pendingConges = congeService.listerCongeEnAttente();
	}

	@Transactional
	public Employe getLoggedInEmploye() {
		String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
		if (username != null) {
			List<Employe> employes = employeService.getAllEmployes();
			for (Employe employe : employes) {
				if (employe.getLogin().equals(username)) {
					return employe;
				}
			}
		}
		return null;
	}


	public int getPendingCongesCount() {
		return pendingConges != null ? pendingConges.size() : 0;
	}

	public Conge getNewConge() {
		return newConge;
	}

	public void setNewConge(Conge newConge) {
		this.newConge = newConge;
	}

	public List<Conge> getPendingConges() {
		return pendingConges;
	}

	public void setPendingConges(List<Conge> pendingConges) {
		this.pendingConges = pendingConges;
	}

	public List<Conge> getEmployeeConges() {
		return employeeConges;
	}

	public void setEmployeeConges(List<Conge> employeeConges) {
		this.employeeConges = employeeConges;
	}

	public List<Conge> getConges() {
		return conges;
	}

	public void setConges(List<Conge> conges) {
		this.conges = conges;
	}

	public List<Conge> getCongeApprouveOuRejeters() {
		return congeApprouveOuRejeters;
	}
}
