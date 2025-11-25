package com.pluralsight;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String username = "root";
    private static String password = "Yearup";
    private static String database = "northwind";
    private static String databaseUrl = "jdbc:mysql://localhost:3306/" + database;


    public static void main(String[] args) {

        System.out.println("Welcome to Northwind db");

        boolean exit = false;

        while (!exit) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");

            int choice = ConsoleHelper.promptForInt("Select an option");

            switch (choice) {
                case 1:
                    displayProducts();
                    break;

                case 2:
                    displayCustomers();
                    break;

                case 3:
                    displayCategoriesAndProducts();
                    break;

                case 0:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    public static void displayProducts() {
        try {
            List<Product> products = getProducts();
            System.out.println("--------------------------------------------------------------");
            System.out.printf("| %-6s | %-25s | %-10s | %-10s |%n",
                    "ID", "PRODUCT NAME", "UnitPrice", "UnitsInStock");
            System.out.println("--------------------------------------------------------------");

            for (Product p : products) System.out.println(p);
            System.out.println("--------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println("Error recovering products " + e.getMessage());

        }
    }

    private static void displayCustomers() {
        try {
            List<Customer> customers = getCustomers();
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("| %-25s | %-30s | %-15s | %-15s | %-15s |%n",
                    "CONTACT NAME", "COMPANY NAME", "CITY", "COUNTRY", "PHONE");
            System.out.println("-------------------------------------------------------------------------------");
            for (Customer c : customers) System.out.println(c);
            System.out.println("-------------------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }


    private static void displayCategoriesAndProducts() {
        try {
            List<Category> categories = getCategories();
            System.out.println("-----------------------------------");
            System.out.printf("| %-6s | %-25s |%n", "ID", "CATEGORY NAME");
            System.out.println("-----------------------------------");
            for (Category c : categories) System.out.println(c);
            System.out.println("-----------------------------------");

            int categoryId = ConsoleHelper.promptForInt("Enter the category ID to see its products");
            List<Product> productsInCategory = getProductsByCategory(categoryId);
            System.out.println("--------------------------------------------------------------");
            System.out.printf("| %-6s | %-25s | %-10s | %-10s |%n",
                    "ID", "PRODUCT NAME", "PRICE", "STOCK");
            System.out.println("--------------------------------------------------------------");
            for (Product p : productsInCategory) System.out.println(p);
            System.out.println("--------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving categories or products: " + e.getMessage());
        }
    }

    public static List<Product> getProducts() throws SQLException, ClassNotFoundException {
        List<Product> products = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
        try (Connection connection = DriverManager.getConnection(
                databaseUrl,
                username,
                password);
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                products.add(new Product(
                                results.getInt("ProductID"),
                                results.getString("ProductName"),
                                results.getDouble("UnitPrice"),
                                results.getInt("UnitsInStock")
                        )
                );
            }
            return products;
        }
    }

    public static List<Customer> getCustomers() throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT ContactName, CompanyName, City, "
                + "Country, Phone FROM customers ORDER BY Country";

        try (Connection connection = DriverManager.getConnection(
                databaseUrl,
                username,
                password);
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            while (results.next()) {
                customers.add(new Customer(
                        results.getString("ContactName"),
                        results.getString("CompanyName"),
                        results.getString("City"),
                        results.getString("Country"),
                        results.getString("Phone")
                ));

            }
            return customers;
        }
    }

    public static List<Category> getCategories() throws SQLException, ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                categories.add(new Category(
                        result.getInt("CategoryID"),
                        result.getString("CategoryName")
                ));
            }
        }
        return categories;
    }

    public static List<Product> getProductsByCategory(int categoryId) throws SQLException, ClassNotFoundException {
        List<Product> products = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products WHERE CategoryID = ?";

        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    products.add(new Product(
                            result.getInt("ProductID"),
                            result.getString("ProductName"),
                            result.getDouble("UnitPrice"),
                            result.getInt("UnitsInStock")
                    ));
                }
            }
        }
        return products;
    }




    }
