---
title: Upgraden naar 27.00
description: Upgraden van 26.00 naar 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
Deze documentatie is een handleiding voor het upgraden van webforJ-apps van 26.00 naar 27.00.
Hieronder staan de wijzigingen die nodig zijn om bestaande apps soepel te blijven uitvoeren.
Raadpleeg zoals altijd het [overzicht van GitHub-releases](https://github.com/webforj/webforj/releases) voor een uitgebreidere lijst met wijzigingen tussen releases.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Tekst- en HTML-inhoud {#text-and-html-content}

In eerdere versies werd een waarde die tussen `<html>` stond en aan `setText()` werd doorgegeven, behandeld als HTML. Dit gedrag is verouderd en wordt verwijderd in webforJ 27.00. De instelling `webforj.legacyHtmlInText` bepaalt het gedrag:

- `true` (standaard): een waarde tussen `<html>` wordt weergegeven als HTML-inhoud.
- `false`: dezelfde waarde wordt letterlijk weergegeven.

De wrapper `<html>` wordt in beide gevallen nooit weergegeven.

De eerste keer dat een waarde tussen `<html>` bij `setText()` terechtkomt, wordt er een waarschuwing gelogd met de naam van de component en de locatie van de aanroep, zodat de aanroep kan worden verplaatst naar `setHtml()`.

## Vereiste binding met `@Size` {#required-binding-using-size-annotation}

In versies ouder dan 27.00 wordt het gebonden component automatisch als vereist gemarkeerd wanneer de [Jakarta-annotatie](/docs/data-binding/validation/jakarta-validation) `@Size` wordt gebruikt.
Vanaf nu wordt een gebonden component alleen als vereist gemarkeerd door `@Size` als er een `min`-waarde is gedefinieerd.

```java
// no longer marks the field as required
@Size(max = 80, message = "Street must be shorter than 80 characters")
private String street;
```

```java
// marks the field as required
@Size(min = 2, max = 80, message = "Street must be between 2 and 80 characters")
private String street;
```
