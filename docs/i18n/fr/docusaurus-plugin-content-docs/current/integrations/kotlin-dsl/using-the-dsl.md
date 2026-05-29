---
title: Using the DSL
sidebar_position: 10
_i18n_hash: 05d1319dd97f2d32392408b2e4ae9058
---
La DSL Kotlin fournit des fonctions de construction pour les composants webforJ. Chaque fonction crée un composant, l'ajoute à un conteneur parent et exécute un bloc de configuration. Cette page couvre les motifs et conventions que vous utiliserez lors de la construction d'interfaces utilisateur avec la DSL.

## Conventions de nommage {#naming-conventions}

Des fonctions DSL sont fournies pour tous les composants standard webforJ, y compris les boutons, les champs, les mises en page, les dialogues, les tiroirs, les listes et les éléments HTML. Chaque fonction utilise le nom de la classe du composant en **camelCase**. `Button` devient `button()`, `TextField` devient `textField()`, et `FlexLayout` devient `flexLayout()`.

```kotlin
div {
  button("Cliquez moi")
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

Créez un composant en ajoutant sa fonction DSL à un bloc parent, ainsi que les arguments optionnels et le bloc de configuration, comme montré ci-dessous :

```kotlin
div {
  // Crée un bouton, l'ajoute à ce div, puis exécute le bloc
  button("Soumettre") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Lorsque vous utilisez la fonction DSL d'un composant, cela crée le composant, l'ajoute au parent et exécute ensuite le bloc de configuration. Le bloc de configuration reçoit le composant comme récepteur (`this`), vous pouvez donc accéder aux propriétés et méthodes directement :

```kotlin
textField("Email") {
  placeholder = "vous@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Imbrication de composants {#nesting-components}

Les composants qui peuvent contenir des enfants acceptent des appels DSL imbriqués à l'intérieur de leur bloc :

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("Tableau de bord")

  div {
    paragraph("Bienvenue de nouveau !")
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

La DSL impose une bonne portée. Vous ne pouvez ajouter des enfants qu'aux composants qui les prennent en charge, et le compilateur empêche les références accidentelles aux portées externes :

```kotlin
div {
  button("Soumettre") {
    // Cela ressemble à ce qu'il ajoute un paragraphe à l'intérieur du bouton,
    // mais en réalité, cela l'ajouterait au div extérieur.
    // La DSL détecte cette erreur au moment de la compilation.
    paragraph("Soumission...") // Ne compilera pas
  }
}
```

Si vous avez besoin d'ajouter à une portée extérieure, utilisez `this` étiqueté pour rendre l'intention explicite :

```kotlin
div {
  button("Soumettre") {
    this@div.add(Paragraph("Soumission..."))  // Explicite autorisé
  }
}
```

Cela maintient le code UI prévisible en rendant les sauts de portée visibles.

## Style des composants {#styling-components}

La DSL Kotlin fournit une propriété d'extension `styles` qui donne un accès par crochet semblable à une carte aux propriétés CSS, équivalente à `setStyle()` et `getStyle()` en Java :

```kotlin
button("Stylé") {
  styles["background-color"] = "#007bff"
  styles["color"] = "white"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[Cours CSS]
Pour les styles réutilisables, ajoutez des classes CSS au lieu de styles en ligne. L'extension `HasClassName` permet d'ajouter des noms de classe avec `+=` :

```kotlin
button("Action Principale") {
  classNames += "btn-primary"
}
```
:::

## Gestion des événements {#event-handling}

Les composants doivent presque toujours répondre à l'interaction de l'utilisateur. La DSL fournit une syntaxe concise pour les écouteurs d'événements utilisant des méthodes avec préfixe `on` qui acceptent des lambdas :

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
// Paramètre texte pour les étiquettes/contenu
button("Cliquez moi")
h1("Titre de la page")
paragraph("Texte du corps")

// Étiquette et espace réservé pour les champs
textField("Nom d'utilisateur", placeholder = "Entrez le nom d'utilisateur")
passwordField("Mot de passe", placeholder = "Entrez le mot de passe")

// Paramètres de valeur pour les entrées
numberField("Quantité", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Arguments avec noms spécifiés
Les arguments nommés vous permettent de passer des paramètres dans n'importe quel ordre, indépendamment de leur apparence dans la signature de la fonction.
:::

## Construction d'une vue complète {#building-a-complete-view}

Avec ces motifs en main, voici un formulaire complet qui les rassemble :

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  private val self = boundComponent

  init {
    self.apply {
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

      val messageField = textArea("Message", placeholder = "Comment pouvons-nous vous aider ?") {
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
    // Gérer l'envoi du formulaire
  }
}
```

La DSL garde la structure de l'UI lisible tout en vous donnant un accès complet à la configuration des composants.
