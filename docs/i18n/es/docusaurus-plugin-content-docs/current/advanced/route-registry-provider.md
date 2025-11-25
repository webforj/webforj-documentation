---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# Proveedor de Registro de Rutas <DocChip chip='since' label='25.11' />

El `RouteRegistryProvider` es una Interfaz de Proveedor de Servicios (SPI) que permite a los marcos de integración proporcionar mecanismos de descubrimiento de rutas personalizados. Esto permite que los marcos integren su propio escaneo de classpath y sistemas de inyección de dependencias con la infraestructura de enrutamiento de webforJ.

## Visión general {#overview}

webforJ descubre rutas escaneando paquetes en busca de componentes anotados con `@Route`. El SPI `RouteRegistryProvider` permite que los marcos anulen este comportamiento predeterminado con su propio mecanismo de descubrimiento.

Utiliza este SPI cuando:

- Integres con marcos de inyección de dependencias (Spring, CDI, ...)
- Soportes entornos especializados (OSGi, cargadores de clases personalizados, GraalVM)
- Construyas adaptadores de marcos que necesiten gestionar el ciclo de vida de los componentes de ruta
- Reutilices escaneos de classpath existentes para optimizar el tiempo de inicio

## Cómo funciona {#how-it-works}

Cuando se llama a `RouteRegistry.ofPackage()`, webforJ verifica proveedores registrados a través de `ServiceLoader` de Java. Si se encuentra un proveedor, el descubrimiento de rutas se delega a ese proveedor. De lo contrario, se utiliza el mecanismo de escaneo predeterminado.

## Construyendo tu proveedor {#building-your-provider}

Para crear un proveedor de descubrimiento de rutas personalizado, implementa la interfaz SPI y regístralo a través del mecanismo ServiceLoader de Java.

### Implementar el SPI {#implement-the-spi}

Crea una clase que implemente `RouteRegistryProvider`:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Escanear paquetes y registrar componentes @Route
  }
}
```

### Habilitar descubrimiento {#enable-discovery}

Agrega el nombre de clase completamente calificado de tu proveedor a `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
