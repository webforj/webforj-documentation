---
title: Browser History
sidebar_position: 30
_i18n_hash: 918006c1e505baa4bbffbfb32eb3d9d7
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

De `BrowserHistory` klasse in webforJ biedt een high-level API om interactie te hebben met de geschiedenis van de browser. Browsergeschiedenis stelt webapplicaties in staat om de navigatie van de gebruiker binnen de app bij te houden. Door gebruik te maken van browsergeschiedenis kunnen ontwikkelaars functies mogelijk maken zoals terug- en vooruitnavigatie, staatbewaring en dynamisch URL-beheer zonder volledige pagina-herlaadacties.

## Navigeren door geschiedenis {#navigating-through-history}

Het beheren van de browsergeschiedenis is een kernfunctie van de meeste webapps. De `BrowserHistory` API stelt ontwikkelaars in staat om te controleren hoe gebruikers door de pagina's en staten van hun applicaties navigeren, waarbij de standaard browserfunctionaliteit wordt nagebootst of aangepast.

### Een geschiedenisinstantie initialiseren of ophalen {#initializing-or-retrieving-a-history-instance}

Om de `BrowserHistory` API te gebruiken, heb je twee hoofdmogelijkheden om een geschiedenisinstantie te verkrijgen:

1) **Een nieuw geschiedenisobject creëren**: Als je onafhankelijk van een routeringscontext werkt, kun je rechtstreeks een nieuwe instantie van de `BrowserHistory` klasse creëren.

```java
BrowserHistory geschiedenis = new BrowserHistory();
```
Deze aanpak is geschikt voor scenario's waarin je geschiedenis expliciet moet beheren buiten een routeringsframework.

2) **De geschiedenis ophalen van de `Router`**: Als je app gebruik maakt van de [routeringsoplossing](../routing/overview) van webforJ, creëert en beheert de `Router` component een gedeelde `BrowserHistory` instantie. Je kunt deze instantie rechtstreeks vanuit de router benaderen voor een consistente aanpak van het geschiedenismangement in je app.

```java
BrowserHistory geschiedenis = Router.getCurrent().getHistory();
```
Deze methode wordt aanbevolen wanneer je app op routering is gebaseerd, omdat deze consistentie in geschiedenisbeheer over alle weergaven en navigatieacties behoudt.

### Geschiedenis beheren {#managing-history}
De volgende methoden kunnen worden gebruikt voor geschiedenisnavigatie in een webforJ-app:

- `back()`: Beweegt de browsergeschiedenis één stap terug, wat simuleert dat een gebruiker de terugknop in zijn browser indrukt. Als er geen verdere invoer meer in de geschiedenisstack is, blijft deze op de huidige pagina.

  ```java
  geschiedenis.back();
  ```

- `forward()`: Beweegt de browsergeschiedenis één stap vooruit, wat simuleert dat een gebruiker de vooruitknop in zijn browser indrukt. Dit werkt alleen als er een invoer verderop in de geschiedenisstack is.

  ```java
  geschiedenis.forward();
  ```

- `go(int index)`: Navigeert naar een specifiek punt in de geschiedenisstack op basis van een index. Een positief getal beweegt vooruit, een negatief getal beweegt achteruit, en nul herlaadt de huidige pagina. Deze methode biedt meer gedetailleerde controle in vergelijking met `back()` en `forward()`.

  ```java
  geschiedenis.go(-2); // Beweegt terug met twee entries in de geschiedenisstack
  ```

- `size()`: Haalt het totale aantal invoeren in de sessiegeschiedenisstack op, inclusief de momenteel geladen pagina. Dit kan nuttig zijn voor het begrijpen van het navigatiepad van de gebruiker of voor het implementeren van aangepaste navigatiecontroles.

  ```java
  int geschiedenisGrootte = geschiedenis.size();
  System.out.println("Geschiedenis Lengte: " + geschiedenisGrootte);
  ```

- `getLocation()`: Retourneert het huidige URL-pad ten opzichte van de oorsprong van de apps. Deze methode helpt ontwikkelaars het huidige pad op te halen, wat nuttig is voor het beheren van URL-gebaseerde routering in single-page applicaties.

  ```java
  Optional<Location> locatie = geschiedenis.getLocation();
  locatie.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
  ```

