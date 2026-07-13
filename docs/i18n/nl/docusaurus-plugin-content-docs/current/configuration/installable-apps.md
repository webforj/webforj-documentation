---
title: Installable Apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 60a6bf616536e9c202af684e9a505af6
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

De `@AppProfile` annotatie in webforJ stelt je in staat om je app installeerbaar te maken op ondersteunde platforms.
Installeerbare webapps integreren met het besturingssysteem van het apparaat.
Wanneer geïnstalleerd, verschijnen ze op het startscherm of in het app-menu, vergelijkbaar met native apps.
Om dit te bereiken, moeten bepaalde metadata zoals naam, beschrijving en iconen worden verstrekt.
Deze details helpen het besturingssysteem om de app te identificeren en weer te geven.

:::info Vereiste Beveiligde Oorsprong
Om een app installeerbaar te maken, moet deze worden aangeboden vanuit een beveiligde oorsprong, zoals `https`.
Browsers wijzen installatiepogingen op onveilige oorsprongen af. Deze regel is echter niet van toepassing wanneer de app lokaal vanaf `localhost` tijdens de ontwikkeling wordt aangeboden.

<!-- vale off -->
Voor meer details over veilige contexten en hun belang, zie de [Secure Contexts MDN documentatie](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Browserondersteuning {#browser-support}

Ondersteuning voor het installeren van een webapp varieert per browser en per platform.

### Desktop {#browser-support-desktop}

- **Chromium-browsers** (Chrome, Edge, Opera, Brave en anderen) installeren elke app die een manifestbestand levert op alle ondersteunde desktopbesturingssystemen.
- **Safari** ondersteunt **Bestand → Voeg toe aan Dock** op macOS Sonoma (Safari 17) en later. De flow werkt voor elke webapp, met of zonder manifestbestand.
- **Firefox** ondersteunt het installeren van webapps vanuit een manifestbestand op desktop niet.

### Mobiel {#browser-support-mobile}

- Op **Android** ondersteunen Chrome, Edge, Firefox, Opera en Samsung Internet allemaal het installeren van webapps.
- Op **iOS 16.3 en eerder** kunnen webapps alleen vanuit Safari worden geïnstalleerd (**Delen → Voeg toe aan startscherm**).
- Op **iOS 16.4 en later** kunnen webapps worden geïnstalleerd vanuit het Deelnemen-menu in Safari, Chrome, Edge, Firefox en Orion.

## `@AppProfile` annotatie {#appprofile-annotation}

De `@AppProfile` annotatie wordt toegepast op de hoofdapp-klasse en vereist minimale configuratie. Ten minste moet je het volgende opgeven:

- **name**: De volledige naam van de app.
- **shortName**: Een beknopte versie van de naam voor gebruik in contexten met beperkte ruimte.

Extra optionele eigenschappen stellen je in staat om het uiterlijk en gedrag van de app aan te passen.

Wanneer de `@AppProfile` annotatie aanwezig is, zorgt webforJ voor:

- Automatisch opzetten van de noodzakelijke meta-tags.
- Genereert een [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Dien gerelateerde middelen zoals iconen en screenshots.

### Voorbeeld: Toepassen van `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is een eenvoudige banktoepassing gebouwd met webforJ",
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

## `@AppProfile` eigenschappen {#appprofile-properties}

De volgende tabel geeft alle eigenschappen weer die door de `@AppProfile` annotatie worden ondersteund:

| **Eigenschap**     | **Type**                                           | **Beschrijving**                                                                                           | **Standaardwaarde**     |
| ------------------ | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ------------------------ |
| `name`             | `String`                                          | De volledige naam van de app, weergegeven in app-menu's en installatie-dialogen.                         | **Verplicht**            |
| `shortName`        | `String`                                          | Een korte versie van de naam, gebruikt in contexten met beperkte ruimte. Mag niet meer dan 12 tekens bevatten. | **Verplicht**            |
| `description`      | `String`                                          | Een beknopte beschrijving van de app, weergegeven tijdens de installatie en in app-instellingen.           | `""`                     |
| `themeColor`       | `String`                                          | De themakleur voor de app, toegepast op de browserinterface wanneer de app wordt gestart.                 | `"#ffffff"`              |
| `backgroundColor`  | `String`                                          | De initiële achtergrondkleur voor de app tijdens het laden.                                               | `"#f8fafc"`              |
| `startUrl`         | `String`                                          | De URL die geopend moet worden wanneer de app wordt gestart.                                              | `"."`                    |
| `display`          | `Display` **_Enum_**                             | De weergavemodus van de app (bijv. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`             |
| `orientation`      | `Orientation` **_Enum_**                         | De standaardoriëntatie van de app (bijv. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`                |
| `icons`            | [`Icon[]`](#appprofileicon-properties)           | Een array van iconen die de app op verschillende resoluties vertegenwoordigen.                            | `[]`                     |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | Geeft een standaardicoon voor de app op. Genereert automatisch pad naar iconen in meerdere formaten als dat is geconfigureerd. | `icons://icon.png`      |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | Een array van screenshots voor de app, gebruikt in installatie-dialogen.                                  | `[]`                     |
| `categories`       | `String[]`                                       | Categorieën om de app te classificeren (bijv. `Financiën`, `Winkelen`).                                 | `[]`                     |

### `@AppProfile.Icon` eigenschappen {#appprofileicon-properties}

Iconen definiëren de visuele representatie van je app in menu's en startschermen. De `@AppProfile.Icon` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                     | **Type** | **Beschrijving**                                                                                        | **Standaardwaarde** |
| ---------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | -------------------- |
| `src`                                                                             | `String` | Het pad naar het icoon. Dit kan een absolute URL zijn of een `ws://` pad.                             | **Verplicht**        |
| `sizes`                                                                           | `String` | Een string die een of meer maten van de afbeelding specificeert in het formaat `BreedtexHoogte` (bijv. `512x512`). | **Verplicht**        |
| `type`                                                                            | `String` | Het mediatype van het icoon (bijv. `image/png`, `image/jpeg`). Als niet opgegeven, wordt het gedetecteerd | `""`                 |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Het doel van het icoon (bijv. `any`, `maskable`, `monochrome`).                                       | `""`                 |

### Voorbeeld {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` eigenschappen {#appprofiledefaulticon-properties}

De `DefaultIcon` annotatie vereenvoudigt de configuratie van app-iconen door meerdere formaatvarianten vanuit een basisicoon te genereren.
Het produceert iconen op de resoluties die apparaten doorgaans aanvragen.

| **Eigenschap** | **Type** | **Beschrijving**                                                                 | **Standaardwaarde** |
| -------------- | -------- | ------------------------------------------------------------------------------- | -------------------- |
| `value`        | `String` | Het pad naar het basisicoonbestand. Dit kan een absolute URL zijn of een `ws://` pad. | **Verplicht**        |
| `sizes`        | `int[]`  | Een array van maten die gegenereerd moeten worden, gespecificeerd als gehele getallen (bijv. `{144, 192, 512}`). | `{144, 192, 512}`   |

:::info Vereisten voor Icoonbestanden
Deze configuratie genereert de daadwerkelijke icoonbestanden voor de app niet automatisch. In plaats daarvan gebruikt het de `@AppProfile.DefaultIcon` annotatie om overeenkomstige [`@AppProfile.Icon`](#appprofileicon-properties) items voor elke opgegeven grootte te genereren.

#### Als je het [webserverprotocol](../managing-resources/assets-protocols#the-webserver-protocol) gebruikt {#if-using-the-webserver-protocol}
- Je moet een basis `icon.png` bestand in de `static/icons` map bieden.
- Je wordt verwacht om extra icoonvariaties op te nemen die `icon-144x144.png`, `icon-192x192.png` en `icon-512x512.png` worden genoemd.
- Deze specifieke maten dekken de resoluties die apparaten doorgaans aanvragen.

#### Als je het [iconenprotocol](../managing-resources/assets-protocols#the-icons-protocol) gebruikt {#if-using-the-icons-protocol}
- Je wordt verwacht om een basis `icon.png` bestand in de `/icons` map te bieden.
- Het `icons` eindpunt biedt dynamisch verschillende icoongroottes op aanvraag wanneer ze worden aangevraagd.
:::

### `@AppProfile.Screenshot` eigenschappen {#appprofilescreenshot-properties}

Screenshots bieden een preview van de app in installatie-dialogen of app stores. De `@AppProfile.Screenshot` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                          | **Type** | **Beschrijving**                                                                                             | **Standaardwaarde** |
| --------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | -------------------- |
| `src`                                                                                   | `String` | Het pad naar de screenshot. Dit kan een absolute URL zijn of een `ws://` pad.                               | **Verplicht**        |
| `sizes`                                                                                 | `String` | Een string die een of meer maten van de afbeelding specificeert in het formaat `BreedtexHoogte` (bijv. `1080x1920`). | **Verplicht**        |
| `type`                                                                                  | `String` | Het mediatype van de screenshot (bijv. `image/png`, `image/jpeg`). Als niet opgegeven, wordt het gedetecteerd | `""`                 |
| `label`                                                                                 | `String` | Een beschrijvende label voor de screenshot.                                                                   | `""`                 |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | De vormfactor van de screenshot (bijv. `narrow`, `wide`).                                                   | `""`                 |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform) | `String` | Het platform waarvoor de screenshot bedoeld is (bijv. `ios`, `android`).                                   | `""`                 |

### Voorbeeld {#example-1}

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
