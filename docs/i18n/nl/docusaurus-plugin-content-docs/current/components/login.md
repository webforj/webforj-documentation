---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

De Login-component is ontworpen om een gebruiksvriendelijke interface voor authenticatie te bieden, waarmee gebruikers kunnen inloggen met een gebruikersnaam en wachtwoord. Het ondersteunt verschillende aanpassingen om de gebruikerservaring op verschillende apparaten en in verschillende regio's te verbeteren.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

De Login-component biedt een gebruiksvriendelijke inlogformulierinterface binnen een dialoog voor het invoeren van authenticatiegegevens. Het verbetert de gebruikerservaring door te bieden:
   >- Duidelijke invoervelden voor gebruikersnaam en wachtwoord.
   >- Zichtbaarheidsschakelaar voor wachtwoord om de invoer te verifiëren.
   >- Invoervalidatiefeedback om het juiste formaat te stimuleren voor indiening.

## Login-indiening {#login-submission}

Wanneer gebruikers hun gebruikersnaam en wachtwoord invoeren, valideert de logincomponent deze invoeren als verplichte velden. Zodra de validatie goedkeurt, wordt een formulierindieningsgebeurtenis getriggerd, die de ingevoerde gegevens aflevert. Om meerdere indieningen te voorkomen, wordt de `Signin`-knop onmiddellijk uitgeschakeld.

De demo hieronder illustreert een basisformulierindieningsproces. Als zowel de gebruikersnaam als het wachtwoord zijn ingesteld op `"admin"`, sluit de inlogdialoog en verschijnt er een uitlogknop. Als de gegevens niet overeenkomen, wordt de standaardfoutmelding van het inlogformulier weergegeven.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Uitschakelen van de Signin-knop
Standaard schakelt het inlogformulier de `Signin`-knop onmiddellijk uit zodra de component de inloggegevens valideert als correct, om meerdere indieningen te voorkomen. U kunt de `Signin`-knop opnieuw inschakelen met behulp van de `setEnabled(true)`-methode.
:::

:::tip Lege wachtwoorden toestaan
In bepaalde scenario's kunnen lege wachtwoorden toegestaan zijn, waardoor gebruikers alleen met een gebruikersnaam kunnen inloggen. De inlogdialoog kan worden geconfigureerd om lege wachtwoorden te accepteren door `setEmptyPassword(true)` in te stellen.
:::

## Internationalisering (i18n) {#internationalization-i18n}

De titels, beschrijvingen, labels en berichten binnen de inlogcomponent zijn volledig aanpasbaar met behulp van de `LoginI18n`-klasse. Deze flexibiliteit stelt u in staat om de inloginterface aan te passen aan specifieke lokalisatiebehoeften of personalisatievoorkeuren.

De demo hieronder illustreert hoe een Duitse vertaling voor de inlogdialoog kan worden aangeboden, zodat alle interface-elementen zijn aangepast aan de Duitse taal om de gebruikerservaring voor Duitstalige gebruikers te verbeteren.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Aangepaste velden {#custom-fields}

De inlogcomponent bevat verschillende slots waarmee u indien nodig extra velden kunt toevoegen. Deze functie biedt meer controle over de informatie die vereist is voor succesvolle authenticatie.

In het onderstaande voorbeeld is een Klant-ID-veld toegevoegd aan het inlogformulier. Gebruikers moeten een geldige ID opgeven om de authenticatie te voltooien, wat de beveiliging verbetert en ervoor zorgt dat toegang alleen wordt verleend na verificatie van alle vereiste gegevens.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Indiening Payload
Houd er rekening mee dat de inlogcomponent extra velden die aan het formulier zijn toegevoegd niet automatisch herkent of opneemt in zijn indieningspayload. Dit betekent dat ontwikkelaars expliciet de waarde van eventuele extra velden aan de clientzijde moeten ophalen en deze moeten verwerken volgens de eisen van de app om het authenticatieproces te voltooien.
:::

## Annuleerknop {#cancel-button}

In bepaalde scenario's kan het wenselijk zijn om een annuleerknop naast de `Signin`-knop toe te voegen. Deze functie is vooral nuttig wanneer een gebruiker probeert toegang te krijgen tot een beperkt gebied van de app en de optie nodig heeft om de actie te annuleren en terug te keren naar zijn vorige locatie. Het inlogformulier bevat standaard een annuleerknop, maar deze is verborgen.

Om de annuleerknop zichtbaar te maken, moet u er een label aan geven - eenmaal gelabeld, verschijnt deze op het scherm. U kunt ook luisteren naar annuleergebeurtenissen om passend te reageren op gebruikersacties, zodat een soepele en gebruiksvriendelijke ervaring bij het navigeren door de app wordt gegarandeerd.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementen verbergen
Om een element van het inlogscherm te verbergen, stelt u eenvoudig het label in op een lege string. Deze benadering is bijzonder nuttig voor het tijdelijk verwijderen van interfacecomponenten zonder de codebase permanent te wijzigen.
:::

## Wachtwoordbeheerders {#password-managers}

De inlogcomponent is ontworpen om compatibel te zijn met browsergebaseerde wachtwoordbeheerders, waardoor de gebruikerservaring wordt verbeterd door het inlogproces te vereenvoudigen. Voor gebruikers van Chromium-gebaseerde browsers integreert de component naadloos met de [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API. Deze integratie maakt verschillende handige functies mogelijk:

- **Auto-invullen**: De browser kan automatisch de velden voor gebruikersnaam en wachtwoord invullen als de gebruiker gemaakte wachtwoorden voor de site heeft opgeslagen.
- **Beheer van inloggegevens**: Na het inloggen kan de browser de gebruiker vragen om nieuwe inloggegevens op te slaan, waardoor toekomstige inloggen sneller en gemakkelijker wordt.
- **Selectie van inloggegevens**: Als er meerdere inloggegevens zijn opgeslagen, kan de browser de gebruiker een keuze bieden uit een van de opgeslagen sets.

## Styling {#styling}

<TableBuilder name="Login" />
