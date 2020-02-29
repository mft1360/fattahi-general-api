package com.fattahi.general.dozer;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fattahi.general.model.BaseEntity;
import com.fattahi.general.page.SimplePageResponse;
import com.fattahi.general.service.ICustomMapper;

/**
 * @author m.fatahi
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ViewInjectingReturnValueHandler implements HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;

	private final ICustomMapper Mapper;

	public ViewInjectingReturnValueHandler(HandlerMethodReturnValueHandler delegate, ICustomMapper mapper) {
		this.delegate = delegate;
		this.Mapper = mapper;
	}

	private Class getDeclaredViewClass(MethodParameter returnType) {
		ResponseView annotation = returnType.getMethodAnnotation(ResponseView.class);
		if (annotation != null) {
			return annotation.value();
		} else {
			return null;
		}
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		Class viewClass = getDeclaredViewClass(returnType);
		if (viewClass != null) {
			if (returnValue instanceof BaseEntity) {
				returnValue = wrapResult((BaseEntity) returnValue, viewClass);
			} else if (returnValue instanceof SimplePageResponse) {
				((SimplePageResponse) returnValue)
						.setContent(wrapResultList(((SimplePageResponse) returnValue).getContent(), viewClass));
			} else if (returnValue instanceof List) {
				returnValue = wrapResultList((List) returnValue, viewClass);
			} else if (returnValue instanceof Set) {
				returnValue = wrapResultList((Set<BaseEntity>) returnValue, viewClass);
			}
		}
		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	private <T> T wrapResult(Object result, Class<T> viewClass) {
		return Mapper.onlyMap(result, viewClass);
	}

	private <T> List<T> wrapResultList(Collection<?> list, Class<T> viewClass) {
		return (List<T>) Mapper.mapList(list, viewClass);
	}
}