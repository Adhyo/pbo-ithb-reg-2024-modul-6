package Model;

import Database.DatabaseConnection;

import java.sql.*;
import java.util.Date;

public class PendudukKueri {
    public boolean insert(Penduduk penduduk) {
        String sql = "INSERT INTO penduduk VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(pstmt, penduduk);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Penduduk penduduk) {
        String sql = "UPDATE penduduk SET nama=?, tempat_lahir=?, tanggal_lahir=?, jenis_kelamin=?, " +
                    "gol_darah=?, alamat=?, rt_rw=?, kel_desa=?, kecamatan=?, agama=?, " +
                    "status_perkawinan=?, pekerjaan=?, kewarganegaraan=?, foto=?, tanda_tangan=?, " +
                    "berlaku_hingga=?, kota_pembuatan=?, tanggal_pembuatan=? WHERE nik=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setStatementParameters(pstmt, penduduk);
            pstmt.setString(19, penduduk.getNik());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(String nik) {
        String sql = "DELETE FROM penduduk WHERE nik=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nik);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Penduduk findByNik(String nik) {
        String sql = "SELECT * FROM penduduk WHERE nik=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nik);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Penduduk penduduk = new Penduduk();
                penduduk.setNik(rs.getString("nik"));
                penduduk.setNama(rs.getString("nama"));
                penduduk.setTempatLahir(rs.getString("tempat_lahir"));
                penduduk.setTanggalLahir(new Date(rs.getDate("tanggal_lahir").getTime()));
                penduduk.setJenisKelamin(rs.getString("jenis_kelamin"));
                penduduk.setGolDarah(rs.getString("gol_darah"));
                penduduk.setAlamat(rs.getString("alamat"));
                penduduk.setRtRw(rs.getString("rt_rw"));
                penduduk.setKelDesa(rs.getString("kel_desa"));
                penduduk.setKecamatan(rs.getString("kecamatan"));
                penduduk.setAgama(rs.getString("agama"));
                penduduk.setStatusPerkawinan(rs.getString("status_perkawinan"));
                penduduk.setPekerjaan(rs.getString("pekerjaan"));
                penduduk.setKewarganegaraan(rs.getString("kewarganegaraan"));
                penduduk.setFoto(rs.getString("foto"));
                penduduk.setTandaTangan(rs.getString("tanda_tangan"));
                penduduk.setBerlakuHingga(rs.getString("berlaku_hingga"));
                penduduk.setKotaPembuatan(rs.getString("kota_pembuatan"));
                penduduk.setTanggalPembuatan(new Date(rs.getDate("tanggal_pembuatan").getTime()));
                return penduduk;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void setStatementParameters(PreparedStatement pstmt, Penduduk penduduk) throws SQLException {
        pstmt.setString(1, penduduk.getNik());
        pstmt.setString(2, penduduk.getNama());
        pstmt.setString(3, penduduk.getTempatLahir());
        pstmt.setDate(4, new java.sql.Date(penduduk.getTanggalLahir().getTime()));
        pstmt.setString(5, penduduk.getJenisKelamin());
        pstmt.setString(6, penduduk.getGolDarah());
        pstmt.setString(7, penduduk.getAlamat());
        pstmt.setString(8, penduduk.getRtRw());
        pstmt.setString(9, penduduk.getKelDesa());
        pstmt.setString(10, penduduk.getKecamatan());
        pstmt.setString(11, penduduk.getAgama());
        pstmt.setString(12, penduduk.getStatusPerkawinan());
        pstmt.setString(13, penduduk.getPekerjaan());
        pstmt.setString(14, penduduk.getKewarganegaraan());
        pstmt.setString(15, penduduk.getFoto());
        pstmt.setString(16, penduduk.getTandaTangan());
        pstmt.setString(17, penduduk.getBerlakuHingga());
        pstmt.setString(18, penduduk.getKotaPembuatan());
        pstmt.setDate(19, new java.sql.Date(penduduk.getTanggalPembuatan().getTime()));
    }
}