package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class foodOrder extends JDialog {
    private JPanel foodOrder;
    private JTable allFoodTable;
    private JTable foodBasketTable;
    private JButton addToBasketButton;
    private JButton deleteFromBasketButton;
    private JButton submitOrderButton;
    private JLabel totalPriceLabel;

    private DefaultTableModel allFoodTableModel;
    private DefaultTableModel foodBasketTableModel;
    private DataBase dataBase;

    public foodOrder() {
        dataBase = new DataBase();

        setTitle("Naruči hranu");
        setContentPane(foodOrder);
        setSize(500, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] basketColumnNames = {"Naziv hrane", "Cijena hrane"};
        foodBasketTableModel = new DefaultTableModel(basketColumnNames, 0);

        displayTable();

        addToBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToBasket();
                updateTotalPriceLabel();
            }
        });

        deleteFromBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedItemsFromBasket();
                updateTotalPriceLabel();
            }
        });

        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User authenticatedUser = User.getCurrentUser();
                if (authenticatedUser != null) {
                    placeOrder(authenticatedUser);
                } else {
                    JOptionPane.showMessageDialog(foodOrder.this, "Korisnik nije pronađen, pokušaj ponovo");
                }
            }
        });

    }


    private void displayTable() {
        List<String[]> foodData = dataBase.getAllFood();

        String[] columnNames = {"Naziv hrane", "Cijena hrane"};
        allFoodTableModel = new DefaultTableModel(columnNames, 0);

        for (String[] row : foodData) {
            allFoodTableModel.addRow(row);
        }

        allFoodTable.setModel(allFoodTableModel);
    }

    private void displayFoodBasketTable() {
        foodBasketTable.setModel(foodBasketTableModel);
    }

    private void addToBasket() {
        int selectedRow = allFoodTable.getSelectedRow();
        if (selectedRow != -1) {
            String foodName = allFoodTableModel.getValueAt(selectedRow, 0).toString();
            String foodPrice = allFoodTableModel.getValueAt(selectedRow, 1).toString();
            foodBasketTableModel.addRow(new Object[]{foodName, foodPrice});

            displayFoodBasketTable();
        } else {
            JOptionPane.showMessageDialog(this, "Odaberite jelo koje zelite da dodate u korpu");
        }
    }

    private void removeSelectedItemsFromBasket() {
        int[] selectedRows = foodBasketTable.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                foodBasketTableModel.removeRow(selectedRows[i]);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Odaberite hranu koju želite ukloniti iz korpe");
        }
    }

    private void updateTotalPriceLabel() {
        double totalPrice = 0.0;
        for (int i = 0; i < foodBasketTableModel.getRowCount(); i++) {
            String priceStr = foodBasketTableModel.getValueAt(i, 1).toString();
            double price = Double.parseDouble(priceStr);
            totalPrice += price;
        }
        totalPriceLabel.setText(String.format("Ukupna cijena: %.2f", totalPrice));
    }


    public void placeOrder(User authenticatedUser) {
        DefaultTableModel model = (DefaultTableModel) foodBasketTable.getModel();
        int rowCount = model.getRowCount();

        String userName = authenticatedUser.getName(); // Assuming 'getName()' returns the user's name

        for (int i = 0; i < rowCount; i++) {
            String foodName = model.getValueAt(i, 0).toString();
            double foodPrice = Double.parseDouble(model.getValueAt(i, 1).toString());
            dataBase.saveOrder(userName, foodName, foodPrice);
        }

        JOptionPane.showMessageDialog(this, "Narudžba uspješno poslana");

        model.setRowCount(0);
        updateTotalPriceLabel();
    }
}