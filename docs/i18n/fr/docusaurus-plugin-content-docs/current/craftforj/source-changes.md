---
title: Writing Changes to Source
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c79e8574cbf260fd784a2cffc00a0ab5
---
Changer une propriété dans craftforJ modifie l'application en cours d'exécution et rien d'autre. Pour conserver un changement, vous devez le passer en revue et l'écrire dans le fichier Java d'où il provient. Cette page décrit cette étape.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4" type="video/mp4" />
  </video>
</div>

:::warning craftforJ écrit dans votre projet
Gardez votre travail sous contrôle de version. Lisez la différence avant de l'appliquer, et relisez-la avant de valider.
:::

## Changements en attente {#pending-changes}

Chaque propriété que vous modifiez est enregistrée comme un changement en attente, et craftforJ montre combien sont en attente. Les changements en attente survivent à un rechargement de page et à un changement de route, car craftforJ les réapplique lorsque vos composants sont reconstruits.

## Révision et application {#reviewing-and-applying}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> pour ouvrir la révision. Les changements sont regroupés par fichier dans lequel ils seront intégrés. Chacun montre la propriété avec sa valeur ancienne et nouvelle, et s'expand en affichant la différence du fichier. Si un changement remplace une valeur calculée par une valeur fixe, craftforJ vous avertit et nomme l'expression qu'il s'apprête à remplacer. Rien n'est écrit jusqu'à ce que vous appliquiez. Avant de le faire, vous pouvez annuler ou éliminer chaque changement individuellement.

![La révision avec des changements regroupés par fichier et un développé en sa différence](/img/craftforj/source-changes/review.png#rounded-border)

## Choisir où un changement est écrit {#choosing-where-a-change-is-written}

L'endroit où un changement est écrit détermine jusqu'où il s'étend. Lorsqu'un composant est construit directement dans une vue, le changement va dans cette vue. Lorsqu'il est construit à l'intérieur d'une classe réutilisable, vous avez deux options :

- **L'utilisation** - l'endroit où le composant est utilisé, ce qui change uniquement l'écran devant vous. C'est la option par défaut.
- **La définition** - l'endroit où le composant est construit, ce qui change chaque écran qui l'utilise.

Chaque changement en attente montre lequel des deux s'applique et vous permet de basculer entre eux. Certaines propriétés ne peuvent être écrites qu'à la définition, car le composant les définit lui-même plutôt que de les accepter de l'appelant. craftforJ les marque avant que vous n'appliquiez.

## Après que vous ayez appliqué {#after-you-apply}

Écrire du Java cause la reconstruction et le redémarrage de votre application. craftforJ signale le redémarrage, attend qu'il se produise, et se reconnecte avec votre sélection et vos changements en attente restants intacts. Les changements appliqués quittent la liste d'attente une fois qu'ils sont dans vos fichiers.

C'est le seul point où votre configuration de rechargement est importante. craftforJ n'a pas besoin de rechargement en direct pour fonctionner, car tout ce que vous modifiez en inspectant prend effet dans l'application en cours d'exécution immédiatement, sans reconstruction impliquée. Écrire dans la source est différent : cela modifie un fichier à partir duquel votre application a été construite, donc l'application doit être reconstruite avant que le changement provienne de votre code plutôt que de craftforJ. Avec [le rechargement en direct](/docs/configuration/deploy-reload/overview) configuré, cela se produit automatiquement. Sans cela, redémarrez l'application vous-même.

## Désactiver {#turning-it-off}

Vous pouvez désactiver l'écriture dans Java pour une application dans les paramètres de craftforJ, ou l'éliminer complètement avec la propriété [`source-changes`](/docs/craftforj/configuration#feature-flags). Avec l'un ou l'autre désactivé, l'édition des propriétés fonctionne toujours mais reste en direct.
