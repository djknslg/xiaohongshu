package com.zhaitiancai.framework.common.validator;

import cn.hutool.core.util.PhoneUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author: zhaitiancai
 * @version: v1.0.0
 * @description: TODO
 **/
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {


	@Override
	public void initialize(PhoneNumber constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		return phoneNumber != null && phoneNumber.matches("\\d{11}");
	}
}
