---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Webopslag <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Webopslag](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) is een fundamenteel concept in webontwikkeling dat websites in staat stelt om gegevens aan de clientzijde op te slaan. Dit stelt webapplicaties in staat om status, voorkeuren en andere informatie lokaal op de browser van de gebruiker op te slaan. Webopslag biedt een manier om gegevens te behouden tijdens pagina-herladingen en browsersessies, waardoor de noodzaak voor herhaalde serververzoeken vermindert en offlinemogelijkheden worden ingeschakeld.

webforJ ondersteunt drie mechanismen voor het opslaan van clientgegevens: [**Cookies**](#cookies), [**Sessies Opslag**](#session-storage) en [**Lokale Opslag**](#local-storage).

:::tip Webopslag in ontwikkelaarstools
U kunt de huidige sleutel-waarde paren van cookies, lokale opslag en sessies opslag zien in de ontwikkelaarstools van uw browser.
:::

## Samenvatting van verschillen {#summary-of-differences}
| Kenmerk            | Cookies                                      | Sessies Opslag                          | Lokale Opslag                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Volharding**     | Configureerbare vervaldatum                  | Duur van de pagina sessie               | Volhardend tot expliciet verwijderd      |
| **Opslaggrootte**  | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) per cookie                             | Rond [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Rond [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Gebruikscases**  | Gebruiker authenticatie, voorkeuren, tracking   | Tijdelijke gegevens, formuliergegevens    | Permanente instellingen, gebruikersvoorkeuren    |
| **Beveiliging**    | Kwetsbaar voor XSS, kan beveiligd worden met vlaggen | Geleegd aan het einde van de sessie, minder risico | Toegankelijk via JavaScript, potentieel risico|

## Webopslag gebruiken {#using-web-storage}
De <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>, en <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> klassen in webforJ strekken zich allemaal uit tot de abstracte <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> klasse. Om het juiste object te verkrijgen, gebruikt u de statische methoden `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()`, of `LocalStorage.getCurrent()`. Om sleutel-waarde paren toe te voegen, te verkrijgen en te verwijderen, gebruikt u de `add(key, value)`, `get(key)`, en `remove(key)` methoden.

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) zijn kleine stukjes gegevens die aan de clientzijde worden opgeslagen en met elke HTTP-aanroep naar de server worden gestuurd. Ze worden vaak gebruikt om gebruikerssessies, voorkeuren en authenticatie-informatie te onthouden. Naast de sleutel-waarde paren kunnen cookies ook attributen hebben. Om attributen voor cookies in te stellen, gebruikt u `add(key, value, attributes)`.

### Sleutelkenmerken: {#key-features}
- Kan gegevens opslaan tussen verschillende domeinen
- Ondersteunt vervaldatums
- Kleine opslaggrootte, meestal beperkt tot 4 KB
- Wordt met elke HTTP-aanroep verzonden
- Kan attributen hebben

:::info Cookie Vervaldatum
Standaard vervallen cookies in webforJ na 30 dagen. U kunt dit wijzigen met de `max-age` of `expires` attributen.
:::

### Cookies gebruiken {#using-cookies}

De volgende codefragment laat het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> object zien.

```java
// Toegang tot cookie opslag
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Voeg een nieuwe cookie toe of werk een bestaande cookie bij
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Toegang tot een cookie met een gegeven sleutel
String username = cookieStorage.get("username");

// Verwijder een cookie met een gegeven sleutel
cookieStorage.remove("username");
```
:::info Cookie Beveiliging
Bepaalde cookie-attributen, zoals `Secure` en `SameSite=None`, vereisen een veilige context met HTTPS. Deze attributen zorgen ervoor dat cookies alleen via veilige verbindingen worden verzonden, waarmee ze worden beschermd tegen onderschepping. Voor meer informatie, zie de [MDN-documentatie over cookiebeveiliging](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Gebruikscases {#use-cases}
De volgende gebruikscases zijn goed geschikt voor het gebruik van cookies:

- **Gebruiker Authenticatie**: Sla sessietokens op om gebruikers ingelogd te houden.
- **Voorkeuren**: Sla gebruikersvoorkeuren op, zoals themainstellingen of taal.
- **Tracking**: Verzamel informatie over het gebruikersgedrag voor analytics.

## Sessies Opslag {#session-storage}
Sessies opslag slaat gegevens op voor de duur van een pagina sessie. De gegevens zijn alleen toegankelijk binnen dezelfde sessie en worden gewist wanneer de pagina of tab wordt gesloten. Het gegevens blijven echter bestaan bij herladen en herstellen. Sessies opslag is het beste voor het opslaan van tijdelijke gegevens tijdens een enkele pagina sessie en het behouden van status tussen verschillende pagina's in dezelfde sessie.

### Sleutelkenmerken {#key-features-1}
- Gegevens worden niet met elke HTTP-aanroep verzonden
- Grotere opslaggrootte dan cookies
- Gegevens worden gewist wanneer de pagina of tab wordt gesloten
- Gegevens worden niet gedeeld tussen tabs

### Sessies opslag gebruiken in webforJ {#using-session-storage-in-webforj}

De volgende codefragment laat het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> object zien.

```java
// Toegang tot sessies opslag
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Voeg een nieuw of update een bestaand sessies opslag paar
sessionStorage.add("currentPage", "3");

// Toegang tot een sessies opslag paar met een gegeven sleutel
String currentPage = sessionStorage.get("currentPage");

// Verwijder een sessies opslag paar met een gegeven sleutel
sessionStorage.remove("currentPage");
```

### Gebruikscases {#use-cases-1}
De volgende gebruikscases zijn goed geschikt voor het gebruik van sessies opslag:

- **Tijdelijke Gegevensopslag**: Sla gegevens op die alleen hoeven te bestaan terwijl de gebruiker op een bepaalde pagina of sessie is.
- **Formuliergegevens**: Bewaar tijdelijk formuliergegevens voor gebruik binnen de sessie.

## Lokale Opslag {#local-storage}
Lokale opslag slaat gegevens op zonder vervaldatum. Het blijft bestaan, zelfs nadat de browser is gesloten, en kan worden geopend telkens wanneer de gebruiker de website opnieuw bezoekt. Lokale opslag is het beste voor het opslaan van gebruikersvoorkeuren of instellingen, het cachen van gegevens om de prestaties te verbeteren, en het opslaan van app-status tussen sessies.

### Sleutelkenmerken {#key-features-2}

- Gegevens blijven bestaan over sessies
- Gegevens worden niet met elke HTTP-aanroep verzonden.
- Grotere opslaggrootte dan cookies
- Niet geschikt voor gevoelige gegevens
- U moet zelf gegevens beheren, aangezien de browser ze nooit automatisch verwijdert

### Lokale opslag gebruiken in webforJ {#using-local-storage-in-webforj}

De volgende codefragment laat het gebruik van het <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> object zien.

```java
// Toegang tot lokale opslag
LocalStorage localStorage = LocalStorage.getCurrent();

// Voeg een nieuw of update een bestaand lokale opslag paar
localStorage.add("theme", "dark");

// Toegang tot een lokale opslag paar met een gegeven sleutel
String theme = localStorage.get("theme");

// Verwijder een lokale opslag paar met een gegeven sleutel
localStorage.remove("theme");
```

### Gebruikscases {#use-cases-2}
De volgende gebruikscases zijn goed geschikt voor het gebruik van lokale opslag:

- **Permanente Gegevens**: Sla gegevens op die beschikbaar moeten zijn over meerdere sessies.
- **Voorkeuren**: Sla gebruikersinstellingen en voorkeuren op die in de tijd blijven bestaan.
