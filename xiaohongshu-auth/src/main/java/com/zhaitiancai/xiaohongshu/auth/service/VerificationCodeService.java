package com.zhaitiancai.xiaohongshu.auth.service;

import com.zhaitiancai.framework.common.response.Response;
import com.zhaitiancai.xiaohongshu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;
import org.springframework.stereotype.Service;

/**
 * 发送短信验证码
 *
 * @return
 */
public interface VerificationCodeService {
	Response<?> send (SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
