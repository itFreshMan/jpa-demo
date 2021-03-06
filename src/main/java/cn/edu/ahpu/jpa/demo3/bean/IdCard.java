package cn.edu.ahpu.jpa.demo3.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class IdCard {
	private Integer id;
	private String cardno;
	
	private People people;

	public IdCard() {
		super();
	}

	public IdCard(Integer id, String cardno) {
		super();
		this.id = id;
		this.cardno = cardno;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=18,nullable = false)
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	

	@OneToOne(mappedBy="idCard",cascade = CascadeType.ALL)
	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}
}
