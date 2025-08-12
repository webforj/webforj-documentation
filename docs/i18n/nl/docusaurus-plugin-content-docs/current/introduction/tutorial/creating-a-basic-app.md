---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
Deze eerste stap legt de basis voor de klantbeheertoepassing door een eenvoudige, interactieve interface te creëren. Dit toont aan hoe je een basis webforJ-app kunt opzetten, met een enkele knop die een dialoog opent wanneer erop wordt geklikt. Het is een rechttoe rechtaan implementatie die belangrijke componenten introduceert en je een gevoel geeft voor hoe webforJ werkt.

Deze stap maakt gebruik van de basisklasse voor de app die door webforJ wordt geleverd om de structuur en het gedrag van de app te definiëren. Door door te gaan naar latere stappen zal je overschakelen naar een meer geavanceerde opzet met routing om meerdere schermen te beheren, zoals geïntroduceerd in [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

Aan het einde van deze stap heb je een functionele app die de basisinteractie met componenten en gebeurtenisafhandeling in webforJ demonstreert. Om de app uit te voeren:

- Ga naar de `1-creating-a-basic-app` directory
- Voer de opdracht `mvn jetty:run` uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Het maken van een webforJ-app {#creating-a-webforj-app}

In webforJ vertegenwoordigt een `App` het centrale knooppunt voor het definiëren en beheren van je project. Elke webforJ-app begint met het creëren van één klasse die de fundamentele `App`-klasse uitbreidt, die als het kernframework dient om:

- De levenscyclus van de app te beheren, inclusief initialisatie en beëindiging.
- Routing en navigatie af te handelen indien ingeschakeld.
- Het thema, de locale en andere algemene configuraties van de app te definiëren.
- Essentiële hulpmiddelen te bieden voor interactie met de omgeving en componenten.

### De `App`-klasse uitbreiden {#extending-the-app-class}

Voor deze stap wordt een klasse genaamd `DemoApplication.java` gemaakt, die de `App`-klasse uitbreidt.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Kern logica van de app gaat hier
  }
}
```

:::tip Belangrijke configuratie-eigenschappen

In deze demo-app is het `webforj.conf`-bestand geconfigureerd met de volgende twee essentiële eigenschappen:

- **`webforj.entry`**: Specificeert de volledig gekwalificeerde naam van de klasse die de `App` uitbreidt en die fungeert als het belangrijkste ingangspunt voor je project. Voor deze tutorial moet je het instellen op `com.webforj.demos.DemoApplication` om ambiguïteit tijdens de initialisatie te voorkomen.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Schakelt de debugmodus in voor gedetailleerde logs en foutzichtbaarheid tijdens de ontwikkeling. Zorg ervoor dat dit is ingesteld op `true` terwijl je aan deze tutorial werkt:
  ```hocon
  webforj.debug = true
  ```

Voor meer details over aanvullende configuratiemogelijkheden, zie de [Configuratiehandleiding](../../configuration/overview).
:::

### De `run()`-methode overschrijven {#overriding-the-run-method}

Nadat je de juiste configuratie voor het project hebt gegarandeerd, wordt de `run()`-methode in je `App`-klasse overschreven.

De `run()`-methode is de kern van je app in webforJ. Het definieert wat er gebeurt nadat de app is geïnitialiseerd en is het belangrijkste ingangspunt voor de functies van je app. Door de `run()`-methode te overschrijven, kun je de logica implementeren die de gebruikersinterface en het gedrag van je app creëert en beheert.

:::tip Gebruik van routing
Wanneer je routing binnen een app implementeert, is het niet nodig om de `run()`-methode te overschrijven, omdat het framework automatisch de initialisatie van routes en de creatie van het initiële `Frame` afhandelt. De `run()`-methode wordt aangeroepen nadat de basismethode is opgelost, zodat het navigatiesysteem van de app volledig is geïnitialiseerd voordat enige logica wordt uitgevoerd. Deze tutorial zal dieper ingaan op het implementeren van routing in [stap 3](scaling-with-routing-and-composites). Meer informatie is ook beschikbaar in het [Routing-artikel](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // App logica
  }
}
```

## Componenten toevoegen {#adding-components}

In webforJ zijn componenten de bouwstenen van de gebruikersinterface van je app. Deze componenten vertegenwoordigen discrete delen van de UI van je app, zoals knoppen, tekstvelden, dialoogvensters of tabellen.

Je kunt een UI beschouwen als een boom van componenten, waarbij een `Frame` als de wortel fungeert. Elke component die aan het `Frame` wordt toegevoegd, wordt een tak of blad in deze boom en draagt bij aan de algehele structuur en het gedrag van je app.

:::tip Componentencatalogus
Zie [deze pagina](../../components/overview) voor een lijst van de verschillende componenten die beschikbaar zijn in webforJ.
:::

