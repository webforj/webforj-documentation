---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Web Storage <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) on keskeinen käsite verkkokehityksessä, joka mahdollistaa verkkosivustojen tallentaa tietoa asiakaspuolella. Tämä mahdollistaa verkkosovelluksille tilan, mieltymysten ja muiden tietojen tallentamisen paikallisesti käyttäjän selaimessa. Web storage tarjoaa tavan säilyttää tietoja sivun uudelleenlatauksien ja selaimen istuntojen yli, mikä vähentää toistuvien palvelinpyyntöjen tarvetta ja mahdollistaa offline-ominaisuudet.

webforJ tukee kolmea mekanismia asiakastietojen tallentamiseksi: [**Evästeet**](#cookies), [**Istuntotallennus**](#session-storage) ja [**Paikallinen tallennus**](#local-storage).

:::tip Web Storage kehittäjätyökaluissa
Voit nähdä nykyiset evästeet, paikallisen tallennuksen ja istuntotallennuksen avain-arvo-parit selaimen kehittäjätyökaluissa.
:::

## Yhteenveto eroista {#summary-of-differences}
| Ominaisuus        | Evästeet                                   | Istuntotallennus                      | Paikallinen tallennus                    |
|--------------------|--------------------------------------------|---------------------------------------|------------------------------------------|
| **Kestävyys**      | Määritettävä vanhentumisaika               | Sivun istunnon kesto                  | Kestävä kunnes erikseen poistettu       |
| **Tallennuskoko**  | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) per eväste                             | Noin [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Noin [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Käyttötapaukset**| Käyttäjäautentikointi, mieltymykset, seuranta | Tilapäiset tiedot, lomaketiedot      | Kestävät asetukset, käyttäjän mieltymykset |
| **Turvallisuus**   | Alttiita XSS:lle, voidaan suojata lipuilla | Tyhjentyy istunnon päättyessä, vähemmän riskiä | Saatavilla JavaScriptin kautta, mahdollinen riski|

## Web storage käyttö {#using-web-storage}
<JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> -luokat webforJ:ssä laajentavat kaikki abstraktia <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> -luokkaa. Saadaksesi sopivan objektin, käytä staattisia metodeja `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()`, tai `LocalStorage.getCurrent()`. Lisätäksesi, saadaksesi ja poistaaksesi avain-arvo-pareja, käytä metodeja `add(key, value)`, `get(key)`, ja `remove(key)`.

## Evästeet {#cookies}
[Evästeet](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) ovat pieniä tietokappaleita, jotka tallennetaan asiakaspuolelle ja lähetetään palvelimelle jokaisen HTTP-pyynnön mukana. Niitä käytetään usein käyttäjäistuntojen, mieltymysten ja autentikointitietojen muistamiseen. Lisäksi evästeillä voi olla attribuutteja. Aseta attribuutit evästeille käyttämällä `add(key, value, attributes)`.

### Avainominaisuudet: {#key-features}
- Voidaan tallentaa tietoa eri verkkotunnuksille
- Tukee vanhentumispäiviä
- Pieni tallennuskoko, tyypillisesti rajoitettu 4 KB:hen
- Lähetetään jokaisen HTTP-pyynnön yhteydessä
- Voidaan lisätä attribuutteja

:::info Evästeiden vanhentuminen
Oletusarvoisesti evästeet webforJ:ssä vanhenevat 30 päivän kuluttua. Voit muuttaa tätä `max-age` tai `expires` -attribuuttien avulla.
:::

### Evästeiden käyttö {#using-cookies}

Seuraava koodinpätkä demonstroi <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> -objektin käyttöä.

```java
// Pääsy eväste tallennukseen
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Lisää uusi eväste tai päivitä olemassa oleva eväste
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Pääsy evästeeseen annetuilla avaimilla
String username = cookieStorage.get("username");

// Poista eväste annetuilla avaimilla
cookieStorage.remove("username");
```
:::info Evästeiden turvallisuus
Tietyt evästeattribuutit, kuten `Secure` ja `SameSite=None`, edellyttävät suojattua yhteyttä HTTPS:n avulla. Nämä attribuutit varmistavat, että evästeitä lähetetään vain suojatuilla yhteyksillä, suojaten niitä sieppauksilta. Lisätietoa, katso [MDN dokumentaatio evästeiden turvallisuudesta](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Käyttötapaukset {#use-cases}
Seuraavat käyttötapaukset soveltuvat hyvin evästeiden käyttöön:

- **Käyttäjäautentikointi**: Tallenna istuntotokenit, jotta käyttäjät pysyvät kirjautuneina.
- **Mieltymykset**: Tallenna käyttäjän mieltymyksiä, kuten teema-asetuksia tai kieltä.
- **Seuranta**: Kerää tietoa käyttäjän käyttäytymisestä analytiikkaa varten.

## Istuntotallennus {#session-storage}
Istuntotallennus tallentaa tietoja sivun istunnon ajan. Tiedot ovat käytettävissä vain saman istunnon sisällä ja tyhjennetään, kun sivu tai välilehti suljetaan. Tiedot kuitenkin säilyvät uudelleenlatausten ja palautusten aikana. Istuntotallennus on paras vaihtoehto tilapäisten tietojen tallentamiseen yhden sivun istunnon aikana ja tilan ylläpitämiseen eri sivujen välillä samassa istunnossa.

### Avainominaisuudet {#key-features-1}
- Tietoja ei lähetetä jokaisen HTTP-pyynnön mukana
- Suurempi tallennuskoko kuin evästeillä
- Tiedot tyhjennetään, kun sivu tai välilehti suljetaan
- Tietoja ei jaeta välilehtien välillä

### Istuntotallennuksen käyttö webforJ:ssä {#using-session-storage-in-webforj}

Seuraava koodinpätkä demonstroi <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> -objektin käyttöä.

```java
// Pääsy istuntotallennukseen
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Lisää uusi tai päivitä olemassa oleva istuntotallennuspari
sessionStorage.add("currentPage", "3");

// Pääsy istuntotallennuspariin annetuilla avaimilla
String currentPage = sessionStorage.get("currentPage");

// Poista istuntotallennuspari annetuilla avaimilla
sessionStorage.remove("currentPage");
```

### Käyttötapaukset {#use-cases-1}
Seuraavat käyttötapaukset soveltuvat hyvin istuntotallennuksen käyttöön:

- **Tilapäinen datan tallennus**: Tallenna tiedot, jotka tarvitsevat vain säilyä, kun käyttäjä on tietyllä sivulla tai istunnossa.
- **Lomaketiedot**: Tallenna tilapäisiä lomaketietoja käytettäväksi istunnon aikana.

## Paikallinen tallennus {#local-storage}
Paikallinen tallennus tallentaa tietoja ilman vanhentumispäivää. Tiedot säilyvät jopa selaimen sulkemisen jälkeen, ja niihin voidaan päästä, milloin tahansa käyttäjä vierailee verkkosivustolla jälleen. Paikallinen tallennus on paras vaihtoehto käyttäjän mieltymysten tai asetusten tallentamiseen, tietojen välimuistiin tallentamiseen suorituskyvyn parantamiseksi ja sovellustilan tallentamiseen istuntojen välillä.

### Avainominaisuudet {#key-features-2}

- Tiedot säilyvät istuntojen yli
- Tietoja ei lähetetä jokaisen HTTP-pyynnön mukana.
- Suurempi tallennuskoko kuin evästeillä
- Ei sovellu arkaluontoisille tiedoille
- Sinun on hallittava tiedot itse, koska selain ei koskaan poista niitä automaattisesti

### Paikallisen tallennuksen käyttö webforJ:ssä {#using-local-storage-in-webforj}

Seuraava koodinpätkä demonstroi <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> -objektin käyttöä.

```java
// Pääsy paikalliseen tallennukseen
LocalStorage localStorage = LocalStorage.getCurrent();

// Lisää uusi tai päivitä olemassa oleva paikallinen tallennuspari
localStorage.add("theme", "dark");

// Pääsy paikalliseen tallennuspariin annetuilla avaimilla
String theme = localStorage.get("theme");

// Poista paikallinen tallennuspari annetuilla avaimilla
localStorage.remove("theme");
```

### Käyttötapaukset {#use-cases-2}
Seuraavat käyttötapaukset soveltuvat hyvin paikallisen tallennuksen käyttöön:

- **Kestävät tiedot**: Tallenna tiedot, jotka tulisi olla saatavilla useiden istuntojen yli.
- **Mieltymykset**: Tallenna käyttäjän asetukset ja mieltymykset, jotka säilyvät ajan myötä.
