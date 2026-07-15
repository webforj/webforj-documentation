---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: 7c98bf3851e1db10d5e0dd68045ea22d
---
In [Project Setup](/docs/introduction/tutorial/project-setup) heb je een webforJ-project gegenereerd. Het is nu tijd om de hoofdklasse voor het project te maken en een interactieve interface toe te voegen met behulp van webforJ-componenten. In deze stap leer je over:

- Het instappunt voor apps die gebruik maken van webforJ en Spring Boot
- webforJ en HTML-elementcomponenten
- Het gebruik van CSS voor het stylen van componenten

Het voltooien van deze stap creëert een versie van [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Voeg hier video in -->

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de hoofdmap met het `pom.xml`-bestand, dit is `1-creating-a-basic-app` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Het instappunt {#entry-point}

Elke webforJ-app bevat een enkele klasse die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> uitbreidt. Voor deze tutorial en andere gepubliceerde webforJ-projecten wordt het vaak `Application` genoemd. Deze klasse bevindt zich in een pakket dat is genoemd naar de `groupId` die je hebt gebruikt in [Project Setup](/docs/introduction/tutorial/project-setup):

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

Binnen de `Application`-klasse gebruikt de `SpringApplication.run()`-methode de configuraties om de app te starten. De verschillende annotaties zijn voor de configuraties van de app.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotaties {#annotations}

De [`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) is een kernannotatie in Spring Boot. Je plaatst deze annotatie op de hoofdklasse om deze te markeren als het startpunt van je app.

`@StyleSheet`, `@AppTheme` en `@AppProfile` zijn slechts enkele van de vele <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">webforJ-annotaties</JavadocLink> die beschikbaar zijn wanneer je expliciet configuraties wilt instellen.

- **`@StyleSheet`** embed een CSS-bestand in de webpagina. Verdere details over hoe je met een specifiek CSS-bestand kunt omgaan, worden later behandeld in [Styling met CSS](#styling-with-css).

- **`@AppTheme`** beheert het visuele thema van de app. Als ingesteld op `system`, adopteert de app automatisch het voorkeurs-thema van de gebruiker: `light`, `dark` of `dark-pure`. Voor informatie over het maken van aangepaste thema's of het overschrijven van de standaardthema's, raadpleeg het artikel over [Thema's](/docs/styling/themes).

- **`@AppProfile`** helpt bij het configureren hoe de app zich presenteert aan de gebruiker als een [installeerbare app](/docs/configuration/installable-apps). Bij minimaal deze annotatie is een `name` voor de volledige naam van de app en een `shortName` nodig voor gebruik wanneer de ruimte beperkt is. De `shortName` mag niet meer dan 12 tekens bevatten.

## Een gebruikersinterface maken {#creating-a-ui}

Om je UI te maken, moet je [HTML-elementcomponenten](/docs/components/html-elements) en [webforJ-componenten](/docs/components/overview) toevoegen. Voor nu heb je alleen een enkele pagina-app, dus je voegt componenten rechtstreeks toe aan de `Application`-klasse.
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
Maak een nieuwe instantie van de component en gebruik de `add()`-methode om deze toe te voegen aan het `Frame`:

```java
// Maak de container voor de UI-elementen
Frame mainFrame = new Frame();

// Maak de HTML-component
Paragraph tutorial = new Paragraph("Tutorial Application!");

// Voeg de component toe aan de container
mainFrame.add(tutorial);
```

### webforJ-componenten gebruiken {#webforj-components-and-html-elements}

Terwijl HTML-elementen nuttig zijn voor structuur, semantiek en lichte UI-behoeften, bieden [webforJ-componenten](/docs/components/overview) meer complexe en dynamische gedrag.

De onderstaande code voegt een [Button](/docs/components/button)-component toe, verandert het uiterlijk met de `setTheme()`-methode en voegt een gebeurtenisluiser toe om een [Message Dialog](/docs/components/option-dialogs/message)-component te maken wanneer de knop wordt aangeklikt. De meeste webforJ-componentmethoden die een component wijzigen, retourneren de component zelf, zodat je meerdere methoden kunt ketenen voor compactere code.

```java
// Maak de container voor de UI-elementen
Frame mainFrame = new Frame();

// Maak de webforJ-component
Button btn = new Button("Info");

// Wijzig de webforJ-component en voeg een gebeurtenisluiser toe
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Dit is een tutorial!", "Info"));

// Voeg de component toe aan de container
mainFrame.add(btn);
```

## Stylen met CSS {#styling-with-css}

De meeste webforJ-componenten hebben ingebouwde methoden om veelvoorkomende stijlwijzigingen aan te brengen, zoals grootte en thematisering.

```java
// Stel de breedte van het Frame in met een CSS-sleutelwoord
mainFrame.setWidth("fit-content");

// Stel de maximale breedte van de knop in met pixels
btn.setMaxWidth(200);

// Stel het thema van de knop in op PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

Naast deze methoden kun je je app stylen met CSS. De sectie **Styling** van de documentatiepagina van elke component bevat specifieke details over de relevante CSS-eigenschappen.

webforJ komt ook met een set ontworpen CSS-variabelen genaamd DWC-tokens. Zie de documentatie over [Styling](/docs/styling/overview) voor gedetailleerde informatie over het stylen van webforJ-componenten en hoe de tokens te gebruiken.

### Een CSS-bestand refereren {#referencing-a-css-file}

Het is het beste om een apart CSS-bestand te hebben om alles georganiseerd en onderhoudbaar te houden. Maak een bestand met de naam `card.css` in `src/main/resources/static/css`, met de volgende CSS-classdefinitie:

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

Verwijs vervolgens naar het bestand in `Application.java` door de `@StyleSheet`-annotatie te gebruiken met de naam van het CSS-bestand. Voor deze stap is het `@StyleSheet("ws://css/card.css")`.

:::tip Webserverprotocol
Deze tutorial gebruikt het Webserverprotocol om naar het CSS-bestand te verwijzen. Om meer te leren over hoe dit werkt, zie [Resources beheren](/docs/managing-resources/overview).
:::

### CSS-klassen aan componenten toevoegen {#adding-css-classes-to-components}

Je kunt dynamisch klassennamen aan componenten toevoegen of verwijderen met de methoden `addClassName()` en `removeClassName()`. Voor deze tutorial wordt er maar één CSS-klasse gebruikt:

```java
mainFrame.addClassName("card");
```

## Voltooide `Application` {#completed-application}

Je `Application`-klasse zou er nu ongeveer zo uit moeten zien:

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
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
Voor een complexere app kun je de UI opdelen in meerdere pagina's voor een betere organisatie. Dit concept wordt later in deze tutorial behandeld in [Routing en Composities](/docs/introduction/tutorial/routing-and-composites).
:::

## Volgende stap {#next-step}

Na het maken van een functionele app met een basisgebruikersinterface, is de volgende stap het toevoegen van een datamodel en het weergeven van de resultaten in een `Table`-component in [Werken met Gegevens](/docs/introduction/tutorial/working-with-data).
