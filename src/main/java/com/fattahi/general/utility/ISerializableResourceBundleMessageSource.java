package com.fattahi.general.utility;

import java.util.Locale;
import java.util.Properties;

public interface ISerializableResourceBundleMessageSource {

   Properties getAllProperties();

   Properties getAllProperties(Locale locale);

}
