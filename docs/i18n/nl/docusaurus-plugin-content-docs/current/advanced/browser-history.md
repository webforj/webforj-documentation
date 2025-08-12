---
title: Browser History
sidebar_position: 20
_i18n_hash: 9b05a2e65e60a737d341a6bc37e9546f
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

De `BrowserHistory`-klasse in webforJ biedt een high-level API om te interageren met de geschiedenis van de browser. De geschiedenis van de browser stelt webtoepassingen in staat om de navigatie van de gebruiker binnen de app bij te houden. Door gebruik te maken van de geschiedenis van de browser, kunnen ontwikkelaars functies inschakelen zoals terug- en vooruitnavigatie, het behouden van de staat en dynamisch URL-beheer zonder volledige pagina-herladen.

## Navigeren door de geschiedenis {#navigating-through-history}

Het beheren van de geschiedenis van de browser is een kernfeature voor de meeste webapps. De `BrowserHistory`-API stelt ontwikkelaars in staat om te bepalen hoe gebruikers door de pagina's en toestanden van hun toepassingen navigeren, waarbij de standaard browsergedrag wordt nagebootst of aangepast.

### Een geschiedenis-instantie initialiseren of ophalen {#initializing-or-retrieving-a-history-instance}

Om de `BrowserHistory`-API te gebruiken, heb je twee hoofdopties voor het verkrijgen van een geschiedenis-instantie:

1) **Een nieuw geschiedenisobject aanmaken**: Als je onafhankelijk werkt van een routeringsscontext, kun je een nieuwe instantie van de `BrowserHistory`-klasse rechtstreeks aanmaken.

```java
BrowserHistory history = new BrowserHistory();
```
Deze aanpak is geschikt voor scenario's waarin je de geschiedenis expliciet buiten een routeringsraamwerk moet beheren.

2) **De geschiedenis ophalen van de `Router`**: Als je app gebruik maakt van de routeroplossing van webforJ, creëert en beheert de `Router`-component een gedeelde instantie van `BrowserHistory`. Je kunt deze instantie rechtstreeks vanuit de router benaderen, wat zorgt voor een consistente aanpak van het geschiedenisbeheer in je app.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Deze methode wordt aanbevolen wanneer je app afhankelijk is van routering, omdat het de consistentie in het geschiedenisbeheer over alle weergaven en navigatieacties behoudt.

### Geschiedenis beheren {#managing-history}
De volgende methoden kunnen worden gebruikt voor geschiedenisnavigatie in een webforJ-app:

- `back()`: Beweegt de browsergeschiedenis een stap terug, alsof een gebruiker de terugknop in de browser indrukt. Als er geen meer vermeldingen in de geschiedenisstack zijn, blijft het op de huidige pagina.

  ```java
  history.back();
  ```

- `forward()`: Beweegt de browsergeschiedenis een stap vooruit, alsof een gebruiker de vooruitknop in de browser indrukt. Dit werkt alleen als er een vermelding vooruit in de geschiedenisstack is.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigeert naar een specifiek punt in de geschiedenisstack op basis van een index. Een positief getal gaat vooruit, een negatief getal gaat achteruit, en nul herlaadt de huidige pagina. Deze methode biedt meer gedetailleerde controle in vergelijking met `back()` en `forward()`.

  ```java
  history.go(-2); // Beweegt twee vermeldingen terug in de geschiedenisstack
  ```

- `size()`: Haalt het totale aantal vermeldingen in de sessiegeschiedenisstack op, inclusief de momenteel geladen pagina. Dit kan nuttig zijn voor het begrijpen van het navigatiepad van de gebruiker of voor het implementeren van aangepaste navigatiecontroles.

  ```java
  int historySize = history.size();
  System.out.println("Geschiedenis Lengte: " + historySize);
  ```

- `getLocation()`: Retourneert het huidige URL-pad relatief ten opzichte van de oorsprong van de app. Deze methode helpt ontwikkelaars het huidige pad op te halen, wat nuttig is voor het beheren van URL-gebaseerde routering in single-page toepassingen.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
  ```

Begrijpen hoe je efficiënt kunt navigeren is de hoeksteen van het bouwen van dynamische toepassingen. Zodra je de basisprincipes van navigatie hebt, is het essentieel om te weten hoe je de URL's die aan deze navigatiegebeurtenissen zijn gekoppeld, kunt openen en bijwerken.

## URL openen en bijwerken {#accessing-and-updating-url}

Een core aspect van navigeren en het beheren van de geschiedenis van de browser is in staat zijn om het huidige URL-pad efficiënt te openen en bij te werken. Dit is essentieel in moderne webapps, waar URL-wijzigingen overeenkomen met verschillende weergaven of toestanden binnen de app. De `BrowserHistory`-API biedt een eenvoudige manier om het huidige pad op te halen en te manipuleren ten opzichte van de root van de app.

:::tip webforJ `Router`
Zie het [`Router`-artikel](../routing/overview) om meer te leren over uitgebreide URL- en routebeheer
:::

`getLocation()` haalt het huidige URL-pad op relatief ten opzichte van de oorsprong van de app. De `getLocation()`-methode retourneert een `Optional<Location>`, waarmee je het padgedeelte van de URL kunt verkrijgen zonder het domein.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
```

## Staat beheren {#managing-state}

`BrowserHistory` stelt je in staat om aangepaste statusinformatie op te slaan en te beheren met behulp van de methoden `pushState()` en `replaceState()`. Door statusbeheermethoden te gebruiken, kun je bepalen welke informatie als onderdeel van de geschiedenisvermelding wordt opgeslagen, wat helpt bij het behouden van een consistente gebruikerservaring bij het navigeren heen en weer binnen je app. De volgende methoden kunnen worden gebruikt om de staat in je webforJ-app te beheren.

- `pushState(Object state, Location location)`: Voegt een nieuwe vermelding toe aan de geschiedenisstack. Accepteert een statusobject en een `Location`-object dat de nieuwe URL vertegenwoordigt.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Vervangt de huidige geschiedenisvermelding. Dit creëert geen nieuwe vermelding in de stack zoals de bovenstaande methode.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Haalt het statusobject op dat is gekoppeld aan de huidige geschiedenisvermelding. Deze methode retourneert een Optional met het statusobject, dat wordt gedeserializeerd in de opgegeven klasse.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Huidige Pagina: " + state.getViewName()));
```

### Luisteren naar statuswijzigingen {#listening-for-state-changes}
De `BrowserHistory`-klasse biedt de mogelijkheid om evenementlisteners te registreren die reageren op wijzigingen in de geschiedenisstatus.

De `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registreert een listener die wordt geactiveerd wanneer de status verandert, bijvoorbeeld wanneer de gebruiker op de terug- of vooruitknoppen van de browser klikt. Deze methode stelt een listener in voor het `popstate`-evenement van de browser, waardoor je app kan reageren op gebruikersacties of programmatisch geactiveerde statuswijzigingen.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Geschiedenisstatus gewijzigd naar: " + event.getLocation().getFullURI());
});
```

Effectief beheren van de status stelt je in staat om apps te creëren die dynamisch reageren op gebruikersacties. Gebruikers kunnen door je app navigeren zonder de context te verliezen, wat zorgt voor een soepelere en intuïtievere ervaring. Bovendien stelt het opslaan van status geavanceerde functies in staat, zoals het herstellen van weergaveposities, het behouden van filter- of sorteervoorkeuren en het ondersteunen van deep linking—al deze dragen bij aan een boeiendere en betrouwbaardere app.
