package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JDialog {
    private JPanel registerPanel;
    private JTextField username;
    private JTextField adresa;
    private JTextField email;
    private JPasswordField password;
    private JButton close;
    private JButton signUp;

    public SignUp() {
        setTitle("Create new account");
        setContentPane(registerPanel);
        setSize(500, 500);
        setModal(true);

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Sign Up button clicked"); // Debug statement
                registerUser();
                setVisible(false);
                foodOrder FoodOrder = new foodOrder();
                FoodOrder.setVisible(true);
                dispose();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void registerUser() {
        System.out.println("registerUser method called"); // Debug statement

        DataBase database = new DataBase();

        String name = username.getText();
        String address = adresa.getText();
        String userEmail = email.getText();
        String userPassword = String.valueOf(password.getPassword());

        if (name.isEmpty() || address.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = database.addUserToDatabase(name, address, userEmail, userPassword);
        if (user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
}
