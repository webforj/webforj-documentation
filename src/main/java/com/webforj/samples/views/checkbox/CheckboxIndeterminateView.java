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

  CheckBox indeterminate = new CheckBox("Parent");
  CheckBox child1 = new CheckBox("Child 1");
  CheckBox child2 = new CheckBox("Child 2");

  public CheckboxIndeterminateView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN).setMargin("var(--dwc-space-l)");

    indeterminate.setIndeterminate(true);
    indeterminate.addToggleListener(this::indeterminateToggle);

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
        .setStyle("margin-left", "30px");;

    getBoundComponent().add(firstRow, secondRow, thirdRow);
  }

  private void onCheck(ToggleEvent e) {
    if (child1.isChecked() && child2.isChecked()) {
      indeterminate.setChecked(true);
    } else {
      if (child1.isChecked() || child2.isChecked()) {
        indeterminate.setChecked(false);
        indeterminate.setIndeterminate(true);
      } else {
        indeterminate.setIndeterminate(false);
        indeterminate.setChecked(false);
      }
    }
  }

  private void indeterminateToggle(ToggleEvent e) {
    if (e.isToggled()) {
      child1.setChecked(true);
      child2.setChecked(true);
    } else {
      child1.setChecked(false);
      child2.setChecked(false);
    }
  }
}
