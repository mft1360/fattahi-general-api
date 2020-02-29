package com.fattahi.general.utility;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author m.fatahi
 */
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource
      implements ISerializableResourceBundleMessageSource {

   @Override
   public Properties getAllProperties() {
      return getAllProperties(new Locale("fa"));
   }

   @Override
   public Properties getAllProperties(Locale locale) {
      clearCacheIncludingAncestors();
      PropertiesHolder propertiesHolder = getMergedProperties(locale);
      Properties properties = propertiesHolder.getProperties();
      return properties;
   }
}
