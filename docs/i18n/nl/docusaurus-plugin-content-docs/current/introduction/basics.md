---
title: App Basics
sidebar_position: 3
_i18n_hash: ad73702df52f27ebff7e226bb75e3a6a
---
Zodra webforJ en de bijbehorende afhankelijkheden zijn ingesteld in uw project, bent u klaar om de app-structuur te creëren. Dit artikel loopt door de belangrijkste elementen van een basis webforJ-app, met specifieke focus op de `Application`- en `HomeView`-klassen, die de fundamentele klassen zijn in het `webforj-archetype-hello-world` starterproject.

## Hoofdapp-klasse: `Application.java` {#main-app-class-applicationjava}

De `Application`-klasse fungeert als het ingangspunt voor uw webforJ-app, waarbij essentiële configuraties en routes worden ingesteld. Om te beginnen, let op de declaratie en annotaties van de klasse.

Deze klasse breidt de kern-`App`-klasse van webforJ uit, waardoor het herkenbaar is als een webforJ-app. Verschillende annotaties configureren het thema, de titel en de routing van de app.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Specificeert dat webforJ het `com.samples.views`-pakket moet scannen op routecomponenten.
- `@AppTitle`: Definieert de titel die op het browsertabblad van de app wordt weergegeven.
- `@StyleSheet`: Verbindt een extern CSS-bestand, `app.css`, waardoor aangepaste opmaak voor de app mogelijk is.

De `Application`-klasse bevat geen aanvullende methoden omdat de configuraties zijn ingesteld via annotaties en webforJ de app-initialisatie afhandelt.

Met `Application.java` ingesteld is de app nu geconfigureerd met een titel en routes die naar het weergavepakket wijzen. Vervolgens biedt een overzicht van de `HomeView`-klasse inzicht in wat er wordt weergegeven wanneer de app wordt uitgevoerd.

### Ontdekken van een `App` {#discovering-an-app}

Een enkele <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> limiet wordt gehandhaafd in webforJ, wat alle foutafhandelingsverantwoordelijkheden naar de Java-zijde verschuift en ontwikkelaars volledige controle geeft over het beheer van fouten.

Tijdens het opstartproces van webforJ worden alle klassen die de <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> uitbreiden gescand. Als er meerdere apps worden gevonden, zoekt het systeem naar de <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> annotatie. Als een van de ontdekte klassen is geannoteerd met <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>@AppEntry</JavadocLink>, wordt de eerste die tegenkomt als ingangspunt beschouwd.

- Als een klasse is geannoteerd met `@AppEntry`, wordt die klasse geselecteerd als het ingangspunt.
- Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt een uitzondering opgegooid, waarin alle ontdekte klassen worden vermeld.
- Als er geen klasse is geannoteerd en er slechts één subklasse van `App` is gevonden, wordt die klasse geselecteerd als het ingangspunt.
- Als er geen klasse is geannoteerd en er meerdere subklassen van `App` zijn gevonden, wordt een uitzondering opgegooid waarin elke subklasse wordt gedetailleerd.

:::tip Foutafhandeling
Voor meer informatie over hoe fouten worden behandeld in webforJ, zie [dit artikel](../advanced/error-handling).
:::

## Hoofdweergaveklasse: `HomeView.java` {#main-view-class-homeviewjava}

De `HomeView`-klasse definieert een eenvoudige weergavecomponent die als de startpagina van de app dient. Het toont een veld en een knop om de naam van de gebruiker te begroeten.

### Klasse-declaratie en annotaties {#class-declaration-and-annotations}

`HomeView` breidt `Composite<FlexLayout>` uit, wat het mogelijk maakt om op te treden als een herbruikbare component die uit een [`FlexLayout`](../components/flex-layout) component bestaat. De [`@Route("/")`](../routing/overview) maakt dit de root-route van de app.

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

- `self`: Het belangrijkste lay-outcomponent dat gebruikmaakt van [`FlexLayout`](../components/flex-layout), geconfigureerd als een container voor de elementen. Dit element maakt gebruik van de `getBoundComponent()`-methode om de hoofd `FlexLayout` die de klasse bevat op te slaan.
- `hello`: Een [`TextField`](../components/fields/textfield) gelabeld `Wat is uw naam?` voor gebruikers om hun naam in te voeren.
- `btn`: Een primair gestyleerde [`Button`](../components/button) gelabeld `Zeg Hallo`.

### Lay-outconfiguratie {#layout-configuration}

De lay-out `(self)` is geconfigureerd met een paar belangrijke stijlkenmerken:

- `FlexDirection.COLUMN` stapelt de elementen verticaal.
- `setMaxWidth(300)` beperkt de breedte tot 300 pixels voor een compacte lay-out.
- `setStyle("margin", "1em auto")` centreert de lay-out met een marge eromheen.

### Componenten aan de lay-out toevoegen {#adding-components-to-the-layout}
Ten slotte worden het hello-tekstveld en de btn-knop toegevoegd aan de [`FlexLayout`](../components/flex-layout) container door `self.add(hello, btn)` aan te roepen. Deze indeling definieert de structuur van de weergave, waardoor het formulier zowel interactief als visueel gecentreerd is.

## De app stylen {#styling-the-app}

Het `styles.css`-bestand biedt aangepaste styling voor uw webforJ-app. Dit CSS-bestand wordt in de Application-klasse verwezen met de [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotatie, waardoor de app stijlen op componenten binnen de app kan toepassen.

Dit bestand bevindt zich in de `resources/static`-directory van het project en kan worden verwezen naar de webserver-URL `ws://app.css`.
