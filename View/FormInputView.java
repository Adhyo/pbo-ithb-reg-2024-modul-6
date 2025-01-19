package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.PendudukController;
import Model.Penduduk;

public class FormInputView extends JFrame {
    private PendudukController controller;
    private JTextField nikField, namaField, tempatLahirField, alamatField, rtRwField;
    private JTextField kelDesaField, kecamatanField, berlakuHinggaField, kotaPembuatanField;
    private JComboBox<Integer> tanggalLahirDay, tanggalLahirMonth, tanggalLahirYear;
    private JComboBox<Integer> tanggalPembuatanDay, tanggalPembuatanMonth, tanggalPembuatanYear;
    private ButtonGroup jenisKelaminGroup, golDarahGroup, kewarganegaraanGroup;
    private JComboBox<String> agamaCombo, statusPerkawinanCombo;
    private ArrayList<JCheckBox> pekerjaanChecks;
    private JTextField wnaField;
    private JTextField fotoField, tandaTanganField;
    private boolean isUpdateMode = false;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private String currentNik;

    public FormInputView(PendudukController controller, boolean isUpdateMode) {
        this.controller = controller;
        this.isUpdateMode = isUpdateMode;
        initComponents();
        this.setVisible(true);
    }

    public void setData(Penduduk penduduk) {
        currentNik = penduduk.getNik();
        nikField.setText(penduduk.getNik());
        nikField.setEditable(!isUpdateMode);
        namaField.setText(penduduk.getNama());
        tempatLahirField.setText(penduduk.getTempatLahir());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(penduduk.getTanggalLahir());
        tanggalLahirDay.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        tanggalLahirMonth.setSelectedItem(cal.get(Calendar.MONTH) + 1);
        tanggalLahirYear.setSelectedItem(cal.get(Calendar.YEAR));
        
        if (isUpdateMode) {
            insertButton.setVisible(false);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
        } else {
            insertButton.setVisible(true);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void initComponents() {
        setTitle("Form Input Data Penduduk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        nikField = new JTextField(20);
        namaField = new JTextField(20);
        tempatLahirField = new JTextField(20);
        alamatField = new JTextField(20);
        rtRwField = new JTextField(20);
        kelDesaField = new JTextField(20);
        kecamatanField = new JTextField(20);
        berlakuHinggaField = new JTextField(20);
        kotaPembuatanField = new JTextField(20);

        initializeDateComponents();

        jenisKelaminGroup = new ButtonGroup();
        JRadioButton priaRadio = new JRadioButton("Pria");
        JRadioButton wanitaRadio = new JRadioButton("Wanita");
        jenisKelaminGroup.add(priaRadio);
        jenisKelaminGroup.add(wanitaRadio);

        golDarahGroup = new ButtonGroup();
        JRadioButton golA = new JRadioButton("A");
        JRadioButton golB = new JRadioButton("B");
        JRadioButton golO = new JRadioButton("O");
        JRadioButton golAB = new JRadioButton("AB");
        golDarahGroup.add(golA);
        golDarahGroup.add(golB);
        golDarahGroup.add(golO);
        golDarahGroup.add(golAB);

        String[] agama = {"Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu"};
        agamaCombo = new JComboBox<>(agama);

        String[] statusPerkawinan = {"Belum Menikah", "Menikah", "Janda/Duda"};
        statusPerkawinanCombo = new JComboBox<>(statusPerkawinan);

        pekerjaanChecks = new ArrayList<>();
        JCheckBox karyawanCheck = new JCheckBox("Karyawan Swasta");
        JCheckBox pnsCheck = new JCheckBox("PNS");
        JCheckBox wiraswastaCheck = new JCheckBox("Wiraswasta");
        JCheckBox akademisiCheck = new JCheckBox("Akademisi");
        JCheckBox pengangguranCheck = new JCheckBox("Pengangguran");
        pekerjaanChecks.add(karyawanCheck);
        pekerjaanChecks.add(pnsCheck);
        pekerjaanChecks.add(wiraswastaCheck);
        pekerjaanChecks.add(akademisiCheck);
        pekerjaanChecks.add(pengangguranCheck);

        pengangguranCheck.addActionListener(e -> {
            boolean isSelected = pengangguranCheck.isSelected();
            pekerjaanChecks.forEach(check -> {
                if (check != pengangguranCheck) {
                    check.setEnabled(!isSelected);
                    if (isSelected) check.setSelected(false);
                }
            });
        });

        kewarganegaraanGroup = new ButtonGroup();
        JRadioButton wniRadio = new JRadioButton("WNI");
        JRadioButton wnaRadio = new JRadioButton("WNA");
        kewarganegaraanGroup.add(wniRadio);
        kewarganegaraanGroup.add(wnaRadio);

        wnaField = new JTextField(20);
        wnaField.setVisible(false);

        wnaRadio.addActionListener(e -> wnaField.setVisible(true));
        wniRadio.addActionListener(e -> wnaField.setVisible(false));

        fotoField = new JTextField(20);
        tandaTanganField = new JTextField(20);

        JButton fotoChooser = new JButton("Pilih Foto");
        JButton ttdChooser = new JButton("Pilih Tanda Tangan");

        fotoChooser.addActionListener(e -> chooseFile(fotoField));
        ttdChooser.addActionListener(e -> chooseFile(tandaTanganField));

        insertButton = new JButton("Insert Data");
        updateButton = new JButton("Update Data");
        deleteButton = new JButton("Delete Data");
            
        insertButton.addActionListener(e -> saveData(false));
        updateButton.addActionListener(e -> saveData(true));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this record?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePenduduk(currentNik);
            }
        });
                    
        int row = 0;
        
        addFormField(mainPanel, gbc, "NIK:", nikField, row++);
        addFormField(mainPanel, gbc, "Nama:", namaField, row++);
        addFormField(mainPanel, gbc, "Tempat Lahir:", tempatLahirField, row++);
        
        JPanel tanggalLahirPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tanggalLahirPanel.add(tanggalLahirDay);
        tanggalLahirPanel.add(new JLabel("/"));
        tanggalLahirPanel.add(tanggalLahirMonth);
        tanggalLahirPanel.add(new JLabel("/"));
        tanggalLahirPanel.add(tanggalLahirYear);
        addFormField(mainPanel, gbc, "Tanggal Lahir:", tanggalLahirPanel, row++);
                    
        JPanel jenisKelaminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jenisKelaminPanel.add(priaRadio);
        jenisKelaminPanel.add(wanitaRadio);
        addFormField(mainPanel, gbc, "Jenis Kelamin:", jenisKelaminPanel, row++);

        JPanel golDarahPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        golDarahPanel.add(golA);
        golDarahPanel.add(golB);
        golDarahPanel.add(golO);
        golDarahPanel.add(golAB);
        addFormField(mainPanel, gbc, "Golongan Darah:", golDarahPanel, row++);

        addFormField(mainPanel, gbc, "Alamat:", alamatField, row++);
        addFormField(mainPanel, gbc, "RT/RW:", rtRwField, row++);
        addFormField(mainPanel, gbc, "Kel/Desa:", kelDesaField, row++);
        addFormField(mainPanel, gbc, "Kecamatan:", kecamatanField, row++);
        addFormField(mainPanel, gbc, "Agama:", agamaCombo, row++);
        addFormField(mainPanel, gbc, "Status Perkawinan:", statusPerkawinanCombo, row++);

        JPanel pekerjaanPanel = new JPanel(new GridLayout(0, 1));
        pekerjaanChecks.forEach(pekerjaanPanel::add);
        addFormField(mainPanel, gbc, "Pekerjaan:", pekerjaanPanel, row++);

        JPanel kewarganegaraanPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kewarganegaraanPanel.add(wniRadio);
        kewarganegaraanPanel.add(wnaRadio);
        kewarganegaraanPanel.add(wnaField);
        addFormField(mainPanel, gbc, "Kewarganegaraan:", kewarganegaraanPanel, row++);

        JPanel fotoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fotoPanel.add(fotoField);
        fotoPanel.add(fotoChooser);
        addFormField(mainPanel, gbc, "Foto:", fotoPanel, row++);

        JPanel ttdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ttdPanel.add(tandaTanganField);
        ttdPanel.add(ttdChooser);
        addFormField(mainPanel, gbc, "Tanda Tangan:", ttdPanel, row++);

        addFormField(mainPanel, gbc, "Berlaku Hingga:", berlakuHinggaField, row++);
        addFormField(mainPanel, gbc, "Kota Pembuatan:", kotaPembuatanField, row++);
        
        JPanel tanggalPembuatanPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tanggalPembuatanPanel.add(tanggalPembuatanDay);
        tanggalPembuatanPanel.add(new JLabel("/"));
        tanggalPembuatanPanel.add(tanggalPembuatanMonth);
        tanggalPembuatanPanel.add(new JLabel("/"));
        tanggalPembuatanPanel.add(tanggalPembuatanYear);
        addFormField(mainPanel, gbc, "Tanggal Pembuatan:", tanggalPembuatanPanel, row++);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        mainPanel.add(insertButton, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        
        updateButton.setVisible(false);
        deleteButton.setVisible(false);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
    }

    private void saveData(boolean isUpdate) {
        String jenisKelamin = getSelectedButtonText(jenisKelaminGroup);
            String golDarah = getSelectedButtonText(golDarahGroup);
            
            StringBuilder pekerjaan = new StringBuilder();
            for (JCheckBox check : pekerjaanChecks) {
                if (check.isSelected()) {
                    if (pekerjaan.length() > 0) pekerjaan.append(", ");
                    pekerjaan.append(check.getText());
                }
            }

            String kewarganegaraan = getSelectedButtonText(kewarganegaraanGroup);
            if (kewarganegaraan.equals("WNA")) {
                kewarganegaraan = "WNA (" + wnaField.getText() + ")";
            }

        if (isUpdate) {
            controller.updatePenduduk(
                nikField.getText(),
                namaField.getText(),
                tempatLahirField.getText(),
                getDateFromComboBoxes(tanggalLahirDay, tanggalLahirMonth, tanggalLahirYear),
                jenisKelamin,
                golDarah,
                alamatField.getText(),
                rtRwField.getText(),
                kelDesaField.getText(),
                kecamatanField.getText(),
                (String) agamaCombo.getSelectedItem(),
                (String) statusPerkawinanCombo.getSelectedItem(),
                pekerjaan.toString(),
                kewarganegaraan,
                fotoField.getText(),
                tandaTanganField.getText(),
                berlakuHinggaField.getText(),
                kotaPembuatanField.getText(),
                getDateFromComboBoxes(tanggalPembuatanDay, tanggalPembuatanMonth, tanggalPembuatanYear)
            );
            controller.showMainMenu();
        } else {
            controller.savePenduduk(
                nikField.getText(),
                namaField.getText(),
                tempatLahirField.getText(),
                getDateFromComboBoxes(tanggalLahirDay, tanggalLahirMonth, tanggalLahirYear),
                jenisKelamin,
                golDarah,
                alamatField.getText(),
                rtRwField.getText(),
                kelDesaField.getText(),
                kecamatanField.getText(),
                (String) agamaCombo.getSelectedItem(),
                (String) statusPerkawinanCombo.getSelectedItem(),
                pekerjaan.toString(),
                kewarganegaraan,
                fotoField.getText(),
                tandaTanganField.getText(),
                berlakuHinggaField.getText(),
                kotaPembuatanField.getText(),
                getDateFromComboBoxes(tanggalPembuatanDay, tanggalPembuatanMonth, tanggalPembuatanYear)
            );
            controller.showMainMenu();
        }
    }

    private void initializeDateComponents() {
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        
        Integer[] months = new Integer[12];
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1;
        }
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }

        tanggalLahirDay = new JComboBox<>(days);
        tanggalLahirMonth = new JComboBox<>(months);
        tanggalLahirYear = new JComboBox<>(years);

        tanggalPembuatanDay = new JComboBox<>(days);
        tanggalPembuatanMonth = new JComboBox<>(months);
        tanggalPembuatanYear = new JComboBox<>(years);
    }

    private Date getDateFromComboBoxes(JComboBox<Integer> dayBox, JComboBox<Integer> monthBox, JComboBox<Integer> yearBox) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, (Integer) yearBox.getSelectedItem());
        cal.set(Calendar.MONTH, (Integer) monthBox.getSelectedItem() - 1);
        cal.set(Calendar.DAY_OF_MONTH, (Integer) dayBox.getSelectedItem());
        return cal.getTime();
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, Component component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(component, gbc);
        gbc.gridwidth = 1;
    }

    private void chooseFile(JTextField field) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif");
        chooser.setFileFilter(filter);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
