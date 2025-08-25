---
title: Routing
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
Routing in webforJ mit Spring funktioniert genau wie in normalen webforJ-Anwendungen. Sie verwenden weiterhin die `@Route`-Annotation, um Routen zu definieren, dieselben Navigationsmuster und denselben Routenlebenszyklus. Der einzige Unterschied besteht darin, dass Ihre Routen, wenn Spring vorhanden ist, auch Spring-Beans über Konstruktorinjektion erhalten können.

Wenn Sie zu einer Route navigieren, erstellt webforJ die Routeninstanz - aber mit Spring im Klassenpfad verwendet es den Container von Spring, um Abhängigkeiten aufzulösen. Das bedeutet, dass Ihre Routen die volle Leistungsfähigkeit des Spring-Ökosystems (Dienste, Repositories, benutzerdefinierte Beans) nutzen können und dabei das vertraute webforJ-Routing-Verhalten beibehalten.

:::tip[Erfahren Sie mehr über Dependency Injection]
Für ein umfassendes Verständnis der Muster der Dependency Injection und des IoC-Containers von Spring siehe [die offizielle Dokumentation zur Dependency Injection von Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Wie webforJ Routen mit Spring erstellt {#how-webforj-creates-routes-with-spring}

webforJ behandelt die Routenerstellung anders, wenn Spring vorhanden ist. Der Mechanismus zur Objekterstellung des Frameworks erkennt Spring Boot im Klassenpfad und delegiert an Spring's `AutowireCapableBeanFactory`, um Instanzen mit Dependency Injection zu erstellen.

Für Klassen, die durch `@Routify`-Scans entdeckt werden, erstellt webforJ immer frische Instanzen und verwendet niemals vorhandene Spring-Beans wieder. Jede Benutzernavigation erhält eine saubere Routeninstanz ohne geteilten Zustand. Der Erstellungsprozess:

1. Benutzer navigiert zu einer Route
2. webforJ fordert eine neue Instanz an (ignoriert alle vorhandenen Spring-Bean-Definitionen)
3. Springs Fabrik erstellt die Instanz und injiziert die Konstruktorabhängigkeiten
4. Die Route wird mit allen erfüllten Abhängigkeiten initialisiert

Dieses Verhalten ist beabsichtigt und unterscheidet sich von typischen Spring-Beans. Selbst wenn Sie eine Route als Spring-Bean mit `@Component` registrieren, ignoriert webforJ diese Bean-Definition und erstellt für jede Navigation eine frische Instanz.

## Verwendung von Spring-Beans in Routen {#using-spring-beans-in-routes}

Ihre Routenklassen benötigen keine Spring-Annotationen. Die `@Route`-Annotation alleine ermöglicht sowohl die Routenentdeckung als auch die Dependency Injection:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Verwenden Sie injizierte Dienste zum Erstellen der UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Der Konstruktor kann erhalten:
- Dienste, die mit `@Service`, `@Repository` oder `@Component` annotiert sind
- Spring Data-Repositories (`JpaRepository`, `CrudRepository`)
- Konfigurationswerte über `@Value`-Annotationen
- Spring-Infrastruktur-Beans (`ApplicationContext`, `Environment`)
- Jede Bean, die im Spring-Kontext registriert ist

## Arbeitsbeispiel {#working-example}

Dieses Beispiel demonstriert ein vollständiges Szenario der Dependency Injection, in dem ein Spring-Dienst in eine webforJ-Route injiziert wird. Der Dienst verwaltet die Geschäftslogik, während die Route die UI-Präsentation behandelt.

Zuerst definieren Sie einen standardmäßigen Spring-Dienst mit der Annotation `@Service`. Dieser Dienst wird vom Container von Spring verwaltet und steht zur Injektion zur Verfügung:

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
  private Button btn = new Button("Zufallszahl generieren");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Die neue Zufallszahl ist " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

In diesem Beispiel ist der `RandomNumberService` eine standardmäßige Spring-Service-Bean. Die `HelloWorldView`-Route deklariert ihn als Konstruktorparameter, und Spring stellt automatisch die Dienstinstanz zur Verfügung, wenn webforJ die Route erstellt.

Beachten Sie, dass die Routenklasse nur die `@Route`-Annotation verwendet - keine Spring-Stereotypen wie `@Component` oder `@Controller` sind erforderlich. Wenn ein Benutzer zu dem Root-Pfad `/` navigiert, erfolgt in webforJ:

1. Eine neue Instanz von `HelloWorldView` wird erstellt 
2. Spring wird um die Auflösung der `RandomNumberService`-Abhängigkeit gebeten
3. Der Dienst wird dem Konstruktor übergeben
4. Die Route verwendet den injizierten Dienst zum Verarbeiten von Button-Klicks

Dieses Muster funktioniert mit jeder Spring-Bean - Dienste, Repositories, Konfigurationsklassen oder benutzerdefinierte Komponenten. Die Dependency Injection erfolgt transparent, sodass Sie sich auf den Aufbau Ihrer UI konzentrieren können, während Spring die Verkabelung verwaltet.
