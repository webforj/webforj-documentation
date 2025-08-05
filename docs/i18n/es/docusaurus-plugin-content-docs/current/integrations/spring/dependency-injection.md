---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
La inyección de dependencias (DI) es un proceso en el que los objetos definen sus dependencias a través de argumentos del constructor en lugar de crear o buscar las dependencias por sí mismos. El contenedor de Spring inyecta estas dependencias al crear el objeto, lo que resulta en un código más limpio y una mejor desconexión entre componentes.

Al usar Spring Boot con webforJ, el marco detecta el contexto de Spring y adapta su creación de objetos para usar la inyección de dependencias de Spring. Esto significa que tus clases de ruta pueden declarar dependencias en servicios, repositorios y otros beans de Spring a través de parámetros del constructor.

:::tip[Aprende más sobre la inyección de dependencias]
Para una comprensión completa de los patrones de inyección de dependencias y el contenedor IoC de Spring, consulta la [documentación oficial de inyección de dependencias de Spring](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Cómo webforJ crea rutas con Spring {#how-webforj-creates-routes-with-spring}

webforJ maneja la creación de rutas de manera diferente cuando Spring está presente. El mecanismo de creación de objetos del marco detecta Spring Boot en el classpath y delega en `AutowireCapableBeanFactory` de Spring para crear instancias con inyección de dependencias.

Para las clases descubiertas a través del escaneo de `@Routify`, webforJ siempre crea nuevas instancias y nunca reutiliza beans existentes de Spring. Cada navegación del usuario recibe una instancia de ruta limpia sin estado compartido. El proceso de creación:

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
    
    // Usa los servicios inyectados para construir la UI
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

Este ejemplo demuestra un escenario completo de inyección de dependencias donde un servicio de Spring se inyecta en una ruta de webforJ. El servicio gestiona la lógica de negocio mientras que la ruta maneja la presentación de la UI.

Primero, define un servicio estándar de Spring usando la anotación `@Service`. Este servicio será gestionado por el contenedor de Spring y estará disponible para inyección:

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
  private Button btn = new Button("Generar número aleatorio");

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

En este ejemplo, el `RandomNumberService` es un bean de servicio estándar de Spring. La ruta `HelloWorldView` lo declara como un parámetro del constructor, y Spring proporciona automáticamente la instancia del servicio cuando webforJ crea la ruta.

Observa que la clase de ruta solo utiliza la anotación `@Route` - no se necesitan estereotipos de Spring como `@Component` o `@Controller`. Cuando un usuario navega a la ruta raíz `/`, webforJ:

1. Crea una nueva instancia de `HelloWorldView` 
2. Pide a Spring que resuelva la dependencia de `RandomNumberService`
3. Pasa el servicio al constructor
4. La ruta utiliza el servicio inyectado para manejar clics en el botón

Este patrón funciona con cualquier bean de Spring - servicios, repositorios, clases de configuración o componentes personalizados. La inyección de dependencias ocurre de manera transparente, lo que te permite concentrarte en construir tu UI mientras Spring gestiona el cableado.
