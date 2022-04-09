package com.example.gestionfacture;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    MenuItem btnProduct;
    @FXML
    MenuItem btnFacture;
    @FXML
    MenuItem btnLigneCommande;


    public void showProductInterface() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("produit_view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.initOwner(mainBorderPane.getScene().getWindow());
        stage.setTitle("Produit");
        stage.setScene(new Scene(root, 700, 600));
        stage.showAndWait();


    }
    public void showFactureInterface() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("facture_view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.initOwner(mainBorderPane.getScene().getWindow());
        stage.setTitle("Facture");
        stage.setScene(new Scene(root, 700, 600));
        stage.showAndWait();


    }

    public void showLigneCommandeInterface() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ligneCommande_view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setMaximized(false);
        stage.initOwner(mainBorderPane.getScene().getWindow());
        stage.setTitle("Ligne de Commande");
        stage.setScene(new Scene(root, 700, 600));
        stage.showAndWait();


    }

    public void initialize() {

        btnProduct.setOnAction(actionEvent -> {
            try {
                showProductInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnFacture.setOnAction(actionEvent -> {
            try {
                showFactureInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnLigneCommande.setOnAction(actionEvent -> {
            try {
                showLigneCommandeInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

   }


}



