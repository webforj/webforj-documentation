package com.webforj.samples.views.button;

import java.util.ArrayList;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Simple program to demonstrate the various Button expanse values.
 */
@Route
@FrameTitle("Button Expanses")
public class ButtonExpansesView extends Composite<FlexLayout> {
	
  ChoiceBox expanses = new ChoiceBox();
  Button demoButton = new Button("None");
	
  public ButtonExpansesView() {
    getBoundComponent().setSpacing("var(--dwc-space-l)")
    .setMargin("var(--dwc-space-l)");
    
    ArrayList<ListItem> categories = new ArrayList<>();
    categories.add(new ListItem("XSMALL", "XSMALL"));
    categories.add(new ListItem("SMALL", "SMALL"));
    categories.add(new ListItem("MEDIUM", "MEDIUM"));
    categories.add(new ListItem("LARGE", "LARGE"));
    categories.add(new ListItem("XLARGE", "XLARGE"));

    expanses.insert(categories).selectIndex(0);
    expanses.setWidth("100px");

    expanses.addSelectListener(event -> {
      String selectedValue = (String) expanses.getValue();
        if (selectedValue != null) {
            demoButton.setExpanse(Expanse.valueOf(selectedValue));
            demoButton.setText(selectedValue);
        }
    });

    getBoundComponent().add(expanses, demoButton);
   
  }
}