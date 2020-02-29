package com.fattahi.general.utility.validator.imp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.fattahi.general.utility.validator.PersionField;

public class PersionFieldValidator implements ConstraintValidator<PersionField, String> {

	@Override
	public void initialize(PersionField constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String regular = "[\u0600-\u06FF\\s]*";
		if (!(StringUtils.isEmpty(value)) && (!value.matches(regular))) {
			return false;
		}
		return true;
	}

}
