package com.example.gestionfacture.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static final String DB_NAME="facture.db";
    public static final String CONNECTION_STRING="jdbc:sqlite:C:\\Users\\HP\\IdeaProjects\\gestion_facture_\\"+DB_NAME;

    private static Connection connection=null;

    static {
        try{
            connection= DriverManager.getConnection(CONNECTION_STRING);

        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public static Connection getConnection(){
        return connection;
    }


    }


