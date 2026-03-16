---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 34159c78405282a71774c6148a31f18a
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Reittisiirtymät tarjoavat deklaratiivisia animaatioita reittejä navigoitaessa. [View Transitions](/docs/advanced/view-transitions) API:ta käyttäen, lisäämällä `@RouteTransition` -annotaatio reittikomponentteihisi, reititin voi automaattisesti käsitellä animaation elinkaaren navigoinnin aikana.

:::warning Kokeellinen API
Tämä API merkitään kokeelliseksi versiosta 25.11 ja saattaa muuttua tulevissa julkaisuissa. API:n allekirjoitus, käyttäytyminen ja suorituskyky voivat vaihdella.
:::

:::info Ohjelmallinen hallinta
Monimutkaisempia siirtymän skenaarioita tai ohjelmallista hallintaa varten, käytä [View Transitions](/docs/advanced/view-transitions) API:ta suoraan.
:::

## `@RouteTransition` -annotaatio {#the-routetransition-annotation}

`@RouteTransition` -annotaatio määrittelee, miten reittikomponentti animaatioidaan näkymään saapumisen tai sieltä poistumisen aikana:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // näkymän toteutus
}
```

Annotaatio hyväksyy seuraavat ominaisuudet:

| Ominaisuus | Kuvaus |
|----------|-------------|
| `enter` | Animaatio, joka sovelletaan kun tämä näkymä ilmestyy |
| `exit` | Animaatio, joka sovelletaan kun tämä näkymä poistuu |

Molemmat ominaisuudet hyväksyvät kaikki ennalta määritellyt siirtymätyypit tai mukautetun merkkijonon arvon:

| Vakio | Vaikutus |
|----------|--------|
| `ViewTransition.NONE` | Ei animaatiota |
| `ViewTransition.FADE` | Ristiinfade vanhan ja uuden sisällön välillä |
| `ViewTransition.SLIDE_LEFT` | Sisältö virtaa vasemmalle (kuten eteenpäin navigoitaessa) |
| `ViewTransition.SLIDE_RIGHT` | Sisältö virtaa oikealle (kuten taaksepäin navigoitaessa) |
| `ViewTransition.SLIDE_UP` | Sisältö virtaa ylöspäin |
| `ViewTransition.SLIDE_DOWN` | Sisältö virtaa alaspäin |
| `ViewTransition.ZOOM` | Vanha sisältö pienenee, uusi sisältö kasvaa |
| `ViewTransition.ZOOM_OUT` | Vanha sisältö kasvaa, uusi sisältö pienenee |

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
- Kun navigoidaan `InboxView`:hin, komponentti saapuu zoom-animaatiolla
- Kun navigoidaan pois `InboxView`:sta, komponentti poistuu sisällön virratessa oikealle

## Navigointivirta {#navigation-flow}

Kun navigoidaan kahden reitin välillä, reititin koordinoi siirtymäsarjan:

1. Poistuvan komponentin `exit` animaatio alkaa
2. [DOM](/docs/glossary#dom) muutokset tapahtuvat (vanha näkymä poistetaan, uusi näkymä lisätään)
3. Saapuvan komponentin `enter` animaatio soitetaan

Jos navigoidaan samaan näkymään, joka jo on näytössä, siirtymä skipataan tarpeettomien animaatioiden välttämiseksi.

:::tip Johdonmukaiset poistumisanimaatiot
Saman poistumisanimaation käyttäminen kaikissa näkymissä luo suuntaa antavaa johdonmukaisuutta. Esimerkiksi, määrittämällä kaikki näkymät poistumaan `SLIDE_RIGHT` -animaatiolla luo yhtenäisen "takaisin" liikkeen mallin, mikä tekee navigointikäyttäytymisestä ennakoitavaa riippumatta alkuperäisestä näkymästä.
:::

## Siirtymän perintä {#transition-inheritance}

Reitit perivät siirtymiä vanhemmilta reiteiltään. Kun reitillä ei ole `@RouteTransition` -annotaatiota, reititin kävelee hierarkiassa ylöspäin löytääkseen sellaisen.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Vanhempi asettelu siirtymän kanssa
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

### Perittyjen siirtymien ylittäminen {#overriding-inherited-transitions}

Lapsireitit voivat ylittää perityn siirtymän määrittelemällä oman `@RouteTransition` -annotaationsa:

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
  // Ylittää SLIDE_UP/SLIDE_DOWN:lla
}
```

## Jaettujen komponenttien siirtymät {#shared-component-transitions}

Voit yhdistää reittisiirtymiä ja jaettujen komponenttien animaatioita luodaksesi yhteyksiä kokemuksia. Komponentit, joilla on vastaavat `view-transition-name` arvot, muotoutuvat näkymien välillä. Käytä `setViewTransitionName()` metodia, joka on saatavilla kaikille komponenteille, jotka toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> -rajapinnan.

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

Kun navigoidaan listasta yksityiskohtanäkymään, tuotteen pienkuva muotoontuu sankarikuvan paikkaan samalla kun muu sisältö siirtyy fade-animaatiolla.
