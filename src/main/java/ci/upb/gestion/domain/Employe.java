package ci.upb.gestion.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Employe.TABLE_NAME)
public class Employe {

	public static final String TABLE_NAME = "employe";
	public static final String TABLE_ID = TABLE_NAME + "_id";
	public static final String TABLE_SEQ = TABLE_ID + "_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = TABLE_SEQ)
	@SequenceGenerator(name = TABLE_SEQ, sequenceName = TABLE_SEQ)
	private Long id;
	private String nom;
	private String prenom;
	private String adresse;
	private String numero;
	private String email;
	private String login;
	private String password;
	private String poste;
	private double salaire;

	@ManyToOne
	@JoinColumn(name = Responsable.TABLE_ID)
	private Responsable responsable;

	@OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
	private Set<Conge> conges = new HashSet<>();

	@ManyToMany(mappedBy = "destinataires")
	private Set<Message> messagesRecus = new HashSet<>();

	public Employe() {
		super();
	}

	public Employe(String poste, double salaire, Responsable responsable, Set<Conge> conges, Set<Message> messagesRecus) {
		this.poste = poste;
		this.salaire = salaire;
		this.responsable = responsable;
		this.conges = conges;
		this.messagesRecus = messagesRecus;
	}

	public Employe(String nom, String prenom, String adresse, String numero, String email,
	               String login, String password, String poste, double salaire,
	               Responsable responsable, Set<Conge> conges, Set<Message> messagesRecus) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numero = numero;
		this.email = email;
		this.login = login;
		this.poste = poste;
		this.password = password;
		this.salaire = salaire;
		this.responsable = responsable;
		this.conges = conges;
		this.messagesRecus = messagesRecus;
	}

	public Long getId() {
		return id;
	}

	public String getPoste() {
		return poste;
	}

	public double getSalaire() {
		return salaire;
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public Set<Conge> getConges() {
		return conges;
	}

	public Set<Message> getMessagesRecus() {
		return messagesRecus;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public void setConges(Set<Conge> conges) {
		this.conges = conges;
	}

	public void setMessagesRecus(Set<Message> messagesRecus) {
		this.messagesRecus = messagesRecus;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getNumero() {
		return numero;
	}

	public String getEmail() {
		return email;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
}
