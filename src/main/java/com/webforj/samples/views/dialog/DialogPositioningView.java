package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;

@Route
@FrameTitle("Dialog Positioning")
public class DialogPositioningView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogPositioningView() {
    self.add(dialog);

    List<RadioButton> options = new ArrayList<>();
    for (Preset preset : Preset.values()) {
      RadioButton option = new RadioButton(preset.getLabel(), preset == Preset.CENTERED);
      option.setUserData("preset", preset);
      options.add(option);
    }

    RadioButtonGroup presets =
        new RadioButtonGroup("Position presets", options.toArray(RadioButton[]::new));
    presets.onChange(
        e -> {
          RadioButton selected = e.getChecked();
          if (selected != null) {
            applyPreset((Preset) selected.getUserData("preset"));
          }
        });

    FlexLayout content =
        new FlexLayout().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-s)");
    content.add(presets);

    applyPreset(Preset.CENTERED);

    dialog
        .addToHeader(new Div("Position presets"))
        .addToContent(content)
        .setMoveable(false)
        .setCloseable(false)
        .setMaxWidth("20rem")
        .open();
  }

  private void applyPreset(Preset preset) {
    dialog.setPosx(preset.getPosx()).setPosy(preset.getPosy());
  }

  private enum Preset {
    NEAR_START("Upper left", "5%", "10%"),
    CENTERED("Middle", "calc(50% - 10rem)", "calc(50% - 4rem)"),
    NEAR_END("Lower right", "calc(95% - 20rem)", "calc(90% - 8rem)");

    private final String label;
    private final String posx;
    private final String posy;

    Preset(String label, String posx, String posy) {
      this.label = label;
      this.posx = posx;
      this.posy = posy;
    }

    String getLabel() {
      return label;
    }

    String getPosx() {
      return posx;
    }

    String getPosy() {
      return posy;
    }
  }
}
