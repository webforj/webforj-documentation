---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
Deze eerste stap legt de basis voor de klantenbeheerapp door een eenvoudige, interactieve interface te creëren. Dit toont aan hoe je een basis webforJ-app instelt, met een enkele knop die een dialoog opent wanneer erop wordt geklikt. Het is een eenvoudige implementatie die sleutelcomponenten introduceert en je een gevoel geeft voor hoe webforJ werkt.

Deze stap maakt gebruik van de basis app-klasse die door webforJ wordt geleverd om de structuur en het gedrag van de app te definiëren. Door verder te gaan naar latere stappen, wordt overgeschakeld naar een meer geavanceerde setup met routing om meerdere schermen te beheren, geïntroduceerd in [Schaalvergroting met Routing en Composities](./scaling-with-routing-and-composites).

Aan het einde van deze stap heb je een functionerende app die basisinteractie met componenten en gebeurtenisafhandeling in webforJ demonstreert. Om de app uit te voeren:

- Ga naar de `1-creating-a-basic-app` directory
- Voer het commando `mvn jetty:run` uit

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Een webforJ-app maken {#creating-a-webforj-app}

In webforJ vertegenwoordigt een `App` het centrale knooppunt voor het definiëren en beheren van je project. Elke webforJ-app begint met het creëren van één klasse die de fundamentele `App`-klasse uitbreidt, die als het kernframework dient om:

- De levenscyclus van de app te beheren, inclusief initialisatie en beëindiging.
- Routing en navigatie te behandelen als dit is ingeschakeld.
- Het thema, de locale en andere algemene configuraties van de app te definiëren.
- Essentiële hulpmiddelen te bieden voor interactie met de omgeving en componenten.

### De `App`-klasse uitbreiden {#extending-the-app-class}

Voor deze stap wordt een klasse genaamd `DemoApplication.java` aangemaakt, die de `App`-klasse uitbreidt.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // Kernapp-logica gaat hier
  }
}
```

:::tip Belangrijke Configuratie-Eigenschappen

In deze demo-app is het `webforj.conf`-bestand geconfigureerd met de volgende twee essentiële eigenschappen:

- **`webforj.entry`**: Specificeert de volledig gekwalificeerde naam van de klasse die `App` uitbreidt en fungeert als het hoofdingangspunt voor je project. Stel het voor deze tutorial in op `com.webforj.demos.DemoApplication` om ambiguïteit tijdens initialisatie te voorkomen.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`**: Schakelt de debugmodus in voor gedetailleerde logs en zichtbaarheid van fouten tijdens de ontwikkeling. Zorg ervoor dat dit op `true` is ingesteld terwijl je aan deze tutorial werkt:
  ```hocon
  webforj.debug = true
  ```

Voor meer details over aanvullende configuratie-opties zie de [Configuratiegids](../../configuration/overview).
:::

### De `run()`-methode overschrijven {#overriding-the-run-method}

Nadat je de juiste configuratie voor het project hebt verzekerd, wordt de `run()`-methode in je `App`-klasse overschreven.

De `run()`-methode is de kern van je app in webforJ. Het definieert wat er gebeurt nadat de app is geïnitialiseerd en is het hoofdingangspunt voor de functies van je app. Door de `run()`-methode te overschrijven, kun je de logica implementeren die de gebruikersinterface en het gedrag van je app creëert en beheert.

