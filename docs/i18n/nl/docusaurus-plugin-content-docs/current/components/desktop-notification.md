---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

De `DesktopNotification` component toont native desktop notificaties buiten het browservenster. Het kan worden gebruikt om gebruikers te waarschuwen voor real-time gebeurtenissen zoals nieuwe berichten, systeemwaarschuwingen of statuswijzigingen terwijl ze jouw app gebruiken. 

<!-- INTRO_END -->

## Setup en vereisten {#setup-and-prerequisites}

:::warning experimentele functie
De `DesktopNotification` component is nog in ontwikkeling en de API kan veranderingen ondergaan naarmate deze volwassen wordt. Om deze functie te gebruiken, zorg ervoor dat je de volgende afhankelijkheid toevoegt aan je pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

Voordat je de `DesktopNotification` component integreert, zorg ervoor dat:

- Je app draait in een **veilige context** (HTTPS).
- De browser niet in incognito- of privé-browsingmodus is.
- De gebruiker met de app heeft gecommuniceerd (bijv. een knop heeft ingedrukt of een toets heeft gedrukt), aangezien notificaties een gebruikersgebaar vereisen om weergegeven te worden.
- De gebruiker toestemming heeft gegeven voor notificaties (dit wordt automatisch aangevraagd indien nodig).

## Basisgebruik {#basic-usage}

Er zijn meerdere manieren om een notificatie te creëren en weer te geven. In de meeste scenario's is de eenvoudigste benadering om een van de statische `show` methoden aan te roepen die de volledige levenscyclus van de notificatie omvatten.

### Voorbeeld: Weergeven van een basale notificatie {#example-displaying-a-basic-notification}

```java
// Basale notificatie met titel en bericht
DesktopNotification.show("Update Beschikbaar", "Je download is voltooid!");
```

Deze enkele regel maakt een notificatie met een titel en body, en probeert deze weer te geven.

## De notificatie aanpassen {#customizing-the-notification}

Er zijn verschillende opties om het uiterlijk en de uitstraling van de weergegeven notificatie aan te passen, afhankelijk van de behoeften van de app en het doel van de notificatie. 

### Een aangepaste `Icon` instellen {#setting-a-custom-icon}

Standaard gebruikt de notificatie het door jou gedefinieerde app-icoon via het [icons protocol](../managing-resources/assets-protocols#the-icons-protocol). Je kunt een aangepast icoon instellen met de `setIcon` methode. De component ondersteunt verschillende URL-schema's:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Opgelost als een context-URL die wijst naar de resourcefolder van de app; afbeelding is base64-gecodeerd.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Opgelost als een webserver-URL, die een volledig gekwalificeerde URL geeft.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Opgelost als een icons-URL.

**Voorbeeld:**

```java
// Een notificatie maken met een aangepaste icoon-URL
DesktopNotification notification = new DesktopNotification(
  "Herinnering", "Vergadering begint over 10 minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Notificatie evenementen {#notification-events}

De `DesktopNotification` ondersteunt verschillende levenscyclus evenementen, en luisteraars kunnen worden toegevoegd om gebeurtenissen te verwerken, zoals wanneer een notificatie wordt weergegeven, gesloten, geklikt of een fout tegenkomt.

| Evenement                  | Beschrijving                                           | Wanneer te gebruiken                                              |
|-----------------------------|-------------------------------------------------------|------------------------------------------------------------------|
| **Open** | Geïnitieerd wanneer de notificatie wordt weergegeven.       | Log notificatie-weergave, update UI, volg betrokkenheid.         |
| **Close**| Geïnitieerd wanneer de notificatie wordt gesloten.         | Resources opschonen, afmeldingen loggen, vervolgacties uitvoeren.|
| **Error**| Geïnitieerd wanneer er een fout optreedt met de notificatie of de gebruiker geen toestemming heeft gegeven.| Beheer fouten op een nette manier, deel de fout mee aan de gebruiker, pas fallback-oplossingen toe.  |
| **Click**| Geïnitieerd wanneer de gebruiker op de notificatie klikt. | Navigeer naar een specifieke sectie, log interacties, fokussen de app opnieuw. |


```java
DesktopNotification notification = new DesktopNotification("Alert", "Je hebt een nieuw bericht!")

// Voeg een evenementluisteraar toe voor het open-evenement
notification.onOpen(event -> {
  System.out.println("Notificatie werd geopend door de gebruiker.");
});

// Luister ook naar het klik-evenement
notification.onClick(event -> {
  System.out.println("Notificatie geklikt.");
});
```

:::warning Klik Gedrag
Browserbeveiligingsbeleid verhindert dat de klikgebeurtenis van de notificatie automatisch je appvenster of tabblad in de focus brengt. Dit gedrag wordt afgedwongen door de browser en kan niet programmatisch worden overschreven. Als je app vereist dat het venster gefocust is, moet je de gebruikers instrueren om binnen de app te klikken na interactie met de notificatie.
:::

## Beveiligings- en compatibiliteitsoverwegingen {#security-and-compatibility-considerations}

Houd de volgende punten in gedachten bij het gebruik van de **DesktopNotification** component:

- **Veilige Context:** Je app moet worden aangeboden via HTTPS om ervoor te zorgen dat notificaties door de meeste moderne browsers zijn toegestaan.
- **Vereiste Gebruikersgebaar:** Notificaties worden alleen weergegeven na een door de gebruiker geïnitieerde actie. Het eenvoudig laden van een pagina zal geen notificatie triggeren.
- **Browserbeperkingen:** Niet alle browsers behandelen aangepaste pictogrammen of focusgedrag op dezelfde manier. Bijvoorbeeld, aangepaste pictogrammen werken mogelijk niet in Safari, terwijl het evenementengedrag kan variëren in andere browsers.
- **Toestemmingen:** Controleer altijd of je app op een nette manier verificatie en verzoeken voor notificatie-toestemmingen aan de gebruiker uitvoert.

## Best practices voor gebruik {#usage-best-practices}

Houd rekening met de volgende best practices bij het gebruik van de `DesktopNotification` component in je app:

- **Informeer je gebruikers:** Laat gebruikers weten waarom notificaties nodig zijn en hoe ze hiervan kunnen profiteren.
- **Bied fallback-oplossingen:** Aangezien sommige browsers notificaties kunnen beperken, overweeg alternatieve manieren om gebruikers te waarschuwen (bijv. in-app berichten).
- **Foutafhandeling:** Registreer altijd een foutluisteraar om scenario's waar notificaties niet kunnen worden weergegeven op een nette manier te beheren.
