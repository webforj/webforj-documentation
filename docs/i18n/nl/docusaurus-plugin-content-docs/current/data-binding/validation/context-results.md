---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
Wanneer je gegevens van de gebruikersinterface naar het model schrijft, activeert de `write`-methode van de `BindingContext` de validaties. De validatieresultaten bepalen of de gegevens acceptabel zijn.

## Verwerken van validatieresultaten {#processing-validation-results}

Je kunt validatieresultaten verwerken om feedback aan de gebruiker te geven. Als een validatie faalt, kun je de gegevensupdate in het model voorkomen en foutmeldingen weergeven die verband houden met elke mislukte validatie.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Context Validatietoestand {#context-validation-state}
<!-- vale on -->

Telkens wanneer de context de componenten valideert, wordt er een `BindingContextValidateEvent` geactiveerd. Dit evenement levert de `ValidationResult` voor alle bindingen die simultaan zijn veranderd. Je kunt deze resultaten gebruiken om acties te triggeren en dienovereenkomstig te reageren, zoals het in- of uitschakelen van de verzendknop op basis van de algehele geldigheid van het formulier.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Luister naar de BindingContextValidateEvent die wordt geactiveerd bij elke gebruikersinteractie.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Autofocus-schending {#auto-focus-violation}

Bij het werken met formulieren die validatie over meerdere velden vereisen, kan het automatisch focussen op het eerste veld met een fout de gebruikerservaring aanzienlijk verbeteren. Deze functie helpt gebruikers onmiddellijk fouten te identificeren en te corrigeren, waardoor het invullen van het formulier wordt gestroomlijnd.

De `BindingContext` vereenvoudigt het proces van het instellen van autofocus op de eerste component met een validatiefout. Door de `setAutoFocusFirstViolation`-methode te gebruiken, kun je deze functie inschakelen met minimale code, waardoor de gebruikersinterface intuïtiever en responsiever wordt voor invoerfouten.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Focus Bewust
Deze functie werkt alleen voor de componenten die de `FocusAcceptorAware`-zorgen implementeren.
:::
