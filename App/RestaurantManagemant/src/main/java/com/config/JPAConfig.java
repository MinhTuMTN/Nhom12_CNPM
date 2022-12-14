package com.config;

import com.dao.DAODatTruoc;
import com.dao.DAODonHang;
import com.entity.Ban;
import com.entity.CaTruc;
import com.entity.ChiTietDonHang;
import com.entity.ChiTietDonHangId;
import com.entity.DatTruoc;
import com.entity.DonHang;
import com.entity.KhachHang;
import com.entity.Luong;
import com.entity.LuongId;
import com.entity.MonAn;
import com.entity.NhanVien;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAConfig {
	static EntityManagerFactory factory;
	static EntityManager entityManager;
    
    static {
        factory = Persistence.createEntityManagerFactory("jpaSQL");
    }

	public static EntityManager getEntityManager() {	
		return factory.createEntityManager();
	}

	public static void main(String[] args) {
        getEntityManager();
        //List<DatTruoc> datTruocs =  new DAODatTruoc().getAllDatTruocChoXacNhan();
		factory.close();
	}
}
