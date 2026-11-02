---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 716e267ebccf57080f5238335670d1b7
---
craftforJ bevat een volledige codering-agent die werkt binnen je **lopende app**. Het schrijft Java, compileert wat het heeft geschreven voordat je het ooit ziet, past de wijziging toe en gaat door met werken nadat je app opnieuw wordt opgestart. Alles wat het doet, gebeurt tegen de app die daadwerkelijk voor je draait, in plaats van tegen een gok uit je repository.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning AI kan nog steeds fouten maken
Werken met de lopende app en het compileren van zijn eigen output maakt de agent aanzienlijk nauwkeuriger dan degene die blind schrijft. Het kan nog steeds fout zijn. Controleer wat het heeft gedaan voordat je het accepteert.
:::

## Het schrijft Java {#it-writes-java}

De agent is niet beperkt tot de eigenschapswijzigingen die je handmatig kunt aanbrengen. Beschrijf een probleem en het schrijft de code ervoor, voegt methoden toe, wijzigt de logica en herschikt een weergave zoals de taak vereist.

Elke wijziging die het schrijft, is in behandeling in plaats van naar schijf geschreven. In behandeling zijnde aanpassingen gaan rechtstreeks naar een echte Java-compiler, en de agent leest de diagnostiek die terugkomt en verhelpt zijn eigen fouten voordat de wijziging ooit aan jou wordt aangeboden. Wat jouw beoordeling bereikt, is code die al compileert tegen je lopende app.

Volledige validatie vereist een JDK. Op een JRE valt craftforJ terug op het parseren van de code, markeert de wijziging als niet-verifieerbaar en instrueert de agent om dat aan te geven in plaats van het als gecheckt voor te stellen.

Het toepassen van een wijziging herstart je app. De agent wacht op de herstart, maakt opnieuw verbinding en pikt zijn plan op waar het was gebleven, zodat een taak die zich over verschillende wijzigingen en herstarts uitstrekt, wordt voltooid.

## Het werkt in stappen {#it-works-in-steps}

Je geeft de agent een doel, geen opdracht. Het plant, inspecteert wat het nodig heeft, handelt, controleert het resultaat en corrigeert zichzelf, terwijl het veel stappen in één beurt uitvoert zonder dat jij elke stap aanstuurt. Elke stap verschijnt in het transcript terwijl het gebeurt, en je kunt elke stap uitbreiden om precies te zien wat de agent heeft aangeroepen en wat er is teruggekomen.

## Wat het kan bereiken {#what-it-can-reach}

De agent heeft een grote set tools die alles dekt wat craftforJ weet over je app, inclusief:

- **Je componenten** - de live boom, de echte eigenschapswaarden, en de Java die elk component heeft gebouwd. Het kan eigenschappen wijzigen, componenten verwijderen en een component op de pagina markeren.
- **Je bron** - het lezen van elk bestand onder je projectroot, het in behandeling nemen van wijzigingen, het tonen van verschillen en deze toepassen.
- **Je routes** - de routeringstabel, de actieve route, navigeren overal naartoe, en het wijzigen van de toegangsregels die op een route zijn opgesteld.
- **Je thema en stijlen** - het lezen en instellen van ontwerp-tokens, het opslaan van een thema en het zoeken naar de beschikbare lettertypen en iconen.
- **De pagina zelf** - het injecteren van CSS en JavaScript op de live pagina, en het maken van een screenshot van een component om deze te bekijken.
- **De webforJ kennisbasis** - dezelfde documentatie, component-stijlen oppervlakte, en `--dwc-*` token tools die de [webforJ MCP server](/docs/ai-tooling/mcp) aan je editor biedt. Het is ingebouwd en altijd beschikbaar.

Omdat het dit allemaal bereikt via craftforJ, werkt het met dezelfde informatie die jij gebruikt. Het leest echte waarden, niet de waarden die je bron suggereert.

## Goedkeuringen {#approvals}

