---
sidebar_position: 4
title: Composing Components
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: e740e537ffcccd1f316f30c21ceb2a4e
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevat webforJ-komponentit itseensä sisältäviksi, uudelleenkäytettäviksi komponenteiksi, joilla on mukautettua käyttäytymistä. Käytä sitä sisäisten webforJ-komponenttien käärettämiseen uudelleenkäytettäväksi liiketoimintalogiikkayksiköksi, uudelleenkäyttääksesi komponenttimalleja sovelluksesi eri osissa sekä yhdistääksesi useita komponentteja paljastamatta toteutuksen yksityiskohtia.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle hallinnan siihen, mitä menetelmiä ja ominaisuuksia käyttäjät voivat käyttää, toisin kuin perinteisessä perinnössä, jossa kaikki on paljastettu.

Jos sinun tarvitsee integroida web-komponentteja muusta lähteestä, käytä erityisiä vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponenteille, joilla on tyypitetty ominaisuusjohtaminen
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponenteille, jotka hyväksyvät slottamiseen perustuvaa sisältöä

<AISkillTip skill="webforj-creating-components" />

## Käyttö {#usage}

Määrittääksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määrittele hallittavan komponentin tyyppi. Tästä tulee sidottu komponenttisi, joka on juurivarasto, joka pitää sisäisen rakenteesi:

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

`getBoundComponent()`-metodi tarjoaa pääsyn taustalla olevaan komponenttiin, mikä mahdollistaa sen ominaisuuksien konfiguroimisen, lapsikomponenttien lisäämisen ja käyttäytymisen hallinnan suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavia asetteluja varten harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttämistä sidottuna komponenttina.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component`- tai `DwcComponent`-luokkia suoraan. Käytä aina koostumismalleja `Composite`-komponentin kanssa mukautettujen komponenttien rakentamiseen.
:::

Yli kirjoita `initBoundComponent()` silloin, kun tarvitset enemmän joustavuutta sidotun komponentin luomisessa ja hallinnassa, esimerkiksi käyttäen parametroituja konstruktoreita oletusilmanargumentteja käyttäen. Käytä tätä mallia, kun sidottu komponentti vaatii komponenttien siirtämistä sen konstruktorille sen sijaan, että ne lisättäisiin jälkikäteen.

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

webforJ hallitsee automaattisesti kaikkia `Composite`-komponenttien elinkaaren hallintaan liittyviä asioita. Käyttämällä `getBoundComponent()`-metodia, suurin osa mukautetusta käytöksestä voidaan käsitellä konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perusasetelmien määrittäminen ja tapahtumarekisteröinti.

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
   // Hakulogiikka täällä
 }
}
```

Jos sinulla on erityisiä asetus- tai puhdistusvaatimuksia, saatat tarvita valinnaisia elinkaaren koukkuja `onDidCreate()` ja `onDidDestroy()`:

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
   // Puhdistusresurssit
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Tietojen päivityslogiikka
 }
}
```

Jos sinun tarvitsee suorittaa toimintoja komponentin kiinnittämisen jälkeen DOM: iin, käytä `whenAttached()`-metodia:

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

Seuraava esimerkki havainnollistaa Todo-sovellusta, jossa jokainen kohde on `Composite`-komponentti, johon kuuluu [`RadioButton`](../components/radiobutton), joka on tyylitelty kytkimeksi, ja Div, jossa on teksti:

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/resources/static/composite/composite.css',
]}
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia liittyvien komponenttien ryhmittelyyn yhdeksi yksiköksi, vaikka uudelleenkäytettävyys ei olisi tärkein huolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/resources/static/composite/analyticscomposite.css',
]}
height='550px'
/>
