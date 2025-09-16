package com.zhaitiancai.xiaohongshu.auth;

import com.zhaitiancai.framework.common.util.JsonUtilS;
import com.zhaitiancai.xiaohongshu.auth.domain.dataobject.UserDO;
import com.zhaitiancai.xiaohongshu.auth.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class XiaohongshuAuthApplicationTests {
	@Resource
	private UserDOMapper userDOMapper;
	@Test
	void testInsert(){
		UserDO user =UserDO.builder()
				.username("宅天才")
				.createTime(LocalDateTime.now())
				.updateTime(LocalDateTime.now())
				.build();
		userDOMapper.insert(user);

	}
	@Test
	void testSelect(){
		final UserDO userDo = userDOMapper.selectByPrimaryKey(1L);
		log.info("User:{}", JsonUtilS.toJsonString(userDo));
	}
	@Test
	void testUpdate(){
		final UserDO userDo = UserDO.builder()
				.id(1L)
				.username("翟天才小红书")
				.updateTime(LocalDateTime.now())
				.build();
		userDOMapper.updateByPrimaryKey(userDo);
	}
	@Test
	void contextLoads() {
	}

}
