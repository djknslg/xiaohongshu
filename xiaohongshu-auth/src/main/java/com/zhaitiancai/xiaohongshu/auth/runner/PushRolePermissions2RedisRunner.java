package com.zhaitiancai.xiaohongshu.auth.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhai
 * @date 2025
 * @version v1.0.0
 * @deprecated 推送角色权限到Redis 中
 */
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

		// todo

		log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
	}
}
