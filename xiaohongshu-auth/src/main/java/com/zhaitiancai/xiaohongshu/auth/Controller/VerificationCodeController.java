package com.zhaitiancai.xiaohongshu.auth.Controller;

import com.zhaitiancai.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zhaitiancai.framework.common.response.Response;
import com.zhaitiancai.xiaohongshu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;
import com.zhaitiancai.xiaohongshu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
@Slf4j
public class VerificationCodeController {
@Resource
private VerificationCodeService verificationCodeService;

	@PostMapping("/code/send")
	@ApiOperationLog(description = "发送短信验证码")
	public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO) {
		return verificationCodeService.send(sendVerificationCodeReqVO);
	}
}
