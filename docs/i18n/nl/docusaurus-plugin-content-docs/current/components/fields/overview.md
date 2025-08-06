---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: b04acdedbd800790417edfe940160bf2
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

Het webforJ-framework ondersteunt zeven verschillende soorten veldcomponenten, elk met verschillende gedragingen en implementaties die passen bij diverse behoeften voor invoer. Hoewel elk van deze componenten variaties heeft in hun implementaties, beschrijft dit artikel de gedeelde eigenschappen onder alle veldklassen.

:::info
Dit gedeelte beschrijft gemeenschappelijke kenmerken van verschillende veldcomponenten in webforJ, en is zelf geen klasse die kan worden geïnstantieerd en gebruikt.
:::

## Gedeelde veld eigenschappen {#shared-field-properties}

### Label {#label}

Een veldlabel is een beschrijvende tekst of titel die is gekoppeld aan het veld en die kan worden gedefinieerd in de constructor of met de `setLabel()`-methode. Labels bieden een korte uitleg of aanwijzing om gebruikers te helpen het doel of de verwachte invoer voor dat specifieke veld te begrijpen. Veldlabels zijn belangrijk voor de bruikbaarheid en spelen een cruciale rol in toegankelijkheid, omdat ze schermlezers en hulpmiddelen in staat stellen om nauwkeurige informatie te geven en de navigatie met het toetsenbord te vergemakkelijken.

### Helpertekst {#helper-text}

Elk veld kan helpertekst onder de invoer weergeven met behulp van de `setHelperText()`-methode. Deze helpertekst biedt aanvullende context of uitleg over de beschikbare invoeren, zodat gebruikers de noodzakelijke informatie hebben om weloverwogen keuzes te maken.

### Vereist {#required}

Je kunt de `setRequired(true)`-methode aanroepen om van gebruikers te vereisen dat ze een waarde opgeven voordat ze een formulier indienen. Deze eigenschap werkt samen met het veldlabel en biedt een visuele indicatie dat een veld noodzakelijk is. Deze visuele aanwijzing helpt individuen om formulieren nauwkeurig in te vullen.

:::info
Veldcomponenten bevatten ingebouwde visuele validatie om gebruikers te waarschuwen wanneer een verplicht veld leeg is of wanneer een gebruiker een waarde heeft verwijderd.
:::

### Spellingcontrole {#spellcheck}

Door `setSpellCheck(true)` te gebruiken, kun je de browser of gebruikersagent toestaan om de spelling van de door de gebruiker ingevoerde tekst te verifiëren en eventuele fouten te identificeren.

### Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties voor het verbeteren van de mogelijkheden van veldcomponenten. Je kunt iconen, labels, laadspinners, wissen/herstellen mogelijkheden, avatar/profielafbeeldingen en andere nuttige componenten binnen een veld nestelen om verdere verduidelijking van de bedoelde betekenis aan gebruikers te geven. Veldcomponenten hebben twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()`-methoden om verschillende componenten vóór en na de weergegeven optie binnen een veld in te voegen. Hier is een voorbeeld met de `TextField`-component:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Stijlen {#styling}

:::info
Omdat alle veldcomponenten zijn opgebouwd uit een enkele webcomponent, delen ze allemaal de volgende Shadow Parts en CSS-Property waarden
:::

<TableBuilder name="Field" />

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
