---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: 1b9e4b7fe64a9bcb0aa2aa16b0866ec9
---
Efficiënte ontwikkelingsworkflows zijn afhankelijk van tools die codewijzigingen detecteren en de app automatisch in real time bijwerken. Continuous Deployment en Dynamic Reload werken samen om het ontwikkelingsproces te vereenvoudigen door handmatige stappen te verminderen, zodat je je wijzigingen snel kunt zien zonder de server handmatig opnieuw te hoeven starten.

## Heruitrol {#redeployment}

Heruitrol in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates in de app worden weerspiegeld zonder een handmatige serverherstart. Dit proces omvat doorgaans het bijwerken van Java-klassen en webresources on-the-fly.

In een webforJ-app betekent dit dat het WAR-bestand wordt gegenereerd telkens wanneer er wijzigingen in de code worden aangebracht.

Wijzigingen aan Java-klassen en resources op het classpath worden doorgaans bewaakt door de IDE. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, treden deze tools in werking om de bijgewerkte klassebestanden in de doeldirectory te compileren en toe te passen.

Voor de beste ervaring kun je automatische heruitrol gebruiken in combinatie met tools of instellingen die het opnieuw laden van de browser automatiseren.

## Live herladen {#live-reload}

Zodra wijzigingen zijn uitgerold, herlaadt live reload automatisch de app zodat de browser updates onmiddellijk weerspiegelt, zonder dat een handmatige browserverversing nodig is.

In een webforJ-app kan live reload automatisch de weergave vernieuwen, waardoor componenten opnieuw worden gerenderd om de laatste staat van de app weer te geven, of zelfs wijzigingen patchen indien nodig op aanvraag.

Voor frontend-bronnen bouwt de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) opnieuw bij elke wijziging en patcht een stylesheet of afbeelding ter plaatse, en herlaadt de weergave alleen wanneer een script verandert.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
