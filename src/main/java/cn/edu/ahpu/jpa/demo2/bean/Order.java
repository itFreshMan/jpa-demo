package cn.edu.ahpu.jpa.demo2.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	private Float amount = 0f;
	private String orderid;
	private Set<OrderItem> items = new HashSet<OrderItem>();

	public Order(Float amount, String orderid) {
		super();
		this.amount = amount;
		this.orderid = orderid;
	}
	
	/**
	 * @param orderid
	 * @param amount
	 */
	public Order(String orderid, Float amount) {
		super();
		this.orderid = orderid;
		this.amount = amount;
	}

	public Order() {
		super();
	}

	@Column(nullable = false)
	public Float getAmount() {
		return amount;
	}
	
	/**
	 * CascadeType共4种：PERSIST（级联新建）, MERGE（级联更新）, REMOVE （级联删除）, REFRESH（级联刷新） ，ALL(前四项之和)
	  	MERGE：若items属性修改了那么order对象保存时同时修改items里的对象。对应EntityManager的merge方法  
   		REMOVE：对order对象删除也对items里的对象也会删除。对应EntityManager的remove方法  
    	PERSIST：对order对象保存时也对items里的对象也会保存。对应EntityManager的presist方法  
    	REFRESH：获取order对象里也同时也重新获取最新的items时的对象。对应EntityManager的refresh(object)方法有效。即会重新查询数据库里的最新数据  

     * 	FetchType.LAZY表示懒加载。对于xxxtoMany时即获得多的一方fetch的默认值是FetchType.LAZY懒加载。
    	对于xxxtoOne时即获得一的一方fetch的默认值是FetchType.EAGER立即加载  
   		mappedBy表示关系统被维护端，它的值是关系维护端维护关系的属性  
	 * 
	 */
	//@*ToMany fetchType默认的为lazy
	//mappedBy  由关系维护端即@*ToMany的Many端的一个属性 来维护关系
	@OneToMany(cascade ={CascadeType.REFRESH,CascadeType.PERSIST},fetch = FetchType.LAZY,mappedBy="order") 
	public Set<OrderItem> getItems() {
		return items;
	}

	@Id
	@Column(length = 12)
	public String getOrderid() {
		return orderid;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}
