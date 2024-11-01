package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class LogIn extends JDialog {

        private JPanel loginPanel;
        private JTextField enteredEmail;
        private JPasswordField enteredPassword;
        private JButton close;
        private JButton login;

        private User user;

        public LogIn() {
            setTitle("Login");
            setContentPane(loginPanel);
            setSize(500,500);
            setModal(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            DataBase database = new DataBase();

            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = enteredEmail.getText();
                    String password = String.valueOf(enteredPassword.getPassword());

                    user = database.getAuthenticatedUser(email,password);

                        if ("admin".equals(email) && "admin".equals(password)) {
                            openAdminPanel();
                        } else if ("kuhinja".equals(email) && "kuhinja".equals(password)) {
                            openKitchenPanel();
                        } else {
                            user = database.getAuthenticatedUser(email, password);
                            if (user != null) {
                                openFoodOrderPanel();
                            } else {
                                JOptionPane.showMessageDialog(LogIn.this,
                                        "Email ili šifra nisu tačni.",
                                        "Pokušaj ponovo",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        dispose(); // Close login dialog
                }
            });

            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

        }

        private void openAdminPanel() {
            setVisible(false);
            adminPanel AdminPanel = new adminPanel();
            AdminPanel.setVisible(true);
            dispose();
        }

        private void openKitchenPanel() {
            setVisible(false);
            kitchenPanel KitchenPanel = new kitchenPanel();
            KitchenPanel.setVisible(true);
            dispose();
        }

        private void openFoodOrderPanel() {
            setVisible(false);
            foodOrder FoodOrder = new foodOrder();
            FoodOrder.setVisible(true);
            dispose();
        }

        public User getUser() {
            return user;
        }
    }
