---
title: App Basics
sidebar_position: 3
_i18n_hash: 23f93367391ac7cd42c28bf4cd3640ee
---
Zodra webforJ en zijn afhankelijkheden zijn ingesteld in uw project, bent u klaar om de app-structuur te creëren. Dit artikel doorloopt de belangrijkste elementen van een basis webforJ-app, met specifieke focus op de `Application` en `HomeView` klassen, die de fundamentele klassen zijn in het `webforj-archetype-hello-world` starterproject.

## Hoofd app-klasse: `Application.java` {#main-app-class-applicationjava}

De `Application` klasse fungeert als het instappunt voor uw webforJ-app, waarbij essentiële configuraties en routes worden ingesteld. Om te beginnen, let op de declaratie van de klasse en de annotaties.

Deze klasse breidt de kern `App` klasse van webforJ uit, waardoor het herkenbaar is als een webforJ-app. Verschillende annotaties configureren het thema, de titel en de routing van de app.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Geeft aan dat webforJ het `com.samples.views` pakket moet scannen op routecomponenten.
- `@AppTitle`: Definieert de titel die op het browsertabblad van de app wordt weergegeven.
- `@StyleSheet`: Verbindt een extern CSS-bestand, `app.css`, waardoor aangepaste styling voor de app mogelijk is.

De `Application` klasse bevat geen extra methoden omdat de configuraties zijn ingesteld via annotaties, en webforJ de app-initialisatie afhandelt.

Met `Application.java` ingesteld, is de app nu geconfigureerd met een titel en routes die naar het views-pakket wijzen. Vervolgens geeft een overzicht van de `HomeView` klasse inzicht in wat er wordt weergegeven wanneer de app wordt uitgevoerd.

### Ontdekken van een `App` {#discovering-an-app}

Een enkele <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> limiet wordt afgedwongen in webforJ, wat alle verantwoordelijkheden voor foutafhandeling naar de Java-kant verschuift en ontwikkelaars volledige controle geeft over het foutbeheer.

Tijdens het webforJ bootstrapproces worden alle klassen die <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> uitbreiden, gescand. Als er meerdere apps worden gevonden, zoekt het systeem naar de <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> annotatie. Als een van de ontdekte klassen is geannoteerd met <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, wordt de eerste die wordt tegengekomen als het instappunt beschouwd.

- Als een klasse is geannoteerd met `@AppEntry`, wordt die klasse geselecteerd als het instappunt.
- Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering opgegooid waarin alle ontdekte klassen worden vermeld.
- Als er geen klasse is geannoteerd en er slechts één subklasse van `App` is gevonden, wordt die klasse geselecteerd als het instappunt.
- Als er geen klasse is geannoteerd en er meerdere subklassen van `App` zijn gevonden, wordt er een uitzondering opgegooid met een gedetailleerd overzicht van elke subklasse.

:::tip Foutafhandeling
Voor meer informatie over hoe fouten worden behandeld in webforJ, zie [dit artikel](../advanced/error-handling).
:::

## Hoofd weergave-klasse: `HomeView.java` {#main-view-class-homeviewjava}

De `HomeView` klasse definieert een eenvoudig weergavecomponent dat fungeert als de homepage voor de app. Het toont een veld en een knop om de naam van de gebruiker te begroeten.

### Klasse declaratie en annotaties {#class-declaration-and-annotations}

`HomeView` breidt `Composite<FlexLayout>` uit, waardoor het kan functioneren als een herbruikbaar component dat is samengesteld uit een [`FlexLayout`](../components/flex-layout) component. De [`@Route("/")`](../routing/overview) maakt dit de rootroute van de app.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wat is je naam?");
  private Button btn = new Button("Zeg Hallo");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Welkom bij webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Componentinitialisatie {#component-initialization}

Binnen de klasse worden verschillende UI-elementen geïnitialiseerd en gedeclareerd:

```java
private final FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Wat is je naam?");
private Button btn = new Button("Zeg Hallo");
```

- `self`: Het hoofdlayoutcomponent dat gebruikmaakt van [`FlexLayout`](../components/flex-layout), geconfigureerd als container voor de elementen. Dit element maakt gebruik van de methode `getBoundComponent()` om de belangrijkste `FlexLayout` die de klasse bevat, op te slaan.
- `hello`: Een [`TextField`](../components/fields/textfield) gelabeld met `Wat is je naam?` voor gebruikers om hun naam in te voeren.
- `btn`: Een primair gestyleerde [`Button`](../components/button) gelabeld met `Zeg Hallo`.

### Layoutconfiguratie {#layout-configuration}

De layout `(self)` is geconfigureerd met een paar belangrijke stijlproperties:

- `FlexDirection.COLUMN` stapelt de elementen verticaal.
- `setMaxWidth(300)` beperkt de breedte tot 300 pixels voor een compact ontwerp.
- `setStyle("margin", "1em auto")` centreert de lay-out met een marge eromheen.

### Componenten toevoegen aan de lay-out {#adding-components-to-the-layout}
Ten slotte worden het hello-tekstveld en de btn-knop toegevoegd aan de [`FlexLayout`](../components/flex-layout) container door `self.add(hello, btn)` aan te roepen. Deze indeling definieert de structuur van de weergave, waardoor het formulier zowel interactief als visueel gecentreerd is.

## Styling van de app {#styling-the-app}

Het `styles.css` bestand biedt aangepaste styling voor uw webforJ-app. Dit CSS-bestand wordt in de Application-klasse vermeld met behulp van de [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotatie, waardoor de app stijlen kan toepassen op componenten binnen de app.

Dit bestand bevindt zich in de `resources/static` directory van het project en kan worden vermeld met behulp van de webserver-URL `ws://app.css`.
