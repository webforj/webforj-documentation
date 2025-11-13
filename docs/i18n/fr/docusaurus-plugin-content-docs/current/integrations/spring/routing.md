---
title: Routage
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
Le routage dans webforJ avec Spring fonctionne exactement de la même manière que dans les applications webforJ classiques. Vous utilisez toujours l'annotation `@Route` pour définir des routes, les mêmes motifs de navigation et le même cycle de vie des routes. La seule différence est que lorsque Spring est présent, vos routes peuvent également recevoir des beans Spring par injection de constructeur.

Lorsque vous naviguez vers une route, webforJ crée l'instance de la route - mais avec Spring sur le classpath, il utilise le conteneur de Spring pour résoudre les dépendances. Cela signifie que vos routes peuvent utiliser toute la puissance de l'écosystème de Spring (services, dépôts, beans customisés) tout en conservant le comportement de routage familier de webforJ.

:::tip[En savoir plus sur l'injection de dépendances]
Pour une compréhension complète des motifs d'injection de dépendances et du conteneur IoC de Spring, consultez [la documentation officielle de Spring sur l'injection de dépendances](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Comment webforJ crée des routes avec Spring {#how-webforj-creates-routes-with-spring}

webforJ gère la création de routes différemment lorsque Spring est présent. Le mécanisme de création d'objets du framework détecte Spring Boot sur le classpath et délègue à `AutowireCapableBeanFactory` de Spring la création d'instances avec injection de dépendances.

Pour les classes découvertes par le balayage `@Routify`, webforJ crée toujours de nouvelles instances et ne réutilise jamais les beans Spring existants. Chaque navigation utilisateur reçoit une instance de route propre sans état partagé. Le processus de création :

1. L'utilisateur navigue vers une route
2. webforJ demande une nouvelle instance (ignorant les définitions de beans Spring existantes)
3. L'usine de Spring crée l'instance et injecte les dépendances du constructeur
4. La route s'initialise avec toutes les dépendances satisfaites

Ce comportement est intentionnel et diffère des beans Spring typiques. Même si vous enregistrez une route en tant que bean Spring avec `@Component`, webforJ ignore cette définition de bean et crée une nouvelle instance pour chaque navigation.

## Utilisation des beans Spring dans les routes {#using-spring-beans-in-routes}

Vos classes de route ne nécessitent pas d'annotations Spring. L'annotation `@Route` seule permet à la fois la découverte des routes et l'injection de dépendances :

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
- Des dépôts Spring Data (`JpaRepository`, `CrudRepository`)
- Des valeurs de configuration via des annotations `@Value`
- Des beans d'infrastructure Spring (`ApplicationContext`, `Environment`)
- Tout bean enregistré dans le contexte Spring

## Exemple fonctionnel {#working-example}

Cet exemple démontre un scénario complet d'injection de dépendances où un service Spring est injecté dans une route webforJ. Le service gère la logique métier tandis que la route gère la présentation de l'UI.

Tout d'abord, définissez un service Spring standard en utilisant l'annotation `@Service`. Ce service sera géré par le conteneur de Spring et disponible pour l'injection :

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

Dans cet exemple, le `RandomNumberService` est un bean de service Spring standard. La route `HelloWorldView` le déclare comme paramètre du constructeur, et Spring fournit automatiquement l'instance du service lorsque webforJ crée la route.

Notez que la classe de la route utilise seulement l'annotation `@Route` - pas de stéréotypes Spring comme `@Component` ou `@Controller` ne sont nécessaires. Lorsque l'utilisateur navigue vers le chemin racine `/`, webforJ :

1. Crée une nouvelle instance de `HelloWorldView`
2. Demande à Spring de résoudre la dépendance `RandomNumberService`
3. Passe le service au constructeur
4. La route utilise le service injecté pour gérer les clics de boutons

Ce modèle fonctionne avec n'importe quel bean Spring - services, dépôts, classes de configuration ou composants personnalisés. L'injection de dépendances se fait de manière transparente, vous permettant de vous concentrer sur la construction de votre UI pendant que Spring gère le câblage.
