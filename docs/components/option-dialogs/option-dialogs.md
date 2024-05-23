---
sidebar_position: 1
title: Option Dialogs
---

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Option Dialogs
<!-- vale on -->

Option dialogs provides a way for the app to communicate with users and gather their input. These dialogs are modal, meaning they block app execution until the user interacts with them, ensuring important messages are addressed before proceeding.

Option dialogs in webforJ are similar to the `JOptionPane` in Swing, solving a fundamental problem of handling blocking dialogs in web applications.

:::tip Modality
When using option dialogs to create modal dialogs in webforJ, the dialog blocks user input to other parts of the app and processes events solely for the modal dialog. This ensures the dialog remains responsive while preventing interactions with other parts, enhancing the user experience and maintaining app flow. The server stops processing any further requests until the dialog is dismissed or a value is returned from it.
:::

# Topics

<DocCardList className="topics-section" />