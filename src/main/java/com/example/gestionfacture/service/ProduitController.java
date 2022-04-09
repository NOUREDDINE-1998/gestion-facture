package com.example.gestionfacture.service;

import com.example.gestionfacture.connection.DBConnection;
import com.example.gestionfacture.domaine.Produit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.Optional;


public class ProduitController {

    Connection connection = DBConnection.getConnection();
    @FXML
    TextField tfDesignation;

    @FXML
    ComboBox<String> cbType = null;

    @FXML
    TableView<Produit> produitTableView;

    @FXML
    TableColumn<Produit,String>nomProduit;

    @FXML
    TableColumn<Produit,String>typeProduit;

    @FXML
    TableColumn<Produit,Float>prixProduit;

    @FXML
    TextField tfPrix;

    private String t[] = {"Informatiques", "Alimentations", "Vetements", "Hygiene", "Fourniture de bureau","Divers"};

    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList(t);
        cbType.setItems(list);
        produitTableView.setItems(getProducts());
    }

    @FXML
    private void addProduct() {
        PreparedStatement statement = null;
        Alert alert = null;
        String query = "insert into produit (designation,type,prix) values(?,?,?)";
        if (!getErrorIfFieldsEmpty()) {
            return;
        }
        if (getCountProduct(tfDesignation.getText()) ==1) {
            alert = alertDialog(Alert.AlertType.WARNING, "Error", "this product is already exist");
            alert.showAndWait();

            return;
        }
        Alert alertConfirm = alertDialog(Alert.AlertType.CONFIRMATION, "Adding product", "Are you sure you want adding this product? if yes hit ok !!!");
        Optional<ButtonType> result = alertConfirm.showAndWait();

        if (result.get().equals(ButtonType.OK)) {
            try{
                Produit produit = new Produit();
                produit.setNomProduit(tfDesignation.getText());
               // String typeComBox = cbType.getSelectionModel().getSelectedItem().toString();
                String typeComBox = cbType.getValue();
                produit.setTypeProduit(typeComBox);
                produit.setPrixProduit(Float.valueOf(tfPrix.getText()));

                statement = connection.prepareStatement(query);
                statement.setString(1, produit.getNomProduit());
                statement.setString(2, produit.getTypeProduit());
                statement.setFloat(3, produit.getPrixProduit());
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 1) {
                    alert = alertDialog(Alert.AlertType.INFORMATION, "Information", "Product added successfully!!!");
                    alert.showAndWait();
                    produitTableView.setItems(getProducts());
                    reset();
                } else{
                    alert = alertDialog(Alert.AlertType.WARNING, "Error", "couldn't insert produit");
                    alert.showAndWait();
                }

            } catch (NumberFormatException e) {
                alert = alertDialog(Alert.AlertType.WARNING, "Error","Attention price of product");
                alert.showAndWait();
            } catch (SQLException e) {
                alert = alertDialog(Alert.AlertType.WARNING, "Error", e.getMessage());
                alert.showAndWait();
            }
            }
        else
            return;
    }

    @FXML
    private void reset() {
        tfDesignation.setText("");
        cbType.getSelectionModel().select(-1);
        tfPrix.setText("");
        tfDesignation.requestFocus();

    }

    private Alert alertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    private int getCountProduct(String name) {
        String query = "select count(nProduit) from produit where designation=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count=resultSet.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //update produit SET type="test" ,  prix=45 where designation="sucre"
    @FXML
    private void updateProduct() {
        String query = "update produit set type=? ,prix=? where designation =?";
        PreparedStatement statement = null;
        Alert alert = null;

        if (!getErrorIfFieldsEmpty()) {
            return;
        }

        try {
            Produit produit = new Produit();
            produit.setNomProduit(tfDesignation.getText());
            String typeComBox = cbType.getSelectionModel().getSelectedItem().toString();
            produit.setTypeProduit(typeComBox);
            produit.setPrixProduit(Float.valueOf(tfPrix.getText()));

            statement = connection.prepareStatement(query);
            statement.setString(1, produit.getTypeProduit());
            statement.setFloat(2, produit.getPrixProduit());
            statement.setString(3, produit.getNomProduit());
            int row = statement.executeUpdate();
            if (row == 1) {
                alert = alertDialog(Alert.AlertType.INFORMATION, "Information", "updated with success!!!");
                alert.showAndWait();
                produitTableView.setItems(getProducts());
                reset();
            } else {
                alert = alertDialog(Alert.AlertType.WARNING, "Error", "couldn't update");
                alert.showAndWait();
            }


        } catch (Exception e) {
            alert = alertDialog(Alert.AlertType.WARNING, "Error", e.getMessage());
            alert.showAndWait();
            //e.printStackTrace();

        }
    }

    private Boolean getErrorIfFieldsEmpty() {
        Alert alert = null;
        if (tfDesignation.getText().equals("") || cbType.getSelectionModel().getSelectedIndex() == -1 || tfPrix.getText().equals("")) {
            alert = alertDialog(Alert.AlertType.WARNING, "Error", "please fill all fields");
            alert.showAndWait();

            return false;
        }
        return true;
    }
    @FXML
    //  select type , prix FROM produit where designation="sucre"
    private void searchProduct() {

        String sql="select type,prix from produit where designation=?";
        PreparedStatement statement = null;
        Alert alert=null;
        try {
            Produit produit = new Produit();
            produit.setNomProduit(tfDesignation.getText());
            if(getCountProduct(produit.getNomProduit())==0){
                alert=alertDialog(Alert.AlertType.WARNING,"Attention","this product is not exist");
                alert.showAndWait();
                return;
            }
            statement = connection.prepareStatement(sql);
            statement.setString(1,produit.getNomProduit());
            ResultSet resultSet = statement.executeQuery();
                produit.setTypeProduit(resultSet.getString(1));
                produit.setPrixProduit(resultSet.getFloat(2));
            tfPrix.setText(String.valueOf(produit.getPrixProduit()));
            cbType.getSelectionModel().select(produit.getTypeProduit());
        } catch (Exception e) {
            alert=alertDialog(Alert.AlertType.ERROR,"Attention",e.getMessage());
            alert.showAndWait();

        }

    }
    @FXML
    //delete from produit where designation="slip"
    private void deleteProduct(){
        String query = "delete from produit where designation =?";
        PreparedStatement statement = null;
        Alert alert = null;

        try {
            Produit produit = new Produit();
            produit.setNomProduit(tfDesignation.getText());
            if(getCountProduct(produit.getNomProduit())==0){
                alert=alertDialog(Alert.AlertType.WARNING,"Attention","this product is not exist");
                alert.showAndWait();
                return;
            }
            statement = connection.prepareStatement(query);
            statement.setString(1, produit.getNomProduit());
            int row = statement.executeUpdate();
            if (row == 1) {
                alert = alertDialog(Alert.AlertType.INFORMATION, "Information", "deleted with success!!!");
                alert.showAndWait();
                produitTableView.setItems(getProducts());
                reset();
            } else {
                alert = alertDialog(Alert.AlertType.WARNING, "Error", "couldn't delete");
                alert.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //SELECT designation,type,prix from produit
    private ObservableList<Produit> getProducts(){
        String query="SELECT designation,type,prix from produit";
        PreparedStatement statement=null;
        try{
            statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();
            ObservableList<Produit>produits=FXCollections.observableArrayList();
            Produit produit;
            while (resultSet.next()){
                produit= new Produit();
                produit.setNomProduit(resultSet.getString(1));
                produit.setTypeProduit(resultSet.getString(2));
                produit.setPrixProduit(resultSet.getFloat(3));
                produits.add(produit);
            }
            nomProduit.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
            typeProduit.setCellValueFactory(new PropertyValueFactory<>("typeProduit"));
            prixProduit.setCellValueFactory(new PropertyValueFactory<>("prixProduit"));
            return produits;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void selectProductFromTableView(){
        Produit produit= produitTableView.getSelectionModel().getSelectedItem();
        if(produit!=null){
            tfDesignation.setText(String.valueOf(produit.getNomProduit()));
            tfPrix.setText(String.valueOf(produit.getPrixProduit()));
            cbType.getSelectionModel().select(produit.getTypeProduit());
        }

    }

    @FXML

    private  void download(){

        }



}
