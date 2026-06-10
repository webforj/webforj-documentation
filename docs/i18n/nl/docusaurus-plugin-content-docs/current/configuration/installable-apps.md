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

De `@AppProfile` annotatie in webforJ stelt je in staat om je app installeerbaar te maken op ondersteunde platforms. 
Installeerbare webapps integreren met het besturingssysteem van het apparaat. 
Wanneer geïnstalleerd, verschijnen ze op het startscherm of in het app-menu, vergelijkbaar met native apps. 
Om dit te bereiken, moeten bepaalde metadata zoals naam, beschrijving en iconen worden verstrekt. 
Deze details helpen het besturingssysteem de app te identificeren en weer te geven.

:::info Vereiste voor Veilige Oorsprong
Voor een app om installeerbaar te zijn, moet deze worden geleverd vanaf een veilige oorsprong, zoals `https`. 
Browsers weigeren installatiepogingen op onveilige oorsprongen. Deze regel is echter niet van toepassing wanneer de app lokaal vanaf `localhost` tijdens de ontwikkeling wordt geleverd.

<!-- vale off -->
Voor meer details over veilige contexten en hun belang, zie de [Veilige Contexten MDN-documentatie](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Browserondersteuning {#browser-support}

De ondersteuning voor het installeren van een webapp varieert per browser en per platform.

### Desktop {#browser-support-desktop}

- **Chromium-browsers** (Chrome, Edge, Opera, Brave en anderen) installeren elke app die een manifestbestand bevat op alle ondersteunde desktop-besturingssystemen.
- **Safari** ondersteunt **Bestand → Toevoegen aan Dock** op macOS Sonoma (Safari 17) en later. De flow werkt voor elke webapp, met of zonder een manifestbestand.
- **Firefox** ondersteunt het installeren van webapps vanuit een manifestbestand op desktop niet.

### Mobiel {#browser-support-mobile}

- Op **Android** ondersteunen Chrome, Edge, Firefox, Opera en Samsung Internet allemaal het installeren van webapps.
- Op **iOS 16.3 en eerder** kunnen webapps alleen vanuit Safari worden geïnstalleerd (**Delen → Voeg toe aan startscherm**).
- Op **iOS 16.4 en later** kunnen webapps worden geïnstalleerd vanuit het Delen-menu in Safari, Chrome, Edge, Firefox en Orion.

## `@AppProfile` annotatie {#appprofile-annotation}

De `@AppProfile` annotatie wordt toegepast op de hoofdapp-klasse en vereist minimale configuratie. Minimaal moet je voorzien in:

- **naam**: De volledige naam van de app.
- **shortName**: Een beknopte versie van de naam voor gebruik in contexten met beperkte ruimte.

Daarnaast kunnen optionele eigenschappen de uitstraling en het gedrag van de app aanpassen.

Wanneer de `@AppProfile` annotatie aanwezig is, doet webforJ:

- Stelt automatisch de benodigde meta-tags in.
- Genereert een [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Levert gerelateerde bronnen zoals iconen en schermafbeeldingen.

### Voorbeeld: Toepassen van `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is een eenvoudige bankapplicatie gebouwd met webforJ",
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

De volgende tabel bevat alle eigenschappen die worden ondersteund door de `@AppProfile` annotatie:

| **Eigenschap**     | **Type**                                           | **Beschrijving**                                                                                           | **Standaardwaarde**     |
| ------------------ | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ------------------------ |
| `name`             | `String`                                           | De volledige naam van de app, weergegeven in app-menu's en installatie-dialogen.                          | **Verplicht**            |
| `shortName`        | `String`                                           | Een korte versie van de naam, gebruikt in contexten met beperkte ruimte. Mag niet meer dan 12 tekens zijn. | **Verplicht**            |
| `description`      | `String`                                           | Een korte beschrijving van de app, weergegeven tijdens de installatie en in app-instellingen.              | `""`                     |
| `themeColor`       | `String`                                           | De themakleur voor de app, toegepast op de browserinterface wanneer de app wordt gestart.                  | `"#ffffff"`              |
| `backgroundColor`  | `String`                                           | De initiële achtergrondkleur voor de app tijdens het laden.                                                | `"#f8fafc"`              |
| `startUrl`         | `String`                                           | De URL die geopend moet worden wanneer de app wordt gestart.                                              | `"."`                    |
| `display`          | `Display` **_Enum_**                              | De weergavemodus van de app (bijv. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                               | `STANDALONE`             |
| `orientation`      | `Orientation` **_Enum_**                          | De standaardoriëntatie van de app (bijv. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`                |
| `icons`            | [`Icon[]`](#appprofileicon-properties)           | Een array van iconen die de app op verschillende resoluties vertegenwoordigen.                            | `[]`                     |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties)| Geeft een standaardicoon voor de app op. Genereert automatisch iconpaden in meerdere formaten als het is geconfigureerd. | `icons://icon.png`      |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties)| Een array van schermafbeeldingen voor de app, gebruikt in installatie-dialogen.                           | `[]`                     |
| `categories`       | `String[]`                                        | Categorieën om de app te classificeren (bijv. `Financiën`, `Winkelen`).                                  | `[]`                     |

### `@AppProfile.Icon` eigenschappen {#appprofileicon-properties}

Iconen definiëren de visuele representatie van je app in menu's en startschermen. De `@AppProfile.Icon` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                     | **Type** | **Beschrijving**                                                                                          | **Standaardwaarde** |
| --------------------------------------------------------------------------------- | -------- | -------------------------------------------------------------------------------------------------------- | ------------------- |
| `src`                                                                            | `String` | Het pad naar het icoon. Dit kan een absolute URL zijn of een `ws://` pad.                                   | **Verplicht**       |
| `sizes`                                                                          | `String` | Een tekenreeks die een of meer afmetingen van de afbeelding specificeert in het formaat `BreedtexHoogte` (bijv. `512x512`). | **Verplicht**       |
| `type`                                                                           | `String` | Het mediatype van het icoon (bijv. `image/png`, `image/jpeg`). Als het niet wordt opgegeven, wordt het gedetecteerd  | `""`                |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Het doel van het icoon (bijv. `any`, `maskable`, `monochrome`).                                          | `""`                |

### Voorbeeld {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` eigenschappen {#appprofiledefaulticon-properties}

De `DefaultIcon` annotatie vereenvoudigt de configuratie van app-iconen door meerdere groottevarianten te genereren vanuit een basisicoon.
Het produceert iconen in de resoluties die apparaten doorgaans aanvragen.

| **Eigenschap** | **Type** | **Beschrijving**                                                               | **Standaardwaarde** |
| -------------- | -------- | ------------------------------------------------------------------------------- | ------------------- |
| `value`        | `String` | Het pad naar het basisicoonbestand. Dit kan een absolute URL zijn of een `ws://` pad. | **Verplicht**       |
| `sizes`        | `int[]`  | Een array van maten om te genereren, gespecificeerd als gehele getallen (bijv. `{144, 192, 512}`). | `{144, 192, 512}`  |

:::info Vereisten voor Icoonbestanden
Deze configuratie genereert de feitelijke icoonbestanden voor de app niet automatisch. In plaats daarvan gebruikt het de annotatie `@AppProfile.DefaultIcon` om de bijbehorende [`@AppProfile.Icon`](#appprofileicon-properties) vermeldingen voor elke opgegeven grootte te genereren.

#### Als je het [webserverprotocol](../managing-resources/assets-protocols#the-webserver-protocol) gebruikt {#if-using-the-webserver-protocol}
- Je moet een basisbestand `icon.png` in de map `static/icons` leveren.
- Je wordt verwacht extra iconvariaties op te nemen met de namen `icon-144x144.png`, `icon-192x192.png` en `icon-512x512.png`.
- Deze specifieke maten dekken de resoluties die apparaten doorgaans aanvragen.

#### Als je het [iconenprotocol](../managing-resources/assets-protocols#the-icons-protocol) gebruikt {#if-using-the-icons-protocol}

- Je wordt verwacht een basisbestand `icon.png` in de map `/icons` te leveren.
- De `icons` endpoint biedt dynamisch verschillende iconformaten op verzoek wanneer ze worden aangevraagd.
:::

### `@AppProfile.Screenshot` eigenschappen {#appprofilescreenshot-properties}

Schermafbeeldingen geven een voorvertoning van de app in installatie-dialogen of app-winkels. De `@AppProfile.Screenshot` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                                  | **Type** | **Beschrijving**                                                                                          | **Standaardwaarde** |
| ----------------------------------------------------------------------------------------------- | -------- | -------------------------------------------------------------------------------------------------------- | ------------------- |
| `src`                                                                                           | `String` | Het pad naar de schermafbeelding. Dit kan een absolute URL zijn of een `ws://` pad.                      | **Verplicht**       |
| `sizes`                                                                                         | `String` | Een tekenreeks die een of meer afmetingen van de afbeelding specificeert in het formaat `BreedtexHoogte` (bijv. `1080x1920`). | **Verplicht**       |
| `type`                                                                                          | `String` | Het mediatype van de schermafbeelding (bijv. `image/png`, `image/jpeg`). Als het niet wordt opgegeven, wordt het gedetecteerd | `""`                |
| `label`                                                                                         | `String` | Een beschrijvende label voor de schermafbeelding.                                                        | `""`                |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | De vormfactor van de schermafbeelding (bijv. `narrow`, `wide`).                                         | `""`                |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)       | `String` | Het platform waarvoor de schermafbeelding bedoeld is (bijv. `ios`, `android`).                          | `""`                |

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
