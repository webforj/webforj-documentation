---
title: Écriture des modifications au code source
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
Changer une propriété dans craftforJ modifie l'application en cours d'exécution et rien d'autre. Pour conserver une modification, vous devez la réviser et l'écrire dans le fichier Java d'où elle provient. Cette page décrit cette étape.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ écrit dans votre projet
Conservez votre travail dans le contrôle de version. Lisez la différence avant de l'appliquer, puis lisez-la à nouveau avant de valider.
:::

## Changements en attente {#pending-changes}

Chaque propriété que vous modifiez est enregistrée comme un changement en attente, et craftforJ indique combien sont en attente. Les changements en attente survivent au rechargement de page et à un changement de route, car craftforJ les réapplique lorsque vos composants sont reconstruits.

## Révision et application {#reviewing-and-applying}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> pour ouvrir la révision. Les changements sont regroupés par fichier. Chacun montre la propriété avec son ancienne et sa nouvelle valeur, et se développe pour afficher la différence dans le fichier. Si un changement remplace une valeur calculée par une valeur fixe, craftforJ vous avertit et nomme l'expression qu'il est sur le point de remplacer. Rien n'est écrit tant que vous ne l'appliquez pas. Avant de le faire, vous pouvez annuler ou supprimer chaque changement individuellement.

![La révision avec les changements regroupés par fichier et un développé pour sa différence](/img/craftforj/source-changes/review.png#rounded-border)

## Choisir où un changement est écrit {#choosing-where-a-change-is-written}

L'endroit où un changement est écrit détermine son ampleur. Lorsqu'un composant est construit directement dans une vue, le changement va dans cette vue. Lorsqu'il est construit à l'intérieur d'une classe réutilisable, vous avez deux options :

- **L'utilisation** - l'endroit où le composant est utilisé, ce qui ne change que l'écran devant vous. C'est le paramètre par défaut.
- **La définition** - l'endroit où le composant est construit, ce qui change tous les écrans qui l'utilisent.

Chaque changement en attente montre lequel des deux s'applique et vous permet de basculer entre eux. Certaines propriétés ne peuvent être écrites qu'à la définition, car le composant les définit lui-même plutôt que de les accepter de l'appelant. craftforJ les marque avant que vous ne les appliquiez.

## Après vous être appliqué {#after-you-apply}

Écrire en Java provoque la reconstruction et le redémarrage de votre application. craftforJ signale le redémarrage, attend qu'il se termine, et se reconnecte avec votre sélection et vos changements en attente restants intacts. Les changements appliqués quittent la liste des changements en attente une fois qu'ils sont dans vos fichiers.

C'est le seul moment où votre configuration de rechargement est importante. craftforJ n'a pas besoin de rechargement en direct pour fonctionner, car tout ce que vous modifiez lors de l'inspection prend effet directement dans l'application en cours d'exécution, sans aucune reconstruction impliquée. Écrire dans le code source est différent : cela modifie un fichier à partir duquel votre application a été construite, donc l'application doit se reconstruire avant que le changement provienne de votre code plutôt que de craftforJ. Avec [rechargement en direct](/docs/configuration/deploy-reload/overview) configuré, cela se produit tout seul. Sans cela, redémarrez l'application vous-même.

## Désactiver {#turning-it-off}

Vous pouvez désactiver l'écriture en Java pour une application dans les paramètres de craftforJ, ou l'enlever complètement avec la propriété [`source-changes`](/docs/craftforj/configuration#feature-flags). Avec l'une ou l'autre option désactivée, l'édition de propriété fonctionne toujours mais reste en direct.
