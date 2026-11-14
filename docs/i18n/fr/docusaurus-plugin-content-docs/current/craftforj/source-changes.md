---
title: Application des modifications
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
Modifier une propriété dans craftforJ change l’application en cours d’exécution, et rien d’autre. Pour conserver une modification, examinez-la et inscrivez-la dans le fichier Java d’où elle provient. Cette page décrit cette étape.

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ écrit dans votre projet
Conservez votre travail dans le contrôle de version. Lisez le diff avant de l’appliquer, puis relisez-le avant de créer un commit.
:::

## Modifications en attente {#pending-changes}

Chaque propriété que vous modifiez est enregistrée comme une modification en attente, et craftforJ indique combien il en reste. Les modifications en attente sont conservées après le rechargement d’une page ou le changement de route, car craftforJ les réapplique lors de la reconstruction de vos composants.

## Examiner et appliquer {#reviewing-and-applying}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> pour ouvrir l’écran de révision. Les modifications sont regroupées par fichier de destination. Chacune affiche la propriété et son ancienne et sa nouvelle valeur, et peut être développée pour afficher le diff du fichier. Si une modification remplace une valeur calculée par une valeur fixe, craftforJ vous avertit et indique l’expression qu’il s’apprête à remplacer. Rien n’est écrit tant que vous n’appliquez pas les modifications. Avant cela, vous pouvez annuler ou supprimer chaque modification individuellement.

![L’écran de révision avec les modifications regroupées par fichier et l’une d’elles développée pour afficher son diff](/img/craftforj/source-changes/review.png#rounded-border)

## Choisir où une modification est écrite {#choosing-where-a-change-is-written}

L’endroit où une modification est écrite détermine sa portée. Lorsqu’un composant est construit directement dans une vue, la modification est inscrite dans cette vue. Lorsqu’il est construit dans une classe réutilisable, deux options s’offrent à vous :

- **L’utilisation** — l’endroit où le composant est utilisé. La modification ne concerne alors que l’écran affiché. C’est l’option par défaut.
- **La définition** — l’endroit où le composant est construit. La modification concerne alors tous les écrans qui l’utilisent.

Chaque modification en attente indique laquelle de ces options s’applique et vous permet de passer de l’une à l’autre. Certaines propriétés ne peuvent être écrites que dans la définition, car le composant les définit lui-même au lieu de les recevoir de l’appelant. craftforJ les signale avant que vous appliquiez les modifications.

## Après l’application {#after-you-apply}

L’écriture dans le code Java entraîne la reconstruction et le redémarrage de votre application. craftforJ signale le redémarrage, l’attend, puis se reconnecte en conservant votre sélection et les modifications en attente restantes. Les modifications appliquées disparaissent de la liste dès qu’elles sont inscrites dans vos fichiers.

C’est le seul moment où votre configuration de rechargement entre en jeu. craftforJ n’a pas besoin du rechargement à chaud pour fonctionner, car les modifications effectuées pendant l’inspection prennent immédiatement effet dans l’application en cours d’exécution, sans reconstruction. L’écriture dans le code source est différente : elle modifie un fichier à partir duquel votre application a été construite. L’application doit donc être reconstruite pour que la modification provienne de votre code plutôt que de craftforJ. Si le [rechargement à chaud](/docs/configuration/deploy-reload/overview) est configuré, cela se fait automatiquement. Sinon, redémarrez l’application vous-même.

## Annuler et rétablir <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> dans craftforJ pour annuler votre dernière modification, et sur <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> pour la rétablir.

L’annulation s’applique aux modifications apportées dans l’application en cours d’exécution, des [modifications de propriétés](/docs/craftforj/inspector#reading-and-changing-properties) aux [ajustements de thème](/docs/craftforj/theme#adjusting-a-theme). Elle s’applique également aux modifications que craftforJ a inscrites dans votre projet, comme les modifications de propriétés appliquées, un [thème enregistré](/docs/craftforj/theme#saving-a-theme) ou un [composant supprimé du code source](/docs/craftforj/inspector#removing-a-component). Annuler une modification écrite restaure le fichier dans son état précédent. Si ce fichier est un fichier Java, votre application doit être reconstruite avant que la modification ne prenne effet, comme [après l’application](#after-you-apply).

<!-- TODO : ajouter l’enregistrement des annulations et des rétablissements -->

Appuyer plusieurs fois sur la touche d’annulation annule vos modifications une par une, en commençant par la plus récente. Le rétablissement les rejoue dans l’ordre où vous les avez effectuées. Si vous apportez une nouvelle modification après une annulation, toutes les modifications que vous auriez pu rétablir sont effacées.

### Lorsqu’une annulation est bloquée {#when-an-undo-is-blocked}

Si vous modifiez et enregistrez un fichier dans votre propre éditeur après que craftforJ y a écrit, annuler ou rétablir cette étape écraserait vos modifications. craftforJ refuse alors l’opération, laisse le fichier tel quel et indique son nom :

```
Modifié en dehors de craftforJ : ContactFormView.java. Annulez cette modification dans votre éditeur ou ignorez cette étape.
```

Deux options s’offrent à vous :

- Annulez votre modification dans votre éditeur pour que le fichier corresponde à ce que craftforJ a écrit, puis réessayez.
- Choisissez **Ignorer**. Le fichier reste tel quel et cette étape est supprimée de l’historique. L’ignorer ne rétablit rien ; des étapes antérieures ayant modifié le même fichier peuvent donc toujours être bloquées.

## Désactiver l’écriture {#turning-it-off}

Vous pouvez désactiver l’écriture dans les fichiers Java pour une application dans les paramètres de craftforJ, ou la supprimer complètement à l’aide de la propriété [`source-changes`](/docs/craftforj/configuration#feature-flags). Dans les deux cas, la modification des propriétés reste possible, mais les changements ne sont appliqués qu’à l’exécution.
