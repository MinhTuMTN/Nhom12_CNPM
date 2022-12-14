package com.dao;

import com.config.JPAConfig;
import com.entity.NhanVien;
import com.entity.TaiKhoan;
import javax.persistence.EntityManager;

public class DAOTaiKhoan {

    public Boolean checkTaiKhoan(String username, String password) {
            EntityManager entityManager = JPAConfig.getEntityManager();
            TaiKhoan taiKhoan = entityManager.find(TaiKhoan.class, username);
            entityManager.close();
            return taiKhoan != null
                    && taiKhoan.getMatKhau().equals(password)
                    && taiKhoan.getTrangThaiTaiKhoan().equals("Còn hoạt động");
    }

    public NhanVien getNhanVienByUserName(String username) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        NhanVien nhanVien = entityManager.find(TaiKhoan.class, username).getNhanVien();
        entityManager.close();
        return nhanVien;
    }
}
