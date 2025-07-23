package com.webforj.samples.views.splitter;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.SplitterBox;

@Route
@FrameTitle("Splitter Position")
public class SplitterPositionView extends Composite<Div> {

  public SplitterPositionView() {
    SplitterBox master = new SplitterBox("Master", SplitterBox.Theme.INFO);
    SplitterBox detail = new SplitterBox("Detail", SplitterBox.Theme.SUCCESS);
    Splitter splitter = new Splitter(master, detail);
    splitter.setPositionRelative(75);

    getBoundComponent().add(splitter);
  }
}
