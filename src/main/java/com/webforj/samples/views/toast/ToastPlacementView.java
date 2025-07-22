package com.webforj.samples.views.toast;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.component.toast.Toast;
import com.webforj.component.toast.Toast.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;

@Route("toastplacement")
@FrameTitle("Toast Placements")
public class ToastPlacementView extends Composite<FlexLayout> {

  ChoiceBox placementChoiceBox;
  Button showToastButton;

  public ToastPlacementView() {
    getBoundComponent().setDirection(FlexDirection.ROW)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setHeight("100vh")
        .setSpacing("var(--dwc-space-m)");

    placementChoiceBox = new ChoiceBox();

    List<ListItem> placementOptions = new ArrayList<>();
    placementOptions.add(new ListItem(Placement.TOP, "TOP"));
    placementOptions.add(new ListItem(Placement.TOP_LEFT, "TOP_LEFT"));
    placementOptions.add(new ListItem(Placement.TOP_RIGHT, "TOP_RIGHT"));
    placementOptions.add(new ListItem(Placement.CENTER, "CENTER"));
    placementOptions.add(new ListItem(Placement.BOTTOM, "BOTTOM"));
    placementOptions.add(new ListItem(Placement.BOTTOM_LEFT, "BOTTOM_LEFT"));
    placementOptions.add(new ListItem(Placement.BOTTOM_RIGHT, "BOTTOM_RIGHT"));

    placementChoiceBox.insert(placementOptions);
    placementChoiceBox.selectIndex(4);
    placementChoiceBox.setMinWidth(160);

    showToastButton = new Button("Show Toast", ButtonTheme.PRIMARY, e -> {
      Placement selectedPlacement = Placement.valueOf(placementChoiceBox.getText());
      Toast.show("This is a toast notification", selectedPlacement);
    });

    getBoundComponent().add(placementChoiceBox, showToastButton);
  }
}
