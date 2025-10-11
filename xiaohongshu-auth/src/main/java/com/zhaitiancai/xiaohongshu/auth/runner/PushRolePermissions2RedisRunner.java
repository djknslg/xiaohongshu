package com.zhaitiancai.xiaohongshu.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhaitiancai.framework.common.util.JsonUtilS;
import com.zhaitiancai.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zhaitiancai.xiaohongshu.auth.domain.dataobject.PermissionDO;
import com.zhaitiancai.xiaohongshu.auth.domain.dataobject.RoleDO;
import com.zhaitiancai.xiaohongshu.auth.domain.dataobject.RolePermissionDO;
import com.zhaitiancai.xiaohongshu.auth.mapper.PermissionDOMapper;
import com.zhaitiancai.xiaohongshu.auth.mapper.RoleDOMapper;
import com.zhaitiancai.xiaohongshu.auth.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhai
 * @date 2025
 * @version v1.0.0
 * @deprecated 推送角色权限到Redis 中
 */
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private RoleDOMapper roleDOMapper;
	@Resource
	private PermissionDOMapper permissionDOMapper;
	@Resource
	private RolePermissionDOMapper rolePermissionDOMapper;

	// 权限同步标记 Key
	private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

	@Override
	public void run(ApplicationArguments args) {
		log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

		try {
			// 是否能够同步数据: 原子操作，只有在键 PUSH_PERMISSION_FLAG 不存在时，才会设置该键的值为 "1"，并设置过期时间为 1 天
			 boolean canPushed = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);

			 //如果无法同步权限数据
			if (!canPushed){
				log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
				return;
			}
			//查出所有角色
			final List<RoleDO> roleDOS = roleDOMapper.selectEnabledList();
			if (CollUtil.isNotEmpty(roleDOS)){
				//拿到所有角色id
				final List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();
				//根据角色id 批量查出所有角色的对应权限
				final List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);
				//从角色 id 分组 每个角色id 对应多个权限id
				final Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream().collect(
						Collectors.groupingBy(RolePermissionDO::getRoleId, Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList())));
				//查询app端所有被启用的权限
				final List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();
				//权限 id -权限 DO
				final Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
						Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
				);
				//组织角色id -权限 关系
				final HashMap< Long , @NotEmpty List<PermissionDO>> roleIdPermissionDOMap = Maps.newHashMap();

				//循环所有角色
				roleDOS.forEach(roleDO -> {
					//当前角色id
					final Long roleId = roleDO.getId();
					//当前角色 ID 对应的权限 ID集合
					final List<Long> permissionIds = roleIdPermissionIdsMap.get(roleId);
					if (CollUtil.isNotEmpty(permissionIds)){
						List<PermissionDO>  perDOS= Lists.newArrayList();
						permissionIds.forEach(permissionId->{
							//根据 权限id 获取具体的权限DO对象
							PermissionDO permissionDO=permissionIdDOMap.get(permissionId);
							if (Objects.nonNull(permissionDO)){
								perDOS.add(permissionDO);
							}
						});
						roleIdPermissionDOMap.put(roleId,perDOS);
					}

				});

				//同步至 Redis 中 方便后续网关鉴权使用
				roleIdPermissionDOMap.forEach((roleId,permissions)->{
					final String key = RedisKeyConstants.buildRolePermissionsKey(roleId);
					redisTemplate.opsForValue().set(key, JsonUtilS.toJsonString(permissions));
				});
			}
			log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
		} catch (Exception e) {
			log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
		}


	}
}
