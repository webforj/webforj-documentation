---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 65cff7dec35cab6a33e0d402512c8f86
---
Dependency injection (DI) is een proces waarbij objecten hun afhankelijkheden definiëren via constructorargumenten in plaats van afhankelijkheden zelf te creëren of op te zoeken. De Spring-container injecteert deze afhankelijkheden bij het aanmaken van het object, wat resulteert in schonere code en een betere ontkoppeling tussen componenten.

Bij het gebruik van Spring Boot met webforJ detecteert het framework de Spring-context en past het de objectcreatie aan om gebruik te maken van de dependency injection van Spring. Dit betekent dat je routeklassen afhankelijkheden kunnen declareren van services, repositories en andere Spring-bonen via constructorparameters.

:::tip[Leer meer over dependency injection]
Voor een uitgebreide begrijp van dependency injection patronen en de IoC-container van Spring, zie [de officiële documentatie over dependency injection van Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Hoe webforJ routes creëert met Spring {#how-webforj-creates-routes-with-spring}

webforJ behandelt het maken van routes anders wanneer Spring aanwezig is. Het objectcreatiemechanisme van het framework detecteert Spring Boot op het classpath en delegeert aan Spring's `AutowireCapableBeanFactory` voor het creëren van instanties met dependency injection.

Voor klassen die worden ontdekt via `@Routify`-scanning, maakt webforJ altijd nieuwe instanties aan en hergebruikt nooit bestaande Spring-bonen. Elke gebruikersnavigatie ontvangt een schone route-instantie zonder gedeelde staat. Het creatieproces:

1. Gebruiker navigeert naar een route
2. webforJ vraagt om een nieuwe instantie (negeert bestaande Spring bean-definities)
3. De fabriek van Spring maakt de instantie aan en injecteert constructorafhankelijkheden
4. De route initialiseert met alle afhankelijkheden voldaan

Dit gedrag is opzettelijk en verschilt van typische Spring-bonen. Zelfs als je een route registreert als een Spring-bon met `@Component`, negeert webforJ die bondefinitie en maakt het een nieuwe instantie voor elke navigatie.

## Spring-bonen gebruiken in routes {#using-spring-beans-in-routes}

Je routeklassen hebben geen Spring-annotaties nodig. De `@Route`-annotatie alleen maakt zowel routeontdekking als dependency injection mogelijk:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Gebruik geïnjecteerde services om de UI te bouwen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

De constructor kan ontvangen:
- Services geannoteerd met `@Service`, `@Repository` of `@Component`
- Spring Data-repositories (`JpaRepository`, `CrudRepository`)
- Configuratiewaarden via `@Value`-annotaties
- Spring-infrastructuurbonen (`ApplicationContext`, `Environment`)
- Elke bean die is geregistreerd in de Spring-context

## Werkend voorbeeld {#working-example}

Dit voorbeeld demonstreert een volledig scenario van dependency injection waarbij een Spring-service wordt geïnjecteerd in een webforJ-route. De service beheert de bedrijfslogica terwijl de route de UI-presentatie afhandelt.

Definieer eerst een standaard Spring-service met de annotatie `@Service`. Deze service wordt beheerd door de container van Spring en is beschikbaar voor injectie:

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
  private Button btn = new Button("Genereer willekeurig nummer");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Het nieuwe willekeurige nummer is " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

In dit voorbeeld is de `RandomNumberService` een standaard Spring-servicebean. De `HelloWorldView`-route verklaart deze als een constructorparameter, en Spring levert automatisch de service-instantie wanneer webforJ de route aanmaakt.

Opmerk dat de routeklasse alleen de `@Route`-annotatie gebruikt - geen Spring-stereotypen zoals `@Component` of `@Controller` zijn nodig. Wanneer een gebruiker navigeert naar het hoofdpad `/`, doet webforJ:

1. Maakt een nieuwe instantie aan van `HelloWorldView` 
2. Vraagt Spring om de afhankelijkheid `RandomNumberService` op te lossen
3. Geeft de service door aan de constructor
4. De route gebruikt de geïnjecteerde service om knopklikken te verwerken

Dit patroon werkt met elke Spring-bean - services, repositories, configuratieklassen of aangepaste componenten. De dependency injection gebeurt transparant, waardoor je je kunt concentreren op het bouwen van je UI terwijl Spring het bedrading beheert.
