---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
Das webforJ-Framework ist darauf ausgelegt, eine Java-API rund um die BBj-Sprache DWC bereitzustellen und bietet eine robuste Architektur für den Aufbau und die Verwaltung von Komponenten.

## Zuordnung von BBj-Steuerelementen zu webforJ-Komponenten {#mapping-bbj-controls-to-webforj-components}
Eines der grundlegenden Prinzipien von webforJ ist die Bindung von BBj-Steuerelementen an webforJ-Komponenten. In dieser Architektur hat jede mit dem Produkt ausgelieferte webforJ-Komponente eine Eins-zu-eins-Zuordnung zu einem zugrunde liegenden BBj-Steuerelement. Diese Zuordnung stellt sicher, dass Java-Komponenten das Verhalten und die Eigenschaften ihrer BBj-Pendants nahtlos widerspiegeln.

Diese enge Übereinstimmung zwischen webforJ-Komponenten und BBj-Steuerelementen vereinfacht die Entwicklung und ermöglicht es Java-Entwicklern, mit vertrauten Konzepten zu arbeiten, wenn sie webbasierte Anwendungen erstellen, ohne dass sie BBj-Code schreiben müssen.

## Die Basisklasse `DwcComponent` {#the-dwccomponent-base-class}
Im Herzen der Komponentenarchitektur von webforJ liegt die Basisklasse DWCComponent. Alle webforJ-Komponenten erben von dieser Klasse. Diese Vererbung gewährt jeder webforJ-Komponente Zugriff auf ihr zugrunde liegendes BBj-Steuerelement und bietet eine direkte Verbindung zwischen der Java-Komponente und dem BBj-Steuerelement, das sie repräsentiert.

Es ist jedoch wichtig zu beachten, dass Entwickler daran gehindert sind, die Klasse DWCComponent weiter zu erweitern. Ein Versuch, dies zu tun, führt zu einer Laufzeitausnahme, die solche Erweiterungen verbietet. Diese Einschränkung dient dazu, die Integrität des zugrunde liegenden BBj-Steuerelements aufrechtzuerhalten und sicherzustellen, dass Entwickler es nicht versehentlich auf eine Weise manipulieren, die zu unbeabsichtigten Konsequenzen führen könnte.

### Endgültige Klassen und Erweiterungseinschränkungen {#final-classes-and-extension-restrictions}
In webforJ sind die meisten Komponentenklassen, mit Ausnahme der integrierten HTML-Elemente und aller Klassen, die von diesen abgeleitet sind, als `final` deklariert. Das bedeutet, dass sie nicht für die Erweiterung oder Vererbung zur Verfügung stehen. Diese Designentscheidung ist absichtlich und verfolgt mehrere Zwecke:

1. **Kontrolle über das zugrunde liegende BBj-Steuerelement**: Wie bereits erwähnt, würde die Erweiterung von webforJ-Komponentenklassen den Entwicklern Kontrolle über das zugrunde liegende BBj-Steuerelement gewähren. Um die Konsistenz und Vorhersehbarkeit des Verhaltens von Komponenten aufrechtzuerhalten, ist dieses Maß an Kontrolle eingeschränkt.

2. **Verhinderung unbeabsichtigter Änderungen**: Durch die Deklaration der Komponentenklassen als `final` wird unabsichtliche Modifikation der Kernkomponenten verhindert, wodurch das Risiko verringert wird, unerwartete Verhaltensweisen oder Sicherheitsanfälligkeiten einzuführen.

3. **Förderung der Verwendung von Kompositen**: Um die Funktionalität der Komponenten zu erweitern, ermutigt das webforJ-Framework die Entwickler, einen komposite Ansatz zu verwenden. Komposite Komponenten sind Java-Klassen, die andere webforJ-Komponenten oder standardmäßige HTML-Elemente enthalten. Während traditionelle Vererbung abgelehnt wird, bieten komposite Komponenten eine Möglichkeit, neue, angepasste Komponenten zu erstellen, die bestehende kapseln.

## Komposite Komponenten: Erweiterung durch Komposition {#composite-components-extending-through-composition}
Im webforJ-Framework spielt das Konzept der kompositen Komponenten eine entscheidende Rolle bei der Erweiterung der Funktionalität von Komponenten. Komposite Komponenten sind Java-Klassen, die nicht durch das Schlüsselwort final eingeschränkt sind, was es Entwicklern ermöglicht, neue Komponenten zu erstellen, die das Verhalten einer einzelnen Komponente erweitern oder mehrere Komponenten zu einer einzigen kombinieren, indem bestehende Komponenten kombiniert werden. Klassen, die dieses Verhalten unterstützen, wurden für die Verwendung durch Entwickler erstellt. Siehe die Abschnitte `Composite` und `ElementComposite`, um zu erfahren, wie man komposite Komponenten korrekt erstellt.

Dieser Ansatz fördert einen moduläreren und flexibleren Entwicklungsstil, der es Entwicklern ermöglicht, maßgeschneiderte Komponenten zu erstellen, die spezifischen Anforderungen entsprechen.
