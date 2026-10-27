---
sidebar_position: 21
title: Debouncing
slug: debouncing
description: >-
  Delay actions until activity settles using the Debouncer class for
  search-as-you-type, autosave, and other rate-limited UI work.
_i18n_hash: fd81dccbd2aeb6e50922c2d09de536de
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Le débouncing est une technique qui retarde l'exécution d'une action jusqu'à ce qu'un temps spécifié se soit écoulé depuis le dernier appel. Chaque nouvel appel réinitialise le minuteur. Cela est utile dans des scénarios comme la recherche au fur et à mesure que l'on tape, où vous voulez attendre que l'utilisateur cesse de taper avant d'exécuter une requête de recherche.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

Créez un `Debouncer` avec un délai en secondes, puis appelez `run()` avec l'action que vous souhaitez dégommer :

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Dans cet exemple, la méthode `search()` est appelée uniquement après que l'utilisateur a cessé de taper pendant 300 millisecondes. Chaque pression sur une touche réinitialise le minuteur via l'événement `onModify`, de sorte que la saisie rapide ne déclenche pas plusieurs recherches.

## Comment cela fonctionne {#how-it-works}

Lorsque vous appelez `run()` avec une action :

1. Si aucune action n'est en attente, le `Debouncer` planifie l'action pour qu'elle s'exécute après le délai.
2. Si une action est déjà en attente, l'action précédente est annulée et le minuteur redémarre avec la nouvelle action.
3. Une fois le délai écoulé sans un nouvel appel, l'action s'exécute.

Le `Debouncer` s'exécute sur le fil d'interface utilisateur en utilisant le mécanisme [`Interval`](/docs/advanced/interval) de webforJ, donc vous n'avez pas besoin d'envelopper les mises à jour de l'interface utilisateur dans `Environment.runLater()`.

:::tip Unités de délai
Le paramètre de délai utilise des secondes comme unité, pas des millisecondes. Utilisez `0.3f` pour 300 ms ou `1.5f` pour 1,5 seconde.
:::

## Contrôle de l'exécution {#controlling-execution}

Les méthodes suivantes peuvent être utilisées pour gérer plus précisément l'exécution et l'utilisation du `Debouncer` :

### Annuler une action en attente {#cancelling-a-pending-action}

Utilisez `cancel()` pour empêcher l'exécution d'une action en attente :

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// L'utilisateur navigue ailleurs avant que la sauvegarde ne s'exécute
debounce.cancel();
```

:::tip Annulation des débounces en attente
Comme pour les intervalles, il est bon d'annuler les actions débouncées en attente lorsqu'un composant est détruit. Cela prévient les fuites de mémoire et évite les erreurs dues aux actions exécutées sur des composants détruits :

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
:::

### Forcer l'exécution immédiate {#forcing-immediate-execution}

Utilisez `flush()` pour exécuter une action en attente immédiatement :

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Forcer la validation avant la soumission du formulaire
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Vérifier l'état en attente {#checking-pending-status}

Utilisez `isPending()` pour vérifier si une action attend d'être exécutée :

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Traitement en cours...");
}
```

## Débouncing au niveau des événements vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ fournit deux approches pour le débouncing :

| Caractéristique | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Portée         | Toute action | Événements d'élément uniquement   |
| Emplacement     | Côté serveur | Côté client                        |
| Unité          | Secondes (float) | Millisecondes (int)              |
| Flexibilité    | Contrôle total avec annulation/vidage | Automatique avec l'événement    |

Utilisez `Debouncer` lorsque vous avez besoin d'un contrôle programmatique sur le débouncing, comme pour annuler ou vider des actions en attente. Utilisez `ElementEventOptions` lorsque vous souhaitez un débouncing côté client simple pour les événements d'élément sans aller-retours supplémentaires vers le serveur.

```java
// Utilisation d'ElementEventOptions pour le débouncing côté client
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Ce gestionnaire est débouncé côté client
}, options);
```
