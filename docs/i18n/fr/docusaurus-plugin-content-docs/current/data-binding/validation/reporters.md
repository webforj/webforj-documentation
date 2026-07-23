---
sidebar_position: 3
title: Reporters
description: >-
  Surface validation outcomes through the DefaultBindingReporter or attach
  custom reporters to bindings with the useReporter callback.
_i18n_hash: e642fa150e90534cdaef8bb0955d4ff0
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reporters are used for providing feedback about the validation process to the user interface. This feature is essential for informing users about the results of their input validation, particularly in complex forms or data-intensive applications.

## Qu'est-ce qu'un reporter de validation ? {#whats-a-validation-reporter}

Un reporter de validation est un composant qui traite et affiche les résultats des validations aux utilisateurs. Il agit comme un pont entre la logique de validation et l'interface utilisateur, assurant que les résultats de validation sont communiqués de manière efficace et claire.

:::tip Composants principaux Reporter par défaut
webforJ inclut le `DefaultBindingReporter`, un reporter de liaisons par défaut conçu pour fonctionner de manière transparente avec tous les composants principaux de webforJ. Ce reporter intégré affiche automatiquement les erreurs de validation, éliminant ainsi le besoin d'une implémentation personnalisée dans de nombreux cas. Selon la configuration du composant, le `DefaultBindingReporter` affiche les erreurs de validation directement sous forme de popover ou en ligne, juste en dessous du composant. Cette fonctionnalité simplifie considérablement le processus de reporting des erreurs, garantissant une communication claire et directe des erreurs de validation, et améliore l'expérience utilisateur en fournissant des retours immédiats et contextuels sur la validation des entrées.
:::

## Configuration des reporters de validation {#configuring-validation-reporters}

Vous pouvez configurer des reporters de validation dans le contexte de liaison pour personnaliser la manière dont les messages sont présentés. En général, vous mettriez en œuvre un reporter de validation pour agréger les résultats de validation et les afficher ensuite de manière conviviale, par exemple en surlignant les champs incorrects, en affichant des messages d'erreur ou en mettant à jour les indicateurs de statut.

Voici un exemple de configuration d'un reporter de validation pour un champ

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@StyleSheet("ws://css/styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Adresse e-mail");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Message personnalisé pour adresse e-mail invalide"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

Dans le code ci-dessus, la liaison de l'e-mail incorpore un reporter personnalisé qui affiche directement les messages de validation sous le champ d'entrée. Cette configuration utilise la méthode `useReporter`, qui configure la manière dont la liaison gère et présente les résultats de validation. Cette méthode lie efficacement la logique de validation à l'interface utilisateur, garantissant que tout problème de validation est immédiatement visible par l'utilisateur, améliorant ainsi l'interactivité et l'expérience utilisateur du formulaire.
