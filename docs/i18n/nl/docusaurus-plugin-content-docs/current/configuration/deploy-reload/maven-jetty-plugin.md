---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 7b8efc852651f2bea9db4ca110fe11ed
---
De Maven Jetty-plug-in is een populaire tool die ontwikkelaars in staat stelt om Java-webapplicaties uit te voeren binnen een ingebedde Jetty-server, rechtstreeks vanuit hun Maven-projecten. 

De Jetty-plug-in start een ingebedde Jetty-server die de bestanden van je app, inclusief Java-klassen en bronnen, controleert op wijzigingen. Wanneer het updates detecteert, wordt de app automatisch opnieuw gedeployed, wat de ontwikkeling versnelt door handmatige build- en implementatiestappen te elimineren. 

:::tip Frontend-wijzigingen
Wijzigingen onder `src/main/frontend` worden afgehandeld door de [frontend watch](/docs/configuration/deploy-reload/frontend-watch), die deze opnieuw opbouwt en de browser tegelijk met de server ververst.
:::

## Jetty-configuraties {#jetty-configurations}

Hier zijn enkele essentiële configuraties voor het verfijnen van de hot deployment en serverinteractie-instellingen van de plug-in:

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard      |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configureert hoe vaak de Jetty-server bestanden controleert op wijzigingen in de **`pom.xml`**. Het skeletproject stelt dit in op `2` seconden. Het verhogen van dit interval kan de CPU-belasting verminderen, maar kan ook vertraging veroorzaken in het weergeven van wijzigingen in de app. | `1`            |

## webforJ-configuraties {#webforj-configurations}

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard      |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Bij het gebruik van hot redeploy wordt het hele WAR-bestand vervangen. Als de cliënt een verzoek verstuurt terwijl de server opnieuw opstart, treedt er een fout op. Deze instelling stelt de cliënt in staat om een pagina opnieuw te proberen te laden, onder de veronderstelling dat de server binnenkort weer online is. Geldt alleen voor ontwikkelomgevingen en behandelt alleen fouten die specifiek zijn voor hot redeployment. | `on`           |
| **`webforj.clientHeartbeatRate`** | Stelt het interval in voor client-pings om de beschikbaarheid van de server te controleren. Dit houdt de communicatie tussen cliënt en server open. Voor ontwikkeling, gebruik kortere intervallen voor snellere foutdetectie. In productie, stel dit in op ten minste 50 seconden om overmatige verzoeken te vermijden. | `50s`          |

## Gebruiksoverwegingen {#usage-considerations}

Hoewel de Jetty-plug-in zeer effectief is voor ontwikkeling, zijn er enkele mogelijke beperkingen:

- **Geheugen- en CPU-gebruik**: Veelvuldig bestandsscanning, ingesteld door lage `scan`-waarden in de `pom.xml`, kan het resourceverbruik verhogen, vooral bij grote projecten. Het verhogen van het interval kan de belasting verminderen, maar vertraagt ook de redeployment.

- **Beperkt gebruik in productie**: De Jetty-plug-in is ontworpen voor ontwikkeling, niet voor productieomgevingen. Het mist de prestatieoptimalisatie en beveiligingsconfiguraties die vereist zijn voor productie, waardoor het beter geschikt is voor lokaal testen.

- **Sessiebeheer**: Tijdens hot redeployment kunnen gebruikerssessies verloren gaan, vooral wanneer er grote structurele wijzigingen in de code plaatsvinden. Dit kan tests verstoren die gebruikmaken van gebruikerssessiegegevens, wat handmatig sessiebeheer of workaround-configuraties voor ontwikkeling vereist.
