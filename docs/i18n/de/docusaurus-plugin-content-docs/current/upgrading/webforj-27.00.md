---
title: Upgrade auf 27.00
description: Upgrade von 26.00 auf 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
Diese Dokumentation ist eine Anleitung zum Upgrade von webforJ-Apps von 26.00 auf 27.00.
Hier sind die Änderungen aufgeführt, die für bestehende Apps erforderlich sind, damit sie weiterhin reibungslos laufen.
Eine umfassendere Liste der Änderungen zwischen den Releases finden Sie wie immer in der [GitHub-Releaseübersicht](https://github.com/webforj/webforj/releases).

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Text- und HTML-Inhalte {#text-and-html-content}

In früheren Versionen wurde ein in `<html>` eingeschlossener und an `setText()` übergebener Wert als HTML behandelt. Dieses Verhalten ist veraltet und wurde in webforJ 27.00 entfernt. Die Einstellung `webforj.legacyHtmlInText` steuert dieses Verhalten:

- `true` (Standard): Ein in `<html>` eingeschlossener Wert gibt seinen Inhalt als HTML aus.
- `false`: Derselbe Wert wird wörtlich angezeigt.

Der `<html>`-Wrapper wird in keinem der beiden Fälle angezeigt.

Wenn ein in `<html>` eingeschlossener Wert zum ersten Mal `setText()` erreicht, wird eine Warnung protokolliert, die die Komponente und die Aufrufstelle nennt. So kann der Aufruf zu `setHtml()` geändert werden.

## Erforderliche Bindung mit `@Size` {#required-binding-using-size-annotation}

In Versionen vor 27.00 wurde die gebundene Komponente bei Verwendung der [Jakarta-Annotation](/docs/data-binding/validation/jakarta-validation) `@Size` automatisch als erforderlich markiert.
Ab jetzt markiert `@Size` eine gebundene Komponente nur dann als erforderlich, wenn ein `min`-Wert festgelegt ist.

```java
// markiert das Feld nicht mehr als erforderlich
@Size(max = 80, message = "Die Straße muss kürzer als 80 Zeichen sein")
private String street;
```

```java
// markiert das Feld als erforderlich
@Size(min = 2, max = 80, message = "Die Straße muss zwischen 2 und 80 Zeichen lang sein")
private String street;
```
