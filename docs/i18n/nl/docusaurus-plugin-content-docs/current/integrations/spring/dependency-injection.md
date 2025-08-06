---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
Dependency injection (DI) is een proces waarbij objecten hun afhankelijkheden definiëren via constructorparameters in plaats van deze zelf te creëren of op te zoeken. De Spring-container injecteert deze afhankelijkheden bij het maken van het object, wat resulteert in schonere code en een betere ontkoppeling tussen componenten.

Wanneer je Spring Boot met webforJ gebruikt, detecteert het framework de Spring-context en past de objectcreatie aan om gebruik te maken van Spring's dependency injection. Dit betekent dat je routeklassen afhankelijkheden van services, repositories en andere Spring-beans kunnen declareren via constructorparameters.

:::tip[Leer meer over dependency injection]
Voor een uitgebreide uitleg over afhankelijkheidsinjectiepatronen en Spring's IoC-container, zie [de officiële documentatie over dependency injection van Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Hoe webforJ routes creëert met Spring {#how-webforj-creates-routes-with-spring}

webforJ behandelt de creatie van routes anders wanneer Spring aanwezig is. Het objectcreatiemechanisme van het framework detecteert Spring Boot op het classpath en delegeert aan Spring's `AutowireCapableBeanFactory` voor het creëren van instanties met dependency injection.

Voor klassen die zijn ontdekt via `@Routify` scanning, creëert webforJ altijd nieuwe instanties en hergebruikt nooit bestaande Spring-beans. Elke gebruikersnavigatie ontvangt een schone route-instantie zonder gedeelde status. Het creatieproces:

1. Gebruiker navigeert naar een route
2. webforJ vraagt om een nieuwe instantie (negeert bestaande Spring bean-definities)
3. De fabriek van Spring creëert de instantie en injecteert constructorafhankelijkheden
4. De route initialiseert met alle afhankelijkheden voldaan

Dit gedrag is opzettelijk en verschilt van typische Spring-beans. Zelfs als je een route registreert als een Spring bean met `@Component`, negeert webforJ die bean-definitie en creëert het een nieuwe instantie voor elke navigatie.

## Spring beans gebruiken in routes {#using-spring-beans-in-routes}

Je routeklassen hebben geen Spring-annotaties nodig. De `@Route`-annotatie alleen maakt zowel routeontdekking als afhankelijkheidsinjectie mogelijk:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Gebruik ingesloten services om UI te bouwen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

De constructor kan ontvangen:
- Services gemarkeerd met `@Service`, `@Repository` of `@Component`
- Spring Data repositories (`JpaRepository`, `CrudRepository`)
- Configuratiewaarden via `@Value` annotaties
- Spring infrastructuur beans (`ApplicationContext`, `Environment`)
- Elke bean die is geregistreerd in de Spring-context

## Werkend voorbeeld {#working-example}

Dit voorbeeld demonstreert een complete afhankelijkheidsinjectiescenario waarin een Spring-service wordt geïnjecteerd in een webforJ-route. De service beheert bedrijfslogica terwijl de route verantwoordelijk is voor de presentatie van de UI.

Definieer eerst een standaard Spring-service met de `@Service` annotatie. Deze service wordt beheerd door de container van Spring en is beschikbaar voor injectie:

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
  private Button btn = new Button("Genereer Willekeurig Getal");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Het nieuwe willekeurige getal is " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

In dit voorbeeld is de `RandomNumberService` een standaard Spring service bean. De `HelloWorldView` route verklaart het als een constructorparameter, en Spring biedt automatisch de service-instantie wanneer webforJ de route creëert.

Let op dat de routeklasse alleen de `@Route` annotatie gebruikt - geen Spring-stereotypen zoals `@Component` of `@Controller` zijn nodig. Wanneer een gebruiker navigeert naar het rootpad `/`, doet webforJ:

1. Creëert een nieuwe instantie van `HelloWorldView`
2. Vraagt Spring om de afhankelijkheid `RandomNumberService` op te lossen
3. Geeft de service door aan de constructor
4. De route gebruikt de ingesloten service om knopklikken af te handelen

Dit patroon werkt met elke Spring bean - services, repositories, configuratieklassen of aangepaste componenten. De afhankelijkheidsinjectie gebeurt transparant, zodat je je kunt concentreren op het bouwen van je UI terwijl Spring het bekabelen beheert.
