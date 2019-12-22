/**
 * 
 */
package com.lsy.java.test.commons.google;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
* @Title: googleTest
* @Description: 
* @Version:1.0.0  
* @author pancm
* @date 2018年5月14日
*/
public class GoogleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();
	}

	/**
	 * 数组里所有对象的某个属性的值改变
	 */
	private static void test(){
		List<User> userList=new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setName("张三");
		userList.add(user);
		User user2=new User();
		user2.setId(2);
		user2.setName("李四");
		userList.add(user2);
		System.out.println("更改之前的数据:"+userList);
	    userList=Lists.transform(userList,new Function<User, User>() {
			@Override
			public User apply(User user) {
				user.setName("王五");
				return user;
			}
		});
		System.out.println("更改之后的数据:"+userList);
	}


}

/**
 *
 * @Title: User
 * @Description:用户pojo类
 * @Version:1.0.0
 * @author pancm
 * @date 2017年9月26日
 */
class User {

	/** 编号 */
	private int id;
	/** 姓名 */
	private String name;

	public User() {
	}

	/**
	 * 构造方法
	 *
	 * @param id
	 *            编号
	 * @param name
	 *            姓名
	 */
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * 获取编号
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置编号
	 *
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取姓名
	 *
	 * @return name
	 */
	public String getName() {
		System.out.println("姓名:"+name);
		return name;
	}

	/**
	 * 设置姓名
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


}
