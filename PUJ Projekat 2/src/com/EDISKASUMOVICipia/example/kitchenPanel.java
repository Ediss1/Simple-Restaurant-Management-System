package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class kitchenPanel extends JDialog{
    private JPanel kuhinja;
    private JTable ordersTable;
    private JButton updateOrdersTable;
    private DataBase dataBase;

    public kitchenPanel() {

        setTitle("Login");
        setContentPane(kuhinja);
        setSize(500,500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dataBase = new DataBase();
        updateOrdersTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrdersTable();
            }
        });
    }

    private void updateOrdersTable() {
        List<String[]> orderData = dataBase.getAllOrders();

        String[] columnNames = {"Ime korisnika", "Naziv hrane", "Cijena hrane", "Vrijeme narudžbe"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (String[] row : orderData) {
            tableModel.addRow(row);
        }

        ordersTable.setModel(tableModel);
    }
}
