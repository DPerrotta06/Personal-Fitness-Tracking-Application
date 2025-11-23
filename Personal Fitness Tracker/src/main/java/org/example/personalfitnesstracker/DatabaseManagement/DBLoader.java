/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.personalfitnesstracker.DatabaseManagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author danie
 */
public final class DBLoader { //singleton design pattern

    private Properties property = new Properties();
    private static DBLoader instance;

    private DBLoader() {
        try (FileInputStream reader = new FileInputStream("config\\db.properties")) {
                property.load(reader);
        } catch (IOException e) {
            Logger.getLogger(DBLoader.class.getName()).log(Level.SEVERE, "File contents couldn't be opened or don't exist!", e);
        }
    }

    public Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }

    public synchronized static DBLoader getInstance() {
        if (instance == null) {
            instance = new DBLoader();
        }
        return instance;
    }

    public String getDbURL() {
        return property.getProperty("db.url");
    }

    public String getDbUser() {
        return property.getProperty("db.user");
    }

    public String getDbPassword() {
        return property.getProperty("db.password");
    }

}
