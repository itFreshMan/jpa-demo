package cn.edu.ahpu.jpa.demo3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import cn.edu.ahpu.jpa.demo3.bean.IdCard;
import cn.edu.ahpu.jpa.demo3.bean.People;

public class JpaTest3 {
	private String persistenceUnitName = "ahpu";//persistence.xml中<persistence-unit/>的name属性

	@Before
	public void init() {
	}

	@Test
	public void createTable() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		factory.close();
	}
	
	
	
	@Test
	public void save() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		 EntityManager session = factory.createEntityManager();
		 session.getTransaction().begin();
		 People p = new People();
		 p.setName("蒋中正");
		 
		 IdCard card = new IdCard();
		 card.setCardno("9527");
		 
//		 card.setPeople(p);
		 p.setIdCard(card);
		 
		 session.persist(p);
		 session.getTransaction().commit();
		 session.close();
		factory.close();
	}
	
}
