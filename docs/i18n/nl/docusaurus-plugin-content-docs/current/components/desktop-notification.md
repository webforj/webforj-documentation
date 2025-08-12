---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 16e95136d6067cafa258ef513f9e757f
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

In webforj 25.00 en hoger biedt de **DesktopNotification** component een eenvoudige interface voor het maken, weergeven en beheren van desktopmeldingen. Met de nadruk op minimale configuratie en ingebouwde gebeurtenisafhandeling kan de component worden gebruikt wanneer het nodig is om gebruikers te informeren over realtime gebeurtenissen (zoals nieuwe berichten, waarschuwingen of systeemgebeurtenissen) terwijl ze uw app gebruiken.

:::warning experimentele functie
De `DesktopNotification` component is nog in ontwikkeling en de API kan veranderingen ondergaan naarmate deze zich verder ontwikkelt. Om deze functie te gebruiken, moet u de volgende afhankelijkheid in uw pom.xml opnemen.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Vereisten
Zorg voordat u de `DesktopNotification` component integreert voor het volgende:

- Uw app draait in een **veilige context** (HTTPS).
- De browser is niet in inkognito- of privé-browsingmodus.
- De gebruiker heeft interactie gehad met de app (bijv. op een knop geklikt of een toets ingedrukt), aangezien meldingen een gebruikersgebaar vereisen om weergegeven te worden.
- De gebruiker heeft toestemming gegeven voor meldingen (dit wordt automatisch gevraagd indien nodig).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Basisgebruik {#basic-usage}

Er zijn meerdere manieren om een melding te maken en weer te geven. In de meeste scenario's is de eenvoudigste benadering om een van de statische `show` methoden aan te roepen die de volledige levenscyclus van de melding encapsuleren.

### Voorbeeld: Een basismelding weergeven {#example-displaying-a-basic-notification}

```java
// Basis melding met titel en bericht
DesktopNotification.show("Update Beschikbaar", "Uw download is voltooid!");
```

Deze één-regelige code maakt een melding met een titel en body en probeert deze weer te geven.

## De melding aanpassen {#customizing-the-notification}

Er zijn verschillende opties voor het aanpassen van het uiterlijk en de stijl van de weergegeven melding, afhankelijk van de behoeften van de app en het doel van de melding.

### Een aangepaste `Icon` instellen {#setting-a-custom-icon}

Standaard gebruikt de melding uw gedefinieerde app-icon via het [icoonprotocol](../managing-resources/assets-protocols#the-icons-protocol). U kunt een aangepast pictogram instellen met de `setIcon` methode. De component ondersteunt verschillende URL-schema's:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Wordt opgelost als een context-URL die naar de bronmap van de app wijst; afbeelding is base64-gecodeerd.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Wordt opgelost als een webserver-URL, wat een volledig gekwalificeerde URL oplevert.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Wordt opgelost als een iconen-URL.

**Voorbeeld:**

```java
// Een melding maken met een aangepaste pictogram-URL
DesktopNotification notification = new DesktopNotification(
  "Herinnering", "Vergadering begint over 10 minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Meldingsevents {#notification-events}

De `DesktopNotification` ondersteunt verschillende levenscycusevents, en luisteraars kunnen worden toegevoegd om evenementen af te handelen, zoals wanneer een melding wordt weergegeven, gesloten, geklikt, of wanneer er een fout optreedt.

| Evenement                  | Beschrijving                                           | Wanneer te gebruiken                                               |
|-----------------------------|-------------------------------------------------------|------------------------------------------------------------------|
| **Open** | Geactiveerd wanneer de melding wordt weergegeven.       | Log de weergave van de melding, werk de interface bij, volg de betrokkenheid.    |
| **Sluiten**| Geactiveerd wanneer de melding is gesloten.         | Ruim hulpbronnen op, log afwijzingen, voer vervolgstappen uit.|
| **Fout**| Geactiveerd wanneer er een fout optreedt met de melding of de gebruiker geen toestemming heeft gegeven.| Behandel fouten op een nette manier, informeer de gebruiker, gebruik alternatieven.  |
| **Klik**| Geactiveerd wanneer de gebruiker op de melding klikt. | Navigeer naar een specifieke sectie, log interacties, richt de app opnieuw uit. |

```java
DesktopNotification notification = new DesktopNotification("Waarschuwing", "U heeft een nieuw bericht!")

// Voeg een gebeurtenisluisteraar toe voor het open-evenement
notification.onOpen(event -> {
  System.out.println("Melding is geopend door de gebruiker.");
});

// Luister op de klik-gebeurtenis
notification.onClick(event -> {
  System.out.println("Melding geklikt.");
});
```

:::warning Klikgedrag
Browserbeveiligingsbeleid voorkomt dat de klikgebeurtenis van de melding automatisch uw appvenster of tabblad in focus brengt. Dit gedrag wordt afgedwongen door de browser en kan niet programmeermatig worden overruled. Als uw app vereist dat het venster op de voorgrond komt, moet u gebruikers instrueren om binnen de app te klikken nadat ze met de melding hebben gecommuniceerd.
:::

## Beveiliging en compatibiliteitsoverwegingen {#security-and-compatibility-considerations}

Houd de volgende punten in gedachten bij het gebruik van de **DesktopNotification** component:

- **Beveiligingscontext:** Uw app moet via HTTPS worden aangeboden om ervoor te zorgen dat meldingen zijn toegestaan door de meeste moderne browsers.
- **Vereiste gebruikersgebaar:** Meldingen worden alleen weergegeven na een door de gebruiker geactiveerde actie. Het simpelweg laden van een pagina triggert geen melding.
- **Browserbeperkingen:** Niet alle browsers behandelen aangepaste pictogrammen of focusgedrag op dezelfde manier. Bijvoorbeeld, aangepaste pictogrammen werken mogelijk niet in Safari, terwijl het evenementgedrag in andere browsers kan variëren.
- **Toestemmingen:** Controleer altijd of uw app toestemming voor meldingen van de gebruiker op een nette manier controleert en aanvraagt.

## Best practices voor gebruik {#usage-best-practices}

Houd de volgende best practices in gedachten bij het gebruik van de `DesktopNotification` component in uw app:

- **Informeer uw gebruikers:** Laat gebruikers weten waarom meldingen nodig zijn en hoe ze van nut kunnen zijn.
- **Bied alternatieven:** Aangezien sommige browsers meldingen kunnen beperken, overweeg alternatieve manieren om gebruikers te waarschuwen (bijv. in-app berichten).
- **Foutafhandeling:** Registreer altijd een foutluisteraar om op een nette manier om te gaan met scenario's waarin meldingen niet kunnen worden weergegeven.
