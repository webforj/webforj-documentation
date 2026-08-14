---
title: App Info
sidebar_position: 10
description: >-
  Read the versions, Java runtime, and project root of the app craftforJ is
  attached to.
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
App-Informationen berichten darüber, womit Ihre App tatsächlich läuft, was nicht immer mit dem übereinstimmt, was in Ihrer `pom.xml` angegeben ist. Neben den Versionen von webforJ und BBj Services umfasst es die Java-Laufzeit, das Betriebssystem und wo die App auf der Festplatte verwurzelt ist.

![Die App-Info-Registerkarte](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Zwei dieser Werte beeinflussen, wie craftforJ sich verhält:

- **Das Projektstammverzeichnis** ist der Ort, an dem craftforJ nach Ihren Quellcodes sucht. [Ändern des Quellcodes](/docs/craftforj/source-changes) kann nicht funktionieren, wenn es falsch ist, also setzen Sie [`project-root`](/docs/craftforj/configuration#project-root), wenn der angegebene Wert nicht mit Ihrem Projekt übereinstimmt.
- **Die Java-Laufzeit** bestimmt, wie gründlich die [Java-Änderungen](/docs/craftforj/ai#it-writes-java) des Assistenten validiert werden, da für eine vollständige Validierung ein Compiler benötigt wird.

:::tip Ein Issue melden
Fügen Sie alles auf dieser Seite zusammen mit einem Protokoll, das aus den Fehlerbehebungseinstellungen von craftforJ heruntergeladen wurde, bei.
:::
