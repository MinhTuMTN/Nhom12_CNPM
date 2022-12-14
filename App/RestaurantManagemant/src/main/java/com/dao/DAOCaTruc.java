package com.dao;

import com.config.JPAConfig;
import com.entity.CaTruc;
import com.entity.DatTruoc;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DAOCaTruc {
    public List<CaTruc> getAllCaTruc(){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT c FROM CaTruc c";
		TypedQuery<CaTruc> query = enma.createQuery(jpql, CaTruc.class);
        
		List<CaTruc> listCaTruc = query.getResultList();
        enma.close();
        return listCaTruc;
    }
    
    public boolean themCaTruc(CaTruc caTruc){
        if(checkCaTrucTonTai(caTruc.getNgayBatDau(), caTruc.getNgayKetThuc()))
            return false;
        
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(caTruc);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally{
            entityManager.close();
        }
    }
    
    public boolean capNhatCaTruc(CaTruc caTruc){
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(caTruc);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally{
            entityManager.close();
        }
    }
    
    public List<CaTruc> findCaTrucTrongMotKhoang(Date date){
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT c FROM CaTruc c WHERE c.ngayBatDau <= :date AND c.ngayKetThuc >= :date";
        TypedQuery<CaTruc> query = enma.createQuery(jpql, CaTruc.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
    
    private boolean checkCaTrucTonTai(Date ngayBatDau, Date ngayKetThuc){
        EntityManager enma = JPAConfig.getEntityManager();

        String jpql = "SELECT c FROM CaTruc c WHERE c.ngayBatDau = :ngayBatDau AND c.ngayKetThuc = :ngayKetThuc";
        TypedQuery<CaTruc> query = enma.createQuery(jpql, CaTruc.class);
        query.setParameter("ngayBatDau", ngayBatDau);
        query.setParameter("ngayKetThuc", ngayKetThuc);
        return !query.getResultList().isEmpty();
    }
}
