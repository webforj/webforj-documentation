---
sidebar_position: 4
title: Komponenttien Koostaminen
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 7ca404aa73a9fd445ce7cd3da09b8155
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

`Composite`-komponentti yhdistää olemassa olevat webforJ-komponentit itsenäisiksi, uudelleenkäytettäviksi komponenteiksi, joilla on mukautettua käyttäytymistä. Käytä sitä sisäisten webforJ-komponenttien kääreenä uudelleenkäytettävien liiketoimintalogiikkayksiköiden luomiseksi, komponenttimallien uudelleenkäytöksi sovelluksessasi ja useiden komponenttien yhdistämiseksi ilman toteutustietojen paljastamista.

`Composite`-komponentilla on vahva yhteys taustalla olevaan sidottuun komponenttiin. Tämä antaa sinulle hallinnan siitä, mitkä menetelmät ja ominaisuudet käyttäjät voivat käyttää, toisin kuin perinteisessä perinnössä, jossa kaikki on paljastettu.

Jos sinun tarvitsee integroida web-komponentteja toisesta lähteestä, käytä erikoistuneita vaihtoehtoja:

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html): Web-komponentteihin, joissa on tyyppiturvallinen ominaisuusjohtaminen
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html): Web-komponentteihin, jotka hyväksyvät slottit sisältöä

<AISkillTip skill="webforj-creating-components" />

## Käyttö {#usage}

Määritelläksesi `Composite`-komponentin, laajenna `Composite`-luokkaa ja määritä sen hallitseman komponentin tyyppi. Tämä tulee olemaan sidottu komponenttisi, joka on juuripurkkisi, joka pitää sisäisen rakenteesi:

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

`getBoundComponent()`-metodi tarjoaa pääsyn taustalla olevaan komponenttiisi, jolloin voit konfiguroida sen ominaisuudet, lisätä lapsikomponentteja ja hallita sen käyttäytymistä suoraan.

Sidottu komponentti voi olla mikä tahansa [webforJ-komponentti](/docs/components/overview) tai [HTML-elementtikomponentti](/docs/components/html-elements). Joustavia asetteluja varten harkitse [`FlexLayout`](/docs/components/flex-layout) tai [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) sidottuna komponenttina.

:::note Komponentin laajentaminen
Älä koskaan laajenna `Component`- tai `DwcComponent`-luokkia suoraan. Käytä aina koostumismalleja `Composite`-komponenttien rakentamiseen.
:::

Ylikirjoita `initBoundComponent()`, kun tarvitset enemmän joustavuutta sidotun komponentin luomisessa ja hallinnassa, esimerkiksi kun käytät parametrisoituja konstruktoreita oletus ilman argumentteja konstruktorin sijaan. Käytä tätä mallia, kun sidottu komponentti vaatii komponentteja annettavaksi sen konstruktorille sen jälkeen, kun ne on lisätty.

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

webforJ hoitaa kaikkien `Composite`-komponenttien elinkaarihallinnan automaattisesti. Käyttämällä `getBoundComponent()`-metodia, suurin osa erityisestä käyttäytymisestä voidaan käsitellä konstruktorissa, mukaan lukien lapsikomponenttien lisääminen, ominaisuuksien asettaminen, perusasetelun luominen ja tapahtumien rekisteröinti.

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

Jos sinulla on erityisiä asetus- tai puhdistusvaatimuksia, saatat joutua käyttämään valinnaisia elinkaarihakuja `onDidCreate()` ja `onDidDestroy()`:

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
   // Tietojen päivittämisen logiikka
 }
}
```

Jos sinun on suoritettava toimenpiteitä komponentin liittämisen jälkeen DOMiin, käytä `whenAttached()`-metodia:

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

Seuraava esimerkki havainnollistaa tehtävälistaa, jossa jokainen kohde on `Composite`-komponentti, joka sisältää [`RadioButton`](/docs/components/radiobutton), `Div`:n tekstillä ja [`Button`](/docs/components/button).

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Esimerkki: Komponenttien ryhmittely {#example-component-grouping}

Joskus saatat haluta käyttää `Composite`-komponenttia ryhmittämään liittyviä komponentteja yhteen yksikköön, vaikka uudelleenkäytettävyys ei olisikaan pääasia:

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
