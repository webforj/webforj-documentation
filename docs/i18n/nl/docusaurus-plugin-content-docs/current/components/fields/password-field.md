---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: 7bdcafe322080112206dd70e6a2146c7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

De `PasswordField` component stelt gebruikers in staat om veilig een wachtwoord in te voeren. Het wordt weergegeven als een teksteditor met één regel waarbij de ingevoerde tekst wordt verborgen, meestal vervangen door symbolen zoals sterretjes ("*") of punten ("•"). Het exacte symbool kan variëren, afhankelijk van de browser en het besturingssysteem.

<!-- INTRO_END -->

## Gebruik van `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld maakt een `PasswordField` met een label en plaatsaanduidingstekst.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Veldwaarde {#field-value}

De `PasswordField` component slaat zijn waarde op en haalt deze op als een gewone `String`, vergelijkbaar met een `TextField`, maar met verborgen visuele weergave om de karakters uit het zicht te houden.


Je kunt de huidige waarde ophalen met:

```java
passwordField.getValue();
```

:::warning gevoelige gegevens
Hoewel het veld de inhoud visueel maskeert, is de teruggegeven waarde van `getValue()` nog steeds een gewone string. Wees voorzichtig met dit wanneer je gevoelige gegevens verwerkt en versleutel of transformeer het vóór opslag.
:::

Om de waarde programmatisch in te stellen of opnieuw in te stellen:

```java
passwordField.setValue("MySecret123!");
```

Als er geen waarde is ingevoerd door de gebruiker en er geen standaardwaarde is ingesteld, retourneert het veld een lege string (`""`).

Dit gedrag lijkt op dat van het native HTML `<input type="password">`, waarbij de `value` eigenschap de huidige invoer bevat.


## Toepassingen {#usages}

De `PasswordField` is het best te gebruiken in scenario's waar het vastleggen of verwerken van gevoelige informatie, zoals wachtwoorden of andere vertrouwelijke gegevens, essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `PasswordField` moet gebruiken:

1. **Gebruikersauthenticatie en registratie**: Wachtwoordvelden zijn cruciaal in apps die gebruikersauthenticatie of registratieprocessen omvatten, waar veilige wachtwoordinvoer vereist is.

