package com.webforj.samples.views.textarea;

import java.util.List;

import com.webforj.component.Composite;
import com.webforj.component.field.TextArea;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TEXT_AREA_WRAP)
@FrameTitle("Word Wrap and Line Wrapping Demo")
public class TextAreaWrapView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();
  TextArea textArea = new TextArea("Text Preview",
      "Honorificabilitudinitatibus califragilisticexpialidocious Taumatawhakatangihangakoauauotamateaturipukakapikimaungahoronukupokaiwhenuakitanatahu グレートブリテンおよび北アイルランド連合王国という言葉は本当に長い言葉");
  ChoiceBox styles = new ChoiceBox("Select a word wrap style");

  public TextAreaWrapView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("50px auto")
        .setMaxWidth("300px");

    textArea.setPlaceholder("Enter text to see wrapping behavior...")
        .setRows(2)
        .setWidth("100%")
        .setHeight("200px")
        .setLineWrap(true)
        .setWrapStyle(TextArea.WrapStyle.CHARACTER_BOUNDARIES);

    styles.insert(List.of(
        new ListItem(TextArea.WrapStyle.WORD_BOUNDARIES, "Word Boundaries"),
        new ListItem(TextArea.WrapStyle.CHARACTER_BOUNDARIES, "Character Boundaries")))
        .selectIndex(1)
        .setWidth("100%")
        .onSelect(event -> {
          ListItem selected = event.getSelectedItem();
          if (selected != null) {
            textArea.setWrapStyle((TextArea.WrapStyle) selected.getKey());
          }
        });

    self.add(styles, textArea);
  }
}
