---
title: Routing
sidebar_position: 15
description: >-
  Inject Spring services and repositories into webforJ @Route classes through
  constructor injection while keeping a fresh instance per navigation.
_i18n_hash: 4bef970301ebc7072162c3dc95b6e544
---
El enrutamiento en webforJ con Spring funciona exactamente de la misma manera que en aplicaciones webforJ normales. Aún utilizas la anotación `@Route` para definir rutas, los mismos patrones de navegación y el mismo ciclo de vida de rutas. La única diferencia es que, cuando Spring está presente, tus rutas también pueden recibir beans de Spring a través de la inyección por constructor.

Cuando navegas a una ruta, webforJ crea la instancia de la ruta, pero con Spring en el classpath, utiliza el contenedor de Spring para resolver dependencias. Esto significa que tus rutas pueden usar todo el poder del ecosistema de Spring (servicios, repositorios, beans personalizados) mientras mantienen el comportamiento familiar de enrutamiento de webforJ.

:::tip[Aprende más sobre inyección de dependencias]
Para una comprensión integral de los patrones de inyección de dependencias y el contenedor IoC de Spring, consulta [la documentación oficial de inyección de dependencias de Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Cómo webforJ crea rutas con Spring {#how-webforj-creates-routes-with-spring}

webforJ maneja la creación de rutas de manera diferente cuando Spring está presente. El mecanismo de creación de objetos del marco detecta Spring Boot en el classpath y delega en `AutowireCapableBeanFactory` de Spring para crear instancias con inyección de dependencias.

Para las clases descubiertas a través del escaneo de `@Routify`, webforJ siempre crea nuevas instancias y nunca reutiliza beans existentes de Spring. Cada navegación de usuario recibe una instancia de ruta limpia sin estado compartido. El proceso de creación:

1. El usuario navega a una ruta
2. webforJ solicita una nueva instancia (ignorando cualquier definición de bean existente en Spring)
3. La fábrica de Spring crea la instancia e inyecta las dependencias del constructor
4. La ruta se inicializa con todas las dependencias satisfechas

Este comportamiento es intencionado y difiere de los beans típicos de Spring. Incluso si registras una ruta como un bean de Spring con `@Component`, webforJ ignora esa definición de bean y crea una nueva instancia para cada navegación.

## Usando beans de Spring en rutas {#using-spring-beans-in-routes}

Tus clases de ruta no requieren anotaciones de Spring. La anotación `@Route` por sí sola permite tanto el descubrimiento de rutas como la inyección de dependencias:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    // Usa servicios inyectados para construir UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
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

Este ejemplo demuestra un escenario completo de inyección de dependencias donde un servicio de Spring se inyecta en una ruta de webforJ. El servicio gestiona la lógica empresarial mientras que la ruta maneja la presentación de la interfaz de usuario.

Primero, define un servicio estándar de Spring utilizando la anotación `@Service`. Este servicio será gestionado por el contenedor de Spring y estará disponible para la inyección:

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

En este ejemplo, el `RandomNumberService` es un bean de servicio de Spring estándar. La ruta `HelloWorldView` lo declara como un parámetro del constructor, y Spring proporciona automáticamente la instancia del servicio cuando webforJ crea la ruta.

Nota que la clase de ruta solo utiliza la anotación `@Route` - no se necesitan estereotipos de Spring como `@Component` o `@Controller`. Cuando un usuario navega a la ruta raíz `/`, webforJ:

1. Crea una nueva instancia de `HelloWorldView`
2. Pide a Spring que resuelva la dependencia de `RandomNumberService`
3. Pasa el servicio al constructor
4. La ruta utiliza el servicio inyectado para manejar los clics del botón

Este patrón funciona con cualquier bean de Spring: servicios, repositorios, clases de configuración o componentes personalizados. La inyección de dependencias ocurre de manera transparente, permitiéndote concentrarte en construir tu interfaz de usuario mientras Spring gestiona el cableado.
