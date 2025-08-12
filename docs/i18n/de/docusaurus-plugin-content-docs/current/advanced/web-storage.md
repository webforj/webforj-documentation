---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Web Storage <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) ist ein grundlegendes Konzept in der Webentwicklung, das es Websites ermöglicht, Daten auf der Client-Seite zu speichern. Dies ermöglicht es Webanwendungen, den Zustand, Einstellungen und andere Informationen lokal im Browser des Benutzers zu speichern. Web storage bietet eine Möglichkeit, Daten über Seitenneuladevorgänge und Browsersitzungen hinweg zu persistieren, wodurch die Notwendigkeit wiederholter Serveranfragen verringert und Offline-Funktionen aktiviert werden.

webforJ unterstützt drei Mechanismen zur Speicherung von Clientdaten: [**Cookies**](#cookies), [**Session Storage**](#session-storage) und [**Local Storage**](#local-storage).

:::tip Web Storage in den Entwicklertools
Sie können die aktuellen Schlüssel-Wert-Paare von Cookies, local storage und session storage in den Entwicklertools Ihres Browsers einsehen.
:::

## Zusammenfassung der Unterschiede {#summary-of-differences}
| Merkmal           | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistenz**     | Konfigurierbares Ablaufdatum                 | Dauer der Seiten-Sitzung                 | Persistent, bis explizit gelöscht        |
| **Speichergröße**  | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) pro Cookie                             | Etwa [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Etwa [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Anwendungsfälle**| Benutzerautorisierung, Einstellungen, Tracking| Temporäre Daten, Formulardaten          | Persistente Einstellungen, Benutzerpräferenzen  |
| **Sicherheit**     | Anfällig für XSS, kann mit Flags gesichert werden | Am Ende der Sitzung gelöscht, geringeres Risiko | Über JavaScript zugänglich, potenzielles Risiko |

## Verwendung von Web Storage {#using-web-storage}
Die Klassen <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> und <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> in webforJ erweitern alle die abstrakte <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink>-Klasse. Um das entsprechende Objekt zu erhalten, verwenden Sie die statischen Methoden `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()` oder `LocalStorage.getCurrent()`. Um Schlüssel-Wert-Paare hinzuzufügen, abzurufen und zu entfernen, verwenden Sie die Methoden `add(key, value)`, `get(key)` und `remove(key)`.

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) sind kleine Datenstücke, die auf der Client-Seite gespeichert und mit jeder HTTP-Anfrage an den Server gesendet werden. Sie werden häufig verwendet, um Benutzersitzungen, Einstellungen und Authentifizierungsinformationen zu speichern. Neben den Schlüssel-Wert-Paaren können Cookies auch Attribute haben. Um Attribute für Cookies festzulegen, verwenden Sie `add(key, value, attributes)`.

### Hauptmerkmale: {#key-features}
- Können Daten über verschiedene Domänen speichern
- Unterstützen Ablaufdaten
- Kleine Speichergröße, typischerweise auf 4 KB beschränkt
- Werden mit jeder HTTP-Anfrage gesendet
- Können Attribute haben

:::info Cookie-Ablauf
Standardmäßig laufen Cookies in webforJ nach 30 Tagen ab. Sie können dies mit den Attributen `max-age` oder `expires` ändern.
:::

### Verwendung von Cookies {#using-cookies}
Der folgende Codeausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>-Objekts.

```java
// Zugriff auf den Cookie-Speicher
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Einen neuen Cookie hinzufügen oder einen bestehenden Cookie aktualisieren
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Auf einen Cookie mit einem bestimmten Schlüssel zugreifen
String username = cookieStorage.get("username");

// Einen Cookie mit einem bestimmten Schlüssel entfernen
cookieStorage.remove("username");
```
:::info Cookie-Sicherheit
Bestimmte Cookie-Attribute, wie `Secure` und `SameSite=None`, erfordern einen sicheren Kontext über HTTPS. Diese Attribute stellen sicher, dass Cookies nur über sichere Verbindungen gesendet werden, um sie vor Abfangen zu schützen. Weitere Informationen finden Sie in der [MDN-Dokumentation zur Cookie-Sicherheit](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Anwendungsfälle {#use-cases}
Die folgenden Anwendungsfälle eignen sich besonders gut für die Verwendung von Cookies:

- **Benutzerautorisierung**: Speichern von Sitzungstokens, um Benutzer angemeldet zu halten.
- **Einstellungen**: Speichern von Benutzereinstellungen, wie z. B. Themen oder Sprache.
- **Tracking**: Erfassen von Informationen über das Nutzerverhalten für Analysen.

## Session Storage {#session-storage}
Session Storage speichert Daten für die Dauer einer Seiten-Sitzung. Die Daten sind nur innerhalb derselben Sitzung zugänglich und werden gelöscht, wenn die Seite oder der Tab geschlossen wird. Die Daten bleiben jedoch für das Neuladen und das Wiederherstellen erhalten. Session Storage ist am besten geeignet, um temporäre Daten während einer einzelnen Seiten-Sitzung zu speichern und den Zustand über verschiedene Seiten in derselben Sitzung beizubehalten.

### Hauptmerkmale {#key-features-1}
- Daten werden nicht mit jeder HTTP-Anfrage gesendet
- Größere Speichergröße als Cookies
- Daten werden gelöscht, wenn die Seite oder der Tab geschlossen wird
- Daten werden nicht zwischen Tabs geteilt

### Verwendung von Session Storage in webforJ {#using-session-storage-in-webforj}
Der folgende Codeausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>-Objekts.

```java
// Zugriff auf den Session-Speicher
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Einen neuen oder bestehenden Session Storage-Paar aktualisieren
sessionStorage.add("currentPage", "3");

// Auf ein Session Storage-Paar mit einem bestimmten Schlüssel zugreifen
String currentPage = sessionStorage.get("currentPage");

// Ein Session Storage-Paar mit einem bestimmten Schlüssel entfernen
sessionStorage.remove("currentPage");
```

### Anwendungsfälle {#use-cases-1}
Die folgenden Anwendungsfälle eignen sich besonders gut für die Verwendung von Session Storage:

- **Temporäre Datenspeicherung**: Speichern von Daten, die nur während der Nutzung einer bestimmten Seite oder Sitzung bestehen müssen.
- **Formulardaten**: Temporäres Speichern von Formulardaten für die Verwendung innerhalb der Sitzung.

## Local Storage {#local-storage}
Local Storage speichert Daten ohne Ablaufdatum. Es bleibt bestehen, auch nachdem der Browser geschlossen wurde, und kann jederzeit aufgerufen werden, wenn der Benutzer die Website erneut besucht. Local Storage eignet sich am besten zur Speicherung von Benutzereinstellungen oder -präferenzen, zum Caching von Daten zur Leistungsverbesserung und zur Speicherung des Anwendungszustands über verschiedene Sitzungen hinweg.

### Hauptmerkmale {#key-features-2}

- Daten bleiben über Sitzungen hinweg bestehen
- Daten werden nicht mit jeder HTTP-Anfrage gesendet.
- Größere Speichergröße als Cookies
- Nicht geeignet für sensible Daten
- Sie müssen die Daten selbst verwalten, da der Browser sie niemals automatisch löscht

### Verwendung von Local Storage in webforJ {#using-local-storage-in-webforj}
Der folgende Codeausschnitt demonstriert die Verwendung des <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink>-Objekts.

```java
// Zugriff auf den Local-Speicher
LocalStorage localStorage = LocalStorage.getCurrent();

// Einen neuen oder bestehenden Local Storage-Paar aktualisieren
localStorage.add("theme", "dark");

// Auf ein Local Storage-Paar mit einem bestimmten Schlüssel zugreifen
String theme = localStorage.get("theme");

// Ein Local Storage-Paar mit einem bestimmten Schlüssel entfernen
localStorage.remove("theme");
```

### Anwendungsfälle {#use-cases-2}
Die folgenden Anwendungsfälle eignen sich besonders gut für die Verwendung von Local Storage:

- **Persistente Daten**: Speichern von Daten, die über mehrere Sitzungen hinweg verfügbar sein sollten.
- **Einstellungen**: Speichern von Benutzereinstellungen und -präferenzen, die über längere Zeit bestehen bleiben.
