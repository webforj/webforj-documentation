---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: be654f5efb68050d8632a27166954583
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Le debounce est une technique qui retarde l'exécution d'une action jusqu'à ce qu'un temps spécifié se soit écoulé depuis le dernier appel. Chaque nouvel appel réinitialise le minuteur. Cela est utile pour des scénarios comme la recherche au fur et à mesure que vous tapez, où vous souhaitez attendre que l'utilisateur cesse de taper avant d'exécuter une requête de recherche.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerDemoView.java'
height='265px'
/>

## Utilisation de base {#basic-usage}

La classe `Debouncer` fournit un moyen simple de debouncer des actions. Créez un `Debouncer` avec un délai en secondes, puis appelez `run()` avec l'action que vous souhaitez debounce :

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Dans cet exemple, la méthode `search()` est appelée uniquement après que l'utilisateur ait cessé de taper pendant 300 millisecondes. Chaque frappe réinitialise le minuteur via l'événement `onModify`, de sorte qu'une saisie rapide ne déclenche pas plusieurs recherches.

## Comment ça fonctionne {#how-it-works}

Lorsque vous appelez `run()` avec une action :

1. Si aucune action n'est en attente, le `Debouncer` programme l'action à exécuter après le délai
2. Si une action est déjà en attente, l'action précédente est annulée et le minuteur redémarre avec la nouvelle action
3. Une fois le délai écoulé sans un autre appel, l'action s'exécute

Le `Debouncer` s'exécute sur le thread de l'interface utilisateur en utilisant le mécanisme [`Interval`](/docs/advanced/interval) de webforJ, vous n'avez donc pas besoin d'envelopper les mises à jour de l'UI dans `Environment.runLater()`.

:::tip Unités de délai
Le paramètre de délai utilise des secondes comme unité, pas des millisecondes. Utilisez `0.3f` pour 300 ms ou `1.5f` pour 1,5 seconde.
:::

## Contrôle de l'exécution {#controlling-execution}

Les méthodes suivantes peuvent être utilisées pour gérer plus précisément l'exécution et l'utilisation du `Debouncer` :

### Annulation d'une action en attente {#cancelling-a-pending-action}

Utilisez `cancel()` pour arrêter une action en attente d'exécution :

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// L'utilisateur navigue avant que l'enregistrement ne s'exécute
debounce.cancel();
```

:::tip Annulation des debounces en attente
Comme pour les intervalles, il est recommandé d'annuler les actions debouncées en attente lorsqu'un composant est détruit. Cela prévient les fuites de mémoire et évite les erreurs liées à l'exécution d'actions sur des composants détruits :

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

### Vérification du statut en attente {#checking-pending-status}

Utilisez `isPending()` pour vérifier si une action attend d'être exécutée :

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Traitement en cours...");
}
```

## Debouncing au niveau des événements vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ propose deux approches pour le debouncing :

| Caractéristique | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Portée         | Toute action | Événements d'élément uniquement    |
| Emplacement     | Côté serveur | Côté client                         |
| Unité          | Secondes (float) | Millisecondes (int)                |
| Flexibilité    | Contrôle total avec annulation/flush | Automatique avec événement         |

Utilisez `Debouncer` lorsque vous avez besoin d'un contrôle programmatique sur le debouncing, comme l'annulation ou le flush des actions en attente. Utilisez `ElementEventOptions` lorsque vous souhaitez un debouncing côté client simple pour les événements d'élément sans трассировки supplémentaires vers le serveur.

```java
// Utilisation d'ElementEventOptions pour le debouncing côté client
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Ce gestionnaire est debounced sur le client
}, options);
```