### App `Frame` {#app-frame}

De `Frame`-klasse in webforJ vertegenwoordigt een niet-nestbare, top-level venster in je app. Een `Frame` fungeert typisch als de belangrijkste container voor UI-componenten, waardoor het een essentiële bouwsteen is voor het construeren van de gebruikersinterface. Elke app begint met ten minste één `Frame`, en je kunt componenten zoals knoppen, dialoogvensters of formulieren aan deze frames toevoegen.

In deze stap wordt een `Frame` binnen de `run()`-methode gemaakt - later zullen hier componenten aan worden toegevoegd.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Server- en client-side componenten {#server-and-client-side-components}

Elke server-side component in webforJ heeft een bijbehorende client-side webcomponent. Server-side componenten verwerken logica en backend-interacties, terwijl client-side componenten zoals `dwc-button` en `dwc-dialog` het frontend-renderen en de opmaak beheren.

:::tip Composite componenten

Naast de kerncomponenten die door webforJ worden aangeboden, kun je aangepaste samengestelde componenten ontwerpen door meerdere elementen te groeperen in een enkele herbruikbare eenheid. Dit concept wordt in deze stap van de tutorial behandeld. Meer informatie is beschikbaar in het [Composite-artikel](../../building-ui/composite-components).
:::

Componenten moeten worden toegevoegd aan een containerklasse die de <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true'>HasComponents</JavadocLink>-interface implementeert. De `Frame` is zo'n klasse - voor deze stap, voeg een `Paragraph` en een `Button` toe aan de `Frame`, die in de UI in de browser zal worden weergegeven:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo-toepassing!");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("Dit is een demo!", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

Het uitvoeren hiervan zou je een eenvoudige gestileerde knop moeten geven waarmee een bericht verschijnt met de tekst "Dit is een demo!"

## Stylen met CSS {#styling-with-css}

Stylen in webforJ geeft je volledige flexibiliteit om het uiterlijk van je app te ontwerpen. Terwijl het framework een samenhangend ontwerp en stijl uit de doos ondersteunt, dwingt het geen specifieke stijlaanpak af, waardoor je aangepaste stijlen kunt toepassen die zijn afgestemd op de vereisten van je app.

Met webforJ kun je dynamisch klassenamen aan componenten toepassen voor conditionele of interactieve styling, CSS gebruiken voor een consistent en schaalbaar ontwerpsysteem, en volledige inline of externe stylesheets injecteren.

### CSS-klassen aan componenten toevoegen {#adding-css-classes-to-components}

Je kunt dynamisch klassenamen aan componenten toevoegen of verwijderen met behulp van de methoden `addClassName()` en `removeClassName()`. Deze methoden stellen je in staat om de stijlen van de component te controleren op basis van de logica van je app. Voeg de klasse `mainFrame` toe aan het `Frame` dat in de vorige stappen is gemaakt door de volgende code in de `run()`-methode op te nemen:

```java
mainFrame.addClassName("mainFrame");
```

### CSS-bestanden aanhechten {#attaching-css-files}

Om je app te stylen, kun je CSS-bestanden in je project opnemen, hetzij door gebruik te maken van asset-annotaties of door de webforJ <JavadocLink type="foundation" location="com/webforj/Page">asset API</JavadocLink> tijdens runtime te benutten. [Zie dit artikel](../../managing-resources/importing-assets) voor meer informatie.

Bijvoorbeeld, de @StyleSheet-annotatie wordt gebruikt om stijlen uit de resources/static-directory in te voegen. Het genereert automatisch een URL voor het opgegeven bestand en injecteert het in de DOM, zodat de stijlen op je app kunnen worden toegepast. Opmerking: bestanden buiten de statische directory zijn niet toegankelijk.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // App logica hier
  }
}
```
:::tip Webserver-URL's
Om ervoor te zorgen dat statische bestanden toegankelijk zijn, moeten ze in de resources/static-map worden geplaatst. Om een statisch bestand op te nemen, kun je de URL construeren met behulp van het webserverprotocol.
:::

### Voorbeeld CSS-code {#sample-css-code}

Een CSS-bestand wordt in je project gebruikt in `resources > static > css > demoApplication.css`, en de volgende CSS wordt gebruikt om enkele basisstijlen op de app toe te passen.

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Zodra dit is gedaan, moet de volgende annotatie aan je `App`-klasse worden toegevoegd:

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Demo Stap 1")
public class DemoApplication extends App {
```

De CSS-stijlen worden toegepast op het hoofd `Frame` en bieden structuur door componenten met een [grid-layout](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) te rangschikken en marge-, padding- en randstijlen toe te voegen om de UI visueel georganiseerd te maken.
