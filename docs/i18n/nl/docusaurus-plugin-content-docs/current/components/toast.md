---
title: Toast
sidebar_position: 140
_i18n_hash: 2027a7fa9671b2b8eb47a3f173ca6f41
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Een `Toast`-melding is een subtiele en niet-opdringerige pop-upmelding die is ontworpen om gebruikers real-time feedback en informatie te geven. Deze meldingen worden doorgaans gebruikt om gebruikers te informeren over operaties zoals succesvolle acties, waarschuwingen of fouten zonder hun workflow te onderbreken. `Toast`-meldingen verdwijnen meestal na een bepaalde tijd en vereisen geen gebruikersreactie.

Met de `Toast`-component van webforJ kun je eenvoudig deze meldingen implementeren om de gebruikerservaring te verbeteren door relevante informatie op een vertrouwde, niet-opdringerige en naadloze manier te bieden. 

## Basics {#basics}

webforJ biedt een snelle en gemakkelijke manier om een `Toast`-component te maken in een enkele regel code met de `Toast.show()`-methode, die een `Toast`-component creëert, deze aan het `Frame` toevoegt en deze weergeeft. Je kunt parameters doorgeven aan de `show`-methode om de weergegeven `Toast` te configureren:

```java
Toast.show("Operatie succesvol voltooid!", Theme.SUCCESS);
```

Als je meer gedetailleerde controle wilt over de component, kun je ook een `Toast` maken met een standaardconstructor en de `open()`-methode gebruiken om deze weer te geven.

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
In tegenstelling tot andere componenten hoeft een `Toast` niet expliciet aan een container zoals een `Frame` te worden toegevoegd. Wanneer je de `open()`-methode aanroept, wordt de `Toast` automatisch aan het eerste app-`Frame` gehecht.
:::

Toasts zijn veelzijdig en bieden subtiele meldingen voor real-time feedback. Bijvoorbeeld:

- **Real-time feedback** voor acties zoals formulierindieningen, gegevensopslag of fouten.
- **Aanpasbare thema's** voor het onderscheiden van succes-, fout-, waarschuwings- of informatiemeldingen.
- **Flexibele plaatsing** opties om meldingen op verschillende plaatsen op het scherm weer te geven zonder de workflow van de gebruiker te onderbreken.

## Duration {#duration}

Je kunt `Toast`-meldingen configureren zodat ze na een bepaalde duur verdwijnen of op het scherm blijven totdat ze worden weggewerkt, afhankelijk van je behoeften. Je kunt de duur aanpassen met de `setDuration()`-methode, of eenvoudig een duurparameter doorgeven aan de constructor of de `show()`-methode.

:::info Standaard Duur
Standaard sluit een `Toast` automatisch na 5000 milliseconden.
:::

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setDuration(10000);
toast.open();
```

### Persistente toasts {#persistent-toasts}

Je kunt een persistente `Toast` maken door een negatieve duur in te stellen. Persistente `Toast`-meldingen sluiten niet automatisch, wat handig kan zijn voor kritieke waarschuwingen of in gevallen waarin enige interactie of bevestiging van de gebruiker vereist is.

:::caution
Wees voorzichtig met persistente `Toast`-meldingen en zorg ervoor dat je een manier biedt voor de gebruiker om de melding te sluiten. Gebruik de `close()`-methode om de `Toast` te verbergen zodra de gebruiker deze heeft erkend of enige vereiste interactie heeft voltooid.
:::

```java
Toast toast = new Toast("Operatie succesvol voltooid!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Placement {#placement}

Met de `Toast`-component van webforJ kun je kiezen waar de melding op het scherm verschijnt om te voldoen aan het ontwerp en de gebruiksvriendelijkheid van je app. Standaard verschijnen `Toast`-meldingen onderaan het scherm in het midden. 

Je kunt de `placement` van een `Toast`-melding instellen met de `setPlacement`-methode met behulp van de `Toast.Placement` enum met een van de volgende waarden:

- **BOTTOM**: Plaatst de melding onderaan in het midden van het scherm.
- **BOTTOM_LEFT**: Plaatst de melding in de linkerbovenhoek van het scherm.
- **BOTTOM_RIGHT**: Plaatst de melding in de rechterbovenhoek van het scherm.
- **TOP**: Plaatst de melding bovenaan in het midden van het scherm.
- **TOP_LEFT**: Plaatst de melding in de linkerbovenhoek van het scherm.
- **TOP_RIGHT**: Plaatst de melding in de rechterbovenhoek van het scherm.

Deze opties stellen je in staat om de plaatsing van de `Toast`-melding te controleren op basis van de ontwerpeisen en gebruiksvriendelijkheid van je app.

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

Door de plaatsing van je `Toast`-meldingen aan te passen, kun je ervoor zorgen dat gebruikers informatie ontvangen op een manier die passend is voor elke app, schermlay-out en context.

## Stacking {#stacking}

De `Toast`-component kan meerdere meldingen tegelijkertijd weergeven, waarbij ze verticaal worden gestapeld op basis van hun plaatsing. Nieuwere meldingen verschijnen dichter bij de plaatsingsrand, terwijl oudere meldingen verder weg worden geduwd. Dit zorgt ervoor dat gebruikers belangrijke informatie niet missen, zelfs niet wanneer er veel aan de hand is.

## Acties en Interactiviteit {#actions-and-interactivity}

Hoewel `Toast`-meldingen standaard geen gebruikersinteractie vereisen, stelt webforJ je in staat om knoppen of andere interactieve elementen toe te voegen om ze nuttiger te maken dan eenvoudige meldingen. 

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Door deze soort interactiviteit toe te voegen, kun je gebruikers de mogelijkheid geven om taken te beheren en acties uit te voeren zonder weg te navigeren van hun huidige scherm, en een `Toast`-melding omvormen tot een waardevol kanaal van interactie en betrokkenheid. 

## Styling {#styling}

Je kunt `Toast`-meldingen stylen met thema's, net als andere webforJ-componenten, waardoor gebruikers waardevolle context krijgen over het type informatie dat wordt weergegeven, en een consistente stijl door je app heen creëren. Je kunt het thema instellen wanneer je de `Toast` maakt of de `setTheme()`-methode gebruiken.

```java
Toast toast = new Toast("Voorbeeldmelding", Theme.INFO);
```

```java
Toast toast = new Toast("Voorbeeldmelding");
toast.setTheme(Theme.INFO);
```

### Aangepaste thema's {#custom-themes}

Naast het gebruik van ingebouwde thema's kun je je eigen aangepaste thema's voor `Toast`-meldingen maken. Dit zorgt voor een meer gepersonaliseerde en gemarkeerde gebruikerservaring, waardoor je volledige controle hebt over de algehele styling van de `Toast`.

Om een aangepast thema aan een `Toast` toe te voegen, kun je aangepaste CSS-variabelen definiëren, die het uiterlijk van de component wijzigen. Het volgende voorbeeld laat zien hoe je een `Toast` kunt maken met een aangepast thema met behulp van webforJ.

:::info `Toast` Targeting
Aangezien de `Toast` zich niet op een specifieke positie in de DOM bevindt, kun je deze targeten met CSS-variabelen. Deze variabelen maken het eenvoudig om consistente aangepaste stijlen toe te passen op alle `Toast`-meldingen.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
