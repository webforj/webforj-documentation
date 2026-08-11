---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

De `DesktopNotification` component toont native desktop notificaties buiten het browservenster. Het kan worden gebruikt om gebruikers te waarschuwen voor realtime gebeurtenissen zoals nieuwe berichten, systeemwaarschuwingen of statuswijzigingen terwijl ze uw app gebruiken.

<!-- INTRO_END -->

## Setup en vereisten {#setup-and-prerequisites}

<ExperimentalWarning />

Om deze functie te gebruiken, voeg de volgende afhankelijkheid toe aan uw pom.xml:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Voordat u de `DesktopNotification` component integreert, moet u ervoor zorgen dat:

- Uw app draait in een **veilige context** (HTTPS).
- De browser niet in incognito- of privé-browsingmodus is.
- De gebruiker interactie met de app heeft gehad (bijv. op een knop heeft geklikt of een toets heeft ingedrukt), aangezien notificaties een gebruikersgebaar vereisen om zichtbaar te zijn.
- De gebruiker toestemming heeft gegeven voor notificaties (dit zal automatisch worden aangevraagd indien nodig).

## Basisgebruik {#basic-usage}

Er zijn meerdere manieren om een notificatie te maken en weer te geven. In de meeste scenario's is de eenvoudigste benadering het aanroepen van een van de statische `show` methoden die de volledige notificatielevenscyclus kapselen.

### Voorbeeld: Een basisnotificatie weergeven {#example-displaying-a-basic-notification}

```java
// Basisnotificatie met titel en bericht
DesktopNotification.show("Update Beschikbaar", "Uw download is voltooid!");
```

Deze één-liner maakt een notificatie met een titel en een bericht, en probeert deze vervolgens weer te geven.

## De notificatie aanpassen {#customizing-the-notification}

Er zijn verschillende opties voor het aanpassen van de uitstraling en het gevoel van de weergegeven notificatie, afhankelijk van de behoeften van de app en het doel van de notificatie.

### Een aangepaste `Icon` instellen {#setting-a-custom-icon}

Standaard gebruikt de notificatie het gedefinieerde app-pictogram via het [icons protocol](../managing-resources/assets-protocols#the-icons-protocol). U kunt een aangepast pictogram instellen met de methode `setIcon`. De component ondersteunt verschillende URL-schema's:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Opgelost als een context-URL die naar de resourcefolder van de app wijst; afbeelding is base64-gecodeerd.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Opgelost als een webserver URL, wat een volledig gekwalificeerde URL oplevert.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Opgelost als een iconen-URL.

**Voorbeeld:**

```java
// Een notificatie maken met een aangepaste pictogram URL
DesktopNotification notification = new DesktopNotification(
  "Herinnering", "Vergadering begint over 10 minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Notificatie-evenementen {#notification-events}

De `DesktopNotification` ondersteunt verschillende levenscyclus evenementen, en luisteraars kunnen worden bevestigd om gebeurtenissen te behandelen, zoals wanneer een notificatie wordt weergegeven, gesloten, geklikt of een fout ondervindt.

| Evenement              | Beschrijving                                           | Wanneer te gebruiken                                           |
|-----------------------------|-------------------------------------------------------|---------------------------------------------------------------|
| **Open** | Geactiveerd wanneer de notificatie wordt weergegeven.       | Log notificatieweergave, update UI, volg betrokkenheid.       |
| **Sluiten**| Geactiveerd wanneer de notificatie wordt gesloten.         | Maak bronnen schoon, logafmeldingen, voer vervolgacties uit. |
| **Fout**| Geactiveerd wanneer er een fout optreedt met de notificatie of de gebruiker geen toestemming heeft gegeven.| Beheer fouten op een elegante manier, verwittig de gebruiker, pas fallback-methoden toe.  |
| **Klik**| Geactiveerd wanneer de gebruiker op de notificatie klikt. | Navigeer naar een specifieke sectie, log interacties, richt de app opnieuw in. |


```java
DesktopNotification notification = new DesktopNotification("Waarschuwing", "U heeft een nieuw bericht!")

// Bevestig een evenement luisteraar voor het open-evenement
notification.onOpen(event -> {
  System.out.println("Notificatie is geopend door de gebruiker.");
});

// Eveneens luisteren naar het klik-evenement
notification.onClick(event -> {
  System.out.println("Notificatie geklikt.");
});
```

:::warning Klikgedrag
Browserbeveiligingsbeleid voorkomt dat het klik-evenement van de notificatie automatisch uw app-venster of tabblad in de focus brengt. Dit gedrag wordt afgedwongen door de browser en kan niet programmatisch worden overschreven. Als uw app vereist dat het venster de focus krijgt, moet u gebruikers instrueren om binnen de app te klikken nadat ze met de notificatie hebben geïnterageerd.
:::

## Beveiligings- en compatibiliteitsoverwegingen {#security-and-compatibility-considerations}

Bij het gebruik van de **DesktopNotification** component, houd de volgende punten in gedachten:

- **Beveiligingscontext:** Uw app moet via HTTPS worden aangeboden om ervoor te zorgen dat notificaties door de meeste moderne browsers worden toegestaan.
- **Vereiste gebruikersgebaar:** Notificaties worden alleen weergegeven na een door de gebruiker geinitieerd actie. Het simpelweg laden van een pagina zal geen notificatie activeren.
- **Browserbeperkingen:** Niet alle browsers behandelen aangepaste pictogrammen of focusgedrag op dezelfde manier. Bijvoorbeeld, aangepaste pictogrammen werken mogelijk niet in Safari, terwijl het evenementgedrag kan variëren in andere browsers.
- **Toestemmingen:** Controleer altijd of uw app toestemming voor notificaties van de gebruiker op een elegante manier controleert en aanvraagt.

## Beste praktijken voor gebruik {#usage-best-practices}

Houd de volgende beste praktijken in gedachten bij het gebruik van de `DesktopNotification` component in uw app:

- **Informeer uw gebruikers:** Laat gebruikers weten waarom notificaties nodig zijn en hoe ze voordeel kunnen hebben bij notificaties.
- **Bied fallbacks aan:** Aangezien sommige browsers notificaties kunnen beperken, overweeg alternatieve manieren om gebruikers te waarschuwen (bijv. in-app berichten).
- **Foutafhandeling:** Registreer altijd een foutluisteraar om scenario's waar notificaties niet kunnen worden weergegeven op een elegante manier te beheren.
