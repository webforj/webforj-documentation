---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
Efficiënte ontwikkelwerkstromen vertrouwen op tools die codewijzigingen detecteren en de app automatisch in realtime bijwerken. Continue deployment en dynamische herlaadfuncties werken samen om het ontwikkelproces te vereenvoudigen door handmatige stappen te verminderen, zodat je je wijzigingen snel kunt zien zonder de server handmatig opnieuw te starten.

## Herdeployement {#redeployment}

Herdeployement in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates in de app worden weerspiegeld zonder een handmatige serverherstart. Dit proces houdt meestal in dat Java-klassen en webbronnen on-the-fly worden bijgewerkt.

In een webforJ-app betekent dit dat het WAR-bestand wordt gegenereerd telkens wanneer er wijzigingen aan de code worden aangebracht.

Wijzigingen in Java-klassen en bronnen op het classpath worden meestal bewaakt door de IDE. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, worden deze tools geactiveerd om de bijgewerkte klassebestanden in de doelmap te compileren en op te slaan om deze wijzigingen toe te passen.

Tools en instellingen die het opnieuw laden van de browser automatiseren of optimaliseren kunnen worden toegevoegd voor een soepelere ervaring.

## Live herladen {#live-reload}

Live herladen zorgt ervoor dat zodra wijzigingen zijn doorgevoerd, de browser deze updates in realtime weergeeft zonder een handmatige verversing van de browser. 

In een webforJ-app kan live herladen automatisch de weergave vernieuwen, componenten opnieuw weergeven om de nieuwste staat van de app weer te geven, of zelfs wijzigingen aanbrengen wanneer dat nodig is.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
