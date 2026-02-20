package com.webforj.samples.views.markdownviewer;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("MarkdownViewer")
public class MarkdownViewerView extends Composite<Div> {

  public MarkdownViewerView() {
    Div self = getBoundComponent();
    self.setStyle("display", "flex")
        .setStyle("justifyContent", "center")
        .setStyle("padding", "var(--dwc-space-l)");

    Div card = new Div();
    card.setStyle("maxWidth", "600px")
        .setStyle("width", "100%")
        .setStyle("padding", "var(--dwc-space-l)")
        .setStyle("background", "var(--dwc-surface-3)")
        .setStyle("border", "1px solid var(--dwc-color-default)")
        .setStyle("borderRadius", "var(--dwc-border-radius-l)");

    MarkdownViewer viewer = new MarkdownViewer();
    viewer.setContent("""
        # Welcome to MarkdownViewer

        This component renders **bold**, *italic*, ~~strikethrough~~, and `inline code`.

        ## Lists

        - First item
        - Second item
        - Third item

        ## Blockquote

        > Markdown makes formatting easy
        > without writing HTML.

        ## Code Block

        ```java
        MarkdownViewer viewer = new MarkdownViewer();
        viewer.setContent("# Hello World");
        ```

        ## Emoticons

        Feeling happy :) or sad :( or surprised :o

        That's awesome! :D
        """);

    card.add(viewer);
    self.add(card);
  }
}