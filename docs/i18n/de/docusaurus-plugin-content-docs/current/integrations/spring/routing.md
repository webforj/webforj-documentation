---
title: Routing
sidebar_position: 15
_i18n_hash: 8518a84a9c7a543b73cf4de16658f650
---
Routing in webforJ mit Spring funktioniert genau wie in normalen webforJ-Anwendungen. Sie verwenden weiterhin die `@Route`-Annotation, um Routen zu definieren, die gleichen Navigationsmuster und denselben Routenlebenszyklus. Der einzige Unterschied besteht darin, dass Ihre Routen bei Vorhandensein von Spring auch Spring-Beans über die Konstruktorinjektion empfangen können.

Wenn Sie zu einer Route navigieren, erstellt webforJ die Routeninstanz - aber mit Spring im Klassenpfad verwendet es den Spring-Container, um Abhängigkeiten aufzulösen. Dies bedeutet, dass Ihre Routen die volle Kraft des Spring-Ökosystems (Dienste, Repositories, benutzerdefinierte Beans) nutzen können, während das vertraute Verhalten der webforJ-Routing-Logik beibehalten wird.

:::tip[Erfahren Sie mehr über die Abhängigkeitsinjektion]
Für ein umfassendes Verständnis der Muster der Abhängigkeitsinjektion und des IoC-Containers von Spring siehe [offizielle Dokumentation zur Abhängigkeitsinjektion von Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Wie webforJ Routen mit Spring erstellt {#how-webforj-creates-routes-with-spring}

webforJ behandelt die Routen Erstellung anders, wenn Spring vorhanden ist. Der Mechanismus zur Objekterstellung des Frameworks erkennt Spring Boot im Klassenpfad und delegiert an Spring's `AutowireCapableBeanFactory`, um Instanzen mit Abhängigkeitsinjektion zu erstellen.

Für Klassen, die durch `@Routify`-Scanning entdeckt werden, erstellt webforJ immer frische Instanzen und verwendet niemals vorhandene Spring-Beans. Jede Benutzernavigation erhält eine saubere Routeninstanz ohne gemeinsamen Zustand. Der Erstellungsprozess:

1. Der Benutzer navigiert zu einer Route
2. webforJ fordert eine neue Instanz an (unter Ignorierung vorhandener Spring-Bean-Definitionen)
3. Die Factory von Spring erstellt die Instanz und injiziert die Konstruktordependenzen
4. Die Route wird mit allen erfüllten Abhängigkeiten initialisiert

Dieses Verhalten ist beabsichtigt und unterscheidet sich von typischen Spring-Beans. Selbst wenn Sie eine Route mit `@Component` als Spring-Bean registrieren, ignoriert webforJ diese Bean-Definition und erstellt für jede Navigation eine neue Instanz.

## Verwendung von Spring-Beans in Routen {#using-spring-beans-in-routes}

Ihre Routenklassen benötigen keine Spring-Annotationen. Die `@Route`-Annotation allein ermöglicht sowohl die Routenerkennung als auch die Abhängigkeitsinjektion:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Verwenden Sie injizierte Dienste, um die UI zu erstellen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    self.add(metricsPanel);
  }
}
```

Der Konstruktor kann Folgendes empfangen:
- Dienste, die mit `@Service`, `@Repository` oder `@Component` annotiert sind
- Spring Data-Repositories (`JpaRepository`, `CrudRepository`)
- Konfigurationswerte über `@Value`-Annotationen
- Spring-Infrastruktur-Beans (`ApplicationContext`, `Environment`)
- Jede Bean, die im Spring-Kontext registriert ist

## Arbeitsbeispiel {#working-example}

Dieses Beispiel demonstriert ein vollständiges Szenario zur Abhängigkeitsinjektion, bei dem ein Spring-Dienst in eine webforJ-Route injiziert wird. Der Dienst verwaltet die Geschäftslogik, während die Route die UI-Präsentation übernimmt.

Zuerst definieren Sie einen Standard-Spring-Dienst mit der `@Service`-Annotation. Dieser Dienst wird vom Spring-Container verwaltet und steht zur Injektion zur Verfügung:

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

In diesem Beispiel ist der `RandomNumberService` eine Standard-Spring-Dienst-Bean. Die `HelloWorldView`-Route erklärt ihn als Parameter des Konstruktors, und Spring stellt automatisch die Dienstinstanz zur Verfügung, wenn webforJ die Route erstellt.

Beachten Sie, dass die Routenklasse nur die `@Route`-Annotation verwendet - keine Spring-Stereotypen wie `@Component` oder `@Controller` sind erforderlich. Wenn ein Benutzer zum Stammverzeichnis `/` navigiert, führt webforJ folgende Schritte aus:

1. Erstellt eine neue Instanz von `HelloWorldView` 
2. Fragt Spring, um die Abhängigkeit `RandomNumberService` aufzulösen
3. Übergibt den Dienst an den Konstruktor
4. Die Route verwendet den injizierten Dienst zur Verarbeitung von Button-Klicks

Dieses Muster funktioniert mit jeder Spring-Bean - Dienste, Repositories, Konfigurationsklassen oder benutzerdefinierten Komponenten. Die Abhängigkeitsinjektion erfolgt transparent, sodass Sie sich darauf konzentrieren können, Ihre UI zu erstellen, während Spring die Verdrahtung verwaltet.
