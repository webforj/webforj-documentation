---
title: Browser History
sidebar_position: 30
_i18n_hash: e0426f58e099d38fa58fa2b722ec0605
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

De `BrowserHistory` klasse in webforJ biedt een hoog-niveau API om te interageren met de geschiedenis van de browser. De browsergeschiedenis stelt webapplicaties in staat om de navigatie van de gebruiker binnen de app bij te houden. Door gebruik te maken van de browsergeschiedenis kunnen ontwikkelaars functies inschakelen zoals terug- en vooruitnavigatie, staatbehoud en dynamisch URL-beheer zonder volledige paginavernieuwingen.

## Navigeren door de geschiedenis {#navigating-through-history}

Het beheren van de geschiedenis van de browser is een kernfunctie van de meeste webapps. De `BrowserHistory` API stelt ontwikkelaars in staat om te controleren hoe gebruikers door de pagina's en staten van hun applicaties navigeren, waarbij de standaard browsergedrag wordt nagebootst of aangepast.

### Een geschiedenis-instantie initialiseren of ophalen {#initializing-or-retrieving-a-history-instance}

Om de `BrowserHistory` API te gebruiken, heb je twee hoofdmogelijkheden voor het verkrijgen van een geschiedenis-instantie:

1) **Een nieuw geschiedenisobject maken**: Als je onafhankelijk van een routeringscontext werkt, kun je een nieuwe instantie van de `BrowserHistory` klasse direct aanmaken.

```java
BrowserHistory history = new BrowserHistory();
```
Deze aanpak is geschikt voor scenario's waarin je de geschiedenis expliciet moet beheren buiten een routeringsframework.

2) **De geschiedenis ophalen van de `Router`**: Als je app gebruik maakt van de [routeroplossing](../routing/overview) van webforJ, creëert en beheert de `Router` component een gedeelde `BrowserHistory` instantie. Je kunt deze instantie direct vanuit de router benaderen voor een consistente aanpak van geschiedenisbeheer in je app.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Deze methode wordt aanbevolen wanneer je app afhankelijk is van routering, omdat het consistentie in geschiedenisbeheer over alle weergaven en navigatie-acties handhaaft.

### Geschiedenis beheren {#managing-history}
De volgende methoden kunnen worden gebruikt voor geschiedenisnavigatie in een webforJ app:

- `back()`: Beweegt de browsergeschiedenis één stap terug, simuleren dat een gebruiker de terugknop in hun browser indrukt. Als er geen entries meer in de geschiedenisstack staan, blijft hij op de huidige pagina.

  ```java
  history.back();
  ```

- `forward()`: Beweegt de browsergeschiedenis één stap vooruit, simuleren dat een gebruiker de vooruitknop in hun browser indrukt. Dit werkt alleen als er een entry vooruit in de geschiedenisstack staat.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigeert naar een specifiek punt in de geschiedenisstack op basis van een index. Een positief getal beweegt vooruit, een negatief getal beweegt terug, en nul vernieuwt de huidige pagina. Deze methode biedt meer gedetailleerde controle in vergelijking met `back()` en `forward()`.

  ```java
  history.go(-2); // Beweegt terug met twee entries in de geschiedenisstack
  ```

- `size()`: Haalt het totale aantal entries in de sessiegeschiedenisstack op, inclusief de momenteel geladen pagina. Dit kan nuttig zijn voor het begrijpen van het navigatiepad van de gebruiker of voor het implementeren van aangepaste navigatiecontroles.

  ```java
  int historySize = history.size();
  System.out.println("Geschiedenis Lengte: " + historySize);
  ```

- `getLocation()`: Retourneert het huidige URL-pad relatief aan de oorsprong van de app. Deze methode helpt ontwikkelaars om het huidige pad op te halen, wat nuttig is voor het beheren van URL-gebaseerde routering in single-page applicaties.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
  ```

Begrijpen hoe je efficiënt kunt navigeren is de hoeksteen van het bouwen van dynamische applicaties. Zodra je de basisprincipes van navigatie begrijpt, is het essentieel om te weten hoe je de URL's die aan deze navigatie-evenementen zijn gekoppeld, kunt openen en bijwerken.

## Toegang krijgen tot en het bijwerken van de URL {#accessing-and-updating-url}

Een essentieel aspect van het navigeren en beheren van de browsergeschiedenis is de mogelijkheid om het huidige URL-pad efficiënt te openen en bij te werken. Dit is essentieel in moderne webapps, waar URL-wijzigingen overeenkomen met verschillende weergaven of staten binnen de app. De `BrowserHistory` API biedt een eenvoudige manier om het huidige pad op te halen en te manipuleren ten opzichte van de root van de app.

:::tip webforJ `Router`
Zie het [`Router` artikel](../routing/overview) om meer te leren over uitgebreide URL- en routeringsbeheer
:::

`getLocation()` haalt het huidige URL-pad op relatief aan de oorsprong van de app. De `getLocation()` methode retourneert een `Optional<Location>`, waardoor je het padgedeelte van de URL kunt verkrijgen zonder het domein.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
```

## Staat beheren {#managing-state}

`BrowserHistory` stelt je in staat om aangepaste staatinformatie op te slaan en te beheren met behulp van de methoden `pushState()` en `replaceState()`. Door gebruik te maken van staatbeheer methoden, kun je controleren welke informatie als onderdeel van de geschiedenisentry wordt opgeslagen, wat helpt bij het handhaven van een consistente gebruikerservaring wanneer je terug en voort navigeren binnen je app. De volgende methoden kunnen worden gebruikt om staat te beheren in je webforJ app.

- `pushState(Object state, Location location)`: Voegt een nieuwe entry toe aan de geschiedenisstack. Accepteert een staatobject en een `Location` object dat de nieuwe URL vertegenwoordigt.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```


- `replaceState(Object state, Location location)`: Vervangt de huidige geschiedenisentry. Dit creëert geen nieuwe entry in de stack zoals de bovenstaande methode.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Haalt het staatobject op dat aan de huidige geschiedenisentry is gekoppeld. Deze methode retourneert een Optional die het staatobject bevat, dat wordt gedeserialiseerd in de opgegeven klasse.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Huidige Pagina: " + state.getViewName()));
```

### Luisteren naar staatwijzigingen {#listening-for-state-changes}
De `BrowserHistory` klasse biedt de mogelijkheid om gebeurtenislisters te registreren die reageren op wijzigingen in de geschiedenisstaat.

De `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registreert een luisteraar die wordt geactiveerd wanneer de staat verandert, zoals wanneer de gebruiker op de terug- of vooruitknoppen van de browser klikt. Deze methode stelt een luisteraar in voor de `popstate` gebeurtenis van de browser, waardoor je app kan reageren op gebruikersacties of programmatically geactiveerde staatwijzigingen.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Geschiedenisstaat gewijzigd naar: " + event.getLocation().getFullURI());
});
```

Effectief staat beheren stelt je in staat om apps te creëren die dynamisch reageren op gebruikersacties. Gebruikers kunnen door je app navigeren zonder context te verliezen, wat zorgt voor een soepelere en intuïtieve ervaring. Bovendien maakt het opslaan van staat geavanceerde functies mogelijk zoals het herstellen van weergaveposities, het handhaven van filter- of sorteervoordelen, en het ondersteunen van deep linking—wat allemaal bijdraagt aan een meer boeiende en betrouwbare app.