Je bepaalt vooraf hoeveel de agent zelfstandig mag doen:

- **Vraag voordat je actie onderneemt** - elke actie met een effect stopt voor jouw goedkeuring.
- **Pas wijzigingen automatisch toe** - de agent werkt vrij, maar vraagt nog steeds om goedkeuring voordat het iets verwijdert of een script uitvoert.
- **Voer autonoom uit** - de agent werkt zonder te stoppen.

Wanneer de agent vraagt, verschijnt de aanvraag inline in het transcript met de actie die het wil ondernemen, en je kunt het eenmalig of voor de rest van het gesprek toestaan.

![De assistent die vraagt voordat hij handelt, inline in het transcript](/img/craftforj/ai/approval-prompt.png#rounded-border)

Als je nieuw bent met de agent, begin dan met het laten vragen voor alles. Zodra je ziet hoe het werkt, verwijdert het toestaan van het toepassen van zijn eigen wijzigingen de meeste onderbrekingen terwijl de beslissingen die belangrijk zijn bij jou blijven.

## Werken met de app in een gesprek {#working-with-the-app-in-a-conversation}

De agent leest wat het nodig heeft wanneer het nodig is, in plaats van dat je het hele app vooraf aanbiedt, en craftforJ toont je wat aan het gesprek is gekoppeld. Je kunt het direct een component uit de boom geven, of er een kiezen van de pagina midden in een gesprek. Voor vragen over hoe iets eruitziet, kan de agent een screenshot van een component maken. Dit vereist een model dat afbeeldingen accepteert.

:::warning Screenshots bevatten alles wat op het scherm staat
Een screenshot bevat alle gegevens die je app op dat moment weergeeft. Overweeg dat voordat je een gehost model op een app met echte gegevens richt.
:::

## Een model configureren {#configuring-a-model}

craftforJ wordt zonder eigen model geleverd, zodat jij degene kunt kiezen die het uitvoert. Voeg een API-sleutel toe voor een van de ondersteunde providers, of wijs craftforJ op een lokaal draaiend model. Je sleutel wordt op de machine die je app uitvoert opgeslagen en de assistent houdt deze alleen in het geheugen zo lang de pagina is open, nooit in browseropslag. Het communiceert met de provider die je vanuit de browser hebt gekozen in plaats van via jouw server, en met niemand anders.

De modelkeuze toont wat één model onderscheidt van een ander, inclusief hoeveel van je app en gesprek tegelijkertijd past, wat een gesprek kost, en of het model afbeeldingen accepteert of redeneert voordat het antwoord geeft. Een model dat geen tools kan aanroepen, kan wel een gesprek voeren maar kan niets inspecteren of wijzigen.

![De modelkeuze die toont wat de beschikbare modellen onderscheidt](/img/craftforj/ai/model-picker.png#rounded-border)

Het lokaal draaien van een model houdt alles op jouw machine. Lokale modellen hebben vaak een klein contextvenster, dat snel wordt gevuld bij een gesprek over een echte app, dus geef het model zoveel context als jouw machine kan dragen.

## Gesprekken {#conversations}

Gesprekken worden per app bewaard en de agent kan terugkijken over eerdere gesprekken wanneer een vraag verwijst naar werk dat je eerder hebt gedaan. Wanneer een gesprek de context van het model overschrijdt, vat craftforJ de oudere berichten samen, zodat het werk doorgaat in plaats van te falen, en noteert dit in de chat.

Wanneer het werk de mogelijkheden van craftforJ overschrijdt, kun je het gesprek samenvatten en het overhandigen aan de assistent van je editor. Die assistent pakt het werk nauwkeuriger op met de [webforJ AI plugin](/docs/ai-tooling) geïnstalleerd.

## Zet het uit {#turning-it-off}

De [`ai.enabled`](/docs/craftforj/configuration#feature-flags) eigenschap verwijdert de assistent volledig uit craftforJ. De [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) eigenschap houdt de assistent, maar voorkomt dat deze zelf Java schrijft.
