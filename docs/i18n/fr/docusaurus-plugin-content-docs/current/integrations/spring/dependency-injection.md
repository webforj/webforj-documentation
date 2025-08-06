---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
L'injection de dépendances (DI) est un processus par lequel les objets définissent leurs dépendances par le biais des arguments du constructeur plutôt que de créer ou de rechercher eux-mêmes les dépendances. Le conteneur Spring injecte ces dépendances lors de la création de l'objet, ce qui entraîne un code plus propre et un meilleur découplage entre les composants.

Lorsque vous utilisez Spring Boot avec webforJ, le framework détecte le contexte Spring et adapte sa création d'objets pour utiliser l'injection de dépendances de Spring. Cela signifie que vos classes de route peuvent déclarer des dépendances sur des services, des dépôts et d'autres beans Spring à travers les paramètres du constructeur.

:::tip[En savoir plus sur l'injection de dépendances]
Pour une compréhension complète des modèles d'injection de dépendances et du conteneur IoC de Spring, consultez [la documentation officielle d'injection de dépendances de Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Comment webforJ crée des routes avec Spring {#how-webforj-creates-routes-with-spring}

webforJ gère la création de routes différemment lorsque Spring est présent. Le mécanisme de création d'objets du framework détecte Spring Boot dans le chemin de classe et délègue à `AutowireCapableBeanFactory` de Spring pour créer des instances avec injection de dépendances.

Pour les classes découvertes via le scan `@Routify`, webforJ crée toujours des instances fraîches et ne réutilise jamais les beans Spring existants. Chaque navigation de l'utilisateur reçoit une instance de route propre sans état partagé. Le processus de création :

1. L'utilisateur navigue vers une route
2. webforJ demande une nouvelle instance (ignorant toutes les définitions de beans Spring existantes)
3. La fabrique de Spring crée l'instance et injecte les dépendances du constructeur
4. La route s'initialise avec toutes les dépendances satisfaites

Ce comportement est intentionnel et diffère des beans Spring typiques. Même si vous enregistrez une route comme un bean Spring avec `@Component`, webforJ ignore cette définition de bean et crée une nouvelle instance pour chaque navigation.

## Utilisation des beans Spring dans les routes {#using-spring-beans-in-routes}

Vos classes de route n'ont pas besoin d'annotations Spring. L'annotation `@Route` seule permet à la fois la découverte des routes et l'injection de dépendances :

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Utilisez les services injectés pour construire l'UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Le constructeur peut recevoir :
- Des services annotés avec `@Service`, `@Repository` ou `@Component`
- Des dépôts Spring Data (`JpaRepository`, `CrudRepository`)
- Des valeurs de configuration via des annotations `@Value`
- Des beans d'infrastructure Spring (`ApplicationContext`, `Environment`)
- Tout bean enregistré dans le contexte Spring

## Exemple fonctionnel {#working-example}

Cet exemple illustre un scénario complet d'injection de dépendances où un service Spring est injecté dans une route webforJ. Le service gère la logique métier tandis que la route s'occupe de la présentation de l'interface utilisateur.

Tout d'abord, définissez un service Spring standard en utilisant l'annotation `@Service`. Ce service sera géré par le conteneur de Spring et disponible pour injection :

```java title="RandomNumberService.java"
@Service
public class RandomNumberService {

  public Integer getRandomNumber() {
    return (int) (Math.random() * 100);
  }
}
```

```java title="HelloWorldView.java"
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private Button btn = new Button("Générer un numéro aléatoire");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Le nouveau numéro aléatoire est " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

Dans cet exemple, le `RandomNumberService` est un bean de service Spring standard. La route `HelloWorldView` le déclare comme paramètre de constructeur, et Spring fournit automatiquement l'instance du service lorsque webforJ crée la route.

Remarquez que la classe de route utilise uniquement l'annotation `@Route` - aucune stéréotype Spring comme `@Component` ou `@Controller` n'est nécessaire. Lorsque l'utilisateur navigue vers le chemin racine `/`, webforJ :

1. Crée une nouvelle instance de `HelloWorldView` 
2. Demande à Spring de résoudre la dépendance `RandomNumberService`
3. Passe le service au constructeur
4. La route utilise le service injecté pour gérer les clics sur le bouton

Ce modèle fonctionne avec n'importe quel bean Spring - services, dépôts, classes de configuration ou composants personnalisés. L'injection de dépendances se produit de manière transparente, vous permettant de vous concentrer sur la construction de votre interface utilisateur pendant que Spring gère le câblage.
