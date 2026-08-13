---
sidebar_position: 10
title: BBj Controls and webforJ Components
description: >-
  See how webforJ components map one-to-one to BBj controls through the
  DwcComponent base class and why composition replaces inheritance.
_i18n_hash: 83f48323774737067ddd9a6bebb0373d
---
Das webforJ-Framework ist darauf ausgelegt, eine Java-API für die DWC-Sprache von BBj bereitzustellen und bietet eine robuste Architektur zum Erstellen und Verwalten von Komponenten.

## Zuordnung von BBj-Steuerelementen zu webforJ-Komponenten {#mapping-bbj-controls-to-webforj-components}
Eines der grundlegenden Prinzipien von webforJ ist die Bindung von BBj-Steuerelementen an webforJ-Komponenten. In dieser Architektur hat jede webforJ-Komponente, die mit dem Produkt geliefert wird, eine Eins-zu-eins-Zuordnung zu einem zugrunde liegenden BBj-Steuerelement. Diese Zuordnung stellt sicher, dass Java-Komponenten das Verhalten und die Eigenschaften ihrer BBj-Pendants nahtlos widerspiegeln.

Diese enge Entsprechung zwischen webforJ-Komponenten und BBj-Steuerelementen vereinfacht die Entwicklung und ermöglicht es Java-Entwicklern, mit vertrauten Konzepten zu arbeiten, wenn sie webbasierte Anwendungen erstellen, ohne dass sie Code in BBj schreiben müssen.

## Die `DwcComponent`-Basis-Klasse {#the-dwccomponent-base-class}
Im Kern der Komponentenarchitektur von webforJ liegt die DWCComponent-Basis-Klasse. Alle webforJ-Komponenten erben von dieser Klasse. Diese Vererbung gewährt jeder webforJ-Komponente Zugriff auf ihr zugrunde liegendes BBj-Steuerelement und bietet eine direkte Verbindung zwischen der Java-Komponente und dem BBj-Steuerelement, das sie repräsentiert.

Es ist jedoch wichtig zu beachten, dass Entwicklern das Erweitern der DWCComponent-Klasse untersagt ist. Der Versuch, dies zu tun, führt zu einer Laufzeitausnahme, die solche Erweiterungen nicht zulässt. Diese Einschränkung dient dazu, die Integrität des zugrunde liegenden BBj-Steuerelements zu wahren und sicherzustellen, dass Entwickler es nicht unbeabsichtigt auf eine Weise manipulieren, die zu unbeabsichtigten Konsequenzen führen könnte.

### Endgültige Klassen und Erweiterungseinschränkungen {#final-classes-and-extension-restrictions}
In webforJ sind die meisten Komponentenklassen, mit Ausnahme der integrierten HTML-Elemente und aller Klassen, die von diesen abgeleitet sind, als `final` deklariert. Dies bedeutet, dass sie nicht zur Erweiterung oder Unterklassenbildung verfügbar sind. Diese Designentscheidung ist absichtlich und dient mehreren Zwecken:

1. **Kontrolle über das zugrunde liegende BBj-Steuerelement**: Wie bereits erwähnt, würde das Erweitern von webforJ-Komponentenklassen Entwicklern Kontrolle über das zugrunde liegende BBj-Steuerelement geben. Um die Konsistenz und Vorhersehbarkeit des Verhaltens von Komponenten zu wahren, ist dieses Maß an Kontrolle eingeschränkt.

2. **Verhinderung unbeabsichtigter Modifikationen**: Die Deklaration der Komponentenklassen als `final` verhindert unbeabsichtigte Modifikationen an Kernkomponenten, wodurch das Risiko verringert wird, unerwartete Verhaltensweisen oder Schwachstellen einzuführen.

3. **Förderung der Verwendung von Kompositen**: Um die Funktionalität von Komponenten zu erweitern, ermutigt das webforJ-Framework die Entwickler, einen kompositen Ansatz zu verwenden. Komposite Komponenten sind Java-Klassen, die andere webforJ-Komponenten oder Standard-HTML-Elemente enthalten. Während traditionelle Vererbung nicht empfohlen wird, bieten Komposite Komponenten eine Möglichkeit, neue, maßgeschneiderte Komponenten zu erstellen, die bestehende Komponenten kapseln.

## Komposite Komponenten: Erweiterung durch Komposition {#composite-components-extending-through-composition}
Im webforJ-Framework spielt das Konzept der kompositen Komponenten eine entscheidende Rolle bei der Erweiterung der Funktionalität von Komponenten. Komposite Komponenten sind Java-Klassen, die nicht durch das Schlüsselwort final eingeschränkt sind, sodass Entwickler neue Komponenten erstellen können, die das Verhalten einer einzelnen Komponente erweitern oder mehrere Komponenten zu einer einzigen zusammenfassen, indem sie bestehende Komponenten kombinieren. Klassen, die dieses Verhalten ermöglichen, wurden zur Verwendung durch Entwickler erstellt. Siehe die Abschnitte `Composite` und `ElementComposite`, um zu erfahren, wie man komposite Komponenten richtig erstellt.

Dieser Ansatz fördert einen modulareren und flexibleren Entwicklungsstil und ermöglicht es Entwicklern, maßgeschneiderte Komponenten zu erstellen, die spezifische Anforderungen erfüllen.
