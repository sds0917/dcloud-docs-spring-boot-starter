package io.github.sds0917.docs.bean;

/**
 * 用户信息
 * 
 * @author 孙东升
 * @date 2018年10月24日
 */
public class User {

	private String id;
	private String name;
	private String pass;
	private Integer sex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

}