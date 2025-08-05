---
sidebar_position: 5
title: Triggers
_i18n_hash: a52300a9683a08701c4e1f1f6150dd9f
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Par défaut, les liaisons revalident automatiquement les composants lorsque les utilisateurs modifient leurs données, comme entrer un nouveau texte, cocher une case à cocher ou sélectionner une nouvelle option dans un bouton radio. Si vous préférez désactiver les validations automatiques et les signaler uniquement lors de l'écriture dans le modèle de données, vous pouvez configurer la liaison pour les désactiver. Cela vous donne le contrôle sur le moment et la manière dont les validations se produisent, vous permettant de gérer les validations en fonction des besoins spécifiques de l'application ou des interactions de l'utilisateur.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Message personnalisé pour une adresse e-mail invalide"))
    .autoValidate(false)
    .add();
```

Il est également possible de désactiver les auto-validations pour tout le contexte.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Mode de Changement de Valeur
Certains composants, comme les composants de champ, implémentent l'interface `ValueChangeModeAware`, qui vous permet de contrôler quand le système signale un `ValueChangeEvent`. Par exemple, vous pouvez configurer les composants de champ pour signaler les changements de valeur uniquement lors du flou. Cette configuration réduit la fréquence des validations, optimisant les performances et améliorant l'expérience utilisateur en se concentrant sur les validations aux moments où l'utilisateur termine une session de saisie, plutôt que pendant la saisie active.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Revalidation {#revalidation}

Bien que les validations soient généralement déclenchées automatiquement lors de l'écriture de données, vous pouvez également les invoquer manuellement pour vérifier l'état des données sans tenter de les écrire dans le modèle. Cette approche manuelle est particulièrement utile dans des scénarios où vous souhaitez activer ou désactiver des fonctionnalités en fonction de la validité des données du formulaire sans effectuer de mise à jour.

Considérons un exemple classique d'un Sélecteur de Dates de Voyage, où un utilisateur doit sélectionner deux dates : la date de début et la date de fin d'un voyage. Il n'est pas valide de choisir une date de fin qui se produit avant la date de début, ou une date de début qui se produit après la date de fin. Vous pouvez résoudre ces dépendances en déclenchant manuellement les validations :

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Date de Début");
  DateTimeField endDateField = new DateTimeField("Date de Fin");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "La date de début est requise")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "La date de début doit être avant la date de fin")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "La date de fin est requise")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "La date de fin doit être après la date de début")
        .add();

    startDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    startDateField.addValueChangeListener(event -> {
      startDate = event.getValue();
      context.getBinding("endDate").validate();
    });

    endDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    endDateField.addValueChangeListener(event -> {
      endDate = event.getValue();
      context.getBinding("startDate").validate();
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Trip" label="Trip.java">

```java showLineNumbers
public class Trip {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }
}
```

</TabItem>
</Tabs>
