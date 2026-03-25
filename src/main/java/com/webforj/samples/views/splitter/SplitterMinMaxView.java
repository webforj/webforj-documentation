package com.webforj.samples.views.splitter;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.SplitterBox;

@Route
@FrameTitle("Splitter Min/Max")
public class SplitterMinMaxView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SplitterMinMaxView() {
    // Create splitter box panels
    SplitterBox master = new SplitterBox("Master", SplitterBox.Theme.INFO);
    SplitterBox detail = new SplitterBox("Detail", SplitterBox.Theme.SUCCESS);

    // Create splitter with the two panels
    Splitter splitter = new Splitter(master, detail);

    // Set minimum and maximum size constraints for master panel
    splitter.setMasterMinSize("200px")
        .setMasterMaxSize("75%");

    self.add(splitter);
  }
}
