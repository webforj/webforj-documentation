---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: bb0d88755455ff4e639e598c104b6d68
---
Votre application depuis [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) peut utiliser `FormView` pour modifier les données des clients existants. Cette étape utilise [Data binding](/docs/data-binding/overview), qui connecte directement les composants de l'UI au modèle de données pour une synchronisation automatique des valeurs. Cela réduit le code répétitif dans votre application et vous permet d'ajouter des vérifications de validation à l'entité Spring `Customer`, permettant à vos utilisateurs de fournir des informations complètes et précises lors du remplissage des formulaires. Cette étape couvre les concepts suivants :

- [Jakarta validation](https://beanvalidation.org)
- Utilisation de la classe [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)

Terminer cette étape crée une version de [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) comme point de comparaison. Pour voir l'application en action :

1. Naviguez jusqu'au répertoire de haut niveau contenant le fichier `pom.xml`, qui est `5-validating-and-binding-data` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement:
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Définition des règles de validation {#defining-validation-rules}

Développer une application avec des données modifiables doit inclure la validation. Les vérifications de validation aident à maintenir des données soumises par les utilisateurs qui sont significatives et précises. Si elles sont laissées sans surveillance, cela pourrait entraîner des problèmes, il est donc important de détecter les types d'erreurs que les utilisateurs peuvent commettre lors du remplissage d'un formulaire en temps réel.

Puisque ce qui est considéré comme valide peut différer entre les propriétés, vous devrez définir ce qui rend chaque propriété valide et informer l'utilisateur s'il y a quelque chose d'invalide. Fort heureusement, vous pouvez facilement le faire avec [Jakarta Validation](https://beanvalidation.org). La validation Jakarta vous permet d'ajouter des contraintes aux propriétés sous forme d'annotations.

Ce tutoriel utilise deux annotations Jakarta, `@NotEmpty` et `@Pattern`. `@NotEmpty` vérifie la nullité et les chaînes vides, tandis que `@Pattern` vérifie si la propriété correspond à une expression régulière que vous définissez. Les deux annotations vous permettent d'ajouter un message à afficher lorsque la propriété devient invalide.

Pour exiger que les prénoms et noms de famille soient obligatoires et contiennent uniquement des lettres, tout en rendant le nom de l'entreprise optionnel et en permettant lettres, chiffres et espaces, appliquez les annotations suivantes à l'entité `Customer` :

```java
@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le prénom du client est requis")
    @Pattern(regexp = "[a-zA-Z]*", message = "Caractères invalides")
    private String firstName = "";

    @NotEmpty(message = "Le nom de famille du client est requis")
    @Pattern(regexp = "[a-zA-Z]*", message = "Caractères invalides")
    private String lastName = "";

    @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Caractères invalides")
    private String company = "";

    private Country country = Country.UNKNOWN;

    public enum Country {
      UNKNOWN,
      GERMANY,
      ENGLAND,
      ITALY,
      USA
    }

    public Customer(String firstName, String lastName, String company, Country country) {
      setFirstName(firstName);
      setLastName(lastName);
      setCompany(company);
      setCountry(country);
    }

    public Customer(String firstName, String lastName, String company) {
      this(firstName, lastName, company, Country.UNKNOWN);
    }

    public Customer(String firstName, String lastName) {
      this(firstName, lastName, "");
    }

    public Customer(String firstName) {
      this(firstName, "");
    }

    public Customer() {
    }

    public void setFirstName(String newName) {
      firstName = newName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setLastName(String newName) {
      lastName = newName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setCompany(String newCompany) {
      company = newCompany;
    }

    public String getCompany() {
      return company;
    }

    public void setCountry(Country newCountry) {
      country = newCountry;
    }

    public Country getCountry() {
      return country;
    }

    public Long getId() {
      return id;
    }

  }
```

Consultez la [référence des contraintes de validation Jakarta Bean](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) pour une liste complète des validations, ou apprenez-en plus dans l'[article sur la validation Jakarta de webforJ](/docs/data-binding/validation/jakarta-validation).

## Liaison des champs {#binding-the-fields}

Pour utiliser les vérifications de validation dans `Customer` pour l'UI dans `FormView`, vous allez créer un `BindingContext` pour la liaison des données. Avant la liaison des données, chaque champ dans `FormView` nécessitait un écouteur d'événements pour se synchroniser manuellement avec une entité Spring `Customer`. La création d'un `BindingContext` dans `FormView` lie et synchronise automatiquement le modèle de données `Customer` avec les composants de l'UI.

### Création d'un `BindingContext` {#creating-a-bindingcontext}

Une instance de `BindingContext` a besoin du bean Spring avec lequel les liaisons sont synchronisées. Dans `FormView`, déclarez un `BindingContext` en utilisant l'entité `Customer` :

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Ensuite, pour lier automatiquement les composants de l'UI aux propriétés du bean en fonction de leurs noms, utilisez `BindingContext.of()` avec les paramètres suivants :

- **`this`** : Auparavant, vous avez déclaré `context` comme étant le `BindingContext`. Le premier paramètre définit quel objet contient les composants liables.
- **`Customer.class`** : Le deuxième paramètre est la classe du bean à utiliser pour la liaison.
- **`true`** : Le troisième paramètre active la validation Jakarta, permettant au contexte d'utiliser les validations que vous avez définies pour `Customer`. Cela changera le style des composants invalides et affichera les messages définis.

Dans l'ensemble, cela ressemblera à la ligne de code suivante :

```java
context = BindingContext.of(this, Customer.class, true);
```

### Rendre le formulaire réactif {#making-the-form-responsive}

Avec la liaison des données, votre application effectue désormais automatiquement des vérifications de validation. En ajoutant un écouteur d’événements aux vérifications, vous pouvez empêcher les utilisateurs de soumettre un formulaire invalide. Ajoutez ce qui suit pour rendre le bouton d'envoi actif uniquement lorsque le formulaire est valide :

```java
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Suppression des écouteurs d'événements pour les composants {#removing-event-listeners-for-components}

Chaque changement d'UI est maintenant automatiquement synchronisé avec le `BindingContext`. Cela signifie que vous pouvez maintenant supprimer les écouteurs d'événements de chaque champ :

**Avant**
```java
// Sans liaison des données
TextField firstName = new TextField("Prénom", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Nom de famille", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Société", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Pays",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Après**
```java
// Avec liaison des données
TextField firstName = new TextField("Prénom");
TextField lastName = new TextField("Nom de famille");
TextField company = new TextField("Société");
ChoiceBox country = new ChoiceBox("Pays");
```

### Liaison par noms de propriété {#binding-by-property-names}

Puisque chaque nom de composant correspondait au modèle de données, webforJ a appliqué [Automatic Binding](/docs/data-binding/automatic-binding). Si les noms ne correspondaient pas, vous pouviez utiliser l'annotation `@UseProperty` pour les mapper.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Prénom");
```

### Lecture des données dans la méthode `fillForm()` {#reading-data-in-the-fillForm()-method}

Auparavant, dans la méthode `fillForm()`, vous initialisiez la valeur de chaque composant en récupérant manuellement les données de la copie de `Customer`. Mais maintenant, puisque vous utilisez un `BindingContext`, vous pouvez utiliser la méthode `read()`. Cette méthode remplit chaque composant lié avec la propriété associée provenant des données dans la copie de `Customer`.

Dans la méthode `fillForm()`, remplacez les méthodes `setValue()` par `read()` :

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Suppression de chaque méthode setValue() pour les composants UI
    
  context.read(customer);
}
```

### Ajout de validation à `submitCustomer()` {#adding-validation-to-submitcustomer}

Le dernier changement à `FormView` pour cette étape consistera à ajouter une protection à la méthode `submitCustomer()`. Avant de valider les modifications dans la base de données H2, l'application effectuera une dernière validation sur les résultats du contexte lié en utilisant la méthode `write()`.

La méthode `write()` met à jour les propriétés d'un bean en utilisant les composants UI liés dans le `BindingContext` et renvoie un `ValidationResult`.

Utilisez la méthode `write()` pour écrire dans la copie de `Customer` en utilisant les composants liés dans `FormView`. Ensuite, si le `ValidationResult` retourné est valide, mettez à jour la base de données H2 en utilisant les données écrites.

```java
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### `FormView` complété

Avec ces changements, voici à quoi ressemble `FormView`. L'application prend désormais en charge la liaison de données et la validation en utilisant Spring Boot et webforJ. Les entrées de formulaire sont automatiquement synchronisées avec le modèle et vérifiées selon les règles de validation.

## Prochaine étape {#next-step}

La prochaine étape, [Intégration d'une mise en page d'application](/docs/introduction/tutorial/integrating-an-app-layout), se concentre sur l'utilisation d'un `AppLayout` pour ajouter un menu latéral accessible aux utilisateurs à la fois sur les pages du tableau des clients et du formulaire des clients. Vous découvrirez également un autre outil de mise en page, le composant `FlexLayout`.
