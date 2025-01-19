package View;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

import Controller.PendudukController;
import Model.Penduduk;
                    
public class HasilKTPView extends JFrame {
    private Penduduk model;
    private PendudukController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public HasilKTPView(Penduduk model) {
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        setTitle("E-KTP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("REPUBLIK HARAPAN BANGSA");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addField(contentPanel, gbc, "NIK", model.getNik(), row++);
        addField(contentPanel, gbc, "Nama", model.getNama(), row++);
        addField(contentPanel, gbc, "Tempat/Tgl Lahir", 
                model.getTempatLahir() + ", " + dateFormat.format(model.getTanggalLahir()), row++);
        addField(contentPanel, gbc, "Jenis Kelamin", model.getJenisKelamin(), row++);
        addField(contentPanel, gbc, "Gol. Darah", model.getGolDarah(), row++);
        addField(contentPanel, gbc, "Alamat", model.getAlamat(), row++);
        addField(contentPanel, gbc, "RT/RW", model.getRtRw(), row++);
        addField(contentPanel, gbc, "Kel/Desa", model.getKelDesa(), row++);
        addField(contentPanel, gbc, "Kecamatan", model.getKecamatan(), row++);
        addField(contentPanel, gbc, "Agama", model.getAgama(), row++);
        addField(contentPanel, gbc, "Status Perkawinan", model.getStatusPerkawinan(), row++);
        addField(contentPanel, gbc, "Pekerjaan", model.getPekerjaan(), row++);
        addField(contentPanel, gbc, "Kewarganegaraan", model.getKewarganegaraan(), row++);
        addField(contentPanel, gbc, "Berlaku Hingga", model.getBerlakuHingga(), row++);

        JPanel photoPanel = new JPanel();
        if (!model.getFoto().isEmpty()) {
            ImageIcon photoIcon = new ImageIcon(model.getFoto());
            Image photo = photoIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH);
            photoPanel.add(new JLabel(new ImageIcon(photo)));
        }

        JPanel signaturePanel = new JPanel();
        if (!model.getTandaTangan().isEmpty()) {
            ImageIcon signIcon = new ImageIcon(model.getTandaTangan());
            Image signature = signIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            signaturePanel.add(new JLabel(new ImageIcon(signature)));
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel(model.getKotaPembuatan() + ", " + 
                      dateFormat.format(model.getTanggalPembuatan())), BorderLayout.WEST);
        bottomPanel.add(signaturePanel, BorderLayout.EAST);

        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(contentPanel);
        mainPanel.add(photoPanel);
        mainPanel.add(bottomPanel);

        add(new JScrollPane(mainPanel));

        JButton mainMenuButton = new JButton("Back to Main Menu");
        mainMenuButton.addActionListener(e -> {
            dispose();
            controller.showMainMenu();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(mainMenuButton);
        mainPanel.add(buttonPanel);

        add(new JScrollPane(mainPanel));
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridx = 1;
        panel.add(new JLabel(value), gbc);
    }
}

