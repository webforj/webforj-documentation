---
sidebar_position: 3
title: Käyttäjän vuorovaikutus
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
Koodisi voi kutsua minkä tahansa komponentin `setValue`- tai `setEnabled`-metodia, mutta käyttäjä ei voi. Käyttäjä kirjoittaa näkyvissä olevaan kenttään ja napsauttaa käytössä olevaa painiketta. TestBed-testi toimii kuten käyttäjä `UserInteraction`-luokan kautta. Sen jokainen verbi tarkistaa, että käyttäjä voisi tehdä kyseisen toiminnon, tekee sen ja antaa komponentin ilmoittaa siitä kuuntelijoilleen samalla tavalla kuin käyttäjän toimiessa selaimessa.

Kukin verbi suoritetaan loppuun ennen testin seuraavan rivin suoritusta, joten testi tarkistaa tuloksen heti toiminnon jälkeen.

<!-- INTRO_END -->

## Käyttäjän tavoin toimiminen {#acting-like-a-user}

Seuraavassa lomakkeessa tilataan sähköpostiosoite. **Subscribe**-painike pysyy poissa käytöstä, kunnes kentässä on osoite:

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

Testi kirjoittaa osoitteen ja napsauttaa **Subscribe**-painiketta eli tekee samat kaksi toimintoa kuin käyttäjä:

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

Kirjoittaminen ilmoittaa muutoksesta kentän muokkauskuuntelijalle, joka ottaa painikkeen käyttöön. Sen jälkeen tehtävä napsautus saavuttaa painikkeen kuuntelijan, joka näyttää vahvistuksen.

## Verbit {#verbs}

`UserInteraction` sisältää verbin kutakin monille komponenteille yhteistä toimintoa varten:

- `click` ja `doubleClick` napsauttavat komponenttia tai sen sisältämää kohdetta, kuten taulukon riviä tai puun solmua.
- `type` korvaa kentän tekstin, `keypress` painaa siinä näppäintä ja `focus` siirtää kohdistuksen komponenttiin.
- `check` ja `uncheck` valitsevat valintaruudun, valintapainikkeen tai kytkimen tai poistavat sen valinnan.
- `select`, `selectIndex`, `selectKey` ja `deselect` muuttavat luetteloiden, taulukoiden, puiden ja välilehtinäkymien valintaa.
- `open`, `close` ja `cancel` avaavat ja sulkevat paneeleja, valintaikkunoita, sivupaneeleja ja pudotusvalikoita sekä peruuttavat kirjautumisen tai latauksen.

Verbi ilmoittaa käyttäjän toiminnasta samalla tavalla ja samassa järjestyksessä kuin käyttäjän tekemä toiminto. Kun välilehti valitaan, ensin ilmoitetaan edellisen välilehden valinnan poistumisesta ja vasta sitten uuden välilehden valinnasta. Toiminnon hiiren painike, sijainti ja muuntonäppäimet annetaan `MouseOptions`- ja `KeyOptions`-olioissa, jotka ovat `click`-, `doubleClick`- ja `keypress`-metodien viimeisiä argumentteja.

<JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink>-viitteessä luetellaan komponentit, jotka vastaavat kuhunkin verbiin.

## Napsautus tavoittaa ympäröivät komponentit {#clicks-reach-the-components-around}

Selaimessa napsautus tavoittaa napsautetun komponentin ja sen jälkeen kaikki sitä ympäröivät komponentit. Verbi toimii samalla tavalla. Seuraavassa kortissa vain kortti kuuntelee napsautuksia, ei sen sisällä oleva avatar:

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

Testi napsauttaa avataria eli sitä kohtaa, jota käyttäjäkin napsauttaisi:

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

Napsautus tavoittaa avataria ympäröivän kortin kuuntelijan, joka näyttää profiilin.

## Mitä käyttäjä ei voi tehdä {#what-a-user-cant-do}

