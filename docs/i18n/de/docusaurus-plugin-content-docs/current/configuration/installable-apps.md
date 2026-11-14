---
title: Installierbare Apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

Mit der Annotation `@AppProfile` in webforJ können Sie Ihre App auf unterstützten Plattformen installierbar machen.
Installierbare Web-Apps sind in das Betriebssystem des Geräts integriert.
Nach der Installation erscheinen sie auf dem Startbildschirm oder im App-Menü, ähnlich wie native Apps.
Dazu müssen bestimmte Metadaten wie Name, Beschreibung und Symbole angegeben werden.
Diese Angaben helfen dem Betriebssystem, die App zu erkennen und anzuzeigen.

:::info Anforderung an einen sicheren Ursprung
Damit eine App installierbar ist, muss sie von einem sicheren Ursprung wie `https` bereitgestellt werden.
Browser lehnen Installationsversuche von unsicheren Ursprüngen ab. Diese Regel gilt jedoch nicht, wenn die App während der Entwicklung lokal über `localhost` bereitgestellt wird.

<!-- vale off -->
Weitere Informationen zu sicheren Kontexten und ihrer Bedeutung finden Sie in der [MDN-Dokumentation zu sicheren Kontexten](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## Browserunterstützung {#browser-support}

Die Unterstützung für die Installation einer Web-App variiert je nach Browser und Plattform.

### Desktop {#browser-support-desktop}

- **Chromium-Browser** (Chrome, Edge, Opera, Brave und andere) installieren auf allen unterstützten Desktopbetriebssystemen jede App, die eine Manifestdatei enthält.
- **Safari** unterstützt **Ablage → Zum Dock hinzufügen** unter macOS Sonoma (Safari 17) und höher. Dieser Ablauf funktioniert mit jeder Web-App, unabhängig davon, ob sie eine Manifestdatei enthält.
- **Firefox** unterstützt die Installation von Web-Apps über eine Manifestdatei auf dem Desktop nicht.

### Mobilgeräte {#browser-support-mobile}

- Unter **Android** unterstützen Chrome, Edge, Firefox, Opera und Samsung Internet die Installation von Web-Apps.
- Unter **iOS 16.3 und älter** können Web-Apps nur über Safari installiert werden (**Teilen → Zum Home-Bildschirm**).
- Unter **iOS 16.4 und höher** können Web-Apps über das Menü „Teilen“ in Safari, Chrome, Edge, Firefox und Orion installiert werden.

## Annotation `@AppProfile` {#appprofile-annotation}

Die Annotation `@AppProfile` wird auf die Haupt-App-Klasse angewendet und erfordert nur eine minimale Konfiguration. Mindestens müssen Sie Folgendes angeben:

- **name**: Der vollständige Name der App.
- **shortName**: Eine Kurzform des Namens für die Verwendung in Bereichen mit begrenztem Platz.

Zusätzliche optionale Eigenschaften ermöglichen die Anpassung des Erscheinungsbilds und Verhaltens der App.

Wenn die Annotation `@AppProfile` vorhanden ist, führt webforJ Folgendes aus:

- Richtet automatisch die erforderlichen Meta-Tags ein.
- Generiert ein [Web-App-Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Stellt zugehörige Ressourcen wie Symbole und Screenshots bereit.

### Beispiel: `@AppProfile` anwenden {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is a simple banking application built with webforJ",
  screenshots = {
    @AppProfile.Screenshot(
      src = "ws://img/screenshots/s1.jpg",
      sizes = "1080x1920"
    )
  }
)
public class Application extends App {
}
```

## Eigenschaften von `@AppProfile` {#appprofile-properties}

In der folgenden Tabelle sind alle von der Annotation `@AppProfile` unterstützten Eigenschaften aufgeführt:

| **Eigenschaft**   | **Typ**                                           | **Beschreibung**                                                                                           | **Standardwert**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | Der vollständige Name der App, der in App-Menüs und Installationsdialogen angezeigt wird.                 | **Erforderlich**      |
| `shortName`       | `String`                                           | Eine Kurzform des Namens für Bereiche mit begrenztem Platz. Sollte höchstens 12 Zeichen umfassen.         | **Erforderlich**      |
| `description`     | `String`                                           | Eine kurze Beschreibung der App, die während der Installation und in den App-Einstellungen angezeigt wird. | `""`                  |
| `themeColor`      | `String`                                           | Die Designfarbe der App, die beim Start der App auf die Browseroberfläche angewendet wird.                | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | Die anfängliche Hintergrundfarbe der App während des Ladens.                                              | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | Die URL, die beim Start der App geöffnet wird.                                                            | `"."`                 |
| `display`         | `Display` **_Enum_**                               | Der Anzeigemodus der App (z. B. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                   | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | Die Standardausrichtung der App (z. B. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                               | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Ein Array von Symbolen, die die App in verschiedenen Auflösungen darstellen.                              | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Gibt ein Standardsymbol für die App an. Bei entsprechender Konfiguration werden automatisch Symbolpfade in mehreren Größen generiert. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Ein Array von Screenshots der App, die in Installationsdialogen verwendet werden.                         | `[]`                  |
| `categories`      | `String[]`                                         | Kategorien zur Klassifizierung der App (z. B. `Finance`, `Shopping`).                                     | `[]`                  |

### Eigenschaften von `@AppProfile.Icon` {#appprofileicon-properties}

Symbole stellen Ihre App visuell in Menüs und auf Startbildschirmen dar. Die Annotation `@AppProfile.Icon` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                     | **Typ** | **Beschreibung**                                                                                         | **Standardwert** |
| ----------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                               | `String` | Der Pfad zum Symbol. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                            | **Erforderlich**  |
| `sizes`                                                                             | `String` | Eine Zeichenfolge, die eine oder mehrere Bildgrößen im Format `BreitexHöhe` angibt (z. B. `512x512`). | **Erforderlich**  |
| `type`                                                                              | `String` | Der Medientyp des Symbols (z. B. `image/png`, `image/jpeg`). Wenn nicht angegeben, wird er automatisch erkannt. | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose)     | `String` | Der Zweck des Symbols (z. B. `any`, `maskable`, `monochrome`).                                         | `""`              |

### Beispiel {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Eigenschaften von `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

Die Annotation `DefaultIcon` vereinfacht die Konfiguration von App-Symbolen, indem sie aus einem Basissymbol mehrere Größenvarianten generiert.
Sie erzeugt Symbole in den Auflösungen, die Geräte häufig anfordern.

| **Eigenschaft** | **Typ** | **Beschreibung**                                                                 | **Standardwert** |
| --------------- | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`         | `String` | Der Pfad zur Basissymbol-Datei. Dies kann eine absolute URL oder ein `ws://`-Pfad sein. | **Erforderlich**  |
| `sizes`         | `int[]`  | Ein Array der zu generierenden Größen, angegeben als Ganzzahlen (z. B. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Anforderungen an Symboldateien
Durch diese Konfiguration werden die eigentlichen Symboldateien für die App nicht automatisch generiert. Stattdessen verwendet sie die Annotation `@AppProfile.DefaultIcon`, um für jede angegebene Größe entsprechende Einträge für [`@AppProfile.Icon`](#appprofileicon-properties) zu generieren.

#### Bei Verwendung des [webserver-Protokolls](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Sie müssen eine Basisdatei `icon.png` im Ordner `static/icons` bereitstellen.
- Es wird erwartet, dass Sie zusätzliche Symbolvarianten mit den Namen `icon-144x144.png`, `icon-192x192.png` und `icon-512x512.png` bereitstellen.
- Diese Größen decken die Auflösungen ab, die Geräte häufig anfordern.

#### Bei Verwendung des [icons-Protokolls](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Es wird erwartet, dass Sie eine Basisdatei `icon.png` im Ordner `/icons` bereitstellen.
- Der Endpunkt `icons` stellt bei Bedarf dynamisch verschiedene Symbolgrößen bereit.
:::

### Eigenschaften von `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Screenshots bieten eine Vorschau der App in Installationsdialogen oder App-Stores. Die Annotation `@AppProfile.Screenshot` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                                  | **Typ** | **Beschreibung**                                                                                              | **Standardwert** |
| ------------------------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                                             | `String` | Der Pfad zum Screenshot. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                             | **Erforderlich**  |
| `sizes`                                                                                           | `String` | Eine Zeichenfolge, die eine oder mehrere Bildgrößen im Format `BreitexHöhe` angibt (z. B. `1080x1920`).    | **Erforderlich**  |
| `type`                                                                                            | `String` | Der Medientyp des Screenshots (z. B. `image/png`, `image/jpeg`). Wenn nicht angegeben, wird er automatisch erkannt. | `""`              |
| `label`                                                                                           | `String` | Eine beschreibende Bezeichnung für den Screenshot.                                                            | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)     | `String` | Der Formfaktor des Screenshots (z. B. `narrow`, `wide`).                                                     | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)          | `String` | Die Plattform, für die der Screenshot vorgesehen ist (z. B. `ios`, `android`).                               | `""`              |

### Beispiel {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
