---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
Le DSL Kotlin fournit des fonctions de construction pour les composants webforJ. Chaque fonction crée un composant, l'ajoute à un conteneur parent et exécute un bloc de configuration. Cette page couvre les motifs et conventions que vous utiliserez lors de la construction d'interfaces utilisateur avec le DSL.

## Conventions de nommage {#naming-conventions}

Des fonctions DSL sont fournies pour tous les composants standard webforJ, y compris les boutons, les champs, les mises en page, les dialogues, les tiroirs, les listes et les éléments HTML. Chaque fonction utilise le nom de la classe du composant en **camelCase**. `Button` devient `button()`, `TextField` devient `textField()`, et `FlexLayout` devient `flexLayout()`.

```kotlin
div {
    button("Cliquez-moi")
    textField("Nom d'utilisateur")
    flexLayout {
        // contenu imbriqué
    }
}
```
:::important Méthodes `Header` et `Footer`
Les méthodes DSL `header` et `footer` ont été renommées en `nativeHeader` et `nativeFooter` pour éviter les conflits avec les slots d'en-tête et de pied de page d'autres composants.
:::

:::important Utilisation du composant `Break`
Une exception : `Break` utilise des accents graves car `break` est un mot-clé Kotlin :

```kotlin
div {
    span("Ligne un")
    `break`()
    span("Ligne deux")
}
```
:::

## Création de composants {#creating-components}

Créez un composant en ajoutant sa fonction DSL à un bloc parent, avec les arguments et le bloc de configuration optionnels, comme montré ci-dessous :

```kotlin
div {
    // Crée un bouton, l'ajoute à ce div, puis exécute le bloc
    button("Soumettre") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

Lorsque vous utilisez la fonction DSL d'un composant, celle-ci crée le composant, l'ajoute au parent, puis exécute le bloc de configuration.
Le bloc de configuration reçoit le composant comme son récepteur (`this`), vous pouvez donc accéder directement aux propriétés et méthodes :

```kotlin
textField("Email") {
    placeholder = "vous@example.com"   // this.placeholder
    required = true                   // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## Imbrication des composants {#nesting-components}

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

Le DSL impose une portée appropriée. Vous ne pouvez ajouter des enfants qu'aux composants qui les prennent en charge, et le compilateur empêche les références accidentelles à des portées extérieures :

```kotlin
div {
    button("Soumettre") {
        // Cela ressemble à ce qu'il ajoute un paragraphe à l'intérieur du bouton,
        // mais il l'ajouterait en fait au div extérieur.
        // Le DSL attrape cette erreur au moment de la compilation.
        paragraph("Soumission en cours...") // Ne compile pas
    }
}
```

Si vous avez besoin d'ajouter à une portée extérieure, utilisez `this` étiqueté pour rendre l'intention explicite :

```kotlin
div {
    button("Soumettre") {
        this@div.add(Paragraph("Soumission en cours..."))  // L'explicite est autorisé
    }
}
```

Cela garde le code de l'UI prévisible en rendant les sauts de portée visibles.

## Style des composants {#styling-components}

Le DSL Kotlin fournit une propriété d'extension `styles` qui donne un accès par indice semblable à une carte aux propriétés CSS, équivalent à `setStyle()` et `getStyle()` en Java :

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
button("Action Principale") {
    classNames += "btn-primary"
}
```
:::

## Gestion des événements {#event-handling}

Les composants ont presque toujours besoin de répondre à l'interaction de l'utilisateur. Le DSL fournit une syntaxe concisée d'écouteur d'événements utilisant les méthodes avec le préfixe `on` qui acceptent des lambdas :

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

En plus des blocs de configuration, la plupart des fonctions DSL acceptent également des paramètres communs avant le bloc pour les options fréquemment utilisées :

```kotlin
// Paramètre texte pour les étiquettes/contenu
button("Cliquez-moi")
h1("Titre de la page")
paragraph("Texte du corps")

// Étiquettes et espaces réservés pour les champs
textField("Nom d'utilisateur", placeholder = "Entrez le nom d'utilisateur")
passwordField("Mot de passe", placeholder = "Entrez le mot de passe")

// Paramètres de valeur pour les entrées
numberField("Quantité", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip Arguments avec noms spécifiés
Les arguments nommés vous permettent de passer des paramètres dans n'importe quel ordre, peu importe comment ils apparaissent dans la signature de la fonction.
:::

## Construction d'une vue complète {#building-a-complete-view}

Avec ces motifs en main, voici un formulaire complet qui les regroupe :

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
        // Gérer la soumission du formulaire
    }
}
```

Le DSL maintient la structure de l'UI lisible tout en vous donnant un accès complet à la configuration des composants.
