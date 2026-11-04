---
sidebar_position: 3
title: Gebruikersinteractie
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
Je code kan op elke component `setValue` of `setEnabled` aanroepen, maar een gebruiker kan dat niet. Een gebruiker typt in een zichtbaar veld en klikt op een ingeschakelde knop. Een TestBed-test werkt zoals de gebruiker: via `UserInteraction`. Elk van de acties controleert of een gebruiker het gebaar zou kunnen maken, voert het uit en laat de component het melden aan de listeners, net zoals wanneer een gebruiker in de browser handelt.

Elke actie is afgerond voordat de volgende regel van de test wordt uitgevoerd, zodat de test het resultaat direct na het gebaar controleert.

<!-- INTRO_END -->

## Handelen als een gebruiker {#acting-like-a-user}

Het volgende formulier schrijft een e-mailadres in. De knop **Subscribe** blijft uitgeschakeld totdat het veld een adres bevat:

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

De test typt een adres en klikt op **Subscribe**: de twee gebaren die een gebruiker maakt:

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

Het typen meldt de wijziging aan de modify-listener van het veld, waardoor de knop wordt ingeschakeld. De daaropvolgende klik bereikt de listener van de knop, die de bevestiging toont.

## Acties {#verbs}

`UserInteraction` heeft één actie voor elk gebaar dat veel componenten gemeen hebben:

- `click` en `doubleClick` klikken op een component of op een item dat deze bevat, zoals een tabelrij of een boomknooppunt.
- `type` vervangt de tekst in een veld, `keypress` drukt een toets in het veld in en `focus` verplaatst de focus naar een component.
- `check` en `uncheck` stellen een selectievakje, keuzerondje of schakelaar in.
- `select`, `selectIndex`, `selectKey` en `deselect` wijzigen de selectie van lijsten, tabellen, bomen en tabbladen.
- `open`, `close` en `cancel` openen en sluiten panelen, dialoogvensters, laden en vervolgkeuzelijsten, en annuleren een aanmelding of upload.

Een actie meldt wat het gebaar van een gebruiker meldt, in dezelfde volgorde. Wanneer een tabblad wordt geselecteerd, wordt eerst gemeld dat de selectie van het vorige tabblad is opgeheven en daarna dat het nieuwe tabblad is geselecteerd. De muisknop, positie en modificatietoetsen van een gebaar geef je op in `MouseOptions` en `KeyOptions`, het laatste argument van `click`, `doubleClick` en `keypress`.

De referentie van <JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> vermeldt welke componenten op elke actie reageren.

## Klikken bereikt de omliggende componenten {#clicks-reach-the-components-around}

Een klik in de browser bereikt de aangeklikte component en vervolgens elke component eromheen. Een actie doet hetzelfde. In de volgende kaart luistert alleen de kaart naar klikken; de avatar erin doet dat niet:

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

De test klikt op de avatar, de plek waarop een gebruiker zou klikken:

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

De klik bereikt de listener van de kaart rondom de avatar, die het profiel toont.

## Wat een gebruiker niet kan doen {#what-a-user-cant-do}

