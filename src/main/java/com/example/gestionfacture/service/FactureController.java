package com.example.gestionfacture.service;


import com.example.gestionfacture.connection.DBConnection;
import com.example.gestionfacture.domaine.Client;
import com.example.gestionfacture.domaine.Facture;
import com.example.gestionfacture.domaine.Produit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.*;
import java.time.LocalDate;


public class FactureController  {

    private final Connection connection= DBConnection.getConnection();

   // private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    ClientController clientController= new ClientController();
    ObservableList<Client> clients= clientController.getClientsId();

    @FXML
    TextField tfNumeroFacture;

    @FXML
    DatePicker dpDateFacture;
    @FXML
    DatePicker dpDateReglement;

    @FXML
    TextField tfNumeroClient;

    @FXML
    TextField tfTotal;
    @FXML
    Button btnAdd;

    @FXML
    // insert data to facture table
    public void addFacture(){
        PreparedStatement statement;
        String query="insert into facture (dateFacture,dateReglement,idClient,total) values(?,?,?,?)";
        Facture facture;
        Alert alert;

            try{
                if(dpDateReglement.getValue()==null){
                    alert=alertDialog(Alert.AlertType.WARNING,"Adding","please enter date of reglement");
                    alert.showAndWait();
                    return;
                }
                facture=new Facture();
                facture.setDateFacture(LocalDate.now());
                facture.setDateReglement(dpDateReglement.getValue());
                facture.setIdClient(Client.getnClient());
                facture.setTotal(0f);

                statement=connection.prepareStatement(query);
                statement.setString(1, facture.getDateFacture().toString());
                statement.setString(2,facture.getDateReglement().toString());
                statement.setInt(3, facture.getIdClient());
                statement.setFloat(4,facture.getTotal());
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 1) {
                     alert=alertDialog(Alert.AlertType.INFORMATION,"Adding","inserted with success");
                     alert.showAndWait();
                } else{
                    alert=alertDialog(Alert.AlertType.ERROR,"Adding","couldn't add facture");
                    alert.showAndWait();
                }


            }catch (Exception e){

                alert=alertDialog(Alert.AlertType.ERROR,"Adding",e.getMessage());
                alert.showAndWait();
            }
    }
    @FXML
// get facture by id
    private void getFactureById(){
        String query="select * from facture where id=?";
        PreparedStatement statement;
        Facture facture;
        Alert alert;
        try{
            if(tfNumeroFacture.getText().equals("")){
                alert=alertDialog(Alert.AlertType.ERROR,"Searching","please enter a number of the facture");
                alert.showAndWait();
                return;
            }
            //disable button add
            btnAdd.setDisable(true);
            facture= new Facture();
            facture.setnFacture(Integer.valueOf(tfNumeroFacture.getText()));
            statement=connection.prepareStatement(query);
            statement.setInt(1,facture.getnFacture());
            ResultSet resultSet=statement.executeQuery();

            if(getCountFacture(facture.getnFacture())==0){
                alert=alertDialog(Alert.AlertType.ERROR,"Searching","this facture doesn't exist");
                alert.showAndWait();
                return;
            }
                facture.setDateFacture(LocalDate.parse((resultSet.getString(2))));
                facture.setDateReglement(LocalDate.parse(resultSet.getString(3)));
                facture.setIdClient(resultSet.getInt(4));
                facture.setTotal(resultSet.getFloat(5));

                dpDateFacture.setValue(facture.getDateFacture());
                dpDateReglement.setValue(facture.getDateReglement());
                tfNumeroClient.setText(String.valueOf(facture.getIdClient()));
                tfTotal.setText(String.valueOf(facture.getTotal()));


        }catch (Exception e){
            e.printStackTrace();
        }
    }
@FXML
    private void reset(){
        tfNumeroFacture.setText("");
        tfNumeroClient.setText("");
        tfTotal.setText("");
        dpDateFacture.setValue(null);
        dpDateReglement.setValue(null);
        btnAdd.setDisable(false);
        tfNumeroFacture.requestFocus();
    }

    private Alert alertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }

    private int getCountFacture(int nFacture) {
        String query = "select count(id) from facture where id=?";
        PreparedStatement statement ;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, nFacture);
            ResultSet resultSet = statement.executeQuery();

            int count=resultSet.getInt(1);

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @FXML
    //delete from FACTURE "
    private void deleteFacture(){
        String query = "delete from facture where id =?";
        PreparedStatement statement ;
        Alert alert ;

        try {
            Facture facture = new Facture();
            if(tfNumeroFacture.getText().equals("")){
                alert=alertDialog(Alert.AlertType.ERROR,"updating","please enter a number of the facture");
                alert.showAndWait();
                return;
            }
            facture.setnFacture(Integer.valueOf(tfNumeroFacture.getText()));

             if(getCountFacture(facture.getnFacture())==0){
                alert=alertDialog(Alert.AlertType.WARNING,"Attention","this facture doesn't exist");
                alert.showAndWait();
                return;
            }
            statement = connection.prepareStatement(query);
            statement.setInt(1, facture.getnFacture());
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
            e.printStackTrace();

        }
    }

    //update facture "
    @FXML
    private void updateFacture() {
        String query = "update facture set dateFacture=? ,dateReglement=?,idClient=?,total=? where id =?";
        PreparedStatement statement = null;
        Alert alert = null;

        if (tfNumeroFacture.getText().equals("")|| dpDateFacture.getValue()==null||
                dpDateReglement.getValue()==null||tfNumeroClient.getText().equals("")||
                tfTotal.getText().equals("")) {
            alert = alertDialog(Alert.AlertType.WARNING, "Attention", "please fill the fields");
            alert.showAndWait();
            return;
        }

        try {
            Facture facture = new Facture();
            facture.setnFacture(Integer.valueOf(tfNumeroFacture.getText()));
            facture.setDateFacture(dpDateFacture.getValue());
            facture.setDateReglement(dpDateReglement.getValue());
            facture.setIdClient(Integer.valueOf(tfNumeroClient.getText()));
            facture.setTotal(Float.valueOf(tfTotal.getText()));

            statement = connection.prepareStatement(query);
            statement.setString(1, facture.getDateFacture().toString());
            statement.setString(2, facture.getDateReglement().toString());
            statement.setInt(3, facture.getIdClient());
            statement.setFloat(4, facture.getTotal());
            statement.setInt(5, facture.getnFacture());

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

}



