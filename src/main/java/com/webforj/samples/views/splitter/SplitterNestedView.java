package com.webforj.samples.views.splitter;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.SplitterBox;

@Route
@FrameTitle("Splitter Nested")
public class SplitterNestedView extends Composite<Div> {

  public SplitterNestedView() {
    SplitterBox top = new SplitterBox("Top", SplitterBox.Theme.WARNING);
    SplitterBox bottom = new SplitterBox("Bottom", SplitterBox.Theme.SUCCESS);
    Splitter verticalSplitter = new Splitter(top, bottom);
    verticalSplitter.setOrientation(Splitter.Orientation.VERTICAL);

    SplitterBox master = new SplitterBox("Start", SplitterBox.Theme.INFO);
    Splitter horizontalSplitter = new Splitter(master, verticalSplitter);

    getBoundComponent().add(horizontalSplitter);
  }
}
