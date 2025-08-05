---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
Lorsque vous écrivez des données de l'interface utilisateur vers le modèle, la méthode `write` du `BindingContext` déclenche les validations. Les résultats de validation déterminent si les données sont acceptables.

## Traitement des résultats de validation {#processing-validation-results}

Vous pouvez traiter les résultats de validation pour fournir un retour d'information à l'utilisateur. Si une validation échoue, vous pouvez empêcher la mise à jour des données dans le modèle et afficher des messages d'erreur associés à chaque validation échouée.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## État de validation du contexte {#context-validation-state}
<!-- vale on -->

Chaque fois que le contexte valide les composants, il déclenche un `BindingContextValidateEvent`. Cet événement livre le `ValidationResult` pour tous les liaisons qui ont changé simultanément. Vous pouvez utiliser ces résultats pour déclencher des actions et réagir de manière appropriée, comme activer ou désactiver le bouton de soumission en fonction de la validité globale du formulaire.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Écoutez le BindingContextValidateEvent qui est déclenché à chaque interaction de l'utilisateur.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Violation de mise au point automatique {#auto-focus-violation}

Lorsqu'il s'agit de formulaires nécessitant une validation sur plusieurs champs, le fait de mettre automatiquement au point le premier champ avec une erreur peut considérablement améliorer l'expérience utilisateur. Cette fonctionnalité aide les utilisateurs à identifier et à corriger immédiatement les erreurs, simplifiant ainsi le processus de remplissage du formulaire.

Le `BindingContext` simplifie le processus de mise en place de la mise au point automatique sur le premier composant présentant une erreur de validation. En utilisant la méthode `setAutoFocusFirstViolation`, vous pouvez activer cette fonctionnalité avec un code minimal, garantissant que l'interface utilisateur devient plus intuitive et réactive aux erreurs de saisie.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Sensible au focus
Cette fonctionnalité ne fonctionne que pour les composants qui implémentent la préoccupation `FocusAcceptorAware`.
:::
