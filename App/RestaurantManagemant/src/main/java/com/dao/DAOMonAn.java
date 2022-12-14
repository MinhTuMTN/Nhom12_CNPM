package com.dao;

import com.config.JPAConfig;
import com.entity.MonAn;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DAOMonAn {
    public List<MonAn> getAll(){
        EntityManager entityManager = JPAConfig.getEntityManager();
        TypedQuery<MonAn> query = entityManager.createNamedQuery("MonAn.findAll", MonAn.class);
        List<MonAn> foods = query.getResultList();
        entityManager.close();
        return foods;
    }
    
    public List<MonAn> findMonAn(String text){
        EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT m FROM MonAn m WHERE m.tenMonAn like :text OR m.maMonAn like :text";
		TypedQuery<MonAn> query = enma.createQuery(jpql, MonAn.class);
		query.setParameter("text", "%" + text + "%");

		List<MonAn> foods = query.getResultList();
        enma.close();
        return foods;
    }
    
    public void insertMonAn(MonAn monAn){
		EntityManager entityManager = JPAConfig.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(monAn);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}        
    }
    
    public void updateMonAn(MonAn monAn){
		EntityManager entityManager = JPAConfig.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.merge(monAn);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw e;
		} finally {
			entityManager.close();
		}        
    }
    
    public MonAn findById(Integer id){
        EntityManager entityManager = JPAConfig.getEntityManager();
        MonAn monAn = entityManager.find(MonAn.class, id);
        entityManager.close();
        return monAn;
    }
}
