---
sidebar_position: 1
title: Enrutamiento
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ca4837305e1ca2ca2b6a4a244c8103f1
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

En las aplicaciones web modernas, **rutear** se refiere al proceso de gestión de la navegación entre diferentes vistas o componentes según la URL o ruta actual. En webforJ, el enrutamiento establece un marco sofisticado para la **navegación del lado del cliente**, donde las actualizaciones de la interfaz de usuario ocurren dinámicamente sin requerir recargas completas de la página, mejorando el rendimiento de tu aplicación.

## Enrutamiento tradicional vs del lado del cliente {#traditional-vs-client-side-routing}

En el enrutamiento tradicional del lado del servidor, cuando un usuario hace clic en un enlace, el navegador envía una solicitud al servidor para un nuevo documento. El servidor responde enviando una nueva página HTML, lo que obliga al navegador a reevaluar el CSS y el JavaScript, volver a renderizar todo el documento y restablecer el estado de la aplicación. Este ciclo introduce retrasos e ineficiencias, ya que el navegador debe volver a cargar recursos y el estado de la página. El proceso generalmente implica:

1. **Solicitud**: El usuario navega a una nueva URL, lo que desencadena una solicitud al servidor.
2. **Respuesta**: El servidor envía de vuelta un nuevo documento HTML junto con activos relacionados (CSS, JS).
3. **Renderización**: El navegador vuelve a renderizar toda la página, a menudo perdiendo el estado de las páginas cargadas anteriormente.

Este enfoque puede llevar a cuellos de botella en el rendimiento y experiencias de usuario subóptimas debido a recargas completas de página repetitivas.

**El enrutamiento del lado del cliente** en webforJ resuelve esto al permitir la navegación directamente en el navegador, actualizando dinámicamente la interfaz de usuario sin enviar una nueva solicitud al servidor. Así es como funciona:

1. **Solicitud inicial única**: El navegador carga la aplicación una vez, incluyendo todos los activos necesarios (HTML, CSS, JavaScript).
2. **Gestión de URLs**: El enrutador escucha los cambios de URL y actualiza la vista según la ruta actual.
3. **Renderización dinámica de componentes**: El enrutador mapea la URL a un componente y lo renderiza dinámicamente, sin refrescar la página.
4. **Preservación del estado**: El estado de la aplicación se mantiene entre navegaciones, asegurando una transición suave entre vistas.

Este diseño permite **enlaces profundos** y **gestión de estado basada en URLs**, permitiendo a los usuarios marcar y compartir páginas específicas dentro de la aplicación mientras disfrutan de una experiencia fluida de una sola página.

## Principios fundamentales {#core-principles}

- **Mapeo de componentes basado en URL**: En webforJ, las rutas están directamente vinculadas a los componentes de la interfaz de usuario. Un patrón de URL se asigna a un componente específico, dictando qué contenido se muestra según la ruta actual.
- **Enrutamiento declarativo**: Las rutas se definen de forma declarativa, típicamente usando anotaciones. Cada ruta corresponde a un componente que se renderiza cuando se coincide con la ruta.
- **Navegación dinámica**: El enrutador cambia dinámicamente entre vistas sin recargar la página, manteniendo la aplicación receptiva y rápida.

## Ejemplo de enrutamiento del lado del cliente en webforJ {#example-of-client-side-routing-in-webforj}

Aquí tienes un ejemplo de cómo definir una ruta para un componente `UserProfileView` para mostrar detalles del usuario según el parámetro `id` en la URL:

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
- El parámetro `id` capturaría `john` de la URL y te permitiría usarlo dentro del componente para obtener y mostrar los datos del usuario.

## Temas {#topics}

<DocCardList className="topics-section" />
