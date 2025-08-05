---
title: Toast
sidebar_position: 140
_i18n_hash: 7350867dde3a34f2c5fe2e40c4d745c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast`-melding is een subtiele en onopdringerige pop-upmelding die is ontworpen om gebruikers voorzien van realtime feedback en informatie. Deze meldingen worden typisch gebruikt om gebruikers te informeren over handelingen zoals succesvolle acties, waarschuwingen of fouten, zonder hun workflow te onderbreken. `Toast`-meldingen verdwijnen doorgaans na een bepaalde tijd en vereisen geen reactie van de gebruiker.

Met de `Toast`-component van webforJ kun je deze meldingen eenvoudig implementeren om de gebruikerservaring te verbeteren door relevante informatie op een vertrouwde, niet-opdringerige en naadloze manier te bieden.

## Basisprincipes {#basics}

webforJ biedt een snelle en gemakkelijke manier om een `Toast`-component te creëren in een enkele regel code met de methode `Toast.show()`, die een `Toast`-component aanmaakt, deze toevoegt aan het `Frame`, en deze weergeeft. Je kunt parameters aan de `show`-methode doorgeven om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol voltooid!", Theme.SUCCESS);
```

Als je meer controle over de component wilt, kun je ook een `Toast` maken met een standaardconstructor en de `open()`-methode gebruiken om deze weer te geven.

```java
Toast toast = new Toast("Operatie succesvol voltooid!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Standaard Gedrag
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()`-methode aanroept, wordt de `Toast` automatisch aan het eerste app `Frame` gehecht.
:::

Toasts zijn veelzijdig en bieden subtiele meldingen voor realtime feedback. Bijvoorbeeld:

- **Realtime feedback** voor acties zoals formulierindieningen, gegevensopslag of fouten.
- **Aanpasbare thema's** om te differentiëren tussen succes-, fout-, waarschuwing- of informatieve berichten.
- **Flexibele plaatsingsopties** om meldingen op verschillende plaatsen op het scherm weer te geven zonder de workflow van de gebruiker onder te breken.

## Duur {#duration}

Je kunt `Toast`-meldingen configureren om na een bepaalde duur te verdwijnen of op het scherm te blijven totdat ze worden weggehaald, afhankelijk van je behoeften. Je kunt de duur aanpassen met de `setDuration()`-methode, of eenvoudig een duurparameter aan de constructor of de `show()`-methode doorgeven.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setDuration(10000);
toast.open();
```

### Blijvende toasts {#persistent-toasts}

Je kunt een blijvende `Toast` maken door een negatieve duur in te stellen. Blijvende `Toast`-meldingen sluiten niet automatisch, wat nuttig kan zijn voor kritieke waarschuwingen of in gevallen waar enige interactie of bevestiging van de gebruiker vereist is.

:::caution
Wees voorzichtig met blijvende `Toast`-meldingen en zorg ervoor dat je een manier biedt voor de gebruiker om de melding te verbergen. Gebruik de `close()`-methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Plaatsing {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de melding op het scherm verschijnt om te voldoen aan de ontwerp- en bruikbaarheidsvereisten van je app. Standaard verschijnen `Toast`-meldingen onderaan het midden van het scherm.

Je kunt de `plaatsing` van een `Toast`-melding instellen met de `setPlacement`-methode met behulp van de `Toast.Placement`-enumeratie met een van de volgende waarden:

- **BOTTOM**: Plaatst de melding onderaan het midden van het scherm.
- **BOTTOM_LEFT**: Plaatst de melding in de linkerbenedenhoek van het scherm.
- **BOTTOM_RIGHT**: Plaatst de melding in de rechterbenedenhoek van het scherm.
- **TOP**: Plaatst de melding bovenaan het midden van het scherm.
- **TOP_LEFT**: Plaatst de melding in de linker bovenhoek van het scherm.
- **TOP_RIGHT**: Plaatst de melding in de rechter bovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-melding te controleren op basis van de ontwerp- en bruikbaarheidsbehoeften van je app.

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Door de plaatsing van je `Toast`-meldingen aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die geschikt is voor elke gegeven app, schermlay-out en context.

## Stapelen {#stacking}

De `Toast`-component kan meerdere meldingen gelijktijdig weergeven, waarbij ze verticaal op elkaar gestapeld worden op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de rand van de plaatsing, waardoor oudere meldingen verder weg worden geduwd. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs wanneer er veel gaande is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-meldingen standaard geen gebruikersinteractie vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan simpele meldingen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Door dit soort interactiviteit toe te voegen, kun je gebruikers in staat stellen om taken af te handelen en acties uit te voeren zonder dat ze van hun huidige scherm hoeven te navigeren, waardoor een `Toast`-melding verandert in een waardevol kanaal van interactie en betrokkenheid.

## Stijling {#styling}

Je kunt `Toast`-meldingen stylen met thema's, net als andere webforJ-componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt getoond, en een consistente stijl in je app ontstaat. Je kunt het thema instellen wanneer je de `Toast` aanmaakt of de `setTheme()`-methode gebruiken.

```java
Toast toast = new Toast("Voorbeeldmelding", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's, kun je je eigen aangepaste thema's voor `Toast`-meldingen maken. Dit zorgt voor een meer gepersonaliseerde en gebrandmerkte gebruikerservaring, waarbij je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van de component wijzigen. Het volgende voorbeeld toont hoe je een `Toast` met een aangepast thema kunt maken met webforJ.

:::info `Toast` Targeting
Omdat de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met behulp van CSS-variabelen. Deze variabelen maken het gemakkelijk om consistente aangepaste stijlen toe te passen op alle Toast-meldingen.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
