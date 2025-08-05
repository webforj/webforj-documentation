---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 6bc148e8bcb06161115d0509601b516b
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

In webforj 25.00 en hoger biedt de **DesktopNotification**-component een eenvoudige interface voor het maken, weergeven en beheren van desktopmeldingen. Met een focus op minimale configuratie en ingebouwde evenementenafhandeling kan de component worden gebruikt wanneer het nodig is om gebruikers te informeren over realtime gebeurtenissen (zoals nieuwe berichten, waarschuwingen of systeemgebeurtenissen) terwijl ze uw app aan het browsen zijn.

:::warning experimentele functie
De `DesktopNotification`-component is nog in ontwikkeling, en de API kan wijzigingen ondergaan naarmate deze verder rijpt. Om deze functie te gebruiken, zorg ervoor dat u de volgende afhankelijkheid in uw pom.xml opneemt.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Voorwaarden
Voordat u de `DesktopNotification`-component integreert, zorg ervoor dat:

- Uw app draait in een **veilige context** (HTTPS).
- De browser zich niet in de incognito- of privé-browsingmodus bevindt.
- De gebruiker heeft interactie gehad met de app (bijvoorbeeld een knop heeft ingedrukt of een toets heeft ingedrukt), aangezien meldingen een gebruikersgebaar vereisen om weergegeven te worden.
- De gebruiker heeft de toestemming voor meldingen verleend (dit wordt automatisch aangevraagd indien nodig).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Basisgebruik {#basic-usage}

Er zijn meerdere manieren om een melding te maken en weer te geven. In de meeste scenario's is de eenvoudigste benadering om een van de statische `show`-methoden aan te roepen die de volledige levenscyclus van de notificatie encapsuleert.

### Voorbeeld: Een eenvoudige melding weergeven {#example-displaying-a-basic-notification}

```java
// Eenvoudige melding met titel en bericht
DesktopNotification.show("Update Beschikbaar", "Uw download is voltooid!");
```

Deze enkele regel maakt een melding met een titel en body, en probeert deze weer te geven.

## Het aanpassen van de melding {#customizing-the-notification}

Er zijn verschillende opties om de look en feel van de weergegeven melding aan te passen, afhankelijk van de behoeften van de app en het doel van de melding.

### Een aangepaste `Icon` instellen {#setting-a-custom-icon}

Standaard gebruikt de melding uw gedefinieerde app-icoon via het [iconenprotocol](../managing-resources/assets-protocols#the-icons-protocol). U kunt een aangepast icoon instellen met de `setIcon`-methode. De component ondersteunt verschillende URL-schema's:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Opgelost als een context-URL die naar de resource-map van de app wijst; afbeelding is base64-gecodeerd.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Opgelost als een webserver-URL, die een volledig gekwalificeerde URL geeft.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Opgelost als een iconen-URL.

**Voorbeeld:**

```java
// Een melding maken met een aangepaste icoon-URL
DesktopNotification notification = new DesktopNotification(
  "Herinnering", "Vergadering begint over 10 minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Meldingsevenementen {#notification-events}

De `DesktopNotification` ondersteunt verschillende levenscyclusgebeurtenissen en luisteraars kunnen worden aangesteld om gebeurtenissen te verwerken, zoals wanneer een melding wordt weergegeven, gesloten, aangeklikt of een fout tegenkomt.

| Evenement                  | Beschrijving                                           | Wanneer te gebruiken                                               |
|-----------------------------|-------------------------------------------------------|------------------------------------------------------------------|
| **Open** | Geactiveerd wanneer de melding wordt weergegeven.       | Log meldingweergave, update UI, volg betrokkenheid.              |
| **Sluiten**| Geactiveerd wanneer de melding wordt gesloten.         | Ruim bronnen op, log afwijzingen, voer opvolging uit.            |
| **Fout**| Geactiveerd wanneer er een fout optreedt met de melding of de gebruiker geen toestemming heeft verleend.| Beheer fouten op een nette manier, informeer de gebruiker, pas alternatieven toe.  |
| **Klik**| Geactiveerd wanneer de gebruiker op de melding klikt. | Navigeer naar een specifiek gedeelte, log interacties, richt de app weer op. |


```java
DesktopNotification notification = new DesktopNotification("Waarschuwing", "U heeft een nieuw bericht!")

// Koppel een evenementluisteraar voor het open-evenement
notification.onOpen(event -> {
  System.out.println("Melding is door de gebruiker geopend.");
});

// Luister ook naar het klik-evenement
notification.onClick(event -> {
  System.out.println("Melding geklikt.");
});
```

:::warning Klikgedrag
Browserbeveiligingsbeleid voorkomt dat het klik-evenement van de melding automatisch uw appvenster of -tabblad in focus brengt. Dit gedrag wordt afgedwongen door de browser en kan niet programmatisch worden overschreven. Als uw app vereist dat het venster gefocust wordt, moet u de gebruikers instructies geven om binnen de app te klikken na interactie met de melding.
:::

## Beveiligings- en compatibiliteitsconsideraties {#security-and-compatibility-considerations}

Wanneer u de **DesktopNotification**-component gebruikt, houdt u rekening met de volgende punten:

- **Veiligheidscontext:** Uw app moet via HTTPS worden aangeboden om ervoor te zorgen dat meldingen door de meeste moderne browsers worden toegestaan.
- **Vereiste gebruikersgebaar:** Meldingen worden alleen weergegeven na een actie die door de gebruiker is geactiveerd. Gewoon een pagina laden triggert geen melding.
- **Browserbeperkingen:** Niet alle browsers behandelen aangepaste icoontjes of focusgedrag op dezelfde manier. Bijvoorbeeld, aangepaste icoontjes werken mogelijk niet in Safari, terwijl het gebeurtenisgedrag in andere browsers kan variëren.
- **Toestemmingen:** Zorg er altijd voor dat uw app op een nette manier controleert op en om toestemming voor meldingen aan de gebruiker vraagt.

## Best practices voor gebruik {#usage-best-practices}

Houd de volgende best practices in gedachten bij het gebruik van de `DesktopNotification`-component in uw app:

- **Informeer uw gebruikers:** Laat gebruikers weten waarom meldingen nodig zijn en hoe ze van nut kunnen zijn.
- **Bied fallbacks aan:** Aangezien sommige browsers meldingen kunnen beperken, overweeg alternatieve manieren om gebruikers te waarschuwen (bijvoorbeeld in-app berichten).
- **Foutafhandeling:** Registreer altijd een foutluisteraar om op een nette manier om te gaan met scenario's waarin meldingen niet worden weergegeven.
