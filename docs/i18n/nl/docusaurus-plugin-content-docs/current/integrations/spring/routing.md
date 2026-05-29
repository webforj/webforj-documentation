---
title: Routing
sidebar_position: 15
_i18n_hash: 8518a84a9c7a543b73cf4de16658f650
---
Routing in webforJ met Spring werkt precies hetzelfde als in gewone webforJ-applicaties. Je gebruikt nog steeds de `@Route` annotatie om routes te definiëren, dezelfde navigatiepatronen en dezelfde route levenscyclus. Het enige verschil is dat wanneer Spring aanwezig is, je routes ook Spring beans kunnen ontvangen via constructorinjectie.

Wanneer je naar een route navigeert, maakt webforJ de route-instantie aan - maar met Spring op de classpath gebruikt het de container van Spring om afhankelijkheden op te lossen. Dit betekent dat je routes de volledige kracht van het Spring-ecosysteem (diensten, repositories, aangepaste beans) kunnen gebruiken terwijl ze het vertrouwde webforJ-routing gedrag behouden.

:::tip[Leer meer over afhankelijkheidsinjectie]
Voor een uitgebreide uitleg over patronen van afhankelijkheidsinjectie en de IoC-container van Spring, zie [de officiële documentatie van Spring over afhankelijkheidsinjectie](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Hoe webforJ routes aanmaakt met Spring {#how-webforj-creates-routes-with-spring}

webforJ behandelt de creatie van routes anders wanneer Spring aanwezig is. Het objectcreatiemechanisme van het framework detecteert Spring Boot op de classpath en delegeert aan Spring's `AutowireCapableBeanFactory` voor het aanmaken van instanties met afhankelijkheidsinjectie.

Voor klassen die ontdekt zijn via `@Routify` scanning, maakt webforJ altijd nieuwe instanties aan en hergebruikt nooit bestaande Spring beans. Elke gebruikersnavigatie ontvangt een schone route-instantie zonder gedeelde staat. Het creatieproces:

1. Gebruiker navigeert naar een route
2. webforJ vraagt om een nieuwe instantie (negeert bestaande definitie van Spring beans)
3. De fabriek van Spring maakt de instantie aan en injecteert constructorafhankelijkheden
4. De route initialiseert met alle tevreden afhankelijkheden

Dit gedrag is opzettelijk en verschilt van typische Spring beans. Zelfs als je een route registreert als een Spring bean met `@Component`, negeert webforJ die bean-definitie en maakt het een nieuwe instantie voor elke navigatie.

## Spring beans gebruiken in routes {#using-spring-beans-in-routes}

Je routeklassen vereisen geen Spring-annotaties. Alleen de `@Route` annotatie maakt zowel routeontdekking als afhankelijkheidsinjectie mogelijk:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Gebruik geïnjecteerde services om UI te bouwen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    self.add(metricsPanel);
  }
}
```

De constructor kan ontvangen:
- Diensten geannoteerd met `@Service`, `@Repository` of `@Component`
- Spring Data repositories (`JpaRepository`, `CrudRepository`)
- Configuratiewaarden via `@Value` annotaties
- Spring infrastructuur beans (`ApplicationContext`, `Environment`)
- Elke bean geregistreerd in de Spring-context

## Werkt voorbeeld {#working-example}

Dit voorbeeld demonstreert een complete afhankelijkheidsinjectiecena waar een Spring-service wordt geïnjecteerd in een webforJ-route. De service beheert de bedrijfslogica terwijl de route de UI-presentatie afhandelt.

Eerst definieer een standaard Spring-service met de annotatie `@Service`. Deze service wordt beheerd door de container van Spring en is beschikbaar voor injectie:

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

In dit voorbeeld is de `RandomNumberService` een standaard Spring-servicebean. De route `HelloWorldView` verklaart deze als een constructorparameter, en Spring verstrekt automatisch de service-instantie wanneer webforJ de route aanmaakt.

Merk op dat de routeklasse alleen de `@Route` annotatie gebruikt - geen Spring-stereotypen zoals `@Component` of `@Controller` zijn nodig. Wanneer een gebruiker navigeert naar het rootpad `/`, doet webforJ:

1. Maakt een nieuwe instantie van `HelloWorldView` aan 
2. Vraagt Spring om de afhankelijkheid van `RandomNumberService` op te lossen
3. Geeft de service door aan de constructor
4. De route gebruikt de geïnjecteerde service om knoppenklikken te verwerken

Dit patroon werkt met elke Spring bean - diensten, repositories, configuratieklassen of aangepaste componenten. De afhankelijkheidsinjectie gebeurt transparant, waardoor je je kunt concentreren op het bouwen van je UI terwijl Spring het bekabelen beheert.
