---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
In [Project Setup](/docs/introduction/tutorial/project-setup) heb je een webforJ-project gegenereerd. Nu is het tijd om de hoofdklasse voor het project te maken en een interactieve interface toe te voegen met behulp van webforJ-componenten. In deze stap leer je over:

- Het toegangspunt voor apps die gebruik maken van webforJ en Spring Boot
- webforJ- en HTML-elementcomponenten
- Het gebruik van CSS om componenten op te maken

Het voltooien van deze stap creëert een versie van [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insert video here -->

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de topdirectory die het `pom.xml`-bestand bevat, dit is `1-creating-a-basic-app` als je het volgt met de versie op GitHub.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Het toegangspunt {#entry-point}

Elke webforJ-app bevat een enkele klasse die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> uitbreidt. Voor deze tutorial, en andere gepubliceerde webforJ-projecten, wordt deze vaak `Application` genoemd. Deze klasse bevindt zich in een pakket dat vernoemd is naar de `groupId` die je gebruikte in [Project Setup](/docs/introduction/tutorial/project-setup):

```
1-creating-a-basic-app 
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

Binnen de `Application`-klasse gebruikt de `SpringApplication.run()`-methode de configuraties om de app te lanceren. De verschillende annotaties zijn voor de configuraties van de app.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Klantapplicatie", shortName = "KlantApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotaties {#annotations}

De [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) is een kernannotatie in Spring Boot. Je plaatst deze annotatie op de hoofdklasse om deze als het startpunt van je app te markeren.

`@StyleSheet`, `@AppTheme` en `@AppProfile` zijn slechts enkele van de vele <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-annotaties</JavadocLink> die beschikbaar zijn wanneer je expliciet configuraties wilt instellen.

- **`@StyleSheet`** embedt een CSS-bestand in de webpagina. Verdere details over hoe te interageren met een specifiek CSS-bestand worden later gevonden in [Styling met CSS](#styling-with-css).

- **`@AppTheme`** beheert het visuele thema van de app. Als ingesteld op `system`, neemt de app automatisch het door de gebruiker geprefereerde thema aan: `licht`, `donker` of `donker-puur`. Voor informatie over het maken van aangepaste thema's of het overschrijven van de standaardthema's, zie het artikel over [Thema's](/docs/styling/themes).

- **`@AppProfile`** helpt bij het configureren hoe de app zich aan de gebruiker presenteert als een [installeerbare app](/docs/configuration/installable-apps). Minimaal heeft deze annotatie een `name` voor de volledige naam van de app en een `shortName` voor gebruik wanneer de ruimte beperkt is. De `shortName` mag niet meer dan 12 tekens bevatten.  

## Een gebruikersinterface maken {#creating-a-ui}

Om je UI te maken, moet je [HTML-elementcomponenten](/docs/components/html-elements) en [webforJ-componenten](/docs/components/overview) toevoegen. Voor nu heb je alleen een enkele pagina-app, dus je voegt componenten direct toe aan de `Application`-klasse. 
Om dit te doen, overschrijf je de `App.run()`-methode en maak je een `Frame` om componenten aan toe te voegen. 

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Maak UI-componenten en voeg ze toe aan het frame

}
```

### HTML-elementen gebruiken {#using-html-elements}

Je kunt standaard HTML-elementen aan je app toevoegen met [HTML-elementcomponenten](/docs/components/html-elements).
Maak een nieuw exemplaar van de component en gebruik de `add()`-methode om deze aan het `Frame` toe te voegen:

```java
// Maak de container voor de UI-elementen
Frame mainFrame = new Frame();

// Maak de HTML-component
Paragraph tutorial = new Paragraph("Tutorial Applicatie!");

// Voeg de component toe aan de container
mainFrame.add(tutorial);
```

### webforJ-componenten gebruiken {#webforj-components-and-html-elements}

Hoewel HTML-elementen nuttig zijn voor structuur, semantiek en lichte UI-behoeften, bieden [webforJ-componenten](/docs/components/overview) complexere en dynamischere gedragingen.

De onderstaande code voegt een [Button](/docs/components/button)-component toe, verandert het uiterlijk met de `setTheme()`-methode en voegt een gebeurtenisluisteraar toe om een [Message Dialog](/docs/components/option-dialogs/message)-component te creëren wanneer de knop wordt ingedrukt.
De meeste methoden van webforJ-componenten die een component wijzigen, retourneren de component zelf, zodat je meerdere methoden kunt ketenen voor compactere code.

```java
// Maak de container voor de UI-elementen
Frame mainFrame = new Frame();

// Maak de webforJ-component
Button btn = new Button("Info");

// Wijzig de webforJ-component en voeg een gebeurtenisluisteraar toe
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Dit is een tutorial!", "Info"));

// Voeg de component toe aan de container
mainFrame.add(btn);
```

## Styling met CSS {#styling-with-css}

De meeste webforJ-componenten hebben ingebouwde methoden om veelvoorkomende stijlwijzigingen aan te brengen, zoals grootte en thema's.

```java
// Stel de breedte van het Frame in met een CSS-tekenwoord
mainFrame.setWidth("fit-content");

// Stel de maximale breedte van de knop in met pixels
btn.setMaxWidth(200);

// Stel het thema van de knop in op PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

Naast deze methoden kun je je app stylen met CSS. Het **Styling**-gedeelte van elke documentatiepagina van componenten heeft specifieke details over de relevante CSS-eigenschappen.

webforJ wordt ook geleverd met een set ontworpen CSS-variabelen genaamd DWC-tokens. Zie de [Styling](/docs/styling/overview)-documentatie voor gedetailleerde informatie over het stylen van webforJ-componenten en hoe de tokens te gebruiken.

### Een CSS-bestand refereren {#referencing-a-css-file} 

Het is het beste om een afzonderlijk CSS-bestand te hebben om alles georganiseerd en onderhoudbaar te houden. Maak een bestand genaamd `card.css` in `src/main/resources/static/css`, met de volgende CSS-class-definitie:

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Verwijs dan naar het bestand in `Application.java` met de `@StyleSheet`-annotatie en de naam van het CSS-bestand. Voor deze stap is het `@StyleSheet("ws://css/card.css")`.

:::tip Webserverprotocol
Deze tutorial gebruikt het Webserverprotocol om naar het CSS-bestand te verwijzen. Om meer te leren over hoe dit werkt, zie [Beheer van bronnen](/docs/managing-resources/overview).
:::

### CSS-klassen aan componenten toevoegen {#adding-css-classes-to-components}

Je kunt dynamisch klassennamen aan componenten toevoegen of verwijderen met de methoden `addClassName()` en `removeClassName()`. Voor deze tutorial wordt er slechts één CSS-klasse gebruikt:

```java
mainFrame.addClassName("card");
```

## Voltooide `Application` {#completed-application}

Je `Application`-klasse zou er nu ongeveer als volgt uit moeten zien:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Klantapplicatie", shortName = "KlantApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Dit is een tutorial!", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Meerdere pagina's
Voor een complexere app kun je de UI in meerdere pagina's opdelen voor een betere organisatie. Dit concept wordt later in deze tutorial behandeld in [Routing en Composities](/docs/introduction/tutorial/routing-and-composites).
:::

## Volgende stap {#next-step}

Na het maken van een functionele app met een basisgebruikersinterface, is de volgende stap om een gegevensmodel toe te voegen en de resultaten in een `Table`-component weer te geven in [Werken met gegevens](/docs/introduction/tutorial/working-with-data).
