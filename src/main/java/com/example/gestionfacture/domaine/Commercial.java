package com.example.gestionfacture.domaine;

public class Commercial {

	private int matricule;
	private String nom;
	private String prenom;
	private int login;
	private int motDePasse;
	public Client m_Client;

	public Commercial(){

	}

	public Commercial(int matricule, String nom, String prenom, int login, int motDePasse) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.motDePasse = motDePasse;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	public int getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(int motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}