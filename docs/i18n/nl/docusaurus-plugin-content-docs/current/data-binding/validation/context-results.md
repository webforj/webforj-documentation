---
sidebar_position: 4
title: Context Results
_i18n_hash: b86bc425ad8c1537e99a44fa34a93b3a
---
Wanneer je gegevens van de gebruikersinterface naar het model schrijft, activeert de `write`-methode van de `BindingContext` de validaties. De validatieresultaten bepalen of de gegevens acceptabel zijn.

## Verwerking van validatieresultaten {#processing-validation-results}

Je kunt validatieresultaten verwerken om feedback aan de gebruiker te geven. Als een validatie faalt, kun je de gegevensupdate in het model voorkomen en foutmeldingen weergeven die zijn gekoppeld aan elke mislukte validatie.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
  displayErrors(result.getMessages());
} else {
  proceedWithUpdate();
}
```

<!-- vale off -->
## Context Validatiestatus {#context-validation-state}
<!-- vale on -->

Wanneer de context de componenten valideert, genereert deze een `BindingContextValidateEvent`. Dit evenement levert de `ValidationResult` voor alle bindings die gelijktijdig zijn veranderd. Je kunt deze resultaten gebruiken om acties te triggeren en op gepaste wijze te reageren, bijvoorbeeld door de verzendknop in te schakelen of uit te schakelen op basis van de algehele geldigheid van het formulier.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Luister naar de BindingContextValidateEvent die wordt geactiveerd bij elke interactie van de gebruiker.
context.addValidateListener(event -> {
  submit.setEnabled(event.isValid());
});
```

## Auto-focus schending {#auto-focus-violation}

Bij formulieren die validatie over meerdere velden vereisen, kan het automatisch focussen op het eerste veld met een fout de gebruikerservaring aanzienlijk verbeteren. Deze functie helpt gebruikers meteen fouten te identificeren en te corrigeren, waardoor het invullen van het formulier efficiënt verloopt.

De `BindingContext` vereenvoudigt het proces van het instellen van auto-focus op de eerste component met een validatiefout. Door de `setAutoFocusFirstViolation`-methode te gebruiken, kun je deze functie inschakelen met minimale code, zodat de gebruikersinterface intuïtiever en responsiever wordt voor invoerfouten.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Focus Bewust
Deze functie werkt alleen voor de componenten die de `FocusAcceptorAware`-zorgen implementeren.
:::
