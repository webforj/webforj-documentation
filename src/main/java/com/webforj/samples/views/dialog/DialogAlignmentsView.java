package com.webforj.samples.views.dialog;

import com.webforj.component.list.ChoiceBox;
import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Alignments")
public class DialogAlignmentsView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private Dialog dialog = new Dialog();
  private ChoiceBox options = new ChoiceBox();

  public DialogAlignmentsView() {
    self.add(dialog);
    options.add(Dialog.Alignment.TOP, "Top");
    options.add(Dialog.Alignment.CENTER, "Center");
    options.add(Dialog.Alignment.BOTTOM, "Bottom");
    options.setLabel("Select Alignment")
            .selectIndex(1)
            .onSelect(e -> dialog.setAlignment((Dialog.Alignment) e.getSelectedItem().getKey()));

    dialog.addToHeader(new Div("Alignments"))
            .addToContent(options)
            .setStyle("display", "flex")
            .setStyle("justify-content", "center")
            .open()
            .setCloseable(false);
  }
}
