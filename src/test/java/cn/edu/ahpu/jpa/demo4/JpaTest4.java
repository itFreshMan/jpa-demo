package cn.edu.ahpu.jpa.demo4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import cn.edu.ahpu.jpa.demo3.bean.IdCard;

public class JpaTest4 {
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
		 session.getTransaction().commit();
		 session.close();
		factory.close();
	}
	
	
	
}
