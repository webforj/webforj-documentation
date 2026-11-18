---
sidebar_position: 1000
title: Glossar
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

Das DOM (Document Object Model) ist eine Programmierschnittstelle für Webdokumente. Es stellt die Seitenstruktur als Baum aus Objekten dar, wobei jeder Knoten einem HTML-Element entspricht. JavaScript und Web-Frameworks verwenden das DOM, um dynamisch auf Inhalte, Struktur und Gestaltung von Webseiten zuzugreifen und diese zu bearbeiten.

## Shadow DOM {#shadow-dom}

Shadow DOM ist ein Webstandard, der die Kapselung des DOM (Document Object Model) und von CSS innerhalb eines bestimmten Elements ermöglicht, das als Shadow Tree bezeichnet wird. Dieses isolierte DOM und CSS sind vom DOM des Hauptdokuments getrennt und bilden so eine abgegrenzte Gültigkeitsgrenze für die Komponente. Shadow DOM hilft dabei, eigenständige, wiederverwendbare Webkomponenten zu erstellen, die sich in eine Webseite einfügen lassen, ohne dass Konflikte mit anderen Stilen und Skripten auf der Seite zu befürchten sind.

Außerdem bietet es Funktionen wie Shadow Parts und Slots, mit denen Entwickler bestimmte Teile des Shadow Tree zur Anpassung durch die übergeordnete Seite freigeben können. So lassen sich Inhalte flexibel an die Komponente übergeben und ihr Erscheinungsbild anpassen, während die Kapselung erhalten bleibt.

## Komponenten mit begrenztem Gültigkeitsbereich {#scoped-components}

Komponenten, deren Stile isoliert sind, um Konflikte mit Stilen außerhalb der Komponente zu verhindern. Diese Isolation wird erreicht, indem eindeutige Datenattribute auf die Stile der Komponente angewendet werden, statt sich auf das native Shadow DOM zu stützen.
