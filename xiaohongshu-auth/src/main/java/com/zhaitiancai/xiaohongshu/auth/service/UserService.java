package com.zhaitiancai.xiaohongshu.auth.service;

import com.zhaitiancai.framework.common.response.Response;
import com.zhaitiancai.xiaohongshu.auth.model.vo.user.UserLoginReqVO;

/**
 * @author: zhaitiancai
 * @date: 2025/10/9 15:41
 * @version: v1.0.0
 * @description: TODO
 **/
public interface UserService {
	/**
	 * 登录与注册
	 */
	Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);
}
