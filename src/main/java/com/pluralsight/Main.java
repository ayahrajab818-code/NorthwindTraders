package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String username = "root";
        String password = "Yearup";
        String database = "northwind";

        String databaseUrl = "jdbc:mysql://localhost:3306/" + database;

        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. Open a connection
        Connection connection = DriverManager.getConnection(
                databaseUrl,
                username,
                password);

        // Create statement
        Statement statement = connection.createStatement();

        // 2. Query product ID + product name
        String query = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                  FROM products
                """;

        ResultSet results = statement.executeQuery(query);

        // 3. Display table header
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-6s | %-25s | %-10s | %-10s |%n",
                "ID", "PRODUCT NAME", "UnitPrice", "UnitsInStock");
        System.out.println("--------------------------------------------------------------");

        // 4. Display table rows
        while (results.next()) {
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            if(name.length() > 25) name = name.substring(0,25);
            double unitPrice = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");
            System.out.printf("| %-6d | %-25s | %-10.2f | %-10d |%n",id, name, unitPrice, stock);
        }

        System.out.println("--------------------------------------------------------------");

        // 5. Close connection
        connection.close();
    }
}
