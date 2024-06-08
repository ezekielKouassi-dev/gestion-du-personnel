package ci.upb.gestion;

import ci.upb.gestion.domain.Employe;
import ci.upb.gestion.domain.Conge;
import ci.upb.gestion.domain.Message;
import ci.upb.gestion.domain.Responsable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashSet;
import java.util.Set;

import static java.time.LocalDate.now;

public class GestionApplication {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionPersonnel");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();

			Responsable responsable = new Responsable(
					"Kouassi",
					"Konan",
					"Yopougon ananeraie",
					"0101151907",
					"kkonan@gmail.com",
					"Heltor225",
					"Azer@1234",
					new HashSet<>()
			);

			Employe employe = new Employe(
					"Kouassi",
					"Amon",
					"Yopougon ananeraie",
					"0142216517",
					"ekouassi506@gmail.com",
					"lezekie",
					"lezekie01",
					"Chauffeur",
					1500.0,
					responsable,
					new HashSet<>(),
					new HashSet<>()
					);

			Message message = new Message(
					"Votre congés est suspect",
					"Demande de congés",
					responsable,
					Set.of(employe)
			);

			Conge conge = new Conge(
					now(),
					now().plusWeeks(2),
					"Vacances",
					"EN_COURS",
					employe
			);

			em.persist(responsable);
			em.persist(employe);
			em.persist(message);
			em.persist(conge);

			em.getTransaction().commit();
		} catch (Exception ex) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			ex.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}
}
