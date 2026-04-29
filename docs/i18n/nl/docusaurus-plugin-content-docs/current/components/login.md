---
title: Login
sidebar_position: 70
_i18n_hash: 59a9ab8cb7ba550b955ab83de0c6d878
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De `Login` component vereenvoudigt de gebruikersauthenticatie door een kant-en-klare inlogdialoog aan te bieden met velden voor gebruikersnaam en wachtwoord. Het omvat functies zoals invoervalidatie, aanpasbare labels en berichten, wachtwoordzichtbaarheidselementen en ondersteuning voor extra aangepaste velden.

<!-- INTRO_END -->

## Een `Login` dialoog maken {#creating-a-login-dialog}

Maak een `Login` dialoog door de component te initialiseren en `open()` aan te roepen om deze weer te geven. De dialoog bevat standaard velden voor gebruikersnaam en wachtwoord, invoervalidatie en een aanmeldknop.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Inloggen {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoeren als verplichte velden. Zodra de validatie slaagt, wordt een formulierindienevenement geactiveerd, waarbij de ingevoerde inloggegevens worden afgeleverd. Om meerdere indieningen te voorkomen, wordt de [Sign in] knop onmiddellijk uitgeschakeld.

Het volgende illustreert een basis `Login` component. Als zowel de gebruikersnaam als het wachtwoord zijn ingesteld op `"admin"` respectievelijk, sluit de inlogdialoog en verschijnt een [Logout] knop. Als de inloggegevens niet overeenkomen, wordt het standaard foutbericht weergegeven.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info De [Sign in] knop uitschakelen
Standaard schakelt `Login` onmiddellijk de [Sign in] knop uit zodra de component de inloginvoeren als correct valideert, om meerdere indieningen te voorkomen. Je kunt de [Sign in] knop weer inschakelen met de methode `setEnabled(true)`.
:::

:::tip Lege Wachtwoorden Toestaan
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de methode `setEmptyPassword(true)` te gebruiken.
:::

## Formulieractie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formulierdata rechtstreeks naar een opgegeven URL indienen in plaats van de indiening via het indieningsevenement te verwerken. Wanneer een actie-URL is ingesteld, voert het formulier een standaard POST-verzoek uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij gebruik van `setAction()`, omzeilt de formulierindiening het `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar het opgegeven eindpunt. De gebruikersnaam en het wachtwoord worden als formulierparameters verzonden met de namen `"username"` en `"password"`, respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in het POST verzoek.

:::tip 
Als er geen actie-URL is ingesteld, wordt de formulierindiening verwerkt via het `LoginSubmitEvent`, waardoor je inloggegevens programmatig aan de serverzijde kunt verwerken.
:::

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met behulp van de `LoginI18n` klasse. Deze flexibiliteit stelt je in staat om de inloginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '600px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component omvat verschillende slots waarmee je extra velden kunt toevoegen indien nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en kunnen worden geraadpleegd via de datakaart van het indieningsevenement.

De volgende inlog heeft een aangepast veld toegevoegd voor een klant ID. Dit kan je helpen bij het beheren van bedrijven of afdelingen met gedeelde inhoud tussen meerdere gebruikers.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Naam Vereist
Aangepaste velden moeten een naam hebben ingesteld met `setName()` om opgenomen te worden in de formulierindiening. De naam wordt gebruikt als de sleutel om de waarde van het veld op te halen uit `event.getData()`.
:::

## Annuleerknop {#cancel-button}

`Login` omvat een [Cancel] knop die standaard verborgen is. Dit is vooral nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleerknop zichtbaar te maken, geef je een label voor deze op. Je kunt ook luisteren naar annuleerevenementen om de annulering op de juiste manier af te handelen.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen Verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit stelt je in staat om de zichtbaarheid te togglen zonder de component uit je code te verwijderen.
:::

## Wachtwoordmanagers {#password-managers}

Deze component werkt samen met browsergebaseerde wachtwoordmanagers om het inlogproces te vereenvoudigen. In op Chromium gebaseerde browsers is het geïntegreerd met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, welke biedt:

- **Automatisch invullen**: De browser kan de velden voor gebruikersnaam en wachtwoord automatisch invullen als de gebruiker inloggegevens voor de site heeft opgeslagen.
- **Credential Management**: Na het inloggen kan de browser de gebruiker vragen om nieuwe inloggegevens op te slaan, waardoor toekomstige inloggen sneller en gemakkelijker wordt.
- **Credential Selectie**: Als er meerdere inloggegevens zijn opgeslagen, kan de browser de gebruiker de keuze geven uit een van de opgeslagen sets.

## Stijlen {#styling}

<TableBuilder name="Login" />
