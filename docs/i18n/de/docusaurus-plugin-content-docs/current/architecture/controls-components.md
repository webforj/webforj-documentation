---
sidebar_position: 10
title: Komponentenarchitektur
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ stellt Ihnen eine Java-API für eine Bibliothek mit Webkomponenten bereit und verwaltet jede davon serverseitig. Auf dieser Seite wird erklärt, wie Komponenten aufgebaut sind und wie Sie sie erweitern können.

## Aufbau von Komponenten {#how-components-are-structured}
Jede mit dem Produkt ausgelieferte webforJ-Komponente ist eine Java-Klasse, der genau eine clientseitige Webkomponente aus der DWC-Komponentenbibliothek zugeordnet ist. Die Java-Klasse verwaltet den Zustand und die API der Komponente auf dem Server, und das Framework hält die Webkomponente im Browser mit ihr synchron.

Sie arbeiten ausschließlich mit der Java-Klasse. Eigenschaften, Ereignisse und das Rendering im Browser werden vom Framework übernommen, sodass Sie Web-Apps vollständig in Java erstellen können.

## Die Basisklasse `DwcComponent` {#the-dwccomponent-base-class}
Das Herzstück der Komponentenarchitektur von webforJ ist die Basisklasse DWCComponent. Alle webforJ-Komponenten erben von dieser Klasse. Sie verwaltet die Verbindung zwischen der Java-Komponente und ihrem clientseitigen Gegenstück, einschließlich Erstellung, Aktualisierung von Eigenschaften und Ereignissen.

Es ist jedoch wichtig zu beachten, dass Entwickler die DWCComponent-Klasse nicht weiter erweitern dürfen. Ein entsprechender Versuch führt zu einer Laufzeitausnahme, die solche Erweiterungen verhindert. Diese Einschränkung dient dazu, die Integrität dieser Verbindung zu wahren und sicherzustellen, dass Entwickler sie nicht versehentlich auf eine Weise manipulieren, die unbeabsichtigte Folgen haben könnte.

### Finale Klassen und Einschränkungen beim Erweitern {#final-classes-and-extension-restrictions}
In webforJ sind die meisten Komponentenklassen als `final` deklariert. Ausgenommen sind die integrierten HTML-Elemente und alle Klassen, die diese erweitern. Das bedeutet, dass diese Komponentenklassen nicht erweitert oder als Unterklassen verwendet werden können. Diese Designentscheidung ist bewusst getroffen und verfolgt mehrere Ziele:

1. **Kontrolle über die clientseitige Verbindung**: Wie bereits erwähnt, würde das Erweitern von webforJ-Komponentenklassen die interne Verbindung zwischen der Java-Komponente und ihrem clientseitigen Gegenstück offenlegen. Um ein konsistentes und vorhersehbares Verhalten der Komponenten sicherzustellen, ist diese Art der Kontrolle eingeschränkt.

2. **Verhindern unbeabsichtigter Änderungen**: Durch die Deklaration der Komponentenklassen als `final` werden unbeabsichtigte Änderungen an Kernkomponenten verhindert und das Risiko unerwarteter Verhaltensweisen oder Sicherheitslücken verringert.

3. **Förderung der Verwendung von Kompositionen**: Um die Funktionalität von Komponenten zu erweitern, empfiehlt das webforJ-Framework einen auf Komposition basierenden Ansatz. Kompositionskomponenten sind Java-Klassen, die andere webforJ-Komponenten oder standardmäßige HTML-Elemente enthalten. Während von traditioneller Vererbung abgeraten wird, bieten Kompositionskomponenten eine Möglichkeit, neue, angepasste Komponenten zu erstellen, die bestehende Komponenten kapseln.

<!-- ## Komponenten zum DOM hinzufügen
TODO: Erläutern, wie webforJ das clientseitige Gegenstück einer Komponente auflöst -->

## Kompositionskomponenten: Erweitern durch Komposition {#composite-components-extending-through-composition}
Im webforJ-Framework spielen Kompositionskomponenten eine zentrale Rolle bei der Erweiterung der Komponentenfunktionalität. Für Kompositionskomponenten ist das Schlüsselwort final nicht vorgeschrieben. Dadurch können Entwickler neue Komponenten erstellen, die das Verhalten einer einzelnen Komponente erweitern oder mehrere Komponenten zu einer zusammenfassen, indem sie vorhandene Komponenten zusammensetzen. Für Entwickler wurden Klassen erstellt, die dieses Verhalten ermöglichen. In den Abschnitten `Composite` und `ElementComposite` erfahren Sie, wie Sie Kompositionskomponenten korrekt erstellen.

Dieser Ansatz fördert einen modulareren und flexibleren Entwicklungsstil und ermöglicht es Entwicklern, maßgeschneiderte Komponenten zu erstellen, die spezifische Anforderungen erfüllen.

<!--
## Komponenten-Hierarchie {#component-hierarchy}
TODO: Grafik erstellen und anzeigen -->
