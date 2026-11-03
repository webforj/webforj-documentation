---
title: Application des modifications
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
Modifier une propriété dans craftforJ change l’application en cours d’exécution, et rien d’autre. Pour conserver une modification, examinez-la et écrivez-la dans le fichier Java dont elle provient. Cette page décrit cette étape.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ écrit dans votre projet
Conservez votre travail dans le contrôle de version. Lisez le diff avant de l’appliquer, puis relisez-le avant de valider.
:::

## Modifications en attente {#pending-changes}

Chaque propriété que vous modifiez est enregistrée comme une modification en attente, et craftforJ indique combien sont en attente. Les modifications en attente sont conservées après le rechargement d’une page ou un changement de route, car craftforJ les réapplique lorsque vos composants sont reconstruits.

## Examiner et appliquer {#reviewing-and-applying}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> pour ouvrir l’écran de vérification. Les modifications sont regroupées par fichier de destination. Chacune affiche la propriété avec son ancienne et sa nouvelle valeur, et permet de développer le diff du fichier. Si une modification remplace une valeur calculée par une valeur fixe, craftforJ vous avertit et indique l’expression qu’il s’apprête à remplacer. Rien n’est écrit tant que vous n’appliquez pas les modifications. Avant cela, vous pouvez annuler ou supprimer chaque modification individuellement.

![L’écran de vérification avec les modifications regroupées par fichier, et l’une d’elles développée pour afficher son diff](/img/craftforj/source-changes/review.png#rounded-border)

## Choisir où écrire une modification {#choosing-where-a-change-is-written}

L’endroit où une modification est écrite détermine sa portée. Lorsqu’un composant est directement créé dans une vue, la modification est écrite dans cette vue. Lorsqu’il est créé dans une classe réutilisable, vous avez deux possibilités :

- **L’utilisation** — l’endroit où le composant est utilisé, ce qui ne modifie que l’écran affiché. C’est l’option par défaut.
- **La définition** — l’endroit où le composant est créé, ce qui modifie tous les écrans qui l’utilisent.

Chaque modification en attente indique laquelle des deux options s’applique et vous permet de passer de l’une à l’autre. Certaines propriétés ne peuvent être écrites que dans la définition, car le composant les définit lui-même au lieu de les accepter de l’appelant. craftforJ les signale avant que vous appliquiez les modifications.

## Après l’application {#after-you-apply}

L’écriture dans un fichier Java entraîne la reconstruction et le redémarrage de votre application. craftforJ signale le redémarrage, attend qu’il se termine, puis se reconnecte en conservant votre sélection et les modifications en attente restantes. Une fois les modifications appliquées dans vos fichiers, elles disparaissent de la liste des modifications en attente.

C’est le seul moment où votre configuration de rechargement entre en jeu. craftforJ n’a pas besoin du rechargement à chaud pour fonctionner : toutes les modifications effectuées pendant l’inspection prennent immédiatement effet dans l’application en cours d’exécution, sans reconstruction. L’écriture dans le code source est différente : elle modifie un fichier à partir duquel votre application a été construite, et l’application doit donc être reconstruite pour que la modification provienne de votre code plutôt que de craftforJ. Si le [rechargement à chaud](/docs/configuration/deploy-reload/overview) est configuré, cela se fait automatiquement. Sinon, redémarrez l’application vous-même.

## Annuler et rétablir <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> dans craftforJ pour annuler votre dernière modification, et sur <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> pour la rétablir.

L’annulation fonctionne pour les modifications effectuées dans l’application en cours d’exécution, des [modifications de propriétés](/docs/craftforj/inspector#reading-and-changing-properties) aux [ajustements de thème](/docs/craftforj/theme#adjusting-a-theme). Elle fonctionne également pour les modifications que craftforJ a écrites dans votre projet, comme les modifications de propriétés appliquées, un [thème enregistré](/docs/craftforj/theme#saving-a-theme) ou un [composant supprimé du code source](/docs/craftforj/inspector#removing-a-component). L’annulation d’une modification écrite rétablit le fichier dans son état précédent. Si ce fichier est un fichier Java, votre application doit être reconstruite avant que la modification soit visible, comme [après l’application](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Appuyer plusieurs fois sur Annuler revient sur vos modifications une par une, en commençant par la plus récente ; Rétablir les rejoue dans l’ordre où vous les avez effectuées. Si vous effectuez une nouvelle modification après une annulation, toutes les modifications que vous auriez pu rétablir sont effacées.

### Quand une annulation est bloquée {#when-an-undo-is-blocked}

Si vous modifiez et enregistrez un fichier dans votre propre éditeur après que craftforJ y a écrit des changements, annuler ou rétablir cette étape écraserait votre modification. craftforJ refuse alors l’opération, laisse le fichier tel quel et indique son nom :

```
Modifié en dehors de craftforJ : ContactFormView.java. Annulez cette modification dans votre éditeur, ou ignorez cette étape.
```

Deux possibilités s’offrent à vous :

- Annulez votre modification dans votre éditeur pour que le fichier corresponde à ce que craftforJ a écrit, puis réessayez.
- Choisissez **Ignorer**. Le fichier reste tel quel et cette étape est supprimée de l’historique. L’ignorance n’annule rien ; les étapes précédentes qui ont modifié le même fichier peuvent donc toujours être bloquées.

## Désactiver cette fonctionnalité {#turning-it-off}

Vous pouvez désactiver l’écriture dans les fichiers Java pour une application dans les paramètres de craftforJ, ou la supprimer entièrement avec la propriété [`source-changes`](/docs/craftforj/configuration#feature-flags). Dans les deux cas, la modification des propriétés reste possible, mais les changements ne s’appliquent qu’à l’exécution.
