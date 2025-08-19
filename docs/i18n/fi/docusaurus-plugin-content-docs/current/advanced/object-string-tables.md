---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: aa2c014d8043f9ad53dfabcdc39844da
---
`ObjectTable` ja `StringTable` tarjoavat staattista pääsyä jaettuun dataan webforJ-ympäristössä. Molempiin pääsee käsiksi mistä tahansa sovelluksessasi, ja ne palvelevat eri tarkoituksia:

- `ObjectTable`: Käytetään Java-objektien tallentamiseen ja hakemiseen sovelluksesi laajuudessa.
- `StringTable`: Työskentelee pysyvien avain-arvo-merkkijonoparien kanssa, joita käytetään usein konfiguraatio- tai ympäristötyyppisessä datassa.

Nämä taulukot ovat saatavilla ympäristötasolla, eikä niiden hallintaan tarvita instanssien hallintaa.

## `ObjectTable` {#objecttable}

`ObjectTable` on globaalisti käytettävissä oleva avain-arvo-kartta, jota käytetään minkä tahansa Java-objektin tallentamiseen. Se tarjoaa yksinkertaisen pääsyn jaettuun tilaan ilman, että mitään tarvitsee instansioida tai konfiguroida. `ObjectTable`:llä on vain yksi instanssi, ja se tyhjennetään, kun sovellus päivitetään tai suljetaan. Se on hyödyllinen tilanteissa, joissa tarvitaan datan saatavuutta useiden komponenttien tai kontekstien välillä ilman, että viittausketjua tarvitsee ylläpitää.

### Objektien asettaminen ja hakeminen {#setting-and-retrieving-objects}

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

### Entryjen poistaminen {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Taulukon koko {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` tarjoaa staattista pääsyä globaaleihin merkkijonomuuttujiin. Se on pysyvä ja rajattu nykyiseen sovellukseen. Arvoja voidaan ohjelmallisesti muuttaa tai syöttää ympäristön konfiguraation kautta. Tämä mekanismi on erityisen hyödyllinen, kun tallennetaan konfiguraatioarvoja, lippuja ja asetuksia, jotka on oltava käytettävissä sovelluslaajuisesti, mutta eivät tarvitse kantaa monimutkaista dataa.

### Merkkijonojen arvojen saaminen ja asettaminen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Esikonfiguroitujen arvojen hakeminen konfiguraatiosta {#pre-configured-values-from-config}

Voit määrittää avaimia [`webforj.conf`](../configuration/properties#configuring-webforjconf) -tiedostossasi:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Sitten voit päästä niihin koodissa:

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
