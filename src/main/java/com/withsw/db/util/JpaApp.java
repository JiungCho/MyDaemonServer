package com.withsw.db.util;

import javax.persistence.EntityManager;

import com.withsw.db.domain.TbUsr;

public class JpaApp {

	public static void main(String[] args) {
		EMF.init();
		EntityManager em = EMF.createEntityManager();
		
		try {
			/*
			em.getTransaction().begin();
			
			TbUsr usr = new TbUsr();
			usr.setUserId("jeruma");
			usr.setUserPw("pw");
			usr.setUserNm("조지웅");
			usr.setUserPerm(1);
			usr.setPhoneNo("01029226565");
			usr.setSmsSendYn("Y");
			usr.setMail("kerusia85@naver.com");
			usr.setMailSendYn("Y");
			usr.setRegUserId("SYSTEM");
			usr.setUptUserId("SYSTEM");
			usr.setDel("N");
			
			em.persist(usr);
			
			System.out.println("ID = " + usr.getUserIdx());
			
			em.getTransaction().commit();
			*/
			
			TbUsr usrOne = em.find(TbUsr.class, 1);
			if(usrOne != null) {
				System.out.println(usrOne.toString());
			}
		} catch(Exception e) {
			e.printStackTrace();
			//em.getTransaction().rollback();
		} finally {
			em.close();
		}

		
		EMF.close();
	}

}
