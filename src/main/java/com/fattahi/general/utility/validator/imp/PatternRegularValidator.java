package com.fattahi.general.utility.validator.imp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.fattahi.general.utility.validator.PatternRegular;

public class PatternRegularValidator implements ConstraintValidator<PatternRegular, String> {

	public String regular = "";

	@Override
	public void initialize(PatternRegular constraintAnnotation) {
		regular = constraintAnnotation.regular();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!(StringUtils.isEmpty(value)) && (!value.matches(regular))) {
			return false;
		}
		return true;
	}

}
