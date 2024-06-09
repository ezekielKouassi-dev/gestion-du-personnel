package ci.upb.gestion.controleur;

import ci.upb.gestion.domain.Employe;
import ci.upb.gestion.domain.Responsable;
import ci.upb.gestion.service.EmployeServiceImpl;
import ci.upb.gestion.service.ResponsableService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LoginControleur implements Serializable {

	private String username;
	private String password;
	private String role;

	@Inject
	private EmployeServiceImpl employeService;

	@Inject
	private ResponsableService responsableService;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String login() {
		System.out.println(username);
		// Vérifier les informations de connexion pour les employés
		List<Employe> employes = employeService.getAllEmployes();
		for (Employe employe : employes) {
			if (employe.getLogin().equals(username) && employe.getPassword().equals(password)) {
				role = "EMPLOYE";
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
				return "/views/admin/indexEmploye.xhtml?faces-redirect=true";
			}
		}

		// Vérifier les informations de connexion pour les responsables
		List<Responsable> responsables = responsableService.findAll();
		for (Responsable responsable : responsables) {
			if (responsable.getLogin().equals(username) && responsable.getPassword().equals(password)) {
				role = "RESPONSABLE";
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
				return "/views/admin/index.xhtml?faces-redirect=true";
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid username or password"));
		return null;
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "/login.xhtml?faces-redirect=true";
	}

	public boolean isEmploye() {
		return "EMPLOYE".equals(role);
	}

	public boolean isResponsable() {
		return "RESPONSABLE".equals(role);
	}

	public String getUsername() {
		return username;
	}

	public EmployeServiceImpl getEmployeService() {
		return employeService;
	}

	public ResponsableService getResponsableService() {
		return responsableService;
	}
}
