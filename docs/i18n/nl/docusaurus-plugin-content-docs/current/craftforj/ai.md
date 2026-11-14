---
title: AI-assistent
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: e9d943043e52eae5368dfe4ba7795142
---
craftforJ bevat een volledige codeeragent die binnen je **actieve app** werkt. De agent schrijft vrijelijk Java, compileert wat hij heeft geschreven voordat jij het ooit te zien krijgt, past de wijziging toe en gaat verder met werken nadat je app opnieuw is opgestart. Alles wat de agent doet, doet hij met de app die daadwerkelijk voor je draait, en niet met een inschatting op basis van je repository.

<VideoPlayer video="craftforj/ai-conversation" width={1920} height={1000} title="AI conversation" />

:::warning AI kan nog steeds fouten maken
Doordat de agent met de actieve app werkt en zijn eigen uitvoer compileert, is hij aanzienlijk nauwkeuriger dan een agent die in het wilde weg schrijft. Toch kan hij fouten maken. Controleer wat hij heeft gedaan voordat je het behoudt.
:::

## De agent schrijft Java {#it-writes-java}

De agent is niet beperkt tot de wijzigingen in eigenschappen die je handmatig kunt aanbrengen. Beschrijf een probleem en de agent schrijft de code ervoor: hij voegt methoden toe, wijzigt logica en herstructureert een weergave als de taak daarom vraagt.

Elke wijziging die de agent schrijft, wordt klaargezet in plaats van naar schijf geschreven. Klaargezette wijzigingen worden rechtstreeks aan een echte Java-compiler voorgelegd. De agent leest de diagnostische meldingen die daaruit voortkomen en herstelt zijn eigen fouten voordat de wijziging ooit aan jou wordt aangeboden. De code die je ter beoordeling krijgt, compileert dus al met je actieve app.

Voor volledige validatie is een JDK vereist. Met een JRE valt craftforJ terug op het parsen van de code, markeert de wijziging als niet-geverifieerd en instrueert de agent om dit te vermelden in plaats van de wijziging als gecontroleerd voor te stellen.

Als je een wijziging toepast, wordt je app opnieuw opgestart. De agent wacht tot het opnieuw opstarten is voltooid, maakt opnieuw verbinding en gaat verder met zijn plan. Zo wordt een taak die meerdere wijzigingen en herstarts omvat, helemaal afgerond.

## De agent werkt in stappen {#it-works-in-steps}

Je geeft de agent een doel, geen opdracht. De agent maakt een plan, inspecteert wat nodig is, voert acties uit, controleert het resultaat en corrigeert zichzelf. Zo voert hij in één beurt meerdere stappen uit zonder dat jij elke stap hoeft aan te sturen. Elke stap verschijnt tijdens de uitvoering in het transcript. Je kunt elke stap uitvouwen om precies te zien wat de agent heeft aangeroepen en wat het resultaat was.

## Wat de agent kan benaderen {#what-it-can-reach}

De agent beschikt over een uitgebreide set tools voor alles wat craftforJ over je app weet, waaronder:

- **Je componenten** - de live componentenboom, de echte eigenschapswaarden en de Java-code waarmee elk component is opgebouwd. De agent kan eigenschappen wijzigen, componenten verwijderen en een component op de pagina markeren.
- **Je broncode** - elk bestand onder de hoofdmap van je project lezen, wijzigingen klaarzetten, verschillen tonen en wijzigingen toepassen.
- **Je routes** - de routeringstabel, de actieve route, naar elke route navigeren en de toegangsregels wijzigen die voor een route zijn opgegeven.
- **Je thema en stijlen** - ontwerptokens lezen en instellen, een thema opslaan en zoeken in de beschikbare lettertypen en pictogrammen.
- **De pagina zelf** - CSS en JavaScript injecteren in de actieve pagina en een screenshot van een component maken om te bekijken.
- **De kennisbank van webforJ** - dezelfde documentatie, componentstijlen en `--dwc-*`-tools voor tokens die de [webforJ MCP-server](/docs/ai-tooling/mcp) je editor biedt. Deze zijn ingebouwd en altijd beschikbaar.

Omdat de agent dit allemaal via craftforJ benadert, werkt hij met dezelfde informatie als jij. Hij leest echte waarden, niet de waarden die je broncode suggereert.

