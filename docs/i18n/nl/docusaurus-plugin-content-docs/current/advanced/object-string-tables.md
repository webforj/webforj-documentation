---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
De `ObjectTable`, `SessionObjectTable` en `StringTable` bieden statische toegang tot gedeelde gegevens in een webforJ-omgeving. Ze zijn overal in je app toegankelijk en dienen verschillende doeleinden:

- `ObjectTable`: Voor het opslaan en ophalen van Java-objecten in je app.
- `SessionObjectTable`: Voor het opslaan en ophalen van Java-objecten in de HTTP-sessie.
- `StringTable`: Voor het werken met persistente sleutel-waarde stringparen, vaak gebruikt voor configuratie of omgeving-achtige gegevens.

Deze tabellen zijn beschikbaar op het omgevingsniveau en vereisen geen instantiebeheer.

## `ObjectTable` {#objecttable}

`ObjectTable` is een wereldwijd toegankelijke sleutel-waarde map voor het opslaan van elk Java-object. Het biedt eenvoudige toegang tot de gedeelde status zonder dat er iets geconfigureerd of geïnstantieerd hoeft te worden. Er is slechts één instantie van ObjectTable en deze wordt gewist wanneer de app wordt vernieuwd of beëindigd. Het is nuttig voor scenario's waarin je gegevens beschikbaar wilt maken over meerdere componenten of contexten zonder een referentieketen te onderhouden.

### Objecten instellen en ophalen {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Controleren op aanwezigheid {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // Sleutel bestaat
}
```

### Invoer verwijderen {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tabelgrootte {#table-size}

```java
int total = ObjectTable.size();
```

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` biedt statische toegang tot HTTP-sessie-attributen wanneer deze draait in een Jakarta Servlet 6.1+ container. In tegenstelling tot `ObjectTable`, dat app-scope heeft, slaat `SessionObjectTable` gegevens op in de HTTP-sessie van de gebruiker, waardoor deze persistent is tussen verzoeken maar uniek voor elke gebruikerssessie.

Het volgt hetzelfde API-patroon als `ObjectTable` voor consistentie.

:::warning
Objecten die zijn opgeslagen in `SessionObjectTable` moeten `Serializable` implementeren om sessie persistentie, replicatie en passivatie in servletcontainers te ondersteunen.
:::

:::warning Beschikbaarheid in `BBjServices`
Deze functie is nog niet beschikbaar bij gebruik van BBjServices in versie 25.03.
:::

### Sessieobjecten instellen en ophalen {#setting-and-retrieving-session-objects}

```java
// ShoppingCart moet Serializable implementeren
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Controleren op aanwezigheid {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // Sessie heeft winkelwagentje
}
```

### Sessie-invoer verwijderen {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Sessie tafelgrootte {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` biedt statische toegang tot globale stringvariabelen. Het is persistent en scope binnen de huidige app. Waarden kunnen programmatig worden gewijzigd of geïnjecteerd via omgeving configuratie. Dit mechanisme is bijzonder nuttig voor het opslaan van configuratiewaarden, vlaggen en instellingen die toegankelijk moeten zijn in de hele app, maar die geen complexe gegevens hoeven te dragen.

### Stringwaarden ophalen en instellen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Voor-geconfigureerde waarden uit configuratie {#pre-configured-values-from-config}

Je kunt sleutels definiëren in je [`webforj.conf`](../configuration/properties#configuring-webforjconf) bestand:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Toegang krijgen in code:

```java
String val = StringTable.get("COMPANY");
```

### Controleren op aanwezigheid {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Sleutel is ingesteld
}
```

### Een sleutel wissen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
