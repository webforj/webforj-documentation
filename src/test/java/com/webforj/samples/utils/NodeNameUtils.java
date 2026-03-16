package com.webforj.samples.utils;

import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;

import java.util.Map;

public final class NodeNameUtils {

  public static String getNodeName(Class<?> klass) {
    return NODE_NAMES.get(klass);
  }

  private static final Map<Class<?>, String> NODE_NAMES = Map.ofEntries(
    Map.entry(Alert.class, "dwc-alert"),
    Map.entry(Button.class, "dwc-button")
  );

}
