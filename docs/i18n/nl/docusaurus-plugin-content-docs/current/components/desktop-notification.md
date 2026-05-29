---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: b7e4651594dee824d6bcdf1ac32e1998
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

De `DesktopNotification` component toont native desktop notificaties buiten het browservenster. Het kan worden gebruikt om gebruikers te waarschuwen voor real-time gebeurtenissen zoals nieuwe berichten, systeemwaarschuwingen of statuswijzigingen terwijl ze je app gebruiken.

<!-- INTRO_END -->

## Setup en vereisten {#setup-and-prerequisites}

<ExperimentalWarning />

Om deze functie te gebruiken, voeg je de volgende afhankelijkheid toe aan je pom.xml:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Voordat je de `DesktopNotification` component integreert, zorg ervoor dat:

- Je app draait in een **veilige context** (HTTPS).
- De browser zich niet in incognito- of privé browse-modus bevindt.
- De gebruiker interactie heeft gehad met de app (bijv. op een knop heeft geklikt of een toets heeft ingedrukt), aangezien notificaties een gebruikersgebaar vereisen om weergegeven te worden.
- De gebruiker toestemming heeft gegeven voor notificaties (dit zal automatisch worden aangevraagd indien nodig).

## Basisgebruik {#basic-usage}

Er zijn verschillende manieren om een notificatie te creëren en weer te geven. In de meeste scenario's is de eenvoudigste benadering om een van de statische `show` methodes aan te roepen die de volledige levenscyclus van de notificatie encapsuleert.

### Voorbeeld: Een basisnotificatie weergeven {#example-displaying-a-basic-notification}

```java
// Basisnotificatie met titel en bericht
DesktopNotification.show("Update Beschikbaar", "Je download is voltooid!");
```

Deze one-liner creëert een notificatie met een titel en body, en probeert deze weer te geven.

## De notificatie aanpassen {#customizing-the-notification}

Er zijn verschillende opties voor het aanpassen van de look en feel van de weergegeven notificatie, afhankelijk van de behoeften van de app en het doel van de notificatie.

### Een aangepaste `Icon` instellen {#setting-a-custom-icon}

Standaard gebruikt de notificatie je gedefinieerde app-icoon via het [icons protocol](../managing-resources/assets-protocols#the-icons-protocol). Je kunt een aangepast icoon instellen met de `setIcon` methode. De component ondersteunt verschillende URL-schema's:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Opgehelderd als een context-URL die naar de resource-map van de app verwijst; afbeelding is base64-gecodeerd.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Opgehelderd als een webserver-URL, wat een volledig gekwalificeerde URL geeft.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Opgehelderd als een icons-URL.

**Voorbeeld:**

```java
// Een notificatie creëren met een aangepaste icoon-URL
DesktopNotification notification = new DesktopNotification(
  "Herinnering", "Vergadering begint over 10 minuten."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Notificatie evenementen {#notification-events}

De `DesktopNotification` ondersteunt verschillende levenscyclus gebeurtenissen, en luisteraars kunnen worden toegevoegd om gebeurtenissen af te handelen, zoals wanneer een notificatie wordt weergegeven, gesloten, geklikt of een fout tegenkomt.

| Evenement            | Beschrijving                                          | Wanneer te gebruiken                                          |
|----------------------|------------------------------------------------------|-------------------------------------------------------------|
| **Open**             | Gevoed wanneer de notificatie wordt weergegeven.     | Log notificatieweergave, update UI, volg engagement.        |
| **Sluiten**         | Gevoed wanneer de notificatie wordt gesloten.        | Ruim resources op, log afwijzingen, voer vervolgacties uit. |
| **Fout**             | Gevoed wanneer er een fout optreedt met de notificatie of de gebruiker geen toestemming heeft gegeven.| Behandel fouten elegant, informeer de gebruiker, pas fallback aan. |
| **Klik**             | Gevoed wanneer de gebruiker op de notificatie klikt. | Navigeer naar een specifieke sectie, log interacties, herfocus de app. |

```java
DesktopNotification notification = new DesktopNotification("Alert", "Je hebt een nieuw bericht!")

// Voeg een evenementluisteraar toe voor het open evenement
notification.onOpen(event -> {
  System.out.println("Notificatie is door de gebruiker geopend.");
});

// Luister ook naar het klik evenement
notification.onClick(event -> {
  System.out.println("Notificatie geklikt.");
});
```

:::warning Klikgedrag
Browserbeveiligingsbeleid voorkomt dat de klikgebeurtenis van de notificatie automatisch je app-venster of tabblad in de focus brengt. Dit gedrag wordt afgedwongen door de browser en kan niet programmatisch worden omzeild. Als je app vereist dat het venster gefocust wordt, moet je gebruikers instrueren om binnen de app te klikken na interactie met de notificatie.
:::

## Beveiliging en compatibiliteit overwegingen {#security-and-compatibility-considerations}

Bij het gebruik van de **DesktopNotification** component, houd je de volgende punten in gedachten:

- **Beveiligingscontext:** Je app moet via HTTPS worden aangeboden om ervoor te zorgen dat notificaties zijn toegestaan door de meeste moderne browsers.
- **Vereiste gebruikersgebaar:** Notificaties worden alleen weergegeven na een door de gebruiker geactiveerde actie. Het simpelweg laden van een pagina activeert geen notificatie.
- **Browserbeperkingen:** Niet alle browsers behandelen aangepaste iconen of focusgedrag op dezelfde manier. Bijvoorbeeld, aangepaste iconen werken mogelijk niet in Safari, terwijl het gedrag van evenementen kan variëren in andere browsers.
- **Toestemmingen:** Controleer altijd of je app op een elegante manier om notificatietoestemming van de gebruiker vraagt.

## Beste gebruikspraktijken {#usage-best-practices}

Houd de volgende beste praktijken in gedachten bij het gebruik van de `DesktopNotification` component in je app:

- **Informeer je gebruikers:** Laat gebruikers weten waarom notificaties nodig zijn en hoe ze hiervan kunnen profiteren.
- **Bied fallback opties aan:** Aangezien sommige browsers notificaties kunnen beperken, overweeg alternatieve manieren om gebruikers te waarschuwen (bijv. in-app berichten).
- **Foutafhandeling:** Registreer altijd een foutluisteraar om scenario's waar notificaties falen op een elegante manier te beheren.
