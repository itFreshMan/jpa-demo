package cn.edu.ahpu.jpa.demo4.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Teacher {
	private Integer id;
	private Integer name;
	private Set<Student> students = new HashSet<Student>();
	
	@ManyToMany(cascade = CascadeType.REFRESH,mappedBy="teachers")
	public Set<Student> getStudent() {
		return students;
	}

	public void setStudent(Set<Student> students) {
		this.students = students;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public Teacher() {
		super();
	}

	public Teacher(Integer id, Integer name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 10 ,nullable = false)
	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

}