## Goedkeuringen {#approvals}

Je bepaalt vooraf hoeveel de agent zelfstandig mag doen:

- **Eerst om toestemming vragen** - elke actie met gevolgen wordt ter goedkeuring aan je voorgelegd.
- **Wijzigingen automatisch toepassen** - de agent werkt vrijuit, maar vraagt nog wel toestemming voordat hij iets verwijdert of een script uitvoert.
- **Autonoom werken** - de agent werkt zonder te stoppen.

Wanneer de agent om toestemming vraagt, verschijnt het verzoek inline in het transcript, samen met de actie die hij wil uitvoeren. Je kunt de actie één keer toestaan of voor de rest van het gesprek.

![De assistent vraagt inline in het transcript om toestemming voordat hij een actie uitvoert](/img/craftforj/ai/approval-prompt.png#rounded-border)

Ben je nieuw met de agent, laat hem dan in het begin overal toestemming voor vragen. Als je eenmaal hebt gezien hoe hij werkt, voorkom je de meeste onderbrekingen door hem zijn eigen wijzigingen te laten toepassen. De belangrijke beslissingen blijven dan wel bij jou.

## In een gesprek met de app werken {#working-with-the-app-in-a-conversation}

De agent leest informatie wanneer hij die nodig heeft, in plaats van vooraf je hele app te ontvangen. craftforJ laat je zien welke informatie aan het gesprek is toegevoegd. Je kunt een component rechtstreeks vanuit de boom aan de agent doorgeven of er midden in een gesprek een op de pagina selecteren. Als je vragen hebt over hoe iets eruitziet, kan de agent een screenshot van een component maken. Hiervoor is een model nodig dat afbeeldingen accepteert.

:::warning Screenshots bevatten alles wat op het scherm staat
Een screenshot bevat alle gegevens die je app op dat moment weergeeft. Houd hier rekening mee voordat je een gehost model gebruikt met een app die echte gegevens toont.
:::

## Een model configureren {#configuring-a-model}

craftforJ wordt niet met een eigen model geleverd, dus je kiest zelf welk model de agent aandrijft. Voeg een API-sleutel toe voor een van de ondersteunde providers of laat craftforJ verbinding maken met een lokaal model. Je sleutel wordt opgeslagen op de machine waarop je app draait. De assistent bewaart de sleutel alleen in het geheugen zolang de pagina is geopend, en nooit in de browseropslag. De assistent communiceert vanuit de browser met de provider die je hebt gekozen, en met niemand anders.

De modelkiezer toont waarin de modellen van elkaar verschillen, waaronder hoeveel van je app en gesprek tegelijk in het contextvenster passen, wat een gesprek kost en of het model afbeeldingen accepteert of eerst redeneert voordat het antwoord geeft. Een model dat geen tools kan aanroepen, kan wel een gesprek voeren, maar niets inspecteren of wijzigen.

![De modelkiezer met de verschillen tussen de beschikbare modellen](/img/craftforj/ai/model-picker.png#rounded-border)

Als je een model lokaal uitvoert, blijft alles op je machine. Lokale modellen gebruiken vaak standaard een klein contextvenster. Een gesprek over een echte app vult dat snel, dus geef het model zoveel context als je machine aankan.

## Gesprekken {#conversations}

Gesprekken worden per app bewaard. Als je vraag verwijst naar eerder werk, kan de agent eerdere gesprekken teruglezen. Wanneer een gesprek groter wordt dan het contextvenster van het model, vat craftforJ de oudere berichten samen zodat het werk doorgaat in plaats van vast te lopen. In de chat wordt vermeld dat dit is gebeurd.

Wanneer het werk de mogelijkheden van craftforJ overstijgt, kun je het gesprek samenvatten en doorgeven aan de assistent in je editor. Met de [webforJ AI-plug-in](/docs/ai-tooling) geïnstalleerd kan die assistent het werk nauwkeuriger voortzetten.

## Uitschakelen {#turning-it-off}

Met de eigenschap [`ai.enabled`](/docs/craftforj/configuration#feature-flags) verwijder je de assistent volledig uit craftforJ. Met de eigenschap [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) behoud je de assistent, maar voorkom je dat hij zelf Java schrijft.
