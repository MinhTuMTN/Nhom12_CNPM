package com.entity;

public class Ban {
	private Integer maBan;
	private String loaiBan;
	private int soLuongGheToiDa;
	private String trangThaiBan;
	
	public Integer getMaBan() {
		return maBan;
	}
	public void setMaBan(Integer maBan) {
		this.maBan = maBan;
	}
	public String getLoaiBan() {
		return loaiBan;
	}
	public void setLoaiBan(String loaiBan) {
		this.loaiBan = loaiBan;
	}
	
	public int getSoLuongGheToiDa() {
		return soLuongGheToiDa;
	}
	public void setSoLuongGheToiDa(int soLuongGheToiDa) {
		this.soLuongGheToiDa = soLuongGheToiDa;
	}
	public String getTrangThaiBan() {
		return trangThaiBan;
	}
	public void setTrangThaiBan(String trangThaiBan) {
		this.trangThaiBan = trangThaiBan;
	}
	public Ban(Integer maBan, String loaiBan, int soLuongGheToiDa, String trangThaiBan) {
		super();
		this.maBan = maBan;
		this.loaiBan = loaiBan;
		this.soLuongGheToiDa = soLuongGheToiDa;
		this.trangThaiBan = trangThaiBan;
	}
	public Ban() {
		// TODO Auto-generated constructor stub
	}
}
