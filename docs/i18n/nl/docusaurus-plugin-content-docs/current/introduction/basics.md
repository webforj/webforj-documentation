---
title: App Basics
sidebar_position: 3
_i18n_hash: e4eae914f0cbd5c9e5eacb6e681570a9
---
Zodra webforJ en zijn afhankelijkheden zijn ingesteld in uw project, bent u klaar om de app-structuur te creëren. Dit artikel bespreekt de sleutelcomponenten van een basis webforJ-app, met specifieke focus op de `Application` en `HomeView` klassen, die de fundamentele klassen zijn in het `webforj-archetype-hello-world` starterproject.

## Hoofd app-klasse: `Application.java` {#main-app-class-applicationjava}

De `Application` klasse dient als het toegangspunt voor uw webforJ-app, waarbij essentiële configuraties en routes worden ingesteld. Let op de declaratie en annotaties van de klasse.

Deze klasse breidt de kern `App` klasse van webforJ uit, waardoor deze herkenbaar is als een webforJ-app. Diverse annotaties configureren het thema, de titel en de routes van de app.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify`: Geeft aan dat webforJ de `com.samples.views` package moet scannen op routecomponenten.
- `@AppTitle`: Definieert de titel die op het browsertabblad van de app wordt weergegeven.
- `@StyleSheet`: Koppelt een extern CSS-bestand, `app.css`, zodat aangepaste opmaak voor de app mogelijk is.

De `Application` klasse bevat geen aanvullende methoden omdat de configuraties zijn ingesteld via annotaties, en webforJ de initialisatie van de app afhandelt.

Met `Application.java` ingesteld, is de app nu geconfigureerd met een titel en routes die naar de view's package wijzen. Vervolgens geeft een overzicht van de `HomeView` klasse inzicht in wat er wordt weergegeven wanneer de app wordt uitgevoerd.

### Een `App` ontdekken {#discovering-an-app}

Een enkele <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> limiet wordt gehandhaafd in webforJ, die alle foutafhandelingsverantwoordelijkheden naar de Java-kant verschuift en ontwikkelaars volledige controle geeft over het foutenbeheer.

Tijdens het opstartproces van webforJ worden alle klassen die <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> uitbreiden gescand. Als er meerdere apps worden gevonden, zoekt het systeem naar de <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink> annotatie. Als een van de ontdekte klassen is geannoteerd met <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, wordt de eerste die wordt aangetroffen als het toegangspunt beschouwd.

- Als een klasse is geannoteerd met `@AppEntry`, wordt die klasse geselecteerd als het toegangspunt.
- Als meerdere klassen zijn geannoteerd met `@AppEntry`, wordt een uitzondering gegenereerd die alle ontdekte klassen opsomt.
- Als geen enkele klasse is geannoteerd en er slechts één subklasse van `App` wordt gevonden, wordt die klasse geselecteerd als het toegangspunt.
- Als geen enkele klasse is geannoteerd en er meerdere subklassen van `App` worden gevonden, wordt een uitzondering gegenereerd die elke subklasse beschrijft.

:::tip Foutafhandeling
Voor meer informatie over hoe fouten worden afgehandeld in webforJ, zie [dit artikel](../advanced/error-handling).
:::

## Hoofd view-klasse: `HomeView.java` {#main-view-class-homeviewjava}

De `HomeView` klasse definieert een eenvoudige viewcomponent die dient als de homepage voor de app. Het toont een veld en een knop om de naam van de gebruiker te begroeten.

### Klassedeklaring en annotaties {#class-declaration-and-annotations}

`HomeView` breidt `Composite<FlexLayout>` uit, waardoor het fungeert als een herbruikbaar component dat is samengesteld uit een [`FlexLayout`](../components/flex-layout) component. De [`@Route("/")`](../routing/overview) maakt dit de rootroute van de app.

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

- `self`: Het hoofd lay-outcomponent dat gebruikmaakt van [`FlexLayout`](../components/flex-layout) en is geconfigureerd als een container voor de elementen. Dit element gebruikt de `getBoundComponent()` methode om de belangrijkste `FlexLayout` die de klasse bevat op te slaan.
- `hello`: Een [`TextField`](../components/fields/textfield) met het label `Wat is uw naam?` voor gebruikers om hun naam in te voeren.
- `btn`: Een primair gestileerde [`Button`](../components/button) met het label `Zeg Hallo`.

### Lay-outconfiguratie {#layout-configuration}

De lay-out `(self)` is geconfigureerd met enkele belangrijke stijlproperties:

- `FlexDirection.COLUMN` stapelt de elementen verticaal.
- `setMaxWidth(300)` beperkt de breedte tot 300 pixels voor een compacte lay-out.
- `setStyle("margin", "1em auto")` centreert de lay-out met een marge eromheen.

### Componenten aan de lay-out toevoegen {#adding-components-to-the-layout}
Tot slot worden het hello-tekstgebied en de btn-knop toegevoegd aan de [`FlexLayout`](../components/flex-layout) container door `self.add(hello, btn)` aan te roepen. Deze indeling definieert de structuur van de weergave, waardoor het formulier zowel interactief als visueel gecentreerd is.

## De app stylen {#styling-the-app}

Het `styles.css` bestand biedt aangepaste opmaak voor uw webforJ-app. Dit CSS-bestand wordt in de Application klasse verwezen met de [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files) annotatie, die de app in staat stelt stijlen toe te passen op componenten binnen de app.

Dit bestand bevindt zich in de `resources/static` directory van het project en kan worden verwezen met de webserver-URL `ws://app.css`.
