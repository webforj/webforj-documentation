---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 863d36cce987eedd9b580968afadcc18
---
craftforJ omvat een volledige code-agent die werkt binnen je **lopende app**. Hij schrijft Java vrijelijk, compileert wat hij heeft geschreven voordat je het ooit ziet, past de wijziging toe en gaat door met werken nadat je app opnieuw is opgestart. Alles wat hij doet, doet hij tegen de app die daadwerkelijk voor je draait, in plaats van tegen een gok gebaseerd op je repository.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4" type="video/mp4" />
  </video>
</div>

:::warning AI kan nog steeds fouten maken
Werken tegen de lopende app en het compileren van zijn eigen output maakt de agent aanzienlijk nauwkeuriger dan eentje die blind schrijft. Hij kan nog steeds fout zijn. Beoordeel wat hij heeft gedaan voordat je het behoudt.
:::

## Hij schrijft Java {#it-writes-java}

De agent is niet beperkt tot de eigenschapswijzigingen die je met de hand kunt aanbrengen. Beschrijf een probleem en hij schrijft de code ervoor, voegt methoden toe, verandert de logica en herstructeert een weergave zoals de taak vereist.

Elke wijziging die hij schrijft, is in de wachtrij in plaats van direct op de schijf geschreven. Gewijzigde bewerkingen gaan rechtstreeks naar een echte Java-compiler, en de agent leest de diagnostiek die terugkomt en herstelt zijn eigen fouten voordat de wijziging ooit aan jou wordt aangeboden. Wat je te zien krijgt is code die al compileert tegen je lopende app.

Volledige validatie vereist een JDK. Op een JRE valt craftforJ terug op het parseren van de code, markeert de bewerking als ongeverifieerd en instrueert de agent om dat te zeggen in plaats van het als gecontroleerd voor te stellen.

Het toepassen van een wijziging start je app opnieuw op. De agent wacht op de herstart, maakt opnieuw verbinding en pakt zijn plan weer op waar hij was gebleven, zodat een taak die zich over verschillende bewerkingen en herstarts uitstrekt, voltooid kan worden.

## Hij werkt in stappen {#it-works-in-steps}

Je geeft de agent een doel, geen commando. Hij plant, inspecteert wat hij nodig heeft, handelt, controleert het resultaat en corrigeert zichzelf, waarbij hij veel stappen in één beurt uitvoert zonder dat jij elke stap aanstuurt. Elke stap verschijnt in de transcriptie terwijl het gebeurt, en je kunt een van hen uitbreiden om precies te zien wat de agent heeft opgeroepen en wat er terugkwam.

## Wat hij kan bereiken {#what-it-can-reach}

De agent heeft een grote set hulpmiddelen die alles dekt wat craftforJ weet over jouw app, inclusief:

- **Je componenten** - de live boom, de werkelijke eigenschapswaarden en de Java die elke component heeft gebouwd. Hij kan eigenschappen wijzigen, componenten verwijderen en een component in de pagina markeren.
- **Je bron** - het lezen van elk bestand onder jouw projectroot, het in de wachtrij plaatsen van wijzigingen, het laten zien van verschillen en het toepassen ervan.
- **Je routes** - de routeringstabel, de actieve route, navigeren naar elke plaats en het wijzigen van de toegangsregels die op een route zijn verklaard.
- **Je thema en stijlen** - het lezen en instellen van ontwerp tokens, het opslaan van een thema en het zoeken naar beschikbare lettertypen en pictogrammen.
- **De pagina zelf** - CSS en JavaScript injecteren op de live pagina, en het maken van een screenshot van een component om deze te bekijken.
- **De webforJ kennisbank** - dezelfde documentatie, componentstijloppervlak en `--dwc-*` token tools die de [webforJ MCP-server](/docs/ai-tooling/mcp) jouw editor biedt. Het is ingebouwde en altijd beschikbaar.

Omdat het al deze informatie via craftforJ bereikt, werkt het met dezelfde informatie als jij. Het leest echte waarden, niet de waarden die jouw bron suggereert.