Verbi suoritetaan vain, jos käyttäjä voisi tehdä kyseisen toiminnon. Ennen kuin osoite kirjoitetaan, [uutiskirjelomakkeen](#acting-like-a-user) **Subscribe**-painike ei ole käytössä, joten testi ei voi myöskään napsauttaa sitä:

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

Napsautus heittää `InteractionRejectedException`-poikkeuksen, eikä painikkeen kuuntelijaa koskaan suoriteta. Testissä tarkistettava viesti kertoo komponentin ja hylkäyksen syyn, minkä jälkeen se näyttää komponenttipuun. Näin epäonnistunut testi kertoo, mitä sivulla oli. Toiminto hylätään, jos komponentti tai jokin sitä ympäröivä komponentti ei ole käytössä tai on piilotettu, tai jos komponentti on suljetun valintaikkunan tai sivupaneelin sisällä. Vain luku -kenttään kirjoittaminen hylätään, samoin kuin toiminto, jota komponentti ei salli, kuten sellaisen hälytyksen sulkeminen, jossa ei ole sulkemispainiketta.

:::tip Liittämättömät komponentit
Komponentin, jota ei ole liitetty, kaikki verbit epäonnistuvat `IllegalStateException`-poikkeuksella, jossa pyydetään lisäämään komponentti ensin kontekstiin.
:::

## Yksittäisen komponentin toiminnot {#gestures-of-one-component}

Jotkin toiminnot koskevat vain tiettyä komponenttia, kuten liukusäätimen siirtäminen, taulukon lajittelu tai puun solmun laajentaminen. Jokaisella webforJ-komponentilla, jolla on tällaisia toimintoja, on vuorovaikutusluokka. `UserInteraction.perform` suorittaa yhden sen toiminnoista samojen tarkistusten jälkeen kuin verbit. Seuraava ohjain näyttää liukusäätimellä asetetun äänenvoimakkuuden:

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

Testi siirtää liukusäätimen arvoon 80 ja kokeilee sitten arvoa 150, joka ylittää liukusäätimen sallitun alueen:

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

Siirto arvoon 80 tavoittaa liukusäätimen kuuntelijan, joka näyttää uuden äänenvoimakkuuden. Siirto arvoon 150 hylätään samalla tavoin kuin verbi, koska käyttäjä ei voi vetää liukusäädintä sen päätepisteen ohi. Viestissä ilmoitetaan liukusäätimen arvoalue ja testissä pyydetty arvo.

Jokaisella webforJ-komponentilla, jolla on omia toimintoja, on komponentin mukaan nimetty vuorovaikutusluokka, kuten `Slider`-komponentin `SliderInteraction`. Kukin luokan staattinen metodi vastaa yhtä toimintoa. Komponentin tarjoamat toiminnot näet sen vuorovaikutusluokan Javadocista. Esimerkiksi <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> sisältää toiminnon `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> toiminnot `sort`, `moveColumn` ja `resizeColumn`, ja <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> toiminnot `expand` ja `collapse`. <JavadocLink type="automation" location="index">webforj-automation-viite</JavadocLink> luettelee ne kaikki: kullekin komponentille on oma pakettinsa polussa `com.webforj.automation.act.component`.

## Tiedostojen lataaminen ja lähettäminen {#uploads-and-downloads}

Käyttäjä lataa tiedostoja omalta koneeltaan, joten testi välittää tiedostot ensin kontekstiin. Seuraava latauskomponentti luettelee vastaanottamiensa tiedostojen nimet:

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

Testi valitsee kaksi tiedostoa `pickFile`-metodilla ja lataa ne sitten nimien perusteella:

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

Tiedosto, joka ylittää tiedostojen enimmäismäärän tai enimmäiskoon tai jonka tyyppiä latauskomponentti ei hyväksy, ilmoitetaan hylkäyskuuntelijoille eikä sitä ladata.

Sovelluksesi käyttäjälle lähettämät tiedostot säilytetään kontekstissa. Seuraava painike lähettää raportin:

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

Testi napsauttaa painiketta ja lukee käyttäjän vastaanottaman tiedoston `getDownloads()`-metodilla:

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

## Tapahtumat ilman toimintoa {#events-without-a-gesture}

Jotkin tapahtumat tavoittavat komponentin toiminnon jälkeen selaimesta eivätkä käyttäjältä, kuten ilmoitus siitä, että sivupaneelin sulkeutuminen on päättynyt. Seuraava näkymä näyttää, onko ohjesivupaneeli avoinna:

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

`close` sulkee sivupaneelin, minkä jälkeen testi laukaisee selaimen ilmoittaman sulkemistapahtuman `UserInteraction.fireEvent`-metodilla:

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

`fireEvent` välittää tapahtuman komponentin kuuntelijoille ja tarkistaa vain, että komponentti on liitetty, sillä tapahtuma ei tule käyttäjältä. Valintaikkunoiden ja sivupaneelien avaus- ja sulkemistapahtumat, sovellusasettelun sivupaneelin tapahtumat sekä latauksen etenemistapahtumat ovat tällaisia. `Element` kuuntelee tapahtumia nimen perusteella, ja `UserInteraction.fireEvent(element, eventName, data)` välittää tapahtuman kyseisen nimen kuuntelijoille.

## Omien toimintojen määrittäminen {#gestures-of-your-own}

`perform` ottaa vastaan minkä tahansa komponentin `Consumer`-olion, joten testi voi määritellä oman toimintonsa. Seuraava testi tilaa uutiskirjeen [uutiskirjelomakkeen](#acting-like-a-user) kautta toiminnolla, joka kirjoittaa ja napsauttaa vastaanottamassaan lomakkeessa:

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

Testi kuvaa käyttäjän tekemää toimintoa, ja muut testit voivat käyttää samaa toimintoa.

## Omat komponentit {#your-own-components}

Sovelluksesi komponentti vastaa toteuttamiensa [huolirajapintojen](/docs/building-ui/component-fundamentals#concern-interfaces) verbeihin ilman lisäkoodia. `Boolean`-arvoa sisältävä komponentti vastaa `HasValue`-rajapinnan kautta toimintoihin `check`, `uncheck` ja `click` kuten valintaruutu. Seuraava kytkin ilmaisee, onko kohde suosikki:

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

Testi valitsee kytkimen ja napsauttaa sitä:

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

Vastaavasti komponentti, joka toteuttaa `HasReadOnly`-rajapinnan yhdessä `HasValue`- tai `HasText`-rajapinnan kanssa, vastaa `type`-verbiin. Komponentti, joka toteuttaa webforJ:n valintarajapintoja, vastaa `select`-verbiin.

### Verbeihin vastaamisen opettaminen komponentille {#teaching-a-component-the-verbs}

Kun mikään huolirajapinta ei sovi, vuorovaikutusluokka määrittää, miten verbi toimii komponentin kanssa. Seuraava laajennin näyttää lisätiedot, kun se on laajennettu:

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

Vuorovaikutusluokan konstruktori nimeää komponenttiluokan, ja luokka toteuttaa yhden rajapinnan kutakin verbiä kohti. Tässä tapauksessa ne ovat `OpenInteraction` ja `CloseInteraction`. `reject` luo poikkeuksen toiminnolle, jota komponentti ei salli:

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

Luokan täysin määritetty nimi lisätään testiresurssien alla olevaan tiedostoon:

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

Testi avaa ja sulkee laajentimen verbien avulla. Toinen sulkemisyritys hylätään luokan antamalla syyllä:

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

Sovelluksesi vuorovaikutus vastaa verbiin ennen webforJ:n samaan komponenttiin toimittamaa vuorovaikutusta. Jos sovelluksessasi on kaksi vuorovaikutusta, jotka vastaavat samaan verbiin saman komponentin kohdalla, testi epäonnistuu ja viestissä nimetään molemmat. Jos haluat vastata vain luokan tietyille esiintymille, ohita `isSupported(component)`.
