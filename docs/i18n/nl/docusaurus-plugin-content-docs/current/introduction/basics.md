---
title: App Basics
description: >-
  Walk through the Application and HomeView classes of the hello-world archetype
  to see how routing, annotations, and views shape a webforJ app.
sidebar_position: 3
_i18n_hash: 2ebddfe300802013e4376681bc2ccf04
---
Zodra webforJ en zijn afhankelijkheden zijn ingesteld in uw project, bent u klaar om de app-structuur te maken. Dit artikel behandelt de belangrijkste elementen van een basis webforJ-app, met specifieke focus op de `Application` en `HomeView` klassen, die de basis klassen zijn in het `webforj-archetype-hello-world` starterproject.

## Hoofd app klasse: `Application.java` {#main-app-class-applicationjava}

De `Application` klasse fungeert als het instappunt voor uw webforJ-app en stelt essentiële configuraties en routes in. Let op de declaratie en annotaties van de klasse.

Deze klasse breidt de kern `App` klasse van webforJ uit, waardoor het herkenbaar is als een webforJ-app. Diverse annotaties configureren het thema, de titel en de routing van de app.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Geeft aan dat webforJ de `com.samples.views` package moet scannen op routecomponenten.
- `@AppTitle`: Definieert de titel die op de browsertab van de app wordt weergegeven.
- `@StyleSheet`: Linkt een extern CSS-bestand, `app.css`, waardoor aangepaste opmaak voor de app mogelijk is.

De `Application` klasse bevat geen aanvullende methoden omdat de configuraties via annotaties zijn ingesteld en webforJ de initialisatie van de app beheert.

Met `Application.java` ingesteld, is de app nu geconfigureerd met een titel en routes die naar de views-package wijzen. Vervolgens geeft een overzicht van de `HomeView` klasse inzicht in wat er wordt weergegeven wanneer de app wordt uitgevoerd.

### Ontdekken van een `App` {#discovering-an-app}

Een enkele <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> limiet wordt gehandhaafd in webforJ, die alle foutafhandelingsverantwoordelijkheden naar de Java-kant verschuift en ontwikkelaars volledige controle over foutbeheer geeft.

Tijdens het bootstrappen van webforJ worden alle klassen die <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> uitbreiden, gescand. Als meerdere apps worden gevonden, zoekt het systeem naar de <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> annotatie. Als een van de ontdekte klassen is geannoteerd met <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, wordt de eerste die wordt aangetroffen als het instappunt beschouwd.

- Als een klasse is geannoteerd met `@AppEntry`, wordt die klasse geselecteerd als het instappunt.
- Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt er een uitzondering opgegooid, waarin alle ontdekte klassen worden vermeld.
- Als geen enkele klasse is geannoteerd en er slechts één subklasse van `App` wordt gevonden, wordt die klasse geselecteerd als het instappunt.
- Als er geen klasse is geannoteerd en meerdere subklassen van `App` worden gevonden, wordt er een uitzondering opgegooid, waarin elke subklasse wordt beschreven.

:::tip Foutafhandeling
Voor meer informatie over hoe fouten worden afgehandeld in webforJ, zie [dit artikel](../advanced/error-handling).
:::

## Hoofd view klasse: `HomeView.java` {#main-view-class-homeviewjava}

De `HomeView` klasse definieert een eenvoudige viewcomponent die fungeert als de homepage voor de app. Het toont een veld en een knop om de naam van de gebruiker te begroeten.

### Klasse declaratie en annotaties {#class-declaration-and-annotations}

`HomeView` breidt `Composite<FlexLayout>` uit, waardoor het kan functioneren als een herbruikbare component samengesteld uit een [`FlexLayout`](../components/flex-layout) component. De [`@Route("/")`](../routing/overview) maakt dit de rootroute van de app.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Wat is uw naam?");
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
private TextField hello = new TextField("Wat is uw naam?");
private Button btn = new Button("Zeg Hallo");
```

- `self`: Het hoofdlay-outcomponent dat gebruikmaakt van [`FlexLayout`](../components/flex-layout), geconfigureerd als een container voor de elementen. Dit element maakt gebruik van de methode `getBoundComponent()` om de belangrijkste `FlexLayout` die de klasse bevat op te slaan.
- `hello`: Een [`TextField`](../components/fields/textfield) gelabeld `Wat is uw naam?` voor gebruikers om hun naam in te voeren.
- `btn`: Een primair gestileerde [`Button`](../components/button) gelabeld `Zeg Hallo`.

### Lay-outconfiguratie {#layout-configuration}

De lay-out `(self)` is geconfigureerd met een paar belangrijke stijl eigenschappen:

- `FlexDirection.COLUMN` stapelt de elementen verticaal.
- `setMaxWidth(300)` beperkt de breedte tot 300 pixels voor een compacte lay-out.
- `setStyle("margin", "1em auto")` centreert de lay-out met een marge eromheen.

### Componenten aan de lay-out toevoegen {#adding-components-to-the-layout}
Ten slotte worden het hello-tekstveld en de btn-knop toegevoegd aan de [`FlexLayout`](../components/flex-layout) container door `self.add(hello, btn)` aan te roepen. Deze indeling definieert de structuur van de weergave en maakt het formulier zowel interactief als visueel gecentreerd.

## Styling van de app {#styling-the-app}

Het `styles.css` bestand biedt aangepaste styling voor uw webforJ-app. Dit CSS-bestand wordt in de Application-klasse verwezen met behulp van de [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotatie, waardoor de app stijlen kan toepassen op de componenten binnen de app.

Dit bestand bevindt zich in de `resources/static` directory van het project en kan worden verwezen met de URL van de webserver `ws://app.css`.
