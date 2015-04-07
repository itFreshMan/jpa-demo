package cn.edu.ahpu.jpa.demo5.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//
@Embeddable
public class AirLinePK implements Serializable{
	private String startCity;
	private String endCity;

	@Column(length = 10 ,nullable = false)
	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	@Column(length = 10 ,nullable = false)
	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public AirLinePK(String startCity, String endCity) {
		super();
		this.startCity = startCity;
		this.endCity = endCity;
	}

	public AirLinePK() {
		super();
	}
	
}
