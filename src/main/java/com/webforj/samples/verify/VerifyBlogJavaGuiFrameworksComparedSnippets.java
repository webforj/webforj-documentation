package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import javax.swing.JButton;
import javax.swing.JLabel;

/** Verification of the snippets in the Java GUI frameworks post. */
public class VerifyBlogJavaGuiFrameworksComparedSnippets extends Composite<Div> {

  public VerifyBlogJavaGuiFrameworksComparedSnippets() {
    // The snippets below are compiled as nested classes.
  }

  // Example 1: the Swing handler
  public static class SwingSave {
    private final JLabel status = new JLabel();

    public JButton build() {
      JButton save = new JButton("Save");
      save.addActionListener(e -> status.setText("Saved"));
      return save;
    }
  }

  // Example 2: the same handler in a routed webforJ view
  @Route("save")
  public static class WebSave extends Composite<Div> {
    private final Paragraph status = new Paragraph();

    public WebSave() {
      Button save = new Button("Save", ButtonTheme.PRIMARY);
      save.onClick(e -> status.setText("Saved"));
      getBoundComponent().add(save, status);
    }
  }
}
