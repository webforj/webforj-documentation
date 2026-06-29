---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# App-Abzeichen <DocChip chip='since' label='26.01' />

webforJ bietet zwei ergänzende Badge-APIs an. <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` malt das App-Icon des Betriebssystems, das im Dock, in der Taskleiste oder auf dem Startbildschirm angezeigt wird. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` malt das Dokument-Favicon, das im Tab-Bereich des Browsers angezeigt wird. Sie zielen auf unterschiedliche Oberflächen ab und haben unterschiedliche Voraussetzungen, daher rufen die meisten Apps beide für die breiteste Sichtbarkeit auf.

<!-- INTRO_END -->

## App-Icon-Abzeichen {#app-icon-badge}

`App.setBadge` rendert das Abzeichen auf dem Icon, das das Betriebssystem für die App verwendet: das macOS-Dock, die Windows-Taskleiste, das Chrome OS-Regal oder den Android-Startbildschirm.

![App-Icon-Abzeichen im macOS-Dock](/img/app-badges/app-badge.png)

### Voraussetzungen {#app-prerequisites}

Das Abzeichen ist nur sichtbar, wenn alle folgenden Bedingungen erfüllt sind:

- Der Browser unterstützt das Zeichnen von Abzeichen auf App-Icons.
- Die Seite wird aus einem sicheren Kontext (HTTPS oder `http://localhost` während der Entwicklung) ausgeliefert. Reine HTTP-Quellen lehnen den Aufruf ab.
- Die App muss auf dem Gerät installiert sein. Der Installationsablauf variiert je nach Browser: Chromium-Browser bieten eine Installationsaufforderung für jede Seite, die ein Manifest bereitstellt, Safari auf macOS verwendet **Ablage → Zum Dock hinzufügen**, und Safari auf iOS verwendet **Teilen → Zum Startbildschirm hinzufügen**.

Um die App installierbar zu machen, wenn sie unter Spring Boot oder einem eigenständigen webforJ-Server läuft, annotieren Sie die <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>-Unterklasse mit <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink>. Der Annotation-Processor erzeugt das Manifest, die App-Icon-Link-Tags und die Meta-Tags, die der Browser benötigt, um die Installationsaufforderung anzuzeigen.

```java
@AppProfile(name = "Inbox", shortName = "Inbox")
public class Application extends App {}
```

Siehe die Seite [Installierbare Apps](../configuration/installable-apps) für die vollständige Liste der `@AppProfile`-Mitglieder, Icon-Größen und plattformspezifische Anleitungen zum Installationsablauf.

### Browserunterstützung {#app-browser-support}

Nach der Installation hängt das Rendern des Badges vom Browser ab. Die Unterstützung für die Installation selbst wird auf der Seite [Installierbare Apps](../configuration/installable-apps#browser-support) behandelt.

| Browser | Badge nach der Installation angezeigt |
| --- | --- |
| Chrome, Edge, Opera und andere Chromium-Browser (Desktop und Android) | Ja |
| Safari auf macOS Sonoma (Safari 17) und später | Ja |
| Safari auf iOS 16.4 und später | Ja |
| Firefox (alle Plattformen) | Nein. Der Aufruf gibt zurück, ohne zu rendern. |

### Setzen und Löschen des Badges {#app-setting-clearing}

Übergeben Sie eine positive Ganzzahl, um ein numerisches Badge anzuzeigen. Übergeben Sie `null` oder `0`, um es zu löschen. Rufen Sie die Überladung ohne Argumente auf, um den Flaggenindikator (einen kleinen Punkt, das genaue visuelle Ergebnis ist plattformspezifisch) anzuzeigen.

```java
App.setBadge(5);     // numerisches Badge
App.setBadge();      // Flaggenindikator ohne Zahl
App.setBadge(0);     // löschen
App.setBadge(null);  // löschen
```

`App.setBadge` gibt sofort zurück. Der Browser schreibt das Badge asynchron auf die Oberfläche des Betriebssystems, und die Änderung wird nicht an die App zurückgemeldet.

## Browser-Tab-Icon-Abzeichen {#browser-tab-icon-badge}

`Page.setIconBadge` malt die Anzahl auf das Dokument-Favicon. Es funktioniert in jedem Tab ohne Installation und erfordert kein Manifest. Das Badge ist im Tab-Bereich des Browsers sichtbar und an jedem anderen Ort, der das Favicon rendert, wie Lesezeichen oder Ansichten kürzlich besuchter Seiten.

![Browser-Tab-Favicon mit einer numerischen Badge-Überlagerung](/img/app-badges/icon-badge.png)

### Setzen und Löschen des Badges {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // numerisches Badge
page.setIconBadge();      // Flaggenindikator ohne Zahl
page.setIconBadge(0);     // löschen
page.setIconBadge(null);  // löschen
```

Das Löschen des Badges stellt das ursprüngliche Favicon wieder her.

:::info Ausführen mit `BBjServices`
Wenn die App von `BBjServices` bereitgestellt wird, ist das Favicon das **Shortcut Image**, das für die App im Enterprise Manager konfiguriert ist. Das Badge wird auf das Icon gemalt, das der Enterprise Manager bereitstellt. Wenn kein Shortcut-Bild konfiguriert ist, hat `Page.setIconBadge` kein Favicon zur Überlagerung und macht stillschweigend nichts.
:::

### Badge-Styling {#styling-the-badge}

Übergeben Sie ein <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink>, um Farbe, Form und Größe zu steuern:

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

Das Optionsobjekt ist ein Werthalter. Alle Setter geben `this` zurück, sodass Aufrufe verkettet werden können.

| Option | Typ | Standard | Hinweise |
|---|---|---|---|
| `color` | `java.awt.Color` | `#e53935` | Hintergrundfarbe des Badges. Die Textfarbe wird automatisch für den Kontrast abgeleitet, sodass die Ziffern auf jeder gewählten Farbe lesbar bleiben. |
| `shape` | `Shape` | `CIRCLE` | `CIRCLE` oder `SQUARE`. |
| `size` | `double` | `1.0` | Relative Größe. `0.5` hat einen Durchmesser, der der Standardeinstellung entspricht; `1.5` ist 50% größer. Das Badge wird begrenzt, um in die Favicon-Leinwand zu passen. |

### Browser-Vorbehalt {#browser-caveat}

Safari aktualisiert das Favicon nach dem ersten Laden der Seite nicht. Aufrufe von `Page.setIconBadge` werden ohne Fehler abgeschlossen, aber Safari zeigt weiterhin das ursprüngliche Icon an. Verwenden Sie `Page.setTitle`, um die Anzahl auch dem Dokumenttitel voranzustellen, wenn Sie einen sichtbaren Hinweis in Safari benötigen.

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") Inbox");
```

## Wahl zwischen den beiden {#choosing-between-the-two}

| Oberfläche | API | Erfordert Installation | Sichtbar in Safari |
|---|---|---|---|
| Betriebssystem-App-Icon | `App.setBadge` | Ja | Ja (macOS Sonoma / iOS 16.4 und später) |
| Browser-Tab-Favicon | `Page.setIconBadge` | Nein | Nein. Der Aufruf wird ohne Fehler abgeschlossen, aber der Tab-Bereich wird nicht aktualisiert. |

Die meisten Apps rufen beide auf, damit das Badge sichtbar ist, unabhängig davon, ob der Benutzer sich in einem installierten Fenster oder in einem regulären Browsertab befindet.
