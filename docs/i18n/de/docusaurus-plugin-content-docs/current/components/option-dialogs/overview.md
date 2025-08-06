---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 7ff00c0abd564956da84fbd20761413e
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Optionsdialoge
<!-- vale on -->

Optionsdialoge bieten eine Möglichkeit für die App, mit den Benutzern zu kommunizieren und deren Eingaben zu sammeln. Diese Dialoge sind modal, was bedeutet, dass sie die Ausführung der App blockieren, bis der Benutzer mit ihnen interagiert, um sicherzustellen, dass wichtige Nachrichten angesprochen werden, bevor fortgefahren wird.

Optionsdialoge in webforJ sind ähnlich wie die `JOptionPane` in Swing und lösen ein grundlegendes Problem bei der Handhabung blockierender Dialoge in Webanwendungen.

:::tip Modalität
Bei der Verwendung von Optionsdialogen zur Erstellung von modalen Dialogen in webforJ blockiert der Dialog die Benutzereingaben in anderen Teilen der App und verarbeitet Ereignisse ausschließlich für den modalen Dialog. Dies stellt sicher, dass der Dialog reaktionsschnell bleibt und Interaktionen mit anderen Teilen verhindert werden, was das Benutzererlebnis verbessert und den Ablauf der App aufrechterhält. Der Server stoppt die Verarbeitung weiterer Anfragen, bis der Dialog geschlossen oder ein Wert daraus zurückgegeben wird.
:::

## Themen {#topics}

<DocCardList className="topics-section" />