## Goedkeuringen {#approvals}

Je beslist vooraf hoeveel de agent op zichzelf mag doen:

- **Vraag om actie** - elke actie met een effect stopt voor jouw goedkeuring.
- **Pas wijzigingen automatisch toe** - de agent werkt vrij, maar vraagt nog steeds voordat hij iets verwijdert of een script uitvoert.
- **Werk autonom** - de agent werkt zonder te stoppen.

Wanneer de agent vraagt, verschijnt de aanvraag inline in de transcriptie met de actie die hij wil ondernemen, en je kunt het een keer of voor de rest van het gesprek toestaan.

![De assistent die vraagt voordat hij handelt, inline in de transcriptie](/img/craftforj/ai/approval-prompt.png#rounded-border)

Als je nieuw bent met de agent, begin dan met het vragen om alles. Zodra je het werk van de agent hebt bekeken, maakt het toestaan dat hij zijn eigen wijzigingen toepast de meeste onderbrekingen weg, terwijl de beslissingen die belangrijk zijn bij jou blijven.

## Werken met de app in een gesprek {#working-with-the-app-in-a-conversation}

De agent leest wat hij nodig heeft terwijl hij het nodig heeft in plaats van dat je hem je hele app van tevoren overhandigt, en craftforJ toont je wat is gekoppeld aan het gesprek. Je kunt hem een component rechtstreeks vanuit de boom geven, of er een kiezen van de pagina midden in een gesprek. Voor vragen over hoe iets eruitziet, kan de agent een screenshot van een component maken. Dit vereist een model dat afbeeldingen accepteert.

:::warning Screenshots bevatten alles wat op het scherm staat
Een screenshot brengt alle gegevens over die je app op dat moment weergeeft. Overweeg dat voordat je een gehost model op een app die tegen echte gegevens draait, richt.
:::

## Een model configureren {#configuring-a-model}

craftforJ levert geen model van zichzelf, dus je kiest degene die het uitvoert. Voeg een API-sleutel toe voor een van de ondersteunde aanbieders, of wijs craftforJ aan op een model dat lokaal draait. Je sleutel wordt opgeslagen op de machine die je app draait, en de assistent houdt deze alleen in het geheugen zolang de pagina open is, nooit in de browseropslag. Hij schakelt rechtstreeks van de browser met de provider waarmee je hebt gekozen, en met niemand anders.

De modelkiezer toont wat een model van een ander onderscheidt, inclusief hoeveel van je app en gesprek tegelijkertijd past, wat een gesprek kost, en of het model afbeeldingen accepteert of redeneert voordat het antwoord geeft. Een model dat geen tools kan aanroepen, kan een gesprek voeren maar kan niets inspecteren of veranderen.

![De modelkiezer die laat zien wat de beschikbare modellen onderscheidt](/img/craftforj/ai/model-picker.png#rounded-border)

Een model dat lokaal draait, houdt alles op jouw machine. Lokale modellen hebben vaak standaard een klein contextvenster, dat snel wordt gevuld door een gesprek over een echte app, dus geef het model zoveel context als je machine kan dragen.

## Gesprekken {#conversations}

Gesprekken worden per app bewaard, en de agent kan eerdere gesprekken terugzien wanneer een vraag verwijst naar werk dat je eerder hebt gedaan. Wanneer een gesprek de context van het model te boven gaat, vat craftforJ de oudere berichten samen zodat het werk doorgaat in plaats van te falen, en merkt dat in de chat op dat het dat heeft gedaan.

Wanneer het werk te groot wordt voor craftforJ, kun je het gesprek samenvatten en het aan de assistent van jouw editor overhandigen. Die assistent pikt het werk nauwkeuriger op met de [webforJ AI-plugin](/docs/ai-tooling) geïnstalleerd.

## Uitzetten {#turning-it-off}

De [`ai.enabled`](/docs/craftforj/configuration#feature-flags) eigenschap verwijdert de assistent volledig uit craftforJ. De [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) eigenschap houdt de assistent maar stopt hem met het zelf schrijven van Java.
