package com.webforj.samples.views.markdownviewer;

import com.webforj.component.Composite;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.router.annotation.Route;

@Route
public class MarkdownViewerView extends Composite<MarkdownViewer> {

  public MarkdownViewerView() {
    getBoundComponent().setContent("""
        # Welcome to MarkdownViewer

        Renders **bold**, *italic*, `code`, and links.

        - List items work too
        - With full markdown support 🎉
        """);
  }
}