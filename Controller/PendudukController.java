package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.util.Date;

public class PendudukController {
    private Penduduk penduduk;
    private FormInputView inputView;
    private HasilKTPView hasilView;
    private PendudukKueri kueri;
    private MainMenuView mainMenuView;
    private FormInputView formInputView;
    private PencarianView pencarianView;

    public PendudukController() {
        kueri = new PendudukKueri();
        showMainMenu();
    }

    public void showMainMenu() {
        if (mainMenuView == null) {
            mainMenuView = new MainMenuView(this);
        }
        mainMenuView.setVisible(true);
    }

    public void showPerekaman() {
        formInputView = new FormInputView(this, false);
        mainMenuView.setVisible(false);
    }

    public void savePenduduk(String nik, String nama, String tempatLahir, Date tanggalLahir,
                            String jenisKelamin, String golDarah, String alamat, String rtRw,
                            String kelDesa, String kecamatan, String agama, String statusPerkawinan,
                            String pekerjaan, String kewarganegaraan, String foto, String tandaTangan,
                            String berlakuHingga, String kotaPembuatan, Date tanggalPembuatan) {
        Penduduk penduduk = new Penduduk();
        if (validateData(nik, nama, tempatLahir, tanggalLahir, jenisKelamin, golDarah, 
                        alamat, rtRw, kelDesa, kecamatan, agama, statusPerkawinan, 
                        pekerjaan, kewarganegaraan, foto, tandaTangan, berlakuHingga, 
                        kotaPembuatan, tanggalPembuatan)){
                            penduduk.setNik(nik);
                            penduduk.setNama(nama);
                            penduduk.setTempatLahir(tempatLahir);
                            penduduk.setTanggalLahir(tanggalLahir);
                            penduduk.setJenisKelamin(jenisKelamin);
                            penduduk.setGolDarah(golDarah);
                            penduduk.setAlamat(alamat);
                            penduduk.setRtRw(rtRw);
                            penduduk.setKelDesa(kelDesa);
                            penduduk.setKecamatan(kecamatan);
                            penduduk.setAgama(agama);
                            penduduk.setStatusPerkawinan(statusPerkawinan);
                            penduduk.setPekerjaan(pekerjaan);
                            penduduk.setKewarganegaraan(kewarganegaraan);
                            penduduk.setFoto(foto);
                            penduduk.setTandaTangan(tandaTangan);
                            penduduk.setBerlakuHingga(berlakuHingga);
                            penduduk.setKotaPembuatan(kotaPembuatan);
                            penduduk.setTanggalPembuatan(tanggalPembuatan);
                        }

        if (kueri.insert(penduduk)) {
            JOptionPane.showMessageDialog(formInputView, "Data saved successfully!");
            showHasilKTP(penduduk);
        } else {
            JOptionPane.showMessageDialog(formInputView, 
                "Failed to save data!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showPencarian() {
        pencarianView = new PencarianView(this);
        mainMenuView.setVisible(false);
    }

    public void searchPenduduk(String nik) {
        Penduduk penduduk = kueri.findByNik(nik);
        if (penduduk != null) {
            formInputView = new FormInputView(this, true);
            formInputView.setData(penduduk);
            pencarianView.dispose();
        } else {
            JOptionPane.showMessageDialog(pencarianView, 
                "Data not found for NIK: " + nik,
                "Not Found",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updatePenduduk(String nik, String nama, String tempatLahir, Date tanggalLahir,
                            String jenisKelamin, String golDarah, String alamat, String rtRw,
                            String kelDesa, String kecamatan, String agama, String statusPerkawinan,
                            String pekerjaan, String kewarganegaraan, String foto, String tandaTangan,
                            String berlakuHingga, String kotaPembuatan, Date tanggalPembuatan) {
        Penduduk penduduk = new Penduduk();
        if (validateData(nik, nama, tempatLahir, tanggalLahir, jenisKelamin, golDarah, 
                        alamat, rtRw, kelDesa, kecamatan, agama, statusPerkawinan, 
                        pekerjaan, kewarganegaraan, foto, tandaTangan, berlakuHingga, 
                        kotaPembuatan, tanggalPembuatan)){
                            penduduk.setNik(nik);
                            penduduk.setNama(nama);
                            penduduk.setTempatLahir(tempatLahir);
                            penduduk.setTanggalLahir(tanggalLahir);
                            penduduk.setJenisKelamin(jenisKelamin);
                            penduduk.setGolDarah(golDarah);
                            penduduk.setAlamat(alamat);
                            penduduk.setRtRw(rtRw);
                            penduduk.setKelDesa(kelDesa);
                            penduduk.setKecamatan(kecamatan);
                            penduduk.setAgama(agama);
                            penduduk.setStatusPerkawinan(statusPerkawinan);
                            penduduk.setPekerjaan(pekerjaan);
                            penduduk.setKewarganegaraan(kewarganegaraan);
                            penduduk.setFoto(foto);
                            penduduk.setTandaTangan(tandaTangan);
                            penduduk.setBerlakuHingga(berlakuHingga);
                            penduduk.setKotaPembuatan(kotaPembuatan);
                            penduduk.setTanggalPembuatan(tanggalPembuatan);
                        }
        if (kueri.update(penduduk)) {
            JOptionPane.showMessageDialog(formInputView, "Data updated successfully!");
            formInputView.dispose();
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(formInputView, 
                "Failed to update data!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deletePenduduk(String nik) {
        if (kueri.delete(nik)) {
            JOptionPane.showMessageDialog(formInputView, "Data deleted successfully!");
            formInputView.dispose();
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(formInputView, 
                "Failed to delete data!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showHasilKTP(Penduduk penduduk) {
        hasilView = new HasilKTPView(penduduk);
        formInputView.dispose();
    }



    private boolean validateData(String nik, String nama, String tempatLahir, Date tanggalLahir,
                               String jenisKelamin, String golDarah, String alamat, String rtRw,
                               String kelDesa, String kecamatan, String agama, String statusPerkawinan,
                               String pekerjaan, String kewarganegaraan, String foto, String tandaTangan,
                               String berlakuHingga, String kotaPembuatan, Date tanggalPembuatan) {
        
        if (nik.isEmpty() || nama.isEmpty() || tempatLahir.isEmpty() || tanggalLahir == null ||
            jenisKelamin == null || golDarah == null || alamat.isEmpty() || rtRw.isEmpty() ||
            kelDesa.isEmpty() || kecamatan.isEmpty() || agama == null || statusPerkawinan == null ||
            pekerjaan.isEmpty() || kewarganegaraan == null || foto.isEmpty() || tandaTangan.isEmpty() ||
            berlakuHingga.isEmpty() || kotaPembuatan.isEmpty() || tanggalPembuatan == null) {
            
            JOptionPane.showMessageDialog(inputView, "Semua field harus diisi!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}