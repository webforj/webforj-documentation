---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Webopslag <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Webopslag](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) is een fundamenteel concept in webontwikkeling dat websites in staat stelt gegevens aan de clientzijde op te slaan. Dit stelt webapplicaties in staat om de status, voorkeuren en andere informatie lokaal in de browser van de gebruiker op te slaan. Webopslag biedt een manier om gegevens te behouden over pagina-herladingen en browsersessies heen, waardoor de noodzaak voor herhaalde serververzoeken vermindert en offline mogelijkheden worden gecreëerd.

webforJ ondersteunt drie mechanismen voor het opslaan van klantgegevens: [**Cookies**](#cookies), [**Sessieopslag**](#session-storage) en [**Lokale opslag**](#local-storage).

:::tip Webopslag in de ontwikkelaarstools
Je kunt momenteel cookie-, lokale opslag- en sessieopslag sleutel-waardeparen zien in de ontwikkelaarstools van je browser.
:::

## Samenvatting van de verschillen {#summary-of-differences}
| Kenmerk            | Cookies                                      | Sessieopslag                          | Lokale opslag                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistentie**    | Configureerbare vervaldatum                 | Duur van de pagina sessie             | Persistente tot expliciet verwijderd      |
| **Opslaggrootte**   | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) per cookie                             | Ongeveer [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Ongeveer [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Toepassingsgebieden**      | Gebruikersauthenticatie, voorkeuren, tracking   | Tijdelijke gegevens, formuliergegevens                | Persistente instellingen, gebruikersvoorkeurs    |
| **Beveiliging**       | Kwetsbaar voor XSS, kan worden beveiligd met vlaggen | Gewist aan het einde van de sessie, minder risico        | Toegankelijk via JavaScript, potentieel risico|

## Webopslag gebruiken {#using-web-storage}
De <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>, en <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> klassen in webforJ breiden allemaal de abstracte <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> klasse uit. Om het juiste object te verkrijgen, gebruik je de statische methoden `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()`, of `LocalStorage.getCurrent()`. Om sleutel-waardeparen toe te voegen, op te halen en te verwijderen, gebruik je de `add(key, value)`, `get(key)`, en `remove(key)` methoden.

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) zijn kleine stukken gegevens die aan de clientzijde worden opgeslagen en met elke HTTP-aanroep naar de server worden verzonden. Ze worden vaak gebruikt om gebruikerssessies, voorkeuren en authenticatie-informatie te onthouden. Naast de sleutel-waardeparen kunnen cookies ook attributen hebben. Om attributen voor cookies in te stellen, gebruik je `add(key, value, attributes)`.

### Sleutelkenmerken: {#key-features}
- Kunnen gegevens opslaan over verschillende domeinen
- Ondersteunen vervaldatums
- Kleine opslaggrootte, doorgaans beperkt tot 4 KB
- Worden met elke HTTP-aanroep verzonden
- Kunnen attributen hebben

:::info Cookie Vervaldatum
Standaard vervallen cookies in webforJ na 30 dagen. Je kunt dit wijzigen met de `max-age` of `expires` attributen.
:::

### Cookies gebruiken {#using-cookies}

De volgende codefragment toont het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> object.

```java
// Toegang tot cookie-opslag
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Voeg een nieuwe cookie toe of werk een bestaande cookie bij
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Toegang tot een cookie met een gegeven sleutel
String username = cookieStorage.get("username");

// Verwijder een cookie met een gegeven sleutel
cookieStorage.remove("username");
```
:::info Cookie Beveiliging
Bepaalde cookie-attributen, zoals `Secure` en `SameSite=None`, vereisen een veilige context met behulp van HTTPS. Deze attributen zorgen ervoor dat cookies alleen via veilige verbindingen worden verzonden, waardoor ze worden beschermd tegen onderschepping. Voor meer informatie, zie de [MDN documentatie over cookiebeveiliging](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Toepassingsgebieden {#use-cases}
De volgende toepassingsgebieden zijn goed geschikt voor het gebruik van cookies:

- **Gebruikersauthenticatie**: Bewaar sessietokens om gebruikers ingelogd te houden.
- **Voorkeuren**: Sla gebruikersvoorkeuren op, zoals thema-instellingen of taal.
- **Tracking**: Verzamel informatie over gebruikersgedrag voor analyses.


## Sessieopslag {#session-storage}
Sessieopslag slaat gegevens op voor de duur van een pagina sessie. De gegevens zijn alleen toegankelijk binnen dezelfde sessie en worden gewist wanneer de pagina of het tabblad wordt gesloten. De gegevens blijven echter behouden voor herladingen en herstel. Sessieopslag is het beste voor het opslaan van tijdelijke gegevens tijdens een enkele pagina sessie en voor het behouden van de status over verschillende pagina's binnen dezelfde sessie.

### Sleutelkenmerken {#key-features-1}
- Gegevens worden niet met elke HTTP-aanroep verzonden
- Grotere opslaggrootte dan cookies
- Gegevens worden gewist wanneer de pagina of het tabblad wordt gesloten
- Gegevens worden niet gedeeld tussen tabbladen

### Sessieopslag gebruiken in webforJ {#using-session-storage-in-webforj}

De volgende codefragment toont het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> object.

```java
// Toegang tot sessieopslag
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Voeg een nieuwe of werk een bestaande sessieopslag eenheid bij
sessionStorage.add("currentPage", "3");

// Toegang tot een sessieopslag eenheid met een gegeven sleutel
String currentPage = sessionStorage.get("currentPage");

// Verwijder een sessieopslag eenheid met een gegeven sleutel
sessionStorage.remove("currentPage");
```

### Toepassingsgebieden {#use-cases-1}
De volgende toepassingsgebieden zijn goed geschikt voor het gebruik van sessieopslag:

- **Tijdelijke Gegevensopslag**: Sla gegevens op die alleen hoeven te blijven bestaan terwijl de gebruiker zich op een bepaalde pagina of sessie bevindt.
- **Formuliergegevens**: Tijdelijk formuliergegevens opslaan voor gebruik binnen de sessie.

## Lokale opslag {#local-storage}
Lokale opslag slaat gegevens op zonder vervaldatum. Het blijft bestaan, zelfs nadat de browser is gesloten, en kan worden benaderd wanneer de gebruiker de website opnieuw bezoekt. Lokale opslag is het beste voor het opslaan van gebruikersvoorkeuren of instellingen, het cachen van gegevens om de prestaties te verbeteren en het opslaan van de applicatiestatus over sessies.

### Sleutelkenmerken {#key-features-2}

- Gegevens blijven bestaan over sessies
- Gegevens worden niet met elke HTTP-aanroep verzonden.
- Grotere opslaggrootte dan cookies
- Niet geschikt voor gevoelige gegevens
- Je moet zelf gegevens beheren, aangezien de browser deze nooit automatisch verwijdert

### Lokale opslag gebruiken in webforJ {#using-local-storage-in-webforj}

De volgende codefragment toont het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> object.

```java
// Toegang tot lokale opslag
LocalStorage localStorage = LocalStorage.getCurrent();

// Voeg een nieuwe of werk een bestaande lokale opslag eenheid bij
localStorage.add("theme", "dark");

// Toegang tot een lokale opslag eenheid met een gegeven sleutel
String theme = localStorage.get("theme");

// Verwijder een lokale opslag eenheid met een gegeven sleutel
localStorage.remove("theme");
```

### Toepassingsgebieden {#use-cases-2}
De volgende toepassingsgebieden zijn goed geschikt voor het gebruik van lokale opslag:

- **Persistente Gegevens**: Sla gegevens op die beschikbaar moeten zijn over meerdere sessies.
- **Voorkeuren**: Bewaar gebruikersinstellingen en voorkeuren die in de tijd blijven bestaan.
