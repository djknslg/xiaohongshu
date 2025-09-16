package com.zhaitiancai.xiaohongshu.auth.Controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zhaitiancai.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zhaitiancai.framework.common.response.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("/test")
public class TestController {
	@GetMapping("/v1")
	@ApiOperationLog(description = "测试接口")
	public Response<String> test() {
		return Response.success("Hello, 犬小哈专栏");
	}
@PostMapping("/v2")
@ApiOperationLog(description = "测试接口2")
public Response<User> test2(@RequestBody @Validated User user) {
//		int i=1/0;
		return Response.success(user);
//	return Response.success(User.builder()
//			.nickName("犬小哈")
//			.createTime(LocalDateTime.now())
//			.build());
}
// 测试登录，浏览器访问： http://localhost:8080/user/doLogin?username=zhang&password=123456
@RequestMapping("/user/doLogin")
public String doLogin( String username,  String password) {
	// 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
	if("zhang".equals(username) && "123456".equals(password)) {
		StpUtil.login(10001);
		return "登录成功";
	}
	return "登录失败";
}

// 查询登录状态，浏览器访问： http://localhost:8080/user/isLogin
@RequestMapping("/user/isLogin")
public String isLogin() {
	return "当前会话是否登录：" + StpUtil.isLogin();
}
}
