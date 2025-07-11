package com.xray_synchronizer.utils;

import java.util.Properties;

public class AppProps {
  private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
  private static AppProps instance;

  private static Properties PROPERTIES;

  private AppProps() {
  }

  static {
    instance = new AppProps();
    PROPERTIES = PropertyLoader.loadProperties(APPLICATION_PROPERTIES_FILE);
  }

  public static String getProperty(String key) {
    return PropertyLoader.getProperty(key, PROPERTIES);
  }

}
