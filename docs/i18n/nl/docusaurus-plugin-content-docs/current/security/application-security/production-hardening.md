---
sidebar_position: 3
title: Productie-hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
webforJ's [server-driven model](/docs/architecture/client-server) en ingebouwde beveiligingen tegen [gebruikelijke bedreigingen](/docs/security/application-security/common-threats) dekken veel, maar een veilige implementatie hangt nog steeds af van hoe je de app opereert. De onderstaande stappen ronden het geheel af.

## Versleutel elke verbinding {#encrypt-every-connection}

Voer productieverkeer alleen via HTTPS uit. Beëindig TLS bij de container, proxy of load balancer voor de app en stuur elke gewone HTTP-verzoek door naar de beveiligde variant, zodat inloggegevens en sessie-identifiers nooit ongecodeerd worden verzonden.

## Vertrouw niets van de browser {#trust-nothing-from-the-browser}

Een gemanipuleerde client kan alles verzenden. Valideer elke waarde die je code ontvangt opnieuw, zelfs waarden die je interface al heeft beperkt, voordat je ze opslaat of erop handelt. Het artikel [Client/Server Interaction](/docs/architecture/client-server) legt uit waarom de server de enige plek is waar een regel echt kan gelden.

webforJ's [gegevensbinding en validatie](/docs/data-binding/validation/overview) helpt hier: omdat binding op de server in Java draait, worden de beperkingen die je aan een model koppelt, inclusief [Jakarta-validatie](/docs/data-binding/validation/jakarta-validation), serverzijde afgedwongen in plaats van alleen in de browser. Beschouw dat als je integriteitslaag, en niet als een verdediging tegen injectie- of markup-aanvallen, die nog steeds de behandeling vereisen die in het artikel over [Gebruikelijke Bedreigingen](/docs/security/application-security/common-threats) wordt beschreven.

## Uitgeschakeld en verborgen zijn geen beveiliging {#disabled-and-hidden-arent-security}

`setEnabled(false)` en `setVisible(false)` zijn interface-aanwijzingen, geen toegangscontrole. webforJ spiegelt de uitgeschakelde status van een controle naar de client, maar het stopt een gemanipuleerde client er niet van om die controle opnieuw in te schakelen en de actie te activeren. Vertrouw nooit op een uitgeschakelde of verborgen controle om te voorkomen dat iets gebeurt.

Plaats de echte regel in de server-side handler: bevestig dat de gebruiker is toegestaan en dat de noodzakelijke voorwaarden gelden voordat je de actie uitvoert, precies zoals je zou doen als de controle de hele tijd ingeschakeld was geweest. De uitgeschakelde status begeleidt eerlijke gebruikers; de server-side regel stopt oneerlijke.

## Beperk je views {#lock-down-your-views}

Beveilig views met [routebeveiliging](/docs/security/overview), zodat elke view de juiste authenticatie en rollen vereist. Geef mensen de smalste toegang die hen in staat stelt om te werken, en geef de voorkeur aan een beveiligd standaardbeleid waarbij een ongecodeerde route nog steeds inloggen vereist.

## Houd geheimen extern {#keep-secrets-external}

Inloggegevens, sleutels en tokens horen niet in code of in je repository. Haal ze in plaats daarvan uit de omgeving of een externe bron, zoals weergegeven in [Beheer van Geheimen](/docs/security/application-security/managing-secrets).

## Zet ontwikkeltools uit {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) is de ontwikkelomgeving die een draaiende app inspecteert en wijzigingen terugschrijft naar de Java-bron. Het vereist zowel `webforj.debug` als `webforj.devtools.craftforj.enabled`, en standaard antwoordt het alleen de machine die de app draait. Projecten die zijn gemaakt met [startforJ](https://docs.webforj.com/startforj) of vanuit een webforJ [archetype](/docs/building-ui/archetypes/overview) hebben beide instellingen ingeschakeld voor ontwikkeling, dus bevestig ze in plaats van aan te nemen.

Controleer of beide eigenschappen niet zijn ingesteld of `false` zijn in de configuratie die je daadwerkelijk implementeert, inclusief elke omgevingsvariabele of profiel die alleen geldt in productie. Laad de geïmplanteerde app en bevestig dat er geen craftforJ-trigger op de pagina verschijnt. Zie [craftforJ-beveiliging](/docs/craftforj/security) voor het volledige overzicht.

## Blijf op de hoogte van afhankelijkheden {#stay-current-on-dependencies}

De bibliotheken die je gebruikt vormen een grotere bron van risico dan je eigen code. Volg adviezen, update webforJ en je andere afhankelijkheden regelmatig, en wanneer een gepatchte versie van een transitieve bibliotheek eerder verschijnt dan de bibliotheek die deze oproept, pin de gefixte versie in je `pom.xml`.

## Failliet stilletjes {#fail-quietly}

Laat geen stack-traces, bestandslocaties of interne identificatoren bij eindgebruikers komen. Registreer de details in je serverlogs en presenteer een eenvoudige, generieke boodschap in de interface. Registreer een aangepaste handler via webforJ's [foutafhandeling](/docs/advanced/error-handling) zodat ongevangen uitzonderingen een gecontroleerde pagina weergeven in plaats van ruwe diagnostiek.

## Verklaar verantwoordelijk {#disclose-responsibly}

Heb je een mogelijke kwetsbaarheid in webforJ zelf gevonden? Meld het privé via GitHub's [private vulnerability reporting](https://github.com/webforj/webforj/security/advisories) in plaats van een publiek probleem of pullverzoek te openen, zodat een oplossing kan komen voordat de details bekend worden.
