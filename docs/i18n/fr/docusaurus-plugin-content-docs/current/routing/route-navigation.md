---
sidebar_position: 4
title: Route Navigation
_i18n_hash: cf1f9e79aa81f240306313a7c0c5a9c4
---
Dans webforJ, naviguer entre les routes est le mécanisme central pour changer les vues et les composants en fonction des actions de l'utilisateur ou des changements d'URL. La navigation permet aux utilisateurs de se déplacer sans interruption entre différentes parties de l'application sans actualiser la page. Cette navigation côté client maintient l'application réactive et fluide tout en préservant l'état de l'application.

## Navigation programmatique {#programmatic-navigation}

Vous pouvez déclencher la navigation depuis n'importe où dans votre application en utilisant la classe `Router`. Cela permet des changements dynamiques dans les composants affichés en fonction d'événements tels que des clics de bouton ou d'autres interactions utilisateur.

Voici un exemple de la façon de naviguer vers une route spécifique :

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Logique du composant ici
}
```

```java
// naviguer vers la vue
Router.getCurrent().navigate(DashboardView.class);
```

Dans cet exemple, naviguer vers le composant `DashboardView` de manière programmatique  entraîne le rendu du composant `DashboardView` et la mise à jour de l'URL du navigateur à `/dashboard`.

Il est également possible de naviguer vers la vue en passant un nouvel `Location`.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Classe vs. Location : Méthodes pour le routage de vue
Lors de la navigation entre les vues, les développeurs ont deux options : ils peuvent soit passer la classe de la vue ou route, permettant au routeur de générer automatiquement l'URL et de rendre la vue, soit passer la localisation directement. Les deux méthodes sont valides, mais **utiliser la classe de vue est l'approche préférée** car elle offre une meilleure flexibilité pour les changements futurs. Par exemple, si vous décidez plus tard de mettre à jour la route, vous n'avez qu'à modifier l'annotation `@Route`, sans avoir à changer de code utilisant la classe de vue pour la navigation.
:::

### Navigation avec paramètres {#navigation-with-parameters}

Lorsque vous devez passer des paramètres avec la route, webforJ vous permet d'incorporer des paramètres dans l'URL. Voici comment naviguer vers une route avec des paramètres :

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Inconnu");
    setTile(id);
  }
}
```

```java
// naviguer vers la vue et passer l'identifiant utilisateur
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Cela navigue vers `/user/JohnDoe`, où `JohnDoe` peut représenter un identifiant utilisateur. Le composant pour cette route peut ensuite extraire le paramètre et l'utiliser en conséquence.

## Instance de vue créée {#created-view-instance}

La méthode `navigate` accepte un `Consumer` Java qui est invoqué une fois la navigation terminée. Le `Consumer` reçoit l'instance du composant de vue créé, enveloppée dans un `Optional` Java, permettant au développeur d'interagir avec la vue après une navigation réussie.

```java
Router.getCurrent().navigate(
    UserProfileView.class,
    ParametersBag.of("id=JohnDoe"), (component) -> {
      component.ifPresent(view -> {
        console().log("Le nouveau titre est : " + view.getTitle());
      });
    });
```

:::info Instances null
Le consommateur reçoit un `Optional` Java pour le composant car il peut être `null`, ou non créé pour diverses raisons. Par exemple, le composant peut ne pas être rendu si les observateurs de navigation mettent leur veto à la navigation et empêchent le processus.
:::

## Options de navigation {#navigation-options}

La classe `NavigationOptions` permet aux développeurs d'affiner la manière dont la navigation est gérée dans l'application. En définissant des options spécifiques, vous pouvez contrôler le comportement de la navigation, comme savoir si l'historique du navigateur doit être mis à jour, invoquer des observateurs du cycle de vie, ou même déclencher des événements de navigation.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Définir des options de navigation {#setting-navigation-options}

La classe `NavigationOptions` fournit plusieurs méthodes pour personnaliser le comportement de navigation. Celles-ci incluent le contrôle de la manière dont les routes sont gérées, si les observateurs sont notifiés, et comment l'historique du navigateur est mis à jour.

Voici les principales options de configuration disponibles dans `NavigationOptions` :

1. **Type de navigation (`setNavigationType`)**  
   Cette option définit si la nouvelle route doit être ajoutée à l'historique du navigateur ou remplacer la route actuelle.

   - **`PUSH`** : Ajoute la nouvelle route à la pile d'historique, préservant la localisation actuelle.
   - **`REPLACE`** : Remplace la route actuelle dans la pile d'historique par la nouvelle localisation, empêchant le bouton de retour de naviguer vers la route précédente.

2. **Déclencher des événements (`setFireEvents`)**  
   Détermine si les [événements du cycle de vie](./navigation-lifecycle/navigation-events) de navigation doivent être déclenchés pendant la navigation. Par défaut, cela est réglé sur `true`, et les événements sont déclenchés. Si réglé sur `false`, aucun événement ne sera déclenché, ce qui est utile pour une navigation silencieuse.

3. **Invoquer des observateurs (`setInvokeObservers`)**  
   Ce drapeau contrôle si la navigation doit déclencher des [observateurs](./navigation-lifecycle/observers) au sein des composants navigués. Les observateurs traitent généralement des événements comme l'entrée ou la sortie de la route. Réglage sur `false` empêche les observateurs d'être invoqués.

4. **Mettre à jour l'historique (`setUpdateHistory`)**  
   Lorsqu'il est réglé sur `false`, cette option empêche la localisation de l'historique d'être mise à jour. Cela est utile lorsque vous souhaitez changer la vue sans affecter la navigation arrière ou avant du navigateur. Cela n'affecte que la gestion de l'historique, pas le cycle de vie des composants ou la gestion des routes.

5. **Objet d'état (`setState`)**  
   [L'objet d'état](./state-management#saving-and-restoring-state-in-browser-history) vous permet de passer des informations supplémentaires lors de la mise à jour de l'historique du navigateur. Cet objet est stocké dans l'état de l'historique du navigateur et peut être utilisé plus tard à des fins personnalisées, comme sauvegarder l'état de l'application pendant la navigation.

## Génération de localisations pour les vues {#generating-locations-for-views}

Le routeur peut générer la localisation pour les vues en fonction du modèle de route défini dans la vue. Vous pouvez également fournir des paramètres supplémentaires pour les segments dynamiques et requis dans l'URL. Cela peut être utile lors de la construction de liens ou du partage de points d'accès directs vers des vues spécifiques dans l'application.

Voici comment générer un `Location` basé sur une classe de vue et des paramètres de route :

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Cela génère un objet `Location` avec le chemin `/user/JohnDoe`, l'URI complet sous forme de chaîne.
