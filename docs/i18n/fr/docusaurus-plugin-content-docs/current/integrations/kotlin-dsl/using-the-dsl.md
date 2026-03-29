---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
Le Kotlin DSL fournit des fonctions de constructeur pour les composants webforJ. Chaque fonction crée un composant, l'ajoute à un conteneur parent et exécute un bloc de configuration. Cette page couvre les motifs et conventions que vous utiliserez lors de la création d'interfaces utilisateur avec le DSL.

## Conventions de nommage {#naming-conventions}

Des fonctions DSL sont fournies pour tous les composants standard de webforJ, y compris les boutons, les champs, les mises en page, les boîtes de dialogue, les tiroirs, les listes et les éléments HTML. Chaque fonction utilise le nom de la classe du composant en **camelCase**. `Button` devient `button()`, `TextField` devient `textField()`, et `FlexLayout` devient `flexLayout()`.

```kotlin
div {
  button("Cliquez-moi")
  textField("Nom d'utilisateur")
  flexLayout {
    // contenu imbriqué
  }
}
```

:::important Utilisation du composant `Break`
Une exception : `Break` utilise des backticks car `break` est un mot-clé Kotlin :

```kotlin
div {
  span("Ligne un")
  `break`()
  span("Ligne deux")
}
```
:::

## Création de composants {#creating-components}

Créez un composant en ajoutant sa fonction DSL à un bloc parent, avec les arguments optionnels et le bloc de configuration, comme montré ci-dessous :

```kotlin
div {
  // Crée un Button, l'ajoute à ce div, puis exécute le bloc
  button("Soumettre") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Lorsque vous utilisez la fonction DSL d'un composant, elle crée le composant, l'ajoute au parent, puis exécute le bloc de configuration. Le bloc de configuration reçoit le composant comme son récepteur (`this`), vous pouvez donc accéder aux propriétés et méthodes directement :

```kotlin
textField("Email") {
  placeholder = "vous@example.com"   // this.placeholder
  required = true                    // this.required
  onModify { validate() }            // this.onModify(...)
}
```

## Emboîtement de composants {#nesting-components}

Les composants pouvant contenir des enfants acceptent des appels DSL imbriqués à l'intérieur de leur bloc :

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("Tableau de bord")

  div {
    paragraph("Bienvenue à nouveau !")
    button("Voir les rapports")
  }

  flexLayout {
    direction = FlexDirection.ROW
    button("Paramètres")
    button("Déconnexion")
  }
}
```

### Sécurité de portée {#scope-safety}

Le DSL impose une bonne portée. Vous ne pouvez ajouter des enfants qu'aux composants qui les prennent en charge, et le compilateur empêche les références accidentelles aux portées extérieures :

```kotlin
div {
  button("Soumettre") {
    // Cela ressemble à ce qu'il ajoute un paragraphe à l'intérieur du bouton,
    // mais en réalité, cela l'ajouterait au div extérieur.
    // Le DSL attrape cette erreur à la compilation.
    paragraph("Soumission...") // Ne compilera pas
  }
}
```

Si vous devez ajouter à une portée extérieure, utilisez `this` label pour rendre l'intention explicite :

```kotlin
div {
  button("Soumettre") {
    this@div.add(Paragraph("Soumission..."))  // L'explicite est autorisé
  }
}
```

Cela garde le code UI prévisible en rendant visibles les sauts de portée.

## Stylisation des composants {#styling-components}

Le Kotlin DSL fournit une propriété d'extension `styles` qui donne un accès de type map à des propriétés CSS, équivalent à `setStyle()` et `getStyle()` en Java :

```kotlin
button("Stylé") {
  styles["background-color"] = "#007bff"
  styles["color"] = "white"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[Classes CSS]
Pour des styles réutilisables, ajoutez des classes CSS au lieu de styles en ligne. L'extension `HasClassName` permet d'ajouter des noms de classe avec `+=` :

```kotlin
button("Action principale") {
  classNames += "btn-primary"
}
```
:::

## Gestion des événements {#event-handling}

Les composants ont presque toujours besoin de répondre à l'interaction de l'utilisateur. Le DSL fournit une syntaxe concise pour les écouteurs d'événements utilisant des méthodes avec le préfixe `on` qui acceptent des lambdas :

```kotlin
button("Sauvegarder") {
  onClick {
    saveData()
    showNotification("Sauvegardé !")
  }
}

textField("Recherche") {
  onModify { event ->
    performSearch(event.text)
  }
}
```

## Paramètres communs {#common-parameters}

En plus des blocs de configuration, la plupart des fonctions DSL acceptent également des paramètres communs avant le bloc pour des options fréquemment utilisées :

```kotlin
// Paramètre de texte pour les étiquettes/contenu
button("Cliquez-moi")
h1("Titre de la page")
paragraph("Texte du corps")

// Étiquette et placeholder pour les champs
textField("Nom d'utilisateur", placeholder = "Entrez le nom d'utilisateur")
passwordField("Mot de passe", placeholder = "Entrez le mot de passe")

// Paramètres de valeur pour les entrées
numberField("Quantité", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Arguments avec noms spécifiés
Les arguments nommés vous permettent de passer des paramètres dans n'importe quel ordre, indépendamment de leur apparition dans la signature de la fonction.
:::

## Construction d'une vue complète {#building-a-complete-view}

Avec ces motifs en main, voici un formulaire complet qui les rassemble :

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  init {
    boundComponent.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Contactez-nous")

      val nameField = textField("Nom", placeholder = "Votre nom") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("Email", placeholder = "vous@example.com") {
        styles["width"] = "100%"
      }

      val messageField = textArea("Message", placeholder = "Comment pouvons-nous aider ?") {
        styles["width"] = "100%"
      }

      button("Envoyer le message") {
        theme = ButtonTheme.PRIMARY
        styles["width"] = "100%"

        onClick {
          submitForm(
            name = nameField.text,
            email = emailField.text,
            message = messageField.text
          )
        }
      }
    }
  }

  private fun submitForm(name: String, email: String, message: String) {
    // Gérer la soumission du formulaire
  }
}
```

Le DSL garde la structure de l'UI lisible tout en vous donnant un accès complet à la configuration des composants.
