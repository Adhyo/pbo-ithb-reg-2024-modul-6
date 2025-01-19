package View;

import Controller.PendudukController;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    private PendudukController controller;
    
    public MainMenuView(PendudukController controller) {
        this.controller = controller;
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("E-KTP System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton perekamanButton = new JButton("Perekaman");
        JButton pencarianButton = new JButton("Pencarian");
        JButton exitButton = new JButton("Exit");
        
        perekamanButton.addActionListener(e -> controller.showPerekaman());
        pencarianButton.addActionListener(e -> controller.showPencarian());
        exitButton.addActionListener(e -> System.exit(0));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(perekamanButton, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(pencarianButton, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(exitButton, gbc);
        
        add(mainPanel);
    }
}