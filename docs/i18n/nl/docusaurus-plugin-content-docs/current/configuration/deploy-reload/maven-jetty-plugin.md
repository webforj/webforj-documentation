---
title: Maven Jetty plugin
_i18n_hash: 7311fe4d0b6c5382244d898f099b9435
---
De Maven Jetty-plugin is een populair hulpmiddel waarmee ontwikkelaars Java-webapps kunnen uitvoeren binnen een ingebedde Jetty-server, rechtstreeks vanuit hun Maven-projecten.

De Jetty Plugin lanceert een ingebedde Jetty-server die de bestanden van uw app, inclusief Java-klassen en bronnen, controleert op wijzigingen. Wanneer het updates detecteert, wordt de app automatisch opnieuw geïmplementeerd, wat de ontwikkeling versnelt door handmatige build- en implementatiestappen te elimineren.

## Jetty-configuraties {#jetty-configurations}

Hier zijn enkele essentiële configuraties voor het verfijnen van de hot deployment en serverinteractie-instellingen van de plugin:

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard      |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configureert hoe vaak de Jetty-server scans uitvoert naar bestandswijzigingen in de **`pom.xml`**. Het skeleton project stelt dit in op `2` seconden. Het verhogen van dit interval kan de CPU-belasting verminderen, maar kan ook de veranderingen vertragen die in de app worden weergegeven. | `1`            |

## webforJ-configuraties {#webforj-configurations}

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard      |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Tijdens het gebruik van hot redeploy wordt het gehele WAR-bestand gewisseld. Als de client een verzoek indient terwijl de server opnieuw opstart, treedt er een fout op. Deze instelling stelt de client in staat om te proberen een pagina te vernieuwen, ervan uitgaande dat de server binnenkort weer online zal zijn. Geldt alleen voor ontwikkelomgevingen en behandelt alleen fouten die specifiek zijn voor hot redeployment. | `on`           |
| **`webforj.clientHeartbeatRate`** | Stelt het interval in voor client-pings om de beschikbaarheid van de server te controleren. Dit houdt de communicatie tussen de client en server open. Voor ontwikkeling, gebruik kortere intervallen voor snellere foutdetectie. In productie moet dit op zijn minst 50 seconden worden ingesteld om overmatige verzoeken te voorkomen. | `50s`          |

## Gebruiksoverwegingen {#usage-considerations}

Hoewel de Jetty Plugin zeer effectief is voor ontwikkeling, zijn er een paar potentiële beperkingen:

- **Geheugen- en CPU-gebruik**: Frequent bestandsinspeccioneren, ingesteld door lage `scan`-waarden in de `pom.xml`, kan het resourceverbruik verhogen, vooral op grote projecten. Het verhogen van het interval kan de belasting verminderen, maar ook de herimplementatie vertragen.

- **Beperkt gebruik in productie**: De Jetty Plugin is ontworpen voor ontwikkeling, niet voor productieomgevingen. Het mist de prestatie-optimalisatie en beveiligingsconfiguraties die nodig zijn voor productie, waardoor het beter geschikt is voor lokale tests.

- **Sessiebeheer**: Tijdens hot redeployment kunnen gebruikerssessies mogelijk niet worden bewaard, vooral wanneer er grote structurele wijzigingen in de code optreden. Dit kan tests die gebruikerssessiegegevens omvatten verstoren, en vereist handmatig sessiebeheer of workaround-configuraties voor ontwikkeling.
