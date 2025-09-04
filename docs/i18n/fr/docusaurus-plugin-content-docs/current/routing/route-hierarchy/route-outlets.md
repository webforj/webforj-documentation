---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
Un **outlet** est un composant désigné, soit un [layout de route](./route-types#layout-routes) ou une [vue de route](./route-types#view-routes), où les routes enfants sont rendues dynamiquement. Il définit où le contenu de la route enfant apparaîtra dans la route parente. Les outlets sont fondamentaux pour créer des interfaces utilisateur modulaires et imbriquées ainsi que des structures de navigation flexibles.

## Définir un outlet {#defining-an-outlet}

Les outlets sont généralement implémentés à l'aide de composants conteneurs qui peuvent contenir et gérer le contenu enfant. Dans webforJ, tout composant qui implémente l'interface `HasComponents`, ou un composite de tels composants, peut servir d'outlet. Par exemple, [`FlexLayout`](../../components/flex-layout) implémente l'interface `HasComponents`, ce qui en fait un outlet valide pour les routes enfants.

Si aucun outlet n'est explicitement défini pour une route, le premier `Frame` de l'application est utilisé comme outlet par défaut. Ce comportement garantit que chaque route enfant a un endroit où être rendu.

:::tip Gestion des Frames
Dans les applications avec plusieurs frames, vous pouvez spécifier quel frame utiliser comme outlet pour les routes enfants en définissant l'attribut `frame` dans l'annotation `@Route`. L'attribut `frame` accepte le nom du frame à utiliser pour le rendu.
:::

### Exemple : {#example}

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Contenu du tableau de bord"));
  }
}
```

Dans cet exemple :

- `MainLayout` agit comme le conteneur de layout, mais comme aucun outlet spécifique n'est défini, le `Frame` par défaut de l'application est utilisé.
- La `DashboardView` est rendue dans `MainLayout` en utilisant l'outlet par défaut (zone de contenu) de l'`AppLayout`.

Ainsi, les routes enfants de `MainLayout` seront automatiquement rendues dans l'emplacement de contenu de l'`AppLayout`, à moins qu'un outlet ou un frame différent ne soit spécifié.

## Cycle de vie de l'outlet {#outlet-lifecycle}

Les outlets sont étroitement liés au cycle de vie des routes. Lorsque la route active change, l'outlet met à jour son contenu dynamiquement en injectant le composant enfant approprié et en retirant les composants qui ne sont plus nécessaires. Cela garantit que seules les vues pertinentes sont rendues à tout moment.

- **Création** : Les outlets sont initialisés avant que les composants enfants ne soient créés.
- **Injection de contenu** : Lorsqu'une route enfant est correspondue, son composant est injecté dans l'outlet.
- **Mise à jour** : Lors de la navigation entre les routes, l'outlet met à jour son contenu, injectant le nouveau composant enfant et retirant tout composant obsolète.

## Outlets personnalisés {#custom-outlets}

L'interface `RouteOutlet` est responsable de la gestion du cycle de vie des composants de route, déterminant comment les composants sont rendus et retirés. Tout composant qui implémente cette interface peut agir comme un outlet pour d'autres composants.

### Méthodes clés dans `RouteOutlet` : {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`** : Responsable du rendu du composant fourni dans l'outlet. Cela est appelé lorsque le routeur correspond à une route et que le composant enfant doit être affiché.
- **`removeRouteContent(Component component)`** : Gère le retrait du composant de l'outlet,appelé typiquement lors de la navigation loin de la route actuelle.

En implémentant `RouteOutlet`, les développeurs peuvent contrôler la manière dont les routes sont injectées dans des zones spécifiques de l'application. par exemple

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

Dans cet exemple, la classe `MainLayout` implémente l'interface `RouteOutlet`, permettant aux composants d'être ajoutés ou retirés dynamiquement du tiroir de l'AppLayout en fonction de la navigation des routes au lieu de la zone de contenu par défaut définie dans le composant `AppLayout`.

## Mise en cache des composants d'outlet {#caching-outlet-components}

Par défaut, les outlets ajoutent et retirent dynamiquement des composants lors de la navigation vers et depuis les routes. Cependant, dans certains cas—particulièrement pour les vues avec des composants complexes—il peut être préférable de basculer la visibilité des composants plutôt que de les retirer complètement du DOM. C'est là que le `PersistentRouteOutlet` entre en jeu, permettant aux composants de rester en mémoire et d'être simplement masqués ou affichés, au lieu d'être détruits et recréés.

Le `PersistentRouteOutlet` met en cache les composants rendus, les maintenant en mémoire lorsque l'utilisateur navigue loin. Cela améliore les performances en évitant la destruction et la recréation inutiles des composants, ce qui est particulièrement bénéfique pour les applications où les utilisateurs changent fréquemment de vues.

### Comment fonctionne `PersistentRouteOutlet` : {#how-persistentrouteoutlet-works}

- **Mise en cache des composants** : Il maintient un cache en mémoire de tous les composants qui ont été rendus dans l'outlet.
- **Basculer la visibilité** : Au lieu de retirer les composants du DOM, il les cache lors de la navigation loin d'une route.
- **Restauration des composants** : Lorsque l'utilisateur navigue de nouveau vers une route précédemment mise en cache, le composant est simplement affiché à nouveau sans besoin de recréation.

Ce comportement est particulièrement utile pour des interfaces utilisateurs complexes où le rendu constant de composants peut dégrader les performances. Toutefois, pour que cette bascule de visibilité fonctionne, les composants gérés doivent implémenter l'interface `HasVisibility`, ce qui permet au `PersistentRouteOutlet` de contrôler leur visibilité.

:::tip Quand utiliser `PersistentRouteOutlet`
Utilisez `PersistentRouteOutlet` lorsque la création et la destruction fréquentes de composants entraînent des goulets d'étranglement de performances dans votre application. Il est généralement recommandé de laisser le comportement par défaut de création et destruction de composants lors des transitions entre routes, car cela aide à éviter les bogues potentiels et les problèmes liés au maintien d'un état cohérent. Cependant, dans des scénarios où les performances sont critiques et où les composants sont complexes ou coûteux à recréer, `PersistentRouteOutlet` peut offrir des améliorations significatives en mettant en cache les composants et en gérant leur visibilité.
:::

### Exemple d'implémentation de `PersistentRouteOutlet` : {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

Dans cet exemple, `MainLayout` utilise `PersistentRouteOutlet` pour gérer ses routes enfants. Lors de la navigation entre les routes, les composants ne sont pas retirés du DOM mais plutôt cachés, garantissant qu'ils restent disponibles pour un rendu rapide lorsqu'un utilisateur navigue de nouveau. Cette approche améliore considérablement les performances, en particulier pour les vues avec un contenu complexe ou une utilisation intensive des ressources.
