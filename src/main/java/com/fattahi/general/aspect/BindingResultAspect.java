package com.fattahi.general.aspect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import com.fattahi.general.exception.BeanValidationException;

/**
 * Aspect for checking binding result. Also, I've used an extra annotation
 * (@CheckBindingResult) which should be put above the rests. This annotation
 * makes the aspect flexible, so that we can have a method that has
 * BindingResult and we can handle it and the aspect will not be triggered for
 * that.
 *
 * @author mohsenfattahi81@gmail.com
 */
@Component
@Aspect
public class BindingResultAspect {

	@Autowired(required = false)
	private Validator validatorEntity;

	@Before("@annotation (com.fattahi.general.aspect.CheckBindingResult) && args (..,bindingResult)")
	public void theAdvice(JoinPoint joinPoint, BindingResult bindingResult) {
		if (bindingResult.getTarget() instanceof List) {
			List<BindingResult> results = new ArrayList<BindingResult>();
			for (Object obj : (List<?>) bindingResult.getTarget()) {
				DataBinder binder = new DataBinder(obj);
				binder.setValidator(validatorEntity);
				binder.validate();
				BindingResult result = binder.getBindingResult();
				if (result.hasErrors()) {
					results.add(result);
				}
			}
			if (results.size() > 0) {
				throw new BeanValidationException(results);
			}
		}
		if (bindingResult.hasErrors()) {
			throw new BeanValidationException(bindingResult);
		}
	}
}
