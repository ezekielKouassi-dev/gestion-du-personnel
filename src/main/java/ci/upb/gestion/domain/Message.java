package ci.upb.gestion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Message.TABLE_NAME)
public class Message {

	public static final String TABLE_NAME = "message";
	public static final String TABLE_ID = TABLE_NAME + "_id";
	public static final String TABLE_SEQ = TABLE_ID + "_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = TABLE_SEQ)
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	private Long id;

	private String contenu;
	private Date dateEnvoi;
	private String sujet;

	@ManyToOne
	@JoinColumn(name = Responsable.TABLE_ID)
	private Responsable responsable;

	@ManyToMany
	@JoinTable(
			name = "employe_message",
			joinColumns = @JoinColumn(name = Message.TABLE_ID),
			inverseJoinColumns = @JoinColumn(name = Employe.TABLE_ID)
	)
	private Set<Employe> destinataires = new HashSet<>();

	public Message() {
		super();
	}

	public Message(String contenu, String sujet, Responsable responsable, Set<Employe> destinataires) {
		this.contenu = contenu;
		this.sujet = sujet;
		this.responsable = responsable;
		this.destinataires = destinataires;
	}

	public Long getId() {
		return id;
	}

	public String getContenu() {
		return contenu;
	}

	public Date getDateEnvoi() {
		return dateEnvoi;
	}

	public String getSujet() {
		return sujet;
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public Set<Employe> getDestinataires() {
		return destinataires;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public void setDestinataires(Set<Employe> destinataires) {
		this.destinataires = destinataires;
	}
}
