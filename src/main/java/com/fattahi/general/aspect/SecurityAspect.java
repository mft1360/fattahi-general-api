package com.fattahi.general.aspect;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.fattahi.general.service.ICurrentUser;

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
public class SecurityAspect {

	@Autowired
	private ICurrentUser currentUser;

	@Autowired
	private ApplicationContext applicationContext;

	@Before("execution (* com.fattahi.*.controller.*.*(..))")
	public void theAdvice(JoinPoint joinPoint) {
		Secured secured = joinPoint.getTarget().getClass().getAnnotation(Secured.class);
		PreAuthorize preAuthorize = joinPoint.getTarget().getClass().getAnnotation(PreAuthorize.class);
		if (preAuthorize != null) {
			boolean flag = false;
			String[] roles = preAuthorize.value().split(" or ");
			for (String role : roles) {
				role = role.replace("hasRole('", "").replace("')", "");
				if (!flag) {
					flag = currentUser.hasRole(role);
				}
			}
			if (!flag) {
				throw new org.springframework.security.access.AccessDeniedException("AccessDenied");
			}
		}
		if (secured != null) {
			boolean flag = false;
			String[] roles = secured.value();
			for (String role : roles) {
				flag = currentUser.hasRole(role);
			}
			if (!flag) {
				throw new org.springframework.security.access.AccessDeniedException("AccessDenied");
			}
		}

		Field field = null;
		try {
			field = joinPoint.getTarget().getClass().getDeclaredField("SecuredMethodBaseController");
		} catch (Exception e) {
			field = null;
		}
		if (field != null) {
			String methodName = joinPoint.getSignature().getName();
			String[] values = null;
			try {
				values = (String[]) field.get(joinPoint.getThis());
			} catch (Exception e) {
				values = null;
			}
			if (values != null) {
				for (String str : values) {
					if (str.split("-")[0].equals(methodName)) {
						if (currentUser.hasRole(str.split("-")[1])) {
							throw new org.springframework.security.access.AccessDeniedException("AccessDenied");
						}
						break;
					}
				}
			}

		}
	}

}
