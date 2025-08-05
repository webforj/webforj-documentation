---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
Die `ObjectTable` und `StringTable` bieten statischen Zugriff auf gemeinsam genutzte Daten in einer webforJ-Umgebung. Beide sind von überall in Ihrer App zugänglich und dienen unterschiedlichen Zwecken:

- `ObjectTable`: Zum Speichern und Abrufen von Java-Objekten in Ihrer App.
- `StringTable`: Zum Arbeiten mit persistenten Schlüssel-Wert-Paaren von Zeichenfolgen, die häufig für Konfigurations- oder Umgebungsdaten verwendet werden.

Diese Tabellen sind auf der Umgebungsebene verfügbar und erfordern kein Instanzenmanagement.

## `ObjectTable` {#objecttable}

`ObjectTable` ist eine global zugängliche Schlüssel-Wert-Karte zum Speichern beliebiger Java-Objekte. Es bietet einfachen Zugriff auf den gemeinsamen Zustand, ohne dass etwas instanziiert oder konfiguriert werden muss. Es gibt nur eine Instanz von ObjectTable, und sie wird gelöscht, wenn die App aktualisiert oder beendet wird. Es ist nützlich in Szenarien, in denen Sie Daten über mehrere Komponenten oder Kontexte hinweg verfügbar machen müssen, ohne eine Referenzkette aufrechtzuerhalten.

### Objekte setzen und abrufen {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Vorhandensein prüfen {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // Schlüssel existiert
}
```

### Einträge entfernen {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tabellengröße {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` bietet statischen Zugriff auf globale Zeichenfolgenvariablen. Es ist persistent und auf die aktuelle App beschränkt. Werte können programmgesteuert geändert oder über die Umgebungs-Konfiguration injiziert werden. Dieses Mechanismus ist besonders nützlich zum Speichern von Konfigurationswerten, Flags und Einstellungen, die appweit zugänglich sein müssen, aber keine komplexen Daten tragen müssen.

### Zeichenfolgenwerte abrufen und setzen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Vorgefertigte Werte aus der Konfiguration {#pre-configured-values-from-config}

Sie können Schlüssel in Ihrer [`webforj.conf`](../configuration/properties#configuring-webforjconf) Datei definieren:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Zugriff erfolgt im Code:

```java
String val = StringTable.get("COMPANY");
```

### Vorhandensein prüfen {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Schlüssel ist gesetzt
}
```

### Einen Schlüssel löschen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
