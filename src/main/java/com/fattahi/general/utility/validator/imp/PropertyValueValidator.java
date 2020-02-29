package com.fattahi.general.utility.validator.imp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fattahi.general.utility.validator.PropertyValueCheak;

public class PropertyValueValidator implements ConstraintValidator<PropertyValueCheak, String> {

	@Override
	public void initialize(PropertyValueCheak constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return true;
	}

}
