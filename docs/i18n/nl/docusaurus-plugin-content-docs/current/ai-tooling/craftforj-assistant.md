---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
De craftforJ-assistent is een code-agent die werkt binnen je **lopende app**. Hij schrijft Java vrijelijk, compiles wat hij heeft geschreven voordat je het ooit ziet, past de wijziging toe en blijft werken nadat je app opnieuw is opgestart. Het wordt geleverd met webforJ als onderdeel van [craftforJ](/docs/craftforj), de ontwikkelomgeving die je de componentboom, routes, live eigenschappen en thematisering van een app biedt terwijl deze draait.

## Hoe de twee zich verhouden {#how-the-two-compare}

| | [webforJ AI-plugin](/docs/ai-tooling) | craftforJ-assistent |
|---|---|---|
| **Leeft in** | Je editor | De lopende app |
| **Leest** | Je bronbestanden | Je app, live, met zijn echte waarden |
| **Doet** | Schrijft code | Schrijft code, en inspecteert, verandert, navigeert en thematiseert de lopende app |
| **Controleert door** | Je volgende build | Elke bewerking te compileren voordat je het ziet, en dan het resultaat draaiend aan je te tonen |
| **Geschikt voor** | Iets helemaal nieuw opbouwen | Begrijpen, repareren, bouwen en prototype maken tegen de app voor je |

De twee zijn complementair en kunnen werk aan elkaar doorgeven. Zodra het werk de limieten van craftforJ overschrijdt, kun je een [craftforJ-gesprek overdragen](/docs/craftforj/ai#conversations) aan je editor.

## Wat het kan doen {#what-it-can-do}

Je geeft de agent een doel in plaats van een commando. Hij plant, inspecteert wat nodig is, handelt, controleert het resultaat, en corrigeert zichzelf over meerdere stappen in één beurt.

Hij schrijft vrijelijk Java, dus hij is niet beperkt tot de eigenschapswijzigingen die je met de hand kunt aanbrengen. Elke bewerking wordt voorbereid in plaats van op de schijf geschreven, verzonden naar een echte Java-compiler en gecorrigeerd door de agent op basis van de diagnostiek die terugkomt, zodat wat je ter beoordeling krijgt al compileert tegen je lopende app. Toepassen ervan herstart de app en de agent pakt zijn plan weer op zodra deze terug is.

Daarnaast heeft hij toegang tot alles wat craftforJ weet: de live componentboom en echte eigenschapswaarden, je Java-bron, de routeringstabel en route-toegangsregels, het thema en de stylesheet, de pagina zelf voor CSS en scripts, schermafbeeldingen van een component en de webforJ-kennisbank en `--dwc-*` token-tools die ingebouwd zijn. Zie [AI Assistant](/docs/craftforj/ai) voor de details.

## Een model configureren {#configuring-a-model}

craftforJ wordt zonder een eigen model geleverd, dus je kiest degene die het uitvoert. Voeg een API-sleutel toe voor een van de ondersteunde aanbieders, of wijs craftforJ aan op een lokaal draaiend model met Ollama. Je sleutel wordt opgeslagen op de machine die je app draait en enkel in de browser vastgehouden terwijl de pagina open is, en de assistent communiceert met je provider vanuit de browser in plaats van via je server. Zie [Een model configureren](/docs/craftforj/ai#configuring-a-model).

:::warning AI kan nog steeds fouten maken
Werken tegen de lopende app en het compileren van zijn eigen output maakt de agent aanzienlijk nauwkeuriger dan een die blind schrijft. Het kan echter nog steeds fout zijn. Bekijk wat het heeft gedaan voordat je het behoudt.
:::

## Aan de slag {#getting-started}

craftforJ is uitgeschakeld totdat je het inschakelt, en het draait alleen in ontwikkeling:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Open craftforJ met <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> en schakel over naar het tabblad AI Assistant. Voor de volledige setup, zie [Aan de slag](/docs/craftforj/getting-started).
