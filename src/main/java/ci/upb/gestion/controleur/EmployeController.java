package ci.upb.gestion.controleur;

import ci.upb.gestion.domain.Employe;
import ci.upb.gestion.domain.Responsable;
import ci.upb.gestion.service.EmployeService;
import ci.upb.gestion.service.ResponsableService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EmployeController implements Serializable {

	private static final long serialVersionUID = 1L;


	@Inject
	private EmployeService employeService;
	@Inject
	private ResponsableService responsableService;

	private transient List<Employe> employes;
	private Employe selectedEmploye = new Employe();

	@PostConstruct
	public void init() {
		employes = employeService.getAllEmployes();
		String employeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (employeId != null) {
			selectedEmploye = employeService.getEmploye(Long.parseLong(employeId));
		}
	}

    /**
     * Enregistre un employé
     */
	public void enregistrer() {
		Responsable responsable = getLoggedInResponsable();
		if (responsable != null) {
			selectedEmploye.setResponsable(responsable);
			employeService.addEmploye(selectedEmploye);
			selectedEmploye = new Employe(); // Reset
			init(); // Recharger la liste des employés

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Employé enregistré."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Employé non enregistré. Responsable non trouvé."));
		}
	}

	public Responsable getLoggedInResponsable() {
		Long userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
		String userRole = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
		System.out.println("Récupération de l'utilisateur connecté. ID: " + userId + ", Role: " + userRole);

		if (userId != null && "RESPONSABLE".equals(userRole)) {
			return responsableService.findById(userId);
		}
		return null;
	}


	@Transactional
	public void updateEmploye() throws IOException {
		if (selectedEmploye != null && selectedEmploye.getId() != null) {
			Employe existingEmploye = employeService.getEmploye(selectedEmploye.getId());
			if (existingEmploye != null) {
				existingEmploye.setNom(selectedEmploye.getNom());
				existingEmploye.setAdresse(selectedEmploye.getAdresse());
				existingEmploye.setEmail(selectedEmploye.getEmail());
				existingEmploye.setPoste(selectedEmploye.getPoste());
				existingEmploye.setNumero(selectedEmploye.getNumero());
				existingEmploye.setSalaire(selectedEmploye.getSalaire());

				employeService.updateEmploye(existingEmploye);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé mis à jour avec succès."));
				FacesContext.getCurrentInstance().getExternalContext().redirect("listEmploye.xhtml?id=" + existingEmploye.getId());
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'employé sélectionné n'existe pas."));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Aucun employé sélectionné."));
		}
	}


	public void deleteEmploye() {
		if (selectedEmploye != null && selectedEmploye.getId() != null) {
			employeService.deleteEmploye(selectedEmploye.getId());
			employes.remove(selectedEmploye);
			selectedEmploye = new Employe(); // Reset selected employe
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Employé supprimé avec succès."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Aucun employé sélectionné."));
		}
	}

	public int getTotalEmployeesCount() {
		List<Employe> allEmployees = employeService.getAllEmployes();
		return allEmployees != null ? allEmployees.size() : 0;
	}


	// Getters and Setters
	public List<Employe> getEmployes() {
		return employes;
	}

	public void setEmployes(List<Employe> employes) {
		this.employes = employes;
	}

	public Employe getSelectedEmploye() {
		return selectedEmploye;
	}

	public void setSelectedEmploye(Employe selectedEmploye) {
		this.selectedEmploye = selectedEmploye;
	}
}
