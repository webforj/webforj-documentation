---
title: App-Info
sidebar_position: 10
description: >-
  craftforJ App-Info zeigt, womit Ihre App tatsächlich läuft: die Versionen von
  webforJ und BBj Services, die Java-Laufzeit, das Betriebssystem und das
  Projektstammverzeichnis.
_i18n_title_hash: a98b1ca473ce24658a6b0d7e3da0ce51
_i18n_description_hash: 2e5f0241dcd8b09393fd3e5ddeca035c
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
