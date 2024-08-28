package ti2cc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        List<Fruit> fruits = new LinkedList<Fruit>();

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
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return fruits;
    }
}
