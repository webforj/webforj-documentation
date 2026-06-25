---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
webforJ's [server-driven model](/docs/architecture/client-server) en ingebouwde beschermingen tegen [common threats](/docs/security/application-security/common-threats) dekken veel, maar een veilige implementatie hangt nog steeds af van hoe je de app bedient. De onderstaande stappen ronden het geheel af.

## Versleutel elke verbinding {#encrypt-every-connection}

Voer productieverkeer alleen via HTTPS uit. Beëindig TLS bij de container, proxy of load balancer voor de app, en stuur elke plain-HTTP-aanroep door naar de beveiligde versie, zodat inloggegevens en sessie-identificaties nooit ongecodeerd worden verzonden.

## Vertrouw niets van de browser {#trust-nothing-from-the-browser}

Een gemanipuleerde client kan alles verzenden. Valideer elke waarde die je code ontvangt opnieuw, zelfs waarden die je interface al heeft beperkt, voordat je ze opslaat of erop handelt. Het artikel [Client/Server Interaction](/docs/architecture/client-server) legt uit waarom de server de enige plek is waar een regel echt kan gelden.

webforJ's [data binding and validation](/docs/data-binding/validation/overview) helpt hier: omdat binding op de server in Java draait, worden de beperkingen die je aan een model koppelt, inclusief [Jakarta validation](/docs/data-binding/validation/jakarta-validation), server-side afgedwongen in plaats van alleen in de browser. Beschouw dat als je integriteitslaag, niet als een verdediging tegen injecties of markup-aanvallen, waarvoor nog steeds de behandeling is beschreven in het artikel [Common Threats](/docs/security/application-security/common-threats).

## Uitgeschakeld en verborgen is geen beveiliging {#disabled-and-hidden-arent-security}

`setEnabled(false)` en `setVisible(false)` zijn interface-aanwijzingen, geen toegangscontroles. webforJ weerspiegelt de uitgeschakelde status van een controle naar de client, maar het stopt een gemanipuleerde client niet van het opnieuw inschakelen van die controle en het activeren van de actie. Vertrouw nooit op een uitgeschakelde of verborgen controle om te voorkomen dat er iets gebeurt.

Plaats de echte regel in de server-side handler: bevestig dat de gebruiker is toegestaan en dat de precondities gelden voordat je de actie uitvoert, precies zoals je zou doen als de controle de hele tijd was ingeschakeld. De uitgeschakelde status helpt eerlijke gebruikers; de server-side regel stopt oneerlijke.

## Beperk je weergaven {#lock-down-your-views}

Beveilig weergaven met [route security](/docs/security/overview) zodat elke weergave de juiste authenticatie en rollen vereist. Geef mensen de smalste toegang die hen in staat stelt om te werken, en geef de voorkeur aan een secure-by-default houding waarbij een ongemarkeerde route nog steeds inloggen vereist.

## Houd geheimen extern {#keep-secrets-external}

Inloggegevens, sleutels en tokens horen niet in code of in je repository. Haal ze in plaats daarvan uit de omgeving of een externe bron, zoals getoond in [Managing Secrets](/docs/security/application-security/managing-secrets).

## Blijf actueel met afhankelijkheden {#stay-current-on-dependencies}

De bibliotheken die je gebruikt zijn een grotere bron van risico dan je eigen code. Houd adviezen in de gaten, werk webforJ en je andere afhankelijkheden regelmatig bij, en wanneer een gepatchte versie van een transitieve bibliotheek eerder uitkomt dan de bibliotheek die het gebruikt, zet dan de gefixte versie vast in je `pom.xml`.

## Faillissement stil {#fail-quietly}

Laat geen stack traces, bestandslocaties of interne identificaties bij eindgebruikers komen. Registreer de details in je serverlogs en presenteer een eenvoudige, generieke boodschap in de interface. Registreer een aangepaste handler via webforJ's [error handling](/docs/advanced/error-handling) zodat niet-afgehandelde uitzonderingen een gecontroleerde pagina naar boven brengen in plaats van ruwe diagnosticen.

## Verantwoordelijke openbaarmaking {#disclose-responsibly}

Heeft u een mogelijke fout in webforJ zelf gevonden? Meld dit privé via GitHub's [private vulnerability reporting](https://github.com/webforj/webforj/security/advisories) in plaats van een openbaar probleem of pull-verzoek te openen, zodat een oplossing kan worden gevonden voordat de details bekend zijn.
