package View;

import Controller.PendudukController;

import javax.swing.*;
import java.awt.*;

public class PencarianView extends JFrame {
    private PendudukController controller;
    private JTextField nikField;
    
    public PencarianView(PendudukController controller) {
        this.controller = controller;
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Pencarian E-KTP");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        nikField = new JTextField(20);
        JButton cariButton = new JButton("Cari");
        JButton backButton = new JButton("Kembali");
        
        cariButton.addActionListener(e -> {
            String nik = nikField.getText().trim();
            if (!nik.isEmpty()) {
                controller.searchPenduduk(nik);
            } else {
                JOptionPane.showMessageDialog(this, "NIK tidak boleh kosong!");
            }
        });
        
        backButton.addActionListener(e -> {
            dispose();
            controller.showMainMenu();
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("NIK:"), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(nikField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(cariButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(backButton, gbc);
        
        add(mainPanel);
    }
}

