---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

En las aplicaciones web modernas, **routing** se refiere al proceso de gestionar la navegación entre diferentes vistas o componentes en función de la URL o ruta actual. En webforJ, el routing establece un sofisticado marco para la **navegación del lado del cliente**, donde las actualizaciones de la UI ocurren de manera dinámica sin requerir la recarga completa de la página, mejorando el rendimiento de tu aplicación.

## Routing tradicional vs del lado del cliente {#traditional-vs-client-side-routing}

En el routing tradicional del lado del servidor, cuando un usuario hace clic en un enlace, el navegador envía una solicitud al servidor para un nuevo documento. El servidor responde enviando una nueva página HTML, lo que obliga al navegador a reevaluar CSS y JavaScript, volver a renderizar todo el documento y restablecer el estado de la aplicación. Este ciclo introduce retrasos e ineficiencias, ya que el navegador debe recargar recursos y el estado de la página. El proceso típicamente involucra:

1. **Solicitud**: El usuario navega a una nueva URL, lo que desencadena una solicitud al servidor.
2. **Respuesta**: El servidor envía un nuevo documento HTML junto con activos relacionados (CSS, JS).
3. **Renderización**: El navegador vuelve a renderizar toda la página, a menudo perdiendo el estado de las páginas cargadas previamente.

Este enfoque puede conducir a cuellos de botella en el rendimiento y experiencias de usuario subóptimas debido a recargas completas de página repetidas.

**Routing del lado del cliente** en webforJ resuelve esto al permitir la navegación directamente en el navegador, actualizando dinámicamente la UI sin enviar una nueva solicitud al servidor. Así es como funciona:

1. **Solicitud Inicial Única**: El navegador carga la aplicación una vez, incluyendo todos los activos requeridos (HTML, CSS, JavaScript).
2. **Gestión de URL**: El router escucha los cambios en la URL y actualiza la vista basada en la ruta actual.
3. **Renderización Dinámica de Componentes**: El router mapea la URL a un componente y lo renderiza dinámicamente, sin refrescar la página.
4. **Preservación del Estado**: El estado de la aplicación se mantiene entre navegaciones, asegurando una transición suave entre vistas.

Este diseño permite **deep linking** y **gestión del estado basada en URL**, permitiendo a los usuarios marcar y compartir páginas específicas dentro de la aplicación mientras disfrutan de una experiencia fluida de una sola página.

## Principios básicos {#core-principles}

- **Mapeo de Componentes Basado en URL**: En webforJ, las rutas están directamente vinculadas a componentes de UI. Un patrón de URL se mapea a un componente específico, dictando qué contenido se muestra en función de la ruta actual.
- **Routing Declarativo**: Las rutas se definen de manera declarativa, típicamente utilizando anotaciones. Cada ruta corresponde a un componente que se renderiza cuando la ruta coincide.
- **Navegación Dinámica**: El router cambia dinámicamente entre vistas sin recargar la página, manteniendo la aplicación responsive y rápida.

## Ejemplo de routing del lado del cliente en webforJ {#example-of-client-side-routing-in-webforj}

Aquí hay un ejemplo de la definición de una ruta para un componente `UserProfileView` que muestra los detalles del usuario en función del parámetro `id` en la URL:

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

En esta configuración:

- Navegar a `/user/john` renderizaría el componente `UserProfileView`.
- El parámetro `id` capturaría `john` de la URL y te permitiría utilizarlo dentro del componente para obtener y mostrar los datos del usuario.

## Temas {#topics}

<DocCardList className="topics-section" />
