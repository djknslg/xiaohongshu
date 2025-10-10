package com.zhaitiancai.xiaohongshu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zhaitiancai.framework.common.exception.BizException;
import com.zhaitiancai.framework.common.response.Response;
import com.zhaitiancai.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zhaitiancai.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zhaitiancai.xiaohongshu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;
import com.zhaitiancai.xiaohongshu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
@Resource
private RedisTemplate<String,Object> redisTemplate;
/**
 * 发送验证码方法
 *
 * 该方法用于发送验证码，是一个接口方法的实现。根据传入的验证码发送请求对象，
 * 执行相应的验证码发送逻辑。目前该方法返回一个空的Response对象，具体实现需要根据业务需求进行完善。
 *
 * @param sendVerificationCodeReqVO 发送验证码的请求对象，包含发送验证码所需的参数信息
 * @return Response<?> 返回一个响应对象，包含操作结果信息。目前实现为返回null，实际使用时应返回具体的响应结果
 */
	@Override
	public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
		//手机号
		String phone =sendVerificationCodeReqVO.getPhone();
		//构建验证码 redis key
		final String key = RedisKeyConstants.buildVerificationCodeKey(phone);
		//判断验证码是否已发送
		boolean isSent=redisTemplate.hasKey(key);
		if (isSent){
			//若之前发送的验证码未过期,则提示发送频烦
			throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
		}
		//生成6为验证码
		final String numbers = RandomUtil.randomNumbers(6);
		//todo 调用第三方短信发送
		log.info("==> 手机号: {}, 已发送验证码：【{}】", phone, sendVerificationCodeReqVO);

		// 存储验证码到 redis, 并设置过期时间为 3 分钟
		redisTemplate.opsForValue().set(key, numbers, 30, TimeUnit.MINUTES);

		return Response.success();
	}
}
