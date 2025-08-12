---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

Het webforJ-framework ondersteunt zeven verschillende typen veldcomponenten, elk met verschillende gedragingen en implementaties die geschikt zijn voor verschillende behoeften voor invoer. Terwijl elk van deze componenten variaties in hun implementaties heeft, beschrijft dit artikel de gedeelde eigenschappen onder alle veldklassen.

:::info
Deze sectie beschrijft de gemeenschappelijke kenmerken van verschillende veldcomponenten in webforJ, en is zelf geen klasse die kan worden geïnstalleerd en gebruikt.
:::

## Gedeelde veld eigenschappen {#shared-field-properties}

### Label {#label}

Een veldlabel is een beschrijvende tekst of titel die is gekoppeld aan het veld en kan worden gedefinieerd in de constructor of door gebruik te maken van de `setLabel()`-methode. Labels bieden een korte uitleg of prompt om gebruikers te helpen de doelstelling of verwachte invoer voor dat specifieke veld te begrijpen. Veldlabels zijn belangrijk voor bruikbaarheid en spelen een cruciale rol in toegankelijkheid, omdat ze schermlezers en ondersteunende technologieën in staat stellen om nauwkeurige informatie te geven en de navigatie met het toetsenbord te vergemakkelijken.

### Hulptekst {#helper-text}

Elk veld kan hulptekst onder de invoer weergeven met behulp van de `setHelperText()`-methode. Deze hulptekst biedt extra context of uitleg over de beschikbare invoeren, zodat gebruikers de nodige informatie hebben om weloverwogen keuzes te maken.

### Vereist {#required}

Je kunt de `setRequired(true)`-methode aanroepen om gebruikers te verplichten een waarde op te geven voordat ze een formulier indienen. Deze eigenschap werkt samen met het veldlabel en biedt een visuele indicatie dat een veld benodigd is. Deze visuele aanwijzing helpt personen om formulieren nauwkeurig in te vullen.

:::info
Veldcomponenten bevatten ingebouwde visuele validatie om gebruikers te informeren wanneer een verplicht veld leeg is of wanneer een gebruiker een waarde heeft verwijderd.
:::

### Spellingcontrole {#spellcheck}

Door `setSpellCheck(true)` te gebruiken, kun je de browser of gebruikersagent toestaan om de spelling van de tekst die door de gebruiker is ingevoerd te verifiëren en eventuele fouten te identificeren.

### Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties om de functionaliteit van veldcomponenten te verbeteren. Je kunt pictogrammen, labels, laadspinners, wissen/resetten mogelijkheden, avatars/profielafbeeldingen en andere nuttige componenten binnen een veld nestelen om de bedoelde betekenis voor gebruikers verder te verduidelijken. Veldcomponenten hebben twee slots: de `prefix` en `suffix`. Gebruik de methoden `setPrefixComponent()` en `setSuffixComponent()` om verschillende componenten vóór en na de weergegeven optie binnen een veld in te voegen. Hier is een voorbeeld met het `TextField`-veld:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

:::info
Omdat alle veldcomponenten zijn opgebouwd uit een enkele webcomponent, delen ze allemaal de
volgende Shadow Parts en CSS-eigenschapwaarden.
:::

<TableBuilder name="Field" />

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
