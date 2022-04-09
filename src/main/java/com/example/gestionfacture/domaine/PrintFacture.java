package com.example.gestionfacture.domaine;

import java.time.LocalDate;

public class PrintFacture {

    private int idFacture;
    private LocalDate dateFacture;
    private LocalDate dateReglement;
    private String designation;
    private float prix;
    private float quantite;
    private  float sousTotal;
    private float total;

    public PrintFacture(){}

    public PrintFacture(int idFacture, LocalDate dateFacture, LocalDate dateReglement, String designation, float prix, float quantite, float sousTotal, float total) {
        this.idFacture = idFacture;
        this.dateFacture = dateFacture;
        this.dateReglement = dateReglement;
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;
        this.sousTotal = sousTotal;
        this.total = total;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getQuantite() {
        return quantite;
    }

    public void setQuantite(float quantite) {
        this.quantite = quantite;
    }

    public float getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(float sousTotal) {
        this.sousTotal = sousTotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
