package com.EDISKASUMOVICipia.example.Data;

import com.EDISKASUMOVICipia.example.Food;
import com.EDISKASUMOVICipia.example.Order;
import com.EDISKASUMOVICipia.example.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static Connection connection = null;
    private static final String CONN_STRING = "jdbc:mysql://localhost:3308/puj2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String GET_ALL_FOOD_SQL = "SELECT * FROM food";
    private static final String ADD_USER_SQL = "INSERT INTO users (ime, adresa, email, password) VALUES (?, ?, ?, ?)";

    private static void connect() {
        if (connection != null) {
            return;
        } else {
            try {
                connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User addUserToDatabase(String name, String address, String userEmail, String userPassword) {
        User user = null;

        connect();


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, userEmail);
            preparedStatement.setString(4, userPassword);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user = new User();
                    user.name = name;
                    user.address = address;
                    user.userEmail = userEmail;
                    user.userPassword = userPassword;
                }
                generatedKeys.close();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            System.out.println("User registration successful: " + user.name);
        } else {
            System.out.println("User registration failed");
        }

        close();
        return user;
    }

    public User getAuthenticatedUser(String email, String password) {
        User user = null;

        connect();

        try {
            Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("ime");
                user.address = resultSet.getString("adresa");
                user.userEmail = resultSet.getString("email");
                user.userPassword = resultSet.getString("password");

                System.out.println("Uspješna prijava korisnika: " + user.name);
                System.out.println("Adresa: " + user.address);
                System.out.println("Email: " + user.userEmail);

                User.setCurrentUser(user);
            } else {
                System.out.println("Prijava otkazana");
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<String[]> getAllFood() {
        List<String[]> foodData = new ArrayList<>();
        connect();

        try {
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_FOOD_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String foodName = rs.getString("foodName");
                String foodPrice = rs.getString("foodPrice");
                foodData.add(new String[]{foodName, foodPrice});
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return foodData;
    }

    public List<String[]> getAllOrders() {
        List<String[]> orderData = new ArrayList<>();
        connect();

        String GET_ALL_ORDERS_SQL = "SELECT userName, foodName, foodPrice, ColumnDateTime FROM orders";

        try {
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_ORDERS_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String foodName = rs.getString("foodName");
                double foodPrice = rs.getDouble("foodPrice");
                Timestamp orderTime = rs.getTimestamp("ColumnDateTime");
                orderData.add(new String[]{userName, foodName, String.valueOf(foodPrice), orderTime.toString()});
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return orderData;
    }

    public void addFood(String foodName, String foodPrice) {
        connect();
        String ADD_FOOD_SQL = "INSERT INTO food (foodName, foodPrice) VALUES (?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(ADD_FOOD_SQL);
            stmt.setString(1, foodName);
            stmt.setString(2, foodPrice);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }



    public void deleteFood(String foodName) {
        connect();
        String DELETE_FOOD_SQL = "DELETE FROM food WHERE foodName = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(DELETE_FOOD_SQL);
            stmt.setString(1, foodName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    public void updateFood(String oldFoodName, String newFoodName, String newFoodPrice) {
        connect();
        String UPDATE_FOOD_SQL = "UPDATE food SET foodName = ?, foodPrice = ? WHERE foodName = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_FOOD_SQL);
            stmt.setString(1, newFoodName);
            stmt.setString(2, newFoodPrice);
            stmt.setString(3, oldFoodName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    public void saveOrder(String userName, String foodName, double foodPrice) {
        connect();
        String SAVE_ORDER_SQL = "INSERT INTO orders (userName, foodName, foodPrice) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(SAVE_ORDER_SQL);
            stmt.setString(1, userName);
            stmt.setString(2, foodName);
            stmt.setDouble(3, foodPrice);
            stmt.executeUpdate();
            stmt.close();

            System.out.println("Sačuvano");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }


}
