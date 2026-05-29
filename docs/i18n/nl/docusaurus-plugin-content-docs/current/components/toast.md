---
title: Toast
sidebar_position: 140
_i18n_hash: 563743d9f91aff0002f8965cbf719d99
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast` is een kleine, tijdelijke melding die verschijnt om gebruikers feedback te geven over een actie of gebeurtenis. Toasts tonen berichten zoals bevestigingen van succes, waarschuwingen of fouten zonder de huidige workflow te onderbreken, en verdwijnen automatisch na een ingestelde tijd.

<!-- INTRO_END -->

## Basisprincipes {#basics}

webforJ biedt een snelle en gemakkelijke manier om een `Toast`-component te creëren in een enkele regel code met de `Toast.show()`-methode, die een `Toast`-component aanmaakt, deze toevoegt aan het `Frame` en deze weergeeft. Je kunt parameters doorgeven aan de `show`-methode om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol voltooid!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle wilt over de component, kun je ook een `Toast` aanmaken met een standaardconstructor en de `open()`-methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol voltooid!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Standaard Gedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()`-methode aanroept, wordt de `Toast` automatisch aan het eerste app `Frame` gehecht.
:::

Toasts zijn veelzijdig en geven subtiele meldingen voor realtime feedback. Bijvoorbeeld:

- **Realtime feedback** voor acties zoals formulier indienen, gegevens opslaan of fouten.
- **Aanpasbare thema's** om te verschillen tussen succes-, fout-, waarschuwing- of informatiemeldingen.
- **Flexibele plaatsingsopties** om meldingen op verschillende gebieden van het scherm weer te geven zonder de workflow van de gebruiker te onderbreken.

## Duur {#duration}

Je kunt `Toast`-meldingen configureren om na een bepaalde tijd te verdwijnen of op het scherm te blijven totdat ze worden verworpen, afhankelijk van je behoeften. Je kunt de duur aanpassen met de `setDuration()`-methode of eenvoudig een duurparameter aan de constructor of de `show()`-methode doorgeven.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setDuration(10000);
toast.open();
```

### Permanente toasts {#persistent-toasts}

Je kunt een permanente `Toast` creëren door een negatieve duur in te stellen. Permanente `Toast`-meldingen sluiten niet automatisch, wat nuttig kan zijn voor kritieke waarschuwingen of in gevallen waarin enige interactie of erkenning van de gebruiker vereist is.

:::caution
Wees voorzichtig met permanente `Toast`-meldingen en zorg ervoor dat je een manier biedt voor de gebruiker om de melding te verwerpen. Gebruik de `close()`-methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of een vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de melding op het scherm verschijnt om te voldoen aan het ontwerp en de bruikbaarheidseisen van je app. Standaard verschijnen `Toast`-meldingen onderaan het scherm in het midden.

Je kunt de `plaatsing` van een `Toast`-melding instellen met de `setPlacement`-methode met behulp van de `Toast.Placement`-enum met een van de volgende waarden:

- **ONDEN**: Plaatst de melding onderaan in het midden van het scherm.
- **ONDEN_LINKS**: Plaatst de melding in de linkerbenedenhoek van het scherm.
- **ONDEN_RECHTS**: Plaatst de melding in de rechterbenedenhoek van het scherm.
- **BOVEN**: Plaatst de melding bovenaan in het midden van het scherm.
- **BOVEN_LINKS**: Plaatst de melding in de linker bovenhoek van het scherm.
- **BOVEN_RECHTS**: Plaatst de melding in de rechter bovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-melding aan te passen op basis van het ontwerp en de gebruiksbehoeften van je app.

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

Door de plaatsing van je `Toast`-meldingen aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke gegeven app, schermindeling en context.

## Stapelen {#stacking}

De `Toast`-component kan meerdere meldingen tegelijkertijd weergeven, waarbij ze verticaal worden gestapeld op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de rand van de plaatsing, waardoor oudere meldingen verder weg worden gedrukt. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs als er veel gaande is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-meldingen standaard geen gebruikersinteractie vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan eenvoudige meldingen. 

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Door dit soort interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken af te handelen en acties uit te voeren zonder hun huidige scherm te verlaten, waardoor een `Toast`-melding een waardevol kanaal voor interactie en betrokkenheid wordt. 

## Styling {#styling}

Je kunt `Toast`-meldingen stylen met thema's, net als andere webforJ-componenten, wat gebruikers waardevolle context biedt over het type informatie dat wordt getoond en een consistente stijl door je app creëert. Je kunt het thema instellen wanneer je de `Toast` maakt of de `setTheme()`-methode gebruiken.

```java
Toast toast = new Toast("Voorbeeldmelding", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's, kun je ook je eigen aangepaste thema's voor `Toast`-meldingen maken. Dit zorgt voor een meer gepersonaliseerde en merkgebonden gebruikerservaring, waarbij je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van de component wijzigen. Het volgende voorbeeld laat zien hoe je een `Toast` met een aangepast thema kunt maken met webforJ.

:::info `Toast` Doelgroepen
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met CSS-variabelen. Deze variabelen maken het gemakkelijk om consistente aangepaste stijlen toe te passen op alle Toast-meldingen.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
