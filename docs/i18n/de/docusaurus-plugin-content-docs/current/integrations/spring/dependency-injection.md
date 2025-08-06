---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
Dependency Injection (DI) ist ein Prozess, bei dem Objekte ihre Abhängigkeiten über Konstruktorargumente definieren, anstatt Abhängigkeiten selbst zu erstellen oder zu suchen. Der Spring-Container injiziert diese Abhängigkeiten beim Erstellen des Objekts, was zu saubererem Code und einer besseren Entkopplung zwischen Komponenten führt.

Bei der Verwendung von Spring Boot mit webforJ erkennt das Framework den Spring-Kontext und passt die Objekt-erstellung an, um die Dependency Injection von Spring zu nutzen. Dies bedeutet, dass Ihre Routenkassen Abhängigkeiten von Diensten, Repositories und anderen Spring-Beans über Konstruktorparameter deklarieren können.

:::tip[Lernen Sie mehr über Dependency Injection]
Für ein umfassendes Verständnis der Muster der Dependency Injection und des IoC-Containers von Spring siehe die [offizielle Dokumentation zur Dependency Injection von Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Wie webforJ Routen mit Spring erstellt {#how-webforj-creates-routes-with-spring}

webforJ behandelt die Routen-erstellung anders, wenn Spring vorhanden ist. Der Mechanismus zur Erstellung von Objekten des Frameworks erkennt Spring Boot im Klassenpfad und delegiert an Spring's `AutowireCapableBeanFactory`, um Instanzen mit Dependency Injection zu erstellen.

Für Klassen, die durch die `@Routify`-Scans entdeckt werden, erstellt webforJ immer frische Instanzen und wiederverwendet niemals vorhandene Spring-Beans. Jede Benutzernavigation erhält eine neue Routeninstanz ohne gemeinsamen Zustand. Der Erstellungsprozess:

1. Benutzer navigiert zu einer Route
2. webforJ fordert eine neue Instanz an (ignoriert dabei alle vorhandenen Spring-Bean-Definitionen)
3. Die Factory von Spring erstellt die Instanz und injiziert die Konstruktionsabhängigkeiten
4. Die Route wird mit allen erfüllten Abhängigkeiten initialisiert

Dieses Verhalten ist absichtlich und unterscheidet sich von typischen Spring-Beans. Selbst wenn Sie eine Route als Spring-Bean mit `@Component` registrieren, ignoriert webforJ diese Bean-Definition und erstellt für jede Navigation eine neue Instanz.

## Verwendung von Spring-Beans in Routen {#using-spring-beans-in-routes}

Ihre Routenkassen benötigen keine Spring-Anmerkungen. Nur die `@Route`-Annotation ermöglicht sowohl die Routenentdeckung als auch die Dependency Injection:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Verwenden Sie die injizierten Dienste, um die UI aufzubauen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
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

Dieses Beispiel zeigt ein vollständiges Szenario der Dependency Injection, bei dem ein Spring-Dienst in eine webforJ-Route injiziert wird. Der Dienst verwaltet die Geschäftslogik, während die Route die UI-Präsentation verarbeitet.

Zuerst definieren Sie einen standardmäßigen Spring-Dienst mit der `@Service`-Annotation. Dieser Dienst wird vom Container von Spring verwaltet und steht zur Injection zur Verfügung:

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
  private Button btn = new Button("Generiere Zufallszahl");

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

In diesem Beispiel ist der `RandomNumberService` eine standardmäßige Spring-Dienst-Bean. Die Route `HelloWorldView` erklärt ihn als Konstruktorparameter, und Spring stellt automatisch die Dienstinstanz zur Verfügung, wenn webforJ die Route erstellt.

Beachten Sie, dass die Routenklasse nur die `@Route`-Annotation verwendet – keine Spring-Stereotypen wie `@Component` oder `@Controller` sind erforderlich. Wenn ein Benutzer zu dem Stamm-Pfad `/` navigiert, führt webforJ:

1. Erstellt eine neue Instanz von `HelloWorldView` 
2. Fragt Spring, um die Abhängigkeit `RandomNumberService` aufzulösen
3. Übergibt den Dienst an den Konstruktor
4. Die Route verwendet den injizierten Dienst, um Button-Klicks zu verarbeiten

Dieses Muster funktioniert mit jeder Spring-Bean – Diensten, Repositories, Konfigurationsklassen oder benutzerdefinierten Komponenten. Die Dependency Injection geschieht transparent, sodass Sie sich darauf konzentrieren können, Ihre UI zu gestalten, während Spring das Verdrahten verwaltet.
