---
title: Maven Jetty plugin
_i18n_hash: 13b8de676f30b5a21eb7e9c2b49945b6
---
De Maven Jetty-plugin is een populaire tool die ontwikkelaars in staat stelt om Java-webapps uit te voeren binnen een ingebedde Jetty-server, rechtstreeks vanuit hun Maven-projecten.

De Jetty-plugin start een ingebedde Jetty-server die de bestanden van je app, inclusief Java-classes en bronnen, controleert op wijzigingen. Wanneer er updates worden gedetecteerd, wordt de app automatisch opnieuw gedeployed, wat de ontwikkeling versnelt door handmatige build- en deploymentstappen te elimineren.

## Jetty-configuraties {#jetty-configurations}

Hier zijn enkele essentiële configuraties om de hot deployment en serverinteractie-instellingen van de plugin fijn af te stemmen:

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard        |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **`scan`**         | Configureert hoe vaak de Jetty-server zoekt naar bestandswijzigingen in de **`pom.xml`**. Het skeletproject stelt dit in op `2` seconden. Het verhogen van dit interval kan de CPU-belasting verminderen, maar kan ook vertraging veroorzaken bij het weergeven van wijzigingen in de app. | `1`              |

## webforJ-configuraties {#webforj-configurations}

| Eigenschap                          | Beschrijving                                                                                                                                                                           | Standaard        |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|
| **`webforj.reloadOnServerError`** | Bij gebruik van hot redeploy wordt het hele WAR-bestand vervangen. Als de client een verzoek verstuurt terwijl de server opnieuw opstart, treedt er een fout op. Deze instelling stelt de client in staat om een pagina-herlaadpoging te doen, ervan uitgaande dat de server spoedig weer online zal zijn. Geldt alleen voor ontwikkelomgevingen en behandelt alleen fouten die specifiek zijn voor hot redeployment. | `on`             |
| **`webforj.clientHeartbeatRate`** | Stelt het interval in voor client pings om de beschikbaarheid van de server te vragen. Dit houdt de communicatie tussen client en server open. Voor ontwikkeling, gebruik kortere intervallen voor snellere foutdetectie. In productie, stel dit in op ten minste 50 seconden om overmatige verzoeken te vermijden. | `50s`            |

## Gebruiksoverwegingen {#usage-considerations}

Hoewel de Jetty-plugin zeer effectief is voor ontwikkeling, zijn er een paar potentiële beperkingen:

- **Geheugen- en CPU-gebruik**: Frequente bestandsaanvragen, ingesteld door lage `scan`-waarden in de `pom.xml`, kunnen het resourceverbruik verhogen, vooral bij grote projecten. Het verhogen van het interval kan de belasting verminderen, maar ook de opnieuw implementatietijd vertragen.

- **Beperkt gebruik in productie**: De Jetty-plugin is ontworpen voor ontwikkeling, niet voor productieomgevingen. Het mist de prestatieoptimalisatie en beveiligingsconfiguraties die vereist zijn voor productie, waardoor het beter geschikt is voor lokaal testen.

- **Sessiebeheer**: Tijdens hot redeployment kunnen gebruikerssessies mogelijk niet worden bewaard, vooral niet wanneer er grote structurele wijzigingen in de code plaatsvinden. Dit kan tests verstoren die gebruikerssessiegegevens omvatten, wat handmatig sessiebeheer of workaround-configuraties voor ontwikkeling vereist.
