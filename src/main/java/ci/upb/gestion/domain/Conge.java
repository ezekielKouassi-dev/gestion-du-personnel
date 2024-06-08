package ci.upb.gestion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = Conge.TABLE_NAME)
public class Conge {

	public static final String TABLE_NAME = "conge";
	public static final String TABLE_ID = TABLE_NAME + "_id";
	public static final String TABLE_SEQ = TABLE_ID + "_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = TABLE_SEQ)
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	private Long id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private String motif;
	private String statut;

	@ManyToOne
	@JoinColumn(name = Employe.TABLE_ID)
	private Employe employe;

	public Conge() {
	}

	public Conge(LocalDate dateDebut, LocalDate dateFin, String motif, String statut, Employe employe) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.motif = motif;
		this.statut = statut;
		this.employe = employe;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public String getMotif() {
		return motif;
	}

	public String getStatut() {
		return statut;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}
}