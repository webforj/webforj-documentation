---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Verkkosäilö <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Verkkosäilö](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) on keskeinen käsite verkkokehityksessä, joka mahdollistaa verkkosivujen tallentaa tietoa asiakaspuolella. Tämä mahdollistaa verkkosovellusten tallentaa tilaa, mieltymyksiä ja muita tietoja paikallisesti käyttäjän selaimessa. Verkkosäilö tarjoaa tavan säilyttää tietoa sivun latausten ja selainistuntojen välillä, mikä vähentää toistuvien palvelinpyyntöjen tarvetta ja mahdollistaa offline-toiminnot.

webforJ tukee kolmea mekanismia asiakastietojen tallentamiseen: [**Evästeet**](#cookies), [**Istuntotallennus**](#session-storage) ja [**Paikallinen tallennus**](#local-storage).

:::tip Verkkosäilö Kehittäjätyökaluissa
Voit nähdä nykyiset evästeet, paikallisen tallennuksen ja istuntotallennuksen avain-arvo-pareja selaimesi kehittäjätyökaluissa.
:::

## Erojen yhteenveto {#summary-of-differences}
| Ominaisuus         | Evästeet                                      | Istuntotallennus                          | Paikallinen tallennus                    |
|--------------------|-----------------------------------------------|------------------------------------------|------------------------------------------|
| **Kestävyys**      | Säädettävä vanhentumisaika                    | Sivuston istunnon kesto                  | Kestävä, kunnes se poistetaan erikseen   |
| **Tallennuskapasiteetti** | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) per eväste | Noin [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB | Noin [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB |
| **Käyttötapaukset**| Käyttäjän todennus, mieltymykset, seuranta    | Tilapäinen data, lomakedata             | Kestäviä asetuksia, käyttäjän mieltymyksiä |
| **Turvallisuus**   | Alttiita XSS:lle, voidaan varmistaa lipuilla | Tyhjennetään istunnon lopussa, pienempi riski | JavaScriptin kautta saavutettavissa, mahdollinen riski |

## Verkkosäilön käyttö {#using-web-storage}
<JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> luokat webforJ:ssä kaikki perivät abstraktista <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> luokasta. Sopivan objektin saamiseksi käytä staattisia menetelmiä `CookieStorage.getCurrent()`, `SessionStorage.getCurrent()`, tai `LocalStorage.getCurrent()`. Avain-arvo-pareja lisätäksesi, saadaksesi ja poistaaksesi käytä `add(key, value)`, `get(key)`, ja `remove(key)` menetelmiä.

## Evästeet {#cookies}
[Evästeet](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) ovat pieniä tietokappaleita, jotka tallennetaan asiakaspuolelle ja lähetetään palvelimelle jokaisen HTTP-pyynnön mukana. Niitä käytetään usein muistamaan käyttäjäistuntoja, mieltymyksiä ja todennus tietoja. Avain-arvo-parin lisäksi evästeillä voi olla myös attribuutteja. Aseta attribuutit evästeille käyttämällä `add(key, value, attributes)`.

### Avainominaisuudet: {#key-features}
- Voidaan tallentaa tietoja eri verkkotunnusten välillä
- Tukee vanhentumispäiviä
- Pieni tallennuskapasiteetti, tyypillisesti rajoitettu 4 KB
- Lähetetään jokaisen HTTP-pyynnön mukana
- Voivat olla oireita

:::info Evästeiden vanhentuminen
Oletuksena evästeet webforJ:ssä vanhenevat 30 päivän kuluttua. Voit muuttaa tätä `max-age` tai `expires` attribuuttien avulla.
:::

### Evästeiden käyttö {#using-cookies}

Seuraava koodinpätkä havainnollistaa <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> objektin käyttöä.

```java
// Pääsy eväste tallennukseen
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Lisää uusi eväste tai päivitä olemassa oleva eväste
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Pääsy evästeeseen annetulla avaimella
String username = cookieStorage.get("username");

// Poista eväste annetulla avaimella
cookieStorage.remove("username");
```
:::info Evästeiden turvallisuus
Tietyt evästeattribuutit, kuten `Secure` ja `SameSite=None`, vaativat turvallisen kontekstin käyttämällä HTTPS:ää. Nämä attribuutit varmistavat, että evästeet lähetetään vain turvallisten yhteyksien kautta, suojaten niitä sieppaamiselta. Lisätietoja saat [MDN-dokumentaatiosta evästeiden turvallisuudesta](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Käyttötapaukset {#use-cases}
Seuraavat käyttötapaukset sopivat hyvin evästeiden käyttöön:

- **Käyttäjäntunnistus**: Tallenna istuntotunnuksia pitääksesi käyttäjät kirjautuneina.
- **Mieltymykset**: Tallenna käyttäjän mieltymykset, kuten teeman asetukset tai kieli.
- **Seuranta**: Kerää tietoja käyttäjän käytöksestä analytiikkaa varten.

## Istuntotallennus {#session-storage}
Istuntotallennus tallentaa tietoa sivuston istunnon ajaksi. Tieto on käytettävissä vain samassa istunnossa ja se tyhjennetään, kun sivu tai välilehti suljetaan. Kuitenkin tieto kestää uudelleenlataukset ja palautukset. Istuntotallennus sopii parhaiten tilapäisten tietojen tallentamiseen yksittäisen sivu-istunnon aikana ja tilan ylläpitämiseen eri sivuilla samassa istunnossa.

### Avainominaisuudet {#key-features-1}
- Tietoa ei lähetetä jokaisen HTTP-pyynnön mukana
- Suurempi tallennuskapasiteetti kuin evästeillä
- Tieto tyhjennetään, kun sivu tai välilehti suljetaan
- Tietoa ei jaeta välilehtien välillä

### Istuntotallennuksen käyttö webforJ:ssä {#using-session-storage-in-webforj}

Seuraava koodinpätkä havainnollistaa <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> objektin käyttöä.

```java
// Pääsy istuntotallennukseen
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Lisää uusi tai päivitä olemassa oleva istuntotallennuspare
sessionStorage.add("currentPage", "3");

// Pääsy istuntotallennuspariin annetulla avaimella
String currentPage = sessionStorage.get("currentPage");

// Poista istuntotallennuspari annetulla avaimella
sessionStorage.remove("currentPage");
```

### Käyttötapaukset {#use-cases-1}
Seuraavat käyttötapaukset sopivat hyvin istuntotallennuksen käyttöön:

- **Tilapäinen datan tallennus**: Tallenna tietoa, jonka pitää vain kestää käyttäjän ollessa tietyllä sivulla tai istunnossa.
- **Lomakedata**: Tilapäisesti tallenna lomakedataa käytettäväksi istunnon aikana.

## Paikallinen tallennus {#local-storage}
Paikallinen tallennus tallentaa tietoa ilman vanhentumispäivää. Se kestää jopa selain suljettaessa ja voidaan kutsua aina, kun käyttäjä vierailee verkkosivustolla uudelleen. Paikallinen tallennus soveltuu parhaiten käyttäjän mieltymysten tai asetusten tallentamiseen, data välimuistiin suorituksen parantamiseksi ja sovellustilan tallentamiseen istuntokohtaisesti.

### Avainominaisuudet {#key-features-2}
- Tieto kestää useita istuntoja
- Tieto ei lähetetä jokaisen HTTP-pyynnön mukana
- Suurempi tallennuskapasiteetti kuin evästeillä
- Ei soveltuva herkkään dataan
- Sinun on hallittava tietoja itse, koska selain ei poisteta sitä automaattisesti

### Paikallisen tallennuksen käyttö webforJ:ssä {#using-local-storage-in-webforj}

Seuraava koodinpätkä havainnollistaa <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> objektin käyttöä.

```java
// Pääsy paikalliseen tallennukseen
LocalStorage localStorage = LocalStorage.getCurrent();

// Lisää uusi tai päivitä olemassa oleva paikallinen tallennuspari
localStorage.add("theme", "dark");

// Pääsy paikalliseen tallennuspariin annetulla avaimella
String theme = localStorage.get("theme");

// Poista paikallinen tallennuspari annetulla avaimella
localStorage.remove("theme");
```

### Käyttötapaukset {#use-cases-2}
Seuraavat käyttötapaukset sopivat hyvin paikallisen tallennuksen käyttöön:

- **Kestävä data**: Tallenna dataa, joka pitää olla saatavilla useiden istuntojen ajan.
- **Mieltymykset**: Tallenna käyttäjän asetukset ja mieltymykset, jotka säilyvät ajan myötä.
