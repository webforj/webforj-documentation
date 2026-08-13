---
title: Routing
sidebar_position: 15
description: >-
  Inject Spring services and repositories into webforJ @Route classes through
  constructor injection while keeping a fresh instance per navigation.
_i18n_hash: 4bef970301ebc7072162c3dc95b6e544
---
Routing in webforJ met Spring werkt precies op dezelfde manier als in gewone webforJ-toepassingen. Je gebruikt nog steeds de `@Route` annotatie om routes te definiëren, dezelfde navigatiepatronen en dezelfde routelevenscyclus. Het enige verschil is dat wanneer Spring aanwezig is, je routes ook Spring-bonen via constructorinjectie kunnen ontvangen.

Wanneer je naar een route navigeert, maakt webforJ de route-instantie - maar met Spring op het classpath gebruikt het de container van Spring om afhankelijkheden op te lossen. Dit betekent dat je routes de volledige kracht van het Spring-ecosysteem kunnen gebruiken (services, repositories, aangepaste bonen) terwijl ze het vertrouwde webforJ-routinggedrag behouden.

:::tip[Leer meer over dependency injection]
Voor een uitgebreide uitleg over afhankelijkheidsinjectiepatronen en de IoC-container van Spring, zie [de officiële documentatie van Spring over dependency injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Hoe webforJ routes maakt met Spring {#how-webforj-creates-routes-with-spring}

webforJ behandelt routecreatie anders wanneer Spring aanwezig is. Het objectcreatiemechanisme van het framework detecteert Spring Boot op het classpath en delegeert naar Spring's `AutowireCapableBeanFactory` voor het maken van instanties met afhankelijkheidsinjectie.

Voor klassen die worden ontdekt via `@Routify` scanning, maakt webforJ altijd nieuwe instanties en hergebruikt nooit bestaande Spring-bonen. Elke gebruikersnavigatie ontvangt een schone route-instantie zonder gedeelde status. Het creatieproces:

1. De gebruiker navigeert naar een route
2. webforJ vraagt om een nieuwe instantie (negerend eventuele bestaande Spring- definitie van bonen)
3. Spring's fabriek maakt de instantie en injecteert afhankelijkheden van de constructor
4. De route wordt geïnitialiseerd met alle voldane afhankelijkheden

Dit gedrag is intentioneel en verschilt van typische Spring-bonen. Zelfs als je een route registreert als een Spring-bon met `@Component`, negeert webforJ die bonendefinitie en maakt het voor elke navigatie een nieuwe instantie aan.

## Spring-bonen gebruiken in routes {#using-spring-beans-in-routes}

Je routeklassen vereisen geen Spring-annotaties. De `@Route` annotatie alleen stelt zowel routeontdekking als afhankelijkheidsinjectie mogelijk:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    // Gebruik geïnjecteerde services om de UI te bouwen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
  }
}
```

De constructor kan ontvangen:
- Services geannoteerd met `@Service`, `@Repository`, of `@Component`
- Spring Data repositories (`JpaRepository`, `CrudRepository`)
- Configuratie waarden via `@Value` annotaties
- Spring infrastructuur bonen (`ApplicationContext`, `Environment`)
- Elke bon geregistreerd in de Spring-context

## Werkend voorbeeld {#working-example}

Dit voorbeeld demonstreert een compleet scenario van afhankelijkheidsinjectie waarbij een Spring-service in een webforJ-route wordt geïnjecteerd. De service beheert de bedrijfslogica terwijl de route zorgt voor de UI-presentatie.

Definieer eerst een standaard Spring-service met behulp van de `@Service` annotatie. Deze service wordt beheerd door de container van Spring en is beschikbaar voor injectie:

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

In dit voorbeeld is de `RandomNumberService` een standaard Spring service-bon. De `HelloWorldView` route verklaart deze als een constructorparameter, en Spring biedt automatisch de service-instantie aan wanneer webforJ de route aanmaakt.

Let op dat de routeklasse alleen de `@Route` annotatie gebruikt - geen Spring stereotype zoals `@Component` of `@Controller` is nodig. Wanneer een gebruiker navigeert naar het root pad `/`, webforJ:

1. Maakt een nieuwe instantie van `HelloWorldView`
2. Vraagt Spring om de afhankelijkheid `RandomNumberService` op te lossen
3. Geeft de service door aan de constructor
4. De route gebruikt de geïnjecteerde service om knopklikken te verwerken

Dit patroon werkt met elke Spring-bon - services, repositories, configuratieklassen of aangepaste componenten. De afhankelijkheidsinjectie gebeurt transparant, zodat je je kunt concentreren op het bouwen van je UI terwijl Spring het verbinden beheert.
