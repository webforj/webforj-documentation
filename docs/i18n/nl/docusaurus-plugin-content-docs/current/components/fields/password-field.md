---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: b0641475acf187af7c45d6786506010d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

De `PasswordField` component stelt gebruikers in staat om veilig een wachtwoord in te voeren. Het wordt weergegeven als een teksteditor met één regel waarin de ingevoerde tekst is gecamoufleerd, meestal vervangen door symbolen zoals sterretjes ("*") of stippen ("•"). Het exacte symbool kan variëren op basis van de browser en het besturingssysteem.

<!-- INTRO_END -->

## Using `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. In het volgende voorbeeld wordt een `PasswordField` gemaakt met een label en een plaatsaanduidingstekst.

<ComponentDemo
path='/webforj/passwordfield'
files={['src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java']}
/>

## Field value {#field-value}

De `PasswordField` component slaat zijn waarde op en haalt deze op als een gewone `String`, vergelijkbaar met een `TextField`, maar met gecamoufleerde visuele weergave om de tekens uit het zicht te houden.

Je kunt de huidige waarde ophalen met:

```java
passwordField.getValue();
```

:::warning sensitive data
Hoewel het veld de inhoud visueel maskeert, is de geretourneerde waarde van `getValue()` nog steeds een gewone string. Wees voorzichtig hiermee bij het omgaan met gevoelige gegevens en versleutel of transformeer deze voordat je ze opslaat.
:::

Om de waarde programmatig in te stellen of te resetten:

```java
passwordField.setValue("MySecret123!");
```

Als er geen waarde door de gebruiker is ingevoerd en er geen standaardwaarde is ingesteld, retourneert het veld een lege string (`""`).

Dit gedrag boots het native HTML `<input type="password">` na, waarbij de `value` eigenschap de huidige invoer bevat.


## Usages {#usages}

De `PasswordField` is het beste te gebruiken in scenario's waarin het vastleggen of behandelen van gevoelige informatie, zoals wachtwoorden of andere vertrouwelijke gegevens, essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `PasswordField` moet gebruiken:

1. **Gebruikersauthenticatie en registratie**: Wachtwoordvelden zijn cruciaal in apps die gebruikersauthenticatie of registratieprocessen omvatten, waar veilige wachtwoordinvoer vereist is.

