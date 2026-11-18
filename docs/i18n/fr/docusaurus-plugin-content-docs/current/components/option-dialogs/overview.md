---
sidebar_position: 35
title: Boîtes de dialogue d'options
hide_giscus_comments: true
description: >-
  Créez des boîtes de dialogue modales dans webforJ qui bloquent l’exécution du
  serveur jusqu’à la réponse de l’utilisateur, selon le modèle JOptionPane de
  Swing.
_i18n_title_hash: bb95c24008fccb9eb46dea4c70a999a2
_i18n_description_hash: d36986b2075fb59a2ebf060fbc0e743d
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Dialogues d'option
<!-- vale on -->

Les dialogues d'option fournissent un moyen pour l'application de communiquer avec les utilisateurs et de recueillir leurs inputs. Ces dialogues sont modaux, ce qui signifie qu'ils bloquent l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec eux, garantissant que les messages importants soient traités avant de continuer.

Les dialogues d'option dans webforJ sont similaires au `JOptionPane` dans Swing, résolvant un problème fondamental de gestion des dialogues bloquants dans les applications web.

:::tip Modalité
Lors de l'utilisation des dialogues d'option pour créer des dialogues modaux dans webforJ, le dialogue bloque l'input utilisateur vers d'autres parties de l'application et traite les événements uniquement pour le dialogue modal. Cela garantit que le dialogue reste réactif tout en empêchant les interactions avec d'autres parties, améliorant ainsi l'expérience utilisateur et maintenant le flux de l'application. Le serveur cesse de traiter toute demande supplémentaire jusqu'à ce que le dialogue soit fermé ou qu'une valeur soit renvoyée.
:::

## Sujets {#topics}

<DocCardList className="topics-section" />
