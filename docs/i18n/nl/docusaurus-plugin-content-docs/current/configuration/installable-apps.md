---
title: Installeerbare apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

Met de annotatie `@AppProfile` in webforJ kun je je app installeerbaar maken op ondersteunde platforms.
Installeerbare webapps integreren met het besturingssysteem van het apparaat.
Na installatie verschijnen ze op het startscherm of in het appmenu, net als native apps.
Hiervoor moet je bepaalde metadata opgeven, zoals de naam, beschrijving en pictogrammen.
Aan de hand van deze gegevens kan het besturingssysteem de app herkennen en weergeven.

:::info Vereiste voor een beveiligde oorsprong
Een app moet vanaf een beveiligde oorsprong, zoals `https`, worden aangeboden om installeerbaar te zijn.
Browsers wijzen installatiepogingen vanaf onbeveiligde oorsprongen af. Deze regel geldt echter niet wanneer je de app tijdens de ontwikkeling lokaal aanbiedt via `localhost`.

<!-- vale off -->
Raadpleeg voor meer informatie over beveiligde contexten en het belang ervan de [MDN-documentatie over beveiligde contexten](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## Browserondersteuning {#browser-support}

Ondersteuning voor het installeren van een webapp verschilt per browser en platform.

### Desktop {#browser-support-desktop}

- **Chromium-browsers** (Chrome, Edge, Opera, Brave en andere) kunnen op alle ondersteunde desktopbesturingssystemen elke app installeren die een manifestbestand bevat.
- **Safari** ondersteunt **Archief → Voeg toe aan Dock** op macOS Sonoma (Safari 17) en hoger. Dit werkt voor elke webapp, met of zonder manifestbestand.
- **Firefox** ondersteunt op desktop geen installatie van webapps vanuit een manifestbestand.

### Mobiel {#browser-support-mobile}

- Op **Android** ondersteunen Chrome, Edge, Firefox, Opera en Samsung Internet allemaal de installatie van webapps.
- Op **iOS 16.3 en ouder** kunnen webapps alleen vanuit Safari worden geïnstalleerd (**Deel → Voeg toe aan beginscherm**).
- Op **iOS 16.4 en hoger** kunnen webapps worden geïnstalleerd via het Deel-menu in Safari, Chrome, Edge, Firefox en Orion.

## De annotatie `@AppProfile` {#appprofile-annotation}

De annotatie `@AppProfile` wordt toegepast op de hoofdklasse van de app en vereist minimale configuratie. Je moet ten minste het volgende opgeven:

- **name**: De volledige naam van de app.
- **shortName**: Een beknopte versie van de naam voor situaties waarin weinig ruimte beschikbaar is.

Met aanvullende optionele eigenschappen kun je het uiterlijk en gedrag van de app aanpassen.

Wanneer de annotatie `@AppProfile` aanwezig is, doet webforJ het volgende:

- Stelt automatisch de benodigde metatags in.
- Genereert een [webapp-manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Biedt gerelateerde resources aan, zoals pictogrammen en schermafbeeldingen.

### Voorbeeld: `@AppProfile` toepassen {#example-applying-appprofile}

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

## Eigenschappen van `@AppProfile` {#appprofile-properties}

In de volgende tabel staan alle eigenschappen die door de annotatie `@AppProfile` worden ondersteund:

| **Eigenschap**     | **Type**                                           | **Beschrijving**                                                                                           | **Standaardwaarde** |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | De volledige naam van de app, weergegeven in appmenu's en installatiedialogen.                            | **Verplicht**         |
| `shortName`       | `String`                                           | Een korte versie van de naam, gebruikt wanneer weinig ruimte beschikbaar is. Mag niet langer zijn dan 12 tekens. | **Verplicht**         |
| `description`     | `String`                                           | Een korte beschrijving van de app, weergegeven tijdens de installatie en in de appinstellingen.            | `""`                  |
| `themeColor`      | `String`                                           | De themakleur van de app, toegepast op de browserinterface wanneer de app wordt gestart.                  | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | De aanvankelijke achtergrondkleur van de app tijdens het laden.                                           | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | De URL die wordt geopend wanneer de app wordt gestart.                                                    | `"."`                 |
| `display`         | `Display` **_Enum_**                               | De weergavemodus van de app (bijv. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | De standaardoriëntatie van de app (bijv. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Een array met pictogrammen die de app bij verschillende resoluties vertegenwoordigen.                     | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Hiermee geef je een standaardpictogram voor de app op. Indien geconfigureerd, worden automatisch pictogrampaden in meerdere formaten gegenereerd. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Een array met schermafbeeldingen van de app, gebruikt in installatiedialogen.                              | `[]`                  |
| `categories`      | `String[]`                                         | Categorieën waarmee de app wordt ingedeeld (bijv. `Finance`, `Shopping`).                                 | `[]`                  |

### Eigenschappen van `@AppProfile.Icon` {#appprofileicon-properties}

Pictogrammen geven de app visueel weer in menu's en op startschermen. De annotatie `@AppProfile.Icon` ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                     | **Type** | **Beschrijving**                                                                                         | **Standaardwaarde** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | Het pad naar het pictogram. Dit kan een absolute URL of een `ws://`-pad zijn.                           | **Verplicht**     |
| `sizes`                                                                          | `String` | Een tekenreeks die een of meer afbeeldingsformaten opgeeft in de vorm `WidthxHeight` (bijv. `512x512`). | **Verplicht**     |
| `type`                                                                           | `String` | Het mediatype van het pictogram (bijv. `image/png`, `image/jpeg`). Als dit niet is opgegeven, wordt het automatisch gedetecteerd. | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Het doel van het pictogram (bijv. `any`, `maskable`, `monochrome`).                                     | `""`              |

### Voorbeeld {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Eigenschappen van `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

Met de annotatie `DefaultIcon` kun je de configuratie van app-pictogrammen vereenvoudigen door vanuit één basispictogram meerdere formaten te genereren.
De annotatie genereert pictogrammen met resoluties die vaak door apparaten worden aangevraagd.

| **Eigenschap** | **Type** | **Beschrijving**                                                             | **Standaardwaarde** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | Het pad naar het basisbestand van het pictogram. Dit kan een absolute URL of een `ws://`-pad zijn. | **Verplicht**     |
| `sizes`      | `int[]`  | Een array met te genereren formaten, opgegeven als gehele getallen (bijv. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Vereisten voor pictogrambestanden
Deze configuratie genereert niet automatisch de daadwerkelijke pictogrambestanden voor de app. De annotatie `@AppProfile.DefaultIcon` genereert in plaats daarvan voor elk opgegeven formaat de bijbehorende vermeldingen van [`@AppProfile.Icon`](#appprofileicon-properties).

#### Bij gebruik van het [webserverprotocol](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Je moet een basisbestand `icon.png` in de map `static/icons` plaatsen.
- Je moet ook aanvullende pictogramvarianten opnemen met de namen `icon-144x144.png`, `icon-192x192.png` en `icon-512x512.png`.
- Deze specifieke formaten komen overeen met resoluties die vaak door apparaten worden aangevraagd.

#### Bij gebruik van het [icons-protocol](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Je moet een basisbestand `icon.png` in de map `/icons` plaatsen.
- Het `icons`-eindpunt levert dynamisch verschillende pictogramformaten wanneer hierom wordt gevraagd.
:::

### Eigenschappen van `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Schermafbeeldingen geven een voorbeeld van de app in installatiedialogen of appstores. De annotatie `@AppProfile.Screenshot` ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                                  | **Type** | **Beschrijving**                                                                                             | **Standaardwaarde** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | Het pad naar de schermafbeelding. Dit kan een absolute URL of een `ws://`-pad zijn.                         | **Verplicht**     |
| `sizes`                                                                                       | `String` | Een tekenreeks die een of meer afbeeldingsformaten opgeeft in de vorm `WidthxHeight` (bijv. `1080x1920`).   | **Verplicht**     |
| `type`                                                                                        | `String` | Het mediatype van de schermafbeelding (bijv. `image/png`, `image/jpeg`). Als dit niet is opgegeven, wordt het automatisch gedetecteerd. | `""`              |
| `label`                                                                                       | `String` | Een beschrijvend label voor de schermafbeelding.                                                            | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | De vormfactor van de schermafbeelding (bijv. `narrow`, `wide`).                                             | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | Het platform waarvoor de schermafbeelding bedoeld is (bijv. `ios`, `android`).                              | `""`              |

### Voorbeeld {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
