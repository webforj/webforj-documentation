package com.webforj.samples.views.checkbox;

import com.webforj.component.Composite;
import com.webforj.component.event.ToggleEvent;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Checkbox Indeterminate")
public class CheckboxIndeterminateView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private CheckBox indeterminate = new CheckBox("Parent");
  private CheckBox child1 = new CheckBox("Child 1");
  private CheckBox child2 = new CheckBox("Child 2");

  public CheckboxIndeterminateView() {
    indeterminate.setIndeterminate(true)
            .addToggleListener(this::indeterminateToggle);

    FlexLayout firstRow = FlexLayout.create(indeterminate)
            .horizontal()
            .build();

    child1.setChecked(false)
            .addToggleListener(this::onCheck);

    FlexLayout secondRow = FlexLayout.create(child1)
            .horizontal()
            .build()
            .setStyle("margin-left", "30px");

    child2.setChecked(true)
            .addToggleListener(this::onCheck);

    FlexLayout thirdRow = FlexLayout.create(child2)
            .horizontal()
            .build()
            .setStyle("margin-left", "30px");
    ;

    self.setDirection(FlexDirection.COLUMN)
            .setMargin("var(--dwc-space-l)")
            .add(firstRow, secondRow, thirdRow);
  }

  private void onCheck(ToggleEvent e) {
    indeterminate.setChecked(child1.isChecked() && child2.isChecked());
    indeterminate.setIndeterminate(child1.isIndeterminate() ^ child2.isChecked());
  }

  private void indeterminateToggle(ToggleEvent e) {
    boolean toggled = e.isToggled();
    child1.setChecked(toggled);
    child2.setChecked(toggled);
  }
}
