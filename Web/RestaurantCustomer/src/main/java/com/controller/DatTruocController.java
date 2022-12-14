package com.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DatTruocDAO;
import com.entity.Ban;

@WebServlet(urlPatterns = "/dat-truoc")
public class DatTruocController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DatTruocDAO dao = new DatTruocDAO();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Ban> listBan = dao.getBanDangCoSan();
		req.setAttribute("listBan", listBan);
		
		req.getRequestDispatcher("/views/dattruoc.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		boolean gioiTinh = Boolean.valueOf(req.getParameter("GioiTinh"));
		String hoTen = req.getParameter("hoTen");
		Date ngaySinh = Date.valueOf(req.getParameter("ngaySinh"));
		String soDienThoai = req.getParameter("soDienThoai");
		int maBan = Integer.valueOf(req.getParameter("soBan"));
		int soLuongNguoi = Integer.valueOf(req.getParameter("soLuongNguoi"));
			
		String thoiGianString = req.getParameter("ngayCheckIn") + " " + req.getParameter("thoiGianCheckIn");
		
		
		dao.insertDatTruoc(gioiTinh, hoTen, ngaySinh, soDienThoai, soLuongNguoi, thoiGianString, maBan);
		req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
}
