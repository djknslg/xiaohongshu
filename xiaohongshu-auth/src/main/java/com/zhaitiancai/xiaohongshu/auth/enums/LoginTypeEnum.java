package com.zhaitiancai.xiaohongshu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author: zhaitiancai
 * @url: http://81.70.228.116/#/
 * @date: 2025-10-09 10:33
 * @description: 登录类型
 **/
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
	//验证码
	VERIFICATION_CODE(1),
	//密码
	PASSWORD(2);

	private final Integer value;
	public static LoginTypeEnum valueOf(Integer code){
		for (LoginTypeEnum loginTypeEnum:LoginTypeEnum.values()){
			if (Objects.equals(code,loginTypeEnum.getValue())){
				return loginTypeEnum;
			}
		}
		return null;
	}
}
