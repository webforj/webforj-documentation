---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
Efficiënte ontwikkelworkflows zijn afhankelijk van tools die codewijzigingen detecteren en de app automatisch in realtime bijwerken. Continue implementatie en dynamisch herladen werken samen om het ontwikkelproces te vereenvoudigen door handmatige stappen te verminderen, zodat je jouw wijzigingen snel kunt zien zonder de server handmatig opnieuw te starten.

## Herimplementatie {#redeployment}

Herimplementatie in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates zichtbaar zijn in de app zonder een handmatige serverherstart. Dit proces houdt doorgaans in dat Java-klassen en webbronnen tijdelijk worden bijgewerkt.

In een webforJ-app betekent dit dat het WAR-bestand opnieuw wordt gegenereerd wanneer aanpassingen aan de code worden aangebracht.

Wijzigingen aan Java-klassen en bronnen op het classpath worden doorgaans gemonitord door de IDE. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, treden deze tools in werking om de bijgewerkte klassebestanden in de doelmap te compileren en aan te brengen om de wijzigingen toe te passen.

Voor de beste ervaring gebruik je automatische herimplementatie in combinatie met tools of instellingen die het herladen van de browser automatiseren.

## Live herladen {#live-reload}

Zodra wijzigingen zijn geïmplementeerd, herlaadt live herladen automatisch de app, zodat de browser updates onmiddellijk weergeeft, zonder dat een handmatige vernieuwing van de browser nodig is.

In een webforJ-app kan live herladen automatisch de weergave vernieuwen, componenten opnieuw renderen om de laatste staat van de app te laten zien, of zelfs wijzigingen patchen indien nodig op aanvraag.

Voor frontend-bronnen bouwt de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) opnieuw op bij elke wijziging en patcht een stylesheet of afbeelding ter plaatse, waarbij de weergave alleen wordt herladen wanneer een script verandert.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
