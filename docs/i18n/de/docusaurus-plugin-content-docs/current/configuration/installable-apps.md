---
title: Installable Apps
sidebar_position: 10
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 2d76df483c951a64d266380d7c96b692
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

Die `@AppProfile`-Annotation in webforJ ermöglicht es Ihnen, Ihre Anwendung auf unterstützten Plattformen installierbar zu machen. 
Installierbare Webanwendungen integrieren sich in das Betriebssystem des Geräts. 
Nach der Installation erscheinen sie auf dem Startbildschirm oder im Anwendungsmenü, ähnlich wie native Apps. 
Um dies zu erreichen, müssen bestimmte Metadaten wie Name, Beschreibung und Symbole bereitgestellt werden. 
Diese Details helfen dem Betriebssystem, die App zu identifizieren und anzuzeigen.

:::info Anforderung an sicheren Ursprung
Damit eine App installierbar ist, muss sie von einem sicheren Ursprung wie `https` bereitgestellt werden. 
Browser lehnen Installationsversuche von unsicheren Ursprüngen ab. Diese Regel gilt jedoch nicht, wenn die App lokal von `localhost` aus während der Entwicklung bereitgestellt wird.

<!-- vale off -->
Für weitere Details zu sicheren Kontexten und deren Bedeutung verweisen Sie auf die [Secure Contexts MDN-Dokumentation](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Unterstützung durch Browser {#browser-support}

Die Unterstützung für die Installation einer Webanwendung variiert je nach Browser und Plattform.

### Desktop {#browser-support-desktop}

- **Chromium-Browser** (Chrome, Edge, Opera, Brave und andere) installieren jede App, die eine Manifestdatei enthält, auf allen unterstützten Desktop-Betriebssystemen.
- **Safari** unterstützt **Ablage → Zum Dock hinzufügen** auf macOS Sonoma (Safari 17) und später. Der Ablauf funktioniert für jede Webanwendung, mit oder ohne Manifestdatei.
- **Firefox** unterstützt die Installation von Webanwendungen aus einer Manifestdatei auf dem Desktop nicht.

### Mobil {#browser-support-mobile}

- Auf **Android** unterstützen Chrome, Edge, Firefox, Opera und Samsung Internet alle die Installation von Webanwendungen.
- Auf **iOS 16.3 und früher** können Webanwendungen nur von Safari aus installiert werden (**Teilen → Zum Home-Bildschirm hinzufügen**).
- Auf **iOS 16.4 und später** können Webanwendungen aus dem Teilen-Menü in Safari, Chrome, Edge, Firefox und Orion installiert werden.

## `@AppProfile`-Annotation {#appprofile-annotation}

Die `@AppProfile`-Annotation wird auf die Hauptanwendungsklasse angewendet und erfordert nur eine minimale Konfiguration. Mindestens müssen Sie bereitstellen:

- **name**: Der vollständige Name der App.
- **shortName**: Eine kurze Version des Namens für die Verwendung in begrenzten Platzkontexten.

Zusätzliche optionale Eigenschaften ermöglichen die Anpassung des Erscheinungsbilds und Verhaltens der App.

Wenn die `@AppProfile`-Annotation vorhanden ist, erledigt webforJ:

- Richtet automatisch notwendige Meta-Tags ein.
- Generiert ein [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Stellt verwandte Ressourcen wie Symbole und Screenshots zur Verfügung.

### Beispiel: Anwendung von `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank ist eine einfache Banking-Anwendung, die mit webforJ erstellt wurde.",
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

## `@AppProfile`-Eigenschaften {#appprofile-properties}

Die folgende Tabelle listet alle Eigenschaften auf, die von der `@AppProfile`-Annotation unterstützt werden:

| **Eigenschaft**      | **Typ**                                           | **Beschreibung**                                                                                           | **Standardwert**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | Der vollständige Name der App, der in App-Menüs und Installationsdialogen angezeigt wird.                 | **Obligatorisch**     |
| `shortName`       | `String`                                           | Eine kurze Version des Namens, die in Kontexten mit begrenztem Platz verwendet wird. Sollte 12 Zeichen nicht überschreiten. | **Obligatorisch**     |
| `description`     | `String`                                           | Eine kurze Beschreibung der App, die während der Installation und in den App-Einstellungen angezeigt wird. | `""`                  |
| `themeColor`      | `String`                                           | Die Hauptfarbe für die App, die auf die Browser-Oberfläche angewendet wird, wenn die App gestartet wird.  | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | Die anfängliche Hintergrundfarbe für die App während des Ladevorgangs.                                    | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | Die URL, die geöffnet wird, wenn die App gestartet wird.                                                  | `"."`                 |
| `display`         | `Display` **_Enum_**                               | Der Anzeigemodus der App (z. B. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                   | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | Die Standardorientierung der App (z. B. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                             | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Ein Array von Symbolen, die die App in verschiedenen Auflösungen darstellen.                               | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Gibt ein Standardsymbol für die App an. Generiert automatisch Symbolpfade in mehreren Größen, falls konfiguriert. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Ein Array von Screenshots der App, die in Installationsdialogen verwendet werden.                         | `[]`                  |
| `categories`      | `String[]`                                         | Kategorien zur Klassifizierung der App (z.B. `Finanzen`, `Einkaufen`).                                    | `[]`                  |

### Eigenschaften der `@AppProfile.Icon` {#appprofileicon-properties}

Symbole definieren die visuelle Darstellung Ihrer App in Menüs und auf dem Startbildschirm. Die Annotation `@AppProfile.Icon` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                     | **Typ** | **Beschreibung**                                                                                        | **Standardwert** |
| ---------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                             | `String` | Der Pfad zum Symbol. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                           | **Obligatorisch** |
| `sizes`                                                                           | `String` | Ein String, der eine oder mehrere Größen des Bildes im Format `BreitexHöhe` (z.B. `512x512`) angibt. | **Obligatorisch** |
| `type`                                                                            | `String` | Der Medientyp des Symbols (z.B. `image/png`, `image/jpeg`). Wenn nicht bereitgestellt, wird er erkannt | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Der Zweck des Symbols (z.B. `any`, `maskable`, `monochrome`).                                         | `""`              |

### Beispiel {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Eigenschaften des `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

Die Annotation `DefaultIcon` vereinfacht die Konfiguration von App-Symbolen, indem sie mehrere Größenvarianten aus einem Basis-Symbol generiert.
Sie erstellt Symbole in den Auflösungen, die von den Geräten häufig angefordert werden.

| **Eigenschaft** | **Typ** | **Beschreibung**                                                              | **Standardwert** |
| ---------------- | ------- | ---------------------------------------------------------------------------- | ----------------- |
| `value`          | `String` | Der Pfad zur Basis-Symbol-Datei. Dies kann eine absolute URL oder ein `ws://`-Pfad sein. | **Obligatorisch** |
| `sizes`          | `int[]` | Ein Array von Größen, die generiert werden sollen, angegeben als Ganzzahlen (z.B. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Anforderungen an Symbol-Dateien
Diese Konfiguration generiert nicht automatisch die tatsächlichen Symbol-Dateien für die App. Stattdessen verwendet sie die Annotation `@AppProfile.DefaultIcon`, um entsprechende [`@AppProfile.Icon`](#appprofileicon-properties)-Einträge für jede angegebene Größe zu generieren.

#### Wenn das [Webserver-Protokoll](../managing-resources/assets-protocols#the-webserver-protocol) verwendet wird {#if-using-the-webserver-protocol}
- Sie müssen eine Basis-Symboldatei `icon.png` im Ordner `static/icons` bereitstellen.
- Es wird erwartet, dass Sie zusätzliche Symbolvariationen namens `icon-144x144.png`, `icon-192x192.png` und `icon-512x512.png` einfügen.
- Diese spezifischen Größen decken die Auflösungen ab, die von den Geräten häufig angefordert werden.

#### Wenn das [Icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol) verwendet wird {#if-using-the-icons-protocol}
- Es wird erwartet, dass Sie eine Basis-Symboldatei `icon.png` im Ordner `/icons` bereitstellen.
- Der `icons`-Endpunkt liefert dynamisch verschiedene Symbolgrößen auf Nachfrage, wenn sie angefordert werden.
:::

### Eigenschaften der `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Screenshots bieten einen Überblick über die App in Installationsdialogen oder App-Stores. Die Annotation `@AppProfile.Screenshot` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                                  | **Typ** | **Beschreibung**                                                                                             | **Standardwert** |
| ----------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                           | `String` | Der Pfad zum Screenshot. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                           | **Obligatorisch** |
| `sizes`                                                                                         | `String` | Ein String, der eine oder mehrere Größen des Bildes im Format `BreitexHöhe` (z.B. `1080x1920`) angibt.     | **Obligatorisch** |
| `type`                                                                                          | `String` | Der Medientyp des Screenshots (z.B. `image/png`, `image/jpeg`). Wenn nicht bereitgestellt, wird er erkannt | `""`              |
| `label`                                                                                         | `String` | Ein beschreibendes Label für den Screenshot.                                                                 | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Der Formfaktor des Screenshots (z.B. `narrow`, `wide`).                                                   | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | Die Plattform, für die der Screenshot bestimmt ist (z.B. `ios`, `android`).                                | `""`              |

### Beispiel {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<div class="videos-container">
  <video controls>
    <source src="/video/install-android.mp4" type="video/mp4"/>
  </video>
</div>
