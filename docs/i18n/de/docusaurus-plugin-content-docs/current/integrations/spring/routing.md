---
title: Routing
sidebar_position: 15
description: >-
  Inject Spring services and repositories into webforJ @Route classes through
  constructor injection while keeping a fresh instance per navigation.
_i18n_hash: 4bef970301ebc7072162c3dc95b6e544
---
Routing in webforJ mit Spring funktioniert genau gleich wie in normalen webforJ-Anwendungen. Sie verwenden weiterhin die `@Route`-Annotation, um Routen zu definieren, die gleichen Navigationsmuster und den gleichen Routenlebenszyklus. Der einzige Unterschied besteht darin, dass Ihre Routen, wenn Spring vorhanden ist, auch Spring-Beans über die Konstruktorinjektion erhalten können.

Wenn Sie zu einer Route navigieren, erstellt webforJ die Routeninstanz - aber mit Spring auf dem Classpath verwendet es den Spring-Container, um Abhängigkeiten aufzulösen. Das bedeutet, dass Ihre Routen die volle Leistungsfähigkeit des Spring-Ökosystems (Dienste, Repositories, benutzerdefinierte Beans) nutzen können, während das vertraute Routing-Verhalten von webforJ beibehalten wird.

:::tip[Erfahren Sie mehr über die Abhängigkeitsinjektion]
Für ein umfassendes Verständnis der Muster zur Abhängigkeitsinjektion und des IoC-Containers von Spring siehe [die offizielle Dokumentation zur Abhängigkeitsinjektion von Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Wie webforJ Routen mit Spring erstellt {#how-webforj-creates-routes-with-spring}

webforJ behandelt die Routencreation anders, wenn Spring vorhanden ist. Der Objektcreation-Mechanismus des Frameworks erkennt Spring Boot auf dem Classpath und delegiert an Springs `AutowireCapableBeanFactory`, um Instanzen mit Abhängigkeitsinjektion zu erstellen.

Für Klassen, die durch das Scannen von `@Routify` entdeckt werden, erstellt webforJ immer frische Instanzen und verwendet niemals vorhandene Spring-Beans. Jeder Benutzernavigation wird eine saubere Routeninstanz ohne gemeinsame Zustände zugewiesen. Der Schaffungsprozess:

1. Benutzer navigiert zu einer Route
2. webforJ fordert eine neue Instanz an (unter Ignorierung vorhandener Spring-Bean-Definitionen)
3. Die Factory von Spring erstellt die Instanz und injiziert die Konstruktordependenzen
4. Die Route wird mit allen erfüllten Abhängigkeiten initialisiert

Dieses Verhalten ist absichtlich und unterscheidet sich von typischen Spring-Beans. Selbst wenn Sie eine Route als Spring-Bean mit `@Component` registrieren, ignoriert webforJ diese Bean-Definition und erstellt für jede Navigation eine frische Instanz.

## Verwendung von Spring-Beans in Routen {#using-spring-beans-in-routes}

Ihre Routenklassen benötigen keine Spring-Annotationen. Die `@Route`-Annotation allein ermöglicht sowohl die Routenentdeckung als auch die Abhängigkeitsinjektion:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    // Verwenden Sie die injizierten Dienste, um die UI zu erstellen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
  }
}
```

Der Konstruktor kann empfangen:
- Dienste, die mit `@Service`, `@Repository` oder `@Component` annotiert sind
- Spring Data-Repositories (`JpaRepository`, `CrudRepository`)
- Konfigurationswerte über `@Value`-Annotationen
- Spring-Infrastruktur-Beans (`ApplicationContext`, `Environment`)
- Jede Bean, die im Spring-Kontext registriert ist

## Arbeitsbeispiel {#working-example}

Dieses Beispiel demonstriert ein umfassendes Abhängigkeitsinjektionsszenario, bei dem ein Spring-Dienst in eine webforJ-Route injiziert wird. Der Dienst verwaltet die Geschäftlogik, während die Route die UI-Präsentation behandelt.

Zunächst definieren Sie einen standardmäßigen Spring-Dienst mit der `@Service`-Annotation. Dieser Dienst wird von Springs Container verwaltet und steht für die Injektion zur Verfügung:

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

  private final FlexLayout self = getBoundComponent();
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

In diesem Beispiel ist der `RandomNumberService` ein standardmäßiges Spring-Dienst-Bean. Die Route `HelloWorldView` erklärt ihn als Konstruktionsparameter, und Spring stellt automatisch die Dienstinstanz zur Verfügung, wenn webforJ die Route erstellt.

Beachten Sie, dass die Routenklasse nur die `@Route`-Annotation verwendet - keine Spring-Stereotypen wie `@Component` oder `@Controller` sind erforderlich. Wenn ein Benutzer den Stammweg `/` navigiert, führt webforJ:

1. Erstellt eine neue Instanz von `HelloWorldView`
2. Fragt Spring, um die Abhängigkeit `RandomNumberService` aufzulösen
3. Übergibt den Dienst an den Konstruktor
4. Die Route verwendet den injizierten Dienst, um Button-Klicks zu verarbeiten

Dieses Muster funktioniert mit jeder Spring-Bean - Diensten, Repositories, Konfigurationsklassen oder benutzerdefinierten Komponenten. Die Abhängigkeitsinjektion erfolgt transparent, sodass Sie sich auf den Aufbau Ihrer UI konzentrieren können, während Spring das Wiring verwaltet.
