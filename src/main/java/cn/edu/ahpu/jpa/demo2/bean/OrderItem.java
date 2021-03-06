package cn.edu.ahpu.jpa.demo2.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {
	private Integer id;
	private Order order ;
	private String productName;
	private Float sellPrice = 0f;	

	public OrderItem() {
		super();
	}

	public OrderItem(Integer id, String productName, Float sellPrice) {
		super();
		this.id = id;
		this.productName = productName;
		this.sellPrice = sellPrice;
		
	}

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	/**
	 * optional = true:说明该字段，即外键字段可以为空 ; =false则反之
	 * @return
	 */
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name="order_id")
	public Order getOrder() {
		return order;
	}

	@Column(length = 40 ,nullable = false)
	public String getProductName() {
		return productName;
	}

	@Column(nullable = false)
	public Float getSellPrice() {
		return sellPrice;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}
}
