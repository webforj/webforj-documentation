---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
_i18n_hash: cdcad4b5ef5d3ba0bd84e4d9deac49b5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De `Login` component vereenvoudigt gebruikersauthenticatie door een kant-en-klaar inlogdialoog te bieden met gebruikersnaam en wachtwoordvelden. Het bevat functies zoals invoervalidatie, aanpasbare labels en berichten, wachtwoordzichtbaarheidscontroles en ondersteuning voor aanvullende aangepaste velden.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Inloggen {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de `Login` component deze invoeren als verplichte velden. Zodra de validatie is geslaagd, wordt een formulierindieningsevent getriggerd, waarbij de ingevoerde gegevens worden afgeleverd. Om meerdere indieningen te voorkomen, wordt de [Aanmelden] knop onmiddellijk uitgeschakeld.

Het volgende illustreert een basale `Login` component. Als de gebruikersnaam en het wachtwoord beide zijn ingesteld op `"admin"`, sluit het inlogdialoogvenster en verschijnt er een [Afmelden] knop. Als de referenties niet overeenkomen, wordt het standaard foutbericht weergegeven.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Uitschakelen van de [Aanmelden] knop
Standaard schakelt `Login` onmiddellijk de [Aanmelden] knop uit zodra de component de inloginvoeren als correct valideert, om meerdere indieningen te voorkomen. Je kunt de [Aanmelden] knop opnieuw inschakelen met de methode `setEnabled(true)`.
:::

:::tip Toestaan van lege wachtwoorden
Je kunt gebruikers toestaan om in te loggen met alleen een gebruikersnaam door de methode `setEmptyPassword(true)` te gebruiken.
:::

## Formuliereactie <DocChip chip='since' label='25.10' />{#form-action}

De `Login` component kan formuliergegevens rechtstreeks naar een opgegeven URL indienen in plaats van de indiening via het indieningsevent te verwerken. Wanneer een actie-URL is ingesteld, voert het formulier een standaard POST-verzoek uit met de gebruikersnaam en het wachtwoord als formulierparameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Bij gebruik van `setAction()`, omzeilt de formulierindiening het `LoginSubmitEvent` en voert in plaats daarvan een traditionele HTTP POST-aanroep uit naar het opgegeven eindpunt. De gebruikersnaam en het wachtwoord worden verzonden als formulierparameters genaamd "username" en "password", respectievelijk. Aangepaste velden met een naamattribuut worden ook opgenomen in het POST-verzoek.

:::tip 
Als er geen actie-URL is ingesteld, wordt de formulierindiening afgehandeld via het `LoginSubmitEvent`, zodat je referenties programmatisch op de serverzijde kunt verwerken.
:::

## Internationalisatie (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de `Login` component zijn volledig aanpasbaar met de `LoginI18n` klasse. Deze flexibiliteit stelt je in staat om de inloginterface aan te passen aan specifieke lokalisatievereisten of personalisatievoorkeuren.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Aangepaste velden {#custom-fields}

De `Login` component bevat verschillende slots waarmee je extra velden kunt toevoegen zoals nodig. Aangepaste velden worden automatisch verzameld wanneer het formulier wordt ingediend en kunnen worden geopend via de gegevenskaart van het indieningsevent.

De volgende login heeft een aangepast veld toegevoegd voor een klant-ID. Dit kan je helpen om bedrijven of afdelingen met gedeelde inhoud over meerdere gebruikers te beheren.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Naam Vereist
Aangepaste velden moeten een naam hebben die is ingesteld met `setName()` om opgenomen te worden in de formulierindiening. De naam wordt gebruikt als de sleutel om de waarde van het veld op te halen uit `event.getData()`.
:::

## Annuleerknop {#cancel-button}

`Login` omvat een [Annuleren] knop die standaard verborgen is. Dit is bijzonder nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en een optie nodig heeft om terug te keren naar hun vorige locatie zonder de inlog te voltooien.

Om de annuleren knop zichtbaar te maken, geef een label voor deze op. Je kunt ook luisteren naar annuleringsevents om de annulering op de juiste manier af te handelen.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen verbergen
Om een element te verbergen, stel je het label in op een lege string. Dit stelt je in staat om de zichtbaarheid te togglen zonder het component uit je code te verwijderen.
:::

## Wachtwoordbeheerders {#password-managers}

Deze component werkt met op de browser gebaseerde wachtwoordbeheerders om het inlogproces te vereenvoudigen. In op Chromium gebaseerde browsers integreert het met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, die biedt:

- **Auto-fill**: De browser kan automatisch de gebruikersnaam en wachtwoordvelden invullen als de gebruiker referenties voor de site heeft opgeslagen.
- **Credential Management**: Na het inloggen kan de browser de gebruiker vragen om nieuwe referenties op te slaan, waardoor toekomstige inloggen sneller en gemakkelijker worden.
- **Credential Selection**: Als er meerdere referenties zijn opgeslagen, kan de browser de gebruiker een keuze bieden uit een van de opgeslagen sets.

## Stijl {#styling}

<TableBuilder name="Login" />
