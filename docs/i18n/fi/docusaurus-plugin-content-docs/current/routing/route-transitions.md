---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 5991e12089a2044ef0fd6b15cae1fb13
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Reittisiirtymät tarjoavat deklaratiiviset animaatiot reittien välillä navigoitaessa. Rakennettu [View Transitions](/docs/advanced/view-transitions) API:n päälle, `@RouteTransition` -annotaation lisääminen reittikomponentteihisi antaa reitittimen automaattisesti käsitellä animaation elinkaaren navigoinnin aikana.

:::warning Kokeellinen API
Tämä API on merkitty kokeelliseksi vuodesta 25.11 ja saattaa muuttua tulevissa julkaisuissa. API:n allekirjoitus, käyttäytyminen ja suorituskykyominaisuudet ovat muutettavissa.
:::

:::info Ohjelmallinen hallinta
Monimutkaisempia siirtymätilanteita tai ohjelmallista hallintaa varten käytä suoraan [View Transitions](/docs/advanced/view-transitions) API:a.
:::

## `@RouteTransition` -annotaatio {#the-routetransition-annotation}

`@RouteTransition` -annotaatio määrittelee, kuinka reittikomponentti animoituu näkymään saapuessaan tai sieltä poistuttaessa:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // näkymän toteutus
}
```

Annotaatio hyväksyy seuraavat ominaisuudet:

| Ominaisuus | Kuvaus |
|------------|--------|
| `enter`    | Animaatio, joka tapahtuu, kun tämä näkymä ilmestyy |
| `exit`     | Animaatio, joka tapahtuu, kun tämä näkymä poistuu |

Molemmat ominaisuudet hyväksyvät minkä tahansa ennalta määritellyn siirtymätyypin tai mukautetun merkkijonon arvon:

| Vakio                  | Vaikutus |
|-----------------------|----------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristifaade vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö kutistuu sisään |

## Peruskäyttö {#basic-usage}

Lisää annotaatio mihin tahansa reittikomponenttiin aktivoidaksesi siirtymät:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    getBoundComponent().add(new H1("Inbox"));
    // ...
  }
}
```

Esimerkissä:
- Kun navigoidaan `InboxView`-komponenttiin, komponentti saapuu zoom-animaatiolla
- Kun navigoidaan pois `InboxView`:stä, komponentti poistuu sisällön virratessa oikealle

## Navigointivirta {#navigation-flow}

Kun navigoidaan kahden reitin välillä, reititin koordinoi siirtymäjärjestyksen:

1. Poistuvan komponentin `exit`-animaatio alkaa
2. [DOM](/docs/glossary#dom) muutokset tapahtuvat (vanha näkymä poistuu, uusi näkymä lisätään)
3. Saapuvan komponentin `enter`-animaatio soitetaan

Jos navigoidaan samaan näkymään, joka on jo näkyvissä, siirtymä ohitetaan turhien animaatioiden välttämiseksi.

:::tip Johdonmukaiset poistumis-animaatiot
Saman poistumis-animaation käyttäminen kaikissa näkymissä luo suuntaa antavaa johdonmukaisuutta. Esimerkiksi, jos määrität kaikki näkymät poistumaan `SLIDE_RIGHT`:lla, se luo yhtenäisen "taaksepäin" liikemallin, mikä tekee navigointikäyttäytymistä ennustettavaksi riippumatta alkuperäisestä näkymästä.
:::

## Siirtymän periminen {#transition-inheritance}

Reitit perivät siirtymät vanhemmilta reiteiltään. Kun reitillä ei ole `@RouteTransition`-annotaatiota, reititin käy hierarkiassa ylöspäin löytääkseen sellaisen.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Vanhempi asettelu, jossa on siirtymä
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Perii ZOOM:in MainLayout:ilta
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Perii ZOOM:in MainLayout:ilta (InboxView:n kautta)
}
```

Kaikki lapsireitit perivät saman animaatiotyylin toistamatta annotaatiota.

### Perittyjen siirtymien ylikirjoittaminen {#overriding-inherited-transitions}

Lapsireitit voivat ylikirjoittaa perityt siirtymät määrittelemällä oman `@RouteTransition`:insa:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Perii ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Ylikirjoittaa SLIDE_UP/SLIDE_DOWN:lla
}
```

## Jaetut komponenttisiirtymät {#shared-component-transitions}

Voit yhdistää reittisiirtymät ja jaetut komponenttianimaatiot luodaksesi yhdistelettyjä kokemuksia. Komponentit, joilla on vastaavat `view-transition-name` -arvot, muuntuvat näkymien välillä. Käytä `setViewTransitionName()` -menetelmää, joka on saatavana kaikille komponenteille, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

```java title="ProductListView.java"
@Route(value = "products", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductListView extends Composite<FlexLayout> {

  private void buildProductCard(Product product) {
      Img thumbnail = new Img(product.getImageUrl());
      thumbnail.setViewTransitionName("product-image-" + product.getId());
      // ...
  }
}
```

```java title="ProductDetailView.java"
@Route(value = "products/:id", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductDetailView extends Composite<FlexLayout> implements DidEnterObserver {

  private Img heroImage = new Img();

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
      String id = parameters.get("id").orElse("");
      heroImage.setViewTransitionName("product-image-" + id);
      // ...
  }
}
```

Kun navigoidaan luettelosta detaljnäkymään, tuotteen pikkukuva muuntuu sankarikuvan paikkaan samalla kun muu sisältö siirtyy häilyväanimaatiolla.
