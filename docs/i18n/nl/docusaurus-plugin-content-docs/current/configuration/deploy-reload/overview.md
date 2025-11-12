---
title: Redeployment en Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
Efficiënte ontwikkelingsworkflows vertrouwen op tools die codewijzigingen detecteren en de app automatisch in realtime bijwerken. Continue implementatie en dynamisch herladen werken samen om het ontwikkelingsproces te vereenvoudigen door handmatige stappen te verminderen, zodat je je wijzigingen snel kunt zien zonder de server handmatig opnieuw te starten.

## Herimplementatie {#redeployment}

Herimplementatie in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates in de app worden weergegeven zonder een handmatige serverherstart. Dit proces omvat doorgaans het bijwerken van Java-klassen en webresources on-the-fly.

In een webforJ-app betekent dit dat het WAR-bestand opnieuw wordt gegenereerd telkens wanneer er wijzigingen in de code worden aangebracht.

Wijzigingen in Java-klassen en resources op het classpath worden doorgaans bewaakt door de IDE. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, worden deze tools geactiveerd om de bijgewerkte klassebestanden in de doeldirectory te compilen en toe te passen.

Voor de beste ervaring, gebruik automatische herimplementatie in combinatie met tools of instellingen die het herladen van de browser automatiseren.

## Live herladen {#live-reload}

Zodra wijzigingen zijn geïmplementeerd, herlaadt live herladen de app automatisch, zodat de browser updates onmiddellijk weergeeft, zonder handmatig de browser te hoeven vernieuwen.

In een webforJ-app kan live herladen automatisch de weergave vernieuwen, componenten opnieuw renderen om de laatste status van de app te tonen, of zelfs wijzigingen patchen indien nodig op aanvraag.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
