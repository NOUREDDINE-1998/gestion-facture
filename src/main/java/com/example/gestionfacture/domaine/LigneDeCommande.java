package com.example.gestionfacture.domaine;


public class LigneDeCommande {

	private int id;
	private int nFacture;  // 22 TOTAL=TOTAL+SOUSTOTAL
	private int idProduit;//1
	private float quantite;//3
	private Float sousTotal; //quantite* produit.prix
	private Facture facture;
	private Produit produit;
	//chaque  creation de ligne de commande autoooo total======update
	public LigneDeCommande() {
	}

	public  LigneDeCommande(int idFacture,int idProduit,float quantite){
		this.nFacture = idFacture;
		this.idProduit = idProduit;
		this.quantite = quantite;
	}

	public LigneDeCommande(int id, int nFactute, int idProduit, float quantite, Float sousTotal) {
		this.id = id;
		this.nFacture = nFactute;
		this.idProduit = idProduit;
		this.quantite = quantite;
		this.sousTotal = sousTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getnFacture() {
		return nFacture;
	}

	public void setnFacture(int nFacture) {
		this.nFacture = nFacture;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public float getQuantite() {
		return quantite;
	}

	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}

	public Float getSousTotal() {
		return sousTotal;
	}

	public void setSousTotal(Float sousTotal) {
		this.sousTotal = sousTotal;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}