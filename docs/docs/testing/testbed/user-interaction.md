---
sidebar_position: 3
title: User interaction
description: Act on the components of a TestBed test through the same gestures a user makes, and get the same events a user causes.
---

Your code can call `setValue` or `setEnabled` on any component, but a user can't. A user types into a field they can see and clicks a button that's enabled. A TestBed test acts the way the user does, through `UserInteraction`. Each of its verbs checks that a user could make the gesture, makes it, and lets the component report it to its listeners, as it does when a user acts in the browser.

Each verb finishes before the next line of the test runs, so the test checks the result right after the gesture.

<!-- INTRO_END -->

## Acting like a user {#acting-like-a-user}

The following form subscribes an email address. Its **Subscribe** button stays disabled until the field holds an address:

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

The test types an address and clicks **Subscribe**, the two gestures a user makes:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Typing reports the change to the modify listener of the field, which enables the button. The click that follows reaches the listener of the button, which shows the confirmation.

## Verbs {#verbs}

`UserInteraction` has one verb for each gesture that many components share:

- `click` and `doubleClick` press a component, or an item it holds such as a row of a table or a node of a tree.
- `type` replaces the text of a field, `keypress` presses a key in it, and `focus` moves the focus onto a component.
- `check` and `uncheck` set a checkbox, a radio button or a switch.
- `select`, `selectIndex`, `selectKey` and `deselect` change the selection of lists, tables, trees and tabbed panes.
- `open`, `close` and `cancel` open and close panels, dialogs, drawers and dropdowns, and cancel a login or an upload.

A verb reports what a user's gesture reports, in the same order. Selecting a tab reports that the previous tab lost the selection before it reports the new one. The mouse button, the position and the modifier keys of a gesture go in `MouseOptions` and `KeyOptions`, the last argument of `click`, `doubleClick` and `keypress`.

The <JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> reference lists the components that answer each verb.

## Clicks reach the components around {#clicks-reach-the-components-around}

A click in the browser reaches the clicked component and then every component around it, and a verb does the same. In the following card, only the card listens for clicks, and the avatar inside it doesn't:

```java title="MemberCard.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class MemberCard extends Composite<Div> {

  public MemberCard() {
    Paragraph profile = new Paragraph();
    Card card = new Card(new Avatar("Ada Lovelace"));
    card.onClick(e -> profile.setText("Profile of Ada Lovelace"));

    getBoundComponent().add(card, profile);
  }
}
```

The test clicks the avatar, the place a user clicks:

```java title="MemberCardTest.java" showLineNumbers
@Test
void shouldOpenTheProfileWhenTheUserClicksTheAvatar(WebforjTestbed context) {
  context.add(new MemberCard());

  UserInteraction.click(ComponentLocator.of(Avatar.class).locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Profile of Ada Lovelace")
          .isPresent());
}
```

The click reaches the listener of the card around the avatar, which shows the profile.

## What a user can't do {#what-a-user-cant-do}