2. **Veilige formulierinvoer**: Bij het ontwerpen van formulieren die invoer van gevoelige informatie vereisen, zoals creditcardgegevens of persoonlijke identificatienummers (PIN's), zorgt een `PasswordField` ervoor dat deze gegevens veilig worden ingevoerd.

3. **Accountbeheer en profielinstellingen**: Wachtwoordvelden zijn waardevol in apps die accountbeheer of profielinstellingen omvatten, zodat gebruikers hun wachtwoorden veilig kunnen wijzigen of bijwerken.

## Wachtwoordzichtbaarheid {#password-visibility}

Gebruikers kunnen de waarde van de `PasswordField` onthullen door op het onthulicoon te klikken. Dit stelt gebruikers in staat te verifiëren wat ze hebben ingevoerd of de informatie naar hun klembord te kopiëren. Voor omgevingen met hoge beveiliging kun je `setPasswordReveal()` gebruiken om het onthulicoon te verwijderen en te voorkomen dat gebruikers de waarde zien. Je kunt controleren of een gebruiker het onthulicoon kan gebruiken om de waarde te tonen met de `isPasswordReveal()` methode.

## Patroonmatching {#pattern-matching}

Het wordt sterk aanbevolen om een reguliere-expressiepatroon op de `PasswordField` toe te passen met de `setPattern()` methode. Dit stelt je in staat om tekenregels en structurele vereisten af te dwingen, waardoor gebruikers gedwongen worden sterke en conforme inloggegevens te creëren. Patroonmatching is vooral nuttig bij het afdwingen van strenge wachtwoordregels, zoals het vereisen van een mix van hoofdletters en kleine letters, cijfers en symbolen.

Het patroon moet voldoen aan de syntaxis van een [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern gebruikt om validatie over alle Unicode-codepunten te garanderen. Voeg **geen** schuine strepen (`/`) rond het patroon toe.

In de volgende snippet vereist het patroon ten minste één kleine letter, één hoofdletter, één cijfer en een minimale lengte van 8 tekens.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Als het patroon ontbreekt of ongeldig is, wordt er geen validatie toegepast.

:::tip
Gebruik `setLabel()` om een duidelijke label te geven die het doel van het wachtwoordveld beschrijft. Om gebruikers te helpen de wachtwoordvereisten te begrijpen, gebruik `setHelperText()` om richtlijnen of regels direct onder het veld weer te geven.
:::


## Minimale en maximale lengte {#minimum-and-maximum-length}

Je kunt de toegestane lengte van de wachtwoordinvoer beheersen met `setMinLength()` en `setMaxLength()` op de `PasswordField`.

De `setMinLength()` methode definieert het minimum aantal tekens dat een gebruiker in het veld moet invoeren om aan de validatie te voldoen. Deze waarde moet een niet-negatief geheel getal zijn en mag niet groter zijn dan de maximale lengte als deze is ingesteld.

```java
passwordField.setMinLength(8); // Minimum 8 tekens
```

Als de gebruiker minder tekens invoert dan het minimum, faalt de invoer de constraintvalidatie. Deze validatie wordt alleen toegepast wanneer de waarde van het veld door de gebruiker wordt gewijzigd.

De `setMaxLength()` methode stelt het maximum aantal tekens in dat in het veld is toegestaan. De waarde moet 0 of groter zijn. Als het niet gedefinieerd is of ingesteld op een ongeldig waarde, heeft het veld geen bovengrens voor het aantal tekens.

```java
passwordField.setMaxLength(20); // Maximale 20 tekens
```

Als de invoer het maximale tekenlimiet overschrijdt, faalt het veld de constraintvalidatie. Zoals bij het minimum, geldt deze regel alleen wanneer de gebruiker de waarde van het veld bijwerkt.

:::tip
Gebruik zowel `setMinLength()` als `setMaxLength()` samen om effectieve invoergrenzen te creëren. Zie de [HTML lengtebeperkingen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) voor meer referentie.
:::


## Best practices {#best-practices}

Aangezien de `PasswordField` component vaak wordt geassocieerd met gevoelige informatie, overweeg de volgende best practices bij het gebruik van de `PasswordField`:

- **Bied wachtwoordsterkte feedback**: Integreer wachtwoordsterkte-indicatoren of feedbackmechanismen om gebruikers te helpen sterke en veilige wachtwoorden te maken. Evalueer factoren zoals lengte, complexiteit en een mix van hoofdletters en kleine letters, cijfers en speciale tekens.

- **Handhaven van wachtwoordopslag**: Sla wachtwoorden nooit in platte tekst op. Implementeer in plaats daarvan de juiste beveiligingsmaatregelen om wachtwoorden veilig te verwerken en op te slaan in je app. Gebruik industriestandaard encryptie-algoritmen voor wachtwoorden en andere gevoelige gegevens.

- **Wachtwoordbevestiging**: Neem een extra bevestigingsveld op wanneer een gebruiker een wachtwoord wijzigt of maakt. Deze maatregel helpt de kans op typfouten te minimaliseren en zorgt ervoor dat gebruikers hun gewenste wachtwoord nauwkeurig invoeren.

- **Sta wachtwoordreset toe**: Als je app gebruikersaccounts omvat, biedt dan een optie voor gebruikers om hun wachtwoord te resetten. Dit kan in de vorm van een "Wachtwoord vergeten" functie die een wachtwoordherstelproces start.

- **Toegankelijkheid**: Stel de `PasswordField` in met toegankelijkheid in gedachten, zodat het voldoet aan toegankelijkheidsnormen zoals het bieden van juiste labels en compatibiliteit met ondersteunende technologieën.
