---
title: Inspection des composants
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
L’inspecteur affiche l’arborescence des composants construite par votre code Java. Un `Composite` apparaît sous la forme de la classe que vous avez écrite et contient les enfants que vous lui avez transmis, dans l’ordre dans lequel webforJ les conserve. La structure dans craftforJ correspond donc à celle de votre code source.

![Arborescence des composants avec un composant sélectionné et mis en surbrillance dans l’application en cours d’exécution](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Sélectionner un composant {#selecting-a-component}

Pour sélectionner un composant sur la page, appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>, puis cliquez dessus. craftforJ sélectionne le nœud correspondant dans l’arborescence. Survolez un nœud de l’arborescence pour mettre en surbrillance le composant correspondant sur la page. Vous pouvez ainsi passer de l’écran à l’arborescence dans les deux sens.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Pour rechercher dans l’arborescence, appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Entourez un terme de barres obliques pour le traiter comme une expression régulière. Faites un clic droit sur un nœud pour afficher les actions disponibles. Vous pouvez ouvrir son code source ou le transmettre à l’[assistant](/docs/craftforj/ai).

## Lire et modifier les propriétés {#reading-and-changing-properties}

La sélection d’un composant affiche ses propriétés dans la barre latérale, regroupées selon leur fonction. Les propriétés proposées varient selon le composant et certaines sont en lecture seule. Les propriétés qui ne se prêtent pas à une représentation sous forme de texte brut disposent d’un éditeur adapté à leur valeur. Toute modification prend effet immédiatement dans l’application en cours d’exécution.

:::info Les modifications en direct ne modifient pas vos fichiers
La modification d’une propriété ne change que l’application affichée devant vous. Pour intégrer cette modification à votre code source, vous devez effectuer une étape distincte, décrite dans [Écrire les modifications dans le code source](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Afficher le code source d’un composant {#viewing-the-source-of-a-component}

Vous pouvez retrouver le code Java à l’origine de n’importe quel composant. Par défaut, le code source s’ouvre en lecture seule dans craftforJ, à la ligne qui a créé le composant. Vous pouvez configurer craftforJ pour qu’il s’ouvre plutôt dans votre éditeur, à la même ligne. Si craftforJ ne peut pas retrouver la ligne d’origine d’un composant, il vous en informe au lieu d’ouvrir une visionneuse vide.

![Visionneuse du code source positionnée sur la ligne qui a créé le composant sélectionné](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Supprimer un composant <DocChip chip='since' label='26.03' /> {#removing-a-component}

Faites un clic droit sur un composant de l’arborescence et choisissez **Supprimer le composant** pour afficher deux façons de le retirer. L’une le supprime de l’application affichée devant vous, l’autre le supprime de votre projet.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### Supprimer uniquement du navigateur {#removing-from-the-browser-only}

**Supprimer uniquement du navigateur** retire le composant de l’application en cours d’exécution sans modifier votre code source Java. Utilisez cette option pour voir à quoi ressemble une mise en page sans un composant donné avant de prendre une décision.

La suppression est temporaire. Le composant réapparaît la prochaine fois que l’application reconstruit cette vue à partir de votre code source, par exemple après le rechargement de la page.

### Supprimer du code source {#removing-from-source}

**Supprimer du code source** modifie le fichier Java qui a créé le composant, afin qu’il reste supprimé après la reconstruction de l’application. La suppression est écrite immédiatement dans le fichier, sans être ajoutée aux [modifications en attente](/docs/craftforj/source-changes#pending-changes) : aucune étape de révision n’est donc nécessaire avant qu’elle ne soit inscrite dans votre fichier.

L’écriture du code Java entraîne la reconstruction et le redémarrage de l’application, comme lors de [l’application des modifications](/docs/craftforj/source-changes#after-you-apply). Si le [rechargement à chaud](/docs/configuration/deploy-reload/overview) est configuré, cela se fait automatiquement. Sinon, redémarrez l’application vous-même.

Prenons l’exemple d’un formulaire avec un bouton **Enregistrer** et un bouton **Annuler**, où **Enregistrer** possède un thème et un gestionnaire de clic :

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

Sélectionnez **Enregistrer**, puis choisissez **Supprimer du code source**. Le résultat est le suivant. La déclaration, le thème, le gestionnaire de clic et la place du bouton dans l’appel `add()` sont supprimés, tout comme l’importation de `ButtonTheme`, qui n’est plus utilisée. Une fois l’application reconstruite, **Annuler** est toujours présent :

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

La suppression concerne uniquement le composant et rien d’autre :

- La suppression d’un conteneur supprime également les composants qu’il contient.
- Le code qui servait uniquement à créer, configurer et ajouter le composant est nettoyé. Cela inclut les champs et les importations qui ne sont utilisés nulle part ailleurs.
- Le code encore nécessaire ailleurs est conservé. Si une autre partie de la classe utilise toujours le composant, par exemple le gestionnaire d’un autre composant, craftforJ le détache uniquement et conserve le code qui le crée et le configure.
- La suppression d’une instance d’un composant réutilisable, comme un `Composite` que vous avez écrit, ne supprime que cette utilisation. La classe qui le définit n’est ni supprimée ni modifiée.

craftforJ n’écrit dans le fichier que si le résultat compile toujours. Les fichiers ne sont jamais supprimés.

### Quand la suppression n’est pas disponible {#when-removal-isnt-available}

L’option **Supprimer du code source** est proposée uniquement si l’écriture de code Java est autorisée à la fois dans la [configuration](/docs/craftforj/configuration#feature-flags) de l’application et dans le paramètre **Modifications du code source Java** de craftforJ. Elle n’est pas proposée pour les composants créés en Kotlin ni pour ceux dont craftforJ ne peut pas retrouver la ligne source.

Certains composants ne peuvent pas être supprimés du code source :

- Les routes, que le routeur place selon leur annotation `@Route`
- Le cadre
- Les composants qu’un autre composant crée dans le cadre de son fonctionnement. Supprimez plutôt le composant englobant.

craftforJ peut également refuser une suppression s’il ne trouve pas le code source du composant ou s’il ne peut pas déterminer quel code supprimer. Par exemple, si une ligne crée plusieurs composants du même type, elle ne permet pas à elle seule de savoir lequel vous avez sélectionné.

En cas de refus, vos fichiers restent inchangés et la raison vous est indiquée :

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Annuler une suppression {#undoing-a-removal}

Pour rétablir un composant supprimé du code source, appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> dans craftforJ. Cela restaure le fichier tel qu’il était avant la suppression. Si vous avez modifié le fichier dans votre propre éditeur depuis, l’annulation est bloquée au lieu d’écraser vos modifications. Consultez [Annuler et rétablir](/docs/craftforj/source-changes#undoing-and-redoing) pour savoir comment fonctionne l’annulation et que faire lorsqu’elle est bloquée.
