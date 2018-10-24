package io.github.sds0917.docs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入口Controller类1 sdfdsfds测试啊
 * 
 * @author 孙东升
 * @date 2018年10月24日
 */
@RestController
@RequestMapping
public class IndexController {

	/**
	 * 首页地址入口
	 */
	@GetMapping("/")
	public void index() {

	}

	/**
	 * 根据id查询用户名称
	 * 
	 * @param id id标识
	 * @return 返回值
	 */
	@PostMapping
	public String findName(String id) {
		return "";
	}

}