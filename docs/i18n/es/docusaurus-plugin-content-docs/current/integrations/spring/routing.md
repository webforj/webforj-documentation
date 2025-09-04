---
title: Routing
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
El enrutamiento en webforJ con Spring funciona exactamente de la misma manera que en aplicaciones webforJ simples. Aún utilizas la anotación `@Route` para definir rutas, los mismos patrones de navegación y el mismo ciclo de vida de la ruta. La única diferencia es que cuando Spring está presente, tus rutas también pueden recibir beans de Spring a través de la inyección por constructor.

Cuando navegas a una ruta, webforJ crea la instancia de la ruta, pero con Spring en el classpath, utiliza el contenedor de Spring para resolver dependencias. Esto significa que tus rutas pueden utilizar todo el poder del ecosistema de Spring (servicios, repositorios, beans personalizados) mientras mantienen el comportamiento de enrutamiento familiar de webforJ.

:::tip[Aprende más sobre inyección de dependencias]
Para una comprensión completa de los patrones de inyección de dependencias y el contenedor IoC de Spring, consulta [la documentación oficial de inyección de dependencias de Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Cómo crea webforJ rutas con Spring {#how-webforj-creates-routes-with-spring}

webforJ maneja la creación de rutas de manera diferente cuando Spring está presente. El mecanismo de creación de objetos del marco detecta Spring Boot en el classpath y delega a `AutowireCapableBeanFactory` de Spring para crear instancias con inyección de dependencias.

Para las clases descubiertas a través del escaneo `@Routify`, webforJ siempre crea nuevas instancias y nunca reutiliza beans de Spring existentes. Cada navegación de usuario recibe una nueva instancia de ruta sin estado compartido. El proceso de creación:

1. El usuario navega a una ruta
2. webforJ solicita una nueva instancia (ignorando cualquier definición de bean de Spring existente)
3. La fábrica de Spring crea la instancia e inyecta las dependencias del constructor
4. La ruta se inicializa con todas las dependencias satisfechas

Este comportamiento es intencional y difiere de los beans típicos de Spring. Incluso si registras una ruta como un bean de Spring con `@Component`, webforJ ignora esa definición de bean y crea una nueva instancia para cada navegación.

## Usando beans de Spring en rutas {#using-spring-beans-in-routes}

Tus clases de ruta no requieren anotaciones de Spring. La anotación `@Route` por sí sola habilita tanto el descubrimiento de rutas como la inyección de dependencias:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Usar servicios inyectados para construir la UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

El constructor puede recibir:
- Servicios anotados con `@Service`, `@Repository` o `@Component`
- Repositorios de Spring Data (`JpaRepository`, `CrudRepository`)
- Valores de configuración a través de anotaciones `@Value`
- Beans de infraestructura de Spring (`ApplicationContext`, `Environment`)
- Cualquier bean registrado en el contexto de Spring

## Ejemplo de trabajo {#working-example}

Este ejemplo demuestra un escenario completo de inyección de dependencias donde un servicio de Spring se inyecta en una ruta de webforJ. El servicio gestiona la lógica de negocios mientras que la ruta maneja la presentación de la UI.

Primero, define un servicio Spring estándar utilizando la anotación `@Service`. Este servicio será gestionado por el contenedor de Spring y estará disponible para inyección:

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
  private Button btn = new Button("Generar Número Aleatorio");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("El nuevo número aleatorio es " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

En este ejemplo, `RandomNumberService` es un bean de servicio de Spring estándar. La ruta `HelloWorldView` lo declara como un parámetro del constructor, y Spring proporciona automáticamente la instancia del servicio cuando webforJ crea la ruta.

Observa que la clase de ruta solo usa la anotación `@Route`: no se necesitan estereotipos de Spring como `@Component` o `@Controller`. Cuando un usuario navega a la ruta raíz `/`, webforJ:

1. Crea una nueva instancia de `HelloWorldView` 
2. Le pide a Spring que resuelva la dependencia `RandomNumberService`
3. Pasa el servicio al constructor
4. La ruta utiliza el servicio inyectado para manejar los clics del botón

Este patrón funciona con cualquier bean de Spring: servicios, repositorios, clases de configuración o componentes personalizados. La inyección de dependencias ocurre de forma transparente, lo que te permite concentrarte en construir tu UI mientras Spring gestiona el cableado.
