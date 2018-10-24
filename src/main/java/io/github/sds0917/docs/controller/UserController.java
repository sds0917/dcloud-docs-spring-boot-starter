package io.github.sds0917.docs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.sds0917.docs.bean.User;

/**
 * 用户接口
 * 
 * @author 孙东升
 * @date 2018年10月24日
 */
@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 分页查询用户列表
	 * 
	 * @param page 当前页
	 * @param size 页大小
	 * @return 用户列表数据
	 */
	@GetMapping
	public ResponseEntity<List<User>> findAll(Integer page, Integer size) {
		return ResponseEntity.ok(new ArrayList<User>());
	}

	/**
	 * 根据用户id查询用户
	 * 
	 * @param id 用户id
	 * @return 影响行数
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> find(@PathVariable(value = "id") String id) {
		return ResponseEntity.ok(new User());
	}

	/**
	 * 根据用户id删除用户
	 * 
	 * @param id 用户id
	 * @return 影响行数
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable(value = "id") String id) {
		return ResponseEntity.ok(1);
	}

	/**
	 * 新增用户
	 * 
	 * @param user 待新增数据
	 * @return 影响行数
	 */
	@PostMapping
	public ResponseEntity<Integer> save(User user) {
		return ResponseEntity.ok(1);
	}

	/**
	 * 更新用户
	 * 
	 * @param user 待更新数据
	 * 这个是测试呢
	 * @return 影响行数
	 */
	@PatchMapping
	public ResponseEntity<Integer> update(User user) {
		return ResponseEntity.ok(1);
	}

}