2. **Veilige formulierinvoer**: Bij het ontwerpen van formulieren die invoer van gevoelige informatie vereisen, zoals creditcardgegevens of persoonlijke identificatienummers (PIN's), maakt het gebruik van een `PasswordField` de invoer van dergelijke gegevens veiliger.

3. **Accountbeheer en profielinstellingen**: Wachtwoordvelden zijn waardevol in apps die accountbeheer of profielinstellingen omvatten, zodat gebruikers hun wachtwoorden veilig kunnen wijzigen of bijwerken.

## Password visibility {#password-visibility}

Gebruikers kunnen de waarde van het `PasswordField` onthullen door op het onthullingspictogram te klikken. Hierdoor kunnen gebruikers controleren wat ze hebben ingevoerd of de informatie naar hun klembord kopiëren. Voor omgevingen met hoge veiligheid kun je echter `setPasswordReveal()` gebruiken om het onthullingspictogram te verwijderen en te voorkomen dat gebruikers de waarde zien. Je kunt controleren of een gebruiker het onthullingspictogram kan gebruiken om de waarde te tonen met de `isPasswordReveal()` methode.

## Pattern matching {#pattern-matching}

Het toepassen van een reguliere expressiepatroon op de `PasswordField` met de `setPattern()` methode wordt sterk aanbevolen. Dit stelt je in staat om karakterregels en structurele vereisten af te dwingen, waardoor gebruikers gedwongen worden om veilige en conforme inloggegevens te creëren. Patroonmatching is vooral nuttig bij het handhaven van sterke wachtwoordregels, zoals het vereisen van een mix van hoofdletters en kleine letters, cijfers en symbolen.

Het patroon moet de syntaxis van een [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) volgen, zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern gebruikt om validatie over alle Unicode-codepunten te garanderen. Voeg **geen** schuine strepen (`/`) rond het patroon toe.

In de volgende snippet vereist het patroon ten minste één kleine letter, één hoofdletter, één cijfer en een minimale lengte van 8 tekens.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Als het patroon ontbreekt of ongeldig is, wordt er geen validatie toegepast.

:::tip
Gebruik `setLabel()` om een duidelijk label te geven dat het doel van het wachtwoordveld beschrijft. Om gebruikers te helpen de vereisten voor wachtwoorden te begrijpen, gebruik je `setHelperText()` om richtlijnen of regels direct onder het veld weer te geven.
:::


## Minimum and maximum length {#minimum-and-maximum-length}

Je kunt de toegestane lengte van de wachtwoordinvoer regelen met `setMinLength()` en `setMaxLength()` op de `PasswordField`.

De `setMinLength()` methode definieert het minimum aantal tekens dat een gebruiker in het veld moet invoeren om de validatie te passeren. Deze waarde moet een niet-negatief geheel getal zijn en mag het maximum niet overschrijden als dat is ingesteld.

```java
passwordField.setMinLength(8); // Minimum 8 tekens
```

Als de gebruiker minder tekens invoert dan het minimum, faalt de invoer de constraintvalidatie. Deze validatie wordt alleen toegepast wanneer de waarde van het veld door de gebruiker wordt gewijzigd.

De `setMaxLength()` methode stelt het maximum aantal toegestane tekens in het veld in. De waarde moet 0 of groter zijn. Als deze niet is gedefinieerd of is ingesteld op een ongeldige waarde, heeft het veld geen bovenlimiet voor tekens.

```java
passwordField.setMaxLength(20); // Maximum 20 tekens
```

Als de invoer het maximale aantal tekens overschrijdt, faalt het veld de constraintvalidatie. Net als bij het minimum, geldt deze regel alleen wanneer de gebruiker de waarde van het veld bijwerkt.

:::tip
Gebruik zowel `setMinLength()` als `setMaxLength()` samen om effectieve invoergrenzen te creëren. Zie de [HTML lengtebeperkingen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) voor meer referentie.
:::


## Best practices {#best-practices}

Aangezien de `PasswordField` component vaak wordt geassocieerd met gevoelige informatie, overweeg de volgende beste praktijken bij het gebruik van de `PasswordField`:

- **Geef feedback over wachtwoordsterkte**: Neem wachtwoordsterkte-indicatoren of feedbackmechanismen op om gebruikers te helpen sterke en veilige wachtwoorden te maken. Evalueer factoren zoals lengte, complexiteit en een mix van hoofdletters, kleine letters, cijfers en speciale tekens.

- **Bevoorkom opslag van wachtwoorden**: Bewaar nooit wachtwoorden als platte tekst. Implementeer in plaats daarvan de juiste beveiligingsmaatregelen om wachtwoorden veilig te behandelen en op te slaan in je app. Gebruik industriestandaard encryptie-algoritmen voor wachtwoorden en andere gevoelige gegevens.

- **Wachtwoordbevestiging**: Neem een extra bevestigingsveld op wanneer een gebruiker een wachtwoord wijzigt of aanmaakt. Deze maatregel helpt de kans op typfouten te minimaliseren en zorgt ervoor dat gebruikers hun gewenste wachtwoord nauwkeurig invoeren.

- **Geeft de mogelijkheid tot resetten van wachtwoord**: Als je app gebruikersaccounts omvat, bied dan een optie voor gebruikers om hun wachtwoord te resetten. Dit kan in de vorm van een "Wachtwoord vergeten" functie die een wachtwoordherstelproces initieert.

- **Toegankelijkheid**: Stel het `PasswordField` op met toegankelijkheid in gedachten, zodat het voldoet aan toegankelijkheidsstandaarden zoals het bieden van juiste labels en compatibiliteit met hulpmiddelen voor toegankelijkheid.
