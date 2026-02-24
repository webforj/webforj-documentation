package com.webforj.samples.views.fields.maskeddatefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.MaskedDateField;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Masked Date Field with Picker")
public class MaskedDateFieldPickerView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private MaskedDateField field = new MaskedDateField("Meeting Date");

  public MaskedDateFieldPickerView() {
    self.setDirection(FlexDirection.COLUMN)
            .setAlignment(FlexAlignment.CENTER)
            .setMargin("var(--dwc-space-m)")
            .add(field);

    field.setMask("%Dz/%Mz/%Yl")
            .setValue(LocalDate.now())
            .setMaxWidth("300px")
            .setHelperText("Click the icon to open the date picker.")
            .setAllowCustomValue(false)
            .getPicker()
            .setIconVisible(true)
            .setAutoOpen(true)
            .setShowWeeks(true);

    whenAttached().thenAccept(c -> field.getPicker().open());
  }
}
