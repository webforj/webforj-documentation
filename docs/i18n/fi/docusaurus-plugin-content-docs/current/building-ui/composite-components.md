---
sidebar_position: 4
title: Composite Components
_i18n_hash: fb15eb19cfe0ca1aebb77a67b10c9ecd
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevat webforJ-komponentit itseensä sulkeutuviksi, uudelleenkäytettäviksi komponenteiksi, joilla on mukautettu käyttäytyminen. Käytä sitä paketoidaksesi sisäiset webforJ-komponentit uudelleenkäytettäviksi liiketoimintalogiikan yksiköiksi, uudelleenkäytä komponenttimalleja koko sovelluksessa ja yhdistä useita komponentteja paljastamatta toteutustietoja.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle hallinnan siitä, mitkä metodit ja ominaisuudet käyttäjien on mahdollista käyttää, toisin kuin perinteisessä perintömallissa, jossa kaikki on paljastettu.

Jos sinun täytyy integroida web-komponentteja muusta lähteestä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponenteille, joissa on tyyppiturvallinen ominaisuusjohtaminen
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponenteille, jotka hyväksyvät slottiaineistoa

<AISkillTip skill="webforj-creating-components" />

## Käyttö {#usage}

Määrittääksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä, minkä tyyppistä komponenttia se hallitsee. Tämä tulee olemaan sidottu komponenttisi, joka on juurialusta, joka sisältää sisäisen rakenteesi:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Pääsy sidottuun komponenttiin sen määrittämiseksi
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Lähetä"));
  }
}
```

`getBoundComponent()`-metodi antaa pääsyn taustalla olevaan komponenttiin, mikä mahdollistaa sen ominaisuuksien määrittämisen, lapsikomponenttien lisäämisen ja käyttäytymisen hallinnan suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavien asetteluiden osalta harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttämistä sidottuna komponenttina.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component`- tai `DwcComponent`-komponentteja suoraan. Käytä aina koostumismalleja `Composite`-komponentin kanssa rakentaaksesi mukautettuja komponentteja.
:::

Ylikirjoita `initBoundComponent()` silloin, kun tarvitset enemmän joustavuutta sidotun komponentin luomiseen ja hallintaan, kuten parametrisoitujen konstruktoreiden käyttäminen oletus ilman argumentteja olevan konstruktorin sijaan. Käytä tätä mallia, kun sidottu komponentti vaatii komponenttien välittämistä sen konstruktorille sen sijaan, että ne lisätään myöhemmin.

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

webforJ hoitaa kaikki `Composite`-komponenttien elinkaaren hallinnan automaattisesti. Käyttämällä `getBoundComponent()`-metodia, suurin osa mukautetusta käyttäytymisestä voidaan käsitellä konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perus asetusten tekeminen ja tapahtumien rekisteröinti.

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
   // Hakulogiikka tänne
 }
}
```

Jos sinulla on ylimääräisiä spesifisiä asetuksia tai puhdistustarpeita, saatat haluta käyttää valinnaisia elinkaarisilmukoita `onDidCreate()` ja `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Alusta komponentit, jotka tarvitsevat DOM-liittymää
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

Jos sinun tarvitsee suorittaa jotain toimia sen jälkeen, kun komponentti on liitetty DOM:iin, käytä `whenAttached()`-metodia:

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

Seuraava esimerkki havainnollistaa Todo-sovellusta, jossa jokainen kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radiobutton) -komponentista, joka on tyylitelty kytkimeksi, ja Div:stä, jossa on teksti:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia ryhmittämään liittyviä komponentteja yhteen yksikköön, vaikka uudelleenkäytettävyys ei olisikaan pääasiallinen huolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
