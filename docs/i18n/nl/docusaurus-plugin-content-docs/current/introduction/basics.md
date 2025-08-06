---
title: App Basics
sidebar_position: 3
_i18n_hash: eb456b4bb94bf617f33f8aa8425ad97f
---
Zodra webforJ en zijn afhankelijkheden zijn ingesteld in uw project, bent u klaar om de app-structuur te creëren. Dit artikel doorloopt de belangrijkste elementen van een basale webforJ-app, met specifieke focus op de `Application` en `HomeView` klassen, die de fundamentele klassen zijn in het `webforj-archetype-hello-world` starterproject.

## Hoofd applicatieklasse: `Application.java` {#main-app-class-applicationjava}

De `Application` klasse dient als het toegangspunt voor uw webforJ-app, en stelt essentiële configuraties en routes in. Let eerst op de declaratie en annotaties van de klasse.

Deze klasse breidt de kernklasse `App` van webforJ uit, waardoor het herkenbaar is als een webforJ-app. Diverse annotaties configureren het thema, de titel en routing van de app.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Geeft aan dat webforJ de `com.samples.views` package moet scannen naar route-componenten.
- `@AppTitle`: Definieert de titel die op het browsertabblad van de app wordt weergegeven.
- `@StyleSheet`: Linkt een extern CSS-bestand, `app.css`, zodat aangepaste stijlen voor de app kunnen worden toegepast.

De `Application` klasse bevat geen extra methoden omdat de configuraties via annotaties zijn ingesteld en webforJ verantwoordelijk is voor de initialisatie van de app.

Met `Application.java` ingesteld, is de app nu geconfigureerd met een titel en routes die naar het views-package wijzen. Vervolgens biedt een overzicht van de `HomeView` klasse inzicht in wat er wordt weergegeven wanneer de app wordt uitgevoerd.

### Ontdekken van een `App` {#discovering-an-app}

Een enkele <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> limiet wordt gehandhaafd in webforJ, wat alle foutafhandelingsverantwoordelijkheden naar de Java-kant verschuift en ontwikkelaars volledige controle over het foutbeheer geeft.

Tijdens het opstartproces van webforJ worden alle klassen die de <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> uitbreiden gescand. Als er meerdere apps worden gevonden, zoekt het systeem naar de <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> annotatie. Als een van de ontdekte klassen is geannoticeerd met <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>, wordt de eerste die wordt tegengekomen als toegangspunt beschouwd.

- Als een klasse is geannoticeerd met `@AppEntry`, wordt die klasse geselecteerd als toegangspunt.
- Als meerdere klassen zijn geannoticeerd met `@AppEntry`, wordt er een uitzondering opgegooid, waarbij alle ontdekte klassen worden vermeld.
- Als geen enkele klasse is geannoticeerd en slechts één subklasse van `App` wordt gevonden, wordt die klasse geselecteerd als toegangspunt.
- Als geen enkele klasse is geannotceerd en meerdere subklassen van `App` worden gevonden, wordt er een uitzondering opgegooid, waarin elke subklasse wordt beschreven.

:::tip Foutafhandeling
Voor meer informatie over hoe fouten worden afgehandeld in webforJ, zie [dit artikel](../advanced/error-handling).
:::

## Hoofd weergaveklasse: `HomeView.java` {#main-view-class-homeviewjava}

De `HomeView` klasse definieert een eenvoudige weergavecomponent die als de homepage voor de app fungeert. Het toont een invoerveld en een knop om de door de gebruiker getypte naam te begroeten.

### Klasse declaratie en annotaties {#class-declaration-and-annotations}

`HomeView` breidt `Composite<FlexLayout>` uit, waardoor het kan functioneren als een herbruikbare component bestaande uit een [`FlexLayout`](../components/flex-layout) component. De [`@Route("/")`](../routing/overview) maakt dit de hoofdroute van de app.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
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
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Wat is uw naam?");
private Button btn = new Button("Zeg Hallo");
```

- `self`: De hoofdlayoutcomponent die gebruik maakt van [`FlexLayout`](../components/flex-layout), geconfigureerd als een container voor de elementen. Dit element maakt gebruik van de `getBoundComponent()` methode om de belangrijkste `FlexLayout` die de klasse bevat op te slaan.
- `hello`: Een [`TextField`](../components/fields/textfield) gelabeld `Wat is uw naam?` zodat gebruikers hun naam kunnen invoeren.
- `btn`: Een primair gestileerde [`Button`](../components/button) gelabeld `Zeg Hallo`.

### Layoutconfiguratie {#layout-configuration}

De lay-out `(self)` is geconfigureerd met een paar belangrijke stijl eigenschappen:

- `FlexDirection.COLUMN` stapelt de elementen verticaal.
- `setMaxWidth(300)` beperkt de breedte tot 300 pixels voor een compacte lay-out.
- `setStyle("margin", "1em auto")` centreert de lay-out met een marge eromheen.

### Componenten toevoegen aan de lay-out {#adding-components-to-the-layout}
Ten slotte worden het hello tekstveld en de btn-knop aan de [`FlexLayout`](../components/flex-layout) container toegevoegd door `self.add(hello, btn)` aan te roepen. Deze regeling definieert de structuur van de weergave, waardoor het formulier zowel interactief als visueel gecentreerd is.

## De app stijlen {#styling-the-app}

Het `styles.css` bestand biedt aangepaste stijlen voor uw webforJ-app. Dit CSS-bestand wordt in de Application-klasse genoemd met behulp van de [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotatie, die de app toestaat stijlen op componenten binnen de app toe te passen.

Dit bestand bevindt zich in de `resources/static` directory van het project en kan worden genoemd met de URL `ws://app.css`.
