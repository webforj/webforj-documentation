---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
description: >-
  Share common field properties across webforJ input components, including
  labels, helper text, required state, spellcheck, and prefix or suffix slots.
_i18n_hash: 56462cf4d659ce15f72a624acae60f0f
---
<JavadocLink type="foundation" location="com/webforj/component/field/DwcField"/>

Het webforJ-framework ondersteunt elf verschillende soorten veldcomponenten, waaronder vier gemaskeerde velden, elk met verschillende gedragingen en implementaties die passen bij verschillende invoerbehoeften. Hoewel elk van deze componenten variaties heeft in hun implementaties, beschrijft dit artikel de gedeelde eigenschappen onder de veldklassen.

:::info
Deze sectie beschrijft gemeenschappelijke kenmerken van verschillende veldcomponenten in webforJ en is zelf geen klasse die kan worden geïnstantieerd en gebruikt.
:::

## Gedeelde veld eigenschappen {#shared-field-properties}

### Label {#label}

Een veldlabel is een beschrijvende tekst of titel die aan het veld is gekoppeld en die kan worden gedefinieerd in de constructor of door gebruik te maken van de `setLabel()`-methode. Labels bieden een korte uitleg of aanwijzing om gebruikers te helpen de functie of verwachte invoer voor dat specifieke veld te begrijpen. Veldlabels zijn belangrijk voor de bruikbaarheid en spelen een cruciale rol in toegankelijkheid, omdat ze schermlezers en ondersteunende technologieën in staat stellen om nauwkeurige informatie te bieden en het navigeren met het toetsenbord te vergemakkelijken.

### Helpertekst {#helper-text}

Elk veld kan helpertekst onder de invoer weergeven met behulp van de `setHelperText()`-methode. Deze helpertekst biedt aanvullende context of uitleg over de beschikbare invoeren, zodat gebruikers de nodige informatie hebben om weloverwogen keuzes te maken.

### Verplicht {#required}

Je kunt de `setRequired(true)`-methode aanroepen om gebruikers te verplichten een waarde op te geven voordat ze een formulier indienen. Deze eigenschap werkt samen met het veldlabel en biedt een visuele indicatie dat een veld noodzakelijk is. Deze visuele aanwijzing helpt individuen om formulieren nauwkeurig in te vullen.

:::info
Veldcomponenten bevatten ingebouwde visuele validatie om gebruikers te waarschuwen wanneer een verplicht veld leeg is of als een gebruiker een waarde heeft verwijderd.
:::

### Spellingscontrole {#spellcheck}

Door `setSpellCheck(true)` te gebruiken, kun je de browser of gebruikersagent toestaan de spelling van de tekst die door de gebruiker is ingevoerd te verifiëren en eventuele fouten te identificeren.

### Voorvoegsel en achtervoegsel {#prefix-and-suffix}

Slots bieden flexibele opties om de mogelijkheden van veldcomponenten te verbeteren. Je kunt pictogrammen, labels, laadspinners, wissen/reset-functies, avatar/profielafbeeldingen en andere nuttige componenten binnen een veld nestelen om de bedoelde betekenis verder te verduidelijken voor gebruikers. Velden hebben twee slots: de `prefix` en `suffix` slots. Gebruik de `setPrefixComponent()` en `setSuffixComponent()`-methoden om verschillende componenten vóór en na de weergegeven optie binnen een veld in te voegen. Hier is een voorbeeld met het `TextField`-veld:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Stijl {#styling}

:::info
Omdat alle veldcomponenten zijn opgebouwd uit één enkele webcomponent, delen ze allemaal de
volgende Shadow Parts en CSS Property waarden.
:::

<TableBuilder name="Field" />

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
