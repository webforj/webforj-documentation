---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 14d81d1a9ff86af17370e0a7eb50608b
---
Navigieren durch verschiedene Ansichten in einer Webanwendung umfasst mehrere Phasen und bietet Gelegenheiten, Aktionen vor, während oder nach einem Übergang auszuführen. Der Navigationslebenszyklus bietet ein ereignisgesteuertes System, das Entwicklern ermöglicht, wichtige Aspekte der Navigation zu verwalten, wie z.B. Datenvalidierung, Zugriffskontrolle, Aktualisierung der Benutzeroberfläche und Handhabung von Bereinigungen.

Dieses flexible System ermöglicht es Entwicklern, Übergänge explizit zu steuern, indem sie an kritischen Punkten im Navigationsprozess anknüpfen. Egal, ob Sie die Navigation blockieren, Daten abrufen müssen, wenn eine Komponente angezeigt wird, oder nicht gespeicherte Änderungen verwalten möchten, Sie haben die volle Kontrolle über den Navigationsfluss durch die Lebenszyklusereignisse und Beobachter.

## Übersicht der Lebenszyklusereignisse {#lifecycle-events-overview}

Der Navigationsprozess wird von einer Reihe von Ereignissen gesteuert, die während der Routenübergänge ausgelöst werden. Diese Ereignisse ermöglichen es Ihnen, an bestimmten Punkten im Lebenszyklus zu reagieren:

1. **WillEnter**: Wird ausgelöst, bevor zu einer Route navigiert wird und bevor ihre Komponente an das DOM angehängt wird. Dies ist ideal für Aufgaben wie Authentifizierungsprüfungen oder das Blockieren der Navigation, falls erforderlich.
2. **DidEnter**: Wird ausgelöst, nachdem die Navigation abgeschlossen und die Komponente an das DOM angehängt wurde. Dieses Ereignis eignet sich für Aktionen wie das Abrufen von Daten, das Ausführen von Animationen oder das Setzen des Fokus auf UI-Elemente.
3. **WillLeave**: Wird ausgelöst, bevor von der aktuellen Route weggewechselt wird und bevor ihre Komponente aus dem DOM entfernt wird. Es ist nützlich für die Verwaltung nicht gespeicherter Daten, um den Benutzer um Bestätigung zu bitten oder Bereinigungsaufgaben durchzuführen.
4. **DidLeave**: Wird ausgelöst, nachdem zu einer anderen Route gewechselt wurde und nachdem die Komponente aus dem DOM entfernt wurde. Dieses Ereignis ist ideal zum Freigeben von Ressourcen oder zum Zurücksetzen der Benutzeroberfläche für die zukünftige Nutzung.
5. **Activate** (seit `25.03`): Wird ausgelöst, wenn zwischengespeicherte Komponenten reaktiviert anstatt neu erstellt werden. Dies geschieht, wenn zu derselben Route mit anderen Parametern navigiert oder zu einer zuvor besuchten Route zurückgekehrt wird. Das Ereignis wird für alle zwischengespeicherten Komponenten in der Routenhierarchie, die im aktuellen Pfad verbleiben, ausgelöst, sodass sowohl übergeordnete Layouts als auch untergeordnete Komponenten ihre Daten aktualisieren oder die Benutzeroberfläche basierend auf neuen Parametern aktualisieren können, während der Komponentenstatus erhalten bleibt.

Diese Ereignisse bieten eine granulare Kontrolle über den Navigationslebenszyklus und ermöglichen es Entwicklern, Datenvalidierung, Benutzeroberflächenaktualisierungen und Ressourcenmanagement während der Routenübergänge zu koordinieren.

## Themen {#topics}

<DocCardList className="topics-section" />
