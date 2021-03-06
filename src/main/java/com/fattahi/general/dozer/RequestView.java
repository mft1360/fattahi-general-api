package com.fattahi.general.dozer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestView {
   @SuppressWarnings("rawtypes")
   public Class value();
}
