package io.github.sds0917.docs.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 * 
 * @author 孙东升
 * @date 2018年10月24日
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7835774939329410171L;
	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 用户名字
	 */
	private String name;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 当前用户的子用户
	 */
	private List<User> childrens;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public List<User> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<User> childrens) {
		this.childrens = childrens;
	}

}