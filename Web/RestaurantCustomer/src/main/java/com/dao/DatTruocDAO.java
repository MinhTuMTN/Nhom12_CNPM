package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.connection.DBConnection;
import com.entity.Ban;


public class DatTruocDAO extends DBConnection{
	
	public List<Ban> getBanDangCoSan(){
		List<Ban> listBan = new ArrayList<Ban>();
		String sql = "SELECT * FROM Ban WHERE trangThaiBan=?";
		try {
			Connection connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "Đang có sẵn");

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ban ban = new Ban(resultSet.getInt("maBan"),
									resultSet.getString("loaiBan"),
									resultSet.getInt("soLuongGheToiDa"),
									resultSet.getString("trangThaiBan"));
				listBan.add(ban);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listBan;
	}
	
	public int insertKhachHang(boolean gioiTinh, String hoTen, Date ngaySinh, String soDienThoai) {
		String sql = "INSERT INTO dbo.KhachHang(gioiTinh, hoTen, ngaySinh, soDienThoai)\r\n"
				+ "		VALUES(?, ?, ?, ?)";
		try {
			Connection connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBoolean(1, gioiTinh);
			statement.setString(2, hoTen);
			statement.setDate(3, ngaySinh);
			statement.setString(4, soDienThoai);

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM dbo.KhachHang\r\n"
				+ "WHERE gioiTinh=? AND hoTen=? AND ngaySinh=? AND soDienThoai=?\r\n"
				+ "ORDER BY maKhachHang DESC";
		int maKhachHang = 0;
		try {
			Connection connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBoolean(1, gioiTinh);
			statement.setString(2, hoTen);
			statement.setDate(3, ngaySinh);
			statement.setString(4, soDienThoai);
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				maKhachHang = resultSet.getInt("maKhachHang");
				return maKhachHang;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maKhachHang;
	}

	public void insertDatTruoc(boolean gioiTinh, String hoTen, Date ngaySinh, String soDienThoai, int soLuongNguoi, String thoiGianCheckIn, int maBan) {
		int maKhachHang = insertKhachHang(gioiTinh, hoTen, ngaySinh, soDienThoai);
		
		String sql = "INSERT INTO dbo.DatTruoc(soLuongNguoi,thoiGianCheckIn,thoiGianDatTruoc,trangThaiDatTruoc,ban_maBan,khachHang_maKhachHang)\r\n"
				+ "VALUES(?, ?, GETDATE(), N'Chờ xác nhận', ?, ?)";
		try {
			Connection connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, soLuongNguoi);
			statement.setString(2, thoiGianCheckIn);
			//statement.setDate(3, new Date(new java.util.Date().getTime()));
			statement.setInt(3, maBan);
			statement.setInt(4, maKhachHang);

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			sql = "UPDATE dbo.Ban SET trangThaiBan=N'Đã đặt trước' WHERE maBan=?";
			Connection connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, maBan);

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	}
}
