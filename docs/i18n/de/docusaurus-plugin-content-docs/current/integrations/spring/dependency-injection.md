---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 65cff7dec35cab6a33e0d402512c8f86
---
Dependency Injection (DI) ist ein Prozess, bei dem Objekte ihre Abhängigkeiten über Konstruktorargumente definieren, anstatt Abhängigkeiten selbst zu erstellen oder nachzuschlagen. Der Spring-Container injiziert diese Abhängigkeiten beim Erstellen des Objekts, was zu saubererem Code und besserer Entkopplung zwischen Komponenten führt.

Beim Einsatz von Spring Boot mit webforJ erkennt das Framework den Spring-Kontext und passt seine Objekt-Erstellung an, um die Dependency Injection von Spring zu nutzen. Das bedeutet, dass Ihre Routenklassen Abhängigkeiten von Services, Repositories und anderen Spring-Beans über Konstruktorparameter deklarieren können.

:::tip[Erfahren Sie mehr über Dependency Injection]
Für ein umfassendes Verständnis von Dependency Injection-Mustern und Springs IoC-Container siehe die [offizielle Dokumentation zur Dependency Injection von Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Wie webforJ Routen mit Spring erstellt {#how-webforj-creates-routes-with-spring}

webforJ behandelt die Routen-Erstellung anders, wenn Spring vorhanden ist. Der Objekt-Erstellungsmechanismus des Frameworks erkennt Spring Boot im Klassenpfad und delegiert an Springs `AutowireCapableBeanFactory`, um Instanzen mit Dependency Injection zu erstellen.

Für Klassen, die durch `@Routify`-Scans entdeckt werden, erstellt webforJ immer frische Instanzen und verwendet niemals vorhandene Spring-Beans. Jede Benutzer-Navigation erhält eine saubere Routeninstanz ohne gemeinsamen Zustand. Der Erstellungsprozess:

1. Benutzer navigiert zu einer Route
2. webforJ fordert eine neue Instanz an (ignoriert vorhandene Spring-Bean-Definitionen)
3. Springs Fabrik erstellt die Instanz und injiziert die Konstruktorabhängigkeiten
4. Die Route wird mit allen erfüllten Abhängigkeiten initialisiert

Dieses Verhalten ist absichtlich und unterscheidet sich von typischen Spring-Beans. Selbst wenn Sie eine Route als Spring-Bean mit `@Component` registrieren, ignoriert webforJ diese Bean-Definition und erstellt eine frische Instanz für jede Navigation.

## Verwendung von Spring-Beans in Routen {#using-spring-beans-in-routes}

Ihre Routenklassen benötigen keine Spring-Anmerkungen. Die `@Route`-Anmerkung allein ermöglicht sowohl die Routenentdeckung als auch die Dependency Injection:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Verwenden Sie injizierte Services, um die UI aufzubauen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Der Konstruktor kann erhalten:
- Services, die mit `@Service`, `@Repository` oder `@Component` annotiert sind
- Spring Data Repositories (`JpaRepository`, `CrudRepository`)
- Konfigurationswerte über `@Value`-Anmerkungen
- Spring-Infrastruktur-Beans (`ApplicationContext`, `Environment`)
- Jede Bean, die im Spring-Kontext registriert ist

## Arbeitsbeispiel {#working-example}

Dieses Beispiel zeigt ein vollständiges Szenario der Dependency Injection, bei dem ein Spring-Service in eine webforJ-Route injiziert wird. Der Service verwaltet die Geschäftslogik, während die Route die UI-Präsentation übernimmt.

Zuerst definieren Sie einen Standard-Spring-Service mit der `@Service`-Anmerkung. Dieser Service wird vom Spring-Container verwaltet und steht für die Injection zur Verfügung:

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

In diesem Beispiel ist der `RandomNumberService` ein standardmäßiger Spring-Service-Bean. Die `HelloWorldView`-Route deklariert ihn als Konstruktorparameter, und Spring stellt automatisch die Service-Instanz bereit, wenn webforJ die Route erstellt.

Beachten Sie, dass die Routenklasse nur die `@Route`-Anmerkung verwendet - keine Spring-Stereotypen wie `@Component` oder `@Controller` sind nötig. Wenn ein Benutzer zu dem Stamm-Pfad `/` navigiert, führt webforJ:

1. Erstellt eine neue Instanz von `HelloWorldView`
2. Fragt Spring nach der Auflösung der `RandomNumberService`-Abhängigkeit
3. Übergibt den Service an den Konstruktor
4. Die Route verwendet den injizierten Service, um Button-Klicks zu verarbeiten

Dieses Muster funktioniert mit jeder Spring-Bean - Services, Repositories, Konfigurationsklassen oder benutzerdefinierten Komponenten. Die Dependency Injection erfolgt transparent, sodass Sie sich auf den Aufbau Ihrer UI konzentrieren können, während Spring das Verdrahten verwaltet.
