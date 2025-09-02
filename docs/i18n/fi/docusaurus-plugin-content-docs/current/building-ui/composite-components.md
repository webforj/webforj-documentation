---
sidebar_position: 2
title: Composite Components
sidebar_class_name: updated-content
_i18n_hash: 997bb40968c4f4ede5eccb00c27e5305
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevia webforJ-komponentteja itse sisällä oleviksi, uudelleenkäytettäviksi komponenteiksi, joilla on mukautettu käyttäytyminen. Käytä sitä kääriäksesi sisäisiä webforJ-komponentteja uudelleenkäytettäviksi liiketoimintalogiikan yksiköiksi, käyttääksesi komponenttimalleja koko sovelluksessa ja yhdistääksesi useita komponentteja ilman, että paljastat toteutustietoja.

`Composite`-komponentilla on vahva yhteys sen alla olevaan sidottuun komponenttiin. Tämä antaa sinulle kontrollin siitä, mitä menetelmiä ja ominaisuuksia käyttäjät voivat käyttää, toisin kuin perinteisessä perinnössä, jossa kaikki on näkyvissä.

Jos sinun tarvitsee integroida web-komponentteja muusta lähteestä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponenteille, joilla on tyyppiturvallinen ominaisuuksien hallinta
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponenteille, jotka hyväksyvät slottisisältöä

## Käyttö {#usage}

Määrittääksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä sen hallitsema komponenttityyppi. Tämä muuttuu sidotuksi komponentiksesi, joka on juurisisältö, joka pitää sisäisen rakenteesi:

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {

  public BasicComposite() {
    // Pääsy sidottuun komponenttiin sen konfiguroimiseksi
    getBoundComponent()
      .setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Lähetä"));
  }
}
```

`getBoundComponent()`-metodi tarjoaa pääsyn alaosaan, jolloin voit konfiguroida sen ominaisuuksia, lisätä lapsikomponentteja ja hallita sen käyttäytymistä suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](../components/overview) tai [HTML-elementtikomponentti](/docs/building-ui/web-components/html-elements). Joustavien asettelujen osalta harkitse [`FlexLayout`](../components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) käyttämistä sidottuna komponenttina.

:::note Komponentin laajennus
Älä koskaan laajenna `Component`- tai `DwcComponent`-luokkaa suoraan. Käytä aina koostumusmalleja `Composite`-komponentin rakentamiseen.
:::

Yli kirjoita `initBoundComponent()` kun tarvitset enemmän joustavuutta sidotun komponentin luomisessa ja hallinnassa, kuten käyttämällä parametrisoituja konstruktoreita oletusarvoisen ilman argumenttia -konstruktorin sijasta. Käytä tätä mallia, kun sidottu komponentti vaatii komponentteja siirrettävän sen konstruktorille sen sijaan, että ne lisättäisiin jälkikäteen.

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

webforJ hallitsee kaikkia `Composite`-komponenttien elinkaaren hallintaa automaattisesti. Käyttämällä `getBoundComponent()`-metodia, suurin osa mukautetusta käyttäytymisestä voidaan hallita konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perusasetusten tekeminen ja tapahtumien rekisteröinti.

```java
public class UserDashboard extends Composite<FlexLayout> {
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
   // Etsintälogiikka täällä
 }
}
```

Jos sinulla on lisäkohtaisia asetuksia tai siivousvaatimuksia, sinun on ehkä käytettävä valinnaisia elinkaarin koukkuja `onDidCreate()` ja `onDidDestroy()`:

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Alustaa komponentit, jotka vaativat DOM-liittämistä
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Siivoaa resurssit
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

Seuraava esimerkki esittelee Todo-sovelluksen, jossa jokainen kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radiobutton) -komponentista, joka on muotoiltu kytkimeksi, ja Div:stä, jossa on teksti:

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia ryhmittääksesi liittyviä komponentteja yhteen yksikköön, vaikka uudelleenkäytettävyys ei olisikaan ensisijainen huolenaihe:

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
