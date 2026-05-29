---
title: Login
sidebar_position: 70
_i18n_hash: 929bacbc38791adc906102078bdd6bfa
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De `Login` component vereenvoudigt gebruikersauthenticatie door een kant-en-klare inlogdialoog met velden voor gebruikersnaam en wachtwoord te bieden. Het bevat functies zoals invoervalidatie, aanpasbare labels en berichten, opties voor wachtwoordweergave en ondersteuning voor extra aangepaste velden.

<!-- INTRO_END -->

## Een `Login` dialoog creëren {#creating-a-login-dialog}

Creëer een `Login` dialoog door de component te initialiseren en `open()` aan te roepen om deze weer te geven. De dialoog bevat standaard velden voor gebruikersnaam en wachtwoord, invoervalidatie en een inlogknop.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Inloggen indienen {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoer als vereiste velden. Zodra de validatie is geslaagd, wordt er een formulierindieningsgebeurtenis geactiveerd, waarmee de ingevoerde gegevens worden geleverd. Om meerdere indelingen te voorkomen, wordt de [Inloggen] knop onmiddellijk uitgeschakeld.

Het volgende illustreert een basis `Login` component. Als de gebruikersnaam en het wachtwoord beide zijn ingesteld op `"admin"`, sluit de inlogdialoog en verschijnt er een [Uitloggen] knop. Als de inloggegevens niet overeenkomen, wordt het standaardfoutbericht weergegeven.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info De [Inloggen] knop uitschakelen
Standaard schakelt `Login` onmiddellijk de [Inloggen] knop uit zodra de component de inloginvoer als correct valideert, om meerdere indelingen te voorkomen. Je kunt de [Inloggen] knop opnieuw inschakelen met de methode `setEnabled(true)`.
:::

:::tip Lege wachtwoorden toestaan
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de methode `setEmptyPassword(true)` te gebruiken.
:::

## Formulieractie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formuliergegevens rechtstreeks naar een opgegeven URL indienen in plaats van de indiening via de indieningsgebeurtenis te verwerken. Wanneer een actie-URL is ingesteld, voert het formulier een standaard POST-aanroep uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij het gebruik van `setAction()` omzeilt de formulierindiening de `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar het opgegeven eindpunt. De gebruikersnaam en het wachtwoord worden verzonden als formulierparameters met de naam `"username"` en `"password"`, respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in de POST-aanroep.

:::tip 
Als er geen actie-URL is ingesteld, wordt de formulierindiening verwerkt via de `LoginSubmitEvent`, zodat je de inloggegevens programmatisch op de serverzijde kunt verwerken.
:::

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met behulp van de `LoginI18n` klasse. Deze flexibiliteit stelt je in staat de inloginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component bevat verschillende slots waarmee je extra velden kunt toevoegen indien nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en zijn toegankelijk via de gegevensmap van de indieningsgebeurtenis.

De volgende inlog heeft een aangepast veld toegevoegd voor een klant-ID. Dit kan je helpen om bedrijven of afdelingen met gedeelde inhoud over meerdere gebruikers te beheren.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/resources/static/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Naam vereist
Aangepaste velden moeten een naam hebben ingesteld met `setName()` om te worden opgenomen in de formulierindiening. De naam wordt gebruikt als sleutel om de waarde van het veld te recupereren via `event.getData()`.
:::

## Annuleerknop {#cancel-button}

`Login` bevat standaard een [Annuleren] knop die verborgen is. Dit is vooral nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleerknop zichtbaar te maken, geef je een label voor de knop. Je kunt ook luisteren naar annuleergebeurtenissen om de annulering op de juiste manier te verwerken.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Elementen verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit stelt je in staat om de zichtbaarheid te wisselen zonder de component uit je code te verwijderen.
:::

## Wachtwoordbeheerders {#password-managers}

Deze component werkt samen met op de browser gebaseerde wachtwoordbeheerders om het inlogproces te vereenvoudigen. In Chromium-gebaseerde browsers integreert het met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die biedt:

- **Auto-fill**: De browser kan automatisch de gebruikersnaam en wachtwoordvelden invullen als de gebruiker inloggegevens voor de site heeft opgeslagen.
- **Inloggegevensbeheer**: Na het inloggen kan de browser de gebruiker vragen om nieuwe inloggegevens op te slaan, zodat toekomstige inloggen sneller en gemakkelijker gaan.
- **Selectie van inloggegevens**: Als er meerdere inloggegevens zijn opgeslagen, kan de browser de gebruiker een keuze aanbieden uit een van de opgeslagen sets.

## Stijl {#styling}

<TableBuilder name="Login" />