Begrijpen hoe je efficiënt kunt navigeren, is de hoeksteen van het bouwen van dynamische applicaties. Zodra je de basisprincipes van navigatie kent, is het essentieel om te weten hoe je toegang kunt krijgen tot en de URL's die verband houden met deze navigatiegebeurtenissen kunt bijwerken.

## Toegang tot en bijwerken van URL {#accessing-and-updating-url}

Een belangrijk aspect van navigeren en het beheren van browsergeschiedenis is om de huidige URL-pad efficiënt te kunnen oproepen en bijwerken. Dit is essentieel in moderne webapps, waar URL-wijzigingen overeenkomen met verschillende weergaven of staten binnen de app. De `BrowserHistory` API biedt een eenvoudige manier om het huidige pad ten opzichte van de root van de app op te halen en te manipuleren.

:::tip webforJ `Router`
Zie het [`Router` artikel](../routing/overview) om meer te leren over uitgebreide URL- en routebeheer
:::

`getLocation()` haalt het huidige URL-pad op ten opzichte van de oorsprong van de app. De `getLocation()` methode retourneert een `Optional<Location>`, waardoor je het padgedeelte van de URL kunt verkrijgen zonder het domein.

```java
Optional<Location> locatie = geschiedenis.getLocation();
locatie.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
```

## Status beheren {#managing-state}

`BrowserHistory` stelt je in staat om aangepaste statusinformatie op te slaan en te beheren met behulp van de methoden `pushState()` en `replaceState()`. Door gebruik te maken van statusbeheermethoden, kun je controleren welke informatie wordt opgeslagen als onderdeel van de geschiedenisinvoer, wat helpt bij het behouden van een consistente gebruikerservaring wanneer je terug- en voortnavigeren binnen je app. De volgende methoden kunnen worden gebruikt om status in je webforJ-app te beheren.

- `pushState(Object state, Location location)`: Voegt een nieuwe invoer toe aan de geschiedenisstack. Accepteert een state-object en een `Location` object dat de nieuwe URL vertegenwoordigt.

```java
Location locatie = new Location("/nieuwe-pagina");
geschiedenis.pushState(mijnStateObject, locatie);
```


- `replaceState(Object state, Location location)`: Vervangt de huidige geschiedenisinvoer. Dit creëert geen nieuwe invoer in de stack zoals de hierboven genoemde methode.

```java
Location locatie = new Location("/geüpdatete-pagina");
geschiedenis.replaceState(mijnStateObject, locatie);
```

- `getState(Class<T> classOfT)`: Haalt het statusobject op dat is gekoppeld aan de huidige geschiedenisinvoer. Deze methode retourneert een Optional die het statusobject bevat, dat wordt gedeserializeerd in de opgegeven klasse.

```java
Optional<MyState> huidigeStatus = geschiedenis.getState(MyState.class);
huidigeStatus.ifPresent(state -> System.out.println("Huidige Pagina: " + state.getViewName()));
```

### Luisteren naar statuswijzigingen {#listening-for-state-changes}
De `BrowserHistory` klasse biedt de mogelijkheid om gebeurtenislisters te registreren die reageren op wijzigingen in de geschiedenisstatus.

De `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registreert een listener die wordt geactiveerd wanneer de status verandert, zoals wanneer de gebruiker op de terug- of vooruitknop van de browser klikt. Deze methode stelt een listener in voor het `popstate` evenement van de browser, waardoor je app kan reageren op gebruikersacties of programmatisch geactiveerde statuswijzigingen.


```java
geschiedenis.addHistoryStateChangeListener(event -> {
  System.out.println("Geschiedenisstatus gewijzigd naar: " + event.getLocation().getFullURI());
});
```

Effectief beheer van status stelt je in staat om apps te creëren die dynamisch reageren op gebruikersacties. Gebruikers kunnen door je app navigeren zonder de context te verliezen, wat zorgt voor een soepelere en intuïtievere ervaring. Bovendien stelt het opslaan van de status geavanceerde functies in staat, zoals het herstellen van kijkposities, het behouden van filter- of sorteersinstellingen en het ondersteunen van diepe links—allemaal factoren die bijdragen aan een boeiendere en betrouwbaardere app.
