---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Web Storage <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Web Storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) ist ein grundlegendes Konzept in der Webentwicklung, das es Websites ermöglicht, Daten auf der Clientseite zu speichern. Dadurch können Webanwendungen den Zustand, Vorlieben und andere Informationen lokal im Browser des Benutzers speichern. Web Storage bietet eine Möglichkeit, Daten über Seitenaktualisierungen und Browsersitzungen hinweg zu persistieren, wodurch die Notwendigkeit wiederholter Serveranfragen verringert und Offline-Funktionen ermöglicht werden.

webforJ unterstützt drei Mechanismen zur Speicherung von Kundendaten: [**Cookies**](#cookies), [**Session Storage**](#session-storage) und [**Local Storage**](#local-storage).

:::tip Web Storage in Developer Tools
Sie können die aktuellen Cookie-, lokalen Speicher- und Sitzungsspeicher-Schlüssel-Wert-Paare in den Entwicklertools Ihres Browsers sehen.
:::

## Zusammenfassung der Unterschiede {#summary-of-differences}
| Feature            | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistenz**     | Konfigurierbares Ablaufdatum                | Dauer der Seiten-Sitzung                 | Persistiert, bis ausdrücklich gelöscht   |
| **Speichergröße**  | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) pro Cookie                             | Ungefähr [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Ungefähr [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Anwendungsfälle**| Benutzerauthentifizierung, Vorlieben, Tracking   | Temporäre Daten, Formulardaten          | Persistente Einstellungen, Benutzerpräferenzen    |
| **Sicherheit**     | Anfällig für XSS, kann mit Flags gesichert werden | Wird am Ende der Sitzung gelöscht, weniger Risiko        | Über JavaScript zugänglich, potenzielles Risiko|

## Verwendung von Web Storage {#using-web-storage}
Die <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> und <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> Klassen in webforJ erweitern alle die abstrakte <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> Klasse. Um das entsprechende Objekt zu erhalten, verwenden Sie die statischen Methoden `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()` oder `LocalStorage.getCurrent()`. Um Schlüssel-Wert-Paare hinzuzufügen, abzurufen und zu entfernen, verwenden Sie die Methoden `add(key, value)`, `get(key)` und `remove(key)`.

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) sind kleine Datenstücke, die auf der Clientseite gespeichert und mit jeder HTTP-Anfrage an den Server gesendet werden. Sie werden oft verwendet, um Benutzersitzungen, Vorlieben und Authentifizierungsinformationen zu speichern. Neben den Schlüssel-Wert-Paaren können Cookies auch Attribute haben. Um Attribute für Cookies festzulegen, verwenden Sie `add(key, value, attributes)`.

### Hauptmerkmale: {#key-features}
- Können Daten über verschiedene Domänen hinweg speichern
- Unterstützen Ablaufdaten
- Kleiner Speicherplatz, typischerweise auf 4 KB beschränkt
- Werden mit jeder HTTP-Anfrage gesendet
- Können Attribute haben

:::info Cookie-Ablauf
Standardmäßig laufen Cookies in webforJ nach 30 Tagen ab. Sie können dies mit den Attributen `max-age` oder `expires` ändern.
:::

### Verwendung von Cookies {#using-cookies}

Der folgende Code-Ausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> Objekts.

```java
// Zugriff auf Cookie-Speicher
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Hinzufügen eines neuen Cookies oder Aktualisieren eines bestehenden Cookies
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Zugriff auf ein Cookie mit einem bestimmten Schlüssel
String username = cookieStorage.get("username");

// Entfernen eines Cookies mit einem bestimmten Schlüssel
cookieStorage.remove("username");
```
:::info Cookie-Sicherheit
Bestimmte Cookie-Attribute, wie `Secure` und `SameSite=None`, erfordern einen sicheren Kontext, der HTTPS verwendet. Diese Attribute stellen sicher, dass Cookies nur über gesicherte Verbindungen gesendet werden, um sie vor Abfangen zu schützen. Weitere Informationen finden Sie in der [MDN-Dokumentation zur Cookie-Sicherheit](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Anwendungsfälle {#use-cases}
Die folgenden Anwendungsfälle eignen sich gut zur Nutzung von Cookies:

- **Benutzerauthentifizierung**: Speicherung von Sitzungstoken, um Benutzer eingeloggt zu halten.
- **Vorlieben**: Speicherung von Benutzerpräferenzen, wie z. B. Themainstellungen oder Sprache.
- **Tracking**: Sammlung von Informationen über das Benutzerverhalten für Analysen.


## Session Storage {#session-storage}
Session Storage speichert Daten für die Dauer einer Seiten-Sitzung. Die Daten sind nur innerhalb derselben Sitzung zugänglich und werden gelöscht, wenn die Seite oder der Tab geschlossen wird. Die Daten persistieren jedoch für Aktualisierungen und Wiederherstellungen. Session Storage ist am besten geeignet, um temporäre Daten während einer einzelnen Seiten-Sitzung zu speichern und den Zustand über verschiedene Seiten innerhalb derselben Sitzung aufrechtzuerhalten.

### Hauptmerkmale {#key-features-1}
- Daten werden nicht mit jeder HTTP-Anfrage gesendet
- Größerer Speicherplatz als Cookies
- Daten werden gelöscht, wenn die Seite oder der Tab geschlossen wird
- Daten werden nicht über Tabs hinweg geteilt

### Verwendung von Session Storage in webforJ {#using-session-storage-in-webforj}

Der folgende Code-Ausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> Objekts.

```java
// Zugriff auf Session-Speicher
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Hinzufügen eines neuen oder Aktualisieren eines bestehenden Session-Speicher-Paares
sessionStorage.add("currentPage", "3");

// Zugriff auf ein Session-Speicher-Paar mit einem bestimmten Schlüssel
String currentPage = sessionStorage.get("currentPage");

// Entfernen eines Session-Speicher-Paares mit einem bestimmten Schlüssel
sessionStorage.remove("currentPage");
```

### Anwendungsfälle {#use-cases-1}
Die folgenden Anwendungsfälle eignen sich gut zur Nutzung von Session Storage:

- **Temporäre Datenspeicherung**: Speicherung von Daten, die nur solange persistieren müssen, wie der Benutzer sich auf einer bestimmten Seite oder Sitzung befindet.
- **Formulardaten**: Temporäre Speicherung von Formulardaten zur Verwendung innerhalb der Sitzung.

## Local Storage {#local-storage}
Local Storage speichert Daten ohne Ablaufdatum. Es persistiert sogar nach dem Schließen des Browsers und kann jederzeit abgerufen werden, wenn der Benutzer die Website erneut besucht. Local Storage ist am besten geeignet, um Benutzerpräferenzen oder Einstellungen zu speichern, Daten zwischenzuspeichern, um die Leistung zu verbessern, und den Anwendungszustand über Sitzungen hinweg zu speichern.

### Hauptmerkmale {#key-features-2}

- Daten persistieren über Sitzungen hinweg
- Daten werden nicht mit jeder HTTP-Anfrage gesendet.
- Größerer Speicherplatz als Cookies
- Nicht geeignet für sensible Daten
- Sie müssen die Daten selbst verwalten, da der Browser sie niemals automatisch löscht

### Verwendung von Local Storage in webforJ {#using-local-storage-in-webforj}

Der folgende Code-Ausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> Objekts.

```java
// Zugriff auf lokalen Speicher
LocalStorage localStorage = LocalStorage.getCurrent();

// Hinzufügen eines neuen oder Aktualisieren eines bestehenden lokalen Speicher-Paares
localStorage.add("theme", "dark");

// Zugriff auf ein lokal gespeichertes Paar mit einem bestimmten Schlüssel
String theme = localStorage.get("theme");

// Entfernen eines lokalen Speicher-Paares mit einem bestimmten Schlüssel
localStorage.remove("theme");
```

### Anwendungsfälle {#use-cases-2}
Die folgenden Anwendungsfälle eignen sich gut zur Nutzung von Local Storage:

- **Persistente Daten**: Speicherung von Daten, die über mehrere Sitzungen hinweg verfügbar sein sollen.
- **Vorlieben**: Speicherung von Benutzereinstellungen und Vorlieben, die über die Zeit bestehen bleiben.
