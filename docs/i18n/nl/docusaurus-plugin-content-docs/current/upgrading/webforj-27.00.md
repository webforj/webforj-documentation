---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
Deze documentatie dient als een gids voor het upgraden van webforJ-apps van 26.00 naar 27.00. Hier zijn de wijzigingen die nodig zijn voor bestaande apps om soepel te blijven draaien. Zoals altijd, zie de [GitHub release-overzicht](https://github.com/webforj/webforj/releases) voor een completer overzicht van de wijzigingen tussen releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Tekst- en HTML-inhoud {#text-and-html-content}

Eerdere versies behandelden een waarde die in `<html>` is gewikkeld en naar `setText()` is doorgegeven als HTML. Dit gedrag is verouderd en is verwijderd in webforJ 27.00. De instelling `webforj.legacyHtmlInText` bepaalt het gedrag:

- `true` (standaard): een waarde die in `<html>` is gewikkeld, toont de inhoud als HTML.
- `false`: dezelfde waarde wordt letterlijk weergegeven.

De `<html>`-wrapper wordt in beide gevallen nooit weergegeven.

De eerste keer dat een waarde die in `<html>` is gewikkeld, `setText()` bereikt, wordt er een waarschuwing vastgelegd die de component en de aanroeplocatie vermeldt, zodat de aanroep kan worden verplaatst naar `setHtml()`.
