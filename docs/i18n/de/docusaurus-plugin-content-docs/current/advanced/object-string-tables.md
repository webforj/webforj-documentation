---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: aa2c014d8043f9ad53dfabcdc39844da
---
Die `ObjectTable` und `StringTable` bieten statischen Zugriff auf gemeinsam genutzte Daten in einer webforJ-Umgebung. Beide sind von überall in Ihrer App zugänglich und dienen unterschiedlichen Zwecken:

- `ObjectTable`: Zum Speichern und Abrufen von Java-Objekten in Ihrer App.
- `StringTable`: Zum Arbeiten mit persistenten Schlüssel-Wert-String-Paaren, die häufig für Konfiguration oder Umgebungsdaten verwendet werden.

Diese Tabellen sind auf der Umgebungsstufe verfügbar und erfordern kein Instanzenmanagement.

## `ObjectTable` {#objecttable}

`ObjectTable` ist eine global zugängliche Schlüssel-Wert-Karte zum Speichern beliebiger Java-Objekte. Es bietet einfachen Zugriff auf einen gemeinsamen Status, ohne etwas instanziieren oder konfigurieren zu müssen. Es gibt nur eine Instanz von ObjectTable, und sie wird gelöscht, wenn die App aktualisiert oder beendet wird. Es ist nützlich für Szenarien, in denen Sie Daten über mehrere Komponenten oder Kontexte hinweg verfügbar machen müssen, ohne eine Referenzkette aufrechtzuerhalten.

### Objekte setzen und abrufen {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Überprüfung auf Vorhandensein {#checking-for-presence}

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

`StringTable` bietet statischen Zugriff auf globale String-Variablen. Es ist persistent und auf die aktuelle App beschränkt. Werte können programmgesteuert modifiziert oder über die Umgebungsabhängigkeit injiziert werden. Dieser Mechanismus ist besonders nützlich für das Speichern von Konfigurationswerten, Flags und Einstellungen, die appweit zugänglich sein müssen, aber keine komplexen Daten tragen müssen.

### String-Werte abrufen und setzen {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Vorgegebene Werte aus der Konfiguration {#pre-configured-values-from-config}

Sie können Schlüssel in Ihrer [`webforj.conf`](../configuration/properties#configuring-webforjconf) Datei definieren:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Dann können Sie darauf im Code zugreifen:

```java
String val = StringTable.get("COMPANY");
```

### Überprüfung auf Vorhandensein {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Schlüssel ist gesetzt
}
```

### Ein Schlüssel löschen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
