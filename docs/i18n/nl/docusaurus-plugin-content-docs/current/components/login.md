---
title: Login
sidebar_position: 70
description: >-
  Display an authentication dialog with the Login component, handling
  submission, validation, custom fields, and form action URLs.
_i18n_hash: 5016fc4d15ba24b16c61eed8e6e272ee
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De `Login` component vereenvoudigt gebruikersauthenticatie door een kant-en-klaar inlogdialoog met velden voor gebruikersnaam en wachtwoord te bieden. Het bevat functies zoals invoervalidatie, aanpasbare labels en berichten, opties voor het weergeven van wachtwoorden en ondersteuning voor extra aangepaste velden.

<!-- INTRO_END -->

## Een `Login` dialoog maken {#creating-a-login-dialog}

Maak een `Login` dialoog door de component te instantiëren en `open()` aan te roepen om deze weer te geven. De dialoog bevat standaard de velden voor gebruikersnaam en wachtwoord, invoervalidatie en een knop voor inloggen.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Inloggen {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoer als verplichte velden. Zodra de validatie geslaagd is, wordt een formulierindienevenement geactiveerd, die de ingevoerde inloggegevens verzendt. Om meerdere indienevenementen te voorkomen, wordt de [Inloggen] knop onmiddellijk uitgeschakeld.

Het volgende illustreert een basis `Login` component. Als zowel de gebruikersnaam als het wachtwoord respectievelijk zijn ingesteld op `"admin"`, sluit de inlogdialoog en verschijnt er een [Uitloggen] knop. Als de inloggegevens niet overeenkomen, wordt het standaardfoutbericht weergegeven.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info De [Inloggen] knop uitschakelen
Standaard schakelt `Login` de [Inloggen] knop onmiddellijk uit zodra de component de inloginvoer valideert als correct, om meerdere indienevenementen te voorkomen. Je kunt de [Inloggen] knop weer inschakelen met de methode `setEnabled(true)`.
:::

:::tip Lege wachtwoorden toestaan
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de methode `setEmptyPassword(true)` te gebruiken.
:::

## Formulieractie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formuliergegevens rechtstreeks naar een opgegeven URL verzenden in plaats van de indiening via het indieningsevenement te verwerken. Wanneer een actietegoed is ingesteld, voert het formulier een standaard POST-aanroep uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij het gebruik van `setAction()` omzeilt de formulierindiening het `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar de opgegeven eindpunt. De gebruikersnaam en het wachtwoord worden verzonden als formulierparameters genaamd `"username"` en `"password"`, respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in de POST-aanroep.

:::tip
Als er geen actietegoed is ingesteld, wordt formulierindiening afgehandeld via het `LoginSubmitEvent`, waardoor je inloggegevens programmatisch op de serverzijde kunt verwerken.
:::

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met behulp van de `LoginI18n` klasse. Deze flexibiliteit stelt je in staat om de logininterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component bevat verschillende slots waarmee je extra velden kunt toevoegen zoals nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en kunnen worden toegankelijk gemaakt via de gegevenskaart van het indieningsevenement.

De volgende login heeft een aangepast veld toegevoegd voor een klant-ID. Dit kan je helpen om bedrijven of afdelingen te beheren met gedeelde inhoud tussen meerdere gebruikers.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/frontend/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Naam benodigd
Aangepaste velden moeten een naam hebben die is ingesteld met `setName()` om opgenomen te worden in de formulierindiening. De naam wordt gebruikt als sleutel om de waarde van het veld op te halen uit `event.getData()`.
:::

## Annuleer knop {#cancel-button}

`Login` bevat een [Annuleer] knop die standaard verborgen is. Dit is bijzonder nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleer knop zichtbaar te maken, geef je een label voor deze. Je kunt ook naar annuleerevenementen luisteren om de annulering op de juiste manier te verwerken.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Elementen verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit laat je toe om de zichtbaarheid te toggelen zonder het component uit je code te verwijderen.
:::

## Wachtwoordmanagers {#password-managers}

Deze component werkt met browsergebaseerde wachtwoordmanagers om het inlogproces te vereenvoudigen. In op Chromium gebaseerde browsers integreert het met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die biedt:

- **Auto-invullen**: De browser kan automatisch de velden voor gebruikersnaam en wachtwoord invullen als de gebruiker inloggegevens voor de site heeft opgeslagen.
- **Credential Management**: Na het inloggen kan de browser de gebruiker vragen om nieuwe inloggegevens op te slaan, waardoor toekomstige inloggen sneller en gemakkelijker worden.
- **Credential Selectie**: Als meerdere inloggegevens zijn opgeslagen, kan de browser de gebruiker de keuze bieden uit een van de opgeslagen sets.

## Stylen {#styling}

<TableBuilder name="Login" />
