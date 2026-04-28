---
title: Toast
sidebar_position: 140
_i18n_hash: c98ff64ae02ebe46d84c803492685d05
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke notificatie die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts tonen berichten zoals bevestigingen van succes, waarschuwingen of fouten zonder de huidige workflow te onderbreken, en verdwijnen automatisch na een bepaalde tijd.

<!-- INTRO_END -->

## Basisprincipes {#basics}

webforJ biedt een snelle en eenvoudige manier om een `Toast`-component te maken in een enkele regel code met de `Toast.show()`-methode, die een `Toast`-component creëert, deze toevoegt aan het `Frame` en deze weergeeft. Je kunt parameters doorgeven aan de `show`-methode om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol afgerond!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle over de component wilt, kun je ook een `Toast` maken met een standaard constructor en de `open()`-methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol afgerond!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

:::info Standaard Gedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()`-methode aanroept, wordt de `Toast` automatisch aan het eerste app-`Frame` gehecht.
:::

Toasts zijn veelzijdig en bieden subtiele notificaties voor real-time feedback. Bijvoorbeeld:

- **Real-time feedback** voor acties zoals formulierindieningen, gegevensopslag of fouten.
- **Aanpasbare thema's** om te differencieren tussen succes-, fout-, waarschuwing- of informatieve berichten.
- **Flexibele plaatsingsopties** om notificaties in verschillende gebieden van het scherm weer te geven zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast`-notificaties configureren om na een bepaalde tijd te verdwijnen of op het scherm te blijven totdat ze worden afgeworpen, afhankelijk van je behoeften. Je kunt de duur aanpassen met de `setDuration()`-methode, of simpelweg een duurparameter doorgeven aan de constructor of de `show()`-methode.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setDuration(10000);
toast.open();
```

### Persistente toasts {#persistent-toasts}

Je kunt een persistente `Toast` maken door een negatieve duur in te stellen. Persistente `Toast`-notificaties sluiten niet automatisch, wat nuttig kan zijn voor kritieke waarschuwingen of in gevallen waarin enige interactie of erkenning van de gebruiker vereist is.

:::caution
Wees voorzichtig met persistente `Toast`-notificaties en zorg ervoor dat je een manier biedt voor de gebruiker om de notificatie af te wijzen. Gebruik de `close()`-methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol afgerond!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de notificatie op het scherm verschijnt om aan de ontwerp- en gebruiksvraag van je app te voldoen. Standaard verschijnen `Toast`-notificaties in het middenonder van het scherm. 

Je kunt de `plaatsing` van een Toast-notificatie instellen met de `setPlacement`-methode met behulp van de `Toast.Placement` enum met een van de volgende waarden:

- **ONDER**: Plaatst de notificatie in het middenonder van het scherm.
- **ONDER_LINKS**: Plaatst de notificatie in de linkerbenedenhoek van het scherm.
- **ONDER_RECHTS**: Plaatst de notificatie in de rechterbenedenhoek van het scherm.
- **BOVEN**: Plaatst de notificatie in het middenboven van het scherm.
- **BOVEN_LINKS**: Plaatst de notificatie in de linkerbovenhoek van het scherm.
- **BOVEN_RECHTS**: Plaatst de notificatie in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-notificatie aan te passen op basis van het ontwerp en de gebruiksvragen van je app.

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='600px'
/>

Door de plaatsing van je `Toast`-notificaties aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke app, schermlay-out en context.

## Stacking {#stacking}

De `Toast`-component kan meerdere notificaties tegelijk weergeven, waarbij ze verticaal worden gestapeld op basis van hun plaatsing. Nieuwere notificaties verschijnen dichter bij de rand waar ze geplaatst zijn, waardoor oudere notificaties verder weg worden geduwd. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs wanneer er veel gaande is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-notificaties standaard geen gebruikersinteractie vereisen, geeft webforJ je de mogelijkheid om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan simpele notificaties. 

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Door deze soort interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken af te handelen en acties uit te voeren zonder hun huidige scherm te verlaten, waardoor een `Toast`-notificatie verandert in een waardevol kanaal voor interactie en betrokkenheid. 

## Stijl {#styling}

Je kunt `Toast`-notificaties stylen met thema's, net als andere webforJ-componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt getoond en een consistente stijl in je app creëert. Je kunt het thema instellen wanneer je de Toast maakt of de `setTheme()`-methode gebruiken.

```java
Toast toast = new Toast("Voorbeeld Notificatie", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeld Notificatie");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's, kun je je eigen aangepaste thema's voor `Toast`-notificaties maken. Dit zorgt voor een meer gepersonaliseerde en gebrandmerkte gebruikerservaring, waardoor je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van de component aanpassen. Het volgende voorbeeld laat zien hoe je een `Toast` met een aangepast thema kunt maken met webforJ.

:::info `Toast` Targeting
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met behulp van CSS-variabelen. Deze variabelen maken het gemakkelijk om consistente aangepaste stijlen toe te passen op alle Toast-notificaties.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
