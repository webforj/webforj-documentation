---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
Le Kotlin DSL est extensible, permettant l'ajout de fonctions DSL pour des composants personnalisés ou des bibliothèques tierces. Vous pouvez créer des composants composites qui utilisent le DSL en interne.

## Ajouter des composants au DSL {#adding-components-to-the-dsl}

Pour rendre un composant disponible dans le DSL, créez une fonction d'extension sur `HasComponents` qui utilise la fonction d'aide `init`.

### Fonction DSL de base {#basic-dsl-function}

Voici le modèle pour un composant simple. Cet exemple utilise un composant `StarRating` personnalisé :

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.example.component.StarRating

fun @WebforjDsl HasComponents.starRating(
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  return init(StarRating(), block)
}
```

La fonction `init` fait trois choses :
1. Ajoute le composant au conteneur parent
2. Exécute le bloc de configuration
3. Renvoie le composant configuré

Vous pouvez maintenant utiliser le composant dans le code DSL :

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### Ajouter des paramètres {#adding-parameters}

La plupart des fonctions DSL acceptent des paramètres communs avant le bloc de configuration :

```kotlin
fun @WebforjDsl HasComponents.starRating(
  value: Int? = null,
  max: Int? = null,
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  val rating = StarRating()
  value?.let { rating.value = it }
  max?.let { rating.max = it }
  return init(rating, block)
}
```

L'utilisation devient plus concise :

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## Création de composants composites {#creating-composite-components}

Un `Composite` regroupe plusieurs composants en une seule unité réutilisable. Le DSL fonctionne bien pour définir la structure composite.

### Composite de base {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Recherche...") {
        styles["flex"] = "1"
      }

      searchButton = button("Rechercher") {
        theme = ButtonTheme.PRIMARY
      }
    }
  }

  fun onSearch(handler: (String) -> Unit) {
    searchButton.onClick {
      handler(searchField.text)
    }
    searchField.onEnter {
      handler(searchField.text)
    }
  }
}
```

Le composite expose des références de composants pour un accès externe et fournit des méthodes pratiques pour des opérations courantes.

### Ajouter un support DSL {#adding-dsl-support}

Créez une fonction DSL afin que le composite puisse être utilisé comme des composants intégrés :

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

Il s'intègre maintenant naturellement :

```kotlin
div {
  h1("Catalogue de Produits")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // Liste des produits...
}
```

### Exemples : Indicateur de statut {#example-status-indicator}

Voici un exemple complet d'un composite d'indicateur de statut :

```kotlin
class StatusIndicator : Composite<Div>() {

  private val self = boundComponent
  private val dot: Div
  private val label: Span

  var status: Status = Status.INACTIVE
    set(value) {
      field = value
      updateDisplay()
    }

  var text: String = ""
    set(value) {
      field = value
      label.text = value
    }

  init {
    self.apply {
      styles["display"] = "flex"
      styles["align-items"] = "center"
      styles["gap"] = "8px"

      dot = div {
        styles["width"] = "10px"
        styles["height"] = "10px"
        styles["border-radius"] = "50%"
        styles["background"] = "gray"
      }

      label = span()
    }
    updateDisplay()
  }

  private fun updateDisplay() {
    dot.styles["background"] = when (status) {
      Status.ACTIVE -> "#22c55e"
      Status.WARNING -> "#f59e0b"
      Status.ERROR -> "#ef4444"
      Status.INACTIVE -> "#9ca3af"
    }
  }

  enum class Status { ACTIVE, WARNING, ERROR, INACTIVE }
}

// Fonction DSL
fun @WebforjDsl HasComponents.statusIndicator(
  text: String? = null,
  status: StatusIndicator.Status? = null,
  block: @WebforjDsl StatusIndicator.() -> Unit = {}
): StatusIndicator {
  val indicator = StatusIndicator()
  text?.let { indicator.text = it }
  status?.let { indicator.status = it }
  return init(indicator, block)
}
```

Utilisation :

```kotlin
div {
  statusIndicator("Base de données", StatusIndicator.Status.ACTIVE)
  statusIndicator("Cache", StatusIndicator.Status.WARNING)
  statusIndicator("API Externe", StatusIndicator.Status.ERROR)
}
```
