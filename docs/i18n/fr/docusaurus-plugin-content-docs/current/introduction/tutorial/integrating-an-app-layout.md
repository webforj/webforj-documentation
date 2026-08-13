---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: ddf62eb6d62a711c38f9ddaf9caeabad
---
Dans cette étape, vous rassemblerez toutes les parties de votre application dans une mise en page cohérente. À la fin de cette étape, la structure de votre application ressemblera étroitement à l'[archétype SideMenu](/docs/building-ui/archetypes/sidemenu), et vous aurez une meilleure compréhension du fonctionnement des composants et concepts suivants :

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) comme référence. Pour voir l'application en action :

1. Naviguez vers le répertoire de niveau supérieur contenant le fichier `pom.xml`, qui est `6-integrating-an-app-layout` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Création d'un composant réutilisable {#creating-a-reusable-component}

Dans une étape précédente, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), vous avez créé deux composants composites contenant le contenu de la table des clients et le formulaire du client. Dans le cadre de cette étape, vous créerez un composant composite plus petit et réutilisable pour afficher le nom de l'application dans le menu latéral et une page à propos. Si vous décidez de changer le nom de l'application à l'avenir, vous n'aurez qu'à le mettre à jour dans ce composant.

Dans `src/main/java/com/webforj/tutorial/components`, créez une classe appelée `AppTitle`. Le composant lié pour `AppTitle` sera un `FlexLayout`, un composant conteneur qui est utilisé tout au long de cette étape pour vous montrer comment créer des mises en page plus complexes. Pour ce `FlexLayout`, vous disposerez la direction des éléments et l'espacement entre les éléments. Cela se fait en utilisant respectivement les méthodes `setDirection()` et `setSpacing()`.

```java title='AppTitle.java'
// Faire du composant lié un FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Disposer les éléments verticalement
    self.setDirection(FlexDirection.COLUMN);

    // Définir l'écart entre les éléments
    self.setSpacing("0px");
  }
}
```

