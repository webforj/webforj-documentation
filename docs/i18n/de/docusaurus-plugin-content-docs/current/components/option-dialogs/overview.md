---
sidebar_position: 35
title: Options-Dialoge
hide_giscus_comments: true
description: >-
  Erstellen Sie modale Dialoge in webforJ, die die Serverausführung blockieren,
  bis der Nutzer antwortet – nach dem JOptionPane-Muster von Swing.
_i18n_title_hash: bb95c24008fccb9eb46dea4c70a999a2
_i18n_description_hash: d36986b2075fb59a2ebf060fbc0e743d
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Optionsdialoge
<!-- vale on -->

Optionsdialoge bieten eine Möglichkeit für die App, mit den Benutzern zu kommunizieren und deren Eingaben zu sammeln. Diese Dialoge sind modal, was bedeutet, dass sie die Ausführung der App blockieren, bis der Benutzer mit ihnen interagiert, und sicherstellen, dass wichtige Nachrichten angesprochen werden, bevor fortgefahren wird.

Optionsdialoge in webforJ sind ähnlich wie `JOptionPane` in Swing und lösen ein grundlegendes Problem beim Umgang mit blockierenden Dialogen in Webanwendungen.

:::tip Modalität
Bei der Verwendung von Optionsdialogen zur Erstellung von modalen Dialogen in webforJ blockiert der Dialog die Benutzereingaben für andere Teile der App und verarbeitet Ereignisse ausschließlich für den modalen Dialog. Dies stellt sicher, dass der Dialog reaktionsschnell bleibt, während Interaktionen mit anderen Teilen verhindert werden, was die Benutzererfahrung verbessert und den App-Fluss aufrechterhält. Der Server stoppt die Verarbeitung weiterer Anfragen, bis der Dialog geschlossen oder ein Wert von ihm zurückgegeben wird.
:::

## Themen {#topics}

<DocCardList className="topics-section" />
