package com.example.gestionfacture.domaine;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Facture {

	private int nFacture;
	private LocalDate dateFacture;
	private LocalDate dateReglement;
	private Float total;//0
	private int idClient;

	private Client m_Client;
	private  List<LigneDeCommande> listLigneDeCommande=new ArrayList<>();

	public Facture() {
	}
	public Facture(LocalDate dateFacture,LocalDate dateReglement) {
		this.dateFacture = dateFacture;
		this.dateReglement = dateReglement;
	}

	public Facture(int nFacture, LocalDate dateFacture, LocalDate dateReglement, Float total, int idClient) {
		this.nFacture = nFacture;
		this.dateFacture = dateFacture;
		this.dateReglement = dateReglement;
		this.total = total;
		this.idClient = idClient;
	}

	public LocalDate getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public LocalDate getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(LocalDate dateReglement) {
		this.dateReglement = dateReglement;
	}

	public int getnFacture() {
		return nFacture;
	}

	public void setnFacture(int nFacture) {
		this.nFacture = nFacture;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Client getM_Client() {
		return m_Client;
	}

	public void setM_Client(Client m_Client) {
		this.m_Client = m_Client;
	}

	public  List<LigneDeCommande> getListLigneDeCommande() {
		return listLigneDeCommande;
	}

	public void setListProduit(List<LigneDeCommande> listProduit) {
		this.listLigneDeCommande = listProduit;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
}