---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Anwendungen von 26.00 auf 27.00. 
Hier sind die erforderlichen Änderungen, damit bestehende Apps weiterhin reibungslos funktionieren. 
Wie immer finden Sie in der [GitHub Release-Übersicht](https://github.com/webforj/webforj/releases) eine umfassendere Liste der Änderungen zwischen den Versionen.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Text- und HTML-Inhalt {#text-and-html-content}

Frühere Versionen behandelten einen in `<html>` eingeschlossenen Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wurde in webforJ 27.00 entfernt. Die Einstellung `webforj.legacyHtmlInText` steuert dies:

- `true` (Standard): Ein in `<html>` eingeschlossener Wert rendert seinen Inhalt als HTML.
- `false`: Der gleiche Wert wird wortwörtlich angezeigt.

Der `<html>`-Wrapper wird in beiden Fällen niemals angezeigt.

Beim ersten Mal, wenn ein in `<html>` eingeschlossener Wert `setText()` erreicht, wird eine Warnung protokolliert, die den Namen der Komponente und den Aufrufstandort angibt, sodass der Aufruf nach `setHtml()` verschoben werden kann.
