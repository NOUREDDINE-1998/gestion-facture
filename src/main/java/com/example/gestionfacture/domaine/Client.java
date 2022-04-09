package com.example.gestionfacture.domaine;

public class Client {

	private static int nClient=1;
	private String nom;
	private String prenom;
	private String adresse;
	private int cp;
	private String ville;
	public Commercial m_Commercial;
	public Facture m_Facture;

	public Client(){

	}

	public Client(int nCienLt, String nom, String prenom, String adresse, int cp, String ville) {
		this.nClient = nCienLt;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public static int getnClient() {
		return nClient;
	}

	public void setnClient(int nClient) {
		this.nClient = nClient;
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

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
}