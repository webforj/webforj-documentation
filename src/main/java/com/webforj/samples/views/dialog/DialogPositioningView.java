package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Positioning")
public class DialogPositioningView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogPositioningView() {
    self.add(dialog);

    RadioButton nearTopLeft = createPositionOption("Near top-left", "5%", "10%", false);
    RadioButton centered =
        createPositionOption("Centered in viewport", "calc(50% - 10rem)", "calc(50% - 4rem)", true);
    RadioButton nearBottomRight =
        createPositionOption("Near bottom-right", "calc(95% - 20rem)", "calc(90% - 8rem)", false);
    RadioButtonGroup positions =
        new RadioButtonGroup("Position preset", nearTopLeft, centered, nearBottomRight);
    positions.onChange(
        e -> {
          RadioButton selected = e.getChecked();
          if (selected != null) {
            setPosition(
                (String) selected.getUserData("posx"), (String) selected.getUserData("posy"));
          }
        });

    dialog
        .addToHeader(new Div("Position presets"))
        .addToContent(positions)
        .setMoveable(false)
        .setCloseable(false)
        .setMaxWidth("20rem")
        .setPosx("calc(50% - 10rem)")
        .setPosy("calc(50% - 4rem)")
        .open();
  }

  private RadioButton createPositionOption(
      String label, String posx, String posy, boolean checked) {
    RadioButton option = new RadioButton(label, checked);
    option.setUserData("posx", posx);
    option.setUserData("posy", posy);
    return option;
  }

  private void setPosition(String x, String y) {
    dialog.setPosx(x).setPosy(y);
  }
}
