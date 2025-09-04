---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

De `PasswordField`-component stelt gebruikers in staat om een wachtwoord veilig in te voeren. Het wordt weergegeven als een teksteditor voor één regel waarbij de ingevoerde tekst wordt verduisterd, meestal vervangen door symbolen zoals sterretjes (”*”) of punten (”•”). Het exacte symbool kan variëren afhankelijk van de browser en het besturingssysteem.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Waarde van het veld {#field-value}

De `PasswordField`-component slaat zijn waarde op en haalt deze op als een gewone `String`, vergelijkbaar met een `TextField`, maar met verduisterde visuele rendering om de karakters verborgen te houden.

Je kunt de huidige waarde ophalen met:

```java
passwordField.getValue();
```

:::warning gevoelige gegevens
Hoewel het veld de inhoud visueel verbergt, is de geretourneerde waarde van `getValue()` nog steeds een gewone string. Wees voorzichtig met het omgaan met gevoelige gegevens en versleutel of transformeer deze voordat je ze opslaat.
:::

Om de waarde programmatisch in te stellen of opnieuw in te stellen:

```java
passwordField.setValue("MySecret123!");
```

Als er door de gebruiker geen waarde is ingevoerd en er geen standaardwaarde is ingesteld, retourneert het veld een lege string (`""`).

Dit gedrag bootst dat van de native HTML `<input type="password">` na, waarbij de `value`-eigenschap de huidige invoer vasthoudt.

## Gebruik {#usages}

De `PasswordField` wordt het best gebruikt in scenario's waarin het vastleggen of omgaan met gevoelige informatie, zoals wachtwoorden of andere vertrouwelijke gegevens, essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `PasswordField` moet gebruiken:

1. **Gebruikersauthenticatie en registratie**: Wachtwoordvelden zijn cruciaal in apps die gebruikersauthenticatie of registratieprocessen omvatten, waarin veilige invoer van wachtwoorden vereist is.

2. **Veilige formulierinvoer**: Bij het ontwerpen van formulieren die invoer van gevoelige informatie vereisen, zoals creditcardgegevens of persoonlijke identificatienummers (PINS), zorgt het gebruik van een `PasswordField` voor een veilige invoer van dergelijke gegevens.

3. **Accountbeheer en profielinstellingen**: Wachtwoordvelden zijn waardevol in apps die accountbeheer of profielinstellingen omvatten, waardoor gebruikers hun wachtwoorden veilig kunnen wijzigen of bijwerken.

## Wachtwoordzichtbaarheid {#password-visibility}

Gebruikers kunnen de waarde van de `PasswordField` onthullen door op het onthulpictogram te klikken. Dit stelt gebruikers in staat om te verifiëren wat ze hebben ingevoerd of de informatie naar hun klembord te kopiëren. Voor hoogbeveiligde omgevingen kun je echter `setPasswordReveal()` gebruiken om het onthulpictogram te verwijderen en te voorkomen dat gebruikers de waarde zien. Je kunt verifiëren of een gebruiker het onthulpictogram kan gebruiken om de waarde te tonen met de `isPasswordReveal()`-methode.

## Patronen matching {#pattern-matching}

Het toepassen van een reguliere expressiepatroon op de `PasswordField` met de `setPattern()`-methode wordt ten zeerste aanbevolen. Dit stelt je in staat om karakterregels en structurele vereisten af te dwingen, waardoor gebruikers sterke en conforme inloggegevens moeten aanmaken. Patroonmatching is vooral nuttig bij het afdwingen van strenge wachtwoordregels, zoals het vereisen van een mix van hoofdletters en kleine letters, cijfers en symbolen.

Het patroon moet voldoen aan de syntaxis van een [JavaScript-reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), zoals geïnterpreteerd door de browser. De `u` (Unicode) vlag wordt intern gebruikt om validatie over alle Unicode-codepunten te garanderen. Voeg **geen** schuine strepen (`/`) rond het patroon toe.

In de onderstaande snippet vereist het patroon minimaal één kleine letter, één grote letter, één cijfer en een minimale lengte van 8 tekens.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Als het patroon ontbreekt of ongeldig is, wordt er geen validatie toegepast.

:::tip
Gebruik `setLabel()` om een duidelijke label te geven die het doel van het wachtwoordveld beschrijft. Om gebruikers te helpen de wachtwoordvereisten te begrijpen, gebruik `setHelperText()` om richtlijnen of regels direct onder het veld weer te geven.
:::

## Minimale en maximale lengte {#minimum-and-maximum-length}

Je kunt de toegestane lengte van de wachtwoordinvoer beheersen met `setMinLength()` en `setMaxLength()` op de `PasswordField`.

De `setMinLength()`-methode definieert het minimale aantal karakters dat een gebruiker in het veld moet invoeren om aanvalidatie te voldoen. Deze waarde moet een niet-negatief geheel getal zijn en mag de maximale lengte niet overschrijden als deze is ingesteld.

```java
passwordField.setMinLength(8); // Minimaal 8 karakters
```

Als de gebruiker minder karakters invoert dan het minimum, mislukt de invoer de constraintvalidatie. Deze validatie wordt alleen toegepast wanneer de waarde van het veld door de gebruiker wordt gewijzigd.

De `setMaxLength()`-methode stelt het maximale aantal toegestane karakters in het veld in. De waarde moet 0 of groter zijn. Als deze niet is gedefinieerd of is ingesteld op een ongeldige waarde, heeft het veld geen bovengrens voor karakters.

```java
passwordField.setMaxLength(20); // Maximaal 20 karakters
```

Als de invoer de maximale tekeninglimiet overschrijdt, faalt het veld de constraintvalidatie. Net als het minimum geldt deze regel alleen wanneer de gebruiker de waarde van het veld bijwerkt.

:::tip
Gebruik zowel `setMinLength()` als `setMaxLength()` samen om effectieve invoerlimieten te creëren. Zie de [HTML lengtebeperkingen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) voor meer referentie.
:::

## Beste praktijken {#best-practices}

Aangezien de `PasswordField`-component vaak wordt geassocieerd met gevoelige informatie, overweeg de volgende best practices bij het gebruik van de `PasswordField`:

- **Geef feedback over de wachtwoordsterkte**: Integreer indicatoren voor wachtwoordsterkte of feedbackmechanismen om gebruikers te helpen sterke en veilige wachtwoorden te creëren. Evalueer factoren zoals lengte, complexiteit en een mix van hoofdletters en kleine letters, cijfers en speciale tekens.

- **Handhaaf wachtwoordopslag**: Bewaar wachtwoorden nooit in platte tekst. Implementeer in plaats daarvan de juiste beveiligingsmaatregelen om wachtwoorden veilig te verwerken en op te slaan in je app. Gebruik industriestandaard versleutelingsalgoritmen voor wachtwoorden en andere gevoelige gegevens.

- **Wachtwoordbevestiging**: Voeg een extra bevestigingsveld toe wanneer een gebruiker een wachtwoord wijzigt of aanmaakt. Deze maatregel helpt de kans op typefouten te minimaliseren en zorgt ervoor dat gebruikers hun gewenste wachtwoord nauwkeurig invoeren.

- **Sta wachtwoordreset toe**: Als je app gebruikersaccounts omvat, bied dan een optie voor gebruikers om hun wachtwoord opnieuw in te stellen. Dit kan in de vorm van een "Wachtwoord vergeten"-functie die een wachtwoordherstelproces initieert.

- **Toegankelijkheid**: Stel de `PasswordField` in met toegankelijkheid in gedachten, zodat het voldoet aan de toegankelijkheidsnormen, zoals het bieden van de juiste labels en compatibiliteit met hulpmiddelen voor assistentie.
