package ci.upb.gestion.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Responsable.TABLE_NAME)
public class Responsable {

	public static final String TABLE_NAME = "responsable";
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

	@OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL)
	private Set<Message> messagesEnvoyes = new HashSet<>();

	public Responsable() {
		super();
	}

	public Responsable(Set<Message> messagesEnvoyes) {
		this.messagesEnvoyes = messagesEnvoyes;
	}

	public Responsable(String nom, String prenom, String adresse, String numero, String email, String login, String password, Set<Message> messagesEnvoyes) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numero = numero;
		this.email = email;
		this.login = login;
		this.password = password;
		this.messagesEnvoyes = messagesEnvoyes;
	}

	public Long getId() {
		return id;
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

	public void setMessagesEnvoyes(Set<Message> messagesEnvoyes) {
		this.messagesEnvoyes = messagesEnvoyes;
	}

	public Set<Message> getMessagesEnvoyes() {
		return messagesEnvoyes;
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