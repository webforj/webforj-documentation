---
title: Inspection des composants
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
L’inspecteur affiche l’arborescence des composants construite par votre code Java. Un `Composite` apparaît sous la forme de la classe que vous avez écrite et contient les composants enfants que vous lui avez fournis, dans l’ordre où webforJ les conserve. La structure dans craftforJ correspond donc à celle de votre code source.

![Arborescence des composants avec un composant sélectionné et mis en surbrillance dans l’application en cours d’exécution](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Sélectionner un composant {#selecting-a-component}

Pour sélectionner un composant sur la page, appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, puis cliquez dessus. craftforJ sélectionne le nœud correspondant dans l’arborescence. Le survol d’un nœud dans l’arborescence produit l’effet inverse et met en surbrillance le composant correspondant sur la page. Vous pouvez ainsi passer de l’écran à l’arborescence et inversement.

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

Pour rechercher dans l’arborescence, appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Si vous encadrez un terme de barres obliques, il est interprété comme une expression régulière. Un clic droit sur un nœud ouvre les actions disponibles. Vous pouvez ouvrir son code source ou le transmettre à l’[assistant](/docs/craftforj/ai).

## Lire et modifier les propriétés {#reading-and-changing-properties}

Lorsque vous sélectionnez un composant, ses propriétés s’affichent dans la barre latérale, regroupées selon leur effet. Les propriétés proposées dépendent du composant, et certaines sont en lecture seule. Les propriétés qui ne se prêtent pas à une représentation en texte brut disposent d’un éditeur adapté à leur valeur. Toute modification prend effet immédiatement dans l’application en cours d’exécution.

:::info Les modifications en direct ne changent pas vos fichiers
La modification d’une propriété agit uniquement sur l’application affichée devant vous. Pour l’intégrer à votre code source, vous devez effectuer une étape distincte, comme décrit dans [Écrire les modifications dans le code source](/docs/craftforj/source-changes).
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## Afficher le code source d’un composant {#viewing-the-source-of-a-component}

Vous pouvez retrouver le code Java à l’origine de chaque composant. Par défaut, le code source s’ouvre en lecture seule dans craftforJ, à la ligne où le composant a été créé. Vous pouvez configurer craftforJ pour qu’il ouvre cette ligne dans votre éditeur à la place. Lorsqu’il est impossible de retrouver la ligne d’un composant, craftforJ vous le signale au lieu d’ouvrir une fenêtre vide.

![Visionneuse du code source positionnée sur la ligne qui a créé le composant sélectionné](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Supprimer un composant <DocChip chip='since' label='26.03' /> {#removing-a-component}

Un clic droit sur un composant de l’arborescence, puis le choix de **Supprimer le composant**, proposent deux façons de le retirer. La première le supprime de l’application affichée devant vous, la seconde de votre projet.

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### Supprimer uniquement du navigateur {#removing-from-the-browser-only}

**Supprimer uniquement du navigateur** retire le composant de l’application en cours d’exécution sans modifier le code source Java. Utilisez cette option pour voir à quoi ressemble une mise en page sans un composant donné avant de vous engager.

La suppression est temporaire. Le composant réapparaît la prochaine fois que l’application construit cette vue à partir de votre code source, par exemple après le rechargement de la page.

### Supprimer du code source {#removing-from-source}

**Supprimer du code source** modifie le fichier Java qui a créé le composant, de sorte qu’il reste supprimé après la reconstruction de l’application. La suppression est écrite immédiatement plutôt qu’ajoutée aux [modifications en attente](/docs/craftforj/source-changes#pending-changes) : elle est donc appliquée à votre fichier sans étape de vérification.

L’écriture dans le fichier Java entraîne la reconstruction et le redémarrage de l’application, comme lorsque vous [appliquez des modifications](/docs/craftforj/source-changes#after-you-apply). Si le [rechargement à chaud](/docs/configuration/deploy-reload/overview) est configuré, cela se fait automatiquement. Sinon, redémarrez vous-même l’application.

Prenons l’exemple d’un formulaire avec un bouton **Enregistrer** et un bouton **Annuler**, où **Enregistrer** a un thème et un écouteur de clic :

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Si vous sélectionnez **Enregistrer** et choisissez **Supprimer du code source**, le code devient celui-ci. La déclaration, le thème, l’écouteur et l’emplacement du bouton dans l’appel à `add()` sont supprimés, ainsi que l’importation de `ButtonTheme`, qui n’est plus utilisée. Après la reconstruction de l’application, **Annuler** est toujours présent :

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

La suppression concerne le composant et rien d’autre :

- La suppression d’un conteneur supprime également les composants qu’il contient.
- Le code qui servait uniquement à créer, configurer et ajouter le composant est nettoyé. Cela inclut les champs et les importations qui ne sont plus utilisés.
- Le code encore nécessaire ailleurs est conservé. Si une autre partie de la classe fait encore référence au composant, par exemple l’écouteur d’un autre composant, craftforJ le détache uniquement et conserve le code qui le crée et le configure.
- La suppression d’une instance d’un composant réutilisable, comme un `Composite` que vous avez écrit, supprime uniquement cette utilisation. La classe qui le définit n’est ni supprimée ni modifiée.

craftforJ n’écrit dans le fichier que si le résultat compile toujours. Les fichiers ne sont jamais supprimés.

### Quand la suppression n’est pas disponible {#when-removal-isnt-available}

L’option **Supprimer du code source** est proposée uniquement si l’écriture dans le code Java est autorisée à la fois dans la [configuration](/docs/craftforj/configuration#feature-flags) de l’application et dans le paramètre **Modifications du code source Java** de craftforJ. Elle n’est pas proposée pour les composants écrits en Kotlin ni pour ceux dont craftforJ ne peut pas retrouver la ligne source.

Certains composants ne peuvent pas être supprimés du code source :

- Les routes, que le routeur place en fonction de leur annotation `@Route`
- Le frame
- Les composants qu’un autre composant construit dans le cadre de sa propre création. Supprimez plutôt le composant englobant.

craftforJ peut également refuser une suppression s’il ne trouve pas le code source du composant ou s’il ne peut pas déterminer quel code supprimer. Par exemple, lorsqu’une ligne crée plusieurs composants du même type, elle ne permet pas à elle seule de savoir lequel vous avez sélectionné.

En cas de refus, vos fichiers restent inchangés et la raison est indiquée :

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Annuler une suppression {#undoing-a-removal}

Pour restaurer un composant supprimé du code source, appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> dans craftforJ. Le fichier revient à son état antérieur à la suppression. Si vous l’avez modifié dans votre propre éditeur depuis, l’annulation est bloquée plutôt que d’écraser vos modifications. Consultez [Annuler et rétablir](/docs/craftforj/source-changes#undoing-and-redoing) pour en savoir plus sur le fonctionnement de l’annulation et sur la marche à suivre lorsqu’elle est bloquée.
