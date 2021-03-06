package cn.edu.ahpu.jpa.demo.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.edu.ahpu.jpa.demo.enumType.Gender;

@Entity
@Table(name = "person")
public class Person {
	private Date birthdate;
	private Byte[] file;
	private Gender gender = Gender.MAN;//枚举类型 不能为空,在这里给予默认值
	private Integer id;
	private String imagepath;
	private String info; 
	private String name;
	
	
	public Person() {
		super();
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	@Temporal(TemporalType.DATE)//TemporalType.TIMESTAMP ==> date+time
	public Date getBirthdate() {
		return birthdate;
	}

	@Lob  //二进制文件： mysql -->longblob
	@Basic(fetch=FetchType.LAZY) //该属性 一般用在 大数据字段上;
	public Byte[] getFile() {
		return file;
	}

	/**
	 * 枚举类型字段对应的数据库字段的值类型默认为 枚举类型中值的index 数据库字段类型int类型
	 * !!注意：如果数据库中存放的是枚举类型的index,则在枚举类型各个值index发生变化时
	 * !! 会对查询造成影响,可能会查询不到数据;
	 * 如现在枚举类型：
	 *	public enum Gender {
			MAN,WOMAN
		}
	 * 如果我之前保存一个id = 1,name ="tom",gender = GENDER.MAN
	 * 那么数据库存的对应的字段(id,name,gender) = (1,"tome",0);//GENDER.MAN相对应的index为0
	 * 使用JPQL查询"select o from Person o where gender = :gender" ;//:gender = GENDER.MAN
	 * 可以查询到,如果在枚举类型中MAN的index发生变化时,如
	 	public enum Gender {
			UNKONWN,MAN,WOMAN//增加一个类型
		}
		或者
		public enum Gender {
			WOMAN,MAN //位置颠倒
		}
	 * 则查询不到
	 * 故建议使用EnumTpye.String,而不使用默认的类型	
	 * @return
	 */
	@Enumerated(EnumType.STRING) 
	@Column(length = 8,nullable = false) 
	public Gender getGender() {
		return gender;
	}
	
	@Transient //not persistance,无需向数据库中持久化此字段;
	public String getImagepath() {
		return imagepath;
	}
	
	/**
	 * 可以标注在变量上面,也可以标注在变量的getter()上
	 * 
	 * @return
	 */
	@Id
	@Column(name = "p_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	@Lob //大文本类型; mysql -> longtext
	public String getInfo() {
		return info;
	}

	@Column(name = "p_name", length = 20, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setFile(Byte[] file) {
		this.file = file;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [birthdate=" + birthdate + ", gender=" + gender
				+ ", id=" + id + ", imagepath=" + imagepath + ", info=" + info
				+ ", name=" + name + "]";
	}

}
