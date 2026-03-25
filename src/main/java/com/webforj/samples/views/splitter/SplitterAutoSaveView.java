package com.webforj.samples.views.splitter;

import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.SplitterBox;

@Route
@FrameTitle("Splitter Auto Save")
public class SplitterAutoSaveView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SplitterAutoSaveView() {
    // Create top splitter with left and right panels
    SplitterBox topLeft = new SplitterBox("Top Left", SplitterBox.Theme.PRIMARY);
    SplitterBox topRight = new SplitterBox("Top Right", SplitterBox.Theme.INFO);
    Splitter topSplitter = new Splitter("topSplitter", topLeft, topRight)
            .setPositionRelative(30)
            .setAutosave(true);

    // Create bottom splitter with left and right panels
    SplitterBox bottomLeft = new SplitterBox("Bottom Left", SplitterBox.Theme.SUCCESS);
    SplitterBox bottomRight = new SplitterBox("Bottom Right", SplitterBox.Theme.WARNING);
    Splitter bottomSplitter = new Splitter("bottomSplitter", bottomLeft, bottomRight)
            .setPositionRelative(75)
            .setAutosave(true);

    // Create master splitter with top and bottom nested splitters
    Splitter masterSplitter = new Splitter("masterSplitter", topSplitter, bottomSplitter)
            .setOrientation(Splitter.Orientation.VERTICAL)
            .setAutosave(true);

    // Create clear state button
    Button clearState = new Button("Clear State", e -> {
      topSplitter.cleanState();
      bottomSplitter.cleanState();
      masterSplitter.cleanState();
      Page.getCurrent().reload();
    });

    // Create reload button
    Element reload = new Element("dwc-icon-button")
            .setAttribute("name", "reload")
            .setAttribute("theme", "primary");
    reload.addEventListener("click", e -> Page.getCurrent().reload());

    // Create toolbar and main layout
    FlexLayout toolbar = FlexLayout.create(reload, clearState)
            .horizontal()
            .build();
    FlexLayout layout = FlexLayout.create(toolbar, masterSplitter)
            .vertical()
            .build()
            .setStyle("padding", "var(--dwc-space-m)")
            .setItemAlignment(FlexAlignment.END, toolbar);

    self.add(layout);
  }
}
