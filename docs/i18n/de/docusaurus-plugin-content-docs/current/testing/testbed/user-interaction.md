---
sidebar_position: 3
title: Benutzerinteraktion
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
Ihr Code kann für jede Komponente `setValue` oder `setEnabled` aufrufen, ein Benutzer jedoch nicht. Ein Benutzer gibt Text in ein sichtbares Feld ein und klickt auf eine aktivierte Schaltfläche. Ein Test mit TestBed verhält sich wie der Benutzer: über `UserInteraction`. Jede ihrer Aktionen prüft, ob ein Benutzer die Geste ausführen könnte, führt sie aus und lässt die Komponente sie wie bei einer Benutzeraktion im Browser an ihre Listener melden.

Jede Aktion ist abgeschlossen, bevor die nächste Zeile des Tests ausgeführt wird. So prüft der Test das Ergebnis unmittelbar nach der Geste.

<!-- INTRO_END -->

## Wie ein Benutzer handeln {#acting-like-a-user}

Das folgende Formular abonniert eine E-Mail-Adresse. Die Schaltfläche **Subscribe** bleibt deaktiviert, bis das Feld eine Adresse enthält:

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

Der Test gibt eine Adresse ein und klickt auf **Subscribe** – die beiden Gesten, die ein Benutzer ausführt:

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

Die Eingabe meldet die Änderung an den Modify-Listener des Felds, wodurch die Schaltfläche aktiviert wird. Der anschließende Klick erreicht den Listener der Schaltfläche, der die Bestätigung anzeigt.

## Aktionen {#verbs}

`UserInteraction` bietet für jede Geste, die viele Komponenten gemeinsam haben, eine Aktion:

- `click` und `doubleClick` klicken auf eine Komponente oder auf ein darin enthaltenes Element, etwa eine Tabellenzeile oder einen Baumknoten.
- `type` ersetzt den Text eines Felds, `keypress` drückt eine Taste darin, und `focus` setzt den Fokus auf eine Komponente.
- `check` und `uncheck` aktivieren bzw. deaktivieren ein Kontrollkästchen, ein Optionsfeld oder einen Schalter.
- `select`, `selectIndex`, `selectKey` und `deselect` ändern die Auswahl in Listen, Tabellen, Bäumen und Registerkartenbereichen.
- `open`, `close` und `cancel` öffnen und schließen Panels, Dialoge, Drawer und Dropdowns und brechen eine Anmeldung oder einen Upload ab.

Eine Aktion meldet, was die Geste eines Benutzers meldet, und zwar in derselben Reihenfolge. Beim Auswählen eines Tabs wird zuerst gemeldet, dass der vorherige Tab die Auswahl verloren hat, und anschließend die Auswahl des neuen Tabs. Die Maustaste, die Position und die Zusatztasten einer Geste werden in `MouseOptions` und `KeyOptions` angegeben – den letzten Argumenten von `click`, `doubleClick` und `keypress`.

Die Referenz zu <JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> listet die Komponenten auf, die auf jede Aktion reagieren.

## Klicks erreichen die umgebenden Komponenten {#clicks-reach-the-components-around}

Ein Klick im Browser erreicht die angeklickte Komponente und anschließend alle sie umgebenden Komponenten. Eine Aktion verhält sich genauso. Bei der folgenden Karte lauscht nur die Karte auf Klicks, nicht aber der darin enthaltene Avatar:

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

Der Test klickt auf den Avatar – also auf die Stelle, auf die auch ein Benutzer klicken würde:

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

Der Klick erreicht den Listener der den Avatar umgebenden Karte, der das Profil anzeigt.

## Was ein Benutzer nicht tun kann {#what-a-user-cant-do}

