package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Div;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;

@Route
@FrameTitle("Toolbar Slots")
public class ToolbarSlotsView extends Composite<Div> {

  Div main = getBoundComponent();
  Button analyzeButton = new Button("Analyze data");
  Toolbar toolbar = new Toolbar();
  H1 title = new H1("webforJ App");
  H1 contentSlot = new H1("content");
  H1 endSlot = new H1("end");
  IconButton profile = new IconButton(TablerIcon.create("user-circle"));
  IconButton home = new IconButton(TablerIcon.create("home"));
  TextField textField = new TextField(TextField.Type.SEARCH);

  public ToolbarSlotsView() {

    toolbar.setTheme(Theme.PRIMARY);

    home.setTooltipText("Start slot");
    toolbar.addToStart(home);

    toolbar.addToTitle(new H1("Title Slot"));

    textField.setTooltipText("Content slot")
        .setPrefixComponent(TablerIcon.create("search"))
        .setWidth("45vw");
    toolbar.addToContent(textField);

    profile.setTooltipText("End slot");
    toolbar.addToEnd(profile);

    FlexLayout content = FlexLayout.create(new H1("Application Title"), new Paragraph("Content goes here"))
        .vertical()
        .build()
        .setPadding("0px 20px");

    main.add(toolbar, content);
  }
}