package com.dao;

import com.config.JPAConfig;
import com.entity.Ban;
import com.entity.ChiTietDonHang;
import com.entity.ChiTietDonHangId;
import com.entity.Coupon;
import com.entity.DatTruoc;
import com.entity.DonHang;
import com.entity.KhachHang;
import com.entity.MonAn;
import com.entity.NhanVien;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DAODonHang {

    public DonHang findDonHangById(Integer id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        DonHang donHang = entityManager.find(DonHang.class, id);
        entityManager.close();
        return donHang;
    }

    public List<DonHang> getDanhSachDonHangDauBep(Integer maDauBep) {
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT d FROM DonHang d WHERE d.dauBep.maNhanVien = :maDauBep AND d.trangThaiDonHang = :trangThaiDonHang";
        TypedQuery<DonHang> query = enma.createQuery(jpql, DonHang.class);
        query.setParameter("trangThaiDonHang", "Đang chuẩn bị");
        query.setParameter("maDauBep", maDauBep);
        return query.getResultList();
    }    

    public Boolean updateDonHang(DonHang donHang) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(donHang);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
        return true;
    }
    
    public DonHang findDonHangChuaThanhToanByMaBan(Long maBan) {
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT d FROM DonHang d WHERE d.ban.maBan = :maBan AND d.trangThaiDonHang = :trangThaiDonHang";
        TypedQuery<DonHang> query = enma.createQuery(jpql, DonHang.class);
        query.setParameter("maBan", maBan);
        query.setParameter("trangThaiDonHang", "Chưa thanh toán");

        DonHang donHang = null;
        try {
            donHang = query.getResultList().get(0);
        } catch (Exception e) {
        }
        enma.close();
        return donHang;
    }

    public float tinhTienMonAn(DonHang donHang) {
        float result = 0;
        for (ChiTietDonHang item : donHang.getChiTietDonHang()) {
            result += item.getMonAn().getGiaTien() * item.getSoLuong();
        }
        return result;
    }

    private float tinhTienGiamCoupon(DonHang donHang, Integer maCoupon) {
        if (maCoupon == null) {
            return 0;
        }

        float totalFood = tinhTienMonAn(donHang);

        EntityManager entityManager = JPAConfig.getEntityManager();
        Coupon coupon = entityManager.find(Coupon.class, maCoupon);
        if (coupon != null) {
            if (totalFood >= coupon.getDonToiThieu()) {
                float soTienDuocGiam = totalFood * coupon.getPhanTramGiam();
                return soTienDuocGiam > coupon.getGiamToiDa() ? coupon.getGiamToiDa() : soTienDuocGiam;
            }
            return 0;
        }
        return 0;
    }

    private DonHang tinhToanVaCapNhat(DonHang donHang) {
        float total;
        try {
            total = tinhTienMonAn(donHang) + donHang.getPhuThu() - tinhTienGiamCoupon(donHang, donHang.getCoupon().getMaCoupon());
        } catch (Exception e) {
            total = tinhTienMonAn(donHang) + donHang.getPhuThu();
        }
        donHang.setSoTienThanhToan(total);
        updateDonHang(donHang);
        return donHang;
    }

    public DonHang capNhatPhuThu(DonHang donHang, Float phuThu) {
        donHang.setPhuThu(phuThu);
        return tinhToanVaCapNhat(donHang);
    }

    public DonHang apDungCoupon(DonHang donHang, Integer maCoupon) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        Coupon coupon = entityManager.find(Coupon.class, maCoupon);
        entityManager.close();
        if (coupon == null) {
            return donHang;
        }

        // Kiểm tra tính hợp lệ của Coupon
        long today = new Date().getTime();
        if (today > coupon.getNgayKetThuc().getTime() || today < coupon.getNgayBatDau().getTime() || tinhTienMonAn(donHang) < coupon.getDonToiThieu()) {
            return donHang;
        }

        donHang.setCoupon(coupon);
        return tinhToanVaCapNhat(donHang);
    }

    public void thanhToanDonHang(DonHang donHang, Integer maNhanVienThuNgan) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        NhanVien thuNgan = entityManager.find(NhanVien.class, maNhanVienThuNgan);
        if (thuNgan == null) {
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Ban ban = donHang.getBan();
            ban.setTrangThaiBan("Đang có sẵn");
            new DAOBan().updateBan(ban);

            DatTruoc datTruoc = new DAODatTruoc().findDatTruocByMaBan(ban.getMaBan());
            if (datTruoc != null) {
                datTruoc.setTrangThaiDatTruoc("Đã phục vụ");
                new DAODatTruoc().updateDatTruoc(datTruoc);
            }

            donHang.setThuNgan(thuNgan);
            donHang.setTrangThaiDonHang("Đã thanh toán");
            tinhToanVaCapNhat(donHang);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<DonHang> getDanhSachDonHangChuaThanhToan() {
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT d FROM DonHang d WHERE d.trangThaiDonHang = :trangThaiDonHang";
        TypedQuery<DonHang> query = enma.createQuery(jpql, DonHang.class);
        query.setParameter("trangThaiDonHang", "Chưa thanh toán");

        return query.getResultList();
    }

    public List<DonHang> getDanhSachDonHangDangChuanBi() {
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT d FROM DonHang d WHERE d.trangThaiDonHang = :trangThaiDonHang";
        TypedQuery<DonHang> query = enma.createQuery(jpql, DonHang.class);
        query.setParameter("trangThaiDonHang", "Đang chuẩn bị");

        return query.getResultList();
    }

    private NhanVien searchDauBep() {
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT n FROM NhanVien n WHERE n.loaiNhanVien = :loaiNhanVien";
        TypedQuery<NhanVien> query = enma.createQuery(jpql, NhanVien.class);
        query.setParameter("loaiNhanVien", "Đầu bếp");

        List<NhanVien> dauBepList = query.getResultList();
        Random rand = new Random();
        return dauBepList.get(rand.nextInt(dauBepList.size()));
    }

    public DonHang insertDonHang(int maNhanVienPhucVu, Ban ban) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        NhanVien phucVu = entityManager.find(NhanVien.class, maNhanVienPhucVu);
        if (phucVu == null) {
            return null;
        }

        DonHang donHang = new DonHang();
        donHang.setPhuThu(ban.getLoaiBan().equals("VIP") ? 50000F : 0F);
        donHang.setSoTienThanhToan(donHang.getPhuThu());
        donHang.setThoiGianCheckIn(new Date());
        donHang.setTrangThaiDonHang("Đang chuẩn bị");
        donHang.setBan(ban);
        donHang.setPhucVu(phucVu);
        donHang.setDauBep(searchDauBep());

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(donHang);

            ban.setTrangThaiBan("Đang phục vụ");
            new DAOBan().updateBan(ban);

            KhachHang khachHang = new KhachHang();
            DatTruoc datTruoc = new DAODatTruoc().findDatTruocByMaBanForPhucVu(ban.getMaBan());
            if (datTruoc != null) {
                datTruoc.setTrangThaiDatTruoc("Đã check-in");
                new DAODatTruoc().updateDatTruoc(datTruoc);
                khachHang = datTruoc.getKhachHang();
            } else {
                entityManager.persist(khachHang);
            }
            donHang.setKhachHang(khachHang);
            entityManager.merge(donHang);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return donHang;
    }

    public void themChiTietDonHang(DonHang donHang, MonAn monAn, int soLuong) {
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
        chiTietDonHang.setChiTietDonHangId(new ChiTietDonHangId(donHang.getMaDonHang(), monAn.getMaMonAn()));
        chiTietDonHang.setDonHang(donHang);
        chiTietDonHang.setMonAn(monAn);
        chiTietDonHang.setSoLuong(soLuong);
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(chiTietDonHang);
            donHang.getChiTietDonHang().add(chiTietDonHang);
            entityManager.merge(donHang);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        tinhToanVaCapNhat(donHang);
    }

    public void capNhatChiTietDonHang(DonHang donHang, MonAn monAn, int soLuong) {
        List<ChiTietDonHang> orderItems = donHang.getChiTietDonHang();
        ChiTietDonHang orderItem = new ChiTietDonHang();
        for (ChiTietDonHang chiTietDonHang : orderItems) {
            if (chiTietDonHang.getMonAn() == monAn) {
                chiTietDonHang.setSoLuong(soLuong);
                orderItem = chiTietDonHang;
            }
        }

        donHang.setChiTietDonHang(orderItems);
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(orderItem);
            entityManager.merge(donHang);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        tinhToanVaCapNhat(donHang);
    }
    
    public void capNhatTrangThaiDonHangDauBep(DonHang donHang){
        donHang.setTrangThaiDonHang("Chưa thanh toán");
        tinhToanVaCapNhat(donHang);
    }
}
