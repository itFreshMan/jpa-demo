package cn.edu.ahpu.jpa.demo4.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {

	private Integer id;
	private Integer name;
	private Set<Teacher> teachers = new HashSet<Teacher>();

	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name="students_teachers",inverseJoinColumns={@JoinColumn(name="tch_id")},
			joinColumns={@JoinColumn(name="stu_id")})
	//inverseJoinColumns指定被维护端字段的名称
	//joinColumns指定维护短字段名称
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public Student() {
		super();
	}

	public Student(Integer id, Integer name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 10, nullable = false)
	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

}
