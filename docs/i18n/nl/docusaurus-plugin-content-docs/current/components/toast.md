---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: e0312bf77de08272221f84c9c231c2df
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke notificatie die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts tonen berichten zoals bevestigingen van succesvolle acties, waarschuwingen of fouten zonder de huidige workflow te onderbreken, en verdwijnen automatisch na een gestelde duur.

<!-- INTRO_END -->

De `Toast.show()`-methode creëert een `Toast`, voegt deze toe aan het `Frame` en toont deze in één enkele regel code. Geef parameters door aan `show()` om de `Toast` die verschijnt te configureren:

```java
Toast.show("Operatie succesvol voltooid!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle over de component wilt, kun je ook een `Toast` maken met een standaardconstructor en de `open()`-methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol voltooid!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Standaard Gedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()`-methode aanroept, wordt de `Toast` automatisch gehecht aan het eerste app `Frame`.
:::

Toasts zijn veelzijdig en bieden subtiele notificaties voor realtime feedback. Bijvoorbeeld:

- **Realtime feedback** voor acties zoals formulierindieningen, gegevensopslag of fouten.
- **Aanpasbare thema's** voor het onderscheiden van succes-, fout-, waarschuwing- of informatieve berichten.
- **Flexibele plaatsingsopties** om meldingen in verschillende gebieden van het scherm te tonen zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast`-meldingen configureren om na een gestelde duur te verdwijnen of op het scherm aan te blijven totdat ze worden weggehaald, afhankelijk van jouw behoeften. Je kunt de duur aanpassen met de `setDuration()`-methode, of gewoon een duurparameter doorgeven aan de constructor of de `show()`-methode.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeldmeldingen");
toast.setDuration(10000);
toast.open();
```

### Continue toasts {#persistent-toasts}

Je kunt een continue `Toast` maken door een negatieve duur in te stellen. Continue `Toast`-meldingen sluiten niet automatisch, wat handig kan zijn voor kritieke waarschuwingen of in gevallen waarin enige interactie of bevestiging van de gebruiker vereist is.

:::caution
Wees voorzichtig met continue `Toast`-meldingen en zorg ervoor dat je een manier biedt voor de gebruiker om de melding te sluiten. Gebruik de `close()`-methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de notificatie op het scherm verschijnt om aan de ontwerp- en bruikbaarheidseisen van je app te voldoen. Standaard verschijnen `Toast`-meldingen onderaan het midden van het scherm.

Je kunt de `plaatsing` van een Toast-melding instellen met de `setPlacement`-methode met behulp van de `Toast.Placement` enum met een van de volgende waarden:

- **ONDERAAN**: Plaatst de melding onderaan het midden van het scherm.
- **ONDERAAN_LINKS**: Plaatst de melding in de linkerbenedenhoek van het scherm.
- **ONDERAAN_RECHTS**: Plaatst de melding in de rechterbenedenhoek van het scherm.
- **BOVENAAN**: Plaatst de melding bovenaan het midden van het scherm.
- **BOVENAAN_LINKS**: Plaatst de melding in de linker bovenhoek van het scherm.
- **BOVENAAN_RECHTS**: Plaatst de melding in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-melding te controleren op basis van de ontwerp- en bruikbaarheidseisen van je app.

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Door de plaatsing van je `Toast`-meldingen aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke specifieke app, schermindeling en context.

## Stapeling {#stacking}

De `Toast`-component kan meerdere meldingen gelijktijdig weergeven, door ze verticaal te stapelen op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de plaatsingsrand, waardoor oudere meldingen verder weg worden gedrukt. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs niet wanneer er veel aan de hand is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-meldingen standaard geen gebruikersinteractie vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan eenvoudige meldingen.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Door deze vorm van interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken te verwerken en acties uit te voeren zonder de huidige schermweergave te verlaten, waardoor een `Toast`-melding kan worden omgevormd tot een waardevol kanaal voor interactie en betrokkenheid.

## Stylen {#styling}

Je kunt `Toast`-meldingen stylen met thema's, net zoals andere webforJ-componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt weergegeven, en er een consistente stijl door je app ontstaat. Je kunt het thema instellen wanneer je de Toast maakt of de `setTheme()`-methode gebruiken.

```java
Toast toast = new Toast("Voorbeeldmelding", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's, kun je je eigen aangepaste thema's voor `Toast`-meldingen creëren. Dit zorgt voor een meer gepersonaliseerde en gebrandmerkte gebruikerservaring, waardoor je volledige controle hebt over de algemene styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die de uitstraling van de component wijzigen. Het volgende voorbeeld laat zien hoe je een `Toast` kunt maken met een aangepast thema met behulp van webforJ.

:::info `Toast` Doel
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze richten met behulp van CSS-variabelen. Deze variabelen maken het eenvoudig om consistente aangepaste stijlen toe te passen op alle Toast-meldingen.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
