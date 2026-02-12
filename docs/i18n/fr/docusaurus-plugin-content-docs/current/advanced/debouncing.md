---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: 89cdcc39e4954963d7e19cb0e5665ca4
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Le débouncing est une technique qui retarde l'exécution d'une action jusqu'à ce qu'un certain temps se soit écoulé depuis le dernier appel. Chaque nouvel appel réinitialise le timer. Cela est utile pour des scénarios comme la recherche au fur et à mesure que vous tapez, où vous voulez attendre que l'utilisateur cesse de taper avant d'exécuter une requête de recherche.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java'
height='265px'
/>

## Utilisation de base {#basic-usage}

La classe `Debouncer` fournit un moyen simple de débourser des actions. Créez un `Debouncer` avec un délai en secondes, puis appelez `run()` avec l'action que vous souhaitez débourser :

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Dans cet exemple, la méthode `search()` n'est appelée que lorsque l'utilisateur cesse de taper pendant 300 millisecondes. Chaque frappe réinitialise le timer via l'événement `onModify`, donc une saisie rapide ne déclenchera pas plusieurs recherches.

## Comment ça fonctionne {#how-it-works}

Lorsque vous appelez `run()` avec une action :

1. Si aucune action n'est en attente, le `Debouncer` programme l'action pour s'exécuter après le délai
2. Si une action est déjà en attente, l'action précédente est annulée et le timer redémarre avec la nouvelle action
3. Une fois le délai écoulé sans un autre appel, l'action s'exécute

Le `Debouncer` s'exécute sur le thread UI en utilisant le mécanisme [`Interval`](/docs/advanced/interval) de webforJ, donc vous n'avez pas besoin d'encapsuler les mises à jour de l'interface utilisateur dans `Environment.runLater()`.

:::tip Unités de délai
Le paramètre de délai utilise des secondes comme unité, pas des millisecondes. Utilisez `0.3f` pour 300 ms ou `1.5f` pour 1,5 seconde.
:::

## Contrôler l'exécution {#controlling-execution}

Les méthodes suivantes peuvent être utilisées pour gérer plus précisément l'exécution et l'utilisation du `Debouncer` :

### Annuler une action en attente {#cancelling-a-pending-action}

Utilisez `cancel()` pour empêcher une action en attente de s'exécuter :

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// L'utilisateur navigue ailleurs avant que l'enregistrement ne s'exécute
debounce.cancel();
```

:::tip Annuler les débourrages en attente
Comme pour les intervalles, il est bon de pratique d'annuler les actions débourrées en attente lorsque un composant est détruit. Cela prévient les fuites de mémoire et évite les erreurs dues à des actions s'exécutant sur des composants détruits :

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

### Vérifier le statut en attente {#checking-pending-status}

Utilisez `isPending()` pour vérifier si une action attend d'être exécutée :

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Traitement en cours...");
}
```

## Débouncing au niveau des événements vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ fournit deux approches pour le débouncing :

| Fonctionnalité | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Portée         | Toute action | Événements d'éléments uniquement |
| Emplacement     | Côté serveur | Côté client |
| Unité          | Secondes (float) | Millisecondes (int) |
| Flexibilité    | Contrôle total avec annulation/flush | Automatique avec événement |

Utilisez `Debouncer` lorsque vous avez besoin d'un contrôle programmatique sur le débouncing, comme l'annulation ou le vidage d'actions en attente. Utilisez `ElementEventOptions` lorsque vous souhaitez un débouncing côté client simple pour les événements d'éléments sans trajets supplémentaires vers le serveur.

```java
// Utilisation de ElementEventOptions pour le débouncing côté client
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Ce gestionnaire est débourré sur le client
}, options);
```
