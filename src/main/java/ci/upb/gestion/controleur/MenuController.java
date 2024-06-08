package ci.upb.gestion.controleur;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class MenuController implements Serializable {
	private String activeMenu = "accueil"; // Stocke l'ID du menu actif

	public String getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(String activeMenu) {
		this.activeMenu = activeMenu;
	}

	// Méthode pour mettre à jour le menu actif
	public void updateActiveMenu(String menuId) {
		System.out.println("le menu selectionné" + menuId);
		setActiveMenu(menuId);
	}
}
