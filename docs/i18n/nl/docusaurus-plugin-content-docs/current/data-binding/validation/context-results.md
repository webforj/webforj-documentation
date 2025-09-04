---
sidebar_position: 4
title: Context Results
_i18n_hash: f7eeb60ff21b1d5dff27b17cc82cdf50
---
Wanneer u gegevens vanuit de gebruikersinterface naar het model schrijft, wordt de `write`-methode van de `BindingContext` geactiveerd en worden de validaties uitgevoerd. De validatieresultaten bepalen of de gegevens acceptabel zijn.

## Verwerken van validatieresultaten {#processing-validation-results}

U kunt validatieresultaten verwerken om feedback aan de gebruiker te geven. Als een validatie faalt, kunt u de gegevensupdate in het model voorkomen en foutmeldingen weergeven die aan elke mislukte validatie zijn gekoppeld.

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

Telkens wanneer de context de componenten valideert, genereert deze een `BindingContextValidateEvent`. Dit evenement levert de `ValidationResult` voor alle bindings die tegelijkertijd zijn veranderd. U kunt deze resultaten gebruiken om acties te activeren en gepast te reageren, zoals het in- of uitschakelen van de verzendknop op basis van de algemene geldigheid van het formulier.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Luister naar de BindingContextValidateEvent die wordt geactiveerd bij elke interactie van de gebruiker.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Auto-focus schending {#auto-focus-violation}

Bij formulieren die validatie vereisen over meerdere velden, kan het automatisch focussen op het eerste veld met een fout de gebruikerservaring aanzienlijk verbeteren. Deze functie helpt gebruikers om onmiddellijk fouten te identificeren en te corrigeren, waardoor het invullen van het formulier efficiënter verloopt.

De `BindingContext` vereenvoudigt het proces van het instellen van auto-focus op de eerste component met een validatiefout. Door de `setAutoFocusFirstViolation`-methode te gebruiken, kunt u deze functie met minimale code inschakelen, zodat de gebruikersinterface intuïtiever en responsiever wordt bij invoerfouten.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Focus Erkend
Deze functie werkt alleen voor de componenten die de `FocusAcceptorAware`-zorgen implementeren.
:::
