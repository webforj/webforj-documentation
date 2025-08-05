---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De Login-component is ontworpen om een gebruiksvriendelijke interface voor authenticatie te bieden, waardoor gebruikers zich kunnen aanmelden met een gebruikersnaam en wachtwoord. Het ondersteunt verschillende aanpassingen om de gebruikerservaring op verschillende apparaten en locaties te verbeteren.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

De Login-component biedt een gebruiksvriendelijke inlogformulierinterface binnen een dialoog voor het invoeren van authenticatiegegevens. Het verbetert de gebruikerservaring door te bieden:
   >- Duidelijke invoervelden voor gebruikersnaam en wachtwoord.
   >- Zichtbaarheidsschakelaar voor wachtwoord om invoer te verifiëren.
   >- Invoervalidatiefedback om de juiste indeling te vragen voordat deze wordt verzonden.

## Login submission {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de logincomponent deze invoer als vereiste velden. Zodra de validatie is geslaagd, wordt een formulierindienevenement geactiveerd, waarbij de ingevoerde gegevens worden verzonden. Om meerdere indieningen te voorkomen, wordt de `Signin` knop onmiddellijk uitgeschakeld.

De demo hieronder illustreert een basisformulierindieningsproces. Als de gebruikersnaam en het wachtwoord beide zijn ingesteld op `"admin"`, sluit de inlogdialoog en verschijnt er een uitlogknop. Als de gegevens niet matchen, wordt de standaardfoutmelding van het inlogformulier weergegeven.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info De Signin-knop uitschakelen
Standaard schakelt het inlogformulier onmiddellijk de `Signin` knop uit zodra de component de logininvoer als correct valideert, om meerdere indieningen te voorkomen. U kunt de `Signin` knop opnieuw inschakelen met de `setEnabled(true)`-methode.
:::

:::tip Lege wachtwoorden toestaan
In bepaalde scenario's kunnen lege wachtwoorden zijn toegestaan, waardoor gebruikers zich alleen met een gebruikersnaam kunnen aanmelden. De inlogdialoog kan worden geconfigureerd om lege wachtwoorden te accepteren door `setEmptyPassword(true)` in te stellen.
:::

## Internationalization (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de logincomponent zijn volledig aanpasbaar met behulp van de `LoginI18n` klasse. Deze flexibiliteit stelt u in staat om de inloginterface af te stemmen op specifieke localisatie-eisen of personalisatievoorkeuren.

De demo hieronder toont hoe u een Duitse vertaling voor de inlogdialoog kunt bieden, zodat alle interface-elementen zijn aangepast aan de Duitse taal om de gebruikerservaring voor Duitstalige gebruikers te verbeteren.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Custom fields {#custom-fields}

De logincomponent omvat verschillende slots, waarmee u indien nodig extra velden kunt toevoegen. Deze functie biedt meer controle over de informatie die nodig is voor succesvolle authenticatie.

In het onderstaande voorbeeld is een Klant-ID-veld toegevoegd aan het inlogformulier. Gebruikers moeten een geldige ID opgeven om de authenticatie te voltooien, wat de veiligheid vergroot en ervoor zorgt dat toegang alleen wordt verleend na verificatie van alle vereiste gegevens.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Indienen lastigheid
Houd er rekening mee dat de logincomponent extra velden die aan het formulier zijn toegevoegd niet automatisch herkent of opneemt in zijn indienen lastigheid. Dit betekent dat ontwikkelaars expliciet de waarde van aanvullende velden van de clientzijde moeten ophalen en deze moeten afhandelen volgens de vereisten van de app om het authenticatieproces te voltooien.
:::

## Cancel button {#cancel-button}

In bepaalde scenario's kan het wenselijk zijn om een annuleerknop naast de `Signin` knop toe te voegen. Deze functie is bijzonder nuttig wanneer een gebruiker een beperkt gebied van de app probeert te openen en een optie nodig heeft om de actie te annuleren en terug te keren naar hun vorige locatie. Het inlogformulier omvat standaard een annuleerknop, maar deze is verborgen.

Om de annuleerknop zichtbaar te maken, moet u een label voor deze voorzien - eenmaal gelabeld, verschijnt het op het scherm. U kunt ook luisteren naar annuleer- evenementen om gepast te reageren op gebruikersacties, waarmee een soepele en gebruiksvriendelijke ervaring voor het navigeren door de app wordt gegarandeerd.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen verbergen
Om een element van het inlogscherm te verbergen, stelt u eenvoudig het label in op een lege tekenreeks. Deze aanpak is bijzonder nuttig voor het tijdelijk verwijderen van interfacecomponenten zonder de codebase permanent te wijzigen.
:::

## Password managers {#password-managers}

De logincomponent is ontworpen om compatibel te zijn met op de browser gebaseerde wachtwoordbeheerders, waardoor de gebruikerservaring wordt verbeterd door het inlogproces te vereenvoudigen. Voor gebruikers van Chromium-gebaseerde browsers integreert de component naadloos met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API. Deze integratie maakt verschillende handige functies mogelijk:

- **Auto-vullen**: De browser kan automatisch de gebruikersnaam en wachtwoordvelden invullen als de gebruiker opgeslagen referenties voor de site heeft.
- **Credential Management**: Na het inloggen kan de browser de gebruiker vragen om nieuwe referenties op te slaan, waardoor toekomstige aanmeldingen sneller en eenvoudiger worden.
- **Credential Selection**: Als er meerdere referenties zijn opgeslagen, kan de browser de gebruiker de keuze bieden om een van de opgeslagen sets te selecteren.

## Styling {#styling}

<TableBuilder name="Login" />
