package com.EDISKASUMOVICipia.example;

import com.EDISKASUMOVICipia.example.Data.DataBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class adminPanel extends JDialog {
    private JPanel adminPanel;
    private JTextField foodName;
    private JTextField foodPrice;
    private JButton addFoodButton;
    private JButton editFoodButton;
    private JButton deleteFoodButton;
    private JTable foodTable;
    private String selectedFoodName;

    public adminPanel() {
        setTitle("Admin Panel");
        setContentPane(adminPanel);
        setSize(500, 500);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        displayTable();

        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFoodToDatabase(foodName.getText(), foodPrice.getText());
                displayTable();
            }
        });

        deleteFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = foodTable.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel tblModel = (DefaultTableModel) foodTable.getModel();
                    String foodNameToDelete = (String) tblModel.getValueAt(selectedRow, 0);
                    deleteFoodFromDatabase(foodNameToDelete);
                    displayTable();
                } else {
                    System.out.println("Ni jedan red nije odabran");
                }
            }
        });

        foodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = foodTable.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel tblModel = (DefaultTableModel) foodTable.getModel();
                    selectedFoodName = (String) tblModel.getValueAt(selectedRow, 0);
                    String selectedFoodPrice = (String) tblModel.getValueAt(selectedRow, 1);

                    foodName.setText(selectedFoodName);
                    foodPrice.setText(selectedFoodPrice);
                }
            }
        });

        editFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFoodName != null) {
                    updateFoodInDatabase(selectedFoodName, foodName.getText(), foodPrice.getText());
                    displayTable();
                } else {
                    System.out.println("Ni jedna hrana nije odabrana za uređivanje");
                }
            }
        });
    }

    private void addFoodToDatabase(String foodName, String foodPrice) {
        DataBase dbHelper = new DataBase();
        dbHelper.addFood(foodName, foodPrice);
    }

    private void deleteFoodFromDatabase(String foodName) {
        DataBase dbHelper = new DataBase();
        dbHelper.deleteFood(foodName);
    }

    private void updateFoodInDatabase(String oldFoodName, String newFoodName, String newFoodPrice) {
        DataBase dbHelper = new DataBase();
        dbHelper.updateFood(oldFoodName, newFoodName, newFoodPrice);
    }

    private void displayTable() {
        DataBase dbHelper = new DataBase();
        List<String[]> foodData = dbHelper.getAllFood();

        String[] columnNames = {"Naziv Hrane", "Cijena"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (String[] row : foodData) {
            tableModel.addRow(row);
        }

        foodTable.setModel(tableModel);
    }
}