A verb runs only when a user could make the gesture. Before an address is typed, **Subscribe** in the [newsletter form](#acting-like-a-user) is disabled, so the test can't click it either:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldRejectTheClickWhileNoEmailIsTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.click(subscribe));

  assertEquals(
      """
      The component Button is disabled and a user cannot interact with it.
      - Frame:
        - NewsletterForm [composite=Div] [node=div]:
          - TextField [label=Email]
          - Button "Subscribe" [disabled]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
  assertFalse(
      ComponentLocator.of(Paragraph.class)
          .byTextContaining("Subscribed")
          .isPresent());
}
```

The click throws an `InteractionRejectedException`, and the listener of the button never runs. Its message, which the test checks, names the component and the reason, followed by the component tree, so a failed test shows what the page held. A gesture is rejected when the component, or a component around it, is disabled or hidden, or sits inside a closed dialog or drawer. Typing into a read only field is rejected, and so is a gesture the component doesn't allow, such as closing an alert that has no close button.

:::tip Components that aren't attached
A component that isn't attached fails every verb with an `IllegalStateException` that asks you to add it to the context first.
:::

## Gestures of one component {#gestures-of-one-component}

Some gestures belong to a single component, such as sliding a slider, sorting a table, or expanding a tree node. Each webforJ component with such gestures has an interaction class, and `UserInteraction.perform` runs one of its gestures after the same check the verbs run. The following control shows the volume its slider is set to:

```java title="VolumeControl.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.slider.Slider;

public class VolumeControl extends Composite<Div> {

  public VolumeControl() {
    Paragraph volume = new Paragraph("Volume 50");
    Slider slider = new Slider(50, 0, 100);
    slider.onSlide(e -> volume.setText("Volume " + e.getValue()));

    getBoundComponent().add(slider, volume);
  }
}
```

The test slides to 80, then tries 150, a value outside the range of the slider:

```java title="VolumeControlTest.java" showLineNumbers
@Test
void shouldShowTheVolumeTheUserSlidTo(WebforjTestbed context) {
  context.add(new VolumeControl());

  var slider = ComponentLocator.of(Slider.class).locate();

  UserInteraction.perform(slider, SliderInteraction.slide(80));

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Volume 80").isPresent());

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.perform(slider, SliderInteraction.slide(150)));

  assertEquals(
      """
      The component Slider ranges from 0 to 100 and a user cannot slide it to 150.
      - Frame:
        - VolumeControl [composite=Div] [node=div]:
          - Slider [min=0] [max=100]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

Sliding to 80 reaches the slide listener, which shows the new volume. Sliding to 150 is rejected like a verb, because no user can drag the slider past its end. The message states the range of the slider and the value the test asked for.

Each webforJ component with gestures of its own has an interaction class named after the component, such as `SliderInteraction` for `Slider`. Every static method of that class is one gesture. To see the gestures a component offers, open the Javadoc of its interaction class, for example <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> with `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> with `sort`, `moveColumn` and `resizeColumn`, or <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> with `expand` and `collapse`. The <JavadocLink type="automation" location="index">webforj-automation reference</JavadocLink> lists all of them, one package per component below `com.webforj.automation.act.component`.

## Uploads and downloads {#uploads-and-downloads}

A user uploads files from their own machine, so the test hands the files to the context first. The following upload lists the names of the files it receives:

```java title="PhotoUpload.java" showLineNumbers
import com.webforj.UploadedFile;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.upload.Upload;
import java.util.stream.Collectors;

public class PhotoUpload extends Composite<Div> {

  public PhotoUpload() {
    Paragraph received = new Paragraph();
    Upload upload = new Upload();
    upload.onUpload(
        e ->
            received.setText(
                e.getFiles().stream()
                    .map(UploadedFile::getClientName)
                    .collect(Collectors.joining(", "))));

    getBoundComponent().add(upload, received);
  }
}
```

The test picks two files with `pickFile`, then uploads them by name:

```java title="PhotoUploadTest.java" showLineNumbers
@Test
void shouldReceiveThePhotosTheUserUploaded(WebforjTestbed context) {
  context.add(new PhotoUpload());

  var upload = ComponentLocator.of(Upload.class).locate();

  context.pickFile("beach.jpg", "beach".getBytes(StandardCharsets.UTF_8));
  context.pickFile("sunset.png", "sunset".getBytes(StandardCharsets.UTF_8));
  UserInteraction.perform(
      upload, UploadInteraction.upload("beach.jpg", "sunset.png"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("beach.jpg, sunset.png")
          .isPresent());
}
```

A file beyond the maximum number of files, larger than the maximum size, or of a type the upload doesn't accept is reported to the reject listeners and isn't uploaded.

The files your app sends to the user are kept by the context. The following button sends a report:

```java title="ReportDownload.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import java.nio.charset.StandardCharsets;

public class ReportDownload extends Composite<Div> {

  public ReportDownload() {
    Button download =
        new Button(
            "Download report",
            e ->
                Page.getCurrent()
                    .download(
                        "Orders: 42".getBytes(StandardCharsets.UTF_8),
                        "report.txt"));

    getBoundComponent().add(download);
  }
}
```

The test clicks the button and reads the file the user receives from `getDownloads()`:

```java title="ReportDownloadTest.java" showLineNumbers
@Test
void shouldSendTheReportWhenTheUserClicksDownload(WebforjTestbed context) {
  context.add(new ReportDownload());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Download report").locate());

  var report = context.getDownloads().get(0);
  assertEquals("report.txt", report.getName());
  assertEquals(
      "Orders: 42", new String(report.getContents(), StandardCharsets.UTF_8));
}
```

## Events without a gesture {#events-without-a-gesture}

Some events reach a component after a gesture, from the browser rather than from the user, such as the report that a drawer finished closing. The following view shows whether its help drawer is open:

```java title="HelpDrawer.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class HelpDrawer extends Composite<Div> {

  public HelpDrawer() {
    Paragraph status = new Paragraph("Help is open");
    Drawer drawer = new Drawer("Help");
    drawer.onClose(e -> status.setText("Help is closed"));
    drawer.open();

    getBoundComponent().add(drawer, status);
  }
}
```

`close` closes the drawer, and the test then fires the close event the browser reports with `UserInteraction.fireEvent`:

```java title="HelpDrawerTest.java" showLineNumbers
@Test
void shouldReportTheClosedHelp(WebforjTestbed context) {
  context.add(new HelpDrawer());

  var drawer = ComponentLocator.of(Drawer.class).locate();

  UserInteraction.close(drawer);
  UserInteraction.fireEvent(new DrawerCloseEvent(drawer, Map.of()));

  assertFalse(drawer.isOpened());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Help is closed")
          .isPresent());
}
```

`fireEvent` delivers the event to the listeners of its component and checks only that the component is attached, since the event doesn't come from the user. The open and close events of dialogs, drawers and the drawer of an app layout, and the progress events of an upload, are of this kind. An `Element` listens to events by name, and `UserInteraction.fireEvent(element, eventName, data)` delivers one to its listeners of that name.

## Gestures of your own {#gestures-of-your-own}

`perform` takes any `Consumer` of a component, so a test can name a gesture of its own. The following test subscribes through the [newsletter form](#acting-like-a-user) with a gesture that types and clicks inside the form it receives:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeThroughTheGestureOfTheTest(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var form = ComponentLocator.of(NewsletterForm.class).locate();

  UserInteraction.perform(form, subscribe("ada@example.com"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}

private static Consumer<NewsletterForm> subscribe(String address) {
  return form -> {
    UserInteraction.type(
        ComponentLocator.of(TextField.class).within(form).locate(), address);
    UserInteraction.click(
        ComponentLocator.of(Button.class)
            .within(form)
            .byText("Subscribe")
            .locate());
  };
}
```

The test reads like the step a user takes, and other tests can share the same gesture.

## Your own components {#your-own-components}

A component of your app answers the verbs of the [concern interfaces](/docs/building-ui/component-fundamentals#concern-interfaces) it implements, with no extra code. A component whose value is a `Boolean`, through `HasValue`, answers `check`, `uncheck` and `click` the way a checkbox does. The following toggle holds whether an item is a favorite:

```java title="FavoriteToggle.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Span;
import com.webforj.concern.HasValue;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

public class FavoriteToggle
    extends Composite<Span> implements HasValue<FavoriteToggle, Boolean> {
  private final EventDispatcher dispatcher = new EventDispatcher();
  private boolean favorite = false;

  public FavoriteToggle() {
    getBoundComponent().setText("Add to favorites");
  }

  @Override
  public Boolean getValue() {
    return favorite;
  }

  @Override
  public FavoriteToggle setValue(Boolean value) {
    favorite = value;
    getBoundComponent()
        .setText(favorite ? "Remove from favorites" : "Add to favorites");
    dispatcher.dispatchEvent(new ValueChangeEvent<>(this, value));

    return this;
  }

  @Override
  public ListenerRegistration<ValueChangeEvent<Boolean>> addValueChangeListener(
      EventListener<ValueChangeEvent<Boolean>> listener) {
    return dispatcher.addListener(ValueChangeEvent.class, listener);
  }
}
```

The test checks the toggle, then clicks it:

```java title="FavoriteToggleTest.java" showLineNumbers
@Test
void shouldToggleTheFavoriteLikeACheckbox(WebforjTestbed context) {
  context.add(new FavoriteToggle());

  var toggle = ComponentLocator.of(FavoriteToggle.class).locate();
  var label = ComponentLocator.of(Span.class).locate();

  UserInteraction.check(toggle);
  assertEquals("Remove from favorites", label.getText());

  UserInteraction.click(toggle);
  assertEquals("Add to favorites", label.getText());
}
```

In the same way, a component that implements `HasReadOnly` together with `HasValue` or `HasText` answers `type`, and one that implements webforJ's selection interfaces answers `select`.

### Teaching a component the verbs {#teaching-a-component-the-verbs}

When no concern interface fits, an interaction class defines how a verb works on your component. The following expander shows its details once it's expanded:

```java title="Expander.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class Expander extends Composite<Div> {
  private final Paragraph details = new Paragraph("Ships in 2 days");

  public Expander() {
    details.setVisible(false);
    getBoundComponent().add(new Paragraph("Shipping"), details);
  }

  public void expand() {
    details.setVisible(true);
  }

  public void collapse() {
    details.setVisible(false);
  }

  public boolean isExpanded() {
    return details.isVisible();
  }
}
```

The interaction class names the component class in its constructor and implements one interface for each verb it answers, here `OpenInteraction` and `CloseInteraction`. `reject` creates the exception for a gesture the component doesn't allow:

```java title="ExpanderInteraction.java" showLineNumbers
import com.webforj.automation.act.AbstractComponentInteraction;
import com.webforj.automation.act.CloseInteraction;
import com.webforj.automation.act.OpenInteraction;
import com.webforj.samples.views.testbed.Expander;

public final class ExpanderInteraction
    extends AbstractComponentInteraction<Expander>
    implements OpenInteraction<Expander>, CloseInteraction<Expander> {

  public ExpanderInteraction() {
    super(Expander.class);
  }

  @Override
  public void open(Expander expander) {
    expander.expand();
  }

  @Override
  public void close(Expander expander) {
    if (!expander.isExpanded()) {
      throw reject(expander, "is already collapsed");
    }

    expander.collapse();
  }
}
```

The class is listed by its fully qualified name in a file under the test resources:

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

The test then opens and closes the expander with the verbs. Closing it a second time is rejected with the reason the class gave:

```java title="ExpanderTest.java" showLineNumbers
@Test
void shouldOpenAndCloseTheDetailsThroughTheVerbs(WebforjTestbed context) {
  context.add(new Expander());

  var expander = ComponentLocator.of(Expander.class).locate();
  var details = ComponentLocator.of(Paragraph.class)
      .byText("Ships in 2 days")
      .locate();

  UserInteraction.open(expander);
  verifyThat(details).isVisible();

  UserInteraction.close(expander);
  verifyThat(details).isHidden();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.close(expander));

  assertEquals(
      """
      The component Expander is already collapsed.
      - Frame:
        - Expander [composite=Div] [node=div]:
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

An interaction of your app answers a verb before the one webforJ ships for the same component. Two interactions of your app that answer the same verb for the same component fail the test with a message that names both. To answer only for some instances of a class, override `isSupported(component)`.
