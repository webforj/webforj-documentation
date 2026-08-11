---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 6ce3da7be312bb71f2ded56a583d7687
---
De Maven Jetty-plugin is een populair hulpmiddel dat ontwikkelaars in staat stelt om Java-webapps te draaien binnen een ingebedde Jetty-server, rechtstreeks vanuit hun Maven-projecten.

De Jetty-plugin start een ingebedde Jetty-server die de bestanden van je app, inclusief Java-classes en bronnen, controleert op wijzigingen. Wanneer het updates detecteert, wordt de app automatisch opnieuw gedeployed, wat de ontwikkeling versnelt door handmatige build- en implementatiestappen te elimineren.

:::tip Frontend-wijzigingen
Wijzigingen onder `src/main/frontend` worden afgehandeld door de [frontend watch](/docs/configuration/deploy-reload/frontend-watch), die deze opnieuw opbouwt en de browser ververst samen met de server.
:::

## Jetty-configuraties {#jetty-configurations}

Hier zijn enkele essentiële configuraties voor het fijn afstemmen van de hot deployment en serverinteractie-instellingen van de plugin:

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **`scan`**         | Configureert hoe vaak de Jetty-server bestanden controleert op wijzigingen in de **`pom.xml`**. Het skeletproject stelt dit in op `2` seconden. Het verhogen van dit interval kan de CPU-belasting verminderen, maar kan ook vertraging opleveren bij het reflecteren van wijzigingen in de app. | `1`              |

## webforJ-configuraties {#webforj-configurations}

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **`webforj.reloadOnServerError`** | Bij het gebruik van hot redeploy wordt het gehele WAR-bestand verwisseld. Als de client een verzoek stuurt terwijl de server opnieuw opstart, treedt er een fout op. Deze instelling stelt de client in staat om een pagina opnieuw te laden, ervan uitgaande dat de server binnenkort weer online is. Geldt alleen voor ontwikkelomgevingen en verhandelt alleen fouten die specifiek zijn voor hot redeployment. | `on`             |
| **`webforj.clientHeartbeatRate`** | Stelt het interval in voor client-pings om de beschikbaarheid van de server te controleren. Dit houdt de communicatie tussen client en server open. Voor ontwikkeling, gebruik kortere intervallen voor snellere foutdetectie. In productie moet dit tenminste 50 seconden worden ingesteld om overmatige verzoeken te vermijden. | `50s`            |

## Gebruiksoverwegingen {#usage-considerations}

Hoewel de Jetty-plugin zeer effectief is voor ontwikkeling, heeft deze enkele potentiële beperkingen:

- **Geheugen- en CPU-gebruik**: Frequente bestandsscans, ingesteld door lage `scan`-waarden in de `pom.xml`, kunnen de resourceconsumptie verhogen, vooral bij grote projecten. Het verhogen van het interval kan de belasting verminderen, maar vertraagt ook de redeployment.

- **Beperkte productiegebruik**: De Jetty-plugin is ontworpen voor ontwikkeling, niet voor productieomgevingen. Het mist de prestatieoptimalisatie en beveiligingsconfiguraties die nodig zijn voor productie, waardoor het beter geschikt is voor lokale tests.

- **Sessiebeheer**: Tijdens hot redeployment kunnen gebruikerssessies mogelijk niet worden behouden, vooral wanneer er grote structurele veranderingen in de code plaatsvinden. Dit kan tests met gebruikerssessiegegevens verstoren, waardoor handmatig sessiebeheer of workaround-configuraties voor ontwikkeling nodig zijn.
