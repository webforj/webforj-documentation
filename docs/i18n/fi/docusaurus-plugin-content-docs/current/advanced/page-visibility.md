---
title: Page Visibility
sidebar_position: 32
sidebar_class_name: new-content
description: >-
  Detect when the tab hosting your app moves between the foreground and the
  background, and react in Java.
_i18n_hash: 8382d0314f6143663c03e11409de08d5
---
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/Page" top='true'/>

`Page`-luokka voi kertoa, kun käyttäjä on vaihtanut pois sovellustasi isännöivältä välilehdeltä, minimalisoinut ikkunan tai palannut takaisin. Käytä sitä esimerkiksi keskeyttääksesi kyselyn ja animaatiot, kun kukaan ei katso, ohjataksesi ilmoituksia tai päivittääksesi vanhentunutta tietoa, kun välilehti saa taas fokuksen.

API:lla on kaksi osaa:

- Tyyppikysely, `getVisibilityState()`, joka palauttaa nykyisen tilan.
- Kuuntelija, `addVisibilityChangeListener(...)`, joka laukaisee jokaisella tilan muutoksella.

## Näkyvyys tilat {#visibility-states}

`PageVisibilityState`:llä on kaksi arvoa:

| Tila | Merkitys |
| --- | --- |
| `VISIBLE` | Sivun sisältö on ainakin osittain näkyvissä. Välilehti on etualalla ei-minimoidussa ikkunassa. |
| `HIDDEN` | Sivun sisältö ei ole käyttäjän nähtävissä. Välilehti on taustalla, ikkuna on minimoitu, näyttö on lukittu tai käyttöjärjestelmä näyttää kuvaversiota. |

## Nykyisen tilan lukeminen {#reading-the-current-state}

`Page.getVisibilityState()` palauttaa `PendingResult<PageVisibilityState>` sekvenssin nykyisellä tilalla.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // käyttäjä katsoo välilehteä
  }
});
```

Kutsu sitä, kun tarvitset yhden vastauksen, esimerkiksi kun aikataulutettu tehtävä herää. Jatkuviin reaktioihin on parempi rekisteröidä kuuntelija.

## Muutosten kuuntelu {#listening-for-changes}

`addVisibilityChangeListener(...)` rekisteröi kuuntelijan, joka ilmoittaa jokaisesta, kun näkyvyystila muuttuu. Vastapuolen alias on `onVisibilityChange(...)`.

```java
ListenerRegistration<PageVisibilityChangeEvent> registration =
    Page.getCurrent().onVisibilityChange(event -> {
      if (event.isHidden()) {
        pauseRendering();
      } else {
        resumeRendering();
      }
    });
```

Tapahtuma siirtää uuden tilan ja muutamia käteviä käyttöliittymiä:

| Metodi | Palauttaa |
| --- | --- |
| `getState()` | Uuden `PageVisibilityState`. |
| `isVisible()` | `true`, kun uusi tila on `VISIBLE`. |
| `isHidden()` | `true`, kun uusi tila on `HIDDEN`. |
| `getPage()` | Sivun, joka tuotti tapahtuman. |

Poista yksittäinen kuuntelija palautetun `ListenerRegistration`:in avulla.

## Esimerkki: ilmoita vain kun välilehti on piilotettu {#example-notify-when-hidden}

Yleinen käyttötapaus on valita toimituskanava sen mukaan, katsoiko käyttäjä tällä hetkellä välilehteä. Alla oleva koodi aikatauluttaa ilmoituksen viiden sekunnin päähän. Jos välilehti on piilotettu ajastimen laukaistessa, se näyttää työpöytähälytyksen ja lisää insignian favicon-lippu. Jos välilehti on näkyvissä, se näyttää sovelluksessa toast-viestin.

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("Ilmoita 5 sekunnin sisällä");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("Sivun näkyvyys demo");
    Paragraph hint = new Paragraph(
        "Napsauta painiketta ja vaihda sitten toiseen välilehteen ajatellessasi ajastimen loppua.");

    notifyButton.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .onClick(e -> schedule.run(this::deliver));

    self.add(title, hint, notifyButton);

    visibilityRegistration = Page.getCurrent().onVisibilityChange(this::onVisibility);
  }

  private void deliver() {
    Page page = Page.getCurrent();
    page.getVisibilityState().thenAccept(state -> {
      if (state == PageVisibilityState.HIDDEN) {
        page.setIconBadge(1);
        activeNotification = new DesktopNotification("Sivun näkyvyys demo",
            "Ajastin laukesi, kun välilehti oli piilotettuna.");
        activeNotification.open();
      } else {
        Toast.show("Ajastin laukesi, kun välilehti oli näkyvissä.", Theme.SUCCESS);
      }
    });
  }

  private void onVisibility(PageVisibilityChangeEvent event) {
    if (event.isVisible() && activeNotification != null) {
      Page.getCurrent().setIconBadge(0);
      activeNotification.close();
      activeNotification = null;
    }
  }

  @Override
  protected void onDidDestroy() {
    schedule.cancel();
    if (visibilityRegistration != null) {
      visibilityRegistration.remove();
    }
  }
}
```

Näkymäkuuntelija poistaa faviconin insignian ja hylkää työpöytähälytyksen, kun käyttäjä palaa välilehdelle.

## Milloin sitä tulisi käyttää {#when-to-use-it}

- **Keskeytä taustatyö.** Lopeta kyselyt, väliarvot ja animaatiot, kun sivu on piilotettu, säästäen kaistaa ja CPU:ta. Käynnistä ne uudelleen, kun se tulee taas näkyväksi.
- **Porttinohjaus ilmoituksille.** Näytä `Toast`, kun käyttäjä voi nähdä välilehden ja `DesktopNotification`, kun ei voi.
- **Päivitä vanhentunut tieto palatessasi.** Kun sivu palaa `HIDDEN`-tilasta, päätä, onko tarpeeksi aikaa kulunut uudelleen noutamiseen.
- **Seuraa sitoutumista.** Merkitse istunto passiiviseksi, kun välilehti on piilotettuna.
