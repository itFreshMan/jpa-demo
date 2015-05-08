package cn.edu.ahpu.jpa.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import cn.edu.ahpu.jpa.demo.bean.Person;
import cn.edu.ahpu.jpa.demo.enumType.Gender;

public class JpaTest {
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
		
		//-->sessionFactory-->session-->begin事务
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Person("传智博客"));
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	@Test
	public void find() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		/**
		 * a.类似与hibernate.get(),立即从数据库查询
		 * b.如果em.find(Person.class, 2);数据库中只有id=1的数据时，
		 * 	会返回null
		 */
		Person p = em.find(Person.class, 1);
		System.out.println(p);
		em.close();
		factory.close();
		
		
	}
	
	@Test
	public void getReference() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		/**
		 * a.类似与hibernate.load(),
		 * 	不会立即查询数据库,而是创建一个代理对象;
		 * 	只有在访问该代理对象的属性时,才会去装在数据;
		 * 	如果在session.close()之后再去访问该对象则回报错
		 * 	org.hibernate.LazyInitializationException: could not initialize proxy - no Session
		 * b.如果em.getReference(Person.class, 2);数据库中只有id=1的数据时，
		 *   会跑出异常：javax.persistence.EntityNotFoundException:
		 *   只有在访问代理对象的属性时才跑出异常;
		 *   在第一部创建代理对象时，正常;
		 */
		Person p = em.getReference(Person.class, 1);
		System.out.println(p); 
		em.close();
		factory.close();
	}
	
	@Test
	public void update() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		/**
		 * jpa的四种状态：new , managed(托管),游离(脱管),删除
		 * 只有在托管的状态，才可以更新;如果为游离状态，则数据库不更新
		 */
		Person p = em.find(Person.class, 1);
		em.clear(); //把实体管理器的所有实体变成 游离状态
		p.setName("老XX");
		em.merge(p);//将游离状态的实体 更新同步为数据库;
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	@Test
	public void delete() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person p = em.find(Person.class, 1);
		em.remove(p);
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	@Test
	public void queryJpql() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		//类似huql
		Query query = em.createQuery("select p from Person p where p.id = ?100 and name =:pname and gender = ?33");
		query.setParameter(100, 1);//100为 ?的坐标
		query.setParameter("pname", "传智博客");//pname为 :pname的别名
		query.setParameter(33,Gender.MAN);
//		Person p = (Person) query.getSingleResult();
		List<Person> list = query.getResultList();
		for(Person p : list){
			System.out.println(p);
		}
		em.close();
		factory.close();
	}
	
	
	@Test
	public void updateJpql() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("update Person p set p.gender=?1 where name =:pname");
		query.setParameter(1, Gender.WOMAN);
		query.setParameter("pname", "传智博客");
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
	
	
	
	@Test
	public void saveMulti() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		//-->sessionFactory-->session-->begin事务
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Person pNew = new Person("传智博客"+System.currentTimeMillis());
		em.persist(pNew);
		Integer pid = pNew.getId();
		
		System.out.println(em.find(Person.class, pid));
		
		System.out.println(em.getReference(Person.class, pid));
		Query query = em.createQuery("select p from Person p where p.id = ?100");
		query.setParameter(100, pid);
		System.out.println(query.getSingleResult());//数据库中没有，但是能够查出来，
		saveTransaction(pid);//
		em.getTransaction().commit();
//		em.getTransaction().rollback();
		em.close();
		factory.close();
	}
	
	public void saveTransaction(Integer pid){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		//-->sessionFactory-->session-->begin事务
		EntityManager em = factory.createEntityManager();
		System.out.println("------------"+em.find(Person.class, pid));
		
		try {
			System.out.println("------------"+em.getReference(Person.class, pid));//不存在会报错
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Query query = em.createQuery("select p from Person p where p.id = ?100");
			query.setParameter(100, pid);
			System.out.println("------------"+query.getSingleResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.close();
		factory.close();
		
	}
}
