package control_demos.buttondemos;

import org.dwcj.App;
import org.dwcj.annotation.InlineStyleSheet;
import org.dwcj.component.window.Frame;
import org.dwcj.component.button.Button;
import org.dwcj.component.button.Button.TextVerticalAlignment;
import org.dwcj.exceptions.DwcjException;

@InlineStyleSheet("context://css/buttonstyles/alignment_styles.css")
public class ButtonAlignment extends App {
  @Override
  public void run() throws DwcjException {
    Frame panel = new Frame();
    panel.addClassName("Frame");

    Button buttonTop = new Button("TOP ALIGNMENT");
    Button buttonCenter = new Button("CENTER ALIGNMENT");
    Button buttonBottom = new Button("BOTTOM ALIGNMENT");

    panel.add(buttonTop, buttonCenter, buttonBottom);

    buttonTop.setTheme(Button.Theme.DEFAULT)
      .setVerticalAlignment(TextVerticalAlignment.TOP);

    buttonCenter.setTheme(Button.Theme.DEFAULT)
      .setVerticalAlignment(TextVerticalAlignment.CENTER);

    buttonBottom.setTheme(Button.Theme.DEFAULT)
      .setVerticalAlignment(TextVerticalAlignment.BOTTOM);
  }
}
