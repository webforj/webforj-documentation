---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

De `BrowserHistory` klasse in webforJ biedt een hoog-niveau API voor interactie met de geschiedenis van de browser. De browsergeschiedenis stelt webapplicaties in staat om de navigatie van de gebruiker binnen de app bij te houden. Door gebruik te maken van browsergeschiedenis kunnen ontwikkelaars functies zoals terug- en vooruitnavigatie, staatbewaring en dynamisch URL-beheer inschakelen zonder volledige pagina-herlaadbeurten te vereisen.

## Navigeren door de geschiedenis {#navigating-through-history}

Het beheren van de geschiedenis van de browser is een kernfunctie voor de meeste web-apps. De `BrowserHistory` API stelt ontwikkelaars in staat om te controleren hoe gebruikers door de pagina's en staten van hun applicaties navigeren, wat de standaard browsergedrag nabootst of aanpast.

### Een geschiedenis instantie initialiseren of ophalen {#initializing-or-retrieving-a-history-instance}

Om de `BrowserHistory` API te gebruiken, heb je twee hoofdopties voor het verkrijgen van een geschiedenis instantie:

1) **Een nieuw geschiedenisobject maken**: Als je onafhankelijk van een routeringscontext werkt, kun je rechtstreeks een nieuwe instantie van de `BrowserHistory` klasse maken.

```java
BrowserHistory history = new BrowserHistory();
```
Deze benadering is geschikt voor scenario's waarin je geschiedenis expliciet buiten een routeringsframework moet beheren.

2) **De geschiedenis ophalen van de `Router`**: Als je app gebruik maakt van de routeringsoplossing van webforJ, creëert de `Router` component een gedeelde `BrowserHistory` instantie. Je kunt deze instantie rechtstreeks vanaf de router benaderen, wat zorgt voor een consistente geschiedenisbeheersbenadering in je app.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Deze methode wordt aanbevolen wanneer je app op routering vertrouwt, omdat deze consistentie in geschiedenisbeheer over alle weergaven en navigatieacties behoudt.

### Geschiedenis beheren {#managing-history}
De volgende methoden kunnen worden gebruikt voor geschiedenis navigatie in een webforJ app:

- `back()`: Beweegt de browsergeschiedenis één stap terug, wat de gebruiker simuleert die op de terugknop van hun browser drukt. Als er geen verdere items in de geschiedenisstack zijn, blijft het op de huidige pagina.

  ```java
  history.back();
  ```

- `forward()`: Beweegt de browsergeschiedenis één stap vooruit, wat de gebruiker simuleert die op de vooruitknop van hun browser drukt. Dit werkt alleen als er een item vooruit in de geschiedenisstack staat.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigeert naar een specifiek punt in de geschiedenisstack op basis van een index. Een positief getal beweegt vooruit, een negatief getal beweegt achteruit en nul herlaadt de huidige pagina. Deze methode biedt meer gedetailleerde controle vergeleken met `back()` en `forward()`.

  ```java
  history.go(-2); // Beweegt twee items terug in de geschiedenisstack
  ```

- `size()`: Haalt het totale aantal items in de sessiegeschiedenisstack op, inclusief de momenteel geladen pagina. Dit kan nuttig zijn om de navigatiepad van de gebruiker te begrijpen of om aangepaste navigatiecontrols te implementeren.

  ```java
  int historySize = history.size();
  System.out.println("Geschiedenis Lengte: " + historySize);
  ```

- `getLocation()`: Geeft het huidige URL-pad terug ten opzichte van de oorsprong van de app. Deze methode helpt ontwikkelaars om het huidige pad op te halen, wat nuttig is voor het beheren van URL-gebaseerde routering in single-page applicaties.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
  ```

Begrijpen hoe je efficiënt kunt navigeren is de essentie van het bouwen van dynamische applicaties. Zodra je de basisprincipes van navigatie hebt, is het essentieel om te weten hoe je de URL's die verbonden zijn met deze navigatie-evenementen kunt ophalen en bijwerken.

## Toegang krijgen tot en bijwerken van URL {#accessing-and-updating-url}

Een kernaspect van navigeren en beheren van browsergeschiedenis is de mogelijkheid om het huidige URL-pad efficiënt te bereiken en bij te werken. Dit is essentieel in moderne web-apps, waar URL-wijzigingen corresponderen met verschillende weergaven of staten binnen de app. De `BrowserHistory` API biedt een eenvoudige manier om het huidige pad ten opzichte van de root van de app op te halen en te manipuleren.

:::tip webforJ `Router`
Zie het [`Router` artikel](../routing/overview) voor meer informatie over uitgebreide URL- en routebeheer
:::

`getLocation()` haalt het huidige URL-pad op ten opzichte van de oorsprong van de app. De `getLocation()` methode geeft een `Optional<Location>` terug, waarmee je het padgedeelte van de URL kunt verkrijgen zonder het domein.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Huidig Pad: " + loc.getFullURI()));
```

## Staat beheren {#managing-state}

`BrowserHistory` laat je aangepaste statusinformatie opslaan en beheren met behulp van de methoden `pushState()` en `replaceState()`. Door gebruik te maken van statusbeheermethoden kun je controleren welke informatie wordt opgeslagen als onderdeel van de geschiedenisitem, wat helpt bij het handhaven van een consistente gebruikerservaring wanneer je heen en weer navigeert binnen je app. De volgende methoden kunnen worden gebruikt om de staat in je webforJ app te beheren.

- `pushState(Object state, Location location)`: Voegt een nieuw item toe aan de geschiedenisstack. Accepteert een statusobject en een `Location` object dat de nieuwe URL vertegenwoordigt.

```java
Location location = new Location("/nieuwe-pagina");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Vervangt het huidige geschiedenisitem. Deze methode creëert geen nieuw item in de stack zoals de bovenstaande methode doet.

```java
Location location = new Location("/geüpdatete-pagina");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Haalt het statusobject op dat is gekoppeld aan het huidige geschiedenisitem. Deze methode retourneert een Optional die het statusobject bevat, dat wordt gedeserialiseerd in de gespecificeerde klasse.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Huidige Pagina: " + state.getViewName()));
```

### Luisteren naar statuswijzigingen {#listening-for-state-changes}
De `BrowserHistory` klasse biedt de mogelijkheid om evenementlisteners te registreren die reageren op wijzigingen in de status van de geschiedenis.

De `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registreert een listener die wordt geactiveerd wanneer de status verandert, zoals wanneer de gebruiker op de terug- of vooruitknoppen van de browser klikt. Deze methode zet een listener op voor het `popstate`-evenement van de browser, waardoor je app kan reageren op gebruikersacties of programmatiche statuswijzigingen.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("Geschiedenisstatus gewijzigd naar: " + event.getLocation().getFullURI());
});
```

Effectief statebeheer stelt je in staat om apps te creëren die dynamisch reageren op gebruikersacties. Gebruikers kunnen door je app navigeren zonder context te verliezen, wat zorgt voor een soepelere en meer intuïtieve ervaring. Bovendien stelt het opslaan van status geavanceerde functies in staat, zoals het herstellen van weergaveposities, het handhaven van filter- of sorteInstellingen, en het ondersteunen van diepere koppelingen - allemaal bijdragend aan een meer betrokken en betrouwbare app.
