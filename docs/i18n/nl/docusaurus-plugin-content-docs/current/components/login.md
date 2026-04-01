---
title: Login
sidebar_position: 70
_i18n_hash: d5724547e5173f77895c401018612328
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De `Login` component vereenvoudigt gebruikersauthenticatie door een kant-en-klaar inlogdialoog te bieden met gebruikersnaam- en wachtwoordvelden. Het bevat functies zoals invoervalidatie, aanpasbare labels en berichten, controles voor de zichtbaarheid van het wachtwoord en ondersteuning voor extra aangepaste velden.

<!-- INTRO_END -->

## Een `Login` dialoog aanmaken {#creating-a-login-dialog}

Maak een `Login` dialoog door de component te initialiseren en `open()` aan te roepen om deze weer te geven. De dialoog bevat standaard gebruikersnaam- en wachtwoordvelden, invoervalidatie en een aanmeldknop.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Inloggen {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoer als verplichte velden. Zodra de validatie geslaagd is, wordt een formulierindienings gebeurtenis geactiveerd, waarbij de ingevoerde inloggegevens worden verzonden. Om meerdere ind dieningen te voorkomen, wordt de [Aanmelden] knop onmiddellijk uitgeschakeld.

Het onderstaande laat een basis `Login` component zien. Als zowel de gebruikersnaam als het wachtwoord zijn ingesteld op `"admin"`, sluit de inlogdialoog, en verschijnt er een [Afmelden] knop. Als de inloggegevens niet overeenkomen, wordt het standaard foutbericht weergegeven.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info De [Aanmelden] Knop Uitschakelen
Standaard schakelt `Login` onmiddellijk de [Aanmelden] knop uit zodra de component de inloginvoer als correct valideert, om meerdere ind dieningen te voorkomen. Je kunt de [Aanmelden] knop opnieuw inschakelen met behulp van de `setEnabled(true)` methode.
:::

:::tip Lege Wachtwoorden Toestaan
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de `setEmptyPassword(true)` methode te gebruiken.
:::

## Formulieractie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formuliergegevens rechtstreeks naar een opgegeven URL indienen in plaats van het indienen via de ind dieningsgebeurtenis af te handelen. Wanneer een actielink is ingesteld, voert het formulier een standaard POST-aanroep uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij het gebruik van `setAction()` omzeilt de formulierindiening de `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar het opgegeven eindpunt. De gebruikersnaam en het wachtwoord worden verzonden als formulierparameters met de naam `"username"` en `"password"`, respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in de POST-aanroep.

:::tip 
Als er geen actielink is ingesteld, wordt de formulierindiening afgehandeld via de `LoginSubmitEvent`, waardoor je de inloggegevens programmatisch aan de serverzijde kunt verwerken.
:::

## Internationalisering (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met de `LoginI18n` klasse. Deze flexibiliteit maakt het mogelijk om de inloginterface aan te passen om aan specifieke lokalisatievereisten of personalisatievoorkeuren te voldoen.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component bevat verschillende slots waarmee je extra velden kunt toevoegen indien nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en kunnen worden benaderd via de gegevensmap van de ind dieningsgebeurtenis.

Het volgende inlogformulier heeft een aangepast veld toegevoegd voor een klant-ID. Dit kan je helpen bedrijven of afdelingen te beheren met gedeelde inhoud tussen meerdere gebruikers.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Naam Vereist
Aangepaste velden moeten een naam hebben die is ingesteld met `setName()` om in de formulierindiening te worden opgenomen. De naam wordt gebruikt als de sleutel om de waarde van het veld op te halen uit `event.getData()`.
:::

## Annuleerknop {#cancel-button}

`Login` bevat een [Annuleren] knop die standaard verborgen is. Dit is bijzonder nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gedeelte van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleren knop zichtbaar te maken, geef een label voor de knop op. Je kunt ook luisteren naar annuleringsgebeurtenissen om de annulering op de juiste manier af te handelen.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen Verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit stelt je in staat om de zichtbaarheid te wisselen zonder het component uit je code te verwijderen.
:::

## Wachtwoordbeheerders {#password-managers}

Deze component werkt samen met browsergebaseerde wachtwoordbeheerders om het inlogproces te vereenvoudigen. In Chromium-gebaseerde browsers integreert het met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die biedt:

- **Automatisch invullen**: De browser kan automatisch de gebruikersnaam- en wachtwoordvelden invullen als de gebruiker inloggegevens voor de site heeft opgeslagen.
- **Beheer van inloggegevens**: Na inloggen kan de browser de gebruiker vragen om nieuwe inloggegevens op te slaan, waardoor toekomstige logins sneller en gemakkelijker worden.
- **Selectie van inloggegevens**: Als er meerdere inloggegevens zijn opgeslagen, kan de browser de gebruiker de keuze aanbieden om uit een van de opgeslagen sets te kiezen.

## Opmaak {#styling}

<TableBuilder name="Login" />
