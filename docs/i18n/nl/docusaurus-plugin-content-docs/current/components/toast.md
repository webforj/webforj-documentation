---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: 07365e349ec9393e79a13969504861bd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke melding die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts tonen berichten zoals bevestigingen van succes, waarschuwingen of fouten zonder de huidige workflow te onderbreken en verdwijnen automatisch na een bepaalde tijd.

<!-- INTRO_END -->

## Basis {#basics}

webforJ biedt een snelle en eenvoudige manier om een `Toast` component te maken in een enkele regel code met de `Toast.show()` methode, die een `Toast` component creëert, deze toevoegt aan het `Frame` en toont. Je kunt parameters doorgeven aan de `show` methode om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol afgerond!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle wilt over het component, kun je ook een `Toast` maken met een standaard constructor en de `open()` methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol afgerond!", 3000, Theme.SUCCESS, Placement.TOP);
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

:::info Standaardgedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()` methode aanroept, wordt de `Toast` automatisch gekoppeld aan het eerste app `Frame`.
:::

Toasts zijn veelzijdig en bieden subtiele meldingen voor real-time feedback. Bijvoorbeeld:

- **Real-time feedback** voor acties zoals formulierindieningen, gegevensopslag of fouten.
- **Aanpasbare thema's** om te onderscheiden tussen succes-, fout-, waarschuwing- of informatieve berichten.
- **Flexibele plaatsingsopties** om meldingen in verschillende delen van het scherm te tonen zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast` meldingen configureren om na een bepaalde tijd te verdwijnen of op het scherm te blijven totdat ze worden weggeklikt, afhankelijk van je behoeften. Je kunt de duur aanpassen met de `setDuration()` methode, of eenvoudig een duurparameter meegeven aan de constructor of de `show()` methode.

:::info Standaardduur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setDuration(10000);
toast.open();
```

### Handmatige toasts {#persistent-toasts}

Je kunt een handmatige `Toast` maken door een negatieve duur in te stellen. Handmatige `Toast` meldingen sluiten niet automatisch, wat nuttig kan zijn voor kritieke waarschuwingen of in gevallen waarin enige interactie of erkenning van de gebruiker vereist is.

:::caution
Wees voorzichtig met handmatige `Toast` meldingen en zorg ervoor dat er een manier is voor de gebruiker om de melding te sluiten. Gebruik de `close()` methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol afgerond!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast` component van webforJ kun je kiezen waar de melding op het scherm verschijnt, zodat deze past bij het ontwerp en de bruikbaarheidseisen van je app. Standaard verschijnen `Toast` meldingen onderaan in het midden van het scherm.

Je kunt de `plaatsing` van een `Toast` melding instellen met de `setPlacement` methode met behulp van de `Toast.Placement` enum met een van de volgende waarden:

- **ONDER**: Plaatst de melding onderaan in het midden van het scherm.
- **ONDER_LINKS**: Plaatst de melding in de linkerbenedenhoek van het scherm.
- **ONDER_RECHTS**: Plaatst de melding in de rechterbenedenhoek van het scherm.
- **BOVEN**: Plaatst de melding bovenaan in het midden van het scherm.
- **BOVEN_LINKS**: Plaatst de melding in de linkerbovenhoek van het scherm.
- **BOVEN_RECHTS**: Plaatst de melding in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast` melding te beheersen op basis van het ontwerp en de bruikbaarheid van je app.

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

Door de plaatsing van je `Toast` meldingen aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor iedere app, schermindeling en context.

## Stapelen {#stacking}

De `Toast` component kan meerdere meldingen gelijktijdig weergeven, door ze verticaal te stapelen op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de plaatsingsrand, waardoor oudere meldingen verder weg worden gedrukt. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs niet wanneer er veel gebeurt.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast` meldingen standaard geen gebruikersinteractie vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan simpele meldingen.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Door deze soort interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken af te handelen en acties uit te voeren zonder hun huidige scherm te verlaten, en een `Toast` melding om te vormen tot een waardevol kanaal voor interactie en betrokkenheid.

## Stijlen {#styling}

Je kunt `Toast` meldingen stylen met thema's, net als andere webforJ componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt weergegeven, en een consistente stijl door je app creëren. Je kunt het thema instellen wanneer je de `Toast` aanmaakt of de `setTheme()` methode gebruiken.

```java
Toast toast = new Toast("Voorbeeldmelding", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's kun je je eigen aangepaste thema's voor `Toast` meldingen creëren. Dit zorgt voor een meer gepersonaliseerde en merkgebonden gebruikerservaring, waardoor je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren die het uiterlijk van het component wijzigen. Het volgende voorbeeld laat zien hoe je een `Toast` met een aangepast thema kunt maken met webforJ.

:::info `Toast` Targeting
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met CSS-variabelen. Deze variabelen maken het eenvoudig om consistente aangepaste stijlen toe te passen op alle `Toast` meldingen.
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
