---
title: Validating and Binding Data
sidebar_position: 5
pagination_next: null
_i18n_hash: 11d03e09c4c37172713713649c920e9e
---
La liaison de données est un mécanisme qui connecte directement les composants de l'interface utilisateur de votre application au modèle de données sous-jacent, permettant une synchronisation automatique des valeurs entre les deux. Cela élimine le besoin d'appels répétitifs de getters et de setters, réduisant ainsi le temps de développement et améliorant la fiabilité du code.

La validation, dans ce contexte, garantit que les données saisies dans le formulaire respectent des règles préétablies, comme être non vides ou suivre un format spécifique. En combinant la liaison de données avec la validation, vous pouvez simplifier l'expérience utilisateur tout en maintenant l'intégrité des données sans avoir à écrire d'importants contrôles manuels.

Pour plus d'informations sur la liaison de données, consultez [cet article.](../../data-binding/overview) Pour exécuter l'application :

- Allez dans le répertoire `4-validating-and-binding-data`
- Exécutez la commande `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/validating-and-binding-data.mp4" type="video/mp4"/>
  </video>
</div>

### Liaison des champs {#binding-the-fields}

La configuration de la liaison de données commence par l'initialisation d'un `BindingContext` pour le modèle `Customer`. Le `BindingContext` lie les propriétés du modèle aux champs du formulaire, permettant une synchronisation automatique des données. Cela est configuré dans le constructeur de `FormView`.

```java title="FormView.java"
BindingContext<Customer> context;
context = BindingContext.of(this, Customer.class, true);
```

`BindingContext.of(this, Customer.class, true)` initialise le contexte de liaison pour la classe `Customer`. Le troisième paramètre, `true`, active [la validation jakarta](https://beanvalidation.org/).

:::info
Cette implémentation utilise l'auto-liaison comme décrit dans l'[article sur la liaison de données](../../data-binding/automatic-binding). Cela fonctionne si les champs dans le modèle de données `Customer` sont nommés de la même manière que les champs correspondants dans le `FormView`.

Si les champs ne sont pas nommés de la même manière, vous pouvez ajouter l'annotation `UseProperty` dans le formulaire au-dessus du champ que vous souhaitez lier afin qu'ils soient informés des champs de données auxquels se référer.
:::

### Liaison de données avec `onDidEnter()` {#data-binding-with-ondidenter}

La méthode `onDidEnter` exploite la configuration de la liaison de données pour simplifier le processus de peuplement des champs du formulaire. Au lieu de définir manuellement des valeurs pour chaque champ, les données sont maintenant synchronisées automatiquement avec le `BindingContext`.

```java {7}
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresent(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      customerId = id;
    });
    context.read(customer);
  }
```

La méthode `context.read` dans le système de liaison de données de webforJ synchronise les champs d'un composant UI avec les valeurs d'un modèle de données. Elle est utilisée dans ce cas pour peupler les champs de formulaire avec des données provenant d'un modèle existant, garantissant que l'interface utilisateur reflète l'état actuel des données.

## Validation des données {#validating-data}

La validation garantit que les données saisies dans le formulaire respectent des règles spécifiques, améliorant ainsi la qualité des données et empêchant les soumissions invalides. Avec la liaison de données, la validation n'a plus besoin d'être mise en œuvre manuellement mais peut être simplement configurée, permettant un retour d'information en temps réel sur les entrées de l'utilisateur.

### Définition des règles de validation {#defining-validation-rules}

À l'aide de [Jakarta](https://beanvalidation.org) et d'expressions régulières, vous pouvez imposer une multitude de règles sur un champ. Les exemples couramment utilisés seraient de s'assurer que le champ n'est pas vide ou nul, ou qu'il suit un certain modèle. Grâce aux annotations dans la classe customer, vous pouvez donner des paramètres de validation jakarta au champ.

:::info
Plus de détails concernant la configuration de la validation sont disponibles [ici](../../data-binding/validation/jakarta-validation.md#installation).
:::

```java
  @NotEmpty(message = "Le nom ne peut pas être vide")
  @Pattern(regexp = "[a-zA-Z]*", message = "Caractères invalides")
  private String firstName = "";
```

La méthode `onValidate` est ensuite ajoutée pour contrôler l'état du bouton `Submit` en fonction de la validité des champs du formulaire. Cela garantit que seules des données valides peuvent être soumises.

```java title="FormView.java"
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

`e.isValid()` renvoie true si tous les champs sont valides, et false sinon. Cela signifie que le bouton `Submit` est activé tant que tous les champs sont valides. Sinon, il reste désactivé, empêchant la soumission jusqu'à ce que des corrections soient apportées.

### Ajout et édition d'entrées avec validation {#adding-and-editing-entries-with-validation}

La méthode `submitCustomer()` valide désormais les données à l'aide du `BindingContext` avant d'effectuer des opérations d'ajout ou de modification. Cette approche élimine le besoin de contrôles de validation manuels, tirant parti des mécanismes intégrés du contexte pour garantir que seules des données valides sont traitées.

- **Mode Ajout** : Si aucun `id` n'est fourni, le formulaire est en mode ajout. Les données validées sont écrites dans le modèle `Customer` et ajoutées au référentiel via `Service.getCurrent().addCustomer(customer)`.
- **Mode Édition** : Si un `id` est présent, la méthode récupère les données du client correspondant, les met à jour avec des entrées validées et engage les modifications dans le référentiel.

L'appel à `context.write(customer)` renverra une instance de `ValidationResult`. Cette classe indique si la validation a été réussie ou non, et stocke tout message associé à ce résultat.

Ce code garantit que toutes les modifications sont validées et automatiquement appliquées au modèle avant d'ajouter un nouveau client ou de modifier un client existant.

```java title="FormView.java"
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerId.isEmpty()) {
      Service.getCurrent().addCustomer(customer);
    }
    Router.getCurrent().navigate(DemoView.class);
  }
}
```

En complétant cette étape, l'application prend désormais en charge la liaison de données et la validation, garantissant que les entrées de formulaire sont synchronisées avec le modèle et respectent les règles préétablies.
