package com.zhaitiancai.xiaohongshu.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan({"com.zhaitiancai.*"})
@MapperScan("com.zhaitiancai.xiaohongshu.auth.mapper")
public class XiaohongshuAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaohongshuAuthApplication.class, args);
	}

}
