---
sidebar_position: 4
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: df54783e5555a595f5cceb28849f3787
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevia webforJ-komponentteja itseään sisältäviksi, uudelleenkäytettäviksi komponenteiksi, joilla on mukautettu käyttäytyminen. Käytä sitä kääriäksesi sisäisiä webforJ-komponentteja uudelleenkäytettävien liiketoimintalogiikka-yksiköiden muotoon, uudelleenkäyttääksesi komponenttipohjia sovelluksessa ja yhdistääksesi useita komponentteja paljastamatta toteutustietoja.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle hallinnan siitä, mitä metodeja ja ominaisuuksia käyttäjät voivat käyttää, toisin kuin perinteisessä periytymisessä, jossa kaikki on paljastettu.

Jos tarvitset integraatiota web-komponentteihin muista lähteistä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponenteille, joissa on tyypillinen ominaisuuksien hallinta
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponenteille, jotka hyväksyvät slotattua sisältöä

## Käyttö {#usage}

Määritelläksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä, minkä tyyppistä komponenttia se hallitsee. Tästä tulee sidottu komponentti, joka on juurikontti, joka pitää sisällään sisäisen rakenteesi:

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

`getBoundComponent()`-metodi tarjoaa pääsyn taustalla olevaan komponenttiin, jolloin voit konfiguroida sen ominaisuuksia, lisätä lapsikomponentteja ja hallita sen käyttäytymistä suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavien asettamisten vuoksi harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttämistä sidottuna komponenttina.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component` tai `DwcComponent` suoraan. Käytä aina koostumuspohjia `Composite`-komponenttien rakentamiseen.
:::

Ylikirjoita `initBoundComponent()` silloin, kun tarvitset enemmän joustavuutta sidotun komponentin luomisessa ja hallinnassa, esimerkiksi käyttämällä parametrisoituja konstruktoreita sen sijaan, että käytät oletus ilman argumentteja olevaa konstruktoria. Käytä tätä mallia, kun sidottu komponentti vaatii komponentteja siirrettäväksi sen konstruktorille sen sijaan, että lisätään jälkikäteen.

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

webforJ hallitsee kaikkia `Composite`-komponenttien elinkaaren hallintatehtäviä automaattisesti. Käyttämällä `getBoundComponent()`-metodia, useimmat mukautetut käyttäytymiset voidaan käsitellä konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perusasetusten luominen ja tapahtumien rekisteröinti.

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
   // Hakulogikka tähän
 }
}
```

Jos sinulla on muita erityisiä asetuksia tai puhdistustarpeita, saatat joutua käyttämään valinnaisia elinkaarikoukkua `onDidCreate()` ja `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Alusta komponentit, jotka vaativat DOM-liitosta
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Puhdista resurssit
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Datan päivittämisen logiikka
 }
}
```

Jos sinun on suoritettava toimintoja, kun komponentti on liitetty DOM:iin, käytä `whenAttached()`-metodia:

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

Seuraava esimerkki esittelee Todo-sovelluksen, jossa jokainen kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radiobutton) -koodista tyyliltään kytkimenä ja Div, jossa on teksti:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia ryhmitelläksesi liittyviä komponentteja yhteen yksikköön, vaikka uudelleenkäytettävyys ei olisi päähuolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
