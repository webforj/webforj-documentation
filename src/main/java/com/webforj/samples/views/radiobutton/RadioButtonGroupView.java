package com.webforj.samples.views.radiobutton;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;

@Route
@FrameTitle("Radio Button Group")
public class RadioButtonGroupView extends Composite<FlexLayout> {

    public RadioButtonGroupView() {

        getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("1em").setMargin("20px");
        RadioButton sAgree = new RadioButton("Strongly Agree");
        RadioButton agree = new RadioButton("Agree");
        RadioButton neutral = new RadioButton("Neutral");
        RadioButton disagree = new RadioButton("Disagree");
        RadioButton sDisagree = new RadioButton("Strongly Disagree");

        RadioButtonGroup group = new RadioButtonGroup(sDisagree, disagree, neutral, agree, sAgree);

        FlexLayout layout = FlexLayout.create(group, sDisagree, disagree, neutral, agree, sAgree)
                .vertical()
                .wrap().wrap()
                .build()
                .addClassName("layout");

        getBoundComponent().add(layout);
    }
}
