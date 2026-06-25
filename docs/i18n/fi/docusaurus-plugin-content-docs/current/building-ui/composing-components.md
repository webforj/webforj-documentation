---
sidebar_position: 4
title: Composite Components
_i18n_hash: 7e40c0b9a2feae4f8e56829bb2c8889b
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevia webforJ-komponentteja itseään sisältäviin, uudelleenkäytettäviin komponentteihin, joilla on mukautettua käyttäytymistä. Käytä sitä kääriäksesi sisäisiä webforJ-komponentteja uudelleenkäytettäviin liiketoimintalogikan yksiköihin, uudelleenkäyttääksesi komponenttimalleja sovelluksessasi ja yhdistääksesi useita komponentteja paljastamatta toteutustietoja.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle hallinnan siitä, mihin menetelmiin ja ominaisuuksiin käyttäjät voivat päästä käsiksi, toisin kuin perinteisessä perinnössä, jossa kaikki on paljastettu.

Jos sinun täytyy integroida web-komponentteja muista lähteistä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponentteihin, joilla on tyypillisesti turvallinen ominaisuuksien hallinta
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponentteihin, jotka hyväksyvät slottattua sisältöä

<AISkillTip skill="webforj-creating-components" />

## Käyttö {#usage}

Määrittääksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä, minkä tyyppistä komponenttia se hallitsee. Tämä tulee olemaan sidottu komponenttisi, joka on juurikontti, joka pitää sisäisen rakenteesi:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Pääsy sidottuun komponenttiin sen konfiguroimiseksi
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Submit"));
  }
}
```

`getBoundComponent()`-menetelmä tarjoaa pääsyn taustalla olevaan komponenttiin, mikä mahdollistaa sen ominaisuuksien määrittämisen, lapsikomponenttien lisäämisen ja käytöksen hallinnan suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavia asetteluja varten harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttämistä sidottuna komponenttinasi.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component` tai `DwcComponent` suoraan. Käytä aina koostumuspatterneja `Composite`-komponenttien rakentamiseen.
:::

Ylikirjoita `initBoundComponent()` kun tarvitset enemmän joustavuutta sidotun komponentin luomisessa ja hallinnassa, kuten käyttämällä parametrisoituja konstruktoreita oletusarvoisen ilman argumentteja rakennetta. Käytä tätä mallia, kun sidottu komponentti vaatii komponenttien siirtämistä sen konstruktorille sen sijaan, että ne lisätään jälkikäteen.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Name");
   emailField = new TextField("Email");
   submitButton = new Button("Submit");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Komponentin elinkaari {#component-lifecycle}

webforJ hallitsee kaikki `Composite`-komponenttien elinkaaren hallinnan automaattisesti. Käyttämällä `getBoundComponent()`-menetelmää suurin osa mukautetusta käytöksestä voidaan käsitellä konstruktiossa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perustason layoutin asettaminen ja tapahtumarekisteröinti.

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
   // Haku logiikka täällä
 }
}
```

Jos sinulla on lisäisiä erityisiä asetuksia tai puhdistustarpeita, saatat tarvita valinnaisia elinkaaren kytkimiä `onDidCreate()` ja `onDidDestroy()`:

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
   // Puhdistaa resurssit
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Datan päivityslogiikka
 }
}
```

Jos sinun tarvitsee suorittaa toimintoja komponentin liittämisen jälkeen DOM:iin, käytä `whenAttached()`-menetelmää:

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

Seuraava esimerkki havainnollistaa Todo-sovellusta, jossa jokainen kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radiobutton) -painikkeesta, joka on tyylitelty kytkimeksi, ja Div:stä, jossa on teksti:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/resources/static/composite/composite.css',
]}
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia ryhmittämään liittyviä komponentteja yhdeksi yksiköksi, vaikka uudelleenkäytettävyys ei olisi päähuolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/resources/static/composite/analyticscomposite.css',
]}
height='500px'
/>
