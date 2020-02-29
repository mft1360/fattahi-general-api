package com.fattahi.general.dozer;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fattahi.general.service.ICustomMapper;
import com.google.common.collect.Lists;

/**
 * @author m.fatahi
 */
public class JsonViewSupportFactoryBean implements InitializingBean {

   @Autowired
   private RequestMappingHandlerAdapter adapter;

   @Autowired(required = true)
   private ICustomMapper                 Mapper;

   @Override
   public void afterPropertiesSet() throws Exception {
      List<HandlerMethodReturnValueHandler> handlers = Lists.newArrayList(adapter.getReturnValueHandlers());
      List<HandlerMethodArgumentResolver> argumentResolvers = Lists.newArrayList(adapter.getArgumentResolvers());
      decorateHandlers(handlers, argumentResolvers);
      adapter.setReturnValueHandlers(handlers);
      adapter.setArgumentResolvers(argumentResolvers);
   }

   private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers,
         List<HandlerMethodArgumentResolver> argumentResolvers) {
      for (HandlerMethodReturnValueHandler handler : handlers) {
         if (handler instanceof RequestResponseBodyMethodProcessor) {
            ViewInjectingReturnValueHandler decorator = new ViewInjectingReturnValueHandler(handler, Mapper);
            int index = handlers.indexOf(handler);
            handlers.set(index, decorator);
            // log.info("JsonView decorator support wired up");
            break;
         }
      }
      for (HandlerMethodArgumentResolver argument : argumentResolvers) {
         if (argument instanceof RequestResponseBodyMethodProcessor) {
            ViewInjectingArgumentValueHandler decorator = new ViewInjectingArgumentValueHandler(Mapper, adapter);
            int index = argumentResolvers.indexOf(argument);
            argumentResolvers.set(index, decorator);
            // log.info("JsonView decorator support wired up");
            break;
         }
      }
   }

}