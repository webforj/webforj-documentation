---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: ef15124e21d87b0b23f9b1acae9228a8
---
Navigieren durch verschiedene Ansichten in einer Webanwendung umfasst mehrere Phasen und bietet Möglichkeiten, Aktionen vor, während oder nach einer Übergangsphase durchzuführen. Der Navigationslebenszyklus bietet ein ereignisgesteuertes System, das Entwicklern ermöglicht, wichtige Aspekte der Navigation zu verwalten, wie z. B. Datenvalidierung, Zugriffssteuerung, Aktualisierung der Benutzeroberfläche und Aufräumarbeiten.

Dieses flexible System gewährleistet reibungslose, konsistente Übergänge, indem es Entwicklern ermöglicht, an kritischen Punkten im Navigationsprozess einzugreifen. Egal, ob Sie die Navigation blockieren, Daten abrufen müssen, wenn eine Komponente angezeigt wird, oder nicht gespeicherte Änderungen verwalten möchten, Sie haben die volle Kontrolle über den Navigationsfluss durch seine Lebenszyklusereignisse und -beobachter.

## Übersicht der Lebenszyklusereignisse {#lifecycle-events-overview}

Der Navigationsprozess wird durch eine Reihe von Ereignissen gesteuert, die während der Routenübergänge ausgelöst werden. Diese Ereignisse ermöglichen es Ihnen, an bestimmten Punkten im Lebenszyklus zu reagieren:

1. **WillEnter**: Wird ausgelöst, bevor zur Route navigiert wird und bevor ihre Komponente an das DOM angehängt wird. Dies ist ideal für Aufgaben wie Authentifizierungsprüfungen oder das Blockieren der Navigation, falls erforderlich.
2. **DidEnter**: Wird nach Abschluss der Navigation und nachdem die Komponente an das DOM angehängt wurde, ausgelöst. Dieses Ereignis eignet sich für Aktionen wie das Abrufen von Daten, das Ausführen von Animationen oder das Setzen des Fokus auf UI-Elemente.
3. **WillLeave**: Wird ausgelöst, bevor von der aktuellen Route navigiert wird und bevor ihre Komponente aus dem DOM entfernt wird. Es ist nützlich für die Verwaltung nicht gespeicherter Daten, um den Benutzer um Bestätigung zu bitten oder Aufräumarbeiten durchzuführen.
4. **DidLeave**: Wird ausgelöst, nachdem zu einer anderen Route gewechselt wurde und nachdem die Komponente aus dem DOM entfernt wurde. Dieses Ereignis eignet sich hervorragend zum Freigeben von Ressourcen oder zum Zurücksetzen der Benutzeroberfläche für eine zukünftige Verwendung.

Diese Ereignisse bieten eine granulare Kontrolle über den Navigationslebenszyklus, was es einfacher macht, komplexe Übergänge zu verwalten und reibungslose Interaktionen zwischen den Routen zu gewährleisten.

## Themen {#topics}

<DocCardList className="topics-section" />
