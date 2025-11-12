---
title: Installierbare Apps
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

Die `@AppProfile`-Annotation in webforJ ermöglicht es Ihnen, Ihre App auf unterstützten Plattformen installierbar zu machen. 
Installierbare Web-Apps integrieren sich nahtlos in das Betriebssystem des Geräts. 
Wenn sie installiert sind, erscheinen sie auf dem Startbildschirm oder im App-Menü, ähnlich wie native Apps. 
Um dies zu erreichen, müssen bestimmte Metadaten wie Name, Beschreibung und Icons bereitgestellt werden. 
Diese Details helfen dem Betriebssystem, die App zu identifizieren und anzuzeigen.

:::info Anforderung für sichere Ursprünge
Damit eine App installierbar ist, muss sie von einem sicheren Ursprung, wie `https`, bereitgestellt werden. 
Diese Anforderung gewährleistet, dass die App die für die Installation erforderlichen Sicherheitsstandards erfüllt. Diese Regel gilt jedoch nicht, wenn die App lokal von `localhost` während der Entwicklung bereitgestellt wird.

<!-- vale off -->
Für weitere Details zu sicheren Kontexten und deren Bedeutung verweisen Sie bitte auf die [Dokumentation zu sicheren Kontexten MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile`-Annotation {#appprofile-annotation}

Die `@AppProfile`-Annotation wird auf die Haupt-App-Klasse angewendet und erfordert minimale Konfiguration. Mindestens müssen Sie folgende Informationen bereitstellen:

- **name**: Der vollständige Name der App.
- **shortName**: Eine prägnante Version des Namens zur Verwendung in Kontexten mit begrenztem Platz.

Zusätzliche optionale Eigenschaften ermöglichen die Anpassung des Erscheinungsbilds und des Verhaltens der App.

Wenn die `@AppProfile`-Annotation vorhanden ist, richtet webforJ:

- Notwendige Meta-Tags automatisch ein.
- Generiert ein [Webanwendungsmanifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Stellt verwandte Ressourcen wie Icons und Screenshots bereit.

### Beispiel: Anwenden von `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank ist eine einfache Banking-Anwendung, die mit webforJ erstellt wurde",
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

Die folgende Tabelle listet alle von der `@AppProfile`-Annotation unterstützten Eigenschaften auf:

| **Eigenschaft**      | **Typ**                                           | **Beschreibung**                                                                                           | **Standardwert**     |
| --------------------- | -------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`                | `String`                                           | Der vollständige Name der App, der in App-Menüs und Installationsdialogen angezeigt wird.                  | **Obligatorisch**     |
| `shortName`           | `String`                                           | Eine kurze Version des Namens, die in Kontexten mit begrenztem Platz verwendet wird. Sollte 12 Zeichen nicht überschreiten. | **Obligatorisch**     |
| `description`         | `String`                                           | Eine kurze Beschreibung der App, die während der Installation und in den App-Einstellungen angezeigt wird. | `""`                  |
| `themeColor`          | `String`                                           | Die Farbgebung der App, die auf der Browser-Oberfläche angewendet wird, wenn die App gestartet wird.      | `"#ffffff"`           |
| `backgroundColor`     | `String`                                           | Die anfängliche Hintergrundfarbe der App während des Ladens.                                              | `"#f8fafc"`           |
| `startUrl`            | `String`                                           | Die URL, die geöffnet werden soll, wenn die App gestartet wird.                                          | `"."`                 |
| `display`             | `Display` **_Enum_**                               | Der Anzeigemodus der App (z.B. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                   | `STANDALONE`          |
| `orientation`         | `Orientation` **_Enum_**                           | Die Standardausrichtung der App (z.B. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                               | `NATURAL`             |
| `icons`               | [`Icon[]`](#appprofileicon-properties)             | Ein Array von Icons, die die App in verschiedenen Auflösungen darstellen.                                  | `[]`                  |
| `defaultIcon`         | [`DefaultIcon`](#appprofiledefaulticon-properties) | Gibt ein Standard-Icon für die App an. Generiert automatisch Icon-Pfade in mehreren Größen, wenn konfiguriert. | `icons://icon.png`   |
| `screenshots`         | [`Screenshot[]`](#appprofilescreenshot-properties) | Ein Array von Screenshots für die App, die in Installationsdialogen verwendet werden.                     | `[]`                  |
| `categories`          | `String[]`                                         | Kategorien zur Klassifizierung der App (z.B. `Finanzen`, `Einkaufen`).                                    | `[]`                  |

### `@AppProfile.Icon`-Eigenschaften {#appprofileicon-properties}

Icons definieren die visuelle Darstellung Ihrer App in Menüs und auf Startbildschirmen. Die Annotation `@AppProfile.Icon` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                     | **Typ** | **Beschreibung**                                                                                           | **Standardwert** |
| ---------------------------------------------------------------------------------- | -------- | ---------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                              | `String` | Der Pfad zum Icon. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                                    | **Obligatorisch** |
| `sizes`                                                                            | `String` | Ein String, der eine oder mehrere Größen des Bildes im Format `WidthxHeight` (z.B. `512x512`) angibt.       | **Obligatorisch** |
| `type`                                                                             | `String` | Der MIME-Typ des Icons (z.B. `image/png`, `image/jpeg`). Wenn nicht angegeben, wird er erkannt.            | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Der Zweck des Icons (z.B. `any`, `maskable`, `monochrome`).                                                 | `""`              |

### Beispiel {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon`-Eigenschaften {#appprofiledefaulticon-properties}

Die `DefaultIcon`-Annotation vereinfacht die Konfiguration von App-Icons, indem sie aus einem Basis-Icon mehrere Größenvarianten generiert.
Dies ist besonders nützlich, um die Kompatibilität auf Geräten mit unterschiedlichen Auflösungen sicherzustellen.

| **Eigenschaft** | **Typ** | **Beschreibung**                                                                 | **Standardwert** |
| ---------------- | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`          | `String` | Der Pfad zur Basis-Icon-Datei. Dies kann eine absolute URL oder ein `ws://`-Pfad sein. | **Obligatorisch** |
| `sizes`          | `int[]`  | Ein Array von Größen, die generiert werden sollen, angegeben als Ganzzahlen (z.B. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Anforderungen an die Icon-Dateien
Diese Konfiguration generiert nicht automatisch die tatsächlichen Icon-Dateien für die App. Stattdessen verwendet sie die Annotation `@AppProfile.DefaultIcon`, um entsprechende [`@AppProfile.Icon`](#appprofileicon-properties) Einträge für jede angegebene Größe zu generieren.

#### Wenn das [Webserver-Protokoll](../managing-resources/assets-protocols#the-webserver-protocol) verwendet wird {#if-using-the-webserver-protocol}
- Sie müssen eine Basis-`icon.png`-Datei im `static/icons`-Ordner bereitstellen.
- Es wird erwartet, dass Sie zusätzliche Icon-Variationen mit den Namen `icon-144x144.png`, `icon-192x192.png` und `icon-512x512.png` einfügen.
- Diese spezifischen Größen gewährleisten die Kompatibilität mit verschiedenen Geräten und Auflösungen.

#### Wenn das [Icons-Protokoll](../managing-resources/assets-protocols#the-icons-protocol) verwendet wird {#if-using-the-icons-protocol}

- Sie müssen eine Basis-`icon.png`-Datei im `/icons`-Ordner bereitstellen.
- Der `icons`-Endpunkt stellt dynamisch verschiedene Icon-Größen auf Anfrage bereit, wenn sie angefordert werden.
:::

### `@AppProfile.Screenshot`-Eigenschaften {#appprofilescreenshot-properties}

Screenshots bieten eine Vorschau der App in Installationsdialogen oder App-Stores. Die Annotation `@AppProfile.Screenshot` unterstützt die folgenden Eigenschaften:

| **Eigenschaft**                                                                                  | **Typ** | **Beschreibung**                                                                                             | **Standardwert** |
| ------------------------------------------------------------------------------------------------ | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                             | `String` | Der Pfad zum Screenshot. Dies kann eine absolute URL oder ein `ws://`-Pfad sein.                            | **Obligatorisch** |
| `sizes`                                                                                           | `String` | Ein String, der eine oder mehrere Größen des Bildes im Format `WidthxHeight` (z.B. `1080x1920`) angibt.    | **Obligatorisch** |
| `type`                                                                                            | `String` | Der MIME-Typ des Screenshots (z.B. `image/png`, `image/jpeg`). Wenn nicht angegeben, wird er erkannt.      | `""`              |
| `label`                                                                                           | `String` | Ein beschreibendes Label für den Screenshot.                                                                 | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)     | `String` | Der Formfaktor des Screenshots (z.B. `narrow`, `wide`).                                                    | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)          | `String` | Die Plattform, für die der Screenshot bestimmt ist (z.B. `ios`, `android`).                                 | `""`              |

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
