---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
`ObjectTable`, `SessionObjectTable` ja `StringTable` tarjoavat staattisen pääsyn jaettuun dataan webforJ-ympäristössä. Kaikki ovat käytettävissä mistä tahansa sovelluksessasi ja palvelevat eri tarkoituksia:

- `ObjectTable`: Java-olioiden tallentamiseen ja hakemiseen koko sovelluksessa.
- `SessionObjectTable`: Java-olioiden tallentamiseen ja hakemiseen HTTP-istuntotason kontekstissa.
- `StringTable`: Pysyvien avain-arvo-merkkijonoparien työskentelyyn, joita käytetään usein konfiguraatio- tai ympäristötietona.

Nämä taulukot ovat saatavilla ympäristötasolla eivätkä vaadi instanssien hallintaa.

## `ObjectTable` {#objecttable}

`ObjectTable` on globaalisti saatavissa oleva avain-arvo-kartta minkä tahansa Java-olion tallentamiseen. Se tarjoaa yksinkertaisen pääsyn jaettuun tilaan ilman, että mitään tarvitsisi instansioida tai konfiguroida. `ObjectTable`:llä on vain yksi instanssi, ja se tyhjennetään, kun sovellus päivitetään tai lopetetaan. Se on hyödyllinen skenaarioissa, joissa tarvitset tietojen saatavuutta useiden komponenttien tai kontekstien välillä ilman viittausketjun ylläpitämistä.


### Olioiden asettaminen ja hakeminen {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Läsnäolon tarkistaminen {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // Avain on olemassa
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

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` tarjoaa staattisen pääsyn HTTP-istuntotason attribuutteihin, kun se toimii Jakarta Servlet 6.1+ -säiliössä. Toisin kuin `ObjectTable`, joka on sovellustason, `SessionObjectTable` tallentaa tietoja käyttäjän HTTP-istuntoon, mikä tekee niistä pysyviä pyyntöjen välillä, mutta ainutlaatuisia jokaiselle käyttäjäistunnolle.

Se noudattaa samaa API-mallia kuin `ObjectTable` johdonmukaisuuden vuoksi.

:::warning
`SessionObjectTable`:ssä tallennettujen objektien tulee toteuttaa `Serializable` tukeakseen istuntojen pysyvyyttä, replikoitumista ja passivointia servlet-säiliöissä.
:::

:::warning Saatavuus `BBjServices`:ssa
Tätä ominaisuutta ei ole vielä saatavilla käytettäessä BBjServicesia versiolla 25.03.
:::

### Istunto-olioiden asettaminen ja hakeminen {#setting-and-retrieving-session-objects}

```java
// ShoppingCart:in tulee toteuttaa Serializable
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Läsnäolon tarkistaminen {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // Istunnolla on ostoskori
}
```

### Istunto-merkintöjen poistaminen {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Istuntotaulukon koko {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` tarjoaa staattisen pääsyn globaalisiin merkkimuuttujiin. Se on pysyvä ja rajoittuu nykyiseen sovellukseen. Arvoja voidaan ohjelmallisesti muuttaa tai injektoida ympäristön konfiguraation kautta. Tämä mekanismi on erityisen hyödyllinen konfiguraatioarvojen, lippujen ja asetusten tallentamiseen, joita on oltava saatavilla koko sovelluksessa, mutta jotka eivät tarvitse kantaa monimutkaista dataa.

### Merkkijonoarvojen hakeminen ja asettaminen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Esikonfiguroidut arvot konfiguraatiosta {#pre-configured-values-from-config}

Voit määrittää avaimet [`webforj.conf`](../configuration/properties#configuring-webforjconf) -tiedostossasi:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Sitten pääset niihin koodissa:

```java
String val = StringTable.get("COMPANY");
```

### Läsnäolon tarkistaminen {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Avain on asetettu
}
```

### Avaimen tyhjentäminen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
