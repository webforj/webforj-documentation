---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: d46665cad03749940ad45e5be8b3991d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke notificatie die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts tonen berichten zoals succesbevestigingen, waarschuwingen of fouten zonder de huidige workflow te onderbreken, en verdwijnen automatisch na een ingestelde duur.

<!-- INTRO_END -->

De methode `Toast.show()` maakt een `Toast`, voegt deze toe aan het `Frame` en toont deze in één enkele regel code. Geef parameters door aan `show()` om de `Toast` die verschijnt te configureren:

```java
Toast.show("De operatie is succesvol voltooid!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle over de component wilt, kun je ook een `Toast` maken met een standaardconstructor en de methode `open()` gebruiken om deze te tonen.

```java
Toast toast = new Toast("De operatie is succesvol voltooid!", 3000, Theme.SUCCESS, Placement.TOP);
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
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de methode `open()` aanroept, wordt de `Toast` automatisch aan het eerste app `Frame` toegevoegd.
:::

Toasts zijn veelzijdig en bieden subtiele notificaties voor realtime feedback. Bijvoorbeeld:

- **Realtime feedback** voor acties zoals formulierindieningen, dataopslag of fouten.
- **Aanpasbare thema's** voor het onderscheiden van succes-, fout-, waarschuwings- of informatieve berichten.
- **Flexibele plaatsing** opties om notificaties in verschillende gebieden van het scherm te tonen zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast`-notificaties zo configureren dat ze verdwijnen na een ingestelde duur of op het scherm blijven totdat ze worden afgewezen, afhankelijk van je behoeften. Je kunt de duur aanpassen met de methode `setDuration()`, of eenvoudig een duurparameter doorgeven aan de constructor of de `show()`-methode.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setDuration(10000);
toast.open();
```

### Voortdurende toasts {#persistent-toasts}

Je kunt een voortdurende `Toast` maken door een negatieve duur in te stellen. Voortdurende `Toast`-notificaties sluiten automatisch niet, wat handig kan zijn voor kritieke waarschuwingen of in gevallen waar enige interactie of bevestiging van de gebruiker vereist is.

:::caution
Wees voorzichtig met voortdurende `Toast`-notificaties en zorg ervoor dat je een manier biedt voor de gebruiker om de notificatie te sluiten. Gebruik de methode `close()` om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("De operatie is succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de notificatie op het scherm verschijnt, zodat deze voldoet aan de ontwerp- en bruikbaarheidsvereisten van je app. Standaard verschijnen `Toast`-notificaties onderaan het midden van het scherm.

Je kunt de `plaatsing` van een Toast-notificatie instellen met de methode `setPlacement` met behulp van de `Toast.Placement` enum met een van de volgende waarden:

- **BOTTOM**: Plaatst de notificatie onderaan het midden van het scherm.
- **BOTTOM_LEFT**: Plaatst de notificatie in de linkerbenedenhoek van het scherm.
- **BOTTOM_RIGHT**: Plaatst de notificatie in de rechterbenedenhoek van het scherm.
- **CENTER**: Plaatst de notificatie in het midden van het scherm.
- **TOP**: Plaatst de notificatie aan de bovenkant van het midden van het scherm.
- **TOP_LEFT**: Plaatst de notificatie in de linkerbovenhoek van het scherm.
- **TOP_RIGHT**: Plaatst de notificatie in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-notificatie aan te passen op basis van de ontwerp- en bruikbaarheidsbehoeften van je app.

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Door de plaatsing van je `Toast`-notificaties aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke gegeven app, schermindeling en context.

## Stapelen {#stacking}

De `Toast`-component kan meerdere notificaties tegelijkertijd weergeven, waarbij ze verticaal worden gestapeld op basis van hun plaatsing. Nieuwere notificaties verschijnen dichter bij de rand van de plaatsing, waardoor oudere notificaties verder weg worden geduwd. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs wanneer er veel aan de hand is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-notificaties standaard geen interactie van de gebruiker vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan eenvoudige notificaties.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Door deze vorm van interactiviteit toe te voegen, kun je gebruikers de mogelijkheid bieden om taken te verwerken en acties uit te voeren zonder dat ze van hun huidige scherm moeten navigeren, waardoor een `Toast`-notificatie een waardevol kanaal voor interactie en betrokkenheid wordt.

## Styling {#styling}

Je kunt `Toast`-notificaties stylen met thema's, net als andere webforJ-componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt weergegeven en een consistente stijl in je app wordt gecreëerd. Je kunt het thema instellen wanneer je de Toast maakt of de methode `setTheme()` gebruiken.

```java
Toast toast = new Toast("Voorbeeld Notificatie", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's kun je je eigen aangepaste thema's voor `Toast`-notificaties maken. Dit zorgt voor een meer gepersonaliseerde en merkspecifieke gebruikerservaring, waarbij je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van de component wijzigen. Het volgende voorbeeld laat zien hoe je een `Toast` kunt maken met een aangepast thema met behulp van webforJ.

:::info `Toast` Targeting
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met CSS-variabelen. Deze variabelen maken het eenvoudig om consistente aangepaste stijlen toe te passen op alle Toast-notificaties.
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