Eine Aktion wird nur ausgeführt, wenn ein Benutzer die Geste ausführen könnte. Solange noch keine Adresse eingegeben wurde, ist **Subscribe** im [Newsletter-Formular](#acting-like-a-user) deaktiviert. Daher kann auch der Test nicht darauf klicken:

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

Der Klick löst eine `InteractionRejectedException` aus, und der Listener der Schaltfläche wird nicht ausgeführt. Die vom Test überprüfte Meldung nennt die Komponente und den Grund, gefolgt vom Komponentenbaum. So zeigt ein fehlgeschlagener Test, was die Seite enthielt. Eine Geste wird abgelehnt, wenn die Komponente selbst oder eine sie umgebende Komponente deaktiviert oder ausgeblendet ist oder sich in einem geschlossenen Dialog oder Drawer befindet. Die Eingabe in ein schreibgeschütztes Feld wird ebenso abgelehnt wie eine Geste, die die Komponente nicht zulässt, etwa das Schließen eines Alerts ohne Schaltfläche zum Schließen.

:::tip Nicht angehängte Komponenten
Bei einer nicht angehängten Komponente schlägt jede Aktion mit einer `IllegalStateException` fehl, die dazu auffordert, sie zuerst dem Kontext hinzuzufügen.
:::

## Gesten einer einzelnen Komponente {#gestures-of-one-component}

Manche Gesten gehören zu einer einzelnen Komponente, etwa das Verschieben eines Sliders, das Sortieren einer Tabelle oder das Erweitern eines Baumknotens. Jede webforJ-Komponente mit solchen Gesten hat eine Interaktionsklasse. `UserInteraction.perform` führt eine ihrer Gesten aus, nachdem dieselben Prüfungen wie bei den anderen Aktionen durchgeführt wurden. Das folgende Steuerelement zeigt die Lautstärke an, auf die der Slider eingestellt ist:

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

Der Test verschiebt den Slider auf 80 und versucht anschließend den Wert 150, der außerhalb des Slider-Bereichs liegt:

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

Das Verschieben auf 80 erreicht den Slide-Listener, der die neue Lautstärke anzeigt. Das Verschieben auf 150 wird wie eine Aktion abgelehnt, weil kein Benutzer den Slider über sein Ende hinaus ziehen kann. Die Meldung nennt den Bereich des Sliders und den vom Test angeforderten Wert.

Jede webforJ-Komponente mit eigenen Gesten hat eine nach der Komponente benannte Interaktionsklasse, etwa `SliderInteraction` für `Slider`. Jede statische Methode dieser Klasse steht für eine Geste. Welche Gesten eine Komponente bietet, erfahren Sie in der Javadoc ihrer Interaktionsklasse, etwa <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> mit `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> mit `sort`, `moveColumn` und `resizeColumn` oder <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> mit `expand` und `collapse`. Die <JavadocLink type="automation" location="index">Referenz zu webforj-automation</JavadocLink> listet sie alle auf, jeweils ein Paket pro Komponente unter `com.webforj.automation.act.component`.

## Uploads und Downloads {#uploads-and-downloads}

Ein Benutzer lädt Dateien von seinem eigenen Gerät hoch, daher übergibt der Test die Dateien zunächst an den Kontext. Der folgende Upload listet die Namen der empfangenen Dateien auf:

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

Der Test wählt mit `pickFile` zwei Dateien aus und lädt sie anschließend anhand ihrer Namen hoch:

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

Eine Datei, die die maximale Dateianzahl überschreitet, größer als die maximale Dateigröße ist oder einen nicht akzeptierten Dateityp hat, wird den Reject-Listenern gemeldet und nicht hochgeladen.

Die Dateien, die Ihre App an den Benutzer sendet, werden vom Kontext gespeichert. Die folgende Schaltfläche sendet einen Bericht:

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

Der Test klickt auf die Schaltfläche und liest die Datei, die der Benutzer über `getDownloads()` erhält:

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

## Ereignisse ohne Geste {#events-without-a-gesture}

Manche Ereignisse erreichen eine Komponente nach einer Geste aus dem Browser statt vom Benutzer, etwa die Meldung, dass ein Drawer vollständig geschlossen wurde. Die folgende Ansicht zeigt an, ob der Hilfebereich geöffnet ist:

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

`close` schließt den Drawer. Anschließend löst der Test mit `UserInteraction.fireEvent` das Schließereignis aus, das der Browser meldet:

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

`fireEvent` übergibt das Ereignis an die Listener der Komponente und prüft nur, ob die Komponente angehängt ist, da das Ereignis nicht vom Benutzer ausgeht. Die Öffnungs- und Schließereignisse von Dialogen, Drawern und dem Drawer eines App-Layouts sowie die Fortschrittsereignisse eines Uploads gehören zu dieser Kategorie. Ein `Element` lauscht auf Ereignisse anhand ihres Namens. `UserInteraction.fireEvent(element, eventName, data)` übergibt ein Ereignis dieses Namens an seine Listener.

## Eigene Gesten {#gestures-of-your-own}

`perform` akzeptiert jeden `Consumer` einer Komponente, sodass ein Test eine eigene Geste definieren kann. Der folgende Test abonniert über das [Newsletter-Formular](#acting-like-a-user) mit einer Geste, die im übergebenen Formular Text eingibt und auf eine Schaltfläche klickt:

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

Der Test liest sich wie der Handlungsschritt eines Benutzers, und andere Tests können dieselbe Geste wiederverwenden.

## Eigene Komponenten {#your-own-components}

Eine Komponente Ihrer App reagiert ohne zusätzlichen Code auf die Aktionen der [Concern-Schnittstellen](/docs/building-ui/component-fundamentals#concern-interfaces), die sie implementiert. Eine Komponente mit einem `Boolean`-Wert reagiert über `HasValue` auf `check`, `uncheck` und `click` wie ein Kontrollkästchen. Der folgende Schalter speichert, ob ein Element als Favorit markiert ist:

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

Der Test aktiviert den Schalter und klickt anschließend darauf:

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

Ebenso reagiert eine Komponente, die `HasReadOnly` zusammen mit `HasValue` oder `HasText` implementiert, auf `type`. Eine Komponente, die die Auswahl-Schnittstellen von webforJ implementiert, reagiert auf `select`.

### Einer Komponente die Aktionen beibringen {#teaching-a-component-the-verbs}

Wenn keine Concern-Schnittstelle passt, legt eine Interaktionsklasse fest, wie eine Aktion für Ihre Komponente funktioniert. Der folgende Expander zeigt seine Details an, sobald er aufgeklappt ist:

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

Die Interaktionsklasse gibt in ihrem Konstruktor die Komponentenklasse an und implementiert für jede unterstützte Aktion eine Schnittstelle – hier `OpenInteraction` und `CloseInteraction`. `reject` erstellt die Ausnahme für eine Geste, die die Komponente nicht zulässt:

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

Die Klasse wird in einer Datei unter den Testressourcen mit ihrem voll qualifizierten Namen aufgeführt:

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

Anschließend öffnet und schließt der Test den Expander mit den Aktionen. Beim zweiten Schließversuch wird die vom Klassenimplementierer angegebene Begründung zurückgegeben:

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

Eine Interaktion Ihrer App wird für eine Komponente vor der von webforJ bereitgestellten Interaktion für dieselbe Komponente verwendet. Wenn zwei Interaktionen Ihrer App dieselbe Aktion für dieselbe Komponente unterstützen, schlägt der Test mit einer Meldung fehl, die beide nennt. Um nur für bestimmte Instanzen einer Klasse zu gelten, überschreiben Sie `isSupported(component)`.
