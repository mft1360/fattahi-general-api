package com.fattahi.general.utility.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fattahi.general.utility.validator.imp.NumberFieldValidator;

@Documented
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberFieldValidator.class)
public @interface NumberField {

	Class<?>[] groups() default {};

	String message() default "data this fiels invalid";

	Class<? extends Payload>[] payload() default {};

}
