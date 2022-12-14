package com.dao;

import com.config.JPAConfig;
import com.entity.Ban;
import com.entity.DatTruoc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DAOBan {

    public void updateBan(Ban ban) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(ban);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public Ban searchBanByMaBan(Integer maBan) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        Ban ban = entityManager.find(Ban.class, maBan);
        entityManager.close();
        return ban;
    }
    
    public List<Ban> getListBanDatTruoc(){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT d FROM DatTruoc d WHERE d.trangThaiDatTruoc = :trangThaiDatTruoc";
		TypedQuery<DatTruoc> query = enma.createQuery(jpql, DatTruoc.class);
		query.setParameter("trangThaiDatTruoc", "Đã xác nhận");
        List<DatTruoc> datTruocs = query.getResultList();
        
        List<Ban> banDatTruoc = new ArrayList<>();
        for(DatTruoc datTruoc: datTruocs)
            banDatTruoc.add(datTruoc.getBan());
		return banDatTruoc;
    }
    
     public List<Ban> getListBanDangCoSan(){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT b FROM Ban b WHERE b.trangThaiBan = :trangThaiBan";
		TypedQuery<Ban> query = enma.createQuery(jpql, Ban.class);
		query.setParameter("trangThaiBan", "Đang có sẵn");
        
		return query.getResultList();
    }
}
