---
sidebar_position: 3
title: Reporters
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---
Les rapporteurs de validation sont utilisés pour fournir des retours d'information sur le processus de validation à l'interface utilisateur. Cette fonctionnalité est essentielle pour informer les utilisateurs des résultats de leur validation d'entrée, en particulier dans des formulaires complexes ou des applications intensives en données.

## Qu'est-ce qu'un rapporteur de validation ? {#whats-a-validation-reporter}

Un rapporteur de validation est un composant qui traite et affiche les résultats des validations aux utilisateurs. Il sert de lien entre la logique de validation et l'interface utilisateur, garantissant que les résultats de validation sont communiqués de manière efficace et claire.

:::tip Composants de base Rapporteur par défaut
webforJ inclut le `DefaultBindingReporter`, un rapporteur de liaison par défaut conçu pour fonctionner sans problème avec tous les composants de base de webforJ. Ce rapporteur intégré affiche automatiquement les erreurs de validation, éliminant ainsi la nécessité d'implémentations personnalisées dans de nombreux cas. En fonction de la configuration du composant, le `DefaultBindingReporter` affiche les erreurs de validation directement sous forme de popover ou en ligne, juste en dessous du composant. Cette fonctionnalité simplifie considérablement le processus de signalement des erreurs, garantissant une communication claire et directe des erreurs de validation, et améliore l'expérience utilisateur en fournissant un retour immédiat et contextuel sur la validation des entrées.
:::

## Configuration des rapporteurs de validation {#configuring-validation-reporters}

Vous pouvez configurer les rapporteurs de validation dans le contexte de liaison pour personnaliser la manière dont les messages sont présentés. En général, vous implémenteriez un rapporteur de validation pour agréger les résultats de validation et les afficher de manière conviviale, comme en mettant en évidence les champs incorrects, en affichant des messages d'erreur ou en mettant à jour des indicateurs de statut.

Voici un exemple de la façon de configurer un rapporteur de validation pour un champ

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
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
            Validator.from(new EmailValidator(), "Message personnalisé pour l'adresse e-mail invalide"))
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

Dans le code ci-dessus, la liaison de l'e-mail incorpore un rapporteur personnalisé qui affiche directement les messages de validation sous le champ de saisie. Cette configuration utilise la méthode `useReporter`, qui configure la manière dont la liaison gère et présente les résultats de validation. Cette méthode relie efficacement la logique de validation à l'interface utilisateur, garantissant que tout problème de validation est immédiatement visible pour l'utilisateur, améliorant l'interactivité du formulaire et l'expérience utilisateur.
