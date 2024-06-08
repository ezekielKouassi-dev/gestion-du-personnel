package ci.upb.gestion.service;

import ci.upb.gestion.domain.Conge;
import ci.upb.gestion.domain.Employe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class CongeService {

	@PersistenceContext(unitName = "gestionPersonnel")
	private EntityManager em;

	@Inject
	private EmailService emailService;

	//@Inject
	//private PdfGenerator pdfGenerator;

	@Transactional
	public void create(Conge conge) {
		em.persist(conge);
	}

	@Transactional
	public void approuverConge(Long congeId) {
		Conge conge = findById(congeId);
		if (conge != null) {
			conge.setStatut("Approuvé");
			update(conge);

			String message = "Votre demande de congé a été approuvée.";
			//byte[] pdfData = pdfGenerator.generateLeaveApprovalPdf(message, conge.getMotif(), "Approuvée"); // Utilisez ici le nom ou l'identifiant du responsable

			//emailService.sendEmailWithAttachment(conge.getEmploye().getEmail(), "Demande de congé approuvée", message, pdfData, "conge_approuve.pdf");
			System.out.println("Conge approuvé : " + congeId);
		} else {
			System.out.println("Conge non trouvé : " + congeId);
		}
	}

	@Transactional
	public void rejeterConge(Long congeId) {
		Conge conge = findById(congeId);
		if (conge != null) {
			conge.setStatut("Rejeté");
			update(conge);

			String message = "Votre demande de congé a été rejetée.";
			//byte[] pdfData = pdfGenerator.generateLeaveApprovalPdf(message, conge.getMotif(), "Rejeté"); // Utilisez ici le nom ou l'identifiant du responsable

		//	emailService.sendEmailWithAttachment(conge.getEmploye().getEmail(), "Demande de congé rejetée", message, pdfData, "conge_rejete.pdf");
			System.out.println("Conge rejeté : " + congeId);
		} else {
			System.out.println("Conge non trouvé : " + congeId);
		}
	}

	public Conge findById(Long id) {
		return em.find(Conge.class, id);
	}

	public List<Conge> findAllPending() {
		return em.createQuery("SELECT c FROM Conge c WHERE c.statut = 'EN_ATTENTE'", Conge.class).getResultList();
	}

	public List<Conge> findAllCongeAll() {
		return em.createQuery("SELECT c FROM Conge c ", Conge.class).getResultList();
	}


	@Transactional
	public void update(Conge conge) {
		em.merge(conge);
	}

	public void sendLeaveRequestNotificationToManager(String managerEmail, String employeeName, String leaveStartDate, String leaveEndDate) {
		String subject = "Nouvelle demande de congé de " + employeeName;
		String content = "L'employé " + employeeName + " a demandé un congé du " + leaveStartDate + " au " + leaveEndDate + ".";
		emailService.sendEmail(managerEmail, subject, content);
	}

	public List<Conge> findCongesByEmploye(Employe employe) {
		return em.createQuery("SELECT c FROM Conge c WHERE c.employe = :employe", Conge.class).setParameter("employe", employe).getResultList();
	}

}
