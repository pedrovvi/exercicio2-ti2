package ti2cc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dao {
    private Connection connection;
    
    public Dao() {
        this.connection = null;
    }

    public boolean connect() {
        String serverName = "localhost";
        int serverPort = 5432;

        String databaseName = "crudpg";
        String databaseUrl = String.format("jdbc:postgresql://%s:%d/%s", serverName, serverPort, databaseName);
        String databaseUser = "docker";
        String databasePassword = "docker";

        boolean connectionStatus = false;

        try {
           Class.forName("org.postgresql.Driver"); 
           this.connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword); 
           connectionStatus = this.connection != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connectionStatus;
    }

    public boolean close() {
        if (this.connection == null) return false;

        try { 
            this.connection.close(); 
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean createFruit(String name, float price) {
        String query = String.format("insert into Fruit (name, price) values ('%s', %s);", name, 
                String.format("%.2f", price).replace(',','.'));

        boolean isFruitCreated = false;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();

            isFruitCreated = true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return isFruitCreated;
    }

    public List<Fruit> getFruitsByName(String name) {
        String query = String.format("select * from Fruit where name = '%s';", name);
        List<Fruit> fruits = new ArrayList<Fruit>();

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                Fruit currentFruit = new Fruit();
                currentFruit.setId(res.getInt("id"));
                currentFruit.setName(res.getString("name"));
                currentFruit.setPrice(res.getFloat("price"));

                fruits.add(currentFruit);
            }

            res.close();
            stmt.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return fruits;
    }

    public List<Fruit> getAllFruits() {
        String query = "select * from Fruit;";
        List<Fruit> fruits = new ArrayList<Fruit>();

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                Fruit currentFruit = new Fruit();
                currentFruit.setId(res.getInt("id"));
                currentFruit.setName(res.getString("name"));
                currentFruit.setPrice(res.getFloat("price"));

                fruits.add(currentFruit);
            }

            res.close();
            stmt.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return fruits;
    }

    public boolean deleteFruitById(int id) {
        String query = String.format("delete from Fruit where id = %d;", id);
        boolean isFruitDeleted = false;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(query);
            isFruitDeleted = true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return isFruitDeleted;
    }

    public boolean editFruitById(int id, String name, float price) {
        String query = String.format("update Fruit set (name, price) = ('%s', %s) where id = %d;", name,
                String.format("%.2f", price).replace(",","."), id);
        boolean isFruitEdited = false;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(query);
            isFruitEdited = true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return isFruitEdited;
    }
}
