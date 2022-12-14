package com.dao;

import com.config.JPAConfig;
import com.entity.Ban;
import com.entity.DatTruoc;
import com.entity.NhanVien;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DAODatTruoc {
    public List<DatTruoc> getAllDatTruocChoXacNhan(){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT d FROM DatTruoc d WHERE d.trangThaiDatTruoc = :trangThaiDatTruoc";
		TypedQuery<DatTruoc> query = enma.createQuery(jpql, DatTruoc.class);
        query.setParameter("trangThaiDatTruoc", "Chờ xác nhận");
        
		List<DatTruoc> listDatTruoc = query.getResultList();
        enma.close();
        return listDatTruoc;
    }
    
    public void chapNhanDatTruoc(DatTruoc datTruoc, int maNhanVien){
        datTruoc.setTrangThaiDatTruoc("Đã xác nhận");
        EntityManager entityManager = JPAConfig.getEntityManager();
        datTruoc.setNhanVienTiepNhan(entityManager.find(NhanVien.class, maNhanVien));        
        updateDatTruoc(datTruoc);
    }
    
    public void tuChoiDatTruoc(DatTruoc datTruoc, int maNhanVien){
        datTruoc.setTrangThaiDatTruoc("Từ chối");
        EntityManager entityManager = JPAConfig.getEntityManager();
        datTruoc.setNhanVienTiepNhan(entityManager.find(NhanVien.class, maNhanVien));  
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Ban ban = datTruoc.getBan();
            ban.setTrangThaiBan("Đang có sẵn");
            new DAOBan().updateBan(ban);
            
            updateDatTruoc(datTruoc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally{
            entityManager.close();
        }
        
    }
    
    public void updateDatTruoc(DatTruoc datTruoc){
        EntityManager entityManager = JPAConfig.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.merge(datTruoc);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}    
    }
    
    public DatTruoc findDatTruocByMaBan(Long maBan){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT d FROM DatTruoc d WHERE d.ban.maBan = :maBan AND d.trangThaiDatTruoc = :trangThaiDatTruoc";
		TypedQuery<DatTruoc> query = enma.createQuery(jpql, DatTruoc.class);
		query.setParameter("maBan", maBan);
        query.setParameter("trangThaiDatTruoc", "Đã check-in");
        
		DatTruoc datTruoc = null;
        try {
            datTruoc = query.getResultList().get(0);
        } catch (Exception e) {
        }
        enma.close();
        return datTruoc;
    }
    
    public DatTruoc findDatTruocByMaBanForPhucVu(Long maBan){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT d FROM DatTruoc d WHERE d.ban.maBan = :maBan AND d.trangThaiDatTruoc = :trangThaiDatTruoc";
		TypedQuery<DatTruoc> query = enma.createQuery(jpql, DatTruoc.class);
		query.setParameter("maBan", maBan);
        query.setParameter("trangThaiDatTruoc", "Đã xác nhận");
        
		DatTruoc datTruoc = null;
        try {
            datTruoc = query.getResultList().get(0);
        } catch (Exception e) {
        }
        enma.close();
        return datTruoc;
    }
}
