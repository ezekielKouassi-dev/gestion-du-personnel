package ci.upb.gestion.domain;

public abstract class AbstractUser {
	private String nom;
	private String prenom;
	private String adresse;
	private String numero;
	private String email;
	private String login;
	private String password;

	public AbstractUser() {
	}

	public AbstractUser(String nom, String prenom, String adresse, String numero, String courriel, String login, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numero = numero;
		this.email = courriel;
		this.login = login;
		this.password = password;
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