:::tip Routing gebruiken
Wanneer je routing binnen een app implementeert, is het niet nodig om de `run()`-methode te overschrijven, omdat het framework automatisch de initialisatie van routes en de creatie van het initiële `Frame` afhandelt. De `run()`-methode wordt aangeroepen nadat de bas-route is opgelost, zodat het navigatiesysteem van de app volledig is geïnitialiseerd voordat enige logica wordt uitgevoerd. Deze tutorial zal verder ingaan op de implementatie van routing in [stap 3](scaling-with-routing-and-composites). Meer informatie is ook beschikbaar in het [Routing-artikel](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // App-logica
  }
}
```

## Componenten toevoegen {#adding-components}

In webforJ zijn componenten de bouwstenen van de gebruikersinterface van je app. Deze componenten vertegenwoordigen afzonderlijke stukken van de UI van je app, zoals knoppen, tekstvelden, dialogen of tabellen.

Je kunt een UI beschouwen als een boom van componenten, met een `Frame` als de wortel. Elke component die aan het `Frame` wordt toegevoegd, wordt een tak of blad in deze boom, en draagt bij aan de algehele structuur en het gedrag van je app.

:::tip Componentencatalogus
Zie [deze pagina](../../components/overview) voor een lijst van de verschillende componenten die beschikbaar zijn in webforJ.
:::

### App `Frame` {#app-frame}

De `Frame`-klasse in webforJ vertegenwoordigt een niet-nestbare, bovenliggende venster in je app. Een `Frame` fungeert doorgaans als de belangrijkste container voor UI-componenten, waardoor het een essentieel bouwblok is voor het construeren van de gebruikersinterface. Elke app begint met ten minste één `Frame`, en je kunt componenten zoals knoppen, dialogen of formulieren aan deze frames toevoegen.

Een `Frame` wordt binnen de `run()`-methode in deze stap gemaakt - later zullen hier componenten aan worden toegevoegd.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Server- en client-side componenten {#server-and-client-side-components}

Elke server-side component in webforJ heeft een bijbehorende client-side webcomponent. Server-side componenten beheren logica en backend-interacties, terwijl client-side componenten zoals `dwc-button` en `dwc-dialog` de front-end weergave en styling beheersen.

:::tip Composite componenten

Naast de kerncomponenten die door webforJ worden geleverd, kun je aangepaste samengestelde componenten ontwerpen door meerdere elementen samen te voegen tot een eenheid die herbruikbaar is. Dit concept wordt in deze stap van de tutorial behandeld. Meer informatie is beschikbaar in het [Composite-artikel](../../building-ui/composite-components)
:::

Componenten moeten worden toegevoegd aan een containerklasse die de <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>-interface implementeert. Het `Frame` is zo'n klasse - voor deze stap voeg je een `Paragraph` en een `Button` toe aan het `Frame`, die in de UI in de browser zullen worden weergegeven:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Demo Applicatie!");
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

Het uitvoeren hiervan zou je een eenvoudig gestileerde knop moeten geven die een bericht laat oppoppen met de tekst "Dit is een demo!".

## Stylen met CSS {#styling-with-css}

Styling in webforJ geeft je volledige vrijheid om het uiterlijk van je app te ontwerpen. Hoewel het framework ondersteuning biedt voor een samenhangend ontwerp en stijl uit de doos, dwingt het geen specifieke stylingaanpak af, zodat je aangepaste stijlen kunt toepassen die aansluiten bij de vereisten van je app.

Met webforJ kun je dynamisch class-namen aan componenten toepassen voor conditionele of interactieve styling, CSS gebruiken voor een consistent en schaalbaar ontwerpsysteem, en hele inline of externe stylesheets injecteren.

### CSS-klassen aan componenten toevoegen {#adding-css-classes-to-components}

Je kunt dynamisch class-namen aan componenten toevoegen of verwijderen met de methoden `addClassName()` en `removeClassName()`. Deze methoden stellen je in staat om de stijlen van de component te controleren op basis van de logica van je app. Voeg de class-naam `mainFrame` toe aan het `Frame` dat in de vorige stappen is gemaakt door de volgende code in de `run()`-methode op te nemen:

```java
mainFrame.addClassName("mainFrame");
```

### CSS-bestanden aanhechten {#attaching-css-files}

Om je app te stylen, kun je CSS-bestanden opnemen in je project, hetzij door gebruik te maken van asset-annotaties of door de webforJ <JavadocLink type="foundation" location="com/webforj/Page" >asset API</JavadocLink> in tijd gebruik. [Zie dit artikel](../../managing-resources/importing-assets) voor meer informatie. 

Bijvoorbeeld, de @StyleSheet-annotatie wordt gebruikt om stijlen op te nemen vanaf de resources/static-directory. Het genereert automatisch een URL voor het opgegeven bestand en injecteert het in de DOM, zodat de stijlen op je app worden toegepast. Houd er rekening mee dat bestanden buiten de statische directory niet toegankelijk zijn.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // App-logica hier
  }
}
```
:::tip Webserver-URLs
Om ervoor te zorgen dat statische bestanden toegankelijk zijn, moeten ze in de resources/static-map worden geplaatst. Om een statisch bestand op te nemen, kun je de URL construeren met behulp van het webserverprotocol.
:::

### Voorbeeld CSS-code {#sample-css-code}

Een CSS-bestand wordt in je project gebruikt op `resources > static > css > demoApplication.css`, en de volgende CSS wordt gebruikt om enkele basisstijlen aan de app toe te passen.

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

De CSS-stijlen worden toegepast op het hoofd `Frame` en bieden structuur door componenten met een [gridlay-out](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout) te rangschikken, en het toevoegen van marge-, padding- en randstijlen om de UI visueel georganiseerd te maken.
