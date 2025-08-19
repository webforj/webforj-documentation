---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: aa2c014d8043f9ad53dfabcdc39844da
---
De `ObjectTable` en `StringTable` bieden statische toegang tot gedeelde gegevens in een webforJ-omgeving. Beide zijn overal in je app toegankelijk en hebben verschillende doeleinden:

- `ObjectTable`: Voor het opslaan en ophalen van Java-objecten in je app.
- `StringTable`: Voor het werken met persistente sleutel-waarde stringparen, vaak gebruikt voor configuratie of omgevings-achtige gegevens.

Deze tabellen zijn beschikbaar op het omgevingsniveau en vereisen geen instantiebeheer.

## `ObjectTable` {#objecttable}

`ObjectTable` is een wereldwijd toegankelijke sleutel-waarde map voor het opslaan van elk Java-object. Het biedt eenvoudige toegang tot gedeelde staat zonder dat je iets hoeft te initialiseren of te configureren. Er is slechts één instantie van ObjectTable en deze wordt gewist wanneer de app wordt vernieuwd of beëindigd. Het is nuttig voor scenario's waarin je gegevens beschikbaar wilt maken in meerdere componenten of contexten zonder een referentieketen te onderhouden.

### Instellingen en ophalen van objecten {#setting-and-retrieving-objects}

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

### Verwijderen van vermeldingen {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tabelgrootte {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` biedt statische toegang tot globale stringvariabelen. Het is persistent en beperkt tot de huidige app. Waarden kunnen programmatologisch worden gewijzigd of geïnjecteerd via omgevingsconfiguratie. Dit mechanisme is bijzonder nuttig voor het opslaan van configuratiewaarden, vlaggen en instellingen die app-breed toegankelijk moeten zijn, maar geen complexe gegevens hoeven te bevatten.

### Verkrijgen en instellen van stringwaarden {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Vooraf geconfigureerde waarden uit configuratie {#pre-configured-values-from-config}

Je kunt sleutels definiëren in je [`webforj.conf`](../configuration/properties#configuring-webforjconf) bestand:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Toegang krijgen in de code:

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
