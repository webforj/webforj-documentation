---
sidebar_position: 4
title: Context Results
_i18n_hash: f7eeb60ff21b1d5dff27b17cc82cdf50
---
Lorsque vous écrivez des données de l'interface utilisateur vers le modèle, la méthode `write` du `BindingContext` déclenche les validations. Les résultats de la validation déterminent si les données sont acceptables.

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

Chaque fois que le contexte valide les composants, il déclenche un `BindingContextValidateEvent`. Cet événement transmet le `ValidationResult` pour tous les bindings qui ont changé simultanément. Vous pouvez utiliser ces résultats pour déclencher des actions et répondre de manière appropriée, par exemple en activant ou désactivant le bouton de soumission en fonction de la validité générale du formulaire.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Écoutez le BindingContextValidateEvent qui est déclenché à chaque interaction de l'utilisateur.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Violation de focus automatique {#auto-focus-violation}

Lorsqu'il s'agit de formulaires nécessitant une validation sur plusieurs champs, le fait de focaliser automatiquement le premier champ présentant une erreur peut considérablement améliorer l'expérience utilisateur. Cette fonctionnalité aide les utilisateurs à identifier et corriger immédiatement les erreurs, simplifiant ainsi le processus de complétion du formulaire.

Le `BindingContext` simplifie le processus de configuration du focus automatique sur le premier composant avec une erreur de validation. En utilisant la méthode `setAutoFocusFirstViolation`, vous pouvez activer cette fonctionnalité avec un code minimal, garantissant que l'interface utilisateur devient plus intuitive et réactive aux erreurs de saisie.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Prise de conscience du focus
Cette fonctionnalité fonctionne uniquement pour les composants qui implémentent le souci `FocusAcceptorAware`.
:::
