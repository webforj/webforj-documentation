---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
De `ObjectTable` en `StringTable` bieden statische toegang tot gedeelde gegevens in een webforJ-omgeving. Beiden zijn toegankelijk vanaf elke locatie in uw app en dienen verschillende doelen:

- `ObjectTable`: Voor het opslaan en ophalen van Java-objecten door uw app heen.
- `StringTable`: Voor het werken met persistente sleutel-waarde stringparen, vaak gebruikt voor configuratie of omgevingsgegevens.

Deze tabellen zijn beschikbaar op het omgevingsniveau en vereisen geen instantiebeheer.

## `ObjectTable` {#objecttable}

`ObjectTable` is een globaal toegankelijke sleutel-waarde kaart voor het opslaan van elk Java-object. Het biedt eenvoudige toegang tot gedeelde toestanden zonder de noodzaak om iets te instantiëren of configureren. Er is slechts één instantie van ObjectTable en het wordt gewist wanneer de app wordt vernieuwd of beëindigd. Het is nuttig voor scenario's waarin u gegevens beschikbaar wilt stellen over meerdere componenten of contexten zonder een referentieketen te behouden.

### Instellen en ophalen van objecten {#setting-and-retrieving-objects}

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

### Verwijderen van invoer {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tabelgrootte {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` biedt statische toegang tot globale stringvariabelen. Het is persistent en gelimiteerd tot de huidige app. Waarden kunnen programmatisch worden gewijzigd of geïnjecteerd via omgevingsconfiguratie. Dit mechanisme is bijzonder nuttig voor het opslaan van configuratiewaarden, vlaggen en instellingen die toegankelijk moeten zijn voor de hele app, maar geen complexe gegevens hoeven mee te dragen.

### Stringwaarden ophalen en instellen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Vooraf geconfigureerde waarden uit config {#pre-configured-values-from-config}

U kunt sleutels definiëren in uw [`webforj.conf`](../configuration/properties#configuring-webforjconf) bestand:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Toegang tot deze waarden in de code:

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
