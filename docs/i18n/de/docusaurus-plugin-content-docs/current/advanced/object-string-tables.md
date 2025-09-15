---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
Die `ObjectTable`, `SessionObjectTable` und `StringTable` bieten statischen Zugriff auf gemeinsame Daten in einer webforJ-Umgebung. Alle sind von überall in Ihrer Anwendung zugänglich und dienen unterschiedlichen Zwecken:

- `ObjectTable`: Zum Speichern und Abrufen von Java-Objekten über Ihre Anwendung hinweg.
- `SessionObjectTable`: Zum Speichern und Abrufen von Java-Objekten im HTTP-Sitzungsbereich.
- `StringTable`: Zum Arbeiten mit persistenten Schlüssel-Wert-Paaren von Strings, die häufig für Konfigurations- oder umgebungsbezogene Daten verwendet werden.

Diese Tabellen sind auf Umgebungsniveau verfügbar und erfordern kein Instanzmanagement.

## `ObjectTable` {#objecttable}

`ObjectTable` ist eine global zugängliche Schlüssel-Wert-Karte zum Speichern beliebiger Java-Objekte. Es bietet einfachen Zugriff auf den gemeinsamen Zustand, ohne dass etwas instanziiert oder konfiguriert werden muss. Es gibt nur eine Instanz von ObjectTable, die gelöscht wird, wenn die Anwendung aktualisiert oder beendet wird. Es ist nützlich für Szenarien, in denen Daten über mehrere Komponenten oder Kontexte hinweg verfügbar gemacht werden müssen, ohne eine Referenzkette aufrechtzuerhalten.

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

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` bietet statischen Zugriff auf HTTP-Sitzungsattribute, wenn es in einem Jakarta Servlet 6.1+ Container läuft. Im Gegensatz zu `ObjectTable`, das auf Anwendungsebene ist, speichert `SessionObjectTable` Daten in der HTTP-Sitzung des Benutzers, was sie über Anfragen hinweg persistent macht, aber für jede Benutzersitzung einzigartig ist.

Es folgt dem gleichen API-Muster wie `ObjectTable` für Konsistenz.

:::warning
Objekte, die im `SessionObjectTable` gespeichert sind, sollten `Serializable` implementieren, um die Sitzungsverfügbarkeit, -replikation und -passivierung in Servlet-Containern zu unterstützen.
:::

:::warning Verfügbarkeit in `BBjServices`
Diese Funktion ist derzeit nicht verfügbar, wenn sie mit BBjServices in Version 25.03 ausgeführt wird.
:::

### Sitzungobjekte setzen und abrufen {#setting-and-retrieving-session-objects}

```java
// ShoppingCart sollte Serializable implementieren
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Überprüfung auf Vorhandensein {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // Sitzung hat Warenkorb
}
```

### Sitzungs-Einträge entfernen {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Sitzungs-Tabellengröße {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` bietet statischen Zugriff auf globale String-Variablen. Es ist persistent und auf die aktuelle Anwendung beschränkt. Werte können programmgesteuert geändert oder über die Umgebungsconfiguration injiziert werden. Dieser Mechanismus ist besonders nützlich zum Speichern von Konfigurationswerten, Flags und Einstellungen, die anwendungsweit zugänglich sein müssen, aber keine komplexen Daten tragen müssen.

### String-Werte abrufen und setzen {#getting-and-setting-string-values}

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

### Einen Schlüssel löschen {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