Ensuite, utilisez des éléments HTML standard pour créer le titre et le sous-titre. Définir la marge inférieure d'un élément d'en-tête à `0px` rapproche les éléments, et vous pouvez styliser le sous-titre en utilisant les [variables CSS DWC](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Gestionnaire de clients");
  private Paragraph subTitle = new Paragraph("Un système d'enregistrement simple");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Rendu optionnel {#optional-rendering}

Bien que `AppTitle` soit simple, ajouter un argument booléen au constructeur vous permet de contrôler quand rendre certaines parties du composant, comme le sous-titre.

```java title='AppTitle.java'
// Ajouter un argument booléen
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Ajouter le titre par défaut
      .add(title);

  // Afficher optionnellement le sous-titre
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### `AppTitle` complété {#completed-app-title}

Dans l'ensemble, le composant réutilisable devrait ressembler à ce qui suit :

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Gestionnaire de clients");
  private Paragraph subTitle = new Paragraph("Un système d'enregistrement simple");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Création d'une page à propos {#creating-an-about-page}

Le premier endroit où ajouter le nouveau composant `AppTitle` sera une page à propos. Cette page inclut une image et le composant `AppTitle`, centré sur la page à l'aide d'un autre `FlexLayout`.

### Centrer le contenu à l'aide d'un `FlexLayout` {#centering-content-using-a-flexlayout}

L'objectif est de centrer le contenu de la page à propos à l'aide du `FlexLayout`. Le composant `FlexLayout` suit le [modèle de mise en page flex CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Les méthodes pour le `FlexLayout`, comme celles utilisées précédemment pour orienter les éléments dans une colonne, sont différentes manières d'organiser les éléments.

Les méthodes d'organisation des éléments dans un `FlexLayout` utilisent un système directionnel relatif. Au lieu de penser aux axes horizontal et vertical, il est préférable de considérer l'axe parallèle aux éléments comme l'axe principal, et l'axe perpendiculaire aux éléments comme l'axe croisé.

Définir à la fois les propriétés `FlexJustifyContent` et `FlexAlignment` sur `CENTER` centrera les éléments le long des axes principal et croisé dans le `FlexLayout`, et faire en sorte que le `FlexLayout` occupe toute la taille de son conteneur parent le rend centré sur la page.

```java
private final FlexLayout layout = new FlexLayout();

// Remplit tout l'espace de l'élément parent
layout.setSize("100%", "100%");

// Faire de l'axe principal vertical
layout.setDirection(FlexDirection.COLUMN);

// Centrer les éléments le long de l'axe croisé
layout.setAlignment(FlexAlignment.CENTER);

// Centrer les éléments le long de l'axe principal
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Pour vous aider à visualiser comment fonctionnent les différentes méthodes, jetez un œil à l'article de blog [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Ajout de ressources {#adding-resources}

Un des éléments qui ira à l'intérieur du `FlexLayout` centré est une image. Pour ce tutoriel, vous pouvez consulter et télécharger [l'image de la page à propos](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) sur GitHub. Une fois téléchargée, ajoutez-la au dossier statique de votre projet dans `src/main/resources/static/images` et nommez-la `Files.svg`.

Mettre cette image dans le dossier statique vous permet de la référencer en utilisant le [protocole Webserver](/docs/managing-resources/assets-protocols#the-webserver-protocol). Ensuite, vous pouvez l'utiliser dans votre application en tant qu'élément HTML, comme ceci :

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Création de `AboutView` {#creating-about-view}

Comme les deux pages d'application existantes, la page à propos sera une vue routable. Dans `src/main/java/com/webforj/tutorial/views`, ajoutez une classe nommée `AboutView`. Utilisez un `FlexLayout` pour le composant lié, comme vous l'avez fait pour `AppTitle`.

Étant donné que vous avez nommé la classe `AboutView`, il n'est pas nécessaire de donner une valeur personnalisée pour le mappage URL ; cette page se rend à `http://localhost:8080/about` par défaut.

Voici ce à quoi cela ressemble lorsque vous utilisez les concepts des étapes précédentes avec les nouveaux composants créés pour créer une nouvelle vue avec un contenu centré :

```java title='AboutView.java'
@Route()
@FrameTitle("À propos")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## Création de la route `Layout` {#creating-the-layout-route}

Elle est brièvement mentionnée dans l'étape [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), mais il existe deux [types de routes](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` et `AboutView` sont toutes des routes `View`, tandis que le type de route que vous utiliserez pour créer le menu latéral de l'application est une route `Layout`.

Les routes de mise en page enveloppent les vues enfants et permettent à certaines parties de l'interface utilisateur de persister à travers les vues, comme un menu latéral. Dans `src/main/java/com/webforj/tutorial/layouts`, créez une classe appelée `MainLayout`.

### Route outlets {#route-outlets}

Comme les routes de vue, `MainLayout` a besoin d'une annotation `@Route`. Cependant, comme elle a `Layout` comme suffixe et que les routes de mise en page ne contribuent pas à l'URL, cette annotation n'a pas besoin d'arguments.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

L'application sait quelles vues rendre à l'intérieur de `MainLayout` en déclarant la classe de mise en page comme [route outlet](/docs/routing/route-hierarchy/route-outlets) dans chaque vue. Les étapes précédentes n'ont qu'une propriété `value` définie dans les annotations `@Route`, donc maintenant vous devrez indiquer explicitement quelles sont les propriétés `value` et `outlet` pour les classes de vue.

<!-- vale Google.Quotes = NO -->
<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>
<!-- vale Google.Quotes = YES -->

:::note Dernières touches
C'est la dernière modification requise pour `FormView` et `AboutView` dans cette étape, alors n'oubliez pas de mettre à jour l'annotation `@Route` pour ces vues avant d'exécuter votre application.
:::

## Utilisation du composant `AppLayout` {#using-the-app-layout-component}

Maintenant que votre application rend les vues à l'intérieur de `MainLayout`, vous pouvez choisir où ces composants se rendent. Choisir le `AppLayout` comme le composant lié pour `MainLayout` vous permet de stocker les vues dans une zone de contenu principale par défaut, tout en vous offrant également différentes zones pour ajouter des éléments pour l'en-tête et le menu latéral.

### Slots {#slots}

Pour de nombreux conteneurs WebforJ, l'utilisation des méthodes `add()` ajoute des composants d'interface utilisateur à la zone de contenu principale. Dans le composant `AppLayout`, il existe plusieurs zones pour ajouter des composants d'interface utilisateur, chacune dans un slot séparé. En marquant `MainLayout` comme une route de mise en page et en définissant son composant lié comme un `AppLayout`, les vues se rendent automatiquement dans le slot de contenu principal.

Dans cette étape, vous utiliserez les slots `drawer-title` et `drawer` pour créer un menu latéral, et le slot `header` pour afficher quelle page l'utilisateur est sur et un basculement pour le menu latéral.

### Création d'un menu latéral {#making-a-side-menu}

Lorsqu'il y a suffisamment d'espace sur l'écran de l'appareil, le composant `AppLayout` affiche un tiroir. C'est là que vous ajouterez à nouveau le `AppTitle` et des éléments qui permettront aux utilisateurs de naviguer dans l'application.

Par défaut, `AppLayout` ne montre pas d'en-tête de tiroir, mais en utilisant la méthode `setDrawerHeaderVisible()`, vous pouvez afficher les éléments qui se trouvent à l'intérieur du slot `drawer-title`, qui sera le `AppTitle` avec son sous-titre affiché.

```java
private AppLayout appLayout = new AppLayout();

// Afficher l'en-tête du tiroir
appLayout.setDrawerHeaderVisible(true);

// Ajouter le AppTitle à l'en-tête du tiroir avec son sous-titre
appLayout.addToDrawerTitle(new AppTitle(true));
```

Le slot `drawer` devrait ensuite contenir les composants qui permettent aux utilisateurs de naviguer dans l'application. L'utilisation du composant [`AppNav`](/docs/components/appnav) facilite la création de nouvelles options de navigation. Pour chaque lien, il vous suffit de créer un `AppNavItem`. Les composants `AppNavItem` dans ce tutoriel utilisent trois paramètres :

- L'étiquette pour le lien
- La vue cible
- Un composant [`Icon`](/docs/components/icon) optionnel, utilisant des images de [Tabler](https://tabler.io/icons)

Regrouper tous les paramètres du tiroir dans `MainLayout` ressemble à ce qui suit :

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Tableau de bord", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("À propos", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Création d'un en-tête {#making-a-header}

Le slot `header` doit inclure deux éléments : un basculement pour montrer ou cacher le menu latéral et un moyen d'afficher le titre de la fenêtre. Ces deux éléments seront à l'intérieur d'un composant [Toolbar](/docs/components/toolbar), une autre façon d'organiser les composants.

Vous pouvez inclure le basculement pour le tiroir `AppLayout` avec le composant `AppDrawerToggle`. Ce composant est déjà stylé avec une icône couramment utilisée pour les options de menu cachées, et cible le tiroir pour l'ouvrir et le fermer.

```java
// Créer les composants conteneurs
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Ajouter la Toolbar à l'en-tête d'AppLayout
appLayout.addToHeader(toolbar);

// Ajouter le AppDrawerToggle à la barre d'outils
toolbar.addToStart(new AppDrawerToggle());
```

L'en-tête peut également afficher le titre de la fenêtre en utilisant l'événement de navigation pour récupérer des détails sur le composant entrant, tout en ayant un écouteur d'événements pour supprimer l'enregistrement afin de prévenir les fuites de mémoire.

```java
// Créer l'élément H1 et l'enregistrement de navigation
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Enregistrer l'événement lors de la navigation
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Supprimer les écouteurs avant que MainLayout soit détruit
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Récupérer le titre de la fenêtre de la classe de vue entrante
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## `MainLayout` complété {#completed-mainlayout}

Voici `MainLayout` avec le contenu créé pour le tiroir et l'en-tête à l'intérieur d'un `AppLayout` :

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
  public class MainLayout extends Composite<AppLayout> {
    private AppLayout self = getBoundComponent();
    private H1 title = new H1("");
    private ListenerRegistration<NavigateEvent> navigateRegistration;
    private Toolbar toolbar = new Toolbar();
    private AppNav appNav = new AppNav();

    public MainLayout() {
      setHeader();
      setDrawer();
      navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
    }

    private void setHeader() {
      self.addToHeader(toolbar);

      toolbar.addToStart(new AppDrawerToggle());
      toolbar.addToTitle(title);
    }

    private void setDrawer() {
      self.setDrawerHeaderVisible(true)
          .addToDrawerTitle(new AppTitle(true));

      appNav.addItem(new AppNavItem("Tableau de bord", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("À propos", AboutView.class,
          TablerIcon.create("info-circle")));
      self.addToDrawer(appNav);
    }

    @Override
    protected void onDidDestroy() {
      if (navigateRegistration != null) {
        navigateRegistration.remove();
      }
    }

    private void onNavigate(NavigateEvent ev) {
      Component component = ev.getContext().getComponent();
      if (component != null) {
        FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
        title.setText(frameTitle != null ? frameTitle.value() : "");
      }
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Mise à jour de `FormView` {#updating-form-view}

Comme mentionné précédemment, le seul changement apporté à `FormView` était l'annotation `@Route`.

```java
@Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
```

## Mise à jour de `MainView` {#updating-main-view}

Pour `MainView`, vous changerez le composant lié d'un `Div` à un `FlexLayout`. Cela vous permet de centrer la table, tout en déplaçant certains composants à l'intérieur de la mise en page. L'utilisation de la méthode `setItemAlignment()` vous permet de choisir un composant dans la mise en page et de le déplacer, afin de garder la table centrée tout en ancrant le bouton d'ajout de client en haut à droite de la mise en page.

```java
// Changer le composant lié en un FlexLayout
private FlexLayout self = getBoundComponent();

// Aligner le bouton à la fin de l'axe croisé
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Une autre amélioration que vous pouvez apporter ici est la largeur de la table. Au lieu d'une largeur fixe, vous pouvez la définir pour correspondre à son conteneur parent, le `FlexLayout`. Ensuite, ce `FlexLayout` peut avoir une largeur maximale afin de ne pas trop s'étirer sur les écrans plus grands.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

En mettant ces éléments ensemble et en créant une autre méthode pour centrer le `FlexLayout` comme dans les étapes précédentes, cela fait de `MainView` les changements mis en évidence :

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Tableau des clients")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Ajouter un client", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      // highlight-next-line
      setFlexLayout();
      // highlight-next-line
      self.add(addCustomer, table);
      // highlight-next-line
      self.setItemAlignment(FlexAlignment.END, addCustomer);
    }

    private void buildTable() {
      // highlight-next-line
      table.setSize("100%", "294px");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Prénom");
      table.addColumn("lastName", Customer::getLastName).setLabel("Nom");
      table.addColumn("company", Customer::getCompany).setLabel("Société");
      table.addColumn("country", Customer::getCountry).setLabel("Pays");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
      table.setKeyProvider(Customer::getId);
      table.addItemClickListener(this::editCustomer);
    }

    // highlight-next-line
    private void setFlexLayout() {
      // highlight-next-line
      self.setSize("100%", "100%")
          // highlight-next-line
          .setMargin("auto")
          // highlight-next-line
          .setMaxWidth(2000)
          // highlight-next-line
          .setDirection(FlexDirection.COLUMN)
          // highlight-next-line
          .setAlignment(FlexAlignment.CENTER);
          // highlight-next-line
    }

    private void editCustomer(TableItemClickEvent<Customer> e) {
      Router.getCurrent().navigate(FormView.class,
          ParametersBag.of("id=" + e.getItemKey()));
    }
  }
`}
</ExpandableCode>
<!-- vale on -->
