---
title: Toast
sidebar_position: 140
_i18n_hash: 0ac4df1a045e2706f2e9309327ba4683
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke notificatie die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts vertonen berichten zoals succesbevestigingen, waarschuwingen of fouten zonder de huidige workflow te onderbreken en verdwijnen automatisch na een ingestelde duur.

<!-- INTRO_END -->

## Basisprincipes {#basics}

webforJ biedt een snelle en eenvoudige manier om een `Toast` component te creëren in een enkele regel code met de `Toast.show()` methode, die een `Toast` component aanmaakt, deze toevoegt aan het `Frame` en deze weergeeft. Je kunt parameters aan de `show` methode doorgeven om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol voltooid!", Theme.SUCCESS);
```

Als je meer gecontroleerde aanpassing wilt over het component, kun je ook een `Toast` maken met een standaardconstructor en de `open()` methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol voltooid!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Standaardgedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()` methode aanroept, wordt de `Toast` automatisch bevestigd aan het eerste app `Frame`.
:::

Toasts zijn veelzijdig en bieden subtiele meldingen voor realtime feedback. Bijvoorbeeld:

- **Realtime feedback** voor acties zoals formulierinzendingen, gegevensopslag of fouten.
- **Aanpasbare thema's** voor het onderscheiden van succes-, fout-, waarschuwing- of informatiemeldingen.
- **Flexibele plaatsingsopties** om meldingen op verschillende gebieden van het scherm weer te geven zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast` notificaties configureren om na een bepaalde duur te verdwijnen of op het scherm te blijven totdat ze worden afgewezen, afhankelijk van jouw behoeften. Je kunt de duur aanpassen met de `setDuration()` methode, of eenvoudig een duurparameter aan de constructor of de `show()` methode doorgeven.

:::info Standaardduur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setDuration(10000);
toast.open();
```

### Persistente toasts {#persistent-toasts}

Je kunt een persistente `Toast` creëren door een negatieve duur in te stellen. Persistente `Toast` notificaties sluiten niet automatisch, wat nuttig kan zijn voor kritieke waarschuwingen of in gevallen waar enige interactie of erkenning van de gebruiker vereist is.

:::caution
Wees voorzichtig met persistente `Toast` notificaties en zorg ervoor dat je de gebruiker een manier biedt om de notificatie te sluiten. Gebruik de `close()` methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met webforJ's `Toast` component kun je kiezen waar de notificatie op het scherm verschijnt om te voldoen aan het ontwerp en de bruikbaarheidseisen van jouw app. Standaard verschijnen `Toast` notificaties in het midden onderaan het scherm. 

Je kunt de `plaatsing` van een Toast-notificatie instellen met de `setPlacement` methode met de `Toast.Placement` enum en een van de volgende waarden:

- **BOVEN**: Plaatst de notificatie in het midden bovenaan het scherm.
- **ONDER_LINKS**: Plaatst de notificatie in de linkerbenedenhoek van het scherm.
- **ONDER_RECHTS**: Plaatst de notificatie in de rechterbenedenhoek van het scherm.
- **BOVEN**: Plaatst de notificatie in het midden bovenaan het scherm.
- **BOVEN_LINKS**: Plaatst de notificatie in de linker bovenhoek van het scherm.
- **BOVEN_RECHTS**: Plaatst de notificatie in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast` notificatie te regelen op basis van de ontwerp en bruikbaarheid behoeften van je app.

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Door de plaatsing van je `Toast` notificaties aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke gegeven app, schermindeling en context.

## Stapelen {#stacking}

De `Toast` component kan meerdere meldingen tegelijkertijd weergeven, waarbij ze verticaal worden gestapeld op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de rand van de plaatsing, waardoor oudere meldingen verder weg worden gedrukt. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs niet wanneer er veel gebeurt.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast` notificaties standaard geen interactie van de gebruiker vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan eenvoudige meldingen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Door deze vorm van interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken af te handelen en acties uit te voeren zonder dat ze van hun huidige scherm hoeven te navigeren, waardoor een `Toast` notificatie verandert in een waardevol kanaal van interactie en betrokkenheid.

## Stijlen {#styling}

Je kunt `Toast` notificaties stylen met thema's, net zoals andere webforJ componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt getoond, en een consistente stijl door je app wordt gecreëerd. Je kunt het thema instellen wanneer je de Toast maakt of de `setTheme()` methode gebruiken.

```java
Toast toast = new Toast("Voorbeeld Notificatie", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's, kun je je eigen aangepaste thema's voor `Toast` notificaties creëren. Dit zorgt voor een meer gepersonaliseerde en gebrandmerkte gebruikerservaring, zodat je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van het component wijzigen. In het volgende voorbeeld wordt aangetoond hoe je een `Toast` met een aangepast thema kunt maken met webforJ.

:::info `Toast` Targeting
Omdat de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met behulp van CSS-variabelen. Deze variabelen maken het gemakkelijk om consistente aangepaste stijlen toe te passen op alle Toast notificaties.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
