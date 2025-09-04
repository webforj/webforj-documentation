---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 65cff7dec35cab6a33e0d402512c8f86
---
L'injection de dépendance (DI) est un processus où les objets définissent leurs dépendances par le biais des arguments du constructeur plutôt que de les créer ou de les rechercher eux-mêmes. Le conteneur Spring injecte ces dépendances lors de la création de l'objet, ce qui entraîne un code plus propre et un meilleur découplage entre les composants.

Lorsque vous utilisez Spring Boot avec webforJ, le cadre détecte le contexte Spring et adapte sa création d'objets pour utiliser l'injection de dépendance de Spring. Cela signifie que vos classes de route peuvent déclarer des dépendances sur des services, des référentiels et d'autres beans Spring par le biais des paramètres du constructeur.

:::tip[En savoir plus sur l'injection de dépendance]
Pour une compréhension complète des modèles d'injection de dépendance et du conteneur IoC de Spring, consultez [la documentation officielle de Spring sur l'injection de dépendance](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Comment webforJ crée des routes avec Spring {#how-webforj-creates-routes-with-spring}

webforJ gère la création de routes différemment lorsque Spring est présent. Le mécanisme de création d'objets du cadre détecte Spring Boot dans le chemin de classe et délègue à `AutowireCapableBeanFactory` de Spring pour créer des instances avec injection de dépendance.

Pour les classes découvertes par le balayage `@Routify`, webforJ crée toujours de nouvelles instances et ne réutilise jamais les beans Spring existants. Chaque navigation de l'utilisateur reçoit une instance de route propre sans état partagé. Le processus de création :

1. L'utilisateur navigue vers une route
2. webforJ demande une nouvelle instance (ignorant toute définition de bean Spring existante)
3. L'usine de Spring crée l'instance et injecte les dépendances du constructeur
4. La route s'initialise avec toutes les dépendances satisfaites

Ce comportement est intentionnel et diffère des beans Spring typiques. Même si vous enregistrez une route en tant que bean Spring avec `@Component`, webforJ ignore cette définition de bean et crée une nouvelle instance pour chaque navigation.

## Utilisation des beans Spring dans les routes {#using-spring-beans-in-routes}

Vos classes de route n'ont pas besoin d'annotations Spring. L'annotation `@Route` seule permet à la fois la découverte de la route et l'injection de dépendance :

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Utiliser les services injectés pour construire l'UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Le constructeur peut recevoir :
- Des services annotés avec `@Service`, `@Repository` ou `@Component`
- Des référentiels Spring Data (`JpaRepository`, `CrudRepository`)
- Des valeurs de configuration via des annotations `@Value`
- Des beans d'infrastructure Spring (`ApplicationContext`, `Environment`)
- Tout bean enregistré dans le contexte Spring

## Exemple fonctionnel {#working-example}

Cet exemple démontre un scénario complet d'injection de dépendance où un service Spring est injecté dans une route webforJ. Le service gère la logique métier tandis que la route gère la présentation de l'UI.

Tout d'abord, définissez un service Spring standard à l'aide de l'annotation `@Service`. Ce service sera géré par le conteneur de Spring et disponible pour injection :

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
  private Button btn = new Button("Générer un nombre aléatoire");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Le nouveau nombre aléatoire est " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

Dans cet exemple, `RandomNumberService` est un bean de service Spring standard. La route `HelloWorldView` le déclare comme paramètre du constructeur, et Spring fournit automatiquement l'instance du service lorsque webforJ crée la route.

Remarque que la classe de route utilise uniquement l'annotation `@Route` - aucune stéréotype Spring comme `@Component` ou `@Controller` n'est nécessaire. Lorsque l'utilisateur navigue vers le chemin racine `/`, webforJ :

1. Crée une nouvelle instance de `HelloWorldView`
2. Demande à Spring de résoudre la dépendance `RandomNumberService`
3. Passe le service au constructeur
4. La route utilise le service injecté pour gérer les clics de bouton

Ce modèle fonctionne avec n'importe quel bean Spring - services, référentiels, classes de configuration, ou composants personnalisés. L'injection de dépendance se fait de manière transparente, vous permettant de vous concentrer sur la construction de votre UI pendant que Spring gère le câblage.
