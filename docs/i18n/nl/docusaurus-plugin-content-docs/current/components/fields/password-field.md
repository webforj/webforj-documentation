---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: 180bd1578c78bf1ee9e746d23f76ec96
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

De `PasswordField` component stelt gebruikers in staat om veilig een wachtwoord in te voeren. Het wordt weergegeven als een teksteditor met één regel waarbij de ingevoerde tekst verborgen is, meestal vervangen door symbolen zoals asterisken (”*”) of punten (”•”). Het exacte symbool kan variëren op basis van de browser en het besturingssysteem.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Veldwaarde {#field-value}

De `PasswordField` component slaat zijn waarde op en haalt deze op als een gewone `String`, vergelijkbaar met een `TextField`, maar met visuele weergave die de tekens verbergt.

Je kunt de huidige waarde ophalen met:

```java
passwordField.getValue();
```

:::warning gevoelige data
Hoewel het veld de inhoud visueel maskeert, is de geretourneerde waarde van `getValue()` nog steeds een gewone string. Wees voorzichtig met dit gegeven bij het omgaan met gevoelige informatie en versleutel of transformeer het voordat je het opslaat.
:::

Om de waarde programmatisch in te stellen of opnieuw in te stellen:

```java
passwordField.setValue("MySecret123!");
```

Als er geen waarde door de gebruiker is ingevoerd en er geen standaardwaarde is ingesteld, retourneert het veld een lege string (`""`).

Dit gedrag lijkt op dat van het native HTML `<input type="password">`, waar de `value`-eigenschap de huidige invoer vasthoudt.

## Gebruikscases {#usages}

De `PasswordField` is het beste te gebruiken in scenario's waarbij het vastleggen of verwerken van gevoelige informatie, zoals wachtwoorden of andere vertrouwelijke gegevens, essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `PasswordField` kunt gebruiken:

1. **Gebruikersauthenticatie en registratie**: Wachtwoordvelden zijn cruciaal in apps die te maken hebben met gebruikersauthenticatie of registratieprocessen, waarbij veilige invoer van wachtwoorden vereist is.

2. **Veilige formulierinvoer**: Bij het ontwerpen van formulieren die invoer van gevoelige informatie vereisen, zoals creditcardgegevens of persoonlijke identificatienummers (PIN's), zorgt het gebruik van een `PasswordField` voor een veilige invoer van dergelijke gegevens.

3. **Accountbeheer en profielinstellingen**: Wachtwoordvelden zijn waardevol in apps die omgaan met accountbeheer of profielinstellingen, waarmee gebruikers veilig hun wachtwoorden kunnen wijzigen of bijwerken.

## Wachtwoordzichtbaarheid {#password-visibility}

Gebruikers kunnen de waarde van de `PasswordField` onthullen door op het onthulicoon te klikken. Dit stelt gebruikers in staat te verifiëren wat ze hebben ingevoerd of de informatie naar hun klembord te kopiëren. Echter, voor omgevingen met hoge beveiliging kun je `setPasswordReveal()` gebruiken om het onthulicoon te verwijderen en te voorkomen dat gebruikers de waarde zien. Je kunt verifiëren of een gebruiker het onthulicoon kan gebruiken om de waarde te tonen met de `isPasswordReveal()`-methode.

## Patroonmatching {#pattern-matching}

Het toepassen van een reguliere expressiepatroon op de `PasswordField` met behulp van de `setPattern()`-methode wordt sterk aanbevolen. Dit stelt je in staat om tekenregels en structurele vereisten af te dwingen, waardoor gebruikers gedwongen worden om veilige en conforme inloggegevens aan te maken. Patroonmatching is vooral nuttig bij het afdwingen van sterke wachtwoordregels, zoals het vereisen van een mix van hoofdletters en kleine letters, cijfers en symbolen.

Het patroon moet voldoen aan de syntax van een [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern gebruikt om validatie over alle Unicode-codepunten te waarborgen. Voeg **geen** schuine strepen (`/`) rond het patroon toe.

In de volgende snippet vereist het patroon minimaal één kleine letter, één hoofdletter, één nummer en een minimale lengte van 8 tekens.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Als het patroon ontbreekt of ongeldig is, wordt er geen validatie toegepast.

:::tip
Gebruik `setLabel()` om een duidelijke label te geven die het doel van het wachtwoordveld beschrijft. Om gebruikers te helpen de wachtwoordvereisten te begrijpen, gebruik `setHelperText()` om richtlijnen of regels direct onder het veld weer te geven.
:::

## Minimale en maximale lengte {#minimum-and-maximum-length}

Je kunt de toegestane lengte van de wachtwoordinvoer controleren door `setMinLength()` en `setMaxLength()` op de `PasswordField` te gebruiken.

De `setMinLength()`-methode definieert het minimale aantal tekens dat een gebruiker in het veld moet invoeren om aan de validatie te voldoen. Deze waarde moet een niet-negatief geheel getal zijn en mag de maximale lengte niet overschrijden als deze is ingesteld.

```java
passwordField.setMinLength(8); // Minimaal 8 tekens
```

Als de gebruiker minder tekens invoert dan het minimum, faalt de invoer de constraintvalidatie. Deze validatie wordt alleen toegepast wanneer de waarde van het veld door de gebruiker wordt gewijzigd.

De `setMaxLength()`-methode stelt het maximale aantal tekens in dat in het veld is toegestaan. De waarde moet 0 of groter zijn. Als het niet is gedefinieerd of is ingesteld op een ongeldige waarde, heeft het veld geen bovengrens voor het aantal tekens.

```java
passwordField.setMaxLength(20); // Maximale 20 tekens
```

Als de invoer de maximale tekenlimiet overschrijdt, faalt het veld de constraintvalidatie. Net als bij het minimum geldt deze regel alleen wanneer de gebruiker de waarde van het veld bijwerkt.

:::tip
Gebruik zowel `setMinLength()` als `setMaxLength()` samen om effectieve invoergrenzen te creëren. Zie de [HTML lengtebeperkingen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) voor meer referentie.
:::

## Beste praktijken {#best-practices}

Aangezien de `PasswordField` component vaak wordt geassocieerd met gevoelige informatie, overweeg de volgende beste praktijken bij het gebruik van de `PasswordField`:

- **Voorzie in feedback over de wachtwoordsterkte**: Neem wachtwoordsterkte-indicatoren of feedbackmechanismen op om gebruikers te helpen sterke en veilige wachtwoorden te creëren. Evalueer factoren zoals lengte, complexiteit en een mix van hoofdletters en kleine letters, cijfers en speciale tekens.

- **Dwing wachtwoordopslag af**: Sla nooit wachtwoorden in gewone tekst op. Implementeer in plaats daarvan de juiste veiligheidsmaatregelen om wachtwoorden veilig in je app te behandelen en op te slaan. Gebruik industrienormen voor versleutelingsalgoritmen voor wachtwoorden en andere gevoelige gegevens.

- **Wachtwoordbevestiging**: Neem een extra bevestigingsveld op wanneer een gebruiker een wachtwoord wijzigt of maakt. Deze maatregel helpt de kans op typfouten te minimaliseren en zorgt ervoor dat gebruikers hun gewenste wachtwoord nauwkeurig invoeren.

- **Sta wachtwoordreset toe**: Als je app gebruikersaccounts omvat, bied dan een optie aan voor gebruikers om hun wachtwoord opnieuw in te stellen. Dit kan in de vorm van een "Wachtwoord Vergeten" functie die een wachtwoordherstelproces initieert.

- **Toegankelijkheid**: Stel de `PasswordField` in met toegankelijkheid in gedachten, zodat deze voldoet aan toegankelijkheidsnormen, zoals het bieden van de juiste labels en compatibiliteit met hulpmiddelen voor mensen met een beperking.
