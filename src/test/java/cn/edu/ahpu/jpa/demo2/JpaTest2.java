package cn.edu.ahpu.jpa.demo2;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import cn.edu.ahpu.jpa.demo2.bean.Order;
import cn.edu.ahpu.jpa.demo2.bean.OrderItem;

public class JpaTest2 {
	private String persistenceUnitName = "ahpu";//persistence.xml中<persistence-unit/>的name属性

	@Before
	public void init() {
	}

	@Test
	public void createTable() {
		/**
		 * factory生成过程中,自动扫描classpath路径下带有@Entity的类，自动生成表,不需要再创建session然后生成
		 * 	11:13:03,643 DEBUG [Ejb3Configuration.java:562] - Detect class: true; detect hbm: true
			11:13:03,652 DEBUG [AbstractJarVisitor.java:116] - Searching mapped entities in jar/par: file:/E:/workspace/eclipse-indigo/jpa-demo/target/classes/
			11:13:03,653 DEBUG [AbstractJarVisitor.java:162] - Filtering: cn.edu.ahpu.jpa.demo.bean.Address
			11:13:03,699 DEBUG [AbstractJarVisitor.java:213] - Java element filter matched for cn.edu.ahpu.jpa.demo.bean.Address
			11:13:03,700 DEBUG [AbstractJarVisitor.java:162] - Filtering: cn.edu.ahpu.jpa.demo.bean.Person
			11:13:03,704 DEBUG [AbstractJarVisitor.java:213] - Java element filter matched for cn.edu.ahpu.jpa.demo.bean.Person
			11:13:03,705 DEBUG [Ejb3Configuration.java:562] - Detect class: true; detect hbm: true
		 *	可以看到执行的日志：直接前往class-path目录筛选满足条件的javabean(带有@javax.persistence.Entity)
		 */
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		factory.close();
	}
	
	
	
	@Test
	public void save() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		 EntityManager session = factory.createEntityManager();
		 session.getTransaction().begin();
		 Order order  = new Order();
		 order.setAmount(34f);
		 order.setOrderid("999");
		 
		 OrderItem item = new OrderItem();
		 item.setProductName("足球");
		 item.setSellPrice(11f);
		 item.setOrder(order);
		 
		 OrderItem item2 = new OrderItem();
		 item2.setProductName("矿泉水");
		 item2.setSellPrice(23f);
		 item2.setOrder(order); //设置关联关系
		 
		 Set<OrderItem> items = new HashSet<OrderItem>();
		 items.add(item);
		 items.add(item2);
		 order.setItems(items);
		 
		 session.persist(order);
		 session.getTransaction().commit();
		 session.close();
		factory.close();
	}
}
