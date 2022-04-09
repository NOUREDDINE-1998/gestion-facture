package com.example.gestionfacture.service;
import com.example.gestionfacture.connection.DBConnection;
import com.example.gestionfacture.domaine.Facture;
import com.example.gestionfacture.domaine.LigneDeCommande;
import com.example.gestionfacture.domaine.PrintFacture;
import com.example.gestionfacture.domaine.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

public class LigneCommandeController implements Initializable {
    // declaration of connection
    private final Connection connection= DBConnection.getConnection();
    @FXML
    TextField numeroCommande;
    @FXML
    TextField quantite;
    @FXML
    TextField sousTotal;
    @FXML
    ComboBox<Integer> numeroFacture;
    @FXML
    ComboBox<String> designationProduit;

    Alert alert;
    private final Map<String,Integer> listProduit = getIdProductList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> produits= FXCollections.observableArrayList();
        ObservableList<Integer> listIdFacture=FXCollections.observableArrayList(getIdFacture());

        //add products to combobox
        for (String product : listProduit.keySet()){
            produits.add(product);
            designationProduit.setItems(produits);
        }

        //add id facture to combobox
        numeroFacture.setItems(listIdFacture);

    }

    // add id facture to combox
    @FXML
    // add data to ligne commande
    private void addLigneCommande(){
        PreparedStatement statement ;
        String query="insert into ligneDeCommande (idFacture,idProduit,quantite,sousTotal) values(?,?,?,?)";
        LigneDeCommande ligneDeCommande=new LigneDeCommande();

        try{
           // Map<String,Integer>  listProduit= getIdProductList();
            if( numeroFacture.getSelectionModel().isEmpty() ||
            designationProduit.getSelectionModel().isEmpty() || quantite.getText().equals("")|| sousTotal.getText().equals("")
            ){
                alert=alertDialog(Alert.AlertType.ERROR,"updating","please fill all the fields");
                alert.showAndWait();
                return;
            }

            int idProduit= listProduit.get(designationProduit.getValue());
            ligneDeCommande.setnFacture(numeroFacture.getValue());
            ligneDeCommande.setIdProduit(idProduit);
            Float price=getPrice(ligneDeCommande.getIdProduit());
            ligneDeCommande.setQuantite(Float.valueOf(quantite.getText()));
            Float sousT=ligneDeCommande.getQuantite()* price;
            ligneDeCommande.setSousTotal(sousT);

            statement=connection.prepareStatement(query);
            statement.setInt(1, ligneDeCommande.getnFacture());
            statement.setInt(2, ligneDeCommande.getIdProduit());
            statement.setFloat(3,ligneDeCommande.getQuantite());
            statement.setDouble(4,ligneDeCommande.getSousTotal());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 1) {
                // appele methode qui fait maj au  prix total de table facture ou idfacture= commande.getnFacture
                float getTotal=getTotalPrixFacture(ligneDeCommande.getnFacture());
                float total=getTotal+sousT;
                updateTotal(ligneDeCommande.getnFacture(), total);
                alert=alertDialog(Alert.AlertType.INFORMATION,"Adding ","added successfully");
                alert.showAndWait();

            } else
            alert=alertDialog(Alert.AlertType.INFORMATION,"Adding ","couldn't insert ligneComande");
            alert.showAndWait();


        }catch (Exception e){
            alert=alertDialog(Alert.AlertType.ERROR,"Adding ",e.getMessage());
            alert.showAndWait();
        }
    }

    // ------------------delete ligne commande----------------------------------------------
    @FXML
    private void deleteFacture(){
        String query = "delete from ligneDeCommande where id =?";
        PreparedStatement statement ;
        Alert alert ;

        try {
            LigneDeCommande commande = new LigneDeCommande();
            if(numeroCommande.getText().equals("")){
                alert=alertDialog(Alert.AlertType.ERROR,"updating","please enter a number of  commande");
                alert.showAndWait();
                return;
            }
            commande.setId(Integer.valueOf(numeroCommande.getText()));

               if(!isIdCommandeExist().contains(commande.getId())){
                alert=alertDialog(Alert.AlertType.WARNING,"Attention","this ligne commande doesn't exist");
                alert.showAndWait();
                return;
           }
            statement = connection.prepareStatement(query);
            statement.setInt(1, commande.getId());
            int row = statement.executeUpdate();
            if (row == 1) {
                alert = alertDialog(Alert.AlertType.INFORMATION, "Information", "deleted with success!!!");
                alert.showAndWait();
                reset();
            } else {
                alert = alertDialog(Alert.AlertType.WARNING, "Error", "couldn't delete");
                alert.showAndWait();
            }


        } catch (Exception e) {
            alert = alertDialog(Alert.AlertType.WARNING, "Error", e.getMessage());
            alert.showAndWait();

        }
    }



