package com.zhaitiancai.xiaohongshu.auth.Controller;

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
}
