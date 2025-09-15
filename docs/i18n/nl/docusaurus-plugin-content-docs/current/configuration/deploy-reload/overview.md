---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: 2e8bf7fded04e11ec6bab6d8a7c1c2b5
---
Efficiënte ontwikkelingsworkflows zijn afhankelijk van tools die codewijzigingen detecteren en automatisch de app in realtime bijwerken. Continue implementatie en dynamisch opnieuw laden werken samen om het ontwikkelingsproces te vereenvoudigen door handmatige stappen te verminderen, zodat je je wijzigingen snel kunt bekijken zonder de server handmatig opnieuw te starten.

## Herimplementatie {#redeployment}

Herimplementatie in Java-ontwikkeling verwijst naar het automatisch detecteren en implementeren van codewijzigingen, zodat updates in de app worden weergegeven zonder een handmatige serverherstart. Dit proces omvat doorgaans het op de vlucht bijwerken van Java-klassen en webbronnen.

In een webforJ-app betekent dit het regenereren van het WAR-bestand telkens wanneer er wijzigingen in de code worden aangebracht.

Wijzigingen aan Java-klassen en bronnen op het classpath worden doorgaans door de IDE gecontroleerd. Wanneer een Java-klasse wordt gewijzigd en het bestand wordt opgeslagen, hetzij automatisch door de IDE of handmatig door de ontwikkelaar, worden deze tools geactiveerd om de bijgewerkte klassebestanden in de doeldirectory te compiler en deze wijzigingen toe te passen.

Tools en instellingen die het opnieuw laden van de browser automatiseren of optimaliseren, kunnen worden toegevoegd voor een meer naadloze ervaring.

## Live herladen {#live-reload}

Live herladen zorgt ervoor dat zodra wijzigingen zijn geïmplementeerd, de browser deze updates in realtime weergeeft zonder een handmatige browserverversing.

In een webforJ-app kan live herladen automatisch de weergave vernieuwen, waarbij componenten opnieuw worden gerenderd om de nieuwste toestand van de app weer te geven, of zelfs wijzigingen doorvoeren naargelang nodig op verzoek.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
