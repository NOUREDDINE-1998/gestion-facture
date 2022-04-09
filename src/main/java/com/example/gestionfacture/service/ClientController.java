package com.example.gestionfacture.service;

import com.example.gestionfacture.connection.DBConnection;
import com.example.gestionfacture.domaine.Client;
import com.example.gestionfacture.domaine.Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ClientController {
    Connection connection= DBConnection.getConnection();

    public   ObservableList<Client> getClientsId(){
        String query="select nClient from client";
        PreparedStatement statement=null;
        Client client;
        try{
            client= new Client();
            statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();
            ObservableList<Client> clients= FXCollections.observableArrayList();
            while (resultSet.next()){
                client.setnClient(resultSet.getInt(1));
                clients.add(client);
            }

        return clients;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