Een actie wordt alleen uitgevoerd als een gebruiker het gebaar zou kunnen maken. Voordat er een adres is getypt, is **Subscribe** in het [nieuwsbrief-formulier](#acting-like-a-user) uitgeschakeld. De test kan er dus ook niet op klikken:

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

De klik veroorzaakt een `InteractionRejectedException` en de listener van de knop wordt nooit uitgevoerd. Het bericht, dat de test controleert, vermeldt de component en de reden, gevolgd door de componentstructuur. Zo laat een mislukte test zien wat er op de pagina stond. Een gebaar wordt afgewezen wanneer de component zelf of een component eromheen is uitgeschakeld of verborgen, of zich in een gesloten dialoogvenster of lade bevindt. Typen in een alleen-lezen veld wordt afgewezen, net als een gebaar dat de component niet toestaat, zoals het sluiten van een melding zonder sluitknop.

:::tip Niet-gekoppelde componenten
Een niet-gekoppelde component laat elke actie mislukken met een `IllegalStateException` die je vraagt om de component eerst aan de context toe te voegen.
:::

## Gebaren van één component {#gestures-of-one-component}

Sommige gebaren zijn specifiek voor één component, zoals een schuifregelaar verslepen, een tabel sorteren of een boomknooppunt uitvouwen. Elke webforJ-component met zulke gebaren heeft een interactieklasse. `UserInteraction.perform` voert een van de gebaren uit na dezelfde controle als bij de acties. De volgende besturing toont het volume waarop de schuifregelaar is ingesteld:

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

De test versleept de schuifregelaar naar 80 en probeert daarna 150, een waarde buiten het bereik van de schuifregelaar:

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

Het verslepen naar 80 bereikt de slide-listener, die het nieuwe volume toont. Het verslepen naar 150 wordt net als een actie afgewezen, omdat een gebruiker de schuifregelaar niet voorbij het einde kan slepen. Het bericht vermeldt het bereik van de schuifregelaar en de waarde die de test heeft opgegeven.

Elke webforJ-component met eigen gebaren heeft een interactieklasse die naar de component is vernoemd, zoals `SliderInteraction` voor `Slider`. Elke statische methode van die klasse is één gebaar. Raadpleeg de Javadoc van de interactieklasse om te zien welke gebaren een component ondersteunt, bijvoorbeeld <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> met `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> met `sort`, `moveColumn` en `resizeColumn`, of <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> met `expand` en `collapse`. De <JavadocLink type="automation" location="index">webforj-automation-referentie</JavadocLink> bevat ze allemaal, met één pakket per component onder `com.webforj.automation.act.component`.

## Uploads en downloads {#uploads-and-downloads}

Een gebruiker uploadt bestanden vanaf de eigen computer, dus de test geeft de bestanden eerst aan de context. De volgende upload toont de namen van de bestanden die deze ontvangt:

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

De test kiest twee bestanden met `pickFile` en uploadt ze vervolgens op naam:

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

Een bestand dat het maximumaantal bestanden overschrijdt, groter is dan de maximale grootte of een niet-geaccepteerd type heeft, wordt gemeld aan de afwijzingslisteners en niet geüpload.

De context bewaart de bestanden die je app naar de gebruiker stuurt. De volgende knop verstuurt een rapport:

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

De test klikt op de knop en leest het bestand dat de gebruiker ontvangt uit `getDownloads()`:

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

## Gebeurtenissen zonder gebaar {#events-without-a-gesture}

Sommige gebeurtenissen bereiken een component na een gebaar vanuit de browser in plaats van vanuit de gebruiker, zoals de melding dat een lade volledig is gesloten. De volgende weergave toont of de help-lade geopend is:

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

`close` sluit de lade. Daarna activeert de test de sluitgebeurtenis die de browser meldt met `UserInteraction.fireEvent`:

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

`fireEvent` bezorgt de gebeurtenis aan de listeners van de component en controleert alleen of de component gekoppeld is, omdat de gebeurtenis niet van de gebruiker afkomstig is. De open- en sluitgebeurtenissen van dialoogvensters, laden en de lade van een app-layout, evenals de voortgangsgebeurtenissen van een upload, zijn van dit type. Een `Element` luistert naar gebeurtenissen op naam. `UserInteraction.fireEvent(element, eventName, data)` bezorgt een gebeurtenis aan de listeners met die naam.

## Eigen gebaren {#gestures-of-your-own}

`perform` accepteert elke `Consumer` van een component, zodat een test een eigen gebaar kan definiëren. De volgende test schrijft zich in via het [nieuwsbrief-formulier](#acting-like-a-user) met een gebaar dat typt en klikt binnen het formulier dat de test ontvangt:

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

De test leest als een stap die een gebruiker neemt, en andere tests kunnen hetzelfde gebaar hergebruiken.

## Eigen componenten {#your-own-components}

Een component van je app reageert zonder extra code op de acties van de [concern-interfaces](/docs/building-ui/component-fundamentals#concern-interfaces) die deze implementeert. Een component met een `Boolean`-waarde die `HasValue` implementeert, reageert op `check`, `uncheck` en `click` zoals een selectievakje. De volgende schakelaar houdt bij of een item favoriet is:

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

De test schakelt de favoriet in en klikt er vervolgens op:

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

Op dezelfde manier reageert een component die `HasReadOnly` samen met `HasValue` of `HasText` implementeert op `type`. Een component die de selectie-interfaces van webforJ implementeert, reageert op `select`.

### Een component acties leren {#teaching-a-component-the-verbs}

Als geen enkele concern-interface geschikt is, definieert een interactieklasse hoe een actie werkt op jouw component. De volgende uitklapper toont de details wanneer deze is uitgeklapt:

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

De interactieklasse vermeldt de componentklasse in de constructor en implementeert één interface voor elke actie waarop deze reageert, hier `OpenInteraction` en `CloseInteraction`. `reject` maakt de uitzondering voor een gebaar dat de component niet toestaat:

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

De klasse wordt met de volledig gekwalificeerde naam vermeld in een bestand onder de testresources:

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

De test opent en sluit de uitklapper vervolgens met de acties. Als deze voor de tweede keer wordt gesloten, wordt de actie afgewezen met de reden die de klasse heeft opgegeven:

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

Een interactie van je app reageert op een actie vóór de interactie die webforJ voor dezelfde component levert. Als twee interacties van je app op dezelfde actie voor dezelfde component reageren, mislukt de test met een bericht waarin beide worden vermeld. Om alleen voor bepaalde instanties van een klasse te reageren, overschrijf je `isSupported(component)`.
