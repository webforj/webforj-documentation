---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: 03f86cbc79737ca141cc9d2e1ad2e28f
---
<!-- vale Google.Headings = NO -->
# Proveedor de Registro de Rutas <DocChip chip='since' label='25.11' />
<!-- vale Google.Headings = YES -->

El `RouteRegistryProvider` es una Interfaz de Proveedor de Servicio (SPI) que permite a los marcos de integración proporcionar mecanismos personalizados de descubrimiento de rutas. Esto permite que los marcos integren su propio escaneo de classpath y sistemas de inyección de dependencias con la infraestructura de enrutamiento de webforJ.

## Descripción general {#overview}

webforJ descubre rutas escaneando paquetes en busca de componentes anotados con `@Route`. La SPI `RouteRegistryProvider` permite a los marcos sobreescribir este comportamiento predeterminado con su propio mecanismo de descubrimiento.

Utiliza esta SPI cuando:

- Se integre con marcos de inyección de dependencias, como Spring, o Contextos e Inyección de Dependencias (CDI)
- Se soporte entornos especializados (OSGi, cargadores de clases personalizados, GraalVM)
- Se construyan adaptadores de marcos que necesiten gestionar el ciclo de vida de los componentes de ruta
- Se reutilicen escaneos de classpath existentes para optimizar el tiempo de inicio

## Cómo funciona {#how-it-works}

Cuando se llama a `RouteRegistry.ofPackage()`, webforJ verifica los proveedores registrados a través del `ServiceLoader` de Java. Si se encuentra un proveedor, el descubrimiento de rutas se delega a ese proveedor. De lo contrario, se utiliza el mecanismo de escaneo predeterminado.

## Creando tu proveedor {#building-your-provider}

Para crear un proveedor de descubrimiento de rutas personalizado, implementa la interfaz SPI y regístrala a través del mecanismo ServiceLoader de Java.

### Implementa la SPI {#implement-the-spi}

Crea una clase que implemente `RouteRegistryProvider`:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Escanea paquetes y registra componentes @Route
  }
}
```

### Habilita el descubrimiento {#enable-discovery}

Agrega el nombre de clase completamente calificado de tu proveedor a `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
