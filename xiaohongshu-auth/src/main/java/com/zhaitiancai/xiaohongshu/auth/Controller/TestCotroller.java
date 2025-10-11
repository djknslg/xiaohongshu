package com.zhaitiancai.xiaohongshu.auth.Controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestCotroller {
//	@NacosValue(value = "${rate-limit.api.limit}", autoRefreshed = true)
	private Integer aaa;
	@GetMapping("/limit")
	public String limit(){
		return "当前限流阈值为: " + aaa;
	}

}
