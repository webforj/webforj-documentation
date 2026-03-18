package com.webforj.samples.utils;

import com.webforj.Page;
import com.webforj.component.alert.Alert;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Img;
import com.webforj.component.icons.Icon;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.optioninput.CheckBox;

import java.util.Map;

public final class NodeNameUtils {

  public static String getNodeName(Class<?> klass) {
    return NODE_NAMES.get(klass);
  }

  private static final Map<Class<?>, String> NODE_NAMES = Map.ofEntries(
    Map.entry(Alert.class, "dwc-alert"),
    Map.entry(Avatar.class, "dwc-avatar"),
    Map.entry(CheckBox.class, "dwc-checkbox"),
    Map.entry(ChoiceBox.class, "dwc-choicebox"),
    Map.entry(Dialog.class, "dwc-dialog"),
    Map.entry(Icon.class, "dwc-icon"),
    Map.entry(Img.class, "img"),
    Map.entry(TextField.class, "dwc-field")
  );

}
