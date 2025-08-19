---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 6ed66a95c218f7a03552269dd824ffd8
---
Navigieren durch verschiedene Ansichten in einer Webanwendung umfasst mehrere Phasen und bietet Gelegenheiten, Aktionen vor, während oder nach einem Übergang auszuführen. Der Navigationslebenszyklus bietet ein ereignisgesteuertes System, das Entwicklern ermöglicht, wichtige Aspekte der Navigation zu verwalten, wie Datenvalidierung, Zugriffskontrolle, UI-Aktualisierung und Aufräumarbeiten.

Dieses flexible System stellt reibungslose, konsistente Übergänge sicher, indem es Entwicklern ermöglicht, an kritischen Punkten im Navigationsprozess einzugreifen. Egal, ob Sie die Navigation blockieren, Daten abrufen müssen, wenn eine Komponente angezeigt wird, oder nicht gespeicherte Änderungen verwalten möchten, Sie haben die volle Kontrolle über den Navigationsfluss durch seine Lebenszyklusereignisse und Beobachter.

## Übersicht der Lebenszyklusereignisse {#lifecycle-events-overview}

Der Navigationsprozess wird von einer Reihe von Ereignissen gesteuert, die während der Routenübergänge ausgelöst werden. Diese Ereignisse ermöglichen es Ihnen, an bestimmten Punkten im Lebenszyklus zu reagieren:

1. **WillEnter**: Wird ausgelöst, bevor zu einer Route navigiert wird und bevor ihre Komponente an das DOM angehängt wird. Dies ist ideal für Aufgaben wie Authentifizierungsprüfungen oder das Blockieren der Navigation, falls erforderlich.
2. **DidEnter**: Wird ausgelöst, nachdem die Navigation abgeschlossen ist und die Komponente an das DOM angehängt wurde. Dieses Ereignis eignet sich für Aktionen wie das Abrufen von Daten, das Ausführen von Animationen oder das Setzen des Fokus auf UI-Elemente.
3. **WillLeave**: Wird ausgelöst, bevor von der aktuellen Route navigiert wird und bevor ihre Komponente aus dem DOM entfernt wird. Es ist nützlich für das Verwalten von nicht gespeicherten Daten, das Auffordern des Benutzers zur Bestätigung oder das Verwalten von Aufräumarbeiten.
4. **DidLeave**: Wird ausgelöst, nachdem zu einer anderen Route gewechselt wurde und nachdem die Komponente aus dem DOM entfernt wurde. Dieses Ereignis ist ideal zum Freigeben von Ressourcen oder zum Zurücksetzen der UI für die zukünftige Verwendung.

Diese Ereignisse bieten granularen Kontrolle über den Navigationslebenszyklus, was es einfacher macht, komplexe Übergänge zu verwalten und reibungslose Interaktionen über die Routen hinweg sicherzustellen.

## Themen {#topics}

<DocCardList className="topics-section" />
