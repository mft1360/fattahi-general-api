package com.fattahi.general.utility.validator.imp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.fattahi.general.utility.validator.NumberField;

public class NumberFieldValidator implements ConstraintValidator<NumberField, String> {

	@Override
	public void initialize(NumberField constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String regular = "[0-9]*";
		if (!(StringUtils.isEmpty(value)) && (!value.matches(regular))) {
			return false;
		}
		return true;
	}

}
