package com.EDISKASUMOVICipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JFrame {
    private JPanel mainPanel;
    private JLabel welcome;
    private JButton login;
    private JButton signup;

    public Start() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        pack();
        setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LogIn loginDialog = new LogIn();
                loginDialog.setVisible(true);
                dispose();
            }
        });

        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SignUp signUpDialog = new SignUp();
                signUpDialog.setVisible(true);
                dispose();
            }
        });
    }
}
