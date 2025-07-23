package com.webforj.samples.views.checkbox;

import com.webforj.concern.HasTextPosition.Position;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Checkbox Horizontal Text")
public class CheckboxHorizontalTextView extends Composite<FlexLayout> {

  public CheckboxHorizontalTextView() {
    getBoundComponent().setDirection(FlexDirection.ROW).setWrap(FlexWrap.WRAP).setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)");
    FlexLayout rightAligned = FlexLayout.create()
        .vertical()
        .build()
        .setWidth("100px");

    FlexLayout leftAligned = FlexLayout.create()
        .vertical()
        .align().end()
        .build()
        .setWidth("100px");

    rightAligned.add(
        new CheckBox("Daily", true),
        new CheckBox("Weekly"),
        new CheckBox("Bi-Weekly"),
        new CheckBox("Monthly"),
        new CheckBox("Annually"));

    leftAligned.add(
        new CheckBox("Daily", true).setTextPosition(Position.LEFT),
        new CheckBox("Weekly").setTextPosition(Position.LEFT),
        new CheckBox("Bi-Weekly").setTextPosition(Position.LEFT),
        new CheckBox("Monthly").setTextPosition(Position.LEFT),
        new CheckBox("Annually").setTextPosition(Position.LEFT));

    getBoundComponent().add(rightAligned, leftAligned);
  }
}
