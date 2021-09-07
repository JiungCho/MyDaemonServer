package com.withsw.db.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static EntityManagerFactory emf;
	private static ThreadLocal<EntityManager> curEm = new ThreadLocal<>();
	
	public static void init() {
		emf = Persistence.createEntityManagerFactory("lamenet_nms_db");
	}
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void close() {
		if(emf != null) {
			emf.close();
		}
	}
	
	public static EntityManager currentEntityManager() {
		EntityManager em = curEm.get();
		
		if(em == null) {
			em = emf.createEntityManager();
			curEm.set(em);
		}
		
		return em;
	}
	
	public static void closeCurrentEntityManager() {
		EntityManager em = curEm.get();
		if(em != null) {
			curEm.remove();
			em.close();
		}
	}
}
