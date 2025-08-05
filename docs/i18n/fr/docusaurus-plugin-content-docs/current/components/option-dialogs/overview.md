---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 7ff00c0abd564956da84fbd20761413e
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Dialogues d'Option
<!-- vale on -->

Les dialogues d'option fournissent un moyen pour l'application de communiquer avec les utilisateurs et de recueillir leurs avis. Ces dialogues sont modaux, ce qui signifie qu'ils bloquent l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec eux, garantissant que les messages importants sont traités avant de continuer.

Les dialogues d'option dans webforJ sont similaires au `JOptionPane` dans Swing, résolvant un problème fondamental de gestion des dialogues bloquants dans les applications web.

:::tip Modalité
Lors de l'utilisation des dialogues d'option pour créer des dialogues modaux dans webforJ, le dialogue bloque l'entrée utilisateur pour d'autres parties de l'application et traite les événements uniquement pour le dialogue modal. Cela garantit que le dialogue reste réactif tout en empêchant les interactions avec d'autres parties, améliorant ainsi l'expérience utilisateur et maintenant le flux de l'application. Le serveur cesse de traiter toute demande supplémentaire jusqu'à ce que le dialogue soit fermé ou qu'une valeur soit renvoyée.
:::

## Sujets {#topics}

<DocCardList className="topics-section" />
