package com.example.gestionfacture.domaine;

public class Produit {

	private int idProduit;
	private String nomProduit;
	private float prixProduit;
	private String typeProduit;
	public Facture m_Facture;

	public Produit(){

	}

	public Produit(int idProduit, String nomProduit, float prixProduit, String typeProduit) {
		this.idProduit = idProduit;
		this.nomProduit = nomProduit;
		this.prixProduit = prixProduit;
		this.typeProduit = typeProduit;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

	public float getPrixProduit() {
		return prixProduit;
	}

	public void setPrixProduit(float prixProduit) {
		this.prixProduit = prixProduit;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}
}