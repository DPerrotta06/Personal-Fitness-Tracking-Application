/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.personalfitnesstracker.DatabaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.example.personalfitnesstracker.Models.User;

/**
 *
 * @author danie
 */
public class DatabaseManager {
    private static final DBLoader loadDatabase = DBLoader.getInstance(); //reads all database credentials once
    
     public static boolean userExists(TextField emailBox, TextField passwordBox){
         String query = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
         try(Connection conn = DriverManager.getConnection(loadDatabase.getDbURL(), loadDatabase.getDbUser(), loadDatabase.getDbPassword());
                 PreparedStatement prepStat = conn.prepareStatement(query)){
                 ResultSet result = prepStat.executeQuery(query);
                 while(result.next()){
                     if(emailBox.getText().equalsIgnoreCase(query)){
                         
                     }
                 }
         }catch(SQLException e){
             Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, "Error connecting to database.", e);
         }
         return false;
     }
}
