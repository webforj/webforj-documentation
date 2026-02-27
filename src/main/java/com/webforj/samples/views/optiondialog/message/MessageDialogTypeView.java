package com.webforj.samples.views.optiondialog.message;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Message Dialog Types")
public class MessageDialogTypeView extends Composite<Div> {

  public MessageDialogTypeView() {
    OptionDialog.showMessageDialog(
            "Your session will expire in 5 minutes due to inactivity. Save your work now to prevent any data loss.",
            "Session is expiring",
            MessageDialog.MessageType.WARNING);

  }
}
