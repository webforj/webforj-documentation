---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
Dans webforJ, la navigation entre les routes est le mécanisme central pour changer de vues et de composants en fonction des actions des utilisateurs ou des modifications d'URL. La navigation permet aux utilisateurs de se déplacer de manière fluide entre différentes parties de l'application sans rafraîchir la page. Cette navigation côté client garde l'application réactive et fluide tout en préservant l'état de l'application.

## Navigation programmatique {#programmatic-navigation}

Vous pouvez déclencher la navigation depuis n'importe où dans votre application en utilisant la classe `Router`. Cela permet des changements dynamiques des composants affichés en fonction d'événements tels que des clics sur des boutons ou d'autres interactions des utilisateurs.

Voici un exemple de comment naviguer vers une route spécifique :

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

Dans cet exemple, naviguer vers le composant `DashboardView` de manière programmatique entraîne le rendu du composant `DashboardView` et la mise à jour de l'URL du navigateur à `/dashboard`.

Il est également possible de naviguer vers la vue en passant une nouvelle `Location`.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Classe vs. Location : Méthodes pour le routage des vues
Lors de la navigation entre les vues, les développeurs ont deux options : ils peuvent soit passer la vue ou la classe de route, permettant au routeur de générer automatiquement l'URL et de rendre la vue, soit passer la location directement. Les deux méthodes sont valides, mais **l'utilisation de la classe de vue est l'approche préférée** car elle offre une meilleure flexibilité pour les changements futurs. Par exemple, si vous décidez ultérieurement de mettre à jour la route, vous n'aurez qu'à modifier l'annotation `@Route`, sans avoir à changer de code qui utilise la classe de vue pour la navigation.
:::

### Navigation avec des paramètres {#navigation-with-parameters}

Lorsque vous devez passer des paramètres avec la route, webforJ vous permet d'intégrer des paramètres dans l'URL. Voici comment vous pouvez naviguer vers une route avec des paramètres :

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
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
// naviguer vers la vue et passer l'ID utilisateur
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Cela navigue vers `/user/JohnDoe`, où `JohnDoe` pourrait représenter un ID utilisateur. Le composant pour cette route peut ensuite extraire le paramètre et l'utiliser en conséquence.

## Instance de vue créée {#created-view-instance}

La méthode `navigate` accepte un `Consumer` Java qui est invoqué une fois la navigation terminée. Le `Consumer` reçoit l'instance du composant de vue créée, enveloppée dans un `Optional` Java, permettant au développeur d'interagir avec la vue après une navigation réussie.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("Le nouveau titre est : " + view.getTitle());
    });
  });
```

:::info Instances nulles
Le consommateur reçoit un `Optional` Java pour le composant car il pourrait être `null`, ou non créé pour diverses raisons. Par exemple, le composant peut ne pas être rendu si les observateurs de navigation refusent la navigation et arrêtent le processus.
:::

## Options de navigation {#navigation-options}

La classe `NavigationOptions` permet aux développeurs de peaufiner la manière dont la navigation est gérée dans l'application. En définissant des options spécifiques, vous pouvez contrôler le comportement de la navigation, comme si vous souhaitez mettre à jour l'historique du navigateur, invoquer des observateurs de cycle de vie, ou même déclencher des événements de navigation.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Définir des options de navigation {#setting-navigation-options}

La classe `NavigationOptions` fournit plusieurs méthodes pour personnaliser le comportement de navigation. Cela inclut le contrôle de la manière dont les routes sont gérées, si les observateurs sont notifiés, et comment l'historique du navigateur est mis à jour.

Voici les principales options de configuration disponibles dans `NavigationOptions` :

1. **Type de navigation (`setNavigationType`)**  
   Cette option définit si la nouvelle route doit être ajoutée à l'historique du navigateur ou remplacer la route actuelle.

   - **`PUSH`** : Ajoute la nouvelle route à la pile de l'historique, préservant la localisation actuelle.
   - **`REPLACE`** : Remplace la route actuelle dans la pile de l'historique par la nouvelle localisation, empêchant le bouton de retour de naviguer vers la route précédente.

2. **Déclencher des événements (`setFireEvents`)**  
   Détermine si les [événements du cycle de vie](./navigation-lifecycle/navigation-events) doivent être déclenchés pendant la navigation. Par défaut, cela est réglé sur `true`, et des événements sont déclenchés. S'il est réglé sur `false`, aucun événement ne sera déclenché, ce qui est utile pour une navigation silencieuse.

3. **Inviter les observateurs (`setInvokeObservers`)**  
   Ce paramètre contrôle si la navigation doit déclencher des [observateurs](./navigation-lifecycle/observers) au sein des composants navigués. Les observateurs gèrent généralement des événements tels que l'entrée ou la sortie de la route. Réglage sur `false` empêche l'invocation des observateurs.

4. **Mettre à jour l'historique (`setUpdateHistory`)**  
   Lorsqu'il est réglé sur `false`, cette option empêche la localisation historique d'être mise à jour. Cela est utile quand vous souhaitez changer de vue sans affecter la navigation de retour ou de forward du navigateur. Cela n'affecte que la gestion de l'historique, pas le cycle de vie du composant ou le traitement de la route.

5. **Objet d'état (`setState`)**  
   [L'objet d'état](./state-management#saving-and-restoring-state-in-browser-history) vous permet de passer des informations supplémentaires lors de la mise à jour de l'historique du navigateur. Cet objet est stocké dans l'état de l'historique du navigateur et peut être utilisé ultérieurement à des fins personnalisées, comme la sauvegarde de l'état de l'application pendant la navigation.

## Génération de localisations pour les vues {#generating-locations-for-views}

Le routeur peut générer la localisation pour les vues en fonction du modèle de route défini dans la vue. Vous pouvez également fournir des paramètres supplémentaires pour des segments dynamiques et requis dans l'URL. Cela peut être utile lors de la construction de liens ou du partage de points d'accès directs vers des vues spécifiques dans l'application.

Voici comment générer une `Location` basée sur une classe de vue et des paramètres de route :

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Cela génère un objet `Location` avec le chemin `/user/JohnDoe`, l'URI complet sous forme de chaîne.
