---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
`ObjectTable` ja `StringTable` tarjoavat staattisen pääsyn jaettuihin tietoihin webforJ-ympäristössä. Molemmat ovat saatavilla mistä tahansa sovelluksesi osasta ja niillä on eri tarkoitukset:

- `ObjectTable`: Java-oliot sovelluksesi tallentamiseen ja hakemiseen.
- `StringTable`: Pysyvien avain-arvo-merkkijonoparien käsittelyyn, jota käytetään usein konfiguraatio- tai ympäristötietojen säilyttämiseen.

Nämä taulukot ovat saatavilla ympäristötasolla eivätkä vaadi instanssien hallintaa.

## `ObjectTable` {#objecttable}

`ObjectTable` on globaalisti käytettävissä oleva avain-arvo-kartta minkä tahansa Java-olion tallentamiseen. Se tarjoaa yksinkertaisen pääsyn jaettuun tilaan ilman, että mitään tarvitsee instanssia tai konfiguroida. ObjectTable:sta on vain yksi instanssi, ja se tyhjennetään, kun sovellus päivitetään tai lopetetaan.
Se on hyödyllinen skenaarioissa, joissa tarvitset tietoa useiden komponenttien tai kontekstien saataville ilman, että tarvitsee ylläpitää viittausketjua.

### Olioiden asettaminen ja hakeminen {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Läsnäolon tarkistaminen {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // Avain existee
}
```

### Merkintöjen poistaminen {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Taulukon koko {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` tarjoaa staattisen pääsyn globaaleihin merkkimuuttujiin. Se on pysyvä ja rajoittuu nykyiseen sovellukseen. Arvoja voidaan ohjelmallisesti muokata tai injektoida ympäristön konfiguraation kautta.
Tämä mekanismi on erityisen hyödyllinen konfiguraatioarvojen, lippujen ja asetusten tallentamisessa, jotka on oltava saatavilla sovelluksen laajuisesti, mutta eivät tarvitse kantaa monimutkaista dataa.

### Merkkijonojen arvojen hakeminen ja asettaminen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Ennakkoon määritellyt arvot konfiguraatiosta {#pre-configured-values-from-config}

Voit määrittää avaimet [`webforj.conf`](../configuration/properties#configuring-webforjconf) -tiedostossasi:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Sitten voit käyttää sitä koodissa:

```java
String val = StringTable.get("COMPANY");
```

### Läsnäolon tarkistaminen {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Avain on asetettu
}
```

### Avain tyhjentäminen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
