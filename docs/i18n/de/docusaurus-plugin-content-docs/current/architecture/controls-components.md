---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
Das webforJ-Framework wurde entwickelt, um eine Java-API rund um die BBj-Sprache DWC bereitzustellen und bietet eine robuste Architektur für den Bau und die Verwaltung von Komponenten.

## Zuordnung von BBj-Steuerelementen zu webforJ-Komponenten {#mapping-bbj-controls-to-webforj-components}
Eines der grundlegenden Prinzipien von webforJ ist die Bindung von BBj-Steuerelementen an webforJ-Komponenten. In dieser Architektur hat jede webforJ-Komponente, die mit dem Produkt geliefert wird, eine Eins-zu-eins-Zuordnung zu einem zugrunde liegenden BBj-Steuerelement. Diese Zuordnung stellt sicher, dass Java-Komponenten das Verhalten und die Eigenschaften ihrer BBj-Pendants nahtlos widerspiegeln.

Diese enge Übereinstimmung zwischen webforJ-Komponenten und BBj-Steuerelementen vereinfacht die Entwicklung und ermöglicht es Java-Entwicklern, mit vertrauten Konzepten zu arbeiten, wenn sie webbasierte Anwendungen erstellen, ohne dass dazu BBj-Code geschrieben werden muss.

## Die `DwcComponent`-Basis-Klasse {#the-dwccomponent-base-class}
Im Herzen der Komponentenarchitektur von webforJ liegt die Basis-Klasse DWCComponent. Alle webforJ-Komponenten erben von dieser Klasse. Diese Vererbung gewährt jeder webforJ-Komponente Zugriff auf ihr zugrunde liegendes BBj-Steuerelement und stellt eine direkte Verbindung zwischen der Java-Komponente und dem BBj-Steuerelement, das sie repräsentiert, her.

Es ist jedoch wichtig zu beachten, dass Entwickler daran gehindert sind, die DWCComponent-Klasse weiter zu erweitern. Der Versuch, dies zu tun, führt zu einer Laufzeitausnahme, die solche Erweiterungen verbietet. Diese Einschränkung ist vorhanden, um die Integrität des zugrunde liegenden BBj-Steuerelements zu wahren und sicherzustellen, dass Entwickler es nicht unbeabsichtigt in einer Weise manipulieren, die zu unbeabsichtigten Konsequenzen führen könnte.

### Endgültige Klassen und Erweiterungseinschränkungen {#final-classes-and-extension-restrictions}
In webforJ sind die meisten Komponentenklassen, mit Ausnahme der integrierten HTML-Elemente und aller Klassen, die diese erweitern, als `final` deklariert. Dies bedeutet, dass sie nicht zur Erweiterung oder Unterklassenbildung verfügbar sind. Diese Designentscheidung ist absichtlich und dient mehreren Zwecken:

1. **Kontrolle über das zugrunde liegende BBj-Steuerelement**: Wie bereits erwähnt, würde die Erweiterung von webforJ-Komponentenklassen den Entwicklern die Kontrolle über das zugrunde liegende BBj-Steuerelement gewähren. Um die Konsistenz und Vorhersagbarkeit des Verhaltens der Komponenten zu wahren, wird dieses Kontrollniveau eingeschränkt.

2. **Verhinderung unbeabsichtigter Änderungen**: Das Festlegen der Komponentenklassen als `final` verhindert unbeabsichtigte Änderungen an Kernkomponenten, wodurch das Risiko verringert wird, unerwartete Verhaltensweisen oder Schwachstellen einzuführen.

3. **Förderung der Verwendung von Kompositen**: Um die Funktionalität von Komponenten zu erweitern, fördert das webforJ-Framework die Verwendung eines kompositen Ansatzes. Komposite Komponenten sind Java-Klassen, die andere webforJ-Komponenten oder standardisierte HTML-Elemente enthalten. Während traditionelle Vererbung nicht empfohlen wird, bieten komposite Komponenten eine Möglichkeit, neue, maßgeschneiderte Komponenten zu erstellen, die vorhandene kapseln.

## Komposite Komponenten: Erweiterung durch Komposition {#composite-components-extending-through-composition}
Im webforJ-Framework spielt das Konzept der kompositen Komponenten eine entscheidende Rolle bei der Erweiterung der Funktionalität von Komponenten. Komposite Komponenten sind Java-Klassen, die nicht durch das final-Schlüsselwort eingeschränkt sind, sodass Entwickler neue Komponenten erstellen können, die das Verhalten einer einzelnen Komponente erweitern oder mehrere Komponenten zu einer einzigen kombinieren, indem sie vorhandene Komponenten zusammensetzen. Klassen, die dieses Verhalten erleichtern, wurden für die Entwicklung zur Verfügung gestellt. Siehe die Abschnitte `Composite` und `ElementComposite`, um zu erfahren, wie man komposite Komponenten korrekt erstellt.

Dieser Ansatz fördert einen modulären und flexiblen Entwicklungsstil, der es Entwicklern ermöglicht, maßgeschneiderte Komponenten zu erstellen, die spezifischen Anforderungen entsprechen.
