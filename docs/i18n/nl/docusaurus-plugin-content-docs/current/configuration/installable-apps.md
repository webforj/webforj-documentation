---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

De `@AppProfile` annotatie in webforJ stelt je in staat om je app installeerbaar te maken op ondersteunde platforms. 
Installeerbare webapps integreren naadloos met het besturingssysteem van het apparaat. 
Wanneer geïnstalleerd, verschijnen ze op het startscherm of in het app-menu, vergelijkbaar met native apps. 
Om dit te bereiken, moeten bepaalde metadata zoals naam, beschrijving en iconen worden verstrekt. 
Deze details helpen het besturingssysteem om de app te identificeren en weer te geven.

:::info Vereiste voor Veilige Oorsprong
Voor een app om installeerbaar te zijn, moet deze vanaf een veilige oorsprong worden geleverd, zoals `https`. 
Deze vereiste zorgt ervoor dat de app voldoet aan de noodzakelijke beveiligingsnormen voor installatie. Deze regel geldt echter niet wanneer de app lokaal vanaf `localhost` tijdens de ontwikkeling wordt aangeboden.

<!-- vale off -->
Voor meer details over veilige contexten en hun belang, raadpleeg de [Secure Contexts MDN-documentatie](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile` annotatie {#appprofile-annotation}

De `@AppProfile` annotatie wordt toegepast op de hoofdapp-klasse en vereist minimale configuratie. Minimaal moet je voorzien in:

- **naam**: De volledige naam van de app.
- **shortName**: Een beknopte versie van de naam voor gebruik in contexten met beperkte ruimte.

Aanvullende optionele eigenschappen maken aanpassing van het uiterlijk en gedrag van de app mogelijk.

Wanneer de `@AppProfile` annotatie aanwezig is, zorgt webforJ voor:

- Automatisch opzetten van noodzakelijke meta-tags.
- Genereren van een [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Serveren van gerelateerde bronnen zoals iconen en screenshots.

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

De volgende tabel geeft een overzicht van alle eigenschappen die worden ondersteund door de `@AppProfile` annotatie:

| **Eigenschap**      | **Type**                                           | **Beschrijving**                                                                                        | **Standaardwaarde**     |
| ------------------- | -------------------------------------------------- | ------------------------------------------------------------------------------------------------------ | ----------------------- |
| `name`              | `String`                                           | De volledige naam van de app, weergegeven in app-menu's en installatie-dialoogvensters.                | **Verplicht**           |
| `shortName`         | `String`                                           | Een korte versie van de naam, gebruikt in contexten met beperkte ruimte. Mag niet meer dan 12 tekens bevatten. | **Verplicht**           |
| `description`       | `String`                                           | Een korte beschrijving van de app, weergegeven tijdens installatie en in app-instellingen.              | `""`                    |
| `themeColor`        | `String`                                           | De themakleur voor de app, toegepast op de browserinterface wanneer de app wordt gestart.              | `"#ffffff"`             |
| `backgroundColor`   | `String`                                           | De initiële achtergrondkleur voor de app tijdens het laden.                                            | `"#f8fafc"`             |
| `startUrl`          | `String`                                           | De URL die wordt geopend wanneer de app wordt gestart.                                                  | `"."`                   |
| `display`           | `Display` **_Enum_**                               | De weergavemodus van de app (bijv. `FULLSCREEN`, `STANDALONE`, `BROWSER`).                             | `STANDALONE`            |
| `orientation`       | `Orientation` **_Enum_**                           | De standaardoriëntatie van de app (bijv. `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                         | `NATURAL`               |
| `icons`             | [`Icon[]`](#appprofileicon-properties)             | Een array van iconen die de app op verschillende resoluties vertegenwoordigen.                          | `[]`                    |
| `defaultIcon`       | [`DefaultIcon`](#appprofiledefaulticon-properties) | Geeft een standaardicoon voor de app op. Genereert automatisch iconpaden in meerdere maten indien geconfigureerd. | `icons://icon.png`     |
| `screenshots`       | [`Screenshot[]`](#appprofilescreenshot-properties) | Een array van screenshots voor de app, gebruikt in installatie-dialoogvensters.                        | `[]`                    |
| `categories`        | `String[]`                                         | Categorieën om de app te classificeren (bijv. `Finance`, `Shopping`).                                 | `[]`                    |

### `@AppProfile.Icon` eigenschappen {#appprofileicon-properties}

Iconen definiëren de visuele representatie van je app in menu's en startschermen. De `@AppProfile.Icon` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                     | **Type** | **Beschrijving**                                                                                          | **Standaardwaarde** |
| ---------------------------------------------------------------------------------- | -------- | -------------------------------------------------------------------------------------------------------- | ------------------- |
| `src`                                                                             | `String` | Het pad naar het icoon. Dit kan een absoluut URL zijn, of een `ws://` pad.                               | **Verplicht**       |
| `sizes`                                                                           | `String` | Een string die een of meer formaten van het beeld specificeert in het formaat `BreedtexHoogte` (bijv. `512x512`). | **Verplicht**       |
| `type`                                                                            | `String` | Het MIME-type van het icoon (bijv. `image/png`, `image/jpeg`). Als dit niet wordt verstrekt, wordt het gedetecteerd. | `""`                |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Het doel van het icoon (bijv. `any`, `maskable`, `monochrome`).                                         | `""`                |

### Voorbeeld {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` eigenschappen {#appprofiledefaulticon-properties}

De `DefaultIcon` annotatie vereenvoudigt de configuratie van app-iconen door meerdere maatvarianten te genereren vanuit een basisicoon.
Dit is met name nuttig om compatibiliteit te waarborgen op apparaten met verschillende resoluties.

| **Eigenschap** | **Type** | **Beschrijving**                                                               | **Standaardwaarde** |
| -------------- | -------- | ------------------------------------------------------------------------------- | ------------------- |
| `value`        | `String` | Het pad naar het basisicoonbestand. Dit kan een absoluut URL zijn, of een `ws://` pad. | **Verplicht**       |
| `sizes`        | `int[]`  | Een array van groottes om te genereren, gespecificeerd als gehele getallen (bijv. `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Vereisten voor Icoonbestanden
Deze configuratie genereert de daadwerkelijke icoonbestanden voor de app niet automatisch. In plaats daarvan gebruikt het de `@AppProfile.DefaultIcon` annotatie om de overeenkomstige [`@AppProfile.Icon`](#appprofileicon-properties) vermeldingen voor elke gespecificeerde grootte te genereren.

#### Als je het [webserverprotocol](../managing-resources/assets-protocols#the-webserver-protocol) gebruikt {#if-using-the-webserver-protocol}
- Je moet een basisbestand `icon.png` in de `static/icons` map leveren.
- Je wordt verwacht aanvullende icoonvarianten op te nemen met de namen `icon-144x144.png`, `icon-192x192.png`, en `icon-512x512.png`.
- Deze specifieke groottes zorgen voor compatibiliteit met verschillende apparaten en resoluties.

#### Als je het [iconenprotocol](../managing-resources/assets-protocols#the-icons-protocol) gebruikt {#if-using-the-icons-protocol}

- Je wordt verwacht een basisbestand `icon.png` in de `/icons` map te leveren.
- De `icons` eindpunt levert dynamisch verschillende icoongroottes op aanvraag wanneer ze worden aangevraagd.
:::

### `@AppProfile.Screenshot` eigenschappen {#appprofilescreenshot-properties}

Screenshots bieden een voorvertoning van de app in installatie-dialoogvensters of app-winkels. De `@AppProfile.Screenshot` annotatie ondersteunt de volgende eigenschappen:

| **Eigenschap**                                                                                  | **Type** | **Beschrijving**                                                                                           | **Standaardwaarde** |
| ----------------------------------------------------------------------------------------------- | -------- | --------------------------------------------------------------------------------------------------------- | ------------------- |
| `src`                                                                                           | `String` | Het pad naar de screenshot. Dit kan een absoluut URL zijn, of een `ws://` pad.                          | **Verplicht**       |
| `sizes`                                                                                         | `String` | Een string die één of meer groottes van het beeld specificeert in het formaat `BreedtexHoogte` (bijv. `1080x1920`). | **Verplicht**       |
| `type`                                                                                          | `String` | Het MIME-type van de screenshot (bijv. `image/png`, `image/jpeg`). Als dit niet wordt verstrekt, wordt het gedetecteerd. | `""`                |
| `label`                                                                                         | `String` | Een beschrijvende label voor de screenshot.                                                                | `""`                |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | De vormfactor van de screenshot (bijv. `narrow`, `wide`).                                                  | `""`                |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)       | `String` | Het platform waarvoor de screenshot bedoeld is (bijv. `ios`, `android`).                                  | `""`                |

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
