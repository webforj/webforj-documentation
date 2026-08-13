---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# App badges <DocChip chip='since' label='26.01' />

webforJ biedt twee complementaire badge-API's. <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` schildert het pictogram van de app op het dock, de taakbalk of het startscherm. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` schildert de document favicon in de browser tabstrip. Ze richten zich op verschillende oppervlakken en hebben verschillende vereisten, dus de meeste apps roepen beide aan voor de breedste zichtbaarheid.

<!-- INTRO_END -->

## App pictogram badge {#app-icon-badge}

`App.setBadge` rendert de badge op het pictogram dat het besturingssysteem voor de app gebruikt: het macOS dock, de Windows taakbalk, de Chrome OS plank of het Android startscherm.

![App pictogram badge in het macOS dock](/img/app-badges/app-badge.png)

### Vereisten {#app-prerequisites}

De badge is alleen zichtbaar wanneer aan al het volgende is voldaan:

- De browser ondersteunt het tekenen van badges op app-pictogrammen.
- De pagina wordt geserveerd vanuit een veilige context (HTTPS, of `http://localhost` tijdens de ontwikkeling). Gewone HTTP-oorsprongen weigeren de oproep.
- De app is op het apparaat geïnstalleerd. De installatieflow varieert per browser: Chromium-browsers bieden een installprompt voor elke pagina die een manifest heeft, Safari op macOS gebruikt **Bestand → Voeg toe aan Dock**, en Safari op iOS gebruikt **Delen → Voeg toe aan startscherm**.

Om de app instelbaar te maken wanneer deze draait onder Spring Boot of een standalone webforJ-server, annoteer de <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> subclass met <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink>. De annotatieprocessor genereert het manifest, de pictogram link-tags en de meta-tags die de browser nodig heeft om de installprompt aan te bieden.

```java
@AppProfile(name = "Inbox", shortName = "Inbox")
public class Application extends App {}
```

Zie de [Instelbare Apps](../configuration/installable-apps) pagina voor de volledige lijst van `@AppProfile`-leden, pictogramgroottes en platform specifieke richtlijnen voor de installatieflow.

### Browserondersteuning {#app-browser-support}

Na installatie hangt het renderen van de badge af van de browser. Ondersteuning voor installatie zelf wordt behandeld op de [Instelbare Apps](../configuration/installable-apps#browser-support) pagina.

| Browser | Badge gerenderd na installatie |
| --- | --- |
| Chrome, Edge, Opera, en andere Chromium-browsers (desktop en Android) | Ja |
| Safari op macOS Sonoma (Safari 17) en later | Ja |
| Safari op iOS 16.4 en later | Ja |
| Firefox (alle platforms) | Nee. De oproep retourneert zonder te renderen. |

### Het instellen en wissen van de badge {#app-setting-clearing}

Geef een positief geheel getal door om een numerieke badge weer te geven. Geef `null` of `0` door om deze te wissen. Roep de overload zonder argumenten aan om de vlagindicator weer te geven (een klein stipje, de exacte weergave is platformafhankelijk).

```java
App.setBadge(5);     // numerieke badge
App.setBadge();      // vlagindicator zonder nummer
App.setBadge(0);     // wissen
App.setBadge(null);  // wissen
```

`App.setBadge` retourneert onmiddellijk. De browser schrijft de badge naar het besturingssysteemoppervlak asynchroon, en de wijziging wordt niet gerapporteerd aan de app.

## Browser tab pictogram badge {#browser-tab-icon-badge}

`Page.setIconBadge` schildert de telling op de document favicon. Het werkt in elke tab zonder installatie en vereist geen manifest. De badge is zichtbaar in de browser tabstrip en op elke andere locatie die de favicon weergeeft, zoals bladwijzers of recente paginaweergaven.

![Browser tab favicon met een numerieke badge overlay](/img/app-badges/icon-badge.png)

### Het instellen en wissen van de badge {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // numerieke badge
page.setIconBadge();      // vlagindicator zonder nummer
page.setIconBadge(0);     // wissen
page.setIconBadge(null);  // wissen
```

Het wissen van de badge herstelt de oorspronkelijke favicon.

:::info Uitvoering met `BBjServices`
Wanneer de app wordt geserveerd door `BBjServices`, is de favicon de **Snelkoppeling Afbeelding** die is geconfigureerd voor de app in de Enterprise Manager. De badge wordt geschilderd op welk pictogram Enterprise Manager ook serveert. Als er geen snelkoppeling afbeelding is geconfigureerd, heeft `Page.setIconBadge` geen favicon om over te leggen en doet het stilletjes niets.
:::

### Het stijlen van de badge {#styling-the-badge}

Geef een <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink> door om kleur, vorm en grootte te regelen:

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

Het opties-object is een waardehouder. Alle setters retourneren `this` zodat aanroepen kunnen worden gekoppeld.

| Optie | Type | Standaard | Opmerkingen |
|---|---|---|---|
| `kleur` | `java.awt.Color` | `#e53935` | Achtergrondkleur van de badge. De tekstkleur wordt automatisch afgeleid voor contrast, zodat de cijfers leesbaar blijven op elke gekozen kleur. |
| `vorm` | `Shape` | `CIRCLE` | `CIRCLE` of `SQUARE`. |
| `grootte` | `double` | `1.0` | Relatieve grootte. `0.5` is de helft van de standaard diameter; `1.5` is 50% groter. De badge wordt geklemd om in het favicon-canvas te passen. |

### Browsercaveat {#browser-caveat}

Safari vernieuwt de favicon niet na de eerste pagina-lading. Oproepen naar `Page.setIconBadge` worden voltooid zonder fout, maar Safari blijft het oorspronkelijke pictogram weergeven. Gebruik `Page.setTitle` om ook de telling voor de documenttitel toe te voegen als je een zichtbare aanwijzing in Safari nodig hebt.

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") Inbox");
```

## Kiezen tussen de twee {#choosing-between-the-two}

| Oppervlak | API | Vereist installatie | Zichtbaar in Safari |
|---|---|---|---|
| Besturingssysteem app pictogram | `App.setBadge` | Ja | Ja (macOS Sonoma / iOS 16.4 en later) |
| Browser tab favicon | `Page.setIconBadge` | Nee | Nee. De oproep voltooit zich zonder fout, maar de tabstrip ververst niet. |

De meeste apps roepen beide aan zodat de badge zichtbaar is, ongeacht of de gebruiker zich in een geïnstalleerd venster of in een reguliere browsertab bevindt.
