---
sidebar_position: 12
title: Route Transitions
_i18n_hash: 98050ac6a061f4dc3728af3888aa44b0
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Reittisiirtymät tarjoavat deklaratiivisia animoituja siirtymiä, kun navigoidaan reittien välillä. Perustuu [View Transitions](/docs/advanced/view-transitions) API:in, lisäämällä `@RouteTransition`-annotaatio reittikomponentteihisi, reititin voi automaattisesti käsitellä animaation elinkaaren navigoinnin aikana.

<ExperimentalWarning />

:::info Ohjelmallinen hallinta
Monimutkaisempia siirtymätilanteita tai ohjelmallista hallintaa varten käytä [View Transitions](/docs/advanced/view-transitions) API:ta suoraan.
:::

## `@RouteTransition` annotaatio {#the-routetransition-annotation}

`@RouteTransition` annotaatio määrittelee, miten reittikomponentti animoidaan, kun se tulee tai poistuu näkymästä:

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
| `enter` | Animaatio, joka sovelletaan, kun tämä näkymä ilmestyy |
| `exit` | Animaatio, joka sovelletaan, kun tämä näkymä poistuu |

Molemmat ominaisuudet hyväksyvät minkä tahansa esimäärätyn siirtymatyypin tai mukautetun merkkijonon arvon:

| Vakio | Vaikutus |
|-------|----------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Risteävä häivytys vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuin eteenpäin navigointi) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuin taaksepäin navigointi) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö kutistuu, uusi sisältö kasvaa sisään |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa pois, uusi sisältö kutistuu sisään |

## Peruskäyttö {#basic-usage}

Lisää annotaatio mihin tahansa reittikomponenttiin ottaaksesi siirtymät käyttöön:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.add(new H1("Inbox"));
    // ...
  }
}
```

Tässä esimerkissä:
- Siirryttäessä `InboxView`-näkymään, komponentti tulee zoom-animaatiolla
- Siirryttäessä pois `InboxView`-näkymästä, komponentti poistuu sisällön virratessa oikealle

## Navigointivirta {#navigation-flow}

Kun navigoidaan kahden reitin välillä, reititin koordinoi siirtymäsarjaa:

1. Poistuvan komponentin `exit` animaatio alkaa
2. [DOM](/docs/glossary#dom) muutokset tapahtuvat (vanha näkymä poistetaan, uusi näkymä lisätään)
3. Tulevan komponentin `enter` animaatio pyörii

Jos navigoidaan samaan näkymään, joka on jo esillä, siirtymä ohitetaan tarpeettomien animaatioiden välttämiseksi.

:::tip Johdonmukaiset poistumis-animaatiot
Saman poistumis-animaation käyttäminen kaikilla näkymillä luo suunnallista johdonmukaisuutta. Esimerkiksi, konfiguroimalla kaikki näkymät poistumaan `SLIDE_RIGHT`-animaatiolla luodaan yhtenäinen "taakse" liikemalli, mikä tekee navigointikäyttäytymisestä ennustettavaa riippumatta lähtönäkymästä.
:::

## Siirtymäperintö {#transition-inheritance}

Reitit perivät siirtymät vanhemmilta reiteiltään. Kun reitillä ei ole `@RouteTransition`, reititin kulkee ylöspäin hierarkiassa löytääkseen sen.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Vanhempi asettelu siirtymällä
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Perii ZOOM:in MainLayout:ista
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Perii ZOOM:in MainLayout:ista (InboxView:n kautta)
}
```

Kaikki lapsireitit perivät saman animaatiotyylin toistamatta annotaatiota.

### Perittyjen siirtymien korvaaminen {#overriding-inherited-transitions}

Lapsireitit voivat korvata perityn siirtymän määrittelemällä oman `@RouteTransition`-annotaation:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Perii ZOOM:in
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Korvataan SLIDE_UP/SLIDE_DOWN:lla
}
```

## Jaetun komponentin siirtymät {#shared-component-transitions}

Voit yhdistää reittisiirtymiä ja jaettuja komponenttianimaatioita luodaksesi yhteensopivia kokemuksia. Komponentit, joilla on samat `view-transition-name` arvot, muuntuvat näkymien välillä. Käytä `setViewTransitionName()`-metodia, joka on saatavilla kaikilla komponenteilla, jotka implementoivat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

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

Kun navigoidaan listasta yksityiskohtanäkymään, tuote pieni kuva muuntuu sankarikuvan paikaksi samalla kun muu sisältö siirtyy häivytysanimaatiolla.
