---
sidebar_position: 4
title: Composing Components
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 96d22d0dc6ba882867ca35edcf1edcca
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevia webforJ-komponentteja itseensä sisältäviin, uudelleenkäytettäviin komponentteihin, joilla on mukautettu käyttäytyminen. Käytä sitä kääriäksesi sisäisiä webforJ-komponentteja uudelleenkäytettäviksi liiketoimintalogiikkayksiköiksi, uudelleenkäytä komponenttipohjia koko sovelluksessasi ja yhdistä useita komponentteja paljastamatta toteutustietoja.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle valvonnan siihen, mitkä menetelmät ja ominaisuudet ovat käyttäjien käytettävissä, toisin kuin perinteisessä perinnössä, jossa kaikki on paljastettu.

Jos tarvitset integraatiota web-komponenttien kanssa toisesta lähteestä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponenteille, joilla on tyyppiturvallinen ominaisuusmanagement
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponenteille, jotka hyväksyvät slotattua sisältöä

<AISkillTip skill="webforj-creating-components" />

## Käyttö {#usage}

Määritelläksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä hallitsemasi komponentin tyyppi. Tämä muodostaa sidotun komponenttisi, joka on juurialustasi, joka pitää sisäistä rakennettasi:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Pääsy sidottuun komponenttiin sen konfiguroimiseksi
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Lähetä"));
  }
}
```

`getBoundComponent()`-metodi tarjoaa pääsyn taustalla olevaan komponenttiisi, jolloin voit konfiguroida sen ominaisuuksia, lisätä lapsikomponentteja ja hallita sen käyttäytymistä suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavien asettelujen osalta harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttöä sidottuna komponenttinasi.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component`- tai `DwcComponent`-luokkia suoraan. Käytä aina koostumusmalleja `Composite`:n kanssa rakentaaksesi mukautettuja komponentteja.
:::

Korvaa `initBoundComponent()` silloin, kun tarvitset suurempaa joustavuutta sidotun komponentin luomisessa ja hallinnassa, esimerkiksi käyttäen parametrisoituja konstruktoreita oletusilman argumentteja olevaan konstruktoriin verrattuna. Käytä tätä mallia, kun sidottu komponentti vaatii komponenttien siirtämistä sen konstruktorin kautta sen sijaan, että ne lisättäisiin sen jälkeen.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Nimi");
   emailField = new TextField("Sähköposti");
   submitButton = new Button("Lähetä");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Komponentin elinkaari {#component-lifecycle}

webforJ hoitaa kaikki `Composite`-komponenttien elinkaaren hallinnan automaattisesti. Käyttämällä `getBoundComponent()`-metodia, suurin osa mukautetusta käyttäytymisestä voidaan hoitaa konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perusasetusten tekeminen ja tapahtumien rekisteröinti.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private final FlexLayout self = getBoundComponent();
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Etsi käyttäjiä...");
   searchButton = new Button("Etsi");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // Hakulogiikka tähän
 }
}
```

Jos sinulla on ylimääräisiä erityisiä asetuksia tai siivoustarpeita, saatat tarvita valinnaisia elinkaaren koukkuja `onDidCreate()` ja `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Alusta komponentit, jotka vaativat DOM-liittämistä
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Siivoa resurssit
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Datan päivityslogiikka
 }
}
```

Jos sinun täytyy suorittaa toimintoja sen jälkeen, kun komponentti on liitetty DOM:iin, käytä `whenAttached()`-metodia:

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## Esimerkki `Composite`-komponentista {#example-composite-component}

Seuraava esimerkki esittelee Todo-sovelluksen, jossa jokainen kohta on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radiobutton) -komponentista, joka on tyylitelty kytkimeksi, ja Div:stä, jossa on teksti:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`:a ryhmitelläksesi samankaltaiset komponentit yhdeksi kokonaisuudeksi, vaikka uudelleenkäytettävyys ei olisikaan päähuolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
