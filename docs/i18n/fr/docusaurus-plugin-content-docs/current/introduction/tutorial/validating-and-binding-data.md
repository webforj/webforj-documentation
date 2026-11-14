---
title: Validation et liaison des données
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
Votre application issue de [Observateurs et paramètres de route](/docs/introduction/tutorial/observers-and-route-parameters) peut utiliser `FormView` pour modifier les données d’un client existant. Cette étape utilise la [liaison de données](/docs/data-binding/overview), qui connecte directement les composants de l’interface utilisateur au modèle de données afin de synchroniser automatiquement les valeurs. Cela réduit le code répétitif de votre application et vous permet d’ajouter des règles de validation à l’entité Spring `Customer`, afin que vos utilisateurs fournissent des informations complètes et exactes lorsqu’ils remplissent les formulaires. Cette étape aborde les concepts suivants :

- [Validation Jakarta](https://beanvalidation.org)
- Utilisation de la classe [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html)

Une fois cette étape terminée, vous aurez créé une version de [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## Exécuter l’application {#running-the-app}

Pendant le développement de votre application, vous pouvez vous servir de [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) comme référence. Pour voir l’application en action :

1. Accédez au répertoire de premier niveau qui contient le fichier `pom.xml` ; si vous suivez la version disponible sur GitHub, il s’agit de `5-validating-and-binding-data`.

2. Utilisez la commande Maven suivante pour exécuter l’application Spring Boot localement :
    ```bash
    mvn
    ```

L’exécution de l’application ouvre automatiquement une nouvelle fenêtre du navigateur à l’adresse `http://localhost:8080`.

## Définir des règles de validation {#defining-validation-rules}

Une application qui permet de modifier des données doit inclure une validation. Les vérifications de validation contribuent à préserver la pertinence et l’exactitude des données soumises par les utilisateurs. Sans vérification, des problèmes peuvent survenir. Il est donc important de repérer en temps réel les erreurs que les utilisateurs peuvent commettre lorsqu’ils remplissent un formulaire.

Comme les critères de validité peuvent varier d’une propriété à l’autre, vous devez définir ce qui rend chaque propriété valide et informer l’utilisateur lorsqu’une valeur ne l’est pas. Heureusement, vous pouvez facilement le faire avec [Jakarta Validation](https://beanvalidation.org). Jakarta Validation vous permet d’ajouter des contraintes aux propriétés sous forme d’annotations.

Ce tutoriel utilise deux annotations Jakarta, `@NotEmpty` et `@Pattern`. `@NotEmpty` vérifie que la valeur n’est ni nulle ni vide, tandis que `@Pattern` vérifie que la propriété correspond à une expression régulière que vous avez définie. Les deux annotations permettent d’ajouter un message à afficher lorsque la propriété devient invalide.

Pour exiger que le prénom et le nom soient obligatoires et ne contiennent que des lettres, tout en rendant le nom de l’entreprise facultatif et en autorisant les lettres, les chiffres et les espaces, appliquez les annotations suivantes à l’entité `Customer` :

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
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

</ExpandableCode>
<!-- vale on -->

Consultez la [référence des contraintes Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) pour obtenir la liste complète des validations, ou découvrez-en davantage dans l’[article webforJ sur Jakarta Validation](/docs/data-binding/validation/jakarta-validation).

## Lier les champs {#binding-the-fields}

Pour utiliser dans l’interface de `FormView` les vérifications de validation définies dans `Customer`, vous allez créer un `BindingContext` pour la liaison de données. Avant la mise en place de cette liaison, chaque champ de `FormView` nécessitait un écouteur d’événement pour synchroniser manuellement ses données avec l’entité Spring `Customer`. La création d’un `BindingContext` dans `FormView` lie le modèle de données `Customer` aux composants de l’interface et les synchronise automatiquement.

### Créer un `BindingContext` {#creating-a-bindingcontext}

Une instance de `BindingContext` a besoin du bean Spring avec lequel les liaisons sont synchronisées. Dans `FormView`, déclarez un `BindingContext` utilisant l’entité `Customer` :

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Ensuite, pour lier automatiquement les composants de l’interface aux propriétés du bean en fonction de leurs noms, utilisez `BindingContext.of()` avec les paramètres suivants :

- **`this`** : vous avez précédemment déclaré `context` en tant que `BindingContext`. Le premier paramètre indique l’objet qui contient les composants pouvant être liés.
- **`Customer.class`** : le deuxième paramètre correspond à la classe du bean à utiliser pour la liaison.
- **`true`** : le troisième paramètre active Jakarta Validation, ce qui permet au contexte d’utiliser les validations définies pour `Customer`. Cela modifie le style des composants non valides et affiche les messages définis.

L’ensemble tient dans la ligne de code suivante :

```java
context = BindingContext.of(this, Customer.class, true);
```

### Adapter le formulaire à l’état de validation {#making-the-form-responsive}

Grâce à la liaison de données, votre application effectue désormais automatiquement les vérifications de validation. En ajoutant un écouteur d’événement pour ces vérifications, vous pouvez empêcher les utilisateurs d’envoyer un formulaire invalide. Ajoutez le code suivant pour n’activer le bouton d’envoi que lorsque le formulaire est valide :

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Supprimer les écouteurs d’événements des composants {#removing-event-listeners-for-components}

Chaque modification de l’interface utilisateur est désormais automatiquement synchronisée avec le `BindingContext`. Vous pouvez donc supprimer les écouteurs d’événements de chaque champ :

**Avant**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Après**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Lier les propriétés par leur nom {#binding-by-property-names}

Comme le nom de chaque composant correspondait à celui du modèle de données, webforJ a appliqué la [liaison automatique](/docs/data-binding/automatic-binding). Si les noms ne correspondaient pas, vous pourriez utiliser l’annotation `@UseProperty` pour les associer.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Lire les données dans la méthode `fillForm()` {#reading-data-in-the-fillForm()-method}

Auparavant, dans la méthode `fillForm()`, vous initialisiez la valeur de chaque composant en récupérant manuellement les données depuis la copie de `Customer`. Maintenant que vous utilisez un `BindingContext`, vous pouvez appeler la méthode `read()`. Cette méthode renseigne chaque composant lié avec la propriété correspondante des données de la copie de `Customer`.

Dans la méthode `fillForm()`, remplacez les méthodes `setValue()` par `read()` :

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### Ajouter la validation à `submitCustomer()` {#adding-validation-to-submitcustomer}

La dernière modification à apporter à `FormView` pour cette étape consiste à ajouter une protection à la méthode `submitCustomer()`. Avant d’enregistrer les modifications dans la base de données H2, l’application effectue une validation finale des résultats du contexte lié à l’aide de la méthode `write()`.

La méthode `write()` met à jour les propriétés d’un bean à partir des composants de l’interface liés dans le `BindingContext` et renvoie un `ValidationResult`.

Utilisez la méthode `write()` pour mettre à jour la copie de `Customer` à partir des composants liés dans `FormView`. Ensuite, si le `ValidationResult` renvoyé est valide, mettez à jour la base de données H2 à l’aide des données enregistrées.


```java title="FormView.java" {2-3}
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

### `FormView` une fois terminé {#completed-formview}

Voici à quoi ressemble `FormView` après ces modifications. L’application prend désormais en charge la liaison de données et la validation avec Spring Boot et webforJ. Les champs du formulaire sont automatiquement synchronisés avec le modèle et vérifiés selon les règles de validation.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));
    fillCountries();
    setColumnsLayout();
    self.setMaxWidth(600)
        .addClassName("card")
        .add(layout);
    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");
  }

  private void setColumnsLayout() {
    List<Breakpoint> breakpoints = List.of(
        new Breakpoint(600, 2));
    layout.setSpacing("var(--dwc-space-l)")
        .setBreakpoints(breakpoints);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

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

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(id -> {
      customerId = Long.valueOf(id);
      if (customerService.doesCustomerExist(customerId)) {
        event.accept();
        fillForm(customerId);
      } else {
        event.reject();
        navigateToMain();
      }

    }, () -> event.accept());
  }

  public void fillForm(Long customerId) {
    customer = customerService.getCustomerByKey(customerId);
    context.read(customer);
  }
}
```

</ExpandableCode>
<!-- vale on -->

## Étape suivante {#next-step}

L’étape suivante, [Intégrer une mise en page d’application](/docs/introduction/tutorial/integrating-an-app-layout), explique comment utiliser un `AppLayout` pour ajouter un menu latéral accessible aux utilisateurs depuis les pages du tableau des clients et du formulaire client. Vous découvrirez également un autre outil de mise en page, le composant `FlexLayout`.
