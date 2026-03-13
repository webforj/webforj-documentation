package com.webforj.samples.utils;

import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;

import java.util.Map;

public final class NodeNameUtils {
  private static final Map<String, String> nodeNames = Map.of(
    Alert.class.getCanonicalName(), "dwc-alert",
    Button.class.getCanonicalName(), "dwc-button"
  );

  public static String getNodeName(Class<?> klass) {
    if (!nodeNames.containsKey(klass.getCanonicalName())) {
      throw new IllegalArgumentException(klass.getCanonicalName() + " has no node name!");
    }
    return nodeNames.get(klass.getCanonicalName());
  }

}
