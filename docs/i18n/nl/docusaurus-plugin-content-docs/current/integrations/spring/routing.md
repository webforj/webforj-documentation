---
title: Routeren
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
Routing in webforJ met Spring werkt precies hetzelfde als in gewone webforJ-toepassingen. Je gebruikt nog steeds de `@Route` annotatie om routes te definiëren, dezelfde navigatiepatronen en dezelfde routelevenscyclus. Het enige verschil is dat wanneer Spring aanwezig is, je routes ook Spring-beans kunnen ontvangen via constructorinjectie.

Wanneer je naar een route navigeert, creëert webforJ de route-instantie - maar met Spring op het classpath gebruikt het de container van Spring om afhankelijkheden op te lossen. Dit betekent dat je routes de volledige kracht van het Spring-ecosysteem (services, repositories, aangepaste beans) kunnen gebruiken, terwijl ze het vertrouwde routinggedrag van webforJ behouden.

:::tip[Leer meer over afhankelijkheidsinjectie]
Voor een uitgebreide understanding van afhankelijkheidsinjectiepatronen en de IoC-container van Spring, zie [Spring's officiële documentatie over afhankelijkheidsinjectie](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Hoe webforJ routes met Spring creëert {#how-webforj-creates-routes-with-spring}

webforJ behandelt routecreatie anders wanneer Spring aanwezig is. Het objectcreatiemechanisme van het framework detecteert Spring Boot op het classpath en delegeert aan Spring's `AutowireCapableBeanFactory` voor het creëren van instanties met afhankelijkheidsinjectie.

Voor klassen die ontdekt zijn via `@Routify` scannen, creëert webforJ altijd nieuwe instanties en hergebruikt nooit bestaande Spring-beans. Elke gebruikersnavigatie ontvangt een schone route-instantie zonder gedeelde staat. Het creatieproces:

1. Gebruiker navigeert naar een route
2. webforJ vraagt om een nieuwe instantie (negeert bestaande Spring bean-definities)
3. De fabrieksfunctie van Spring creëert de instantie en injecteert constructorafhankelijkheden
4. De route initialiseert met alle voldane afhankelijkheden

Dit gedrag is opzettelijk en verschilt van typische Spring-beans. Zelfs als je een route registreert als een Spring-bean met `@Component`, negeert webforJ die bean-definitie en creëert het een nieuwe instantie voor elke navigatie.

## Spring-beans in routes gebruiken {#using-spring-beans-in-routes}

Je routeklassen hoeven geen Spring-annotaties te hebben. Alleen de `@Route` annotatie staat routeontdekking en afhankelijkheidsinjectie toe:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Gebruik geïnjecteerde services om de UI op te bouwen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

De constructor kan ontvangen:
- Services geannoteerd met `@Service`, `@Repository` of `@Component`
- Spring Data repositories (`JpaRepository`, `CrudRepository`)
- Configuratiewaarden via `@Value` annotaties
- Spring infrastructuurbeantjes (`ApplicationContext`, `Environment`)
- Elke bean geregistreerd in de Spring-context

## Werkend voorbeeld {#working-example}

Dit voorbeeld demonstreert een complete afhankelijkheidsinjectiescenario waarin een Spring-service in een webforJ-route wordt geïnjecteerd. De service beheert de bedrijfslogica terwijl de route de UI-presentatie afhandelt.

Definieer eerst een standaard Spring-service met behulp van de `@Service` annotatie. Deze service zal worden beheerd door de container van Spring en beschikbaar zijn voor injectie:

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
  private Button btn = new Button("Genereer Willekeurig Nummer");

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

In dit voorbeeld is de `RandomNumberService` een standaard Spring-service bean. De `HelloWorldView` route verklaart het als een constructorparameter, en Spring biedt automatisch de service-instantie aan wanneer webforJ de route creëert.

Merk op dat de routeklasse alleen de `@Route` annotatie gebruikt - geen Spring-stereotypen zoals `@Component` of `@Controller` zijn nodig. Wanneer een gebruiker naar het startpad `/` navigeert, webforJ:

1. Maakt een nieuwe instantie van `HelloWorldView` 
2. Vraagt Spring om de afhankelijkheid `RandomNumberService` op te lossen
3. Geeft de service door aan de constructor
4. De route gebruikt de geïnjecteerde service om knopklikken af te handelen

Dit patroon werkt met elke Spring-bean - services, repositories, configuratieklassen of aangepaste componenten. De afhankelijkheidsinjectie gebeurt transparant, zodat je je kunt concentreren op het bouwen van je UI terwijl Spring het bedrading beheert.
