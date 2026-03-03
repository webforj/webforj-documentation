---
title: Login
sidebar_position: 70
_i18n_hash: e0aded01aee7ef12465b2d7661cc0477
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-inloggen" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="inloggen" location="com/webforj/component/inloggen/Login" top='true'/>

De `Login` component vereenvoudigt gebruikersauthenticatie door een kant-en-klare inlogdialoog met velden voor gebruikersnaam en wachtwoord te bieden. Het bevat functies zoals invoervalidatie, aanpasbare labels en berichten, controles voor wachtwoordzichtbaarheid en ondersteuning voor extra aangepaste velden.

<!-- INTRO_END -->

## Een `Login` dialoog creëren {#creating-a-login-dialog}

Creëer een `Login` dialoog door de component te initialiseren en `open()` aan te roepen om deze weer te geven. De dialoog bevat standaard velden voor gebruikersnaam en wachtwoord, invoervalidatie en een inloggen-knop.

<ComponentDemo 
path='/webforj/inloggenbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Inloggen indiening {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoer als verplichte velden. Zodra de validatie is geslaagd, wordt er een formulierindieningsgebeurtenis getriggerd, die de ingevoerde referenties levert. Om meerdere indieningen te voorkomen, wordt de [Inloggen] knop onmiddellijk uitgeschakeld.

Het volgende illustreert een basis `Login` component. Als de gebruikersnaam en het wachtwoord beide zijn ingesteld op `"admin"` respectievelijk, sluit de inlogdialoog en verschijnt er een [Afmelden] knop. Als de referenties niet overeenkomen, wordt het standaardfoutbericht weergegeven.

<ComponentDemo 
path='/webforj/inloggenindiening?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info De [Inloggen] Knop Uitschakelen
Standaard schakelt `Login` onmiddellijk de [Inloggen] knop uit zodra de component de inloginvoer als correct valideert, om meerdere indieningen te voorkomen. Je kunt de [Inloggen] knop weer inschakelen met de `setEnabled(true)` methode.
:::

:::tip Lege Wachtwoorden Toestaan
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de `setEmptyPassword(true)` methode te gebruiken.
:::

## Formulieractie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formulierdata rechtstreeks indienen naar een opgegeven URL in plaats van de indiening via de indieningsgebeurtenis te verwerken. Wanneer er een actie-URL is ingesteld, voert het formulier een standaard POST-aanroep uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij het gebruik van `setAction()`, omzeilt de formulierindiening de `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar de opgegeven endpoint. De gebruikersnaam en het wachtwoord worden verzonden als formulierparameters genaamd "username" en "password", respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in de POST-aanroep.

:::tip 
Als er geen actie-URL is ingesteld, wordt de formulierindiening verwerkt via de `LoginSubmitEvent`, zodat je referenties programmatically aan de serverzijde kunt verwerken.
:::

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met behulp van de `LoginI18n` klasse. Deze flexibiliteit stelt je in staat om de inloginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

<ComponentDemo 
path='/webforj/inloggeninternationalisatie?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component bevat verschillende slots waarmee je extra velden kunt toevoegen indien nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en kunnen worden benaderd via de datakaart van de indieningsgebeurtenis.

De volgende inlog heeft een aangepast veld toegevoegd voor een klant-ID. Dit kan je helpen bij het beheren van bedrijven of afdelingen met gedeelde inhoud tussen meerdere gebruikers.

<ComponentDemo 
path='/webforj/inloggenaangepastevelden?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/inloggen/inloggenAangepasteVelden.css'
height = '700px'
/>

:::info Naam Vereist
Aangepaste velden moeten een naam hebben die is ingesteld met `setName()` om te worden opgenomen in de formulierindiening. De naam wordt gebruikt als de sleutel om de waarde van het veld te halen uit `event.getData()`.
:::

## Annuleerknop {#cancel-button}

`Login` bevat een [Annuleer] knop die standaard verborgen is. Dit is bijzonder nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleerknop zichtbaar te maken, geef je een label ervoor op. Je kunt ook luisteren naar annuleergebeurtenissen om de annulering op de juiste manier af te handelen.

<ComponentDemo 
path='/webforj/inloggenannuleerknop?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen Verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit stelt je in staat om de zichtbaarheid te schakelen zonder het component uit je code te verwijderen.
:::

## Wachtwoordmanagers {#password-managers}

Deze component werkt met browsergebaseerde wachtwoordmanagers om het inloggen te vereenvoudigen. In Chromium-gebaseerde browsers integreert het met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die biedt:

- **Automatisch invullen**: De browser kan automatisch de velden voor gebruikersnaam en wachtwoord invullen als de gebruiker referenties voor de site heeft opgeslagen.
- **Referentiebeheer**: Na het inloggen kan de browser de gebruiker vragen om nieuwe referenties op te slaan, waardoor toekomstige inlogpogingen sneller en gemakkelijker worden.
- **Referentieselectie**: Als meerdere referenties zijn opgeslagen, kan de browser de gebruiker de keuze bieden om uit een van de opgeslagen sets te kiezen.

## Stijling {#styling}

<TableBuilder name="Inloggen" />