// get price of produit to count sous total of ligne de commande
    private Float getPrice(int idProduit) {
        String query="select prix from produit where nProduit=?";
        PreparedStatement statement=null;
        try{
            statement=connection.prepareStatement(query);
            statement.setInt(1,idProduit);
            ResultSet resultSet=statement.executeQuery();
            Float price=resultSet.getFloat(1);
            return price;
        }catch (Exception e){
            e.printStackTrace();
            return 0f;
        }
    }

// get all list of commande

    public List<LigneDeCommande> getCommandes(){
        String query="select * from ligneDeCommande";
        PreparedStatement statement=null;
        try{
            statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();
            List<LigneDeCommande>commandeList=new ArrayList<>();
            while (resultSet.next()){
            LigneDeCommande ligneDeCommande = new LigneDeCommande();
            ligneDeCommande.setId(resultSet.getInt(1));
            ligneDeCommande.setnFacture(resultSet.getInt(2));
            ligneDeCommande.setIdProduit(resultSet.getInt(3));
            ligneDeCommande.setQuantite(resultSet.getFloat(4));
            ligneDeCommande.setSousTotal(resultSet.getFloat(5));
            commandeList.add(ligneDeCommande);
            }
            return commandeList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



// get list commande by idFacture
    @FXML
    public void getCommandeById(){
        String query="select * from ligneDeCommande where id=?";
        PreparedStatement statement;
        LigneDeCommande ligneDeCommande= new LigneDeCommande();
        try{


            if(numeroCommande.getText().equals("")){
                alert=alertDialog(Alert.AlertType.ERROR,"Attention","please enter a number of commande");
                alert.showAndWait();
                return;
            }
            ligneDeCommande.setId(Integer.valueOf(numeroCommande.getText()));

            if(!isIdCommandeExist().contains(ligneDeCommande.getId())){
                alert=alertDialog(Alert.AlertType.WARNING,"Attention","this LigneCommande doesn't exist");
                alert.showAndWait();
                return;
            }

            statement=connection.prepareStatement(query);
            statement.setInt(1,ligneDeCommande.getId());
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                ligneDeCommande.setId(resultSet.getInt(1));
                ligneDeCommande.setnFacture(resultSet.getInt(2));
                ligneDeCommande.setIdProduit(resultSet.getInt(3));
                ligneDeCommande.setQuantite(resultSet.getFloat(4));
                ligneDeCommande.setSousTotal(resultSet.getFloat(5));

                //affect values to fields text

                numeroFacture.setValue(ligneDeCommande.getnFacture());
                int p=ligneDeCommande.getIdProduit();

                for (String key: listProduit.keySet())
                {
                    if (p==(listProduit.get(key))) {
                       designationProduit.setValue(key);
                    }
                }

                quantite.setText(String.valueOf(ligneDeCommande.getQuantite()));
                sousTotal.setText(String.valueOf(ligneDeCommande.getSousTotal()));

            }

        }catch (Exception e){
            alert=alertDialog(Alert.AlertType.ERROR,"Attention",e.getMessage());
            alert.showAndWait();

        }
    }

    // get prix total de facture ou idfacture= commande.getnFacture

    public float getTotalPrixFacture(int id){
        //get price total facture set total=111111111 where id=233
        String query="select total from facture where id=?";
        PreparedStatement statement=null;
        try{
            statement=connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet=statement.executeQuery();
            Float total=resultSet.getFloat(1);
            return total;
        }catch (Exception e){
            e.printStackTrace();
            return 0f;
        }
    }

    // update total of facture after add ligne commande to it's table
    public void updateTotal(int id , float total){
        String query="update facture set total=? where id =?";
        PreparedStatement statement=null;
        try{
            statement=connection.prepareStatement(query);
            statement.setFloat(1,total);
            statement.setInt(2,id);
            int row=statement.executeUpdate();
            if(row==1){
                System.out.println("updated with success!!");
            }else
                System.out.println("couldn't update");

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    // get  list of id and designation of product

    private Map<String,Integer> getIdProductList(){
        String query="select nproduit , designation from produit ";
        PreparedStatement statement=null;
        Map<String,Integer>idProductList=new LinkedHashMap<>();
        try{
            statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                Produit produit = new Produit();
                produit.setIdProduit(resultSet.getInt(1));
                produit.setNomProduit(resultSet.getString(2));
               idProductList.put(produit.getNomProduit(),produit.getIdProduit());
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return idProductList;
    }

    //select the whole facture
    private List<Integer> getIdFacture(){
        String query="select id from facture ";
        PreparedStatement statement;
        Facture facture;
        try{

            facture= new Facture();
            statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();
            List<Integer> listIdFacture= new ArrayList<>();

            while (resultSet.next()){
                facture.setnFacture(resultSet.getInt(1));
                listIdFacture.add(facture.getnFacture());
            }
            return listIdFacture;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    private void fillSousTotalPriceTextField(){
        Alert alert;
try {
    Map<String, Integer> listProduit = getIdProductList();
    Float sTotal=0f;

    if(designationProduit.getValue()==null){
        alert=alertDialog(Alert.AlertType.WARNING,"Attention","please select a product");
        alert.showAndWait();
        return;
    }
        int idProduit = listProduit.get(designationProduit.getValue());
        Float price = getPrice(idProduit);
        if(quantite.getText().equals("")){
            quantite.setText(String.valueOf(0));
        }
         sTotal = price * Float.valueOf(quantite.getText());
        System.out.println(sTotal);
        sousTotal.setText(sTotal.toString());

    }catch (Exception e ){
    alert=alertDialog(Alert.AlertType.WARNING,"Attention",e.getMessage());
    alert.showAndWait();
    return;
}

    }


    private Alert alertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    @FXML
    private void reset(){
        numeroCommande.setText("");
        sousTotal.setText("");
        quantite.setText("");
        designationProduit.setValue("");
        numeroFacture.setValue(0);
        numeroCommande.requestFocus();
    }

    private List<Integer> isIdCommandeExist(){
        LigneDeCommande c = new LigneDeCommande();
        List<Integer> ids= new ArrayList<>();
        for (LigneDeCommande i:getCommandes()){
            ids.add(i.getId());
        }

       return ids;
    }


    //upfate ligne commande by its id----------
    @FXML
    private void updateLigneCommande() {
        String query = "update ligneDeCommande set idFacture=? ,idProduit=?,quantite=?,sousTotal=? where id =?";
        PreparedStatement statement = null;
        Alert alert = null;
        if(numeroCommande.getText().equals("")|| numeroFacture.getSelectionModel().isEmpty() ||
                designationProduit.getSelectionModel().isEmpty() || quantite.getText().equals("")|| sousTotal.getText().equals("")
        ){
            alert=alertDialog(Alert.AlertType.ERROR,"updating","please enter a number of  commande");
            alert.showAndWait();
            return;
        }

        try {
            LigneDeCommande ligneDeCommande = new LigneDeCommande();
            ligneDeCommande.setId(Integer.valueOf(numeroCommande.getText()));
            int idProduit=listProduit.get(designationProduit.getValue());
            ligneDeCommande.setnFacture(numeroFacture.getValue());
            ligneDeCommande.setIdProduit(idProduit);
            ligneDeCommande.setQuantite(Float.valueOf(quantite.getText()));
            ligneDeCommande.setSousTotal(Float.valueOf(sousTotal.getText()));

            statement = connection.prepareStatement(query);
            statement.setInt(1, ligneDeCommande.getnFacture());
            statement.setInt(2, ligneDeCommande.getIdProduit());
            statement.setFloat(3, ligneDeCommande.getQuantite());
            statement.setFloat(4, ligneDeCommande.getSousTotal());
            statement.setInt(5, ligneDeCommande.getId());

            int row = statement.executeUpdate();
            if (row == 1) {
                alert = alertDialog(Alert.AlertType.INFORMATION, "Information", "updated with success!!!");
                alert.showAndWait();
                reset();
            } else {
                alert = alertDialog(Alert.AlertType.WARNING, "Error", "couldn't update");
                alert.showAndWait();
            }


        } catch (Exception e) {
            alert = alertDialog(Alert.AlertType.WARNING, "Error", e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private  void printFacture(){
        String query = "SELECT DISTINCT facture.id, dateFacture, dateReglement,produit.designation,produit.prix,ligneDeCommande.quantite,ligneDeCommande.sousTotal ,total\n" +
                "from produit,facture,ligneDeCommande where produit.nProduit=ligneDeCommande.idProduit\n" +
                " and facture.id=ligneDeCommande.idFacture AND ligneDeCommande.idFacture=?";
        PreparedStatement statement ;
        Alert alert;
        try {
            if(numeroFacture.getValue()==null){
                alert=alertDialog(Alert.AlertType.WARNING,"Error","please enter a Facture number to be printed");
                alert.showAndWait();
                return;
            }
            PrintFacture facture = null;
            int idFacture=numeroFacture.getValue();
            statement = connection.prepareStatement(query);
            statement.setInt(1,idFacture);
            ResultSet resultSet = statement.executeQuery();
            List<PrintFacture> factures= new ArrayList<>();
            while (resultSet.next()){
                facture= new PrintFacture();
                facture.setIdFacture(resultSet.getInt(1));
                facture.setDateFacture(LocalDate.parse((resultSet.getString(2))));
                facture.setDateReglement(LocalDate.parse(resultSet.getString(3)));
                facture.setDesignation(resultSet.getString(4));
                facture.setPrix(resultSet.getFloat(5));
                facture.setQuantite(resultSet.getFloat(6));
                facture.setSousTotal(resultSet.getFloat(7));
                facture.setTotal(resultSet.getFloat(8));
                factures.add(facture);
            }
            System.out.println("the date of facture : "+facture.getDateFacture().toString());
            System.out.println("\t\t\tFacture n°: "+facture.getIdFacture());
            System.out.println("-----------------------------------------------------------------------");
            System.out.print("Product : \t\t" +"Quantity :\t\t"+"Price  : \t\t"+"SousTotal :\n");
            for (PrintFacture facture1:factures){
                StringBuilder printFacture=new StringBuilder(facture1.getDesignation()+"\t\t");
                printFacture.append(facture1.getQuantite()+"\t\t");
                printFacture.append(facture1.getPrix()+" Dh\t\t");
                printFacture.append(facture1.getSousTotal()+" DH");
                System.out.println(printFacture);
                System.out.println("-----------------------------------------------------------------------");
            }
            System.out.println("To Pay "+facture.getTotal()+" Dh");


        } catch (Exception e) {
            alert=alertDialog(Alert.AlertType.ERROR,"Error",e.getMessage());
            alert.showAndWait();


        }

    }


}






