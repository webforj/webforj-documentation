---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
Efficiënte ontwikkelworkflows zijn afhankelijk van tools die codewijzigingen detecteren en de app automatisch in realtime bijwerken. Continue implementatie en dynamisch herladen werken samen om het ontwikkelproces te vereenvoudigen door handmatige stappen te verminderen, zodat je je wijzigingen snel kunt zien zonder de server handmatig opnieuw op te starten.

## Herimplementatie {#redeployment}

Herimplementatie in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates in de app worden weergegeven zonder een handmatige serverherstart. Dit proces omvat meestal het bijwerken van Java-klassen en webresources on-the-fly.

In een webforJ-app betekent dit het opnieuw genereren van het WAR-bestand telkens wanneer er wijzigingen in de code worden aangebracht.

Wijzigingen in Java-klassen en resources op het classpath worden doorgaans door de IDE gemonitord. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, worden deze tools ingeschakeld om de bijgewerkte klassebestanden te compileren en in de doelmap te plaatsen om deze wijzigingen toe te passen.

Tools en instellingen die het herladen van de browser automatiseren of optimaliseren, kunnen worden toegevoegd voor een soepelere ervaring.

## Live herladen {#live-reload}

Live herladen zorgt ervoor dat zodra wijzigingen zijn geïmplementeerd, de browser die updates in realtime weerspiegelt zonder dat er een handmatige browserverversing nodig is.

In een webforJ-app kan live herladen automatisch de weergave vernieuwen, componenten opnieuw renderen om de nieuwste status van de app weer te geven, of zelfs wijzigingen patchen wanneer dat op aanvraag nodig is.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
